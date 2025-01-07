package comcarniceria.entidades;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(Long productoId) {
        productos.removeIf(producto -> producto.getId().equals(productoId));
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double calcularTotal() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
