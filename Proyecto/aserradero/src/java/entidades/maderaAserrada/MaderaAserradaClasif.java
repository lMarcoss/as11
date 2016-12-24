package entidades.maderaAserrada;

import java.math.BigDecimal;

/**
 *
 * @author lmarcoss
 */
public class MaderaAserradaClasif {

    private String id_administrador;
    private String id_empleado;
    private String empleado;
    private String id_madera;
    private BigDecimal grueso;
    private BigDecimal ancho;
    private BigDecimal largo;
    private BigDecimal volumen;
    private BigDecimal costo_por_volumen;

    public MaderaAserradaClasif() {
    }

    public MaderaAserradaClasif(String id_administrador, String id_empleado, String empleado, String id_madera, BigDecimal grueso, BigDecimal ancho, BigDecimal largo, BigDecimal volumen, BigDecimal costo_por_volumen) {
        this.id_administrador = id_administrador;
        this.id_empleado = id_empleado;
        this.empleado = empleado;
        this.id_madera = id_madera;
        this.grueso = grueso;
        this.ancho = ancho;
        this.largo = largo;
        this.volumen = volumen;
        this.costo_por_volumen = costo_por_volumen;
    }

    public void setId_administrador(String id_administrador) {
        this.id_administrador = id_administrador;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
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

    public String getId_administrador() {
        return id_administrador;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public String getEmpleado() {
        return empleado;
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
