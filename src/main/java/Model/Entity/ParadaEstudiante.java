package Model.Entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "paradas_estudiantes")
public class ParadaEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ubicacionId", nullable = false)
    private Ubicacion ubicacion;

    @ManyToOne
    @JoinColumn(name = "reservaId", nullable = false)
    private Reserva reserva;

    public ParadaEstudiante() {
    }

    public ParadaEstudiante(Ubicacion ubicacion, Reserva reserva) {
        this.ubicacion = ubicacion;
        this.reserva = reserva;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "ParadaEstudiante{" +
                "id=" + id +
                ", ubicacion=" + ubicacion +
                ", reserva=" + reserva +
                '}';
    }
}
