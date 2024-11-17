package Model.DAO;
import Model.Entity.Administrador;
import Model.Entity.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;


public class UsuarioDAO extends GenericDAO{

    public UsuarioDAO() {
        super();
    }

    private static Map<String, Usuario> usuarios = new HashMap<>();

    public void guardarUsuario(Usuario usuario) {
        usuarios.put(String.valueOf(usuario.getId()), usuario);
    }

    public boolean validarCredenciales(String email, String contraseña) {
        boolean valido = false;
        try {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE email = :email AND contraseña = :contraseña";

            Query query = em.createNativeQuery(sql);
            query.setParameter("email", email);
            query.setParameter("contraseña", contraseña);

            Long count = ((Number) query.getSingleResult()).longValue();
            if (count > 0) {
                valido = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valido;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        Usuario usuario = null;
        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
            Query query = em.createQuery(jpql);
            query.setParameter("email", email);
            usuario = (Usuario) query.getSingleResult();
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }


    public String obtenerTipoDeUsuario(String email) {
        String tipoUsuario = null;
        try {
            String sql = "SELECT tipo_usuario FROM usuarios WHERE email = :email";

            Query query = em.createNativeQuery(sql);
            query.setParameter("email", email);

            tipoUsuario = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipoUsuario;
    }

    public void guardarUsuarioDb(Administrador usuario) {
        try {
            beginTransaction();
            em.persist(usuario);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }


   /* public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }*/

    public void eliminarAdministradorEnDB(int id) {
        try {
            beginTransaction();
            Administrador administrador = em.find(Administrador.class, id);
            if (administrador != null) {
                em.remove(administrador);
            }
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }



}
