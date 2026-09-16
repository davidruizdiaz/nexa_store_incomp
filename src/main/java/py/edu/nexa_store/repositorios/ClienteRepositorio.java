package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c " +
    "LEFT JOIN FETCH c.ciudad " +
    "ORDER BY c.idCliente DESC LIMIT 50")
    List<Cliente> listarTodos();

    @Query("SELECT c FROM Cliente c " +
    "LEFT JOIN FETCH c.ciudad " +
    "WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(c.direccion) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    " ORDER BY c.idCliente DESC LIMIT 50")
    List<Cliente> buscarPorTermino(@Param("termino") String termino);
}
