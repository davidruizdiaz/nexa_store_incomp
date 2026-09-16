package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.ProductoCategoria;

public interface ProductoCategoriaRepositorio extends JpaRepository<ProductoCategoria, Long> {

    @Query("SELECT c FROM ProductoCategoria c " +
    "LEFT JOIN FETCH c.productoDepartamento " +
    "ORDER BY c.idProductoCategoria DESC LIMIT 50")
    List<ProductoCategoria> listarTodos();

    @Query("SELECT c FROM ProductoCategoria c " +
    "LEFT JOIN FETCH c.productoDepartamento " +
    "WHERE LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY c.idProductoCategoria DESC LIMIT 50")
    List<ProductoCategoria> buscarPorTermino(@Param("termino") String termino);
}
