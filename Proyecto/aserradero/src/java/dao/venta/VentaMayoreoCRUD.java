package dao.venta;

import dao.Conexion;
import entidades.venta.Venta;
import entidades.venta.VentaMayoreo;
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
public class VentaMayoreoCRUD extends Conexion implements OperacionesCRUD {

    @Override
    public void registrar(Object objeto) throws Exception {
        VentaMayoreo ventaMayoreo = (VentaMayoreo) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO VENTA_MAYOREO (id_administrador,id_venta,id_madera,num_piezas,volumen,monto,tipo_madera) VALUES (?,?,?,?,?,?,?)");
            st = cargarObject(st, ventaMayoreo);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        VentaMayoreo ventaMayoreo = (VentaMayoreo) objeto;
        st.setString(1, ventaMayoreo.getId_administrador());
        st.setString(2, ventaMayoreo.getId_venta());
        st.setString(3, ventaMayoreo.getId_madera());
        st.setFloat(4, ventaMayoreo.getNum_piezas());
        st.setBigDecimal(5, ventaMayoreo.getVolumen());
        st.setBigDecimal(6, ventaMayoreo.getMonto());
        st.setString(7, ventaMayoreo.getTipo_madera());
        return st;
    }

    @Override
    public <T> List listar(String id_jefe) throws Exception {
        List<Venta> listaVentas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_VENTA_MAYOREO WHERE id_jefe = ?")) {
                st.setString(1, id_jefe);
                listaVentas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Venta venta = (Venta) extraerVenta(rs);
                        listaVentas.add(venta);
                    }
                }
            } catch (Exception e) {
                listaVentas = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return listaVentas;
    }

    private Venta extraerVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId_venta(rs.getString("id_venta"));
        venta.setFecha(rs.getDate("fecha"));
        venta.setId_cliente(rs.getString("id_cliente"));
        venta.setCliente(rs.getString("cliente"));
        venta.setId_empleado(rs.getString("id_empleado"));
        venta.setEmpleado(rs.getString("empleado"));
        venta.setId_jefe(rs.getString("id_jefe"));
        venta.setEstatus(rs.getString("estatus"));
        venta.setTipo_venta(rs.getString("tipo_venta"));
        venta.setTipo_pago(rs.getString("tipo_pago"));
        venta.setMonto(rs.getBigDecimal("monto"));
        return venta;
    }

    public <T> List listarDetalleVentaMayoreo(String id_venta) throws Exception {
        // Se consulta el detalle de una venta mayoreo
        List<VentaMayoreo> detalles;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_MAYOREO WHERE id_venta = ?")) {
                st.setString(1, id_venta);
                detalles = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaMayoreo venta = (VentaMayoreo) extraerObject(rs);
                        detalles.add(venta);
                    }
                }
            } catch (Exception e) {
                detalles = null;
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return detalles;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        VentaMayoreo ventaMayoreo = new VentaMayoreo();
        ventaMayoreo.setId_administrador(rs.getString("id_administrador"));
        ventaMayoreo.setId_venta(rs.getString("id_venta"));
        ventaMayoreo.setId_madera(rs.getString("id_madera"));
        ventaMayoreo.setNum_piezas(rs.getInt("num_piezas"));
        ventaMayoreo.setVolumen(rs.getBigDecimal("volumen"));
        ventaMayoreo.setMonto(rs.getBigDecimal("monto"));
        ventaMayoreo.setTipo_madera(rs.getString("tipo_madera"));
        return ventaMayoreo;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        VentaMayoreo ventaMayoreoM = (VentaMayoreo) objeto;
        VentaMayoreo ventaMayoreo = null;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement(
                "SELECT * FROM VENTA_MAYOREO WHERE id_venta = ? AND id_madera = ? AND tipo_madera = ?")) {
            st.setString(1, ventaMayoreoM.getId_venta());
            st.setString(2, ventaMayoreoM.getId_madera());
            st.setString(3, ventaMayoreoM.getTipo_madera());
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
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE VENTA_MAYOREO SET num_piezas = ?, volumen = ?, monto = ? WHERE id_venta = ? AND id_madera = ? AND tipo_madera = ?");
            st.setInt(1, ventaMayoreo.getNum_piezas());
            st.setBigDecimal(2, ventaMayoreo.getVolumen());
            st.setBigDecimal(3, ventaMayoreo.getMonto());
            st.setString(4, ventaMayoreo.getId_venta());
            st.setString(5, ventaMayoreo.getId_madera());
            st.setString(6, ventaMayoreo.getTipo_madera());
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
        VentaMayoreo ventaMayoreo = (VentaMayoreo) objeto;
        if (!ventaPagado(ventaMayoreo.getId_venta())) {
            try {
                this.abrirConexion();
                PreparedStatement st = this.conexion.prepareStatement("DELETE FROM VENTA_MAYOREO WHERE id_venta = ? AND id_madera = ? AND tipo_madera=?");
                st.setString(1, ventaMayoreo.getId_venta());
                st.setString(2, ventaMayoreo.getId_madera());
                st.setString(3, ventaMayoreo.getTipo_madera());
                st.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
                throw e;
            } finally {
                this.cerrarConexion();
            }
        }

    }

    @Override
    public <T> List buscar(String nombre_campo, String dato, String id_jefe) throws Exception {
        List<VentaMayoreo> ventaMayoreos;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_MAYOREO WHERE " + nombre_campo + " like ? AND id_administrador = ?")) {
                st.setString(1, "%" + dato + "%");
                st.setString(2, id_jefe);
                ventaMayoreos = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaMayoreo ventaMayoreo = (VentaMayoreo) extraerObject(rs);
                        ventaMayoreos.add(ventaMayoreo);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return ventaMayoreos;
    }

    private boolean ventaPagado(String id_venta) throws Exception {
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
}
