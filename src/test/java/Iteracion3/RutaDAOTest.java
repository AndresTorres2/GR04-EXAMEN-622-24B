package Iteracion3;

import Model.DAO.RutaDAO;
import Model.Entity.Ruta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RutaDAOTest {
    RutaDAO rutaDAO;
    private List<Ruta> rutas = new ArrayList<>();
    @Before
    public void setUp() throws Exception {
        rutaDAO = new RutaDAO();
        for (int i = 0; i < 3; i++) {
            Ruta ruta = new Ruta(0, "Ciudad" + (char)('A' + i), "Ciudad" + (char)('B' + i), null);
            rutaDAO.guardarRutaDb(ruta);
            rutas.add(ruta);
        }
    }

    @Test
    public void given_RutasExist_when_ReadAll_then_AllRutasAreReturned() {
        List<Ruta> rutasObtenidas = rutaDAO.obtenerTodasLasRutas();

       System.out.println(rutasObtenidas.size());
        assertTrue(rutasObtenidas.size() >= rutas.size());
    }

    @Test
    public void given_RutaExists_when_Delete_then_RutaIsRemoved() {
        Ruta ruta = new Ruta(0, "CiudadX", "CiudadY", null);
        rutaDAO.guardarRutaDb(ruta);

        Ruta rutaGuardada = rutaDAO.obtenerRutaId(ruta.getId());
        assertNotNull(rutaGuardada);

        rutaDAO.eliminarRutaDb(rutaGuardada.getId());

        Ruta rutaEliminada = rutaDAO.obtenerRutaId(ruta.getId());
        assertNull(rutaEliminada);
    }
    @Test
    public void given_RutaExists_when_Update_then_RutaIsUpdated() {
        // Crear y guardar una nueva ruta
        Ruta ruta = new Ruta(0, "CiudadInicial", "CiudadFinal", null);
        rutaDAO.guardarRutaDb(ruta);

        Ruta rutaGuardada = rutaDAO.obtenerRutaId(ruta.getId());
        assertNotNull(rutaGuardada);

        rutaGuardada.setOrigen("CiudadModificada");
        rutaGuardada.setDestino("CiudadFinalModificada");
        rutaDAO.actualizarRutaDb(rutaGuardada);


        Ruta rutaActualizada = rutaDAO.obtenerRutaId(ruta.getId());
        assertEquals("CiudadModificada", rutaActualizada.getOrigen());
        assertEquals("CiudadFinalModificada", rutaActualizada.getDestino());
        rutaDAO.eliminarRutaDb(rutaActualizada.getId());
    }



    @After
    public void tearDown() {

        for (Ruta ruta : rutas) {
            rutaDAO.eliminarRutaDb(ruta.getId());
        }
    }
}