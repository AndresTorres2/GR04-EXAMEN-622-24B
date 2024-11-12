package Model.DAO;

import Model.Entity.Bus;
import Model.Entity.Conductor;
import Model.Entity.Ruta;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusDAO extends GenericDAO {

    private static Map<String, Bus> busDatabase = new HashMap<>();

    public Bus createBus(String id, int capacidad) {
        busDatabase.put(id, new Bus(id, capacidad));
        return busDatabase.get(id);
    }
    public void actualizarBus(String id, int nuevaCapacidad, Conductor nuevoConductor) {
        Bus busExistente = busDatabase.get(id);
        if (busExistente != null) {
            busExistente.setCapacidad(nuevaCapacidad);
            busDatabase.put(id, busExistente);
        }
    }

    public Bus deleteBus(String id) {
        return busDatabase.remove(id);
    }

    public Bus findBusById(String id) {
        return busDatabase.get(id);
    }

//implementacion con la DB
    public BusDAO() {
        super();
    }

    // Método para crear un nuevo Bus en la base de datos
    public void crearBusEnDB(Bus bus) {
        try {
            beginTransaction();
            em.persist(bus);
            commitTransaction();
        }catch (EntityExistsException e) {
            rollbackTransaction();
            throw new RuntimeException(" El bus no se puede registrar, debido a que ya existe ese numero de bus: " + bus.getBusId());
        }
        catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    // Método para verificar si un Bus existe en la base de datos por su ID
    public boolean existeBusEnDB(int id) {
        return em.find(Bus.class, id) != null;
    }

    // Método para obtener un Bus de la base de datos por su ID
    public Bus obtenerBusPorId(String id) {
        Bus bus = null;
        try {
            bus = em.find(Bus.class, id);
            if (bus != null) {
                em.refresh(bus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bus;
    }

    // Método para actualizar un Bus en la base de datos
    public void actualizarBusEnDB(Bus bus) {
        try {
            beginTransaction();
            em.merge(bus);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }


    public void eliminarBusEnDB(String id) {
        try {
            Bus bus = em.find(Bus.class, id);
            if (bus != null) {
                beginTransaction();
                em.remove(bus);
                commitTransaction();
            }
        }catch (PersistenceException e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("No se puede eliminar el bus porque hay viajes asociados a esa bus. " +
                    "Si desea eliminar este bus, debe eliminar los viajes asociados a ese bus");

        }
        catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    public List<Bus> obtenerTodosLosBuses() {
        List<Bus> buses = new ArrayList<>();
        try {
            buses = em.createQuery("SELECT b FROM Bus b", Bus.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buses;
    }
    public void actualizarBusDb(Bus bus) {
        try {
            beginTransaction();
            em.merge(bus);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

}
