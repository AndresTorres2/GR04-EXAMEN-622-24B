package Model.DAO;

import Model.Entity.ParadaEstudiante;
import Model.Entity.Viaje;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import java.util.List;

public class ParadaEstudianteDAO extends GenericDAO {

    public ParadaEstudiante obtenerParadaPorViaje(Viaje viaje) {
        try {
            String query = "SELECT p FROM ParadaEstudiante p WHERE p.viaje = :viaje";
            return em.createQuery(query, ParadaEstudiante.class)
                    .setParameter("viaje", viaje)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void crearParada(ParadaEstudiante parada) {
        executeInTransaction(() -> {
            em.persist(parada);
        });
    }


    public void actualizarParada(ParadaEstudiante parada) {
        try {
            em.getTransaction().begin();
            em.merge(parada);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
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
