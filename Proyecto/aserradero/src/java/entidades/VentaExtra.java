package entidades;

/**
 *
 * @author lmarcoss
 */
public class VentaExtra {
    protected String id_venta;
    protected String tipo;
    protected float monto;
    protected String observacion;

    public VentaExtra() {
    }

    public VentaExtra(String id_venta, String tipo, float monto, String observacion) {
        this.id_venta = id_venta;
        this.tipo = tipo;
        this.monto = monto;
        this.observacion = observacion;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getId_venta() {
        return id_venta;
    }

    public String getTipo() {
        return tipo;
    }

    public float getMonto() {
        return monto;
    }

    public String getObservacion() {
        return observacion;
    }
    
    
}
