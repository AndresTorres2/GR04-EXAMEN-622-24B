package Iteracion_4;

import Model.DAO.ReservaDAO;
import Model.Entity.*;
import org.junit.*;

import java.sql.Time;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ReservaDAOMockTest {
    ReservaDAO reservaDAOMock;
    @Before
    public void setUp() {
        reservaDAOMock = mock(ReservaDAO.class);
    }

    @Test(expected = RuntimeException.class)
    public void given_Reserva_when_CreateError_then_ExceptionIsThrown() {
        Estudiante estudiante3 = new Estudiante(3, "Laura", "Martínez", "laura.martinez@example.com", "456789", "password321");
        Bus bus3 = new Bus("BUS-003", 40);
        Ruta ruta = new Ruta(2, "Ciudad C", "Ciudad D", new ArrayList<>());
        Viaje viaje3 = new Viaje(3, bus3, null, Time.valueOf("10:00:00"), ruta, "matutino", 20, null);
        Reserva nuevaReserva = new Reserva(4, viaje3, estudiante3, null);
        doThrow(new RuntimeException("Error al crear la reserva")).when(reservaDAOMock).guardarReserva(any(Reserva.class),any(Viaje.class));
        reservaDAOMock.guardarReserva(nuevaReserva,viaje3);
    }
}