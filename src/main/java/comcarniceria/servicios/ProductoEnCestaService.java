package comcarniceria.servicios;

import comcarniceria.entidades.Cesta;
import comcarniceria.entidades.ProductoEnCesta;
import comcarniceria.entidades.Usuario;
import comcarniceria.repositorios.ProductoEnCestaRepository;
import comcarniceria.repositorios.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoEnCestaService extends AbstractService<ProductoEnCesta, Long> {

    private final ProductoEnCestaRepository productoEnCestaRepository;
    private final ProductoRepository productoRepository;

    public ProductoEnCestaService(ProductoEnCestaRepository productoEnCestaRepository, ProductoRepository productoRepository) {
        super(productoEnCestaRepository);
        this.productoEnCestaRepository = productoEnCestaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductoEnCesta editar(Long id, ProductoEnCesta entidadEditada) throws Exception {
        return productoEnCestaRepository.save(entidadEditada);
    }
}
