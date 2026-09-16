package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.EstadoVenta;
import py.edu.nexa_store.entidades.Venta;

public interface VentaRepositorio extends JpaRepository<Venta, Long> {

    @Query("SELECT v FROM Venta v " +
    "LEFT JOIN FETCH v.tienda " +
    "LEFT JOIN FETCH v.cliente " +
    "LEFT JOIN FETCH v.vendedor " +
    "ORDER BY v.idVenta DESC LIMIT 50")
    List<Venta> listarTodos();

    @Query("SELECT v FROM Venta v WHERE v.estado = :estado ORDER BY v.idVenta DESC")
    List<Venta> buscarPorEstado(@Param("estado") EstadoVenta estado);

    @Query("SELECT v FROM Venta v WHERE v.tienda.idTienda = :idTienda ORDER BY v.idVenta DESC")
    List<Venta> buscarPorTienda(@Param("idTienda") Long idTienda);

    @Query("SELECT v FROM Venta v " +
    "LEFT JOIN FETCH v.tienda " +
    "LEFT JOIN FETCH v.cliente " +
    "LEFT JOIN FETCH v.vendedor " +
    "WHERE CAST(v.fechaVenta AS string) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(v.estado) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(v.cliente.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(v.vendedor.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(v.vendedor.apellido) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY v.idVenta DESC LIMIT 50")
    List<Venta> buscarPorTermino(@Param("termino") String termino);
}
