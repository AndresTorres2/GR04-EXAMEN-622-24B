package Iteracion_4;

import Model.DAO.BusDAO;
import Model.DAO.ConductorDAO;
import Model.DAO.RutaDAO;
import Model.DAO.ViajeDAO;
import Model.Entity.Bus;
import Model.Entity.Conductor;
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
    private ConductorDAO conductorDAO;
    private BusDAO busDAO;
    private RutaDAO rutaDAO;
    private List<Viaje> viajes;
    private List<Bus> buses;
    private List<Ruta> rutas;
    private Conductor conductor;
    @Before
    public void setUp() {
        viajeDAO = new ViajeDAO();
        conductorDAO = new ConductorDAO();
        busDAO = new BusDAO();
        rutaDAO = new RutaDAO();
        viajes = new ArrayList<>();
        buses = new ArrayList<>();
        rutas = new ArrayList<>();


        conductor = new Conductor(0, "luiso", "Pérez", "luisoperez@example.com", "123456", "password123");
        conductorDAO.guardarConductorDb(conductor);


        Date fechaViaje = Date.valueOf("2024-11-30");
        for (int i = 0; i < 3; i++) {

            Ruta ruta = new Ruta(0, "CiudadA", "CiudadB", new ArrayList<>());
            rutaDAO.guardarRutaDb(ruta);
            rutas.add(ruta);


            Bus bus = new Bus("BUS-00" + i, 40 + i);
            busDAO.crearBusEnDB(bus);
            buses.add(bus);


            Viaje viaje = new Viaje(0, bus, fechaViaje, Time.valueOf("10:00:00"), ruta, "matutino", 20 + i, conductor);
            viajeDAO.crearViajeEnDB(viaje);
            viajes.add(viaje);
        }
    }
    @Test
    public void given_Conductor_when_obtenerListaDeViajesPorConductor_then_ReturnsViajes() {

        List<Viaje> listaViajesObtenidos = viajeDAO.obtenerListaDeViajesPorConductor(conductor.getId());


        assertEquals("La cantidad de viajes debe ser igual a los viajes creados para el conductor",
                viajes.size(), listaViajesObtenidos.size());


        for (Viaje viaje : listaViajesObtenidos) {
            assertEquals("El conductor del viaje debe coincidir con el conductor esperado",
                    conductor.getId(), viaje.getConductor().getId());
        }

    }
    @After
    public void tearDown() {
        for (Viaje viaje : viajes) {
            viajeDAO.eliminarViajeEnDB(viaje.getId());
        }
        for (Bus bus : buses) {
            busDAO.eliminarBusEnDB(bus.getBusId());
        }
        for (Ruta ruta : rutas) {
            rutaDAO.eliminarRutaDb(ruta.getId());
        }
        conductorDAO.eliminarConductorDb(String.valueOf(conductor.getId()));
    }
}