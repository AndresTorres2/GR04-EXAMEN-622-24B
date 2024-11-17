package Iteracion_4;

import Model.DAO.*;
import Model.Entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;

public class ReservaDAOTest {
    ReservaDAO reservaDAO;
    ViajeDAO viajeDAO;
    EstudianteDAO estudianteDAO;
    BusDAO busDAO;
    RutaDAO rutaDAO;
    UsuarioDAO usuarioDAO;
    private List<Estudiante> estudiantes = new ArrayList<>();
    private List<Bus> buses = new ArrayList<>();
    private List<Ruta> rutas = new ArrayList<>();
    private List<Viaje> viajes = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        reservaDAO = new ReservaDAO();
        viajeDAO = new ViajeDAO();
        estudianteDAO = new EstudianteDAO();
        busDAO = new BusDAO();
        rutaDAO = new RutaDAO();
        usuarioDAO = new UsuarioDAO();
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


            Reserva reserva = new Reserva(0, viaje, estudiante, fechaReserva);
            reservaDAO.guardarReserva(reserva, viaje);
            reservas.add(reserva);
        }
    }

    @Test
    public void given_Reservas_when_ReadAll_then_AllReservasAreReturned() {
        List<Reserva> reservas = reservaDAO.obtenerTodasLasReservas();
        assertNotNull(reservas);
        assertFalse(reservas.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void given_ReadError_when_ReadAll_then_ExceptionIsThrown() {
        reservaDAO.em.close();
        reservaDAO.obtenerTodasLasReservas();
    }


    @Test
    public void given_Reserva_when_Create_then_ReservaIsCreatedSuccessfully() {
        Date fechaReserva = Date.valueOf("2024-11-18");
        Date fechaViaje = Date.valueOf("2024-11-20");
        Estudiante estudiante3 = new Estudiante(0, "Laura", "Martínez", "laura.martinez@example.com", "456789", "password321");
        estudianteDAO.guardarEstudianteDb(estudiante3);
        Bus bus3 = new Bus("BUS-003", 40);
        busDAO.crearBusEnDB(bus3);
        Ruta ruta = new Ruta(0, "Ciudad C", "Ciudad D", new ArrayList<>());
        rutaDAO.guardarRutaDb(ruta);
        Viaje viaje3 = new Viaje(0, bus3, fechaViaje, Time.valueOf("10:00:00"), ruta, "matutino", 20, null);
        viajeDAO.crearViajeEnDB(viaje3);
        Reserva nuevaReserva = new Reserva(0, viaje3, estudiante3, fechaReserva);
        reservaDAO.guardarReserva(nuevaReserva, viaje3);

        List<Reserva> reservas = reservaDAO.obtenerTodasLasReservas();
        assertTrue(reservas.contains(nuevaReserva));
        Usuario estudianteEnDB = usuarioDAO.buscarUsuarioPorEmail("laura.martinez@example.com");
        reservaDAO.cancelarReserva(nuevaReserva.getId(), viaje3);
        estudianteDAO.eliminarEstudianteDb(estudianteEnDB.getId());
        viajeDAO.eliminarViajeEnDB(viaje3.getId());
        busDAO.eliminarBusEnDB(bus3.getBusId());
        rutaDAO.eliminarRutaDb(ruta.getId());

    }


    @Test
    public void given_Reserva_when_Delete_then_ReservaIsDeletedSuccessfully() {
        Date fechaReserva = Date.valueOf("2024-11-18");
        Date fechaViaje = Date.valueOf("2024-11-20");
        Estudiante estudiante3 = new Estudiante(0, "Laura", "Martínez", "laura.martinez@example.com", "456789", "password321");
        estudianteDAO.guardarEstudianteDb(estudiante3);
        Bus bus3 = new Bus("BUS-003", 40);
        busDAO.crearBusEnDB(bus3);
        Ruta ruta = new Ruta(0, "Ciudad C", "Ciudad D", new ArrayList<>());
        rutaDAO.guardarRutaDb(ruta);
        Viaje viaje3 = new Viaje(0, bus3, fechaViaje, Time.valueOf("10:00:00"), ruta, "matutino", 20, null);
        viajeDAO.crearViajeEnDB(viaje3);
        Reserva nuevaReserva = new Reserva(0, viaje3, estudiante3, fechaReserva);
        reservaDAO.guardarReserva(nuevaReserva, viaje3);
        reservaDAO.cancelarReserva(nuevaReserva.getId(), viaje3);


        List<Reserva> reservasActuales = reservaDAO.obtenerTodasLasReservas();
        assertFalse(reservasActuales.contains(nuevaReserva));
        Usuario estudianteEnDB = usuarioDAO.buscarUsuarioPorEmail("laura.martinez@example.com");
        estudianteDAO.eliminarEstudianteDb(estudianteEnDB.getId());
        viajeDAO.eliminarViajeEnDB(viaje3.getId());
        busDAO.eliminarBusEnDB(bus3.getBusId());
        rutaDAO.eliminarRutaDb(ruta.getId());
    }

    @Test(expected = RuntimeException.class)
    public void given_InvalidReservaId_when_CancelarReserva_then_ThrowRuntimeException() {


        Viaje viaje = new Viaje();

        int invalidReservaId = 9999;
        doThrow(new RuntimeException("Reserva no encontrada")).when(reservaDAO).cancelarReserva(invalidReservaId, viaje);
    }


    @Test
    public void given_ExistingId_when_GetById_then_ReservaIsReturned() {
        // Recupera una reserva por su ID desde la base de datos
        List<Reserva> reservasIniciales = reservaDAO.obtenerTodasLasReservas();
        Reserva reservaEsperada = reservasIniciales.get(0);

        Reserva reservaObtenida = reservaDAO.obtenerReservaPorId(reservaEsperada.getId());
        assertEquals(reservaEsperada, reservaObtenida);
    }

    @Test
    public void given_Viaje_when_CheckIfNotEmpty_then_ReturnsFalseIfHasReservations() {

        List<Reserva> reservas = reservaDAO.obtenerTodasLasReservas();
        Viaje viaje2 = reservas.get(1).getViaje();

        boolean isEmpty = reservaDAO.verificarViajeVacio(viaje2);
        assertFalse(isEmpty); // El viaje no debería estar vacío, ya que tiene reservas
    }



    @Test
    public void given_Viaje_when_ListPassengers_then_ReturnsCorrectPassengers() {
        // Recupera los pasajeros del viaje
        List<Reserva> reservas = reservaDAO.obtenerTodasLasReservas();
        Viaje viaje2 = reservas.get(0).getViaje();

        List<Estudiante> pasajeros = reservaDAO.listarPasajerosPorViaje(viaje2);
        System.out.println("Lista de Pasajeros:");
        for (Estudiante estudiante : pasajeros) {
            System.out.println("Estudiante: " + estudiante);
        }
        System.out.println("Estudiante esperado: " + reservas.get(0).getEstudiante());
        assertTrue(pasajeros.size() >= 1);

        assertTrue(pasajeros.stream()
                .anyMatch(estudiante -> estudiante.getId() == reservas.get(0).getEstudiante().getId()));

    }


    @After
    public void tearDown() {
        if (!reservaDAO.em.isOpen()) {
            reservaDAO.openEntityManager();
        }
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