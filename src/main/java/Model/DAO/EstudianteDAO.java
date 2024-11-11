package Model.DAO;

import Model.Entity.Conductor;
import Model.Entity.Estudiante;
import Model.Entity.Usuario;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.List;

public class EstudianteDAO extends GenericDAO {


    public Estudiante obtenerEstudiantePorId(int idEstudiante) {
        return em.find(Estudiante.class, idEstudiante);
    }

    public List<Usuario> obtenerEstudiantes() {
        String jpql = "SELECT u FROM Usuario u WHERE TYPE(u) = Estudiante";
        return em.createQuery(jpql, Usuario.class).getResultList();
    }
    public void guardarEstudianteDb(Estudiante Estudiante) {
        try {
            beginTransaction();
            em.persist(Estudiante);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }
    public void eliminarEstudianteDb(int idEstudiante) {
        try {
            beginTransaction();
            em.remove(obtenerEstudiantePorId(idEstudiante));
            commitTransaction();
        }
        catch (Exception e) {
            rollbackTransaction();
            System.out.println("No se encontró la ruta con ID: " + idEstudiante);
            e.printStackTrace();
        }
    }


}
