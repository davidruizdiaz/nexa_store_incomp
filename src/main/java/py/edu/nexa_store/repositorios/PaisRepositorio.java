package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.Pais;

public interface PaisRepositorio extends JpaRepository<Pais, Long> {

    @Query("SELECT p FROM Pais p ORDER BY p.idPais DESC LIMIT 50")
    List<Pais> listarTodos();

    // agregar consulta para búsqueda por termino
    List<Pais> buscarPorTermino(@Param("termino") String termino);
}
