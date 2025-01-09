package comcarniceria.controladores;

import comcarniceria.entidades.Producto;
import comcarniceria.servicios.CestaService;
import comcarniceria.servicios.ProductoEnCestaService;
import comcarniceria.servicios.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductoEnCestaController {

    private final ProductoEnCestaService productoEnCestaService;
    private final CestaService cestaService;
    private final ProductoService productoService;

    public ProductoEnCestaController(ProductoEnCestaService productoEnCestaService, CestaService cestaService, ProductoService productoService) {
        this.productoEnCestaService = productoEnCestaService;
        this.cestaService = cestaService;
        this.productoService = productoService;
    }

    @PostMapping("/agregarProducto")
    public String agregarProductoACesta(@RequestParam Long productoId,
                                        @RequestParam(required = false) Long cestaId,
                                        @RequestParam Integer cantidad) throws Exception {
        if (cestaId == null) {
            throw new IllegalArgumentException("El id de la cesta no puede ser nulo");
        }
        Producto producto = productoService.encontrarPorId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        cestaService.agregarProductoACesta(cestaId, productoId, cantidad);
        return "redirect:/productos";
    }

    @PostMapping("/eliminarProducto/{cestaId}")
    public String eliminarProductosDeCesta(@PathVariable Long cestaId,
                                           @RequestParam Long productoId,
                                           RedirectAttributes redirectAttributes) {
        try {
            boolean confirmacion = true;
            if (confirmacion) {
                cestaService.eliminarProductoDeCesta(cestaId, productoId);
                redirectAttributes.addFlashAttribute("success", "Producto eliminado de la cesta con éxito.");
            } else {
                redirectAttributes.addFlashAttribute("info", "Eliminación de producto cancelada.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el producto de la cesta.");
        }
        return "redirect:/productos";
    }

    @PostMapping("/incrementarProducto/{id}")
    public String incrementarProductoEnCesta(@PathVariable Long cestaId,
                                             @RequestParam Long productoId,
                                             RedirectAttributes redirectAttributes) throws Exception {
        try {
            cestaService.agregarProductoACesta(cestaId, productoId, 1);
            redirectAttributes.addFlashAttribute("success", "Producto inccrementado en la cesta con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo inccrementar el producto en la cesta.");
        }
        return "redirect:/productos";
    }


    @PostMapping("/decrementarProducto/{id}")
    public String decrementarProductoEnCesta(@PathVariable Long cestaId,
                                             @RequestParam Long productoId,
                                             RedirectAttributes redirectAttributes) throws Exception {
        try {
            cestaService.agregarProductoACesta(cestaId, productoId, -1);
            redirectAttributes.addFlashAttribute("success", "Producto decrementado en la cesta con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo decrementar el producto en la cesta.");
        }
        return "redirect:/productos";
    }

    @PostMapping("/actualizarCantidad/{cestaId}")
    public String actualizarCantidadProductosEnCesta(@PathVariable Long cestaId,
                                                     @RequestParam Long productoId,
                                                     @RequestParam Integer cantidad,
                                                     RedirectAttributes redirectAttributes) {
        try {
            if (cantidad <= 0) {
                boolean confirmacion = true;
                if (confirmacion) {
                    cestaService.eliminarProductoDeCesta(cestaId, productoId);
                    redirectAttributes.addFlashAttribute("success", "Producto eliminado de la cesta con éxito.");
                } else {
                    redirectAttributes.addFlashAttribute("info", "Eliminación de producto cancelada.");
                }
            } else {
                cestaService.actualizarCantidadProductoEnCesta(cestaId, productoId, cantidad);
                redirectAttributes.addFlashAttribute("success", "Cantidad del producto actualizada con éxito.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar la cantidad del producto.");
        }
        return "redirect:/productos";
    }
}
