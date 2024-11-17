package Iteracion_3;

import Model.DAO.UsuarioDAO;
import Model.Entity.Administrador;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UsuarioDAOTest {
    private UsuarioDAO administradorDAO;
    private Administrador administrador;
    @Before
    public void setUp() throws Exception {
        administradorDAO = new UsuarioDAO();
        administrador = new Administrador(0, "Juan", "Perez", "juan.perez@email.com", "123456789", "contrasenaSegura");
        administradorDAO.guardarUsuarioDb(administrador);

    }
    @Test
    public void givenValidCredentials_whenValidarCredenciales_thenReturnTrue() {
        boolean resultado = administradorDAO.validarCredenciales("juan.perez@email.com", "contrasenaSegura");
        assertTrue("Las credenciales deberían ser válidas.", resultado);
    }
    @After
    public void tearDown() {
        administradorDAO.eliminarAdministradorEnDB(administrador.getId());
    }

}