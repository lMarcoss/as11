package dao;

import entidades.PagoCompra;
import interfaces.OperacionesCRUD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos
 */
public class PagoCompraCRUD extends Conexion implements OperacionesCRUD {
@Override
    public void registrar(Object objeto) throws Exception {
        PagoCompra pagoCompra = (PagoCompra) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO PAGO_COMPRA (fecha,id_compra,monto) VALUES (?,?,?)");
            st = cargarObject(st, pagoCompra);
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
        List<PagoCompra> pagoCompras;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PAGO_COMPRA")) {
                pagoCompras = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        PagoCompra pagoCompra = (PagoCompra) extraerObject(rs);
                        pagoCompras.add(pagoCompra);
                    }
                }
            }catch(Exception e){
                pagoCompras = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return pagoCompras;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        PagoCompra pagoCompraM = (PagoCompra) objeto;
        PagoCompra pagoCompra = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PAGO_COMPRA WHERE fecha = ? AND id_compra  = ?")) {
                st.setDate(1, pagoCompraM.getFecha());
                st.setString(2, pagoCompraM.getId_compra());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        pagoCompra = (PagoCompra) extraerObject(rs);
                    }
                }
            }
        return pagoCompra;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        PagoCompra pagoCompra = (PagoCompra) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE PAGO_COMPRA SET monto = ?, pago=? WHERE fecha = ? AND id_compra = ? ");
            st.setFloat(1,pagoCompra.getMonto());
            st.setString(2,pagoCompra.getPago());
            st.setDate(3,pagoCompra.getFecha());
            st.setString(4,pagoCompra.getId_compra());
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
        PagoCompra pagoCompra = (PagoCompra) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM PAGO_COMPRA WHERE id_compra = ? and fecha=?");
            st.setString(1,pagoCompra.getId_compra());
            st.setDate(2,pagoCompra.getFecha());
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
        List<PagoCompra> pagoCompras;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PAGO_COMPRA WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                pagoCompras = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        PagoCompra pagoCompra = (PagoCompra) extraerObject(rs);
                        pagoCompras.add(pagoCompra);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return pagoCompras;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        PagoCompra pagoCompra = new PagoCompra();
        pagoCompra.setFecha(rs.getDate("fecha"));
        pagoCompra.setId_compra(rs.getString("id_compra"));
        pagoCompra.setMonto(rs.getFloat("monto"));
        pagoCompra.setPago(rs.getString("pago"));
        return pagoCompra;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        PagoCompra pagoCompra = (PagoCompra) objeto;
        st.setDate(1,pagoCompra.getFecha());
        st.setString(2,pagoCompra.getId_compra());
        st.setFloat(3,pagoCompra.getMonto());
        return st;
    }
    public boolean consultaProveedor (PagoCompra pagoCompra) throws Exception{
        boolean existe=false;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT CONSULTA_PROVEEDOR (?)")) {
                st.setString(1,pagoCompra.getId_compra());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        existe = rs.getInt(1)==1;
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return existe;
    }
    
    public boolean pagarCompra (PagoCompra pagoCompra) throws Exception{
        boolean pago=false;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT PAGAR_COMPRA (?,?)")) {
                st.setString(1,pagoCompra.getId_compra());
                st.setFloat(2,pagoCompra.getMonto());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        if(rs.getInt(1)==1){
                            pago=true;
                        }else{
                            pago=false;
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return pago;
    }
}
