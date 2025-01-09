package comcarniceria.servicios;


import comcarniceria.entidades.Pedido;
import comcarniceria.repositorios.CestaRepository;
import comcarniceria.repositorios.PedidoRepository;
import org.springframework.stereotype.Service;



@Service
public class PedidoService extends AbstractService<Pedido, Long> {

    private final PedidoRepository pedidoRepository;
    private final CestaRepository cestaRepository;

    public PedidoService(PedidoRepository pedidoRepository, CestaRepository cestaRepository) {
        super(pedidoRepository);
        this.pedidoRepository = pedidoRepository;
        this.cestaRepository = cestaRepository;
    }

    @Override
    public Pedido editar(Long id, Pedido entidadEditada) throws Exception {
        return pedidoRepository.save(entidadEditada);
    }



}

