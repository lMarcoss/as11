package entidades;

import java.math.BigDecimal;

/**
 *
 * @author lmarcoss
 */
public class CuentaPorCobrar {
    private String id_persona; // puede ser id_proveedor o id_cliente
    private String persona;// proveedor o cliente
    private BigDecimal monto;

    public CuentaPorCobrar() {
    }

    public CuentaPorCobrar(String id_persona, String persona, BigDecimal monto) {
        this.id_persona = id_persona;
        this.persona = persona;
        this.monto = monto;
    }

    public String getId_persona() {
        return id_persona;
    }

    public String getPersona() {
        return persona;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
