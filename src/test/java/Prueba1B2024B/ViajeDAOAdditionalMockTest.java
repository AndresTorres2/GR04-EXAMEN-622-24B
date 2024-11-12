package Prueba1B2024B;


import Model.DAO.ViajeDAO;
import Model.Entity.Conductor;
import Model.Entity.Viaje;
import Model.Entity.Bus;
import Model.Entity.Ruta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
        import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ViajeDAOAdditionalMockTest {

    @Mock
    private ViajeDAO mockViajeDAO;
    private Viaje viaje;

    @Before
    public void setUp() {
        viaje = new Viaje(1, new Bus(), Date.valueOf("2024-11-01"),
                Time.valueOf("08:00:00"), new Ruta(), "Mañana", 0, new Conductor());
    }

    @Test
    public void given_ExistingId_when_CheckExistence_then_ReturnsTrue() {
        when(mockViajeDAO.existeViaje(viaje.getId())).thenReturn(true);

        boolean exists = mockViajeDAO.existeViaje(viaje.getId());

        verify(mockViajeDAO, times(1)).existeViaje(viaje.getId());
        assertTrue(exists);
        System.out.println("Prueba con Mockito: El viaje con ID " + viaje.getId() + " existe.");
    }

    @Test
    public void given_Viaje_when_Update_then_Viaje_Is_Updated_Successfully() {
        doNothing().when(mockViajeDAO).actualizarViaje(viaje.getId(), viaje);

        mockViajeDAO.actualizarViaje(viaje.getId(), viaje);

        verify(mockViajeDAO, times(1)).actualizarViaje(viaje.getId(), viaje);
        System.out.println("Prueba con Mockito: Se ha actualizado el viaje correctamente con ID " + viaje.getId());
    }

    @Test
    public void given_Viaje_when_Delete_then_Viaje_Is_Deleted_Successfully() {
        doNothing().when(mockViajeDAO).eliminarViaje(viaje.getId());

        mockViajeDAO.eliminarViaje(viaje.getId());

        verify(mockViajeDAO, times(1)).eliminarViaje(viaje.getId());
        System.out.println("Prueba con Mockito: Se ha eliminado el viaje con ID " + viaje.getId());
    }
    //Prueba Correcta---------------
    @Test
    public void given_Jornada_when_ListViajes_then_ReturnsCorrectList() {
        List<Object[]> viajesList = new ArrayList<>();
        viajesList.add(new Object[]{"10:00:00", "Ciudad A", "Ciudad B", "1, 2, 3"});
        when(mockViajeDAO.listarViajesPorJornada("Mañana")).thenReturn(viajesList);

        List<Object[]> result = mockViajeDAO.listarViajesPorJornada("Mañana");

        verify(mockViajeDAO, times(1)).listarViajesPorJornada("Mañana");
        assertEquals(viajesList, result);
        System.out.println("Prueba con Mockito: Se ha listado correctamente los viajes de la jornada 'Mañana'.");
    }

/*
    @Test
    public void given_IdsArray_when_ConvertIdsToIntegers_then_ReturnsCorrectArray() {
        String[] ids = {"1", "2", "3"};
        int[] expectedIds = {1, 2, 3};
        when(mockViajeDAO.convertirIdsAEnteros(ids)).thenReturn(expectedIds);

        int[] result = mockViajeDAO.convertirIdsAEnteros(ids);

        verify(mockViajeDAO, times(1)).convertirIdsAEnteros(ids);
        assertArrayEquals(expectedIds, result);
        System.out.println("Prueba con Mockito: Conversión de IDs a enteros fue exitosa.");
    }
    */
}
