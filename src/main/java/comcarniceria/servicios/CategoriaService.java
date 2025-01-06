package comcarniceria.servicios;

import comcarniceria.entidades.Categoria;
import comcarniceria.repositorios.CategoriaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends AbstractService<Categoria, Long> {

    private final CategoriaRepository categoriaRepository;

    protected CategoriaService(JpaRepository<Categoria, Long> repository, CategoriaRepository categoriaRepository) {
        super(repository);
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria editar(Long id, Categoria entidadEditada) throws Exception {
        return categoriaRepository.save(entidadEditada);
    }
}
