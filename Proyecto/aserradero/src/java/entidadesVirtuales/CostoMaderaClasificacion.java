package entidadesVirtuales;

/**
 *
 * @author lmarcoss
 */
//Es una vista en la base de datos con nombre COSTO_MADERA_CLASIFICACION
public class CostoMaderaClasificacion {
    private String id_madera;
    private Float grueso;
    private Float ancho;
    private Float largo;
    private Float volumen;
    private float monto_volumen;

    public CostoMaderaClasificacion() {
    }

    public CostoMaderaClasificacion(String id_madera, Float grueso, Float ancho, Float largo, Float volumen, float monto_volumen) {
        this.id_madera = id_madera;
        this.grueso = grueso;
        this.ancho = ancho;
        this.largo = largo;
        this.volumen = volumen;
        this.monto_volumen = monto_volumen;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setGrueso(Float grueso) {
        this.grueso = grueso;
    }

    public void setAncho(Float ancho) {
        this.ancho = ancho;
    }

    public void setLargo(Float largo) {
        this.largo = largo;
    }

    public void setVolumen(Float volumen) {
        this.volumen = volumen;
    }

    public void setMonto_volumen(float monto_volumen) {
        this.monto_volumen = monto_volumen;
    }

    public String getId_madera() {
        return id_madera;
    }

    public Float getGrueso() {
        return grueso;
    }

    public Float getAncho() {
        return ancho;
    }

    public Float getLargo() {
        return largo;
    }

    public float getMonto_volumen() {
        return monto_volumen;
    }

    public Float getVolumen() {
        return volumen;
    }
    
}
