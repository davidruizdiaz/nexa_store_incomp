package py.edu.nexa_store.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.nexa_store.entidades.Venta;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaFormDTO {

    private Venta venta = new Venta();
    private List<DetalleForm> detalles = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleForm {
        private Long productoId;
        private String productoNombre;
        private Integer unidadesVendidas;
        private BigDecimal valorVendido;
    }
}
