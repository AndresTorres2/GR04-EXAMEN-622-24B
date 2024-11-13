package Iteracion3;

import Model.DAO.ViajeDAO;
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
    private List<Viaje> viajes;
    @Before
    public void setUp() throws Exception {
        viajeDAO = new ViajeDAO();
        viajes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Viaje viaje = new Viaje();
            viaje.setId(0);
            viaje.setFecha(new Date(System.currentTimeMillis()));
            viaje.setHoraDeSalida(new Time(System.currentTimeMillis()));
            viaje.setJornada("matutina");
            viaje.setAsientosOcupados(20);


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



    @After
    public void tearDown() {
        for (Viaje viaje : viajes) {
            viajeDAO.eliminarViajeEnDB(viaje.getId());
        }
    }
}