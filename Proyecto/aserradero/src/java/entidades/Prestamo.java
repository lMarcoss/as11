package entidades;

import java.sql.Date;

/**
 *
 * @author lmarcoss
 */
public class Prestamo {
    private int id_prestamo;
    private Date fecha; 			
    private String id_persona;
    private String persona;
    private String id_administrador;
    private float monto;
    private int interes;
    private float interes_mesual;

    public Prestamo() {
    }

    public Prestamo(int id_prestamo, Date fecha, String id_persona, String persona, String id_administrador, float monto, int interes, float interes_mesual) {
        this.id_prestamo = id_prestamo;
        this.fecha = fecha;
        this.id_persona = id_persona;
        this.persona = persona;
        this.id_administrador = id_administrador;
        this.monto = monto;
        this.interes = interes;
        this.interes_mesual = interes_mesual;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public void setId_administrador(String id_administrador) {
        this.id_administrador = id_administrador;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setInteres(int interes) {
        this.interes = interes;
    }

    public void setInteres_mesual(float interes_mesual) {
        this.interes_mesual = interes_mesual;
    }

    public int getId_prestamo() {
        return id_prestamo;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getId_persona() {
        return id_persona;
    }

    public String getPersona() {
        return persona;
    }

    public String getId_administrador() {
        return id_administrador;
    }

    public float getMonto() {
        return monto;
    }

    public int getInteres() {
        return interes;
    }

    public float getInteres_mesual() {
        return interes_mesual;
    }
    
}
