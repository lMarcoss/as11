package entidades;

/**
 *
 * @author lmarcoss
 */
public class Usuario {
    public String id_empleado;
    public String nombre_usuario;
    public String contrasenia;
    public String email;

    public Usuario() {
    }

    public Usuario(String id_empleado, String nombre_usuario, String contrasenia, String email) {
        this.id_empleado = id_empleado;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.email = email;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getEmail() {
        return email;
    }
   
}
