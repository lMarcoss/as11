package entidades.registros;

/**
 *
 * @author lmarcoss
 */
public class Localidad {
    
    public String nombre_localidad;
    public String nombre_municipio;
    public String telefono_localidad;
    public String estado;

    public Localidad() {
    }

    public Localidad(String nombre_localidad, String nombre_municipio, String telefono_localidad, String estado) {
        this.nombre_localidad = nombre_localidad;
        this.nombre_municipio = nombre_municipio;
        this.telefono_localidad = telefono_localidad;
        this.estado = estado;
    }

    public void setNombre_localidad(String nombre_localidad) {
        this.nombre_localidad = nombre_localidad;
    }

    public void setNombre_municipio(String nombre_municipio) {
        this.nombre_municipio = nombre_municipio;
    }

    public void setTelefono_localidad(String telefono_localidad) {
        this.telefono_localidad = telefono_localidad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_localidad() {
        return nombre_localidad;
    }

    public String getNombre_municipio() {
        return nombre_municipio;
    }

    public String getTelefono_localidad() {
        return telefono_localidad;
    }

    public String getEstado() {
        return estado;
    }
    
}
