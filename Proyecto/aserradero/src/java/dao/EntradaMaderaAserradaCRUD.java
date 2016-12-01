package dao;

import entidades.EntradaMaderaAserrada;
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
public class EntradaMaderaAserradaCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        EntradaMaderaAserrada produccionMadera = (EntradaMaderaAserrada) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO ENTRADA_MADERA_ASERRADA (fecha,id_madera,num_piezas,id_empleado) VALUES (?,?,?,?)");
            st = cargarObject(st, produccionMadera);
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
        List<EntradaMaderaAserrada> produccionMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_ENTRADA_MADERA_ASERRADA")) {
                produccionMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        EntradaMaderaAserrada produccionMadera = (EntradaMaderaAserrada) extraerObject(rs);
                        produccionMaderas.add(produccionMadera);
                    }
                }
            }catch(Exception e){
                produccionMaderas = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return produccionMaderas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        EntradaMaderaAserrada produccionM = (EntradaMaderaAserrada) objeto;
        EntradaMaderaAserrada produccion = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_ENTRADA_MADERA_ASERRADA WHERE id_entrada = ?")) {
                st.setInt(1, produccionM.getId_entrada());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        produccion = (EntradaMaderaAserrada) extraerObject(rs);
                    }
                }
            }
        return produccion;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        EntradaMaderaAserrada produccion = (EntradaMaderaAserrada) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE ENTRADA_MADERA_ASERRADA SET num_piezas = ? WHERE id_entrada = ?");
            st.setInt(1,produccion.getNum_piezas());
            st.setInt(2,produccion.getId_entrada());
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
        EntradaMaderaAserrada produccionMadera = (EntradaMaderaAserrada) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM ENTRADA_MADERA_ASERRADA WHERE id_entrada = ?");
            st.setInt(1,produccionMadera.getId_entrada());
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
        List<EntradaMaderaAserrada> produccionMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_ENTRADA_MADERA_ASERRADA WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                produccionMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        EntradaMaderaAserrada produccionMadera = (EntradaMaderaAserrada) extraerObject(rs);
                        produccionMaderas.add(produccionMadera);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return produccionMaderas;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        EntradaMaderaAserrada produccionMadera = new EntradaMaderaAserrada();
        produccionMadera.setId_entrada(rs.getInt("id_entrada"));
        produccionMadera.setFecha(rs.getDate("fecha"));
        produccionMadera.setId_madera(rs.getString("id_madera"));
        produccionMadera.setNum_piezas(rs.getInt("num_piezas"));
        produccionMadera.setId_empleado(rs.getString("id_empleado"));
        produccionMadera.setEmpleado(rs.getString("empleado"));
        produccionMadera.setId_jefe(rs.getString("id_jefe"));
        
        return produccionMadera;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        EntradaMaderaAserrada produccionMadera = (EntradaMaderaAserrada) objeto;
        st.setDate(1,produccionMadera.getFecha());
        st.setString(2,produccionMadera.getId_madera());
        st.setInt(3,produccionMadera.getNum_piezas());
        st.setString(4,produccionMadera.getId_empleado());
        return st;
    }
    
    public List<EntradaMaderaAserrada> buscarPorFecha(String fecha) throws Exception {
        List<EntradaMaderaAserrada> produccionMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_ENTRADA_MADERA_ASERRADA WHERE fecha = ?")) {
                st.setString(1, fecha);
                produccionMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        EntradaMaderaAserrada produccionMadera = (EntradaMaderaAserrada) extraerObject(rs);
                        produccionMaderas.add(produccionMadera);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return produccionMaderas;
    }
}
