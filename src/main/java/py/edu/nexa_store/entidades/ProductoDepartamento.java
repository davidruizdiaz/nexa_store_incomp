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
@Table(name = "producto_departamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDepartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_departamento_id_seq")
    @SequenceGenerator(name = "producto_departamento_id_seq", sequenceName = "producto_departamento_id_seq", allocationSize = 1)
    @Column(name = "id_producto_departamento")
    private Long idProductoDepartamento;

    @Column(name = "descripcion")
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto_familia")
    private ProductoFamilia productoFamilia;

    @Transient
    private Long productoFamiliaId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "productoDepartamento", fetch = FetchType.LAZY)
    private List<ProductoCategoria> productoCategorias = new ArrayList<>();
}
