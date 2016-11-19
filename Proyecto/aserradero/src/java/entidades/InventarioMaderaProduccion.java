package entidades;

/**
 *
 * @author lmarcoss
 */
public class InventarioMaderaProduccion {
    private String id_administrador;
    private String id_madera;
    private int num_piezas;
    private float volumen_total;
    private float monto_total;

    public InventarioMaderaProduccion() {
    }

    public InventarioMaderaProduccion(String id_administrador, String id_madera, int num_piezas, float volumen_total, float monto_total) {
        this.id_administrador = id_administrador;
        this.id_madera = id_madera;
        this.num_piezas = num_piezas;
        this.volumen_total = volumen_total;
        this.monto_total = monto_total;
    }

    public void setId_administrador(String id_administrador) {
        this.id_administrador = id_administrador;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setVolumen_total(float volumen_total) {
        this.volumen_total = volumen_total;
    }

    public void setMonto_total(float monto_total) {
        this.monto_total = monto_total;
    }

    public String getId_administrador() {
        return id_administrador;
    }

    public String getId_madera() {
        return id_madera;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public float getVolumen_total() {
        return volumen_total;
    }

    public float getMonto_total() {
        return monto_total;
    }
    
}
