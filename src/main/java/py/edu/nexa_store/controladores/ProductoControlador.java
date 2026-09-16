package py.edu.nexa_store.controladores;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import py.edu.nexa_store.entidades.Producto;
import py.edu.nexa_store.servicios.ProductoCategoriaServicio;
import py.edu.nexa_store.servicios.ProductoServicio;

// agregar anotaciones
public class ProductoControlador {

    private final ProductoServicio productoServicio;
    private final ProductoCategoriaServicio productoCategoriaServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        // completar
        return "producto/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        // completar
        return "producto/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("producto") Producto producto, BindingResult result, Model model) {
        // completar
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            productoServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/productos";
    }
}
