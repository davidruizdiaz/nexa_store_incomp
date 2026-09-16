package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.ProductoDepartamento;

public interface ProductoDepartamentoRepositorio extends JpaRepository<ProductoDepartamento, Long> {

    @Query("SELECT d FROM ProductoDepartamento d " +
    "LEFT JOIN FETCH d.productoFamilia " +
    "ORDER BY d.idProductoDepartamento DESC LIMIT 50")
    List<ProductoDepartamento> listarTodos();

    @Query("SELECT d FROM ProductoDepartamento d " +
    "LEFT JOIN FETCH d.productoFamilia " +
    "WHERE LOWER(d.descripcion) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY d.idProductoDepartamento DESC LIMIT 50")
    List<ProductoDepartamento> buscarPorTermino(@Param("termino") String termino);
}
