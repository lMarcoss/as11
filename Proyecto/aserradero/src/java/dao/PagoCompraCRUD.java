package dao;

import entidades.PagoCompra;
import entidadesVirtuales.MontoPagoCompra;
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
public class PagoCompraCRUD extends Conexion implements OperacionesCRUD {

    @Override
    public void registrar(Object objeto) throws Exception {
        PagoCompra pagoCompra = (PagoCompra) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(""
                    + "INSERT INTO PAGO_COMPRA (fecha, id_proveedor, monto_pago, monto_por_pagar) values ( ?, ?, ?, ?)");
            st = cargarObject(st, pagoCompra);
            st.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        PagoCompra pagoCompra = (PagoCompra) objeto;
        st.setDate(1, pagoCompra.getFecha());
        st.setString(2, pagoCompra.getId_proveedor());
        st.setFloat(3, pagoCompra.getMonto_pago());
        st.setFloat(4, pagoCompra.getMonto_por_pagar());
        return st;
    }

    @Override
    public <T> List listar() throws Exception {
        List<PagoCompra> pagoCompras;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_PAGO_COMPRA")) {
                pagoCompras = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        PagoCompra pagoCompra = (PagoCompra) extraerObject(rs);
                        pagoCompras.add(pagoCompra);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                pagoCompras = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            cerrarConexion();
        }
        return pagoCompras;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        PagoCompra pagoCompra = new PagoCompra();
        pagoCompra.setId_pago(rs.getInt("id_pago"));
        pagoCompra.setFecha(rs.getDate("fecha"));
        pagoCompra.setId_proveedor(rs.getString("id_proveedor"));
        pagoCompra.setProveedor(rs.getString("proveedor"));
        pagoCompra.setId_administrador(rs.getString("id_administrador"));
        pagoCompra.setMonto_pago(rs.getFloat("monto_pago"));
        pagoCompra.setMonto_por_pagar(rs.getFloat("monto_por_pagar"));
        return pagoCompra;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        PagoCompra pagoCompraM = (PagoCompra) objeto;
        PagoCompra pagoCompra = null;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_PAGO_COMPRA WHERE id_pago = ?")) {
            st.setInt(1, pagoCompraM.getId_pago());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    pagoCompra = (PagoCompra) extraerObject(rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return pagoCompra;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        PagoCompra pagoCompra = (PagoCompra) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE PAGO_COMPRA SET fecha = ?, id_proveedor= ?, monto_pago = ?, monto_por_pagar = ? where id_pago = ?");
            st.setDate(1, pagoCompra.getFecha());
            st.setString(2, pagoCompra.getId_proveedor());
            st.setFloat(3, pagoCompra.getMonto_pago());
            st.setFloat(4, pagoCompra.getMonto_por_pagar());
            st.setInt(5, pagoCompra.getId_pago());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }

    @Override
    public void eliminar(Object objeto) throws Exception {
        PagoCompra pagoCompra = (PagoCompra) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM PAGO_COMPRA WHERE id_pago = ?");
            st.setInt(1, pagoCompra.getId_pago());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<PagoCompra> pagoCompras;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_PAGO_COMPRA WHERE " + nombre_campo + " like ?")) {
                st.setString(1, "%" + dato + "%");
                pagoCompras = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        PagoCompra pagoCompra = (PagoCompra) extraerObject(rs);
                        pagoCompras.add(pagoCompra);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return pagoCompras;
    }

    public <T> List listarMontoPagoCompra(String administrador) throws Exception {
        List<MontoPagoCompra> listaMontoPagoCompra;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_MONTO_PAGO_COMPRA WHERE id_administrador = ? AND monto_por_pagar > 0")) {
                st.setString(1, administrador);
                listaMontoPagoCompra = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        MontoPagoCompra montoPagoCompra = (MontoPagoCompra) extraerMontoPagoCompra(rs);
                        listaMontoPagoCompra.add(montoPagoCompra);
                    }
                }
            } catch (Exception e) {
                listaMontoPagoCompra = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return listaMontoPagoCompra;
    }

    private MontoPagoCompra extraerMontoPagoCompra(ResultSet rs) throws SQLException {
        MontoPagoCompra monto = new MontoPagoCompra();
        monto.setId_proveedor(rs.getString("id_proveedor"));
        monto.setProveedor(rs.getString("proveedor"));
        monto.setMonto_por_pagar(rs.getFloat("monto_por_pagar"));
        monto.setId_administrador(rs.getString("id_administrador"));
        return monto;
    }
}
