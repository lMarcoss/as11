package entidades;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */

 public class InventarioMaderaEntrada{
   private int num_piezas;
   private float volumen_total;

   public InventarioMaderaEntrada(){

   }

    public InventarioMaderaEntrada(int num_piezas, float volumen_total) {
        this.num_piezas = num_piezas;
        this.volumen_total = volumen_total;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setVolumen_total(float volumen_total) {
        this.volumen_total = volumen_total;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public float getVolumen_total() {
        return volumen_total;
    }

 }
