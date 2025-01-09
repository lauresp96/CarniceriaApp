package comcarniceria.repositorios;

import comcarniceria.entidades.ProductoEnCesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ProductoEnCestaRepository extends JpaRepository<ProductoEnCesta, Long> {
    Optional<ProductoEnCesta> findByCestaIdAndProductoId(Long cestaId, Long productoId);
}
