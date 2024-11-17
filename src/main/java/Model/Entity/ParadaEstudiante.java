package Model.Entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "paradasEstudiante")
public class ParadaEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ubicacionId", nullable = false)
    private Ubicacion ubicacion;

    @Id
    @ManyToOne
    @JoinColumn(name = "estudianteId", nullable = false) // Estudiante es una entidad, no un String
    private Estudiante estudiante;

    @Id
    @ManyToOne
    @JoinColumn(name = "viajeId", nullable = false)
    private Viaje viaje;

    public ParadaEstudiante() {
    }

    public ParadaEstudiante(Estudiante estudiante, Ubicacion ubicacion, Viaje viaje) {
        this.estudiante = estudiante;
        this.ubicacion = ubicacion;
        this.viaje = viaje;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}
