package dao.venta;

import dao.Conexion;
import entidades.venta.Venta;
import entidades.venta.VentaPaquete;
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
public class VentaPaqueteCRUD extends Conexion implements OperacionesCRUD {

    @Override
    public void registrar(Object objeto) throws Exception {
        VentaPaquete ventaPaquete = (VentaPaquete) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO VENTA_PAQUETE (id_venta,numero_paquete,id_madera,num_piezas,volumen,monto,tipo_madera) VALUES (?,?,?,?,?,?,?)");
            st = cargarObject(st, ventaPaquete);
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
        VentaPaquete ventaPaquete = (VentaPaquete) objeto;
        st.setString(1, ventaPaquete.getId_venta());
        st.setInt(2, ventaPaquete.getNumero_paquete());
        st.setString(3, ventaPaquete.getId_madera());
        st.setFloat(4, ventaPaquete.getNum_piezas());
        st.setFloat(5, ventaPaquete.getVolumen());
        st.setFloat(6, ventaPaquete.getMonto());
        st.setString(7, ventaPaquete.getTipo_madera());
        return st;
    }

//    @Override
//    public <T> List listar() throws Exception {
//        List<VentaPaquete> ventaPaquetes;
//        try {
//            this.abrirConexion();
//            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_PAQUETE")) {
//                ventaPaquetes = new ArrayList();
//                try (ResultSet rs = st.executeQuery()) {
//                    while (rs.next()) {
//                        VentaPaquete ventaPaquete = (VentaPaquete) extraerObject(rs);
//                        ventaPaquetes.add(ventaPaquete);
//                    }
//                }
//            } catch (Exception e) {
//                ventaPaquetes = null;
//                System.out.println(e);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            throw e;
//        } finally {
//            this.cerrarConexion();
//        }
//        return ventaPaquetes;
//    }
    @Override
    public <T> List listar(String id_jefe) throws Exception {
        // Se consultan datos generales de las ventas extra
        List<Venta> ventas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_VENTA_PAQUETE WHERE id_jefe = ?")) {
                st.setString(1, id_jefe);
                ventas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Venta venta = (Venta) extraerVenta(rs);
                        ventas.add(venta);
                    }
                }
            } catch (Exception e) {
                ventas = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return ventas;
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

    public <VentaPaquete> List listarDetalleVP(String id_venta) throws Exception {
        // Se consulta el detalle de una venta extra
        List<VentaPaquete> detalles;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_PAQUETE WHERE id_venta = ?")) {
                st.setString(1, id_venta);
                detalles = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaPaquete venta = (VentaPaquete) extraerDetalleVP(rs);
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

    private Object extraerDetalleVP(ResultSet rs) throws SQLException {
        VentaPaquete ventaPaquete = new VentaPaquete();
        ventaPaquete.setId_venta(rs.getString("id_venta"));
        ventaPaquete.setNumero_paquete(rs.getInt("numero_paquete"));
        ventaPaquete.setId_madera(rs.getString("id_madera"));
        ventaPaquete.setNum_piezas(rs.getInt("num_piezas"));
        ventaPaquete.setVolumen(rs.getFloat("volumen"));
        ventaPaquete.setMonto(rs.getFloat("monto"));
        ventaPaquete.setTipo_madera(rs.getString("tipo_madera"));
        return ventaPaquete;
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
        ventaPaquete.setTipo_madera(rs.getString("tipo_madera"));
        return ventaPaquete;
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
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "UPDATE VENTA_PAQUETE SET num_piezas = ?, volumen = ?, monto = ?, tipo_madera = ? WHERE id_venta = ? AND numero_paquete = ? AND id_madera = ?");
            st.setInt(1, ventaPaquete.getNum_piezas());
            st.setFloat(2, ventaPaquete.getVolumen());
            st.setFloat(3, ventaPaquete.getMonto());
            st.setString(4, ventaPaquete.getTipo_madera());
            st.setString(5, ventaPaquete.getId_venta());
            st.setInt(6, ventaPaquete.getNumero_paquete());
            st.setString(7, ventaPaquete.getId_madera());
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
        VentaPaquete ventaPaquete = (VentaPaquete) objeto;
        if (!ventaPagado(ventaPaquete.getId_venta())) {
            try {
                this.abrirConexion();
                PreparedStatement st = this.conexion.prepareStatement(
                        "DELETE FROM VENTA_PAQUETE WHERE id_venta = ? AND numero_paquete = ? AND id_madera = ?");
                st.setString(1, ventaPaquete.getId_venta());
                st.setInt(2, ventaPaquete.getNumero_paquete());
                st.setString(3, ventaPaquete.getId_madera());
                st.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
                throw e;
            } finally {
                this.cerrarConexion();
            }
        }
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

    @Override
    public <T> List buscar(String nombre_campo, String dato, String id_jefe) throws Exception {
        List<Venta> ventaPaquetes;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_VENTA_PAQUETE WHERE " + nombre_campo + " like ? AND id_jefe = ?")) {
                st.setString(1, "%" + dato + "%");
                st.setString(2, id_jefe);
                ventaPaquetes = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Venta ventaPaquete = (Venta) extraerVenta(rs);
                        ventaPaquetes.add(ventaPaquete);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return ventaPaquetes;
    }

}
