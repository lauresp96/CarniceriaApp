package comcarniceria.controladores;

import comcarniceria.servicios.CategoriaService;
import comcarniceria.servicios.ImagenService;
import comcarniceria.servicios.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductosController {

    private final ProductoService productoService;
    private final ImagenService imagenService;
    private final CategoriaService categoriaService;

    public ProductosController(ProductoService productoService, ImagenService imagenService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.imagenService = imagenService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String mostrarProductos(Model model) {
        model.addAttribute("productos", productoService.buscarEntidades());
        return "productos";
    }
}
