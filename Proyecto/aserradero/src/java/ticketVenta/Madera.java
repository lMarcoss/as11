package ticketVenta;

/**
 *
 * @author lmarcoss
 */
public class Madera {
    private String id_madera;
    private float  grueso;
    private float  ancho;
    private float  largo;
    private float  volumen_unitario;
    private int num_piezas;
    private float costo_volumen;
    private float volumen_total;
    private float costo_total;

    public Madera() {
    }

    public Madera(String id_madera, float grueso, float ancho, float largo, float volumen_unitario, int num_piezas, float costo_volumen, float volumen_total, float costo_total) {
        this.id_madera = id_madera;
        this.grueso = grueso;
        this.ancho = ancho;
        this.largo = largo;
        this.volumen_unitario = volumen_unitario;
        this.num_piezas = num_piezas;
        this.costo_volumen = costo_volumen;
        this.volumen_total = volumen_total;
        this.costo_total = costo_total;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setGrueso(float grueso) {
        this.grueso = grueso;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public void setLargo(float largo) {
        this.largo = largo;
    }

    public void setVolumen_unitario(float volumen_unitario) {
        this.volumen_unitario = volumen_unitario;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setCosto_volumen(float costo_volumen) {
        this.costo_volumen = costo_volumen;
    }

    public void setVolumen_total(float volumen_total) {
        this.volumen_total = volumen_total;
    }

    public void setCosto_total(float costo_total) {
        this.costo_total = costo_total;
    }

    public String getId_madera() {
        return id_madera;
    }

    public float getGrueso() {
        return grueso;
    }

    public float getAncho() {
        return ancho;
    }

    public float getLargo() {
        return largo;
    }

    public float getVolumen_unitario() {
        return volumen_unitario;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public float getCosto_volumen() {
        return costo_volumen;
    }

    public float getVolumen_total() {
        return volumen_total;
    }

    public float getCosto_total() {
        return costo_total;
    }
    
}
