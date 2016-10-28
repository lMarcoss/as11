package entidades;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */

public class PagoLuz{
    public String id_pago_luz;
    public String fecha;
    public String id_empleado;
    public Float monto;
    public String observacion;

    public PagoLuz(){

    }
    public PagoLuz(String id_pago_luz, String fecha, String id_empleado, Float monto, String observacion){
     this.id_pago_luz = id_pago_luz;
     this.fecha = fecha;
     this.id_empleado = id_empleado;
     this.monto = monto;
     this.observacion = observacion;     
    }

    public String getId_pago_luz() {
        return id_pago_luz;
    }

    public void setId_pago_luz(String id_pago_luz) {
        this.id_pago_luz = id_pago_luz;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
