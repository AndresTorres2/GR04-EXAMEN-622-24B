package Model.DAO;
import Model.Entity.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Object[] buscarUsuarioPorEmail(String email) {
        Object[] usuario = null;
        try {
            String sql = "SELECT tipo_usuario, id, apellidos, nombres, phone FROM usuarios WHERE email = :email";

            Query query = em.createNativeQuery(sql);
            query.setParameter("email", email);

            List<Object[]> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                usuario = resultList.get(0);
            }
        } catch (Exception e) {
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

    /*Obtener el id del Usuario basado en el email*/

    public String obtenerIdUsuario(String email) {
        String idUsuario = null;
        try {
            String sql = "SELECT id FROM usuarios WHERE email = :email";
            
            Query query = em.createNativeQuery(sql);
            query.setParameter("email", email);
            
            idUsuario = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idUsuario;
    }
    

   /* public Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }*/
}
