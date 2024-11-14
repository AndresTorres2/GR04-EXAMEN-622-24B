package Iteracion_4;

import Model.DAO.ViajeDAO;
import Model.Entity.Viaje;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViajeDAOMockTest {
    @Mock
    private ViajeDAO viajeDAO;

    @Test
    public void given_NoViajes_when_obtenerListaDeViajesPorConductor_then_ReturnsNoViajesMessage() {
        int idConductor = 202160111;
        when(viajeDAO.obtenerListaDeViajesPorConductor(idConductor)).thenReturn(null);
        List<Viaje> listaViajes = viajeDAO.obtenerListaDeViajesPorConductor(idConductor);
        assertNull(listaViajes);
        verify(viajeDAO).obtenerListaDeViajesPorConductor(idConductor);
    }

}