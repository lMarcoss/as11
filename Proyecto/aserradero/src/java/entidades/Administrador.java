package entidades;

/**
 *
 * @author lmarcoss
 */
public class Administrador {
    private String id_administrador;
    private String nombre;

    public Administrador() {
    }

    public Administrador(String id_administrador, String nombre) {
        this.id_administrador = id_administrador;
        this.nombre = nombre;
    }

    public void setId_administrador(String id_administrador) {
        this.id_administrador = id_administrador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_administrador() {
        return id_administrador;
    }

    public String getNombre() {
        return nombre;
    }
    
}
