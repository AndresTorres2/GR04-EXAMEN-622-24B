package Iteracion3;

import Model.DAO.BusDAO;
import Model.Entity.Bus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BusDAOTest {
    BusDAO busDAO;
    private List<Bus> buses = new ArrayList<>();
    @Before
    public void setUp() throws Exception {
        busDAO = new BusDAO();
        for (int i = 0; i < 3; i++) {
            Bus bus = new Bus("Bus-00" + i, 40 + i);
            busDAO.crearBusEnDB(bus);
            buses.add(bus);
        }
    }

    @Test
    public void given_BusesExist_when_ReadAll_then_AllBusesAreReturned() {
        List<Bus> busesObtenidos = busDAO.obtenerTodosLosBuses();

        assertTrue(busesObtenidos.size() >= buses.size());

    }


    @Test
    public void given_BusExists_when_Delete_then_BusIsRemoved() {
        Bus bus = new Bus("Bus-009", 50);
        busDAO.crearBusEnDB(bus);

        Bus busGuardado = busDAO.obtenerBusPorId(bus.getBusId());
        assertNotNull(busGuardado);


        busDAO.eliminarBusEnDB(busGuardado.getBusId());

        Bus busEliminado = busDAO.obtenerBusPorId(bus.getBusId());
        assertNull(busEliminado);
    }

    @Test
    public void given_BusExists_when_Update_then_BusIsUpdated() {
        // Crear y guardar un nuevo bus
        Bus bus = new Bus("Bus-010", 60);
        busDAO.crearBusEnDB(bus);


        Bus busGuardado = busDAO.obtenerBusPorId(bus.getBusId());
        assertNotNull(busGuardado);

        busGuardado.setCapacidad(75);
        busDAO.actualizarBusEnDB(busGuardado);


        Bus busActualizado = busDAO.obtenerBusPorId(bus.getBusId());
        assertEquals(75, busActualizado.getCapacidad());


        busDAO.eliminarBusEnDB(busActualizado.getBusId());


    }




    @After
    public void tearDown() {
        for (Bus bus : buses) {
            busDAO.eliminarBusEnDB(bus.getBusId());
        }
    }
}