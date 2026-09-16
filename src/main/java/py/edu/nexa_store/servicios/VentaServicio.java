package py.edu.nexa_store.servicios;

import java.util.List;

import py.edu.nexa_store.entidades.EstadoVenta;
import py.edu.nexa_store.entidades.Venta;

// agregar anotaciones
public class VentaServicio {

   // completar el servicio

    public List<Venta> listarTodos() {
    }

    public Venta buscarPorId(Long id) {
    }

    @Transactional
    public Venta guardar(Venta venta) {
    }

    @Transactional
    public void cancelar(Long id) {
    }

    public List<Venta> listarPorEstado(EstadoVenta estado) {
    }

    public List<Venta> listarPorTienda(Long idTienda) {
    }

    public List<Venta> buscarPorTermino(String termino) {
    }
}
