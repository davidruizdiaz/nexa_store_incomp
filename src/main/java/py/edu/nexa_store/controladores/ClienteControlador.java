package py.edu.nexa_store.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.entidades.Cliente;
import py.edu.nexa_store.servicios.CiudadServicio;
import py.edu.nexa_store.servicios.ClienteServicio;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteControlador {

    private final ClienteServicio clienteServicio;
    private final CiudadServicio ciudadServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("clientes", (termino != null && !termino.isBlank())
                ? clienteServicio.buscarPorTermino(termino.trim())
                : clienteServicio.listarTodos());
        model.addAttribute("ciudades", ciudadServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "cliente/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteServicio.buscarPorId(id);
        if (cliente.getCiudad() != null) {
            cliente.setCiudadId(cliente.getCiudad().getIdCiudad());
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clienteServicio.listarTodos());
        model.addAttribute("ciudades", ciudadServicio.listarTodos());
        return "cliente/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model) {
        if (cliente.getCiudadId() != null) {
            cliente.setCiudad(ciudadServicio.buscarPorId(cliente.getCiudadId()));
        } else {
            cliente.setCiudad(null);
        }
        if (result.hasErrors()) {
            model.addAttribute("error", "Hay errores en los datos del formulario. Revise los campos del cliente.");
            model.addAttribute("clientes", clienteServicio.listarTodos());
            model.addAttribute("ciudades", ciudadServicio.listarTodos());
            return "cliente/form";
        }
        try {
            clienteServicio.guardar(cliente);
            return "redirect:/clientes";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar el cliente: " + e.getMessage());
            model.addAttribute("clientes", clienteServicio.listarTodos());
            model.addAttribute("ciudades", ciudadServicio.listarTodos());
            return "cliente/form";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            clienteServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/clientes";
    }
}
