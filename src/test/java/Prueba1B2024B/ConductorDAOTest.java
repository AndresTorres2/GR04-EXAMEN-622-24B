package Prueba1B2024B;

import Model.DAO.ConductorDAO;
import Model.DAO.UsuarioDAO;
import Model.Entity.Conductor;
import Model.Entity.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConductorDAOTest {
    ConductorDAO conductorDAO;
    UsuarioDAO usuarioDAO;


    @Before
    public void setUp() throws Exception {
        conductorDAO = new ConductorDAO();
        usuarioDAO = new UsuarioDAO();
    }

    @Test
    public void testInsertConductor() {
        Conductor conductor = new Conductor(0,"Andresdasdsa","Santiagosadsa","asdas321@example.com","09821321321","contrasena");
        conductorDAO.guardarConductorDb(conductor);
        Usuario conductorEnDB = usuarioDAO.buscarUsuarioPorEmail("asdas321@example.com");
        assertNotNull(conductorEnDB);
        conductorDAO.eliminarConductorDb(String.valueOf(conductorEnDB.getId()));

    }
    @After
    public void tearDown() throws Exception {

    }
}