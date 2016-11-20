package dao;

import entidades.OtroGasto;
import interfaces.OperacionesCRUD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rcortes
 */
 public class OtroGastoCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        OtroGasto otrogasto = (OtroGasto) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(""
                    + "INSERT INTO OTRO_GASTO (fecha, id_empleado, nombre_gasto, monto, observacion) values (?, ?, ?, ?, ?)");
            st = cargarObject(st, otrogasto);
            st.executeUpdate();
        }catch(Exception e){
            System.err.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List listar() throws Exception {
        List<OtroGasto> otrosgastos;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_OTRO_GASTO")){
                otrosgastos = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        OtroGasto otrogasto = (OtroGasto) extraerObject(rs);
                        otrosgastos.add(otrogasto);
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                otrosgastos = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            cerrarConexion();
        }
        return otrosgastos;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        OtroGasto otrogastoM = (OtroGasto) objeto;
        OtroGasto otrogasto = null;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_OTRO_GASTO WHERE id_gasto = ?")) {
            st.setInt(1, otrogastoM.getId_gasto());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    otrogasto = (OtroGasto) extraerObject(rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return otrogasto;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        OtroGasto otrogasto = (OtroGasto) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE OTRO_GASTO SET fecha = ?,id_empleado = ?, nombre_gasto = ?, monto = ?, observacion = ? where id_gasto=?");
            st.setDate(1, otrogasto.getFecha());
            st.setString(2, otrogasto.getId_empleado());
            st.setString(3, otrogasto.getNombre_gasto());
            st.setFloat(4, otrogasto.getMonto());
            st.setString(5, otrogasto.getObservacion());
            st.setInt(6, otrogasto.getId_gasto());
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
        OtroGasto otrogasto = (OtroGasto) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM OTRO_GASTO WHERE id_gasto = ?");
            st.setInt(1, otrogasto.getId_gasto());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<OtroGasto> otrosgastos;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_OTRO_GASTO WHERE "+nombre_campo+" like ?")){
                st.setString(1, "%"+dato+"%");
                otrosgastos = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        OtroGasto otrogasto = (OtroGasto) extraerObject(rs);
                        otrosgastos.add(otrogasto);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return otrosgastos;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        OtroGasto otrogasto = new OtroGasto();
        otrogasto.setId_gasto(rs.getInt("id_gasto"));
        otrogasto.setFecha(rs.getDate("fecha"));
        otrogasto.setId_empleado(rs.getString("id_empleado"));
        otrogasto.setEmpleado(rs.getString("empleado"));
        otrogasto.setNombre_gasto(rs.getString("nombre_gasto"));
        otrogasto.setMonto(rs.getFloat("monto"));
        otrogasto.setObservacion(rs.getString("observacion"));
        return otrogasto;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        OtroGasto otrogasto = (OtroGasto) objeto;
        st.setDate(1, otrogasto.getFecha());
        st.setString(2, otrogasto.getId_empleado());
        st.setString(3, otrogasto.getNombre_gasto());
        st.setFloat(4,otrogasto.getMonto());
        st.setString(5, otrogasto.getObservacion());
        return st;
    }

 }
