package entidades;

import java.math.BigDecimal;

/**
 *
 * @author lmarcoss
 */
public class CostoMadera {
    private String id_madera;
    private BigDecimal monto_volumen;

    public CostoMadera() {
    }

    public CostoMadera(String id_madera, BigDecimal monto_volumen) {
        this.id_madera = id_madera;
        this.monto_volumen = monto_volumen;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setMonto_volumen(BigDecimal monto_volumen) {
        this.monto_volumen = monto_volumen;
    }

    public String getId_madera() {
        return id_madera;
    }

    public BigDecimal getMonto_volumen() {
        return monto_volumen;
    }
    
}
