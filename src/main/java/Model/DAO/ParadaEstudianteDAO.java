package Model.DAO;

import Model.Entity.ParadaEstudiante;
import Model.Entity.Reserva;
import jakarta.persistence.NoResultException;

public class ParadaEstudianteDAO extends GenericDAO {

    public ParadaEstudiante obtenerParadaPorReserva(Reserva reserva) {
        try {
            String query = "SELECT p FROM ParadaEstudiante p WHERE p.reserva = :reserva";
            return em.createQuery(query, ParadaEstudiante.class)
                    .setParameter("reserva", reserva)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void crearParada(ParadaEstudiante parada) {
        executeInTransaction(() -> {
            em.persist(parada);
        });
    }


    public void actualizarParada(ParadaEstudiante parada) {
        try {
            System.out.println("----------- PARADA DAO -------------");
            System.out.println("Actualizando parada: " + parada);
            em.getTransaction().begin();
            em.merge(parada);  // Realiza la actualización en la base de datos
            em.flush();        // Asegura que los cambios se persistan
            em.getTransaction().commit(); // Finaliza la transacción
            em.refresh(parada); // Sincroniza el objeto con los datos de la base de datos
            em.clear();         // Limpia el contexto de persistencia
            System.out.println("Parada actualizada: " + parada);
            System.out.println("------------------------");
        } catch (Exception e) {
            em.getTransaction().rollback(); // En caso de error, se revierte la transacción
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
