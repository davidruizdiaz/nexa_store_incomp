package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.ProductoFamilia;

public interface ProductoFamiliaRepositorio extends JpaRepository<ProductoFamilia, Long> {

    // agregar consulta para listarTodos
    List<ProductoFamilia> listarTodos();

    @Query("SELECT f FROM ProductoFamilia f " +
    "WHERE LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY f.idProductoFamilia DESC LIMIT 50")
    List<ProductoFamilia> buscarPorTermino(@Param("termino") String termino);
}
