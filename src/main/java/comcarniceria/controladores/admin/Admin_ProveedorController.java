package comcarniceria.controladores.admin;

import comcarniceria.entidades.Proveedor;
import comcarniceria.servicios.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/proveedores")
public class Admin_ProveedorController {

    private final ProveedorService proveedorService;

    public Admin_ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String mostrarTodosProveedores(Model model) {
        List<Proveedor> proveedores = proveedorService.buscarEntidades();
        model.addAttribute("proveedores", proveedores);
        return "admin/lista-proveedores";
    }

    @GetMapping("/crear")
    public String formularioCreacionProveedor(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "admin/proveedor-form";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor) throws Exception {
        Proveedor proveedorGuardado = proveedorService.guardar(proveedor);
        return "redirect:/admin/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String formularioEdicionProveedor(@PathVariable Long id, Model model) throws Exception {
        Proveedor proveedor = proveedorService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        if (proveedor != null) {
            model.addAttribute("proveedor", proveedor);
        }
        return "admin/proveedor-form";
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicionProveedor(@PathVariable Long id, @ModelAttribute Proveedor proveedor) throws Exception {
        proveedor.setId(id);
        Proveedor proveedorEditado = proveedorService.editar(id, proveedor);
        return "redirect:/admin/proveedores";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarPorId(id);
        return "redirect:/admin/proveedores";

    }
}
