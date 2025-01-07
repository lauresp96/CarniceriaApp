package comcarniceria.controladores;

import comcarniceria.entidades.Carrito;
import comcarniceria.entidades.Producto;
import comcarniceria.servicios.CarritoService;
import comcarniceria.servicios.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @ModelAttribute("carrito")
    public Carrito crearCarrito() {
        return new Carrito();
    }

    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam("productoId") Long productoId,
                                   @ModelAttribute("carrito") Carrito carrito) {
        carritoService.agregarProductoAlCarrito(productoId, carrito);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(@ModelAttribute("carrito") Carrito carrito, Model model) {
        model.addAttribute("productos", carrito.getProductos());
        model.addAttribute("total", carrito.calcularTotal());
        return "carrito";
    }

    @PostMapping("/carrito/eliminar")
    public String eliminarProducto(@RequestParam Long productoId,
                                   @ModelAttribute("carrito") Carrito carrito) {
        carritoService.eliminarProductoDelCarrito(productoId, carrito);
        return "redirect:/carrito";
    }
}
