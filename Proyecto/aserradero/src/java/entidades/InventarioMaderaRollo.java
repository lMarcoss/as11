package entidades;

import java.math.BigDecimal;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */
public class InventarioMaderaRollo {

    private String id_administrador;
    private int num_piezas;
    private BigDecimal volumen_total;

    public InventarioMaderaRollo() {

    }

    public InventarioMaderaRollo(String id_administrador, int num_piezas, BigDecimal volumen_total) {
        this.id_administrador = id_administrador;
        this.num_piezas = num_piezas;
        this.volumen_total = volumen_total;
    }

    public void setId_administrador(String id_administrador) {
        this.id_administrador = id_administrador;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setVolumen_total(BigDecimal volumen_total) {
        this.volumen_total = volumen_total;
    }

    public String getId_administrador() {
        return id_administrador;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public BigDecimal getVolumen_total() {
        return volumen_total;
    }

}
