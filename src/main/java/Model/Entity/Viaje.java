package Model.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "viajes")
public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "busId", nullable = false)
    private Bus bus;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "horaDeSalida", nullable = false)
    private Time horaDeSalida;

    @ManyToOne
    @JoinColumn(name = "rutaId", nullable = false)
    private Ruta ruta;

    @Column(name = "jornada", nullable = false, length = 20)
    private String jornada;

    @Column(name = "asientosOcupados")
    private int asientosOcupados;

    @ManyToOne
    @JoinColumn(name = "conductorId", referencedColumnName = "id", nullable = false)
    private Conductor conductor;

    public Viaje() {
    }

    public Viaje(int id, Bus bus, Date fecha, Time horaDeSalida, Ruta ruta, String jornada, int asientosOcupados, Conductor conductor) {
        this.id = id;
        this.bus = bus;
        this.fecha = fecha;
        this.horaDeSalida = horaDeSalida;
        this.ruta = ruta;
        this.jornada = jornada;
        this.asientosOcupados =  asientosOcupados;
        this.conductor = conductor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHoraDeSalida() {
        return horaDeSalida;
    }

    public void setHoraDeSalida(Time horaDeSalida) {
        this.horaDeSalida = horaDeSalida;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public int getAsientosOcupados() {
        return asientosOcupados;
    }

    public void setAsientosOcupados(int asientosOcupados) {
        this.asientosOcupados = asientosOcupados;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
}
