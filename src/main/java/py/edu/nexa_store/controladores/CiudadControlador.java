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
import py.edu.nexa_store.entidades.Ciudad;
import py.edu.nexa_store.servicios.CiudadServicio;
import py.edu.nexa_store.servicios.PaisServicio;

@Controller
@RequestMapping("/ciudades")
@RequiredArgsConstructor
public class CiudadControlador {

    private final CiudadServicio ciudadServicio;
    private final PaisServicio paisServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("ciudad", new Ciudad());
        model.addAttribute("ciudades", (termino != null && !termino.isBlank())
                ? ciudadServicio.buscarPorTermino(termino.trim())
                : ciudadServicio.listarTodos());
        model.addAttribute("paises", paisServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "ciudad/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Ciudad ciudad = ciudadServicio.buscarPorId(id);
        if (ciudad.getPais() != null) {
            ciudad.setPaisId(ciudad.getPais().getIdPais());
        }
        model.addAttribute("ciudad", ciudad);
        model.addAttribute("ciudades", ciudadServicio.listarTodos());
        model.addAttribute("paises", paisServicio.listarTodos());
        return "ciudad/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("ciudad") Ciudad ciudad, BindingResult result, Model model) {
        if (ciudad.getPaisId() != null) {
            ciudad.setPais(paisServicio.buscarPorId(ciudad.getPaisId()));
        } else {
            ciudad.setPais(null);
        }
        if (result.hasErrors()) {
            model.addAttribute("error", "Hay errores en los datos del formulario. Revise los campos de la ciudad.");
            model.addAttribute("ciudades", ciudadServicio.listarTodos());
            model.addAttribute("paises", paisServicio.listarTodos());
            return "ciudad/form";
        }
        try {
            ciudadServicio.guardar(ciudad);
            return "redirect:/ciudades";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar la ciudad: " + e.getMessage());
            model.addAttribute("ciudades", ciudadServicio.listarTodos());
            model.addAttribute("paises", paisServicio.listarTodos());
            return "ciudad/form";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            ciudadServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/ciudades";
    }
}
