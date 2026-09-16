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
import py.edu.nexa_store.entidades.ProductoDepartamento;
import py.edu.nexa_store.servicios.ProductoDepartamentoServicio;
import py.edu.nexa_store.servicios.ProductoFamiliaServicio;

@Controller
@RequestMapping("/producto-departamentos")
@RequiredArgsConstructor
public class ProductoDepartamentoControlador {

    private final ProductoDepartamentoServicio productoDepartamentoServicio;
    private final ProductoFamiliaServicio productoFamiliaServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("productoDepartamento", new ProductoDepartamento());
        model.addAttribute("productoDepartamentos", (termino != null && !termino.isBlank())
                ? productoDepartamentoServicio.buscarPorTermino(termino.trim())
                : productoDepartamentoServicio.listarTodos());
        model.addAttribute("productoFamilias", productoFamiliaServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "producto-departamento/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ProductoDepartamento productoDepartamento = productoDepartamentoServicio.buscarPorId(id);
        if (productoDepartamento.getProductoFamilia() != null) {
            productoDepartamento.setProductoFamiliaId(productoDepartamento.getProductoFamilia().getIdProductoFamilia());
        }
        model.addAttribute("productoDepartamento", productoDepartamento);
        model.addAttribute("productoDepartamentos", productoDepartamentoServicio.listarTodos());
        model.addAttribute("productoFamilias", productoFamiliaServicio.listarTodos());
        return "producto-departamento/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("productoDepartamento") ProductoDepartamento productoDepartamento, BindingResult result, Model model) {
        if (productoDepartamento.getProductoFamiliaId() != null) {
            productoDepartamento.setProductoFamilia(productoFamiliaServicio.buscarPorId(productoDepartamento.getProductoFamiliaId()));
        } else {
            productoDepartamento.setProductoFamilia(null);
        }
        if (result.hasErrors()) {
            model.addAttribute("error", "Hay errores en los datos del formulario. Revise los campos del departamento de producto.");
            model.addAttribute("productoDepartamentos", productoDepartamentoServicio.listarTodos());
            model.addAttribute("productoFamilias", productoFamiliaServicio.listarTodos());
            return "producto-departamento/form";
        }
        try {
            productoDepartamentoServicio.guardar(productoDepartamento);
            return "redirect:/producto-departamentos";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar el departamento de producto: " + e.getMessage());
            model.addAttribute("productoDepartamentos", productoDepartamentoServicio.listarTodos());
            model.addAttribute("productoFamilias", productoFamiliaServicio.listarTodos());
            return "producto-departamento/form";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            productoDepartamentoServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/producto-departamentos";
    }
}
