package comcarniceria.controladores;


import comcarniceria.entidades.Pago;
import comcarniceria.entidades.Pedido;
import comcarniceria.servicios.PagoService;
import comcarniceria.servicios.PedidoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/Pago")
public class PagoController {

    private final PagoService pagoService;
    private final PedidoService pedidoService;

    public PagoController(PagoService pagoService, PedidoService pedidoService) {
        this.pagoService = pagoService;
        this.pedidoService = pedidoService;
    }

}
