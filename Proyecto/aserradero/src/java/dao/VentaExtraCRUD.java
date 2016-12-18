package dao;

import entidades.VentaExtra;
import entidades.Venta;
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
public class VentaExtraCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        VentaExtra ventaExtra = (VentaExtra) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO VENTA_EXTRA (id_venta,tipo,monto,observacion) VALUES (?,?,?,?)");
            st = cargarObject(st, ventaExtra);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List listar(String id_jefe) throws Exception {
        // Se consultan datos generales de las ventas extra
        List<Venta> ventas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_VENTA_EXTRA")) {
                ventas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Venta venta = (Venta) extraerVenta(rs);
                        ventas.add(venta);
                    }
                }
            }catch(Exception e){
                ventas = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
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
    
    public <VentaExtra> List listarDetalleVE(String id_venta) throws Exception {
        // Se consulta el detalle de una venta extra
        List<VentaExtra> detalles;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_EXTRA WHERE id_venta = ?")) {
                st.setString(1,id_venta);
                detalles = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaExtra venta = (VentaExtra) extraerDetalleVE(rs);
                        detalles.add(venta);
                    }
                }
            }catch(Exception e){
                detalles = null;
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return detalles;
    }
    
    private Object extraerDetalleVE(ResultSet rs) throws SQLException {
        VentaExtra ventaExtra = new VentaExtra();
        ventaExtra.setId_venta(rs.getString("id_venta"));
        ventaExtra.setTipo(rs.getString("tipo"));
        ventaExtra.setMonto(rs.getFloat("monto"));
        ventaExtra.setObservacion(rs.getString("observacion"));
        return ventaExtra;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        VentaExtra ventaExtraM = (VentaExtra) objeto;
        VentaExtra ventaExtra = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VENTA_EXTRA_Y_DETALLE WHERE id_venta = ? AND tipo = ? AND (id_venta NOT IN (SELECT id_venta FROM VENTA WHERE estatus = 'Pagado') OR length(?) = 18)")) {
                st.setString(1, ventaExtraM.getId_venta());
                st.setString(2, ventaExtraM.getTipo());
                st.setString(3, "123456789123456789"); // Solamente los administradores tienen derecho a modificar  
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        ventaExtra = (VentaExtra) extraerObject(rs);
                    }
                }
            }
        return ventaExtra;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        VentaExtra ventaExtra = (VentaExtra) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement(
                    "UPDATE VENTA_EXTRA SET monto = ?, observacion = ? WHERE id_venta = ? AND tipo = ?");
            st.setFloat(1,ventaExtra.getMonto());
            st.setString(2,ventaExtra.getObservacion());
            st.setString(3,ventaExtra.getId_venta());
            st.setString(4,ventaExtra.getTipo());
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
        VentaExtra ventaExtra = (VentaExtra) objeto;
        if(!ventaPagado(ventaExtra.getId_venta())){
            try{
                this.abrirConexion();
                PreparedStatement st= this.conexion.prepareStatement("DELETE FROM VENTA_EXTRA WHERE id_venta = ? AND tipo = ?");
                st.setString(1,ventaExtra.getId_venta());
                st.setString(2,ventaExtra.getTipo());
                st.executeUpdate();
            }catch(Exception e){
                System.out.println(e);
                throw e;
            }finally{
                this.cerrarConexion();
            }
        }
        
    }
    // consultamos si la venta está pagada
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
        List<VentaExtra> ventaExtras;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_VENTA_EXTRA WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                ventaExtras = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        VentaExtra ventaExtra = (VentaExtra) extraerObject(rs);
                        ventaExtras.add(ventaExtra);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return ventaExtras;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        VentaExtra ventaExtra = new VentaExtra();
        ventaExtra.setFecha(rs.getDate("fecha"));
        ventaExtra.setId_venta(rs.getString("id_venta"));
        ventaExtra.setId_cliente(rs.getString("id_cliente"));
        ventaExtra.setCliente(rs.getString("cliente"));
        ventaExtra.setId_empleado(rs.getString("id_empleado"));
        ventaExtra.setEmpleado(rs.getString("empleado"));
        ventaExtra.setEstatus(rs.getString("estatus"));
        ventaExtra.setTipo(rs.getString("tipo"));
        ventaExtra.setMonto(rs.getFloat("monto"));
        ventaExtra.setObservacion(rs.getString("observacion"));
        return ventaExtra;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        VentaExtra ventaExtra = (VentaExtra) objeto;
        st.setString(1,ventaExtra.getId_venta());
        st.setString(2,ventaExtra.getTipo());
        st.setFloat(3,ventaExtra.getMonto());
        st.setString(4,ventaExtra.getObservacion());
        return st;
    }
    
    
}
