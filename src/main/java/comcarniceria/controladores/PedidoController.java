package comcarniceria.controladores;

import comcarniceria.entidades.Carrito;
import comcarniceria.entidades.Pedido;
import comcarniceria.entidades.Producto;
import comcarniceria.servicios.PedidoService;
import comcarniceria.servicios.ProductoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("carrito")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ProductoService productoService;

    public PedidoController(PedidoService pedidoService, ProductoService productoService) {
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }

    @PostMapping("/pedido/confirmar")
    public String confirmarPedido(@ModelAttribute Carrito carrito, HttpSession session) throws Exception {

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal((int) carrito.calcularTotal());

        List<Producto> productos = new ArrayList<>();
        for (Producto producto : carrito.getProductos()) {
            Producto productoPersistido = productoService.encontrarPorId(producto.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no disponible"));
            productos.add(productoPersistido);
        }
        pedido.setProductos(productos);

        // Guardar pedido en la base de datos
        pedidoService.guardar(pedido);

        // Guardar los productos para el PDF posterior en la sesión
        session.setAttribute("productosDelPedido", productos);

        // Limpiar el carrito después de confirmar el pedido
        carrito.getProductos().clear();

        return "redirect:/pedido/exito";
    }

    @GetMapping("/pedido/exito")
    String pedidoExito(Model model) {
        return "pedido-exito";
    }

    @GetMapping("/pedido/pdf")
    public void generarPdf(HttpSession session, HttpServletResponse response) throws IOException {
        // Recuperar los productos de la sesión
        List<Producto> productos = (List<Producto>) session.getAttribute("productosDelPedido");

        if (productos == null || productos.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay productos en el pedido.");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pedido_confirmado.pdf");

        // Crear un documento PDF vacío
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Crear flujo de contenido
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(100, 750);

        // Título
        contentStream.showText("Pedido Confirmado");
        contentStream.newLineAtOffset(0, -20);

        // Fecha del pedido
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaPedido = LocalDateTime.now().format(formatter);
        contentStream.showText("Fecha del Pedido: " + fechaPedido);
        contentStream.newLineAtOffset(0, -20);

        // Fecha de entrega (3 días después)
        String fechaEntrega = LocalDateTime.now().plusDays(3).format(formatter);
        contentStream.showText("Fecha de Entrega Estimada: " + fechaEntrega);
        contentStream.newLineAtOffset(0, -20);

        // Detalles de los productos
        contentStream.showText("Productos:");
        contentStream.newLineAtOffset(0, -20);

        for (Producto producto : productos) {
            contentStream.showText("- " + producto.getNombre() + " - " + producto.getPrecio() + "€");
            contentStream.newLineAtOffset(0, -15);
        }

        // Total del pedido
        double total = productos.stream().mapToDouble(Producto::getPrecio).sum();
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Total: " + total + "€");

        // Cerrar flujo de contenido y documento
        contentStream.endText();
        contentStream.close();

        // Escribir el documento PDF en la respuesta HTTP
        document.save(response.getOutputStream());
        document.close();
    }
}
