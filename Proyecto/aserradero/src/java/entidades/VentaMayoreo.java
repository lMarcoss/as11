package entidades;

/**
 *
 * @author lmarcoss
 */
public class VentaMayoreo {
    private int id_venta;
    private String id_madera;
    private int num_piezas;
    private float volumen;
    private float monto;

    public VentaMayoreo() {
    }

    public VentaMayoreo(int id_venta, String id_madera, int num_piezas, float volumen, float monto) {
        this.id_venta = id_venta;
        this.id_madera = id_madera;
        this.num_piezas = num_piezas;
        this.volumen = volumen;
        this.monto = monto;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getId_venta() {
        return id_venta;
    }

    public String getId_madera() {
        return id_madera;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public float getVolumen() {
        return volumen;
    }

    public float getMonto() {
        return monto;
    }
}
