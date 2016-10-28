package ticketVenta;

import java.util.List;

/**
 *
 * @author lmarcoss
 */
public class Paquete {
    private int numero_paquete;
    List<Madera> listaMadera;
    private float monto_total_paquete;

    public Paquete() {
    }

    public Paquete(int numero_paquete, List<Madera> listaMadera, float monto_total_paquete) {
        this.numero_paquete = numero_paquete;
        this.listaMadera = listaMadera;
        this.monto_total_paquete = monto_total_paquete;
    }

    public void setNumero_paquete(int numero_paquete) {
        this.numero_paquete = numero_paquete;
    }

    public void setListaMadera(List<Madera> listaMadera) {
        this.listaMadera = listaMadera;
    }

    public void setMonto_total_paquete(float monto_total_paquete) {
        this.monto_total_paquete = monto_total_paquete;
    }

    public int getNumero_paquete() {
        return numero_paquete;
    }

    public List<Madera> getListaMadera() {
        return listaMadera;
    }

    public float getMonto_total_paquete() {
        return monto_total_paquete;
    }

}
