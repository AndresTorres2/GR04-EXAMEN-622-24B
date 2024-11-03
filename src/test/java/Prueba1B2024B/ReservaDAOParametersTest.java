package Prueba1B2024B;

import Model.DAO.ReservaDAO;
import Model.Entity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ReservaDAOParametersTest {
    private Viaje viaje;
    private List<Estudiante> estudiantesEsperados;
    private ReservaDAO reservaDAO;

    @Before
    public void setUp() {
        reservaDAO = new ReservaDAO();

        Ruta ruta = new Ruta(1, "Ciudad A", "Ciudad B", new ArrayList<>());
        Bus bus = new Bus("BUS-001", 40);

        viaje = new Viaje(1, bus, null, Time.valueOf("08:00:00"), ruta, "matutino", 20, null);

        // Crear reservas para el viaje
        reservaDAO.createReserva(new Reserva(1, viaje, new Estudiante(1, "Carlos", "López", "carlos@example.com", "123456", "pass1"), null));
        reservaDAO.createReserva(new Reserva(2, viaje, new Estudiante(2, "Ana", "Pérez", "ana@example.com", "78910", "pass2"), null));
        reservaDAO.createReserva(new Reserva(3, viaje, new Estudiante(3, "Juan", "González", "juan@example.com", "456789", "pass3"), null));
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(new Estudiante(2, "Ana", "Pérez", "ana@example.com", "78910", "pass2"),
                        new Estudiante(1, "Carlos", "López", "carlos@example.com", "123456", "pass1"),
                        new Estudiante(3, "Juan", "González", "juan@example.com", "456789", "pass3"))
                }
        });
    }


    public ReservaDAOParametersTest(List<Estudiante> estudiantesEsperados) {
        this.estudiantesEsperados = estudiantesEsperados;
    }

    @Test
    public void given_Viaje_when_ListPassengersSorted_then_ReturnsSortedPassengers() {
        List<Estudiante> pasajerosObtenidos = reservaDAO.listPassengersByViajeSorted(viaje);

        assertEquals(estudiantesEsperados.size(), pasajerosObtenidos.size());

        for (int i = 0; i < estudiantesEsperados.size(); i++) {
            assertEquals(estudiantesEsperados.get(i).getNombre(), pasajerosObtenidos.get(i).getNombre());
        }

        System.out.println("Pasajeros ordenados: " + pasajerosObtenidos);
    }
}
