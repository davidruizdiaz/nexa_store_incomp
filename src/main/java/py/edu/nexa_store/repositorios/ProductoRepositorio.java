package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p " +
    "LEFT JOIN FETCH p.productoCategoria " +
    "ORDER BY p.idProducto DESC LIMIT 50")
    List<Producto> listarTodos();

    @Query("SELECT p FROM Producto p " +
    "LEFT JOIN FETCH p.productoCategoria " +
    "WHERE LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR CAST(p.precioCosto AS string) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY p.idProducto DESC LIMIT 50")
    List<Producto> buscarPorTermino(@Param("termino") String termino);
}
