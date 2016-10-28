package entidades;

/**
 *
 * @author lmarcoss
 */
public class ProduccionDetalle {
    
    private String id_produccion;
    private String id_madera;
    private int num_piezas;

    public ProduccionDetalle() {
    }

    public ProduccionDetalle(String id_produccion, String id_madera, int num_piezas) {
        this.id_produccion = id_produccion;
        this.id_madera = id_madera;
        this.num_piezas = num_piezas;
    }


    public void setId_produccion(String id_produccion) {
        this.id_produccion = id_produccion;
    }

    public void setId_madera(String id_madera) {
        this.id_madera = id_madera;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }
    
    public String getId_produccion() {
        return id_produccion;
    }

    public String getId_madera() {
        return id_madera;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

}
