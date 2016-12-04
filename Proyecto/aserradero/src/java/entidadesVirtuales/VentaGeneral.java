/*
 * Entidad creada para consultar datos generales de cada tipo de venta
 * VENTA_EXTRA
 * VENTA_MAYOREO
 * VENTA_PAQUETE
 */
package entidadesVirtuales;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author lmarcoss
 */
public class VentaGeneral {
    // atributos generales de las ventas
    private String id_venta;
    private Date fecha;
    private String id_cliente;
    private String cliente;
    private String id_empleado;
    private String empleado;
    private String id_jefe;
    private String estatus;
    private String tipo_venta;
    private String tipo_pago;
    private BigDecimal monto; // Es el único atributo que se extrae de los tipos de ventas

    public VentaGeneral() {
    }

    public VentaGeneral(String id_venta, Date fecha, String id_cliente, String cliente, String id_empleado, String empleado, String id_jefe, String estatus, String tipo_venta, String tipo_pago, BigDecimal monto) {
        this.id_venta = id_venta;
        this.fecha = fecha;
        this.id_cliente = id_cliente;
        this.cliente = cliente;
        this.id_empleado = id_empleado;
        this.empleado = empleado;
        this.id_jefe = id_jefe;
        this.estatus = estatus;
        this.tipo_venta = tipo_venta;
        this.tipo_pago = tipo_pago;
        this.monto = monto;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setTipo_venta(String tipo_venta) {
        this.tipo_venta = tipo_venta;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getId_venta() {
        return id_venta;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public String getCliente() {
        return cliente;
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

    public String getEstatus() {
        return estatus;
    }

    public String getTipo_venta() {
        return tipo_venta;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public BigDecimal getMonto() {
        return monto;
    }
    
}
