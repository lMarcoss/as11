package dao;

import entidades.VentaMayoreo;
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
public class VentaMayoreoCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        VentaMayoreo ventaMayoreo = (VentaMayoreo) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO VENTA_MAYOREO (id_venta,id_madera,num_piezas,volumen,monto) VALUES (?,?,?,?,?)");
            st = cargarObject(st, ventaMayoreo);
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
        List<VentaMayoreo> ventaMayoreos;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_MAYOREO")) {
                ventaMayoreos = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaMayoreo ventaMayoreo = (VentaMayoreo) extraerObject(rs);
                        ventaMayoreos.add(ventaMayoreo);
                    }
                }
            }catch(Exception e){
                ventaMayoreos = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return ventaMayoreos;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        VentaMayoreo ventaMayoreoM = (VentaMayoreo) objeto;
        VentaMayoreo ventaMayoreo = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_MAYOREO WHERE id_venta = ? AND id_madera = ? AND (id_venta NOT IN (SELECT id_venta FROM VENTA WHERE estatus = 'Pagado') OR length(?) = 18)" )) {
                st.setInt(1, ventaMayoreoM.getId_venta());
                st.setString(2, ventaMayoreoM.getId_madera());
                st.setString(3, "123456789123456789"); // Solamente los administradores tienen derecho a modificar  
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ventaMayoreo = (VentaMayoreo) extraerObject(rs);
                    }
                }
            }
        return ventaMayoreo;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        VentaMayoreo ventaMayoreo = (VentaMayoreo) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE VENTA_MAYOREO SET num_piezas = ?, volumen = ?, monto = ? WHERE id_venta = ? AND id_madera = ?");
            st.setInt(1,ventaMayoreo.getNum_piezas());
            st.setFloat(2,ventaMayoreo.getVolumen());
            st.setFloat(3,ventaMayoreo.getMonto());
            st.setInt(4,ventaMayoreo.getId_venta());
            st.setString(5,ventaMayoreo.getId_madera());
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
        VentaMayoreo ventaMayoreo = (VentaMayoreo) objeto;
        if(!ventaPagado(ventaMayoreo.getId_venta())){
            try{
                this.abrirConexion();
                PreparedStatement st= this.conexion.prepareStatement("DELETE FROM VENTA_MAYOREO WHERE id_venta = ? AND id_madera = ?");
                st.setInt(1,ventaMayoreo.getId_venta());
                st.setString(2,ventaMayoreo.getId_madera());
                st.executeUpdate();
            }catch(Exception e){
                System.out.println(e);
                throw e;
            }finally{
                this.cerrarConexion();
            }
        }
        
    }
    private boolean ventaPagado(int id_venta) throws Exception{
        boolean pagado = false;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT id_venta FROM VENTA WHERE estatus = ? AND id_venta = ?")) {
            st.setString(1, "Pagado");
            st.setInt(2, id_venta);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    pagado = true;
                }
            }
        }
        return pagado;
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<VentaMayoreo> ventaMayoreos;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_MAYOREO WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                ventaMayoreos = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaMayoreo ventaMayoreo = (VentaMayoreo) extraerObject(rs);
                        ventaMayoreos.add(ventaMayoreo);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return ventaMayoreos;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        VentaMayoreo ventaMayoreo = new VentaMayoreo();
        ventaMayoreo.setId_venta(rs.getInt("id_venta"));
        ventaMayoreo.setId_madera(rs.getString("id_madera"));
        ventaMayoreo.setNum_piezas(rs.getInt("num_piezas"));
        ventaMayoreo.setVolumen(rs.getFloat("volumen"));
        ventaMayoreo.setMonto(rs.getFloat("monto"));
        return ventaMayoreo;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        VentaMayoreo ventaMayoreo = (VentaMayoreo) objeto;
        st.setInt(1, ventaMayoreo.getId_venta());
        st.setString(2,ventaMayoreo.getId_madera());
        st.setFloat(3,ventaMayoreo.getNum_piezas());
        st.setFloat(4,ventaMayoreo.getVolumen());
        st.setFloat(5,ventaMayoreo.getMonto());
        return st;
    }
}
