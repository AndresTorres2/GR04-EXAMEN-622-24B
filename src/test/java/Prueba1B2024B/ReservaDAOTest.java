package Prueba1B2024B;

import Model.DAO.ReservaDAO;
import Model.Entity.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

import static org.junit.Assert.*;

public class ReservaDAOTest {
    ReservaDAO reservaDAO;
    @Before
    public void setUp() throws Exception {
        reservaDAO= new ReservaDAO();
        Ruta ruta = new Ruta(1,"Ciudad A","Ciudad B", new ArrayList<>());
        Bus bus1 = new Bus("BUS-001",40);
        Bus bus2 = new Bus("BUS-002",40);
        Viaje viaje1 = new Viaje(1,bus1,null, Time.valueOf("08:00:00"),ruta,"matutino",15,null);
        Viaje viaje2 = new Viaje(2,bus2,null, Time.valueOf("13:00:00"),ruta,"vespertino",10,null);
        Estudiante estudiante1 = new Estudiante(1,"juan","perez", "juan.perez@example.com","123456","password123");
        Estudiante estudiante2 = new Estudiante(1,"andres","torres", "andres.torres@example.com","78910","contraseña123");
        List<Reserva> reservas = Arrays.asList(
                new Reserva(1, viaje1, estudiante1, null),
                new Reserva(2, viaje2, estudiante2, null),
                new Reserva(3, viaje2, estudiante2, null)
        );
        reservas.forEach(reservaDAO::createReserva);
    }
    @Test
    public void given_Reservas_when_ReadAll_then_AllReservasAreReturned() {
        List<Reserva> reservas = reservaDAO.getAllReservas(false);
        assertEquals(3, reservas.size());
    }

    @Test
    public void given_ReadError_when_ReadAll_then_ExceptionIsThrown() {

       //Para simular que lanza error la db
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaDAO.getAllReservas(true);
        });

        assertEquals("Error al leer la base de datos", exception.getMessage());
    }
    @Test
    public void given_Reserva_when_Create_then_ReservaIsCreatedSuccessfully() {
        Estudiante estudiante3 = new Estudiante(3, "Laura", "Martínez", "laura.martinez@example.com", "456789", "password321");
        Bus bus3 = new Bus("BUS-003", 40);
        Ruta ruta = new Ruta(2, "Ciudad C", "Ciudad D", new ArrayList<>());
        Viaje viaje3 = new Viaje(3, bus3, null, Time.valueOf("10:00:00"), ruta, "matutino", 20, null);
        Reserva nuevaReserva = new Reserva(4, viaje3, estudiante3, null);
        reservaDAO.createReserva(nuevaReserva);

        List<Reserva> reservas = reservaDAO.getAllReservas(false);
        assertTrue(reservas.contains(nuevaReserva));
    }
    @Test
    public void given_Reserva_when_Delete_then_ReservaIsDeletedSuccessfully() {
        List<Reserva> reservasIniciales = reservaDAO.getAllReservas(false);
        System.out.println("Reservas Iniciales: " + reservasIniciales);
        Reserva reservaAEliminar = reservasIniciales.get(1);
        System.out.println("Reserva a eliminar: " + reservaAEliminar);
        reservaDAO.deleteReserva(reservaAEliminar.getId(), false);
        List<Reserva> reservasActuales = reservaDAO.getAllReservas(false);
        System.out.println("Reservas Actuales: " + reservasActuales);
        assertFalse(reservasActuales.contains(reservaAEliminar));
    }
    @Test(expected = RuntimeException.class)
    public void given_NonExistentReserva_when_Delete_then_ExceptionIsThrown() {
        List<Reserva> reservasIniciales = reservaDAO.getAllReservas(false);
        Reserva reservaAEliminar = reservasIniciales.get(0);
        reservaDAO.deleteReserva(reservaAEliminar.getId(),true);
    }






    //No se si sirva En total REALICE 7 TEST
    @Test
    public void given_ExistingId_when_GetById_then_ReservaIsReturned() {
        List<Reserva> reservasIniciales = reservaDAO.getAllReservas(false);
        Reserva reservaEsperada = reservasIniciales.get(0);

        Reserva reservaObtenida = reservaDAO.readReserva(reservaEsperada.getId());
        assertEquals(reservaEsperada, reservaObtenida);
    }



}