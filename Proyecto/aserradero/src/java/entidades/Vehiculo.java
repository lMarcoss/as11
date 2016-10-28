package entidades;

/**
 *
 * @author Ricardo Cortés Cruz ->> ricardo.crts.crz@gmail.com
 */
 
 public class Vehiculo{
   private String id_vehiculo;
   private String matricula;
   private String tipo;
   private String color;
   private String carga_admitida;
   private String motor;
   private String modelo;
   private Float costo;
   private String id_empleado;
   private String empleado;

   public Vehiculo(){

   }

    public Vehiculo(String id_vehiculo, String matricula, String tipo, String color, String carga_admitida, String motor, String modelo, Float costo, String id_empleado, String empleado) {
        this.id_vehiculo = id_vehiculo;
        this.matricula = matricula;
        this.tipo = tipo;
        this.color = color;
        this.carga_admitida = carga_admitida;
        this.motor = motor;
        this.modelo = modelo;
        this.costo = costo;
        this.id_empleado = id_empleado;
        this.empleado = empleado;
    }



    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getCarga_admitida() {
        return carga_admitida;
    }

    public void setCarga_admitida(String carga_admitida) {
        this.carga_admitida = carga_admitida;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getEmpleado() {
        return empleado;
    }
    

 }
