package Prueba1B2024B;

import Model.DAO.EmailDAO;
import jakarta.mail.MessagingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class EmailDAOParametersTest {
    private EmailDAO emailDAO;

    private final String destinatario;
    private final String asunto;
    private final String mensaje;
    private final Class<? extends Exception> expectedException;

    @Before
    public void setUp() {
        emailDAO = new EmailDAO();
    }

    public EmailDAOParametersTest(String destinatario, String asunto, String mensaje, Class<? extends Exception> expectedException) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {null, "Asunto", "Mensaje", IllegalArgumentException.class},
                {"", "Asunto", "Mensaje", IllegalArgumentException.class},
                {"destinatario@ejemplo.com", "Asunto", "Mensaje", null},
                {"destinatario@ejemplo.com", "", "", null},
        });
    }

    @Test
    public void testEnviarCorreo() {
        if (expectedException != null) {
            assertThrows(expectedException, () -> emailDAO.enviarCorreo(destinatario, asunto, mensaje));
        } else {
            try {
                emailDAO.enviarCorreo(destinatario, asunto, mensaje);
            } catch (Exception e) {
                throw new AssertionError("No se esperaba excepción, pero se lanzó: " + e.getClass().getSimpleName());
            }
        }
    }
}
