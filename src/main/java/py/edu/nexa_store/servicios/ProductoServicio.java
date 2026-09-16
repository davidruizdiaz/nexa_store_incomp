package py.edu.nexa_store.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.Producto;
import py.edu.nexa_store.excepciones.RecursoNoEncontradoException;
import py.edu.nexa_store.repositorios.ProductoRepositorio;

@Service
@RequiredArgsConstructor
public class ProductoServicio {

    private final ProductoRepositorio productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.listarTodos();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el producto con id " + id));
    }

    @Transactional
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el producto con id " + id);
        }
        productoRepository.deleteById(id);
    }

    public List<Producto> buscarPorTermino(String termino) {
        return productoRepository.buscarPorTermino(termino);
    }
}
