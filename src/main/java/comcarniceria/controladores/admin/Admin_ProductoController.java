package comcarniceria.controladores.admin;

import comcarniceria.entidades.Categoria;
import comcarniceria.entidades.Producto;
import comcarniceria.entidades.Proveedor;
import comcarniceria.servicios.CategoriaService;
import comcarniceria.servicios.ImagenService;
import comcarniceria.servicios.ProductoService;
import comcarniceria.servicios.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/productos")
public class Admin_ProductoController {

    private final ProductoService productoService;
    private final ImagenService imagenService;
    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;

    public Admin_ProductoController(ProductoService productoService, ImagenService imagenService, CategoriaService categoriaService, ProveedorService proveedorService) {
        this.productoService = productoService;
        this.imagenService = imagenService;
        this.categoriaService = categoriaService;
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String mostrarTodos(Model model) {
        List<Producto> productos = productoService.buscarEntidades();
        model.addAttribute("productos", productos);
        return "admin/lista-productos";
    }

    @GetMapping("/crear")
    public String formularioCreacionProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.buscarEntidades());
        model.addAttribute("proveedores", proveedorService.buscarEntidades());
        return "admin/producto-form";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@RequestParam("nombre") String nombre,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam("precio") double precio,
                                  @RequestParam("cantiadStock") int cantiadStock,
                                  @RequestParam("categoria.id") Long categoriaId,
                                  @RequestParam("proveedor.id") Long proveedorId,
                                  @RequestParam("imagen") MultipartFile imagen) throws Exception { // al poner imagen no puedo usar @ModelAttribute, tengo que usar @RequestParam.

        try {
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCantiadStock(cantiadStock);
            Optional<Categoria> categoria = categoriaService.encontrarPorId(categoriaId);
            producto.setCategoria(categoria.get());
            Optional<Proveedor> proveedor = proveedorService.encontrarPorId(proveedorId);
            producto.setProveedor(proveedor.get());


            if (!imagen.isEmpty()) {
                String imagenRuta = imagenService.guardarImagen(imagen);
                producto.setImagen(imagenRuta);
            }
            productoService.guardar(producto);

            return "redirect:/admin/productos";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String formularioEdicionProducto(@PathVariable Long id, Model model) throws Exception {
        Producto productoEditado = productoService.encontrarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        model.addAttribute("producto", productoEditado);
        model.addAttribute("categorias", categoriaService.buscarEntidades());
        model.addAttribute("proveedores", proveedorService.buscarEntidades());

        return "admin/producto-form";
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicionProducto(@PathVariable Long id,
                                         @RequestParam("nombre") String nombre,
                                         @RequestParam("descripcion") String descripcion,
                                         @RequestParam("precio") double precio,
                                         @RequestParam("cantiadStock") int cantiadStock,
                                         @RequestParam("categoria.id") Long categoriaId,
                                         @RequestParam("proveedor.id") Long proveedorId,
                                         @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws Exception {
        try {
            Producto productoEditado = productoService.encontrarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


            productoEditado.setNombre(nombre);
            productoEditado.setDescripcion(descripcion);
            productoEditado.setPrecio(precio);
            productoEditado.setCantiadStock(cantiadStock);
            Optional<Categoria> categoria = categoriaService.encontrarPorId(categoriaId);
            productoEditado.setCategoria(categoria.get());
            Optional<Proveedor> proveedor = proveedorService.encontrarPorId(proveedorId);
            productoEditado.setProveedor(proveedor.get());

            if (imagen != null && !imagen.isEmpty()) {
                String imagenRuta = imagenService.guardarImagen(imagen);
                productoEditado.setImagen(imagenRuta);
            }
            productoService.editar(id, productoEditado);
            return "redirect:/admin/productos";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            Producto producto = productoService.encontrarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Eliminar la imagen del sistema de archivos si existe
            if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
                imagenService.eliminarImagen(producto.getImagen());
            }
            if (producto.getCategoria() != null && producto.getCategoria().getProductos().isEmpty()) {
                categoriaService.eliminarPorId(id);
            }
            productoService.eliminarPorId(id);
            return "redirect:/admin/productos";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
