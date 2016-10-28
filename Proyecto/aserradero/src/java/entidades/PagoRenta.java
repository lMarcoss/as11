package entidades;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */

 public class PagoRenta{
   public String id_pago_renta;
   public String fecha;
   public String nombre_persona;
   public String id_empleado;
   public Float monto;
   public String observacion;

   public PagoRenta(){

   }
   public PagoRenta(String id_pago_renta,String  fecha, String nombre_persona, String id_empleado, Float monto, String observacion){
     this.id_pago_renta = id_pago_renta;
     this.fecha =fecha;
     this.nombre_persona = nombre_persona;
     this.id_empleado = id_empleado;
     this.monto = monto;
     this.observacion = observacion;
   }
   public String getId_pago_renta() {
        return id_pago_renta;
    }

    public void setId_pago_renta(String id_pago_renta) {
        this.id_pago_renta = id_pago_renta;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre_persona() {
        return nombre_persona;
    }

    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
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
