package entidadesVirtuales;

import java.math.BigDecimal;

/**
 *
 * @author lmarcoss
 */
//Es una vista en la base de datos con nombre COSTO_MADERA_CLASIFICACION
public class CostoMaderaClasificacion {
    private String id_madera;
    private BigDecimal grueso;
    private BigDecimal ancho;
    private BigDecimal largo;
    private BigDecimal volumen;
    private BigDecimal monto_volumen;

    public CostoMaderaClasificacion() {
    }

    public CostoMaderaClasificacion(String id_madera, BigDecimal grueso, BigDecimal ancho, BigDecimal largo, BigDecimal volumen, BigDecimal monto_volumen) {
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

    public void setMonto_volumen(BigDecimal monto_volumen) {
        this.monto_volumen = monto_volumen;
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

    public BigDecimal getMonto_volumen() {
        return monto_volumen;
    }

    public BigDecimal getVolumen() {
        return volumen;
    }
    
}
