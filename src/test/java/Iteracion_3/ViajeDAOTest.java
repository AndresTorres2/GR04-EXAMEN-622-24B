package Iteracion_3;

import Model.DAO.BusDAO;
import Model.DAO.RutaDAO;
import Model.DAO.ViajeDAO;
import Model.Entity.Bus;
import Model.Entity.Ruta;
import Model.Entity.Viaje;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ViajeDAOTest {
    private ViajeDAO viajeDAO;
    private RutaDAO rutaDAO;
    private BusDAO busDAO;
    private List<Viaje> viajes;
    private List<Bus> buses;
    private List<Ruta> rutas;
    @Before
    public void setUp() throws Exception {
        rutaDAO = new RutaDAO();
        viajeDAO = new ViajeDAO();
        busDAO = new BusDAO();

        buses = new ArrayList<>();
        rutas = new ArrayList<>();
        viajes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bus bus = new Bus("BUS-00" + i, 40 + i);
            busDAO.crearBusEnDB(bus);
            buses.add(bus);
            Ruta ruta = new Ruta(0, "Ciudad" + (char)('A' + i), "Ciudad" + (char)('B' + i), new ArrayList<>());
            rutaDAO.guardarRutaDb(ruta);
            rutas.add(ruta);
            Viaje viaje = new Viaje(0, bus, Date.valueOf("2024-12-12"), Time.valueOf("10:00:00"), ruta, "matutino", 20 + i, null);
            viajeDAO.crearViajeEnDB(viaje);
            viajes.add(viaje);
            viajeDAO.crearViajeEnDB(viaje);
            viajes.add(viaje);
        }
    }
    @Test
    public void given_ViajesExist_when_ReadAll_then_AllViajesAreReturned() {
        List<Viaje> viajesObtenidos = viajeDAO.obtenerTodosLosViajes();

        System.out.println(viajesObtenidos.size());

        assertTrue(viajesObtenidos.size() >= viajes.size());
    }
    @Test
    public void  given_ViajeExists_when_DeleteViaje_then_ViajeIsDeleted() {
        Bus busSeleccionado = buses.get(0);
        Ruta rutaSeleccionada = rutas.get(0);
        Viaje nuevoViaje = new Viaje(
                0,
                busSeleccionado,
                Date.valueOf("2024-12-15"),
                Time.valueOf("10:00:00"),
                rutaSeleccionada,
                "matutino",
                15,
                null
        );

        viajeDAO.crearViajeEnDB(nuevoViaje);


        Viaje viajeCreado = viajeDAO.obtenerViajeEnDB(nuevoViaje.getId());
        assertNotNull(viajeCreado);
        System.out.println(viajeCreado.getId());

        viajeDAO.eliminarViajeEnDB(viajeCreado.getId());

        Viaje viajeEliminado = viajeDAO.obtenerViajeEnDB(viajeCreado.getId());
        System.out.println(viajeEliminado);
        assertNull(viajeEliminado);

    }



    @After
    public void tearDown() {
        for (Viaje viaje : viajes) {
            viajeDAO.eliminarViajeEnDB(viaje.getId());
        }
        for(Ruta ruta: rutas){
            rutaDAO.eliminarRutaDb(ruta.getId());
        }
        for (Bus bus: buses){
            busDAO.eliminarBusEnDB(bus.getBusId());
        }

    }
}