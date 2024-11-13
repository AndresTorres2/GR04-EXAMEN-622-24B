package Iteracion3;

import Model.DAO.ConductorDAO;
import Model.DAO.UsuarioDAO;
import Model.Entity.Conductor;
import Model.Entity.Estudiante;
import Model.Entity.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConductorDAOTest {
    ConductorDAO conductorDAO;
    UsuarioDAO usuarioDAO;
    private List<Conductor> conductores = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        conductorDAO = new ConductorDAO();
        usuarioDAO = new UsuarioDAO();
        for (int i = 0; i < 3; i++) {
            // Crear un nuevo conductor
            Conductor conductor = new Conductor(0, "Nombre" + i, "Apellido" + i, "email" + i + "@example.com", "09821321321" + i, "contrasena" + i);

            // Guardar conductor en la base de datos
            conductorDAO.guardarConductorDb(conductor);
            conductores.add(conductor);
        }
    }
    @Test
    public void given_NewConductor_when_Insert_then_ConductorIsSaved() {
        Conductor conductor = new Conductor(0,"Andresdasdsa","Santiagosadsa","asdas321@example.com","09821321321","contrasena");
        conductorDAO.guardarConductorDb(conductor);
        Usuario conductorEnDB = usuarioDAO.buscarUsuarioPorEmail("asdas321@example.com");
        assertNotNull(conductorEnDB);
        conductorDAO.eliminarConductorDb(String.valueOf(conductorEnDB.getId()));

    }

    @Test
    public void given_Conductor_when_Update_then_ConductorIsUpdatedSuccessfully() {
        Conductor conductor = new Conductor(0, "Carlos", "Perez", "carlos.perez@example.com", "0987654321", "password123");
        conductorDAO.guardarConductorDb(conductor);
        Conductor conductorEnDB = conductorDAO.obtenerConductorDb(String.valueOf(conductor.getId()));

        conductorEnDB.setNombre("Carlos Updated");
        conductorEnDB.setApellido("Perez Updated");
        conductorDAO.actualizarConductorDb(conductorEnDB);
        Usuario conductorActualizado = usuarioDAO.buscarUsuarioPorEmail(conductorEnDB.getEmail());
        assertEquals("Carlos Updated", conductorActualizado.getNombre());
        assertEquals("Perez Updated", conductorActualizado.getApellido());
        System.out.println("Nombre actualizado: " + conductorActualizado.getNombre());
        System.out.println("Apellido actualizado: " + conductorActualizado.getApellido());
        conductorDAO.eliminarConductorDb(String.valueOf(conductorEnDB.getId()));
    }
    @Test
    public void given_Conductor_when_ReadAll_then_AllConductorsAreReturned() {
        List<Usuario> conductores = conductorDAO.obtenerConductores();
        for (Usuario conductor : conductores) {
            System.out.println("Conductores: " + conductores);
        }
        assertNotNull(conductores);
        assertFalse(conductores.isEmpty());

    }
    @Test
    public void given_Conductor_when_Delete_then_ConductorIsRemoved() {
        Conductor conductor = new Conductor(0, "Carlos", "Perez", "carlos.perez@example.com", "0987654324", "password126");
        conductorDAO.guardarConductorDb(conductor);

        Usuario conductorEnDB = usuarioDAO.buscarUsuarioPorEmail("carlos.perez@example.com");
        conductorDAO.eliminarConductorDb(String.valueOf(conductorEnDB.getId()));
        Usuario conductorEliminado = usuarioDAO.buscarUsuarioPorEmail("carlos.perez@example.com");
        assertNull(conductorEliminado);

    }
    @After
    public void tearDown() throws Exception {
        for (Conductor conductor : conductores) {
            Usuario conductorEnDB = usuarioDAO.buscarUsuarioPorEmail(conductor.getEmail());

            conductorDAO.eliminarConductorDb(String.valueOf(conductorEnDB.getId()));
        }
    }
}