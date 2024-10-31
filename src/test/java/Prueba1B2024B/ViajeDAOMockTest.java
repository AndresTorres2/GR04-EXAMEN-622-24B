package Prueba1B2024B;


import Model.DAO.ViajeDAO;
import Model.Entity.*;
import static org.mockito.Mockito.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ViajeDAOMockTest {

    @Mock
    private EntityManager em;

    @Mock
    private Query query;

    @InjectMocks
    private ViajeDAO mockViajeDAO;

    private Viaje viaje;

    @Before
    public void setUp() {
        // Crear un ejemplo de viaje
        viaje = new Viaje(1, new Bus(), Date.valueOf("2024-10-01"), Time.valueOf("10:00:00"),
                new Ruta(), "Mañana", 0, new Conductor());
    }

    @Test
    public void given_ConductorId_when_ObtenerViajesPorConductor_then_ReturnsViajes() {
        String conductorId = "202170222"; // ID de Marcelo

        // Lista de viajes simulada para el conductor
        List<Viaje> viajesSimulados = new ArrayList<>();
        viajesSimulados.add(viaje);

        // Simular el comportamiento del método obtenerViajesPorConductor
        doReturn(viajesSimulados).when(mockViajeDAO).obtenerViajesPorConductor(conductorId);

        // Llamar al método para probar la interacción
        List<Viaje> resultado = mockViajeDAO.obtenerViajesPorConductor(conductorId);

        // Verificar que el método obtenerViajesPorConductor se haya llamado una vez con el parámetro esperado
        verify(mockViajeDAO, times(1)).obtenerViajesPorConductor(conductorId);

        // Mensaje de confirmación de la prueba
        System.out.println("Prueba con Mockito: Se ha obtenido el viaje correctamente\nViajes: " + resultado);
    }
}
