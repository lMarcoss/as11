package entidades;

import java.sql.Date;

/**
 *
 * @author lmarcoss
 */
public class ProduccionMadera {
    private int id_produccion;
    private Date fecha;
    private String id_madera;
    private int num_piezas;
    private String id_empleado;
    private String empleado;
    private String id_jefe;

    public ProduccionMadera() {
    }

    public ProduccionMadera(int id_produccion, Date fecha, String id_madera, int num_piezas, String id_empleado, String empleado, String id_jefe) {
        this.id_produccion = id_produccion;
        this.fecha = fecha;
        this.id_madera = id_madera;
        this.num_piezas = num_piezas;
        this.id_empleado = id_empleado;
        this.empleado = empleado;
        this.id_jefe = id_jefe;
    }

    public void setId_produccion(int id_produccion) {
        this.id_produccion = id_produccion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public void setId_jefe(String id_jefe) {
        this.id_jefe = id_jefe;
    }

    public int getId_produccion() {
        return id_produccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getId_madera() {
        return id_madera;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public String getEmpleado() {
        return empleado;
    }

    public String getId_jefe() {
        return id_jefe;
    }

}
