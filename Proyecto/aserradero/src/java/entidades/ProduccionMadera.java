package entidades;

import java.sql.Date;

/**
 *
 * @author lmarcoss
 */
public class ProduccionMadera {
    private Date fecha;
    private String id_produccion;
    private String id_empleado;

    public ProduccionMadera() {
    }

    public ProduccionMadera(Date fecha, String id_produccion, String id_empleado) {
        this.fecha = fecha;
        this.id_produccion = id_produccion;
        this.id_empleado = id_empleado;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId_produccion(String id_produccion) {
        this.id_produccion = id_produccion;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getId_produccion() {
        return id_produccion;
    }

    public String getId_empleado() {
        return id_empleado;
    }
    
}
