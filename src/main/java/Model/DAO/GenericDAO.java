package Model.DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class GenericDAO {
    private static EntityManagerFactory emf;
    public EntityManager em;

    static {
        emf = Persistence.createEntityManagerFactory("persistencia");
    }
    public GenericDAO() {

        this.em = emf.createEntityManager();
    }
    public void beginTransaction() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    public void commitTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    public void rollbackTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
    public void openEntityManager() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
    }


}
