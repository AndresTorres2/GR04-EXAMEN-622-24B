package Model.DAO;

import Model.Entity.ParadaEstudiante;
import Model.Entity.Viaje;
import jakarta.persistence.PersistenceException;
import java.util.List;

public class ParadaEstudianteDAO extends GenericDAO {

    public void guardarParadaEstudiante(ParadaEstudiante paradaEstudiante) {
        try {
            executeInTransaction(() -> em.persist(paradaEstudiante));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ParadaEstudiante> obtenerParadasEstudiantesPorViaje(Viaje viaje) {
        List<ParadaEstudiante> paradasEstudiantes = null;
        try {
            paradasEstudiantes = em.createQuery(
                            "SELECT p FROM ParadaEstudiante p WHERE p.viaje = :viaje", ParadaEstudiante.class)
                    .setParameter("viaje", viaje)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paradasEstudiantes;
    }

    public void eliminarParadaEstudiante(ParadaEstudiante paradaEstudiante) {
        try {
            executeInTransaction(() -> em.remove(em.contains(paradaEstudiante) ? paradaEstudiante : em.merge(paradaEstudiante)));
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("No se puede eliminar la parada del estudiante porque tiene dependencias asociadas.", e);
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    public void actualizarParadaEstudiante(ParadaEstudiante paradaEstudiante) {
        try {
            executeInTransaction(() -> em.merge(paradaEstudiante));
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    private void executeInTransaction(Runnable action) {
        try {
            beginTransaction();
            action.run();
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            throw e;
        }
    }
}
