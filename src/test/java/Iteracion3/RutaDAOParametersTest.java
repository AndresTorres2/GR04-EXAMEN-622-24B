package Iteracion3;

import Model.DAO.RutaDAO;
import Model.Entity.Calle;
import Model.Entity.Ruta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class RutaDAOParametersTest {
    private RutaDAO rutaDAO;
    private Ruta rutaEsperada;
    private Ruta ruta;

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {0, "Ciudad A", "Ciudad B", null, new Ruta(0, "Ciudad A", "Ciudad B", null)},
                {0, "Ciudad C", "Ciudad D", null, new Ruta(0, "Ciudad C", "Ciudad D", null)},
                {0, "Ciudad E", "Ciudad F", null, new Ruta(0, "Ciudad E", "Ciudad F", null)},
                {0, "Ciudad G", "Ciudad H", null, new Ruta(0, "Ciudad G", "Ciudad H", null)},
                {0, "Ciudad I", "Ciudad J", null, new Ruta(0, "Ciudad I", "Ciudad J", null)},
                {0, "Ciudad K", "Ciudad L", null, new Ruta(0, "Ciudad K", "Ciudad L", null)},
                {0, "Ciudad M", "Ciudad N", null, new Ruta(0, "Ciudad M", "Ciudad N", null)},
                {0, "Ciudad O", "Ciudad P", null, new Ruta(0, "Ciudad O", "Ciudad P", null)}
        });
    }
    public RutaDAOParametersTest(int id, String origen, String destino, List<Calle> calles, Ruta rutaEsperada) {
        this.ruta = new Ruta(id, origen, destino, calles);
        this.rutaEsperada = rutaEsperada;
    }
    @Before
    public void setUp() {
        rutaDAO = new RutaDAO();
    }

    @Test
    public void given_Ruta_when_Save_then_Ruta_Is_Saved_Successfully() {
        rutaDAO.guardarRutaDb(ruta);

        // Obtener la ruta de la base de datos
        Ruta rutaObtenida = rutaDAO.obtenerRutaId(ruta.getId());


        assertNotNull(rutaObtenida);
        assertEquals(rutaEsperada.getOrigen(), rutaObtenida.getOrigen());
        assertEquals( rutaEsperada.getDestino(), rutaObtenida.getDestino());
        assertEquals( rutaEsperada.getCalles(), rutaObtenida.getCalles());


        rutaDAO.eliminarRutaDb(rutaObtenida.getId());
    }

}