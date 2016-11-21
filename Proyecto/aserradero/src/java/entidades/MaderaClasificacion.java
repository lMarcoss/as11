package entidades;

import java.math.BigDecimal;

/**
 *
 * @author lmarcoss
 */
public class MaderaClasificacion {
    
    private String id_madera;
    private BigDecimal grueso;
    private BigDecimal ancho;
    private BigDecimal largo;
    private BigDecimal volumen;

    public MaderaClasificacion() {
    }

    public MaderaClasificacion(String id_madera, BigDecimal grueso, BigDecimal ancho, BigDecimal largo, BigDecimal volumen) {
        this.id_madera = id_madera;
        this.grueso = grueso;
        this.ancho = ancho;
        this.largo = largo;
        this.volumen = volumen;
    }


    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setGrueso(BigDecimal grueso) {
        this.grueso = grueso;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public void setVolumen(BigDecimal volumen) {
        this.volumen = volumen;
    }

    public String getId_madera() {
        return id_madera;
    }

    public BigDecimal getGrueso() {
        return grueso;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public BigDecimal getLargo() {
        return largo;
    }

    public BigDecimal getVolumen() {
        return volumen;
    }
    
}
