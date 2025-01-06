package comcarniceria.controladores.admin;

import comcarniceria.entidades.Producto;
import comcarniceria.servicios.ImagenService;
import comcarniceria.servicios.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/productos")
public class Admin_ProductoController {

    private final ProductoService productoService;
    private final ImagenService imagenService;

    public Admin_ProductoController(ProductoService productoService, ImagenService imagenService) {
        this.productoService = productoService;
        this.imagenService = imagenService;
    }

    @GetMapping
    public String mostrarTodos(Model model) {
        List<Producto> productos = productoService.buscarEntidades();
        model.addAttribute("productos", productos);
        return "admin/lista-productos";
    }

    @GetMapping("/crear")
    public String formularioCreacionProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "admin/producto-form";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@RequestParam("nombre") String nombre,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam("precio") double precio,
                                  @RequestParam("cantiadStock") int cantiadStock,
                                  @RequestParam("imagen") MultipartFile imagen) throws Exception { // al poner imagen no puedo usar @ModelAttribute, tengo que usar @RequestParam.

        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCantiadStock(cantiadStock);


            if (!imagen.isEmpty()) {
                String imagenRuta = imagenService.guardarImagen(imagen);
                producto.setImagen(imagenRuta);
            }
            productoService.guardar(producto);

            return "redirect:/admin/productos";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String formularioEdicionProducto(@PathVariable Long id, Model model) throws Exception {
        Producto productoEditado = productoService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        model.addAttribute("producto", productoEditado);

        return "admin/producto-form";
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicionProducto(@PathVariable Long id,
                                         @RequestParam("nombre") String nombre,
                                         @RequestParam("descripcion") String descripcion,
                                         @RequestParam("precio") double precio,
                                         @RequestParam("cantiadStock") int cantiadStock,
                                         @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws Exception {
        try {
            Producto productoEditado = productoService.encontrarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


            productoEditado.setNombre(nombre);
            productoEditado.setDescripcion(descripcion);
            productoEditado.setPrecio(precio);
            productoEditado.setCantiadStock(cantiadStock);

            if (imagen != null && !imagen.isEmpty()) {
                String imagenRuta = imagenService.guardarImagen(imagen);
                productoEditado.setImagen(imagenRuta);
            }
            productoService.editar(id, productoEditado);
            return "redirect:/admin/productos";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            Producto producto = productoService.encontrarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Eliminar la imagen del sistema de archivos si existe
            if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
                imagenService.eliminarImagen(producto.getImagen());
            }
            productoService.eliminarPorId(id);
            return "redirect:/admin/productos";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
