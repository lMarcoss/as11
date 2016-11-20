package entidades;

import java.sql.Date;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 * Modificado por: lmarcoss
 */

 public class EntradaMaderaRollo{
    private int id_entrada;
    private Date fecha;
    private String id_proveedor;
    private String proveedor;
    private String id_chofer;
    private String chofer;
    private String id_empleado;
    private String empleado;
    private String id_jefe;
    private int num_piezas;    
    private float volumen_primario;
    private float costo_primario;
    private float volumen_secundario;
    private float costo_secundario;
    private float volumen_terciario;
    private float costo_terciario;
    private String volumen_total;
    private String monto_total;
    private int id_pago;

    public EntradaMaderaRollo() {
    }

    public EntradaMaderaRollo(int id_entrada, Date fecha, String id_proveedor, String proveedor, String id_chofer, String chofer, String id_empleado, String empleado, String id_jefe, int num_piezas, float volumen_primario, float costo_primario, float volumen_secundario, float costo_secundario, float volumen_terciario, float costo_terciario, String volumen_total, String monto_total, int id_pago) {
        this.id_entrada = id_entrada;
        this.fecha = fecha;
        this.id_proveedor = id_proveedor;
        this.proveedor = proveedor;
        this.id_chofer = id_chofer;
        this.chofer = chofer;
        this.id_empleado = id_empleado;
        this.empleado = empleado;
        this.id_jefe = id_jefe;
        this.num_piezas = num_piezas;
        this.volumen_primario = volumen_primario;
        this.costo_primario = costo_primario;
        this.volumen_secundario = volumen_secundario;
        this.costo_secundario = costo_secundario;
        this.volumen_terciario = volumen_terciario;
        this.costo_terciario = costo_terciario;
        this.volumen_total = volumen_total;
        this.monto_total = monto_total;
        this.id_pago = id_pago;
    }

    public void setId_entrada(int id_entrada) {
        this.id_entrada = id_entrada;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public void setId_chofer(String id_chofer) {
        this.id_chofer = id_chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public void setId_jefe(String id_jefe) {
        this.id_jefe = id_jefe;
    }

    public void setNum_piezas(int num_piezas) {
        this.num_piezas = num_piezas;
    }

    public void setVolumen_primario(float volumen_primario) {
        this.volumen_primario = volumen_primario;
    }

    public void setCosto_primario(float costo_primario) {
        this.costo_primario = costo_primario;
    }

    public void setVolumen_secundario(float volumen_secundario) {
        this.volumen_secundario = volumen_secundario;
    }

    public void setCosto_secundario(float costo_secundario) {
        this.costo_secundario = costo_secundario;
    }

    public void setVolumen_terciario(float volumen_terciario) {
        this.volumen_terciario = volumen_terciario;
    }

    public void setCosto_terciario(float costo_terciario) {
        this.costo_terciario = costo_terciario;
    }

    public void setVolumen_total(String volumen_total) {
        this.volumen_total = volumen_total;
    }

    public void setMonto_total(String monto_total) {
        this.monto_total = monto_total;
    }

    public void setId_pago(int pago) {
        this.id_pago = id_pago;
    }

    public int getId_entrada() {
        return id_entrada;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getId_chofer() {
        return id_chofer;
    }

    public String getChofer() {
        return chofer;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public String getEmpleado() {
        return empleado;
    }

    public String getId_jefe() {
        return id_jefe;
    }

    public int getNum_piezas() {
        return num_piezas;
    }

    public float getVolumen_primario() {
        return volumen_primario;
    }

    public float getCosto_primario() {
        return costo_primario;
    }

    public float getVolumen_secundario() {
        return volumen_secundario;
    }

    public float getCosto_secundario() {
        return costo_secundario;
    }

    public float getVolumen_terciario() {
        return volumen_terciario;
    }

    public float getCosto_terciario() {
        return costo_terciario;
    }

    public String getVolumen_total() {
        return volumen_total;
    }

    public String getMonto_total() {
        return monto_total;
    }

    public int getId_pago() {
        return id_pago;
    }
    
 }
