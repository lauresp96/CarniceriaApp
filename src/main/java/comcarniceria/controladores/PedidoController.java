package comcarniceria.controladores;

import comcarniceria.entidades.Cesta;
import comcarniceria.entidades.Pedido;
import comcarniceria.entidades.Producto;
import comcarniceria.servicios.CestaService;
import comcarniceria.servicios.PedidoService;
import comcarniceria.servicios.ProductoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
   private final CestaService cestaService;

    public PedidoController(PedidoService pedidoService, CestaService cestaService) {
        this.pedidoService = pedidoService;
        this.cestaService = cestaService;
    }

}
