package comcarniceria.servicios;

import comcarniceria.entidades.Proveedor;
import comcarniceria.repositorios.ProveedorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService extends AbstractService<Proveedor, Long> {

    private final ProveedorRepository proveedorRepository;

    protected ProveedorService(JpaRepository<Proveedor, Long> repository, ProveedorRepository proveedorRepository) {
        super(repository);
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public Proveedor editar(Long id, Proveedor entidadEditada) throws Exception {
        return proveedorRepository.save(entidadEditada);
    }
}
