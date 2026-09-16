package py.edu.nexa_store.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.ProductoFamilia;
import py.edu.nexa_store.excepciones.RecursoNoEncontradoException;
import py.edu.nexa_store.repositorios.ProductoFamiliaRepositorio;

@Service
@RequiredArgsConstructor
public class ProductoFamiliaServicio {

    private final ProductoFamiliaRepositorio productoFamiliaRepository;

    public List<ProductoFamilia> listarTodos() {
        return productoFamiliaRepository.listarTodos();
    }

    public ProductoFamilia buscarPorId(Long id) {
        return productoFamiliaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la familia de producto con id " + id));
    }

    @Transactional
    public ProductoFamilia guardar(ProductoFamilia productoFamilia) {
        return productoFamiliaRepository.save(productoFamilia);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoFamiliaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró la familia de producto con id " + id);
        }
        productoFamiliaRepository.deleteById(id);
    }

    public List<ProductoFamilia> buscarPorTermino(String termino) {
        return productoFamiliaRepository.buscarPorTermino(termino);
    }
}
