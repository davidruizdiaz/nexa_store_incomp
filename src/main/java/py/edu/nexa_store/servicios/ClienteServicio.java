package py.edu.nexa_store.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.Cliente;
import py.edu.nexa_store.excepciones.RecursoNoEncontradoException;
import py.edu.nexa_store.repositorios.ClienteRepositorio;

@Service
@RequiredArgsConstructor
public class ClienteServicio {

    private final ClienteRepositorio clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el cliente con id " + id));
    }

    @Transactional
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el cliente con id " + id);
        }
        clienteRepository.deleteById(id);
    }

    public List<Cliente> buscarPorTermino(String termino) {
        return clienteRepository.buscarPorTermino(termino);
    }
}
