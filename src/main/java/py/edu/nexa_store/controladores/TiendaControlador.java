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
import py.edu.nexa_store.entidades.Tienda;
import py.edu.nexa_store.servicios.TiendaServicio;

@Controller
@RequestMapping("/tiendas")
@RequiredArgsConstructor
public class TiendaControlador {

    private final TiendaServicio tiendaServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("tienda", new Tienda());
        model.addAttribute("tiendas", (termino != null && !termino.isBlank())
                ? tiendaServicio.buscarPorTermino(termino.trim())
                : tiendaServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "tienda/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tienda", tiendaServicio.buscarPorId(id));
        model.addAttribute("tiendas", tiendaServicio.listarTodos());
        return "tienda/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("tienda") Tienda tienda, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Hay errores en los datos del formulario. Revise los campos de la tienda.");
            model.addAttribute("tiendas", tiendaServicio.listarTodos());
            return "tienda/form";
        }
        try {
            tiendaServicio.guardar(tienda);
            return "redirect:/tiendas";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar la tienda: " + e.getMessage());
            model.addAttribute("tiendas", tiendaServicio.listarTodos());
            return "tienda/form";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            tiendaServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/tiendas";
    }
}
