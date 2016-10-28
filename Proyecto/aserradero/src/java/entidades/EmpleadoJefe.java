package entidades;

/**
 *
 * @author lmarcoss
 */
public class EmpleadoJefe {
    public String id_empleado;
    public String id_jefe;

    public EmpleadoJefe() {
    }

    public EmpleadoJefe(String id_empleado, String id_jefe) {
        this.id_empleado = id_empleado;
        this.id_jefe = id_jefe;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setId_jefe(String id_jefe) {
        this.id_jefe = id_jefe;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public String getId_jefe() {
        return id_jefe;
    }
    
    
}
