package comcarniceria.servicios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public abstract class AbstractService<E, ID> {

    private final JpaRepository<E, ID> repository;


    protected AbstractService(JpaRepository<E, ID> repository) {
        this.repository = repository;
    }

    public List<E> buscarEntidades() {
        return repository.findAll();
    }

    public Set<E> buscarEntidadesSet() {
        Set<E> eSet = new HashSet<E>(this.repository.findAll());
        return eSet;
    }

    public Optional<E> encontrarPorId(ID id) {
        return repository.findById(id);
    }

//    public Page<E> buscarTodas(Pageable pageable) {
//        return repository.findAll(pageable);
//    }

    public E guardar(E entidad) throws Exception {
        return repository.save(entidad);
    }

    public void guardar(List<E> ents) throws Exception {
        Iterator<E> iterator = ents.iterator();
        while (iterator.hasNext()) {
            E e = iterator.next();
            repository.save(e);
        }
    }

    public void eliminarPorId(ID id) {
        repository.deleteById(id);
    }

    public abstract E editar(ID id, E entidadEditada) throws Exception;

}
