package entidades;

import java.sql.Date;

/**
 *
 * @author lmarcoss
 */
public class PagoCompra {
    private Date fecha;
    private String id_compra;
    private float monto;
    private String pago;

    public PagoCompra() {
    }

    public PagoCompra(Date fecha, String id_compra, String pago, float monto) {
        this.fecha = fecha;
        this.id_compra = id_compra;
        this.pago = pago;
        this.monto = monto;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId_compra(String id_compra) {
        this.id_compra = id_compra;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getId_compra() {
        return id_compra;
    }

    public String getPago() {
        return pago;
    }

    public float getMonto() {
        return monto;
    }
    
}
