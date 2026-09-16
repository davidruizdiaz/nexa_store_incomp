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
import py.edu.nexa_store.entidades.ProductoCategoria;
import py.edu.nexa_store.servicios.ProductoCategoriaServicio;
import py.edu.nexa_store.servicios.ProductoDepartamentoServicio;

@Controller
@RequestMapping("/producto-categorias")
@RequiredArgsConstructor
public class ProductoCategoriaControlador {

    private final ProductoCategoriaServicio productoCategoriaServicio;
    private final ProductoDepartamentoServicio productoDepartamentoServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("productoCategoria", new ProductoCategoria());
        model.addAttribute("productoCategorias", (termino != null && !termino.isBlank())
                ? productoCategoriaServicio.buscarPorTermino(termino.trim())
                : productoCategoriaServicio.listarTodos());
        model.addAttribute("productoDepartamentos", productoDepartamentoServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "producto-categoria/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ProductoCategoria productoCategoria = productoCategoriaServicio.buscarPorId(id);
        if (productoCategoria.getProductoDepartamento() != null) {
            productoCategoria.setProductoDepartamentoId(productoCategoria.getProductoDepartamento().getIdProductoDepartamento());
        }
        model.addAttribute("productoCategoria", productoCategoria);
        model.addAttribute("productoCategorias", productoCategoriaServicio.listarTodos());
        model.addAttribute("productoDepartamentos", productoDepartamentoServicio.listarTodos());
        return "producto-categoria/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("productoCategoria") ProductoCategoria productoCategoria, BindingResult result, Model model) {
        if (productoCategoria.getProductoDepartamentoId() != null) {
            productoCategoria.setProductoDepartamento(productoDepartamentoServicio.buscarPorId(productoCategoria.getProductoDepartamentoId()));
        } else {
            productoCategoria.setProductoDepartamento(null);
        }
        if (result.hasErrors()) {
            model.addAttribute("error", "Hay errores en los datos del formulario. Revise los campos de la categoría de producto.");
            model.addAttribute("productoCategorias", productoCategoriaServicio.listarTodos());
            model.addAttribute("productoDepartamentos", productoDepartamentoServicio.listarTodos());
            return "producto-categoria/form";
        }
        try {
            productoCategoriaServicio.guardar(productoCategoria);
            return "redirect:/producto-categorias";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar la categoría de producto: " + e.getMessage());
            model.addAttribute("productoCategorias", productoCategoriaServicio.listarTodos());
            model.addAttribute("productoDepartamentos", productoDepartamentoServicio.listarTodos());
            return "producto-categoria/form";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            productoCategoriaServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/producto-categorias";
    }
}
