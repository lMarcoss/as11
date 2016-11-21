package entidades;

import java.math.BigDecimal;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */

 public class ClasificacionMaderaEntrada{
   public String clasificacion;
   public BigDecimal costo;

   public ClasificacionMaderaEntrada(){
       
   }
   
    public ClasificacionMaderaEntrada(String clasificacion, BigDecimal costo) {
        this.clasificacion = clasificacion;
        this.costo = costo;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    
   
 }
