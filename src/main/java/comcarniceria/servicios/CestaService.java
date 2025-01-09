package comcarniceria.servicios;

import comcarniceria.entidades.Cesta;
import comcarniceria.entidades.Producto;
import comcarniceria.entidades.ProductoEnCesta;
import comcarniceria.entidades.Usuario;
import comcarniceria.repositorios.CestaRepository;
import comcarniceria.repositorios.ProductoEnCestaRepository;
import comcarniceria.repositorios.ProductoRepository;
import comcarniceria.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CestaService extends AbstractService<Cesta, Long> {

    private final CestaRepository cestaRepository;
    private final ProductoRepository productoRepository;
    private final ProductoEnCestaRepository productoEnCestaRepository;
    private final UsuarioRepository usuarioRepository;

    protected CestaService(JpaRepository<Cesta, Long> repository, CestaRepository cestaRepository, ProductoRepository productoRepository, ProductoEnCestaRepository productoEnCestaRepository, UsuarioRepository usuarioRepository) {
        super(repository);
        this.cestaRepository = cestaRepository;
        this.productoRepository = productoRepository;
        this.productoEnCestaRepository = productoEnCestaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Cesta editar(Long id, Cesta entidadEditada) throws Exception {
        return cestaRepository.save(entidadEditada);
    }

    public List<Cesta> findByUsuario(Usuario usuario) {
        return cestaRepository.findByUsuario(usuario);
    }

    @Transactional
    public void agregarProductoACesta(Long id, Long productoId, Integer cantidad) {
        Cesta cesta = cestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Optional<ProductoEnCesta> productoEnCestaOptional = cesta.getProductosEnCesta().stream()
                .filter(pc -> pc.getProducto().getId().equals(productoId))
                .findFirst();
        if (productoEnCestaOptional.isPresent()) {
            ProductoEnCesta productoEnCesta = productoEnCestaOptional.get();
            int nuevaCantidad = productoEnCesta.getCantidad() + cantidad;
            if (nuevaCantidad > 0) {
                productoEnCesta.setCantidad(nuevaCantidad);
            } else {
                cesta.getProductosEnCesta().remove(productoEnCesta);
                productoEnCestaRepository.delete(productoEnCesta);
            }
        } else {
            if (cantidad > 0) {
                ProductoEnCesta productoEnCesta = new ProductoEnCesta();
                productoEnCesta.setCesta(cesta);
                productoEnCesta.setProducto(producto);
                productoEnCesta.setCantidad(cantidad);
                cesta.getProductosEnCesta().add(productoEnCesta);
            } else {
                throw new RuntimeException("La cantidad debe ser mayor que cero para agregar un producto a la cesta.");
            }
            cestaRepository.save(cesta);
        }
    }

    @Transactional
    public void eliminarProductoDeCesta(Long cestaId, Long productoId) {
        Cesta cesta = cestaRepository.findById(cestaId)
                .orElseThrow(() -> new EntityNotFoundException("Cesta no encontrada"));

        ProductoEnCesta productoEnCesta = productoEnCestaRepository.findByCestaIdAndProductoId(cestaId, productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en la cesta"));

        cesta.getProductosEnCesta().remove(productoEnCesta);
        productoEnCestaRepository.delete(productoEnCesta);

        cestaRepository.save(cesta);
    }


    @Transactional
    public void actualizarCantidadProductoEnCesta(Long cestaId, Long productoId, Integer cantidad) {
        Cesta cesta = encontrarPorId(cestaId)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        ProductoEnCesta productoEnCesta = cesta.getProductosEnCesta().stream()
                .filter(pc -> pc.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en la cesta"));

        if (cantidad <= 0) {
            // Eliminar el producto si la cantidad es cero o negativa
            cesta.getProductosEnCesta().remove(productoEnCesta);
            productoRepository.delete(productoEnCesta.getProducto());
        } else {
            // Actualizar la cantidad del producto en la cesta
            productoEnCesta.setCantidad(cantidad);
        }
        cestaRepository.save(cesta);
    }

}



