package py.edu.nexa_store.entidades;


import java.util.ArrayList;
import java.util.List;

// agregar anotaciones
public class Pais {

    // agregar anotaciones para idPais
    private Long idPais;

    // agregar anotaciones para idPais
    private String descripcion;

    // agregar anotaciones para territorio
    private String territorio;

    // agregar anotaciones para ciudades
    private List<Ciudad> ciudades = new ArrayList<>();
}
