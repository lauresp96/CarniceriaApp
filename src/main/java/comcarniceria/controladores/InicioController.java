package comcarniceria.controladores;

import comcarniceria.entidades.Producto;
import comcarniceria.servicios.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class InicioController {

    private final ProductoService productoService;

    public InicioController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String incio(Model model) {
        model.addAttribute("productos", productoService.buscarEntidades());
        return "inicio";
    }
}
