package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.Tienda;

public interface TiendaRepositorio extends JpaRepository<Tienda, Long> {

    @Query("SELECT t FROM Tienda t ORDER BY t.idTienda DESC LIMIT 50")
    List<Tienda> listarTodos();

    @Query("SELECT t FROM Tienda t " +
    "WHERE LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY t.idTienda DESC LIMIT 50")
    List<Tienda> buscarPorTermino(@Param("termino") String termino);
}
