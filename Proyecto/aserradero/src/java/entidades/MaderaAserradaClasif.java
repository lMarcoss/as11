package entidades;

import java.math.BigDecimal;

/**
 *
 * @author lmarcoss
 */
public class MaderaAserradaClasif {
    
    private String id_madera;
    private BigDecimal grueso;
    private BigDecimal ancho;
    private BigDecimal largo;
    private BigDecimal volumen;
    private BigDecimal costo_por_volumen;

    public MaderaAserradaClasif() {
    }

    public MaderaAserradaClasif(String id_madera, BigDecimal grueso, BigDecimal ancho, BigDecimal largo, BigDecimal volumen, BigDecimal costo_por_volumen) {
        this.id_madera = id_madera;
        this.grueso = grueso;
        this.ancho = ancho;
        this.largo = largo;
        this.volumen = volumen;
        this.costo_por_volumen = costo_por_volumen;
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

    public void setCosto_por_volumen(BigDecimal costo_por_volumen) {
        this.costo_por_volumen = costo_por_volumen;
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

    public BigDecimal getCosto_por_volumen() {
        return costo_por_volumen;
    }
    
}
