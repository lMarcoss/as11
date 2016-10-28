package entidades;

/**
 *
 * @author lmarcoss
 */
public class MaderaClasificacion {
    
    private String id_madera;
    private float grueso;
    private float ancho;
    private float largo;
    private float volumen;

    public MaderaClasificacion() {
    }

    public MaderaClasificacion(String id_madera, float grueso, float ancho, float largo, float volumen) {
        this.id_madera = id_madera;
        this.grueso = grueso;
        this.ancho = ancho;
        this.largo = largo;
        this.volumen = volumen;
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

    public void setVolumen(float volumen) {
        this.volumen = volumen;
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

    public float getVolumen() {
        return volumen;
    }
    
}
