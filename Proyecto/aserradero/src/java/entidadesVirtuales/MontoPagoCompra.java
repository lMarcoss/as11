package entidadesVirtuales;

/**
 *
 * @author lmarcoss
 */
public class MontoPagoCompra {
    private String id_proveedor;
    private String proveedor;
    private float monto_por_pagar;
    private String id_administrador;

    public MontoPagoCompra() {
    }

    public MontoPagoCompra(String id_proveedor, String proveedor, float monto_por_pagar, String id_administrador) {
        this.id_proveedor = id_proveedor;
        this.proveedor = proveedor;
        this.monto_por_pagar = monto_por_pagar;
        this.id_administrador = id_administrador;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public void setMonto_por_pagar(float monto_por_pagar) {
        this.monto_por_pagar = monto_por_pagar;
    }

    public void setId_administrador(String id_administrador) {
        this.id_administrador = id_administrador;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public float getMonto_por_pagar() {
        return monto_por_pagar;
    }

    public String getId_administrador() {
        return id_administrador;
    }
    
}
