package py.edu.nexa_store.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "venta_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_detalle_id_seq")
    @SequenceGenerator(name = "venta_detalle_id_seq", sequenceName = "venta_detalle_id_seq", allocationSize = 1)
    @Column(name = "id_venta_detalle")
    private Long idVentaDetalle;

    @Column(name = "unidades_vendidas")
    private Integer unidadesVendidas;

    @Column(name = "valor_vendido", precision = 18, scale = 2)
    private BigDecimal valorVendido;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Transient
    private Long ventaId;

    @Transient
    private Long productoId;
}
