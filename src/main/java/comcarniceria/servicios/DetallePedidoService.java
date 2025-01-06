package comcarniceria.servicios;

import comcarniceria.entidades.DetallePedido;
import comcarniceria.repositorios.DetallePedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoService extends AbstractService<DetallePedido, Long> {

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        super(detallePedidoRepository);
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public DetallePedido editar(Long id, DetallePedido entidadEditada) throws Exception {
        return detallePedidoRepository.save(entidadEditada);
    }
}
