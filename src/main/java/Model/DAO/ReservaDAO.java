package Model.DAO;

import Model.Entity.*;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ReservaDAO extends GenericDAO {



    public ReservaDAO() {
        super();
    }



    public void guardarReserva(Reserva reserva, Viaje viaje) {

        executeInTransaction(() -> {
            em.persist(reserva);
            actualizarAsientosOcupados(viaje, 1);
        });
    }

    private void executeInTransaction(Runnable action) {
        try {
            beginTransaction();
            action.run();
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
            throw new RuntimeException("Error al leer la base de datos");


        }
        return reservas;
    }
    private boolean forceReadError = false;
    public void setForceReadError(boolean forceReadError) {
        this.forceReadError = forceReadError;
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
            throw e;
        }
        return reserva;
    }

    public void cancelarReserva(int reservaId, Viaje viaje) {
        executeInTransaction(() -> {
            Reserva reserva = em.find(Reserva.class, reservaId);
            em.remove(reserva);
            actualizarAsientosOcupados(viaje, -1);
        });
    }



    public List<Reserva> obtenerReservasPorDia(int diaSeleccionado, Estudiante usuario) {
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
    public List<Reserva> obtenerReservasPorDiaYFechas(List<LocalDate> fechas, Estudiante estudiante,int diaSeleccionado) {
        List<Reserva> reservasList = new ArrayList<>();

        // Filtrar reservas por las fechas pasadas
        for (Reserva reserva : obtenerReservasPorDia(diaSeleccionado,estudiante)) {
            if (fechas.contains(reserva.getViaje().getFecha().toLocalDate())) {
                reservasList.add(reserva);
                System.out.println("Reserva: " + reserva + ", Fecha: " + reserva.getViaje().getFecha().toLocalDate());
            }
        }

        return reservasList;
    }












}
