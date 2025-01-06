package comcarniceria.servicios;

import comcarniceria.entidades.Producto;
import comcarniceria.repositorios.ProductoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService extends AbstractService<Producto, Long> {

    private final ProductoRepository productoRepository;
    protected ProductoService(JpaRepository<Producto, Long> repository, ProductoRepository productoRepository) {
        super(repository);
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto editar(Long id, Producto entidadEditada) throws Exception {
        return productoRepository.save(entidadEditada);
    }
}
