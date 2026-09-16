package py.edu.nexa_store.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendedor_id_seq")
    @SequenceGenerator(name = "vendedor_id_seq", sequenceName = "vendedor_id_seq", allocationSize = 1)
    @Column(name = "id_vendedor")
    private Long idVendedor;

    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 80, nullable = false)
    private String apellido;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "sexo")
    private String sexo;

    // agregar anotaciones para tienda
    private Tienda tienda;

    @Transient
    private Long tiendaId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vendedor", fetch = FetchType.LAZY)
    private List<Venta> ventas = new ArrayList<>();
}
