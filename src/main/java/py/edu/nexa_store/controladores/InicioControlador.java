package py.edu.nexa_store.controladores;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import py.edu.nexa_store.servicios.TiendaServicio;
import py.edu.nexa_store.servicios.VendedorServicio;
import py.edu.nexa_store.servicios.VentaServicio;

public class InicioControlador {

    private final TiendaServicio tiendaServicio;
    private final VendedorServicio vendedorServicio;
    private final VentaServicio ventaServicio;

    @GetMapping("/")
    public String inicio() {
        return "";    // completar
    }

    // cambiar a TiendaControlador
    @GetMapping("/tienda/ver/{id}")
    public String ver(@PathVariable Long id, Model model) {
        model.addAttribute("tienda", tiendaServicio.buscarPorId(id));
        model.addAttribute("vendedores", vendedorServicio.listarPorTienda(id));
        model.addAttribute("ventas", ventaServicio.listarPorTienda(id));
        return "tienda/detalle";
    }
}
