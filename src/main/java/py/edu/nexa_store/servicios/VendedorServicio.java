package py.edu.nexa_store.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.Vendedor;
import py.edu.nexa_store.excepciones.RecursoNoEncontradoException;
import py.edu.nexa_store.repositorios.VendedorRepositorio;

@Service
@RequiredArgsConstructor
public class VendedorServicio {

    private final VendedorRepositorio vendedorRepository;

    public List<Vendedor> listarTodos() {
        return vendedorRepository.listarTodos();
    }

    public Vendedor buscarPorId(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el vendedor con id " + id));
    }

    @Transactional
    public Vendedor guardar(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!vendedorRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el vendedor con id " + id);
        }
        vendedorRepository.deleteById(id);
    }

    public List<Vendedor> listarPorTienda(Long idTienda) {
        return vendedorRepository.buscarPorTienda(idTienda);
    }

    public List<Vendedor> buscarPorTermino(String termino) {
        return vendedorRepository.buscarPorTermino(termino);
    }
}
