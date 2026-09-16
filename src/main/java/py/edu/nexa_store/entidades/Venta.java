package py.edu.nexa_store.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_id_seq")
    @SequenceGenerator(name = "venta_id_seq", sequenceName = "venta_id_seq", allocationSize = 1)
    @Column(name = "id_venta")
    private Long idVenta;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDate fechaVenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", columnDefinition = "VARCHAR(20) DEFAULT 'Completada'")
    private EstadoVenta estado;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    // agregar anotaciones para cliente
    private Cliente cliente;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Vendedor vendedor;

    @Transient
    private Long tiendaId;

    @Transient
    private Long clienteId;

    @Transient
    private Long vendedorId;

    // agregar anotaciones para ventaDetalles
    private List<VentaDetalle> ventaDetalles = new ArrayList<>();
}
