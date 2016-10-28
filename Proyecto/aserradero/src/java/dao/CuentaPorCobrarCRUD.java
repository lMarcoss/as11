package dao;

import entidades.CuentaPorCobrar;
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
public class CuentaPorCobrarCRUD extends Conexion implements OperacionesCRUD{
@Override
    public void registrar(Object objeto) throws Exception{
        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("INSERT INTO CUENTA_POR_COBRAR (id_proveedor,monto) VALUES (?,?)");
            st = cargarObject(st, cuentaPorCobrar);
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
        List<CuentaPorCobrar> cuentaPorCobrares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_COBRAR")) {
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            }catch(Exception e){
                cuentaPorCobrares = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return cuentaPorCobrares;
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
        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = ?");
            st.setString(1,cuentaPorCobrar.getId_persona());
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
        List<CuentaPorCobrar> cuentaPorCobrares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_COBRAR WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return cuentaPorCobrares;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        CuentaPorCobrar cuentaPorCobrar = new CuentaPorCobrar();
        cuentaPorCobrar.setId_persona(rs.getString("id_persona"));
        cuentaPorCobrar.setPersona(rs.getString("persona"));
        cuentaPorCobrar.setMonto(rs.getFloat("monto"));        
        return cuentaPorCobrar;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objecto) throws SQLException {
        return st;
    }
    
    public <T> List listarCuentasPorCobrarProveedor() throws Exception{
        List<CuentaPorCobrar> cuentaPorCobrares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_COBRAR_PROVEEDOR")) {
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            }catch(Exception e){
                cuentaPorCobrares = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return cuentaPorCobrares;
    }
    public <T> List listarCuentasPorCobrarCliente() throws Exception{
        List<CuentaPorCobrar> cuentaPorCobrares;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_COBRAR_CLIENTE")) {
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            }catch(Exception e){
                cuentaPorCobrares = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return cuentaPorCobrares;
    }
}
