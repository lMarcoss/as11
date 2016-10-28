package dao;

import entidades.DetalleCompra;
import interfaces.OperacionesCRUD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rcortes
 */
public class DetalleCompraCRUD extends Conexion implements OperacionesCRUD{
    @Override
    public void registrar(Object objeto) throws Exception {
        DetalleCompra detallecompra = (DetalleCompra) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(""
                    + "INSERT INTO DETALLE_COMPRA (id_compra,clasificacion, volumen, monto) values(?,?,?,?)");
            st = cargarObject(st, detallecompra);
            st.executeUpdate();
        }catch(Exception e){
            System.err.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List listar() throws Exception {
        List<DetalleCompra> detallecompras;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM DETALLE_COMPRA;")){
                detallecompras = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        DetalleCompra detallecompra = (DetalleCompra) extraerObject(rs);
                        detallecompras.add(detallecompra);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                detallecompras = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            cerrarConexion();
        }
        return detallecompras;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        DetalleCompra detallecompraM = (DetalleCompra) objeto;
        DetalleCompra detallecompra = null;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM DETALLE_COMPRA WHERE id_compra = ? AND clasificacion = ?")) {
            st.setString(1, detallecompraM.getId_compra());
            st.setString(2, detallecompraM.getClasificacion());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    detallecompra = (DetalleCompra) extraerObject(rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return detallecompra;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        DetalleCompra detallecompra = (DetalleCompra) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE DETALLE_COMPRA SET volumen = ?, monto = ? where id_compra=? AND clasificacion = ?");
            st.setFloat(1, detallecompra.getVolumen());
            st.setFloat(2, detallecompra.getMonto());            
            st.setString(3, detallecompra.getId_compra()); 
            st.setString(4, detallecompra.getClasificacion());
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
        DetalleCompra detallecompra = (DetalleCompra) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM DETALLE_COMPRA WHERE id_compra = ? AND clasificacion= ?");
            st.setString(1, detallecompra.getId_compra());
            st.setString(2, detallecompra.getClasificacion());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<DetalleCompra> detallecompras;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM DETALLE_COMPRA WHERE "+nombre_campo+" like ?")){
                st.setString(1, "%"+dato+"%");
                detallecompras = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        DetalleCompra detallecompra = (DetalleCompra) extraerObject(rs);
                        detallecompras.add(detallecompra);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return detallecompras;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        DetalleCompra detallecompra = new DetalleCompra();
        detallecompra.setId_compra(rs.getString("id_compra"));
        detallecompra.setClasificacion(rs.getString("clasificacion"));        
        detallecompra.setVolumen(Float.valueOf(rs.getString("volumen")));        
        detallecompra.setMonto(Float.valueOf(rs.getString("monto")));        
        return detallecompra;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        DetalleCompra detallecompra = (DetalleCompra) objeto;
        st.setString(1, detallecompra.getId_compra());
        st.setString(2, detallecompra.getClasificacion());
        st.setString(3,String.valueOf(detallecompra.getVolumen()));
        st.setString(4,String.valueOf(detallecompra.getMonto()));
        return st;
    }
}
