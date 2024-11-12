package Prueba1B2024B;

import Model.Entity.Viaje;
import Model.DAO.ViajeDAO;
import Model.Entity.Bus;
import Model.Entity.Ruta;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ViajeDAOAdditionalTest {

    private ViajeDAO viajeDAO;

    @Before
    public void setUp() {
        viajeDAO = new ViajeDAO();
        Ruta ruta = new Ruta(1, "Ciudad A", "Ciudad B", new ArrayList<>());
        Bus bus = new Bus("BUS-001", 40);
        Viaje viaje = new Viaje(1, bus, null, Time.valueOf("08:00:00"), ruta, "Mañana", 0, null);
        viajeDAO.crearViaje(viaje.getId(), viaje);
    }

//    @Test
// public void given_Jornada_when_ListViajes_then_ReturnsCorrectViajes() {
//     List<Object[]> viajes = viajeDAO.listarViajesPorJornada("Mañana");
//     assertNotNull(viajes);
//     assertEquals(false, viajes.isEmpty());
//     System.out.println("Viajes encontrados para la jornada 'Mañana': " + viajes.size());
// }

    //Prueba realizada en MOkito
/*    @Test
    public void given_IdsArray_when_ConvertIdsToIntegers_then_ReturnsCorrectArray() {
        String[] ids = {"1", "2", "3"};
        int[] convertedIds = viajeDAO.convertirIdsAEnteros(ids);
        assertArrayEquals(new int[]{1, 2, 3}, convertedIds);
        System.out.println("Conversión de IDs correcta: " + Arrays.toString(ids));
    }*/

/*    @Test
    public void given_IdString_when_ConvertToList_then_ReturnsCorrectIdList() {
        String idsViajes = "1, 2, 3";
        List<Integer> idList = viajeDAO.convertirCadenaAListaDeIds(idsViajes);

        assertEquals(3, idList.size());
        assertTrue(idList.contains(1));
        assertTrue(idList.contains(2));
        assertTrue(idList.contains(3));
        System.out.println("Conversión de cadena a lista de IDs correcta: " + idsViajes);
    }*/

    @Test
    public void given_ConductorId_when_ListViajesByConductor_then_ReturnsCorrectList() {
        List<Viaje> viajes = viajeDAO.obtenerListaDeViajesPorConductor(1);
        assertNotNull(viajes);
        System.out.println("Número de viajes encontrados para el conductor con ID 1: " + viajes.size());
    }
}