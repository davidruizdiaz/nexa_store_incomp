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
@Table(name = "producto_familia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoFamilia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_familia_id_seq")
    @SequenceGenerator(name = "producto_familia_id_seq", sequenceName = "producto_familia_id_seq", allocationSize = 1)
    @Column(name = "id_producto_familia")
    private Long idProductoFamilia;

    @Column(name = "descripcion")
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "productoFamilia", fetch = FetchType.LAZY)
    private List<ProductoDepartamento> productoDepartamentos = new ArrayList<>();
}
