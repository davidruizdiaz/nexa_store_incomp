package py.edu.nexa_store.controladores;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import py.edu.nexa_store.entidades.Pais;
import py.edu.nexa_store.servicios.PaisServicio;

// agregar anotaciones
public class PaisControlador {

    private final PaisServicio paisServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        // completar
        return "pais/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        // completar
        return "pais/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("pais") Pais pais, BindingResult result, Model model) {
        // completar
        return "";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            paisServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/paises";
    }
}
