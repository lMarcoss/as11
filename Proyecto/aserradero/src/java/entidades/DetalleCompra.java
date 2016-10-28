package entidades;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */

  public class DetalleCompra{
    public String id_compra;
    public String clasificacion;
    public Float volumen;
    public Float monto;
    
    public DetalleCompra(){
        
    }
    
    public DetalleCompra(String id_compra, String clasificacion, Float volumen, Float monto) {
        this.id_compra = id_compra;
        this.clasificacion = clasificacion;
        this.volumen = volumen;
        this.monto = monto;
    }

    public String getId_compra() {
        return id_compra;
    }

    public void setId_compra(String id_compra) {
        this.id_compra = id_compra;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Float getVolumen() {
        return volumen;
    }

    public void setVolumen(Float volumen) {
        this.volumen = volumen;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }
  }
