package dao;

import entidades.CuentaPorCobrar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos
 */
public class CuentaPorCobrarCRUD extends Conexion {

    public <T> List listar() throws Exception {
        List<CuentaPorCobrar> cuentaPorCobrares;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM CUENTA_POR_COBRAR")) {
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            } catch (Exception e) {
                cuentaPorCobrares = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return cuentaPorCobrares;
    }

    // Buscar Cuenta por cobrar persona
    public <T> List buscarCCProveedor(String nombre_campo, String dato) throws Exception {
        List<CuentaPorCobrar> cuentaPorCobrares;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM C_POR_COBRAR_PROVEEDOR WHERE " + nombre_campo + " like ?")) {
                st.setString(1, "%" + dato + "%");
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return cuentaPorCobrares;
    }

    // Buscar Cuenta por cobrar cliente
    public <T> List buscarCCCliente(String nombre_campo, String dato) throws Exception {
        List<CuentaPorCobrar> cuentaPorCobrares;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM C_POR_COBRAR_PROVEEDOR WHERE " + nombre_campo + " like ?")) {
                st.setString(1, "%" + dato + "%");
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return cuentaPorCobrares;
    }

    public Object extraerObject(ResultSet rs) throws SQLException {
        CuentaPorCobrar cuentaPorCobrar = new CuentaPorCobrar();
        cuentaPorCobrar.setId_persona(rs.getString("id_persona"));
        cuentaPorCobrar.setPersona(rs.getString("persona"));
        cuentaPorCobrar.setId_jefe(rs.getString("id_jefe"));
        cuentaPorCobrar.setMonto(rs.getBigDecimal("monto"));
        return cuentaPorCobrar;
    }

    public <T> List listarCuentasPorCobrarProveedor() throws Exception {
        List<CuentaPorCobrar> cuentaPorCobrares;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM C_POR_COBRAR_PROVEEDOR")) {
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            } catch (Exception e) {
                cuentaPorCobrares = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return cuentaPorCobrares;
    }

    public <T> List listarCuentasPorCobrarCliente() throws Exception {
        List<CuentaPorCobrar> cuentaPorCobrares;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM C_POR_COBRAR_CLIENTE")) {
                cuentaPorCobrares = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CuentaPorCobrar cuentaPorCobrar = (CuentaPorCobrar) extraerObject(rs);
                        cuentaPorCobrares.add(cuentaPorCobrar);
                    }
                }
            } catch (Exception e) {
                cuentaPorCobrares = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return cuentaPorCobrares;
    }
}
