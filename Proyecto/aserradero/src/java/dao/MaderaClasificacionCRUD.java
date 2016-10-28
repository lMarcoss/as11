package dao;

import entidades.MaderaClasificacion;
import entidadesVirtuales.CostoMaderaClasificacion;
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
public class MaderaClasificacionCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        MaderaClasificacion maderaClasificacion = (MaderaClasificacion) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO MADERA_CLASIFICACION (id_madera,grueso,ancho,largo,volumen) VALUES (?,?,?,?,?)");
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
    public <T> List listar() throws Exception {
        List<MaderaClasificacion> maderaClasificaciones;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM MADERA_CLASIFICACION")) {
                maderaClasificaciones = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        MaderaClasificacion maderaClasificacion = (MaderaClasificacion) extraerObject(rs);
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
    public Object modificar(Object objeto) throws Exception {
        MaderaClasificacion maderaClasificacionM = (MaderaClasificacion) objeto;
        MaderaClasificacion maderaClasificacion = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM MADERA_CLASIFICACION WHERE id_madera = ?")) {
                st.setString(1, maderaClasificacionM.getId_madera());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        maderaClasificacion = (MaderaClasificacion) extraerObject(rs);
                    }
                }
            }
        return maderaClasificacion;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        MaderaClasificacion maderaClasificacion = (MaderaClasificacion) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE MADERA_CLASIFICACION SET grueso = ?, ancho = ?, largo = ?, volumen = ? WHERE id_madera= ?");
            st.setFloat(1,maderaClasificacion.getGrueso());
            st.setFloat(2,maderaClasificacion.getAncho());
            st.setFloat(3,maderaClasificacion.getLargo());
            st.setFloat(4,maderaClasificacion.getVolumen());
            st.setString(5,maderaClasificacion.getId_madera());
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
        MaderaClasificacion maderaClasificacion = (MaderaClasificacion) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM MADERA_CLASIFICACION WHERE id_madera = ?");
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
        List<MaderaClasificacion> maderaClasificaciones;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM MADERA_CLASIFICACION WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                maderaClasificaciones = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        MaderaClasificacion maderaClasificacion = (MaderaClasificacion) extraerObject(rs);
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
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        MaderaClasificacion maderaClasificacion = new MaderaClasificacion();
        maderaClasificacion.setId_madera(rs.getString("id_madera"));
        maderaClasificacion.setGrueso(rs.getFloat("grueso"));
        maderaClasificacion.setAncho(rs.getFloat("ancho"));
        maderaClasificacion.setLargo(rs.getFloat("largo"));
        maderaClasificacion.setVolumen(rs.getFloat("volumen"));
        return maderaClasificacion;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        MaderaClasificacion maderaClasificacion = (MaderaClasificacion) objeto;
        st.setString(1, maderaClasificacion.getId_madera());
        st.setFloat(2,maderaClasificacion.getGrueso());
        st.setFloat(3,maderaClasificacion.getAncho());
        st.setFloat(4,maderaClasificacion.getLargo());
        st.setFloat(5,maderaClasificacion.getVolumen());
        return st;
    }

    public List<MaderaClasificacion> buscarMaderaClasificacionPorId(String id_madera) throws Exception {
        List<MaderaClasificacion> maderaClasificaciones;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM MADERA_CLASIFICACION WHERE id_madera = ?")) {
                st.setString(1, id_madera);
                maderaClasificaciones = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        MaderaClasificacion maderaClasificacion = (MaderaClasificacion) extraerObject(rs);
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
    
    public <T> List listarCostoMaderaClasif() throws Exception {
        List<CostoMaderaClasificacion> cMaderaClasifs;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM COSTO_MADERA_CLASIFICACION")) {
                cMaderaClasifs = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        CostoMaderaClasificacion cMaderaClasif = (CostoMaderaClasificacion) extraerCostoMaderaClasif(rs);
                        cMaderaClasifs.add(cMaderaClasif);
                    }
                }
            }catch(Exception e){
                cMaderaClasifs = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return cMaderaClasifs;
    }
   
    public Object extraerCostoMaderaClasif(ResultSet rs) throws SQLException {
        CostoMaderaClasificacion cMaderaClasif = new CostoMaderaClasificacion();
        cMaderaClasif.setId_madera(rs.getString("id_madera"));
        cMaderaClasif.setGrueso(rs.getFloat("grueso"));
        cMaderaClasif.setAncho(rs.getFloat("ancho"));
        cMaderaClasif.setLargo(rs.getFloat("largo"));
        cMaderaClasif.setVolumen((rs.getFloat("volumen")));
        cMaderaClasif.setMonto_volumen(Float.valueOf(rs.getString("monto_volumen")));
        return cMaderaClasif;
    }
}
