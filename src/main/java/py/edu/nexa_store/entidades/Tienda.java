package py.edu.nexa_store.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tienda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tienda_id_seq")
    @SequenceGenerator(name = "tienda_id_seq", sequenceName = "tienda_id_seq", allocationSize = 1)
    @Column(name = "id_tienda")
    private Long idTienda;

    @Column(name = "descripcion")
    private String descripcion;

    // agregar anotaciones para vendedores
    private List<Vendedor> vendedores = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tienda", fetch = FetchType.LAZY)
    private List<Venta> ventas = new ArrayList<>();
}
