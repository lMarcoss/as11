package dao;

import entidades.CuentaPorPagar;
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
public class CuentaPorPagarCRUD extends Conexion implements OperacionesCRUD {

    @Override
    public void registrar(Object objeto) throws Exception{
        CuentaPorPagar cuentaPorPagar = (CuentaPorPagar) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("INSERT INTO CUENTA_POR_PAGAR (id_cliente,monto) VALUES (?,?)");
            st = cargarObject(st, cuentaPorPagar);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List listar() throws Exception{
        List<CuentaPorPagar> cuentaPorPagares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_PAGAR_CLIENTE")) {
                cuentaPorPagares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorPagar cuentaPorPagar = (CuentaPorPagar) extraerObject(rs);
                        cuentaPorPagares.add(cuentaPorPagar);
                    }
                }
            }catch(Exception e){
                cuentaPorPagares = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return cuentaPorPagares;
    }

    @Override
    public Object modificar(Object objeto) throws Exception{
        return null;
    }

    @Override
    public void actualizar(Object objeto) throws Exception{
    }

    @Override
    public void eliminar(Object objeto) throws Exception{
        CuentaPorPagar cuentaPorPagar = (CuentaPorPagar) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM CUENTA_POR_PAGAR WHERE id_cliente = ?");
            st.setString(1,cuentaPorPagar.getId_persona());
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception{
        List<CuentaPorPagar> cuentaPorPagares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_PAGAR WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                cuentaPorPagares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorPagar cuentaPorPagar = (CuentaPorPagar) extraerObject(rs);
                        cuentaPorPagares.add(cuentaPorPagar);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return cuentaPorPagares;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        CuentaPorPagar cuentaPorPagar = new CuentaPorPagar();
        cuentaPorPagar.setId_persona(rs.getString("id_persona"));
        cuentaPorPagar.setPersona(rs.getString("persona"));
        cuentaPorPagar.setMonto(rs.getBigDecimal("monto"));
        return cuentaPorPagar;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objecto) throws SQLException {
        CuentaPorPagar cuentaPorPagar = (CuentaPorPagar) objecto;
        st.setString(1,cuentaPorPagar.getId_persona());    
        st.setBigDecimal(2,cuentaPorPagar.getMonto());
        return st;
    }
    public <T> List listarClientes() throws Exception{
        List<CuentaPorPagar> cuentaPorPagares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_PAGAR_CLIENTE")) {
                cuentaPorPagares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorPagar cuentaPorPagar = (CuentaPorPagar) extraerObject(rs);
                        cuentaPorPagares.add(cuentaPorPagar);
                    }
                }
            }catch(Exception e){
                cuentaPorPagares = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return cuentaPorPagares;
    }
    public <T> List listarProveedores() throws Exception{
        List<CuentaPorPagar> cuentaPorPagares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_PAGAR_PROVEEDOR")) {
                cuentaPorPagares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorPagar cuentaPorPagar = (CuentaPorPagar) extraerObject(rs);
                        cuentaPorPagares.add(cuentaPorPagar);
                    }
                }
            }catch(Exception e){
                cuentaPorPagares = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return cuentaPorPagares;
    }
}
