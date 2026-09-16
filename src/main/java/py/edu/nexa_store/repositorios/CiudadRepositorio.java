package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.Ciudad;

public interface CiudadRepositorio extends JpaRepository<Ciudad, Long> {

    @Query("SELECT c FROM Ciudad c " +
    "LEFT JOIN FETCH c.pais " +
    "ORDER BY c.idCiudad DESC LIMIT 50")
    List<Ciudad> listarTodos();

    @Query("SELECT c FROM Ciudad c " +
    "LEFT JOIN FETCH c.pais " +
    "WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(c.departamento) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY c.idCiudad DESC LIMIT 50")
    List<Ciudad> buscarPorTermino(@Param("termino") String termino);
}
