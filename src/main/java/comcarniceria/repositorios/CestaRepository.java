package comcarniceria.repositorios;

import comcarniceria.entidades.Cesta;
import comcarniceria.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CestaRepository extends JpaRepository<Cesta, Long> {
    List<Cesta> findByUsuario(Usuario usuario);

    Optional<Usuario> findByUsuarioId(Long usuarioId);
}
