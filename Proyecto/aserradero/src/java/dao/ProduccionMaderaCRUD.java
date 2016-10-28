package dao;

import entidades.ProduccionMadera;
import interfaces.OperacionesCRUD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lmarcoss
 */
public class ProduccionMaderaCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        ProduccionMadera produccionMadera = (ProduccionMadera) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO PRODUCCION_MADERA (fecha,id_produccion,id_empleado) VALUES (?,?,?)");
            st = cargarObject(st, produccionMadera);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List listar() throws Exception {
        List<ProduccionMadera> produccionMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PRODUCCION_MADERA")) {
                produccionMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProduccionMadera produccionMadera = (ProduccionMadera) extraerObject(rs);
                        produccionMaderas.add(produccionMadera);
                    }
                }
            }catch(Exception e){
                produccionMaderas = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return produccionMaderas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        //no se va a modificar
        return null;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        //no se va a actualizar tabla
    }

    @Override
    public void eliminar(Object objeto) throws Exception {
        ProduccionMadera produccionMadera = (ProduccionMadera) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM PRODUCCION_MADERA WHERE id_produccion = ?");
            st.setString(1,produccionMadera.getId_produccion());
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<ProduccionMadera> produccionMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PRODUCCION_MADERA WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                produccionMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProduccionMadera produccionMadera = (ProduccionMadera) extraerObject(rs);
                        produccionMaderas.add(produccionMadera);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return produccionMaderas;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        ProduccionMadera produccionMadera = new ProduccionMadera();
        produccionMadera.setFecha(rs.getDate("fecha"));
        produccionMadera.setId_produccion(rs.getString("id_produccion"));
        produccionMadera.setId_empleado(rs.getString("id_empleado"));
        return produccionMadera;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        ProduccionMadera produccionMadera = (ProduccionMadera) objeto;
        st.setDate(1,produccionMadera.getFecha());
        st.setString(2,produccionMadera.getId_produccion());
        st.setString(3,produccionMadera.getId_empleado());
        return st;
    }
    
    public List<ProduccionMadera> buscarPorId(String id_produccion) throws Exception {
        List<ProduccionMadera> produccionMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PRODUCCION_MADERA WHERE id_produccion = ?")) {
                st.setString(1, id_produccion);
                produccionMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProduccionMadera produccionMadera = (ProduccionMadera) extraerObject(rs);
                        produccionMaderas.add(produccionMadera);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return produccionMaderas;
    }
}
