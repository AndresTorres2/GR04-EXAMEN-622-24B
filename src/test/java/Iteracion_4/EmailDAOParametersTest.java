package Iteracion_4;

import Model.DAO.EmailDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(Parameterized.class)
public class EmailDAOParametersTest {
    private EmailDAO emailDAO;

    private final String destinatario;
    private final String asunto;
    private final String mensaje;


    @Before
    public void setUp() {
        emailDAO = new EmailDAO();
    }

    public EmailDAOParametersTest(String destinatario, String asunto, String mensaje) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"estudiante@ejemplo.com", "Reserva confirmada", "Estimado estudiante, su reserva ha sido realizada con éxito."},
                {"estudiante@ejemplo.com", "Reserva cancelada", "Estimado estudiante, su reserva ha sido cancelada con éxito."},
                {"estudiante@ejemplo.com", "Ubicación del bus compartida", "Estimados estudiantes, se ha empezado a compartir la ubicación del bus para su monitoreo."},
        });
    }

    @Test
    public void given_ValidParameters_when_SendEmail_then_EmailIsSentSuccessfully() {
        assertDoesNotThrow(() -> emailDAO.enviarCorreo(destinatario, asunto, mensaje),
                "Se esperaba que el envío fuera exitoso, pero se lanzó una excepción");
    }
}
