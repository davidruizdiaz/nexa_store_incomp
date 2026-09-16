package py.edu.nexa_store.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.Pais;
import py.edu.nexa_store.excepciones.RecursoNoEncontradoException;
import py.edu.nexa_store.repositorios.PaisRepositorio;

@Service
@RequiredArgsConstructor
public class PaisServicio {

    private final PaisRepositorio paisRepository;

    public List<Pais> listarTodos() {
        return paisRepository.listarTodos();
    }

    public Pais buscarPorId(Long id) {
        return paisRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el país con id " + id));
    }

    @Transactional
    public Pais guardar(Pais pais) {
        return paisRepository.save(pais);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!paisRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el país con id " + id);
        }
        paisRepository.deleteById(id);
    }

    public List<Pais> buscarPorTermino(String termino) {
        return paisRepository.buscarPorTermino(termino);
    }
}
