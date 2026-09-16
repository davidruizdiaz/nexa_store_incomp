package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.Vendedor;

public interface VendedorRepositorio extends JpaRepository<Vendedor, Long> {

    @Query("SELECT v FROM Vendedor v " +
    "LEFT JOIN FETCH v.tienda " +
    "ORDER BY v.idVendedor DESC LIMIT 50")
    List<Vendedor> listarTodos();

    @Query("SELECT v FROM Vendedor v WHERE v.tienda.idTienda = :idTienda ORDER BY v.idVendedor DESC")
    List<Vendedor> buscarPorTienda(@Param("idTienda") Long idTienda);

    @Query("SELECT v FROM Vendedor v " +
    "LEFT JOIN FETCH v.tienda " +
    "WHERE LOWER(v.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(v.apellido) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR CAST(v.edad AS string) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(v.sexo) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY v.idVendedor DESC LIMIT 50")
    List<Vendedor> buscarPorTermino(@Param("termino") String termino);
}
