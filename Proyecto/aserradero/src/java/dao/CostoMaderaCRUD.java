package dao;

import entidades.CostoMadera;
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
public class CostoMaderaCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        CostoMadera costoMadera = (CostoMadera) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO COSTO_MADERA (id_madera,monto_volumen) VALUES (?,?)");
            st = cargarObject(st, costoMadera);
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
        List<CostoMadera> costoMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM COSTO_MADERA")) {
                costoMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CostoMadera costoMadera = (CostoMadera) extraerObject(rs);
                        costoMaderas.add(costoMadera);
                    }
                }
            }catch(Exception e){
                costoMaderas = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return costoMaderas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        CostoMadera costoMaderaM = (CostoMadera) objeto;
        CostoMadera costoMadera = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM COSTO_MADERA WHERE id_madera = ?")) {
                st.setString(1, costoMaderaM.getId_madera());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        costoMadera = (CostoMadera) extraerObject(rs);
                    }
                }
            }
        return costoMadera;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        CostoMadera costoMadera = (CostoMadera) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE COSTO_MADERA SET  monto_volumen = ? WHERE id_madera = ?");
            st.setFloat(1, costoMadera.getMonto_volumen());
            st.setString(3, costoMadera.getId_madera());
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
        CostoMadera costoMadera = (CostoMadera) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM COSTO_MADERA WHERE id_madera = ?");
            st.setString(1,costoMadera.getId_madera());
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
        List<CostoMadera> costoMaderas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM COSTO_MADERA WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                costoMaderas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CostoMadera costoMadera = (CostoMadera) extraerObject(rs);
                        costoMaderas.add(costoMadera);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return costoMaderas;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        CostoMadera costoMadera = new CostoMadera();
        costoMadera.setId_madera(rs.getString("id_madera"));
        costoMadera.setMonto_volumen(rs.getFloat("monto_volumen"));
        return costoMadera;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        CostoMadera costoMadera = (CostoMadera) objeto;
        st.setString(1,costoMadera.getId_madera());
        st.setFloat(2,costoMadera.getMonto_volumen());
        return st;
    }

    public List<CostoMadera> buscarPorId(String id_costoMadera) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
