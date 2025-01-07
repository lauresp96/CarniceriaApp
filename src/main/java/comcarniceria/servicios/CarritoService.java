package comcarniceria.servicios;


import comcarniceria.entidades.Carrito;
import comcarniceria.entidades.Producto;
import comcarniceria.repositorios.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    private final ProductoRepository productoRepository;

    public CarritoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public void agregarProductoAlCarrito(Long productoId, Carrito carrito) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        carrito.agregarProducto(producto);
    }

    // Eliminar producto del carrito
    public void eliminarProductoDelCarrito(Long productoId, Carrito carrito) {
        carrito.eliminarProducto(productoId);
    }
}
