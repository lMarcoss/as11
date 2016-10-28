package dao;

import entidades.ProduccionDetalle;
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
public class ProduccionDetalleCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        ProduccionDetalle produccionDetalle = (ProduccionDetalle) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO PRODUCCION_DETALLE (id_produccion,id_madera,num_piezas) VALUES (?,?,?)");
            st = cargarObject(st, produccionDetalle);
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
        List<ProduccionDetalle> produccionDetalles;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PRODUCCION_DETALLE")) {
                produccionDetalles = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProduccionDetalle produccionDetalle = (ProduccionDetalle) extraerObject(rs);
                        produccionDetalles.add(produccionDetalle);
                    }
                }
            }catch(Exception e){
                produccionDetalles = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return produccionDetalles;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        ProduccionDetalle produccionDetalleM = (ProduccionDetalle) objeto;
        ProduccionDetalle produccionDetalle = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PRODUCCION_DETALLE WHERE id_produccion = ? AND id_madera  = ?")) {
                st.setString(1, produccionDetalleM.getId_produccion());
                st.setString(2, produccionDetalleM.getId_madera());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        produccionDetalle = (ProduccionDetalle) extraerObject(rs);
                    }
                }
            }
        return produccionDetalle;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        ProduccionDetalle produccionDetalle = (ProduccionDetalle) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE PRODUCCION_DETALLE SET num_piezas = ? WHERE id_produccion = ? AND id_madera = ? ");
            st.setInt(1,produccionDetalle.getNum_piezas());
            st.setString(2,produccionDetalle.getId_produccion());
            st.setString(3,produccionDetalle.getId_madera());
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public void eliminar(Object objeto) throws Exception {
        ProduccionDetalle produccionDetalle = (ProduccionDetalle) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM PRODUCCION_DETALLE WHERE id_produccion = ? AND id_madera = ?");
            st.setString(1,produccionDetalle.getId_produccion());
            st.setString(2,produccionDetalle.getId_madera());
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
        List<ProduccionDetalle> produccionDetalles;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PRODUCCION_DETALLE WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                produccionDetalles = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProduccionDetalle produccionDetalle = (ProduccionDetalle) extraerObject(rs);
                        produccionDetalles.add(produccionDetalle);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return produccionDetalles;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        ProduccionDetalle produccionDetalle = new ProduccionDetalle();
        produccionDetalle.setId_produccion(rs.getString("id_produccion"));
        produccionDetalle.setId_madera(rs.getString("id_madera"));
        produccionDetalle.setNum_piezas(rs.getInt("num_piezas"));
        return produccionDetalle;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        ProduccionDetalle produccionDetalle = (ProduccionDetalle) objeto;
        st.setString(1,produccionDetalle.getId_produccion());
        st.setString(2,produccionDetalle.getId_madera());
        st.setInt(3,produccionDetalle.getNum_piezas());
        return st;
    }
    
    public List<ProduccionDetalle> buscarPorId(String id_produccion) throws Exception {
        List<ProduccionDetalle> produccionDetalles;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PRODUCCION_DETALLE WHERE id_produccion = ? AND id_madera = ?")) {
                st.setString(1, id_produccion);
                produccionDetalles = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ProduccionDetalle produccionDetalle = (ProduccionDetalle) extraerObject(rs);
                        produccionDetalles.add(produccionDetalle);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return produccionDetalles;
    }
}
