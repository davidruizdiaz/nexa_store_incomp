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
import py.edu.nexa_store.entidades.Vendedor;
import py.edu.nexa_store.servicios.TiendaServicio;
import py.edu.nexa_store.servicios.VendedorServicio;

@Controller
@RequestMapping("/vendedores")
@RequiredArgsConstructor
public class VendedorControlador {

    private final VendedorServicio vendedorServicio;
    private final TiendaServicio tiendaServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("vendedor", new Vendedor());
        model.addAttribute("vendedores", (termino != null && !termino.isBlank())
                ? vendedorServicio.buscarPorTermino(termino.trim())
                : vendedorServicio.listarTodos());
        model.addAttribute("tiendas", tiendaServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "vendedor/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Vendedor vendedor = vendedorServicio.buscarPorId(id);
        if (vendedor.getTienda() != null) {
            vendedor.setTiendaId(vendedor.getTienda().getIdTienda());
        }
        model.addAttribute("vendedor", vendedor);
        model.addAttribute("vendedores", vendedorServicio.listarTodos());
        model.addAttribute("tiendas", tiendaServicio.listarTodos());
        return "vendedor/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("vendedor") Vendedor vendedor, BindingResult result, Model model) {
        if (vendedor.getTiendaId() != null) {
            vendedor.setTienda(tiendaServicio.buscarPorId(vendedor.getTiendaId()));
        } else {
            vendedor.setTienda(null);
        }
        if (result.hasErrors() || vendedor.getTienda() == null) {
            model.addAttribute("error", "Debe completar todos los campos y seleccionar una tienda.");
            model.addAttribute("vendedores", vendedorServicio.listarTodos());
            model.addAttribute("tiendas", tiendaServicio.listarTodos());
            return "vendedor/form";
        }
        try {
            vendedorServicio.guardar(vendedor);
            return "redirect:/vendedores";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar el vendedor: " + e.getMessage());
            model.addAttribute("vendedores", vendedorServicio.listarTodos());
            model.addAttribute("tiendas", tiendaServicio.listarTodos());
            return "vendedor/form";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            vendedorServicio.eliminar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/vendedores";
    }
}
