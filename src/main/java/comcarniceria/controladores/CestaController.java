package comcarniceria.controladores;

import comcarniceria.entidades.Cesta;
import comcarniceria.entidades.Producto;
import comcarniceria.entidades.ProductoEnCesta;
import comcarniceria.servicios.CestaService;
import comcarniceria.servicios.ProductoService;
import comcarniceria.servicios.UsuarioService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cesta")
public class CestaController {

    private final CestaService cestaService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;


    public CestaController(CestaService cestaService, UsuarioService usuarioService, ProductoService productoService) {
        this.cestaService = cestaService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }


    @GetMapping("/crear")
    public String formularioCreacionCesta(Model model) {
        model.addAttribute("cesta", new Cesta());
        return "cesta-form";
    }

    @PostMapping("/guardar")
    public String guardarCesta(@ModelAttribute Cesta cesta,
                               @RequestParam(value = "productoId") Long productoId,
                               @RequestParam Integer cantidad) throws Exception {
        // Depuración: Imprime el valor de productoId recibido
        System.out.println("Producto ID recibido: " + productoId);

        cesta.setFecha(LocalDateTime.now());
        cestaService.guardar(cesta);

        // Luego asociamos el producto a la cesta, para modal
        Producto producto = productoService.encontrarPorId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Agregar el producto a la cesta
        cestaService.agregarProductoACesta(cesta.getId(), productoId, cantidad);

        return "redirect:/productos?agregarACesta=true&cestaId=" + cesta.getId();
    }


    @GetMapping("/listar")
    public String listarCestas(Model model) {
        // Verificamos si el usuario tiene cestas creadas
        List<Cesta> cestas = cestaService.buscarEntidades();

        if (cestas.isEmpty()) {
            model.addAttribute("mensaje", "No tienes ninguna cesta creada. ¡Crea una nueva!");
        } else {
            model.addAttribute("cestas", cestas);
        }
        return "cesta-lista";
    }

    @GetMapping("/{id}")
    public String obtenerCestaPorId(@PathVariable Long id, Model model) throws Throwable {
        Cesta cesta = cestaService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        // Metodo para Calcular total de la cesta:
        model.addAttribute("cesta", cesta);

        return "cesta";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) throws Throwable {
        Cesta cesta = (Cesta) cestaService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        model.addAttribute("cesta", cesta);
        return "cesta-form";
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicionCesta(@PathVariable Long id,
                                      @ModelAttribute Cesta cesta) throws Throwable {
        Cesta cestaExistente = cestaService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        cestaService.guardar(cestaExistente);
        return "redirect:/cesta";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCesta(@PathVariable Long id) {
        Cesta cestaExistente = cestaService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        cestaService.eliminarPorId(id);
        return "redirect:/productos";
    }
}
