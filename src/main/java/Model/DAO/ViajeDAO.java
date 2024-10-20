package Model.DAO;

import Model.Entity.Viaje;
import Model.Entity.Ruta;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ViajeDAO extends GenericDAO{
    public ViajeDAO() {
        super();
    }
    private final HashMap<Integer, Viaje> viajes = new HashMap<>();

    public void crearViaje(Integer id, Viaje viaje) {
        viajes.put(id, viaje);
    }
    public boolean existeViaje(Integer id) {
        return viajes.containsKey(id);
    }
    public void eliminarViaje(Integer id) {
        viajes.remove(id);
    }
    public void actualizarViaje(Integer id, Viaje nuevoViaje) {
        viajes.put(id, nuevoViaje);
    }
    public Viaje obtenerViaje(Integer id) {
        return viajes.get(id);
    }


    public void crearViajeEnDB(Viaje viaje) {
        try {
            em.getTransaction().begin();
            em.persist(viaje);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean existeViajeEnDB(Integer id) {
        return em.find(Viaje.class, id) != null;
    }

    public void eliminarViajeEnDB(Integer id) {
        try {
            Viaje viaje = em.find(Viaje.class, id);
            if (viaje != null) {
                em.getTransaction().begin();
                em.remove(viaje);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizarViajeEnDB(Viaje nuevoViaje) {
        try {
            em.getTransaction().begin();
            em.merge(nuevoViaje);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public Viaje obtenerViajeEnDB(Integer id) {
        return em.find(Viaje.class, id);
    }

    public List<Viaje> obtenerTodosLosViajes() {
        List<Viaje> viajes = new ArrayList<>();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT v FROM Viaje v", Viaje.class);
            viajes = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return viajes;
    }

    public List<Object[]> listarViajesPorJornada(String jornada) {
        List<Object[]> resultList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT v.horaDeSalida, r.origen, r.destino, (SELECT GROUP_CONCAT(v2.id)  " +
                    "FROM Viajes v2 WHERE v2.rutaId = v.rutaId AND v2.jornada = :jornada ) AS idViajes " +
                    "FROM Viajes v " +
                    "JOIN Rutas r ON v.rutaId = r.id " +
                    "WHERE v.jornada = :jornada " +
                    "AND v.rutaId IN ( " +
                    "    SELECT DISTINCT rutaId " +
                    "    FROM Viajes " +
                    "    WHERE jornada = :jornada " +
                    ") " +
                    "ORDER BY r.origen, r.destino;";

            Query query = em.createNativeQuery(sql);
            query.setParameter("jornada", jornada);

            resultList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public Viaje obtenerViajePorCodigo(int codigo) {
        try {
            Viaje viaje = em.find(Viaje.class, codigo);
            if (viaje != null) {
                em.refresh(viaje);
            }
            return viaje;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int[] convertirIdsAEnteros(String[] idsViajesSeleccionados) {
        if (idsViajesSeleccionados != null) {
            return Arrays.stream(idsViajesSeleccionados)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        return new int[0];
    }
    public List<Viaje> obtenerListaDeViajes(String[] idsViajes) {
        List<Viaje> listaViajes = new ArrayList<>();
        for (int id : convertirIdsAEnteros(idsViajes)) {
            listaViajes.add(obtenerViajePorCodigo(id));
        }
        return listaViajes;
    }


    public List<Integer> convertirCadenaAListaDeIds(String idsViajes) {
        List<Integer> viajesIdList = new ArrayList<>();

        if (idsViajes != null && !idsViajes.isEmpty()) {
            String[] idArray = idsViajes.split(",");
            for (String id : idArray) {
                try {
                    viajesIdList.add(Integer.parseInt(id.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("ID inválido: " + id);
                }
            }
        }
        return viajesIdList;
    }

    public List<Viaje> obtenerViajesPorIds(String idsViajes) {
        List<Viaje> viajesList = new ArrayList<>();
        for (Integer idViaje : convertirCadenaAListaDeIds(idsViajes)) {
            viajesList.add(obtenerViajePorCodigo(idViaje));
        }

        return viajesList;
    }
}
