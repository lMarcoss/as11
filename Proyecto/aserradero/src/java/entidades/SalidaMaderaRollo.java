package entidades;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author lmarcoss
 */
public class SalidaMaderaRollo {
    private int id_salida;
    private String id_empleado;
    private String empleado;
    private String id_jefe;
    private Date fecha;
    private int num_piezas;
    private BigDecimal volumen_total;
    
    public SalidaMaderaRollo() {
    }

    public SalidaMaderaRollo(int id_salida, String id_empleado, String empleado, String id_jefe, Date fecha, int num_piezas, BigDecimal volumen_total) {
        this.id_salida = id_salida;
        this.id_empleado = id_empleado;
        this.empleado = empleado;
        this.id_jefe = id_jefe;
        this.fecha = fecha;
        this.num_piezas = num_piezas;
        this.volumen_total = volumen_total;
    }

    public void setId_salida(int id_salida) {
        this.id_salida = id_salida;
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

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setVolumen_total(BigDecimal volumen_total) {
        this.volumen_total = volumen_total;
    }

    public int getId_salida() {
        return id_salida;
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

    public Date getFecha() {
        return fecha;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public BigDecimal getVolumen_total() {
        return volumen_total;
    }
}
