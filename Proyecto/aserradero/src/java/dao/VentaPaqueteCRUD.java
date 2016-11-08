package dao;

import entidades.VentaPaquete;
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
public class VentaPaqueteCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        VentaPaquete ventaPaquete = (VentaPaquete) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO VENTA_PAQUETE (id_venta,numero_paquete,id_madera,num_piezas,volumen,monto) VALUES (?,?,?,?,?,?)");
            st = cargarObject(st, ventaPaquete);
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
        List<VentaPaquete> ventaPaquetes;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_PAQUETE")) {
                ventaPaquetes = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaPaquete ventaPaquete = (VentaPaquete) extraerObject(rs);
                        ventaPaquetes.add(ventaPaquete);
                    }
                }
            }catch(Exception e){
                ventaPaquetes = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return ventaPaquetes;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        VentaPaquete ventaPaqueteM = (VentaPaquete) objeto;
        VentaPaquete ventaPaquete = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement(
                    "SELECT * FROM VENTA_PAQUETE WHERE id_venta = ? AND numero_paquete = ? AND id_madera = ? AND (id_venta NOT IN (SELECT id_venta FROM VENTA WHERE estatus = 'Pagado') OR length(?) = 18)")) {
                st.setString(1, ventaPaqueteM.getId_venta());
                st.setInt(2, ventaPaqueteM.getNumero_paquete());
                st.setString(3, ventaPaqueteM.getId_madera());
                st.setString(4, "123456789123456789"); // Solamente los administradores tienen derecho a modificar  
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ventaPaquete = (VentaPaquete) extraerObject(rs);
                    }
                }
            }
        return ventaPaquete;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        VentaPaquete ventaPaquete = (VentaPaquete) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement(
                    "UPDATE VENTA_PAQUETE SET num_piezas = ?, volumen = ?, monto = ? WHERE id_venta = ? AND numero_paquete = ? AND id_madera = ?");
            st.setInt(1,ventaPaquete.getNum_piezas());
            st.setFloat(2,ventaPaquete.getVolumen());
            st.setFloat(3,ventaPaquete.getMonto());
            st.setString(4,ventaPaquete.getId_venta());
            st.setInt(5,ventaPaquete.getNumero_paquete());
            st.setString(6,ventaPaquete.getId_madera());
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
        VentaPaquete ventaPaquete = (VentaPaquete) objeto;
        if(!ventaPagado(ventaPaquete.getId_venta())){
            try{
                this.abrirConexion();
                PreparedStatement st= this.conexion.prepareStatement(
                        "DELETE FROM VENTA_PAQUETE WHERE id_venta = ? AND numero_paquete = ? AND id_madera = ?");
                st.setString(1,ventaPaquete.getId_venta());
                st.setInt(2,ventaPaquete.getNumero_paquete());
                st.setString(3,ventaPaquete.getId_madera());
                st.executeUpdate();
            }catch(Exception e){
                System.out.println(e);
                throw e;
            }finally{
                this.cerrarConexion();
            }
        }
    }
    
    private boolean ventaPagado(String id_venta) throws Exception{
        boolean pagado = false;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT id_venta FROM VENTA WHERE estatus = ? AND id_venta = ?")) {
            st.setString(1, "Pagado");
            st.setString(2, id_venta);
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
        List<VentaPaquete> ventaPaquetes;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_PAQUETE WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                ventaPaquetes = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaPaquete ventaPaquete = (VentaPaquete) extraerObject(rs);
                        ventaPaquetes.add(ventaPaquete);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return ventaPaquetes;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        VentaPaquete ventaPaquete = new VentaPaquete();
        ventaPaquete.setId_venta(rs.getString("id_venta"));
        ventaPaquete.setNumero_paquete(rs.getInt("numero_paquete"));
        ventaPaquete.setId_madera(rs.getString("id_madera"));
        ventaPaquete.setNum_piezas(rs.getInt("num_piezas"));
        ventaPaquete.setVolumen(rs.getFloat("volumen"));
        ventaPaquete.setMonto(rs.getFloat("monto"));
        return ventaPaquete;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        VentaPaquete ventaPaquete = (VentaPaquete) objeto;
        st.setString(1, ventaPaquete.getId_venta());
        st.setInt(2,ventaPaquete.getNumero_paquete());
        st.setString(3,ventaPaquete.getId_madera());
        st.setFloat(4,ventaPaquete.getNum_piezas());
        st.setFloat(5,ventaPaquete.getVolumen());
        st.setFloat(6,ventaPaquete.getMonto());
        return st;
    }
}
