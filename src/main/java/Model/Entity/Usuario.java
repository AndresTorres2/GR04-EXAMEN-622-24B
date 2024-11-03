package Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name= "Usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombres", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellido;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "contraseña", nullable = false)
    private String contrasena;

    public Usuario() {
    }
   public Usuario(int id, String nombre, String apellido, String email, String phone,String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.contrasena = contrasena;
   }
   public int getId() {
        return id;
   }
   public void setId(int id) {
        this.id = id;
   }
   public String getNombre() {
        return nombre;

   }
   public void setNombre(String nombre) {
        this.nombre = nombre;
   }
   public String getApellido() {
        return apellido;
   }
   public void setApellido(String apellido) {
        this.apellido = apellido;
   }
   public String getEmail() {
        return email;
   }
   public void setEmail(String email) {
        this.email = email;
   }
   public String getPhone() {
        return phone;
   }
   public void setPhone(String phone) {
        this.phone = phone;
   }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
