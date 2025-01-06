package comcarniceria.servicios;

import comcarniceria.entidades.Pedido;
import comcarniceria.repositorios.PedidoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoService extends AbstractService<Pedido, Long> {

    private final PedidoRepository pedidoRepository;

    protected PedidoService(JpaRepository<Pedido, Long> repository, PedidoRepository pedidoRepository) {
        super(repository);
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido editar(Long id, Pedido entidadEditada) throws Exception {
        return pedidoRepository.save(entidadEditada);
    }
}
