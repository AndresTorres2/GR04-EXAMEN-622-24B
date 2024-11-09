package Model.DAO;

import Model.Entity.Conductor;
import Model.Entity.Usuario;
import jakarta.persistence.PersistenceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConductorDAO extends GenericDAO{

    private static Map<String, Conductor> conductores = new HashMap<>();

    // < 0  (0, "Cristian", "Hernandez", "andres@gmail.com", "0991935087", "1234")  >

    public void guardarConductor(Conductor conductor) {
        conductores.put(String.valueOf(conductor.getId()), conductor);
    }

    public void eliminarConductor(String id) {
        conductores.remove(id);
    }
//    public void actualizarConductor(int id, Conductor nuevoConductor) {
//        conductores.put(id, nuevoConductor);
//    }
    public Conductor buscarPorId(String id) {
        return conductores.get(id);
    }



    //IMPLEMENTACION EN LA DB
    public List<Usuario> obtenerConductores() {
        String jpql = "SELECT u FROM Usuario u WHERE TYPE(u) = Conductor";
        return em.createQuery(jpql, Usuario.class).getResultList();
    }

    public Conductor obtenerConductorDb(String idConductor) {
        try {
            Conductor conductor = em.find(Conductor.class, idConductor);
            return conductor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void eliminarConductorDb(String idConductor) {
        try {
            beginTransaction();
                em.remove(obtenerConductorDb(idConductor));
            commitTransaction();
        }catch (PersistenceException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("No se puede eliminar el conductor porque hay viajes asociados a este conductor. " +
                    "Si desea eliminar al conductor, debe eliminar los viajes asociados a ese conductor");

        }
        catch (Exception e) {
            rollbackTransaction();
            System.out.println("No se encontró la ruta con ID: " + idConductor);
            e.printStackTrace();
        }
    }
    public void guardarConductorDb(Conductor conductor) {
        try {
            beginTransaction();
            em.persist(conductor);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            System.out.println("Error en crear conductor " );
            e.printStackTrace();
        }
    }


}
