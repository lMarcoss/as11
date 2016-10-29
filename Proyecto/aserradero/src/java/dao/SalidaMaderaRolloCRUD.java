package dao;

import entidades.SalidaMaderaRollo;
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
public class SalidaMaderaRolloCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        SalidaMaderaRollo salida = (SalidaMaderaRollo) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(""
                    + "INSERT INTO SALIDA_MADERA_ROLLO (fecha,id_empleado,num_piezas,volumen_total) values(?,?,?,?)");
            st = cargarObject(st, salida);
            st.executeUpdate();
        }catch(Exception e){
            System.err.println(e);
            System.out.println("Hola:"+e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }

    @Override
    public <T> List listar() throws Exception {
        List<SalidaMaderaRollo> salidas = null;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_SALIDA_MADERA_ROLLO")){
                salidas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        SalidaMaderaRollo salida = (SalidaMaderaRollo) extraerObject(rs);
                        salidas.add(salida);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            cerrarConexion();
        }
        
        return salidas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        SalidaMaderaRollo salidaM = (SalidaMaderaRollo) objeto;
        SalidaMaderaRollo salida = null;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_SALIDA_MADERA_ROLLO WHERE id_salida = ?")) {
            st.setInt(1, salidaM.getId_salida());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    salida = (SalidaMaderaRollo) extraerObject(rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return salida;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        SalidaMaderaRollo salida = (SalidaMaderaRollo) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE SALIDA_MADERA_ROLLO SET fecha = ?, id_empleado = ?, num_piezas = ?, volumen_total = ? WHERE id_salida = ?");
            st.setDate(1, salida.getFecha());
            st.setString(2, salida.getId_empleado());
            st.setInt(3, salida.getNum_piezas());
            st.setFloat(4, salida.getVolumen_total());
            st.setFloat(5, salida.getId_salida());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        List<SalidaMaderaRollo> salidas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_SALIDA_MADERA_ROLLO WHERE "+nombre_campo+" like ?")){
                st.setString(1, "%"+dato+"%");
                salidas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        SalidaMaderaRollo salida = (SalidaMaderaRollo) extraerObject(rs);
                        salidas.add(salida);
                    }
                }catch(Exception e){
                    System.out.println(e);
                    throw e;
                }
            }catch(Exception e){
                System.out.println(e);
            throw e;
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return salidas;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        SalidaMaderaRollo salida = new SalidaMaderaRollo();
        salida.setId_salida(rs.getInt("id_salida"));
        salida.setFecha(rs.getDate("fecha"));
        salida.setId_empleado(rs.getString("id_empleado"));
        salida.setEmpleado(rs.getString("empleado"));
        salida.setId_jefe(rs.getString("id_jefe"));
        salida.setNum_piezas(rs.getInt("num_piezas"));
        salida.setVolumen_total(rs.getFloat("volumen_total"));
        return salida;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        SalidaMaderaRollo salida = (SalidaMaderaRollo) objeto;
        st.setDate(1, salida.getFecha());
        st.setString(2, salida.getId_empleado());        
        st.setInt(3, salida.getNum_piezas());
        st.setFloat(4, salida.getVolumen_total());
        return st;
    }
    
}
