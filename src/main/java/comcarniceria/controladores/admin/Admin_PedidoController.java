package comcarniceria.controladores.admin;

import comcarniceria.servicios.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pedidos")
public class Admin_PedidoController {

    private final PedidoService pedidoService;

    public Admin_PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

}
