package Iteracion_3;

import Model.DAO.BusDAO;
import Model.Entity.Bus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class BusDAOParametersTest {
    private BusDAO busDAO;
    private Bus busEsperado;
    private Bus bus;

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"Bus-0", 50, new Bus("Bus-0", 50)},
                {"Bus-1", 30, new Bus("Bus-1", 30)},
                {"Bus-2", 45, new Bus("Bus-2", 45)},
                {"Bus-3", 60, new Bus("Bus-3", 60)}
        });
    }
    public BusDAOParametersTest(String id, int capacidad, Bus busEsperado) {
        this.bus = new Bus(id, capacidad);
        this.busEsperado = busEsperado;
    }
    @Before
    public void setUp() {
        busDAO = new BusDAO();
    }

    @Test
    public void given_Bus_when_Save_then_Bus_Is_Saved_Successfully() {
        busDAO.crearBusEnDB(bus);

        Bus busObtenido = busDAO.obtenerBusPorId(bus.getBusId());

        assertNotNull(busObtenido);
        assertEquals(busEsperado.getCapacidad(), busObtenido.getCapacidad());

        busDAO.eliminarBusEnDB(busObtenido.getBusId());
    }

}