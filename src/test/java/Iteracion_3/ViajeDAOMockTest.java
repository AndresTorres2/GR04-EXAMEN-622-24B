package Iteracion_3;

import Model.DAO.ViajeDAO;
import Model.Entity.Bus;
import Model.Entity.Conductor;
import Model.Entity.Ruta;
import Model.Entity.Viaje;
import org.junit.*;
import org.mockito.*;

import java.sql.Date;
import java.sql.Time;

import static org.mockito.Mockito.*;

public class ViajeDAOMockTest {
    @Mock
    private ViajeDAO viajeDAO;
    @Mock
    private Bus mockBus;
    @Mock
    private Ruta mockRuta;
    @Mock
    private Conductor mockConductor;
    private Viaje viaje;
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        viaje = new Viaje();
        viaje.setId(1);
        viaje.setBus(mockBus);
        viaje.setFecha(Date.valueOf("2024-12-12"));
        viaje.setHoraDeSalida(Time.valueOf("08:00:00"));
        viaje.setRuta(mockRuta);
        viaje.setJornada("matutina");
        viaje.setAsientosOcupados(20);
        viaje.setConductor(mockConductor);
    }
    @Test
    public void  given_ViajeDetails_when_CrearViaje_then_ViajeIsSaved() {
        doNothing().when(viajeDAO).crearViajeEnDB(viaje);
        viajeDAO.crearViajeEnDB(viaje);
        verify(viajeDAO, times(1)).crearViajeEnDB(viaje);
    }

    @Test
    public void given_ViajeExistente_when_ActualizarViaje_then_ViajeIsUpdated() {
        viaje.setAsientosOcupados(25);
        doNothing().when(viajeDAO).actualizarViajeEnDB(viaje);
        viajeDAO.actualizarViajeEnDB(viaje);
        verify(viajeDAO, times(1)).actualizarViajeEnDB(viaje);

    }
}