package Model.DAO;

import Model.Entity.*;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.*;

public class ReservaDAO extends GenericDAO {
    private static Map<Integer, Reserva> reservaDatabase = new HashMap<>();


    public ReservaDAO() {
        super();
    }

    public void guardarReserva(Reserva reserva, Viaje viaje) {
        try {
            beginTransaction();
            em.persist(reserva);
            actualizarAsientosOcupados(viaje, 1);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    private void actualizarAsientosOcupados(Viaje viaje, int incremento) {
        viaje.setAsientosOcupados(viaje.getAsientosOcupados() + incremento);
        em.merge(viaje);
    }

    public List<Reserva> obtenerTodasLasReservas() {
        List<Reserva> reservas = null;
        try {
            beginTransaction();
            TypedQuery<Reserva> query = em.createQuery("SELECT r FROM Reserva r", Reserva.class);
            reservas = query.getResultList();
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return reservas;
    }
    public Reserva obtenerReservaPorId(int reservaId) {
        Reserva reserva = null;
        try {
            beginTransaction();

            TypedQuery<Reserva> query = em.createQuery("SELECT r FROM Reserva r WHERE r.id = :reservaId", Reserva.class);
            query.setParameter("reservaId", reservaId);

            reserva = query.getSingleResult();
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return reserva;
    }
    public void cancelarReserva(int reservaId, Viaje viaje) {
        try {
            beginTransaction();
            Reserva reserva = em.find(Reserva.class, reservaId);
            em.remove(reserva);
            actualizarAsientosOcupados(viaje,-1);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }

    }
    public List<Reserva> obtenerReservasPorDia(int diaSeleccionado, Usuario usuario) {
        List<Reserva> reservasFiltradas = new ArrayList<>();
        for (Reserva  reserva : obtenerReservasPorEstudianteId(usuario.getId()))
        {
            int diaReserva = reserva.getViaje().getFecha().getDay();
            if(diaReserva == diaSeleccionado){
                reservasFiltradas.add(reserva);
            }
        }

        return reservasFiltradas;
    }
    public List<Reserva> obtenerReservasPorEstudianteId(int estudianteId) {
        List<Reserva> reservas = null;
        try {
            beginTransaction();
            TypedQuery<Reserva> query = em.createQuery("SELECT r FROM Reserva r WHERE r.estudiante.id = :estudianteId", Reserva.class);
            query.setParameter("estudianteId", estudianteId); // Configura el parámetro de la consulta
            reservas = query.getResultList();
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return reservas;
    }

    public void guardarVariasReservas(List<Viaje> listaViajes, Estudiante estudiante) {
        for (Viaje viaje : listaViajes) {
            Reserva reserva = new Reserva(0, viaje, estudiante, new Date(System.currentTimeMillis()));
            guardarReserva(reserva, viaje);
        }
    }


    public boolean verificarViajeVacio(Viaje viaje) {
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.viaje = :viaje", Long.class);
            query.setParameter("viaje", viaje);
            return query.getSingleResult() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Estudiante> listarPasajerosPorViaje(Viaje viaje) {
        List<Estudiante> pasajeros = new ArrayList<>();
        try {
            beginTransaction();
            // Consulta para obtener los estudiantes de un viaje
            TypedQuery<Estudiante> query = em.createQuery("SELECT r.estudiante FROM Reserva r WHERE r.viaje = :viaje", Estudiante.class);
            query.setParameter("viaje", viaje);
            pasajeros = query.getResultList();

            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return pasajeros;
    }

    public List<Estudiante> listarPasajerosPorViajeOrdenado(Viaje viaje) {
        List<Estudiante> pasajeros = new ArrayList<>();
        try {
            beginTransaction();

            // Consulta para obtener y ordenar los estudiantes por nombre en un viaje
            TypedQuery<Estudiante> query = em.createQuery("SELECT r.estudiante FROM Reserva r WHERE r.viaje = :viaje ORDER BY r.estudiante.nombre ASC", Estudiante.class);
            query.setParameter("viaje", viaje);
            pasajeros = query.getResultList();

            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return pasajeros;
    }
    public boolean existeReserva(int estudianteId, int viajeId) {
        boolean existe = false;
        try {
            beginTransaction();
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(r) FROM Reserva r WHERE r.estudiante.id = :estudianteId AND r.viaje.id = :viajeId",
                    Long.class
            );
            query.setParameter("estudianteId", estudianteId);
            query.setParameter("viajeId", viajeId);
            Long count = query.getSingleResult();
            existe = (count > 0);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
        return existe;
    }



    ////CODIGO PARA LAS PRUEBAS UNITARIAS.
    public void createReserva(Reserva reserva) {
        reservaDatabase.put(reserva.getId(), reserva);
    }


    public Reserva readReserva(int id) {
        return reservaDatabase.get(id);
    }


    public void deleteReserva(int id, boolean simulateError) {
        if (simulateError) {
            throw new RuntimeException("Error al eliminar la reserva");
        }
        reservaDatabase.remove(id);
    }


    public List<Reserva> getAllReservas(boolean simulateReadError) {
        if (simulateReadError) {
            throw new RuntimeException("Error al leer la base de datos");
        }
        return new ArrayList<>(reservaDatabase.values());
    }


    //Estas si van
    public boolean isViajeEmpty(Viaje viaje) {
        for (Reserva reserva : reservaDatabase.values()) {
            if (reserva.getViaje().equals(viaje)) {
                return false;
            }
        }
        return true;
    }

    public List<Estudiante> listPassengersByViaje(Viaje viaje) {
        List<Estudiante> pasajeros = new ArrayList<>();

        for (Reserva reserva : reservaDatabase.values()) {
            if (reserva.getViaje().equals(viaje)) {
                pasajeros.add(reserva.getEstudiante());
            }
        }
        return pasajeros;
    }
    public List<Estudiante> listPassengersByViajeSorted(Viaje viaje) {
        List<Estudiante> pasajeros = new ArrayList<>();

        for (Reserva reserva : reservaDatabase.values()) {
            if (reserva.getViaje().equals(viaje)) {
                pasajeros.add(reserva.getEstudiante());
            }
        }
        pasajeros.sort(Comparator.comparing(Estudiante::getNombre));

        return pasajeros;
    }







}
