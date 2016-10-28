package entidades;

import java.sql.Date;

/**
 *
 * @author lmarcoss
 */
public class Venta {
    public Date fecha;
    public int id_venta;
    public String id_cliente;
    public String id_empleado;
    public String estatus;
    private String tipo_venta;
    private String tipo_pago;

    public Venta() {
    }

    public Venta(Date fecha, int id_venta, String id_cliente, String id_empleado, String estatus, String tipo_venta, String tipo_pago) {
        this.fecha = fecha;
        this.id_venta = id_venta;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
        this.estatus = estatus;
        this.tipo_venta = tipo_venta;
        this.tipo_pago = tipo_pago;
    }


    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setTipo_venta(String tipo_venta) {
        this.tipo_venta = tipo_venta;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getId_venta() {
        return id_venta;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getTipo_venta() {
        return tipo_venta;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }
    
    
}
