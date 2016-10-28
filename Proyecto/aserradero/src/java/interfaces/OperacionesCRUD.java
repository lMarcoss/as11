
package interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lmarcoss
 */
public interface OperacionesCRUD{
    //Parámetro genérico
    public void registrar(Object objeto) throws Exception;
    //Método genérico: Devolver una lista de cualquier tipo de objeto
    public <T> List listar() throws Exception;
    //Devuelve un objeto del tipo de objeto que recibe: Objeto genérico
    public Object modificar(Object objeto) throws Exception;
    public void actualizar(Object objeto) throws Exception;
    public void eliminar(Object objeto) throws Exception;
    //Método genérico: Puede devolver una lista de cualquier tipo de objeto
    public <T> List buscar(String nombre_campo, String dato) throws Exception;
    //Extraer datos del objeto
    public Object extraerObject(ResultSet rs) throws SQLException;
    //Cargar datos del objeto al PreparedStatement
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException;
}
