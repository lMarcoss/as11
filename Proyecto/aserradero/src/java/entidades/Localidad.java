package entidades;

/**
 *
 * @author lmarcoss
 */
public class Localidad {
    
    public String nombre_localidad;
    public String nombre_municipio;
    public String telefono;

    public Localidad() {
    }

    public Localidad(String nombre_localidad, String nombre_municipio, String telefono) {
        this.nombre_localidad = nombre_localidad;
        this.nombre_municipio = nombre_municipio;
        this.telefono = telefono;
    }

    public void setNombre_localidad(String nombre_localidad) {
        this.nombre_localidad = nombre_localidad;
    }

    public void setNombre_municipio(String nombre_municipio) {
        this.nombre_municipio = nombre_municipio;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre_localidad() {
        return nombre_localidad;
    }

    public String getNombre_municipio() {
        return nombre_municipio;
    }

    public String getTelefono() {
        return telefono;
    }
    
}
