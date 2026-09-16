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
@Table(name = "producto_categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_categoria_id_seq")
    @SequenceGenerator(name = "producto_categoria_id_seq", sequenceName = "producto_categoria_id_seq", allocationSize = 1)
    @Column(name = "id_producto_categoria")
    private Long idProductoCategoria;

    @Column(name = "descripcion")
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_departamento")
    private ProductoDepartamento productoDepartamento;

    @Transient
    private Long productoDepartamentoId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "productoCategoria", fetch = FetchType.LAZY)
    private List<Producto> productos = new ArrayList<>();
}
