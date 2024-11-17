package Model.DAO;

import Model.Entity.Ubicacion;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO extends GenericDAO {

    public List<Ubicacion> obtenerTodasLasUbicaciones() {
        List<Ubicacion> ubicaciones = new ArrayList<>();
        try {
            ubicaciones = em.createQuery("SELECT u FROM Ubicacion u", Ubicacion.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ubicaciones;
    }

    public Ubicacion obtenerUbicacionPorId(int id) {
        Ubicacion ubicacion = null;
        try {
            beginTransaction();
            TypedQuery<Ubicacion> query = em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :ubicacionId", Ubicacion.class);
            query.setParameter("ubicacionId", id);

            ubicacion = query.getSingleResult();
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return ubicacion;
    }

    public void agregarUbicacion(Ubicacion ubicacion) {
        try {
            beginTransaction();
            em.persist(ubicacion);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    public void actualizarUbicacion(Ubicacion ubicacion) {
        try {
            beginTransaction();
            em.merge(ubicacion);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    public void eliminarUbicacion(Ubicacion ubicacion) {
        try {
            beginTransaction();
            em.remove(ubicacion);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }
}
