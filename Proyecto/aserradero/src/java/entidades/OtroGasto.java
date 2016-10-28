package entidades;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */

 public class OtroGasto{
   public String id_gasto;    
   public String fecha;
   public String id_empleado;
   public String nombre_gasto;
   public Float monto;
   public String observacion;

   public OtroGasto(){

   }    
   public OtroGasto(String id_gasto, String fecha, String id_empleado, String nombre_gasto, Float monto, String observacion) {
        this.id_gasto = id_gasto;
        this.fecha = fecha;
        this.id_empleado = id_empleado;
        this.nombre_gasto = nombre_gasto;
        this.monto = monto;
        this.observacion = observacion;
    }

    public String getId_gasto() {
        return id_gasto;
    }

    public void setId_gasto(String id_gasto) {
        this.id_gasto = id_gasto;
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

    public String getNombre_gasto() {
        return nombre_gasto;
    }

    public void setNombre_gasto(String nombre_gasto) {
        this.nombre_gasto = nombre_gasto;
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
