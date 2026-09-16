package py.edu.nexa_store.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.ProductoCategoria;
import py.edu.nexa_store.excepciones.RecursoNoEncontradoException;
import py.edu.nexa_store.repositorios.ProductoCategoriaRepositorio;

@Service
@RequiredArgsConstructor
public class ProductoCategoriaServicio {

    private final ProductoCategoriaRepositorio productoCategoriaRepository;

    public List<ProductoCategoria> listarTodos() {
        return productoCategoriaRepository.listarTodos();
    }

    public ProductoCategoria buscarPorId(Long id) {
        return productoCategoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la categoría de producto con id " + id));
    }

    @Transactional
    public ProductoCategoria guardar(ProductoCategoria productoCategoria) {
        return productoCategoriaRepository.save(productoCategoria);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoCategoriaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró la categoría de producto con id " + id);
        }
        productoCategoriaRepository.deleteById(id);
    }

    public List<ProductoCategoria> buscarPorTermino(String termino) {
        return productoCategoriaRepository.buscarPorTermino(termino);
    }
}
