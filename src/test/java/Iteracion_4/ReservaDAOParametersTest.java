package Iteracion_4;

import Model.DAO.*;
import Model.Entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ReservaDAOParametersTest {
    private ViajeDAO viajeDAO;
    private ReservaDAO reservaDAO;
    private EstudianteDAO estudianteDAO;
    private BusDAO busDAO;
    private RutaDAO rutaDAO;

    private List<Viaje> viajes;
    private List<Estudiante> estudiantes;
    private List<Reserva> reservas;
    private List<Bus> buses;
    private List<Ruta> rutas;

    private final List<Estudiante> estudiantesEsperados;
    private Viaje viaje;

    @Before
    public void setUp() throws Exception {
        reservaDAO = new ReservaDAO();
        viajeDAO = new ViajeDAO();
        estudianteDAO = new EstudianteDAO();
        busDAO = new BusDAO();
        rutaDAO = new RutaDAO();

        viajes = new ArrayList<>();
        estudiantes = new ArrayList<>();
        reservas = new ArrayList<>();
        buses = new ArrayList<>();
        rutas = new ArrayList<>();


        Date fechaReserva = Date.valueOf("2024-11-25");
        Date fechaViaje = Date.valueOf("2024-11-30");

        for (int i = 0; i < 3; i++) {
            Estudiante estudiante = new Estudiante(0, "Estudiante" + i, "Apellido" + i, "email" + i + "@example.com", "tel" + i, "password" + i);
            estudianteDAO.guardarEstudianteDb(estudiante);
            estudiantes.add(estudiante);

            Bus bus = new Bus("BUS-00" + i, 40 + i);
            busDAO.crearBusEnDB(bus);
            buses.add(bus);

            Ruta ruta = new Ruta(0, "Ciudad" + (char)('A' + i), "Ciudad" + (char)('B' + i), new ArrayList<>());
            rutaDAO.guardarRutaDb(ruta);
            rutas.add(ruta);

            Viaje viaje = new Viaje(0, bus, fechaViaje, Time.valueOf("10:00:00"), ruta, "matutino", 20 + i, null);
            viajeDAO.crearViajeEnDB(viaje);
            viajes.add(viaje);

            Reserva reserva = new Reserva(0, viajes.get(0), estudiante, fechaReserva);
            reservaDAO.guardarReserva(reserva, viajes.get(0));
            reservas.add(reserva);
        }


        this.viaje = viajes.get(0);
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(
                        new Estudiante(0, "Estudiante0", "Apellido0", "email0@example.com", "tel0", "password0"),
                        new Estudiante(0, "Estudiante1", "Apellido1", "email1@example.com", "tel1", "password1"),
                        new Estudiante(0, "Estudiante2", "Apellido2", "email2@example.com", "tel2", "password2"))
                }
        });
    }


    public ReservaDAOParametersTest(List<Estudiante> estudiantesEsperados) {
        this.estudiantesEsperados = estudiantesEsperados;
    }

    @Test
    public void given_Viaje_when_ListPassengersSorted_then_ReturnsSortedPassengers() {
        List<Estudiante> pasajerosObtenidos = reservaDAO.listarPasajerosPorViajeOrdenado(viaje);

        assertEquals(estudiantesEsperados.size(), pasajerosObtenidos.size());

        for (int i = 0; i < estudiantesEsperados.size(); i++) {
            assertEquals(estudiantesEsperados.get(i).getNombre(), pasajerosObtenidos.get(i).getNombre());
        }

        System.out.println("Pasajeros ordenados: " + pasajerosObtenidos);
    }

    @After
    public void tearDown() {
        // Limpiar la base de datos
        for (Reserva reserva : reservas) {
            reservaDAO.cancelarReserva(reserva.getId(), reserva.getViaje());
        }
        for (Estudiante estudiante : estudiantes) {
            estudianteDAO.eliminarEstudianteDb(estudiante.getId());
        }
        for (Viaje viaje : viajes) {
            viajeDAO.eliminarViajeEnDB(viaje.getId());
        }
        for (Bus bus : buses) {
            busDAO.eliminarBusEnDB(bus.getBusId());
        }
        for (Ruta ruta : rutas) {
            rutaDAO.eliminarRutaDb(ruta.getId());
        }
    }
}
