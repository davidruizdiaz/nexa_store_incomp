package py.edu.nexa_store.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import py.edu.nexa_store.dto.VentaFormDTO;
import py.edu.nexa_store.entidades.EstadoVenta;
import py.edu.nexa_store.entidades.Venta;
import py.edu.nexa_store.entidades.VentaDetalle;
import py.edu.nexa_store.entidades.Vendedor;
import py.edu.nexa_store.servicios.ClienteServicio;
import py.edu.nexa_store.servicios.ProductoServicio;
import py.edu.nexa_store.servicios.TiendaServicio;
import py.edu.nexa_store.servicios.VendedorServicio;
import py.edu.nexa_store.servicios.VentaServicio;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaControlador {

    private final VentaServicio ventaServicio;
    private final TiendaServicio tiendaServicio;
    private final ClienteServicio clienteServicio;
    private final VendedorServicio vendedorServicio;
    private final ProductoServicio productoServicio;

    @GetMapping
    public String listar(@RequestParam(name = "termino", required = false) String termino, Model model) {
        model.addAttribute("ventas", (termino != null && !termino.isBlank())
                ? ventaServicio.buscarPorTermino(termino.trim())
                : ventaServicio.listarTodos());
        model.addAttribute("termino", termino);
        return "venta/list";
    }

    @GetMapping("/formulario")
    public String formulario(@RequestParam(name = "id", required = false) Long id, Model model) {
        VentaFormDTO ventaForm = new VentaFormDTO();
        Venta venta = new Venta();
        if (id == null) {
            venta.setFechaVenta(LocalDate.now());
            venta.setEstado(EstadoVenta.Completada);
        }
        ventaForm.setVenta(venta);
        if (id != null) {
            Venta ventaExistente = ventaServicio.buscarPorId(id);
            if (ventaExistente.getTienda() != null) {
                ventaExistente.setTiendaId(ventaExistente.getTienda().getIdTienda());
            }
            if (ventaExistente.getCliente() != null) {
                ventaExistente.setClienteId(ventaExistente.getCliente().getIdCliente());
            }
            if (ventaExistente.getVendedor() != null) {
                ventaExistente.setVendedorId(ventaExistente.getVendedor().getIdVendedor());
            }
            ventaForm.setVenta(ventaExistente);
            List<VentaFormDTO.DetalleForm> detalles = new ArrayList<>();
            if (ventaExistente.getVentaDetalles() != null) {
                for (VentaDetalle vd : ventaExistente.getVentaDetalles()) {
                    VentaFormDTO.DetalleForm df = new VentaFormDTO.DetalleForm();
                    df.setProductoId(vd.getProducto() != null ? vd.getProducto().getIdProducto() : null);
                    df.setProductoNombre(vd.getProducto() != null ? vd.getProducto().getDescripcion() : "");
                    df.setUnidadesVendidas(vd.getUnidadesVendidas());
                    df.setValorVendido(vd.getValorVendido());
                    detalles.add(df);
                }
            }
            ventaForm.setDetalles(detalles);
        }
        model.addAttribute("ventaForm", ventaForm);
        model.addAttribute("tiendas", tiendaServicio.listarTodos());
        model.addAttribute("clientes", clienteServicio.listarTodos());
        model.addAttribute("productos", productoServicio.listarTodos());
        return "venta/form";
    }

    @GetMapping("/api/vendedores")
    @ResponseBody
    public List<java.util.Map<String, Object>> vendedoresPorTienda(@RequestParam Long tiendaId) {
        List<Vendedor> vendedores = vendedorServicio.listarPorTienda(tiendaId);
        List<java.util.Map<String, Object>> result = new ArrayList<>();
        for (Vendedor v : vendedores) {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("idVendedor", v.getIdVendedor());
            map.put("nombre", v.getNombre());
            map.put("apellido", v.getApellido());
            result.add(map);
        }
        return result;
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("ventaForm") VentaFormDTO ventaForm, Model model) {
        Venta venta = ventaForm.getVenta();
        if (venta.getEstado() == null) {
            venta.setEstado(EstadoVenta.Completada);
        }
        if (venta.getTiendaId() != null) {
            venta.setTienda(tiendaServicio.buscarPorId(venta.getTiendaId()));
        }
        if (venta.getClienteId() != null) {
            venta.setCliente(clienteServicio.buscarPorId(venta.getClienteId()));
        }
        if (venta.getVendedorId() != null) {
            venta.setVendedor(vendedorServicio.buscarPorId(venta.getVendedorId()));
        }
        if (venta.getFechaVenta() == null || venta.getTienda() == null
                || venta.getCliente() == null || venta.getVendedor() == null) {
            model.addAttribute("error", "Debe completar la fecha y seleccionar tienda, cliente y vendedor.");
            model.addAttribute("ventaForm", ventaForm);
            model.addAttribute("tiendas", tiendaServicio.listarTodos());
            model.addAttribute("clientes", clienteServicio.listarTodos());
            model.addAttribute("productos", productoServicio.listarTodos());
            return "venta/form";
        }
        if (ventaForm.getDetalles() == null || ventaForm.getDetalles().isEmpty()) {
            model.addAttribute("error", "Debe agregar al menos un detalle de venta.");
            model.addAttribute("ventaForm", ventaForm);
            model.addAttribute("tiendas", tiendaServicio.listarTodos());
            model.addAttribute("clientes", clienteServicio.listarTodos());
            model.addAttribute("productos", productoServicio.listarTodos());
            return "venta/form";
        }
        try {
            boolean esNueva = venta.getIdVenta() == null;
            if (!esNueva) {
                Venta ventaExistente = ventaServicio.buscarPorId(venta.getIdVenta());
                venta.getVentaDetalles().clear();
                if (ventaExistente.getVentaDetalles() != null) {
                    for (VentaDetalle vd : ventaExistente.getVentaDetalles()) {
                        venta.getVentaDetalles().remove(vd);
                    }
                }
            }
            for (VentaFormDTO.DetalleForm df : ventaForm.getDetalles()) {
                VentaDetalle vd = new VentaDetalle();
                vd.setVenta(venta);
                if (df.getProductoId() != null) {
                    vd.setProducto(productoServicio.buscarPorId(df.getProductoId()));
                }
                vd.setUnidadesVendidas(df.getUnidadesVendidas());
                vd.setValorVendido(df.getValorVendido());
                venta.getVentaDetalles().add(vd);
            }
            ventaServicio.guardar(venta);
            return "redirect:/ventas";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo guardar la venta: " + e.getMessage());
            model.addAttribute("ventaForm", ventaForm);
            model.addAttribute("tiendas", tiendaServicio.listarTodos());
            model.addAttribute("clientes", clienteServicio.listarTodos());
            model.addAttribute("productos", productoServicio.listarTodos());
            return "venta/form";
        }
    }

    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id) {
        try {
            ventaServicio.cancelar(id);
        } catch (Exception e) {
            // registro inexistente, se ignora
        }
        return "redirect:/ventas";
    }
}
