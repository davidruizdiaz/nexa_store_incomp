package py.edu.nexa_store.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.edu.nexa_store.entidades.VentaDetalle;

public interface VentaDetalleRepositorio extends JpaRepository<VentaDetalle, Long> {

    @Query("SELECT vd FROM VentaDetalle vd " +
    "LEFT JOIN FETCH vd.venta " +
    "LEFT JOIN FETCH vd.producto " +
    "ORDER BY vd.idVentaDetalle DESC LIMIT 50")
    List<VentaDetalle> listarTodos();

    @Query("SELECT vd FROM VentaDetalle vd " +
    "LEFT JOIN FETCH vd.producto " +
    "WHERE vd.venta.idVenta = :idVenta ORDER BY vd.idVentaDetalle DESC")
    List<VentaDetalle> buscarPorVenta(@Param("idVenta") Long idVenta);

    @Query("SELECT vd FROM VentaDetalle vd " +
    "LEFT JOIN FETCH vd.venta " +
    "LEFT JOIN FETCH vd.producto " +
    "WHERE CAST(vd.unidadesVendidas AS string) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR CAST(vd.valorVendido AS string) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(vd.producto.descripcion) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "OR LOWER(vd.venta.estado) LIKE LOWER(CONCAT('%', :termino, '%')) " +
    "ORDER BY vd.idVentaDetalle DESC LIMIT 50")
    List<VentaDetalle> buscarPorTermino(@Param("termino") String termino);
}
