package Model.DAO;

import Model.Entity.Ruta;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RutaDAO extends GenericDAO {


    //Implementacion con DB
    public List<Ruta> obtenerTodasLasRutas() {
        List<Ruta> rutas = new ArrayList<>();
        try {
            rutas = em.createQuery("SELECT r FROM Ruta r", Ruta.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rutas;
    }
    public void guardarRutaDb(Ruta ruta) {
        try {
            executeInTransaction(() -> em.persist(ruta));
        } catch (Exception e) {
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

    public Ruta  obtenerRutaId(int rutaId) {
        Ruta ruta =  null;
        try {
            beginTransaction();
            ruta = em.find(Ruta.class, rutaId);
            commitTransaction();
        }
        catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return ruta;
    }
    public void eliminarRutaDb(int rutaId) {
        try {
            Ruta ruta = obtenerRutaId(rutaId);
            em.getTransaction().begin();
            em.remove(ruta);
            em.getTransaction().commit();
        }
        catch (PersistenceException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("No se puede eliminar la ruta porque hay viajes asociados a esa ruta. " +
                    "Si desea eliminar esta ruta, debe eliminar los viajes asociados a esa ruta");

        }
        catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }
    public void actualizarRutaDb(Ruta ruta) {
        try {
            beginTransaction();
            em.merge(ruta);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }



}
