package py.edu.nexa_store.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.ProductoDepartamento;
import py.edu.nexa_store.excepciones.RecursoNoEncontradoException;
import py.edu.nexa_store.repositorios.ProductoDepartamentoRepositorio;

@Service
@RequiredArgsConstructor
public class ProductoDepartamentoServicio {

    private final ProductoDepartamentoRepositorio productoDepartamentoRepository;

    public List<ProductoDepartamento> listarTodos() {
        return productoDepartamentoRepository.listarTodos();
    }

    public ProductoDepartamento buscarPorId(Long id) {
        return productoDepartamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el departamento de producto con id " + id));
    }

    @Transactional
    public ProductoDepartamento guardar(ProductoDepartamento productoDepartamento) {
        return productoDepartamentoRepository.save(productoDepartamento);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoDepartamentoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el departamento de producto con id " + id);
        }
        productoDepartamentoRepository.deleteById(id);
    }

    public List<ProductoDepartamento> buscarPorTermino(String termino) {
        return productoDepartamentoRepository.buscarPorTermino(termino);
    }
}
