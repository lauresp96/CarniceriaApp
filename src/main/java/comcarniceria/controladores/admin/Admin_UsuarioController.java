package comcarniceria.controladores.admin;

import comcarniceria.entidades.Usuario;
import comcarniceria.servicios.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class Admin_UsuarioController {

    private final UsuarioService usuarioService;

    public Admin_UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String mostrarTodos(Model model) {
        List<Usuario> usuarios = usuarioService.buscarEntidades();
        model.addAttribute("usuarios", usuarios);
        return "admin/lista-usuarios";
    }

    @GetMapping("/crear")
    public String formularioCreacionUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario-form";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) throws Exception {
        Usuario usuarioGuardado = usuarioService.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String formularioEdicionUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
        }
        return "admin/usuario-form";
    }

    @PostMapping("/editar{id}")
    public String guardarEdicionUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario) throws Exception {
        usuario.setId(id);
        Usuario usuarioEditado = usuarioService.editar(id, usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarPorId(id);
        return "redirect:/admin/usuarios";
    }

}
