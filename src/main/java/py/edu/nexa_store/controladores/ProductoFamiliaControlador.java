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
import py.edu.nexa_store.entidades.ProductoFamilia;
import py.edu.nexa_store.servicios.ProductoFamiliaServicio;

@Controller
@RequestMapping("/producto-familias")
@RequiredArgsConstructor
public class ProductoFamiliaControlador {

    private final ProductoFamiliaServicio productoFamiliaServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("productoFamilia", new ProductoFamilia());
        model.addAttribute("productoFamilias", (termino != null && !termino.isBlank())
                ? productoFamiliaServicio.buscarPorTermino(termino.trim())
                : productoFamiliaServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "producto-familia/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("productoFamilia", productoFamiliaServicio.buscarPorId(id));
        model.addAttribute("productoFamilias", productoFamiliaServicio.listarTodos());
        return "producto-familia/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("productoFamilia") ProductoFamilia productoFamilia, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Hay errores en los datos del formulario. Revise los campos de la familia de producto.");
            model.addAttribute("productoFamilias", productoFamiliaServicio.listarTodos());
            return "producto-familia/form";
        }
        try {
            productoFamiliaServicio.guardar(productoFamilia);
            return "redirect:/producto-familias";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar la familia de producto: " + e.getMessage());
            model.addAttribute("productoFamilias", productoFamiliaServicio.listarTodos());
            return "producto-familia/form";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            productoFamiliaServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/producto-familias";
    }
}
