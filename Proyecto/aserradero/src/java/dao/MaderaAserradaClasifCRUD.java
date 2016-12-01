package dao;

import entidades.MaderaAserradaClasif;
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
public class MaderaAserradaClasifCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        MaderaAserradaClasif maderaClasificacion = (MaderaAserradaClasif) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO MADERA_ASERRADA_CLASIF (id_madera,grueso,ancho,largo,volumen,costo) VALUES (?,?,?,?,?,?)");
            st = cargarObject(st, maderaClasificacion);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }
    
    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        MaderaAserradaClasif maderaClasificacion = (MaderaAserradaClasif) objeto;
        st.setString(1, maderaClasificacion.getId_madera());
        st.setBigDecimal(2,maderaClasificacion.getGrueso());
        st.setBigDecimal(3,maderaClasificacion.getAncho());
        st.setBigDecimal(4,maderaClasificacion.getLargo());
        st.setBigDecimal(5,maderaClasificacion.getVolumen());
        st.setBigDecimal(6,maderaClasificacion.getCosto_por_volumen());
        return st;
    }

    @Override
    public <T> List listar() throws Exception {
        List<MaderaAserradaClasif> maderaClasificaciones;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM MADERA_ASERRADA_CLASIF")) {
                maderaClasificaciones = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        MaderaAserradaClasif maderaClasificacion = (MaderaAserradaClasif) extraerObject(rs);
                        maderaClasificaciones.add(maderaClasificacion);
                    }
                }
            }catch(Exception e){
                maderaClasificaciones = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return maderaClasificaciones;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        MaderaAserradaClasif maderaClasificacion = new MaderaAserradaClasif();
        maderaClasificacion.setId_madera(rs.getString("id_madera"));
        maderaClasificacion.setGrueso(rs.getBigDecimal("grueso"));
        maderaClasificacion.setAncho(rs.getBigDecimal("ancho"));
        maderaClasificacion.setLargo(rs.getBigDecimal("largo"));
        maderaClasificacion.setVolumen(rs.getBigDecimal("volumen"));
        maderaClasificacion.setCosto_por_volumen(rs.getBigDecimal("costo_por_volumen"));
        return maderaClasificacion;
    }
    
    @Override
    public Object modificar(Object objeto) throws Exception {
        MaderaAserradaClasif maderaClasificacionM = (MaderaAserradaClasif) objeto;
        MaderaAserradaClasif maderaClasificacion = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM MADERA_ASERRADA_CLASIF WHERE id_madera = ?")) {
                st.setString(1, maderaClasificacionM.getId_madera());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        maderaClasificacion = (MaderaAserradaClasif) extraerObject(rs);
                    }
                }
            }
        return maderaClasificacion;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        MaderaAserradaClasif maderaClasificacion = (MaderaAserradaClasif) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE MADERA_ASERRADA_CLASIF SET grueso = ?, ancho = ?, largo = ?, volumen = ?, costo_por_volumen = ? WHERE id_madera= ?");
            st.setBigDecimal(1,maderaClasificacion.getGrueso());
            st.setBigDecimal(2,maderaClasificacion.getAncho());
            st.setBigDecimal(3,maderaClasificacion.getLargo());
            st.setBigDecimal(4,maderaClasificacion.getVolumen());
            st.setBigDecimal(5,maderaClasificacion.getCosto_por_volumen());
            st.setString(6,maderaClasificacion.getId_madera());
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
        MaderaAserradaClasif maderaClasificacion = (MaderaAserradaClasif) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM MADERA_ASERRADA_CLASIF WHERE id_madera = ?");
            st.setString(1,maderaClasificacion.getId_madera());
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
        List<MaderaAserradaClasif> maderaClasificaciones;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM MADERA_ASERRADA_CLASIF WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                maderaClasificaciones = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        MaderaAserradaClasif maderaClasificacion = (MaderaAserradaClasif) extraerObject(rs);
                        maderaClasificaciones.add(maderaClasificacion);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return maderaClasificaciones;
    }
    
}
