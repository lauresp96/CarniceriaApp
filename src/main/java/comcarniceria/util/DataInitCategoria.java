package comcarniceria.util;

import comcarniceria.entidades.Categoria;
import comcarniceria.servicios.CategoriaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitCategoria implements CommandLineRunner {

    private final CategoriaService categoriaService;

    public DataInitCategoria(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoriaService.buscarEntidades().isEmpty()) {
            // Crear categorías predefinidas
            Categoria categoria1 = new Categoria();
            categoria1.setNombre("Carnes de Res");
            categoria1.setDescripcion("Cortes frescos de carne de res.");
            categoriaService.guardar(categoria1);

            Categoria categoria2 = new Categoria();
            categoria2.setNombre("Carnes de Cerdo");
            categoria2.setDescripcion("Cortes frescos de carne de cerdo.");
            categoriaService.guardar(categoria2);

            Categoria categoria3 = new Categoria();
            categoria3.setNombre("Aves");
            categoria3.setDescripcion("Pollo y otros cortes de aves.");
            categoriaService.guardar(categoria3);

            Categoria categoria4 = new Categoria();
            categoria4.setNombre("Embutidos");
            categoria4.setDescripcion("Embutidos y salchichones.");
            categoriaService.guardar(categoria4);


            Categoria categoria5 = new Categoria();
            categoria4.setNombre("Ternera");
            categoria4.setDescripcion("Carne de ternera.");
            categoriaService.guardar(categoria4);
        }
    }
}
