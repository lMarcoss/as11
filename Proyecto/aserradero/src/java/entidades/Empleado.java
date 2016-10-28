package entidades;

/**
 *
 * @author lmarcoss
 */
public class Empleado {
    private String id_empleado;
    private String id_persona;
    private String empleado;
    private String id_jefe;
    private String jefe;
    private String roll;
    private String estatus;

    public Empleado() {
    }

    public Empleado(String id_empleado, String id_persona, String empleado, String id_jefe, String jefe, String roll, String estatus) {
        this.id_empleado = id_empleado;
        this.id_persona = id_persona;
        this.empleado = empleado;
        this.id_jefe = id_jefe;
        this.jefe = jefe;
        this.roll = roll;
        this.estatus = estatus;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public void setId_jefe(String id_jefe) {
        this.id_jefe = id_jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public String getEmpleado() {
        return empleado;
    }

    public String getId_persona() {
        return id_persona;
    }

    public String getId_jefe() {
        return id_jefe;
    }

    public String getJefe() {
        return jefe;
    }

    public String getRoll() {
        return roll;
    }

    public String getEstatus() {
        return estatus;
    }

    
}
