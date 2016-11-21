package dao;

import entidades.InventarioMaderaProduccion;
import interfaces.OperacionesCRUD;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos
 */
public class InventarioMaderaProduccionCRUD extends Conexion implements OperacionesCRUD{
@Override
    public void registrar(Object objeto) throws Exception{
        InventarioMaderaProduccion inventarioMaderaProduccion = (InventarioMaderaProduccion) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("INSERT INTO INVENTARIO_MADERA_PRODUCCION (id_madera,num_piezas) VALUES (?,?)");
            st = cargarObject(st, inventarioMaderaProduccion);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List listar() throws Exception{
        List<InventarioMaderaProduccion> inventarioMaderaProducciones;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_INVENTARIO_PRODUCION")) {
                inventarioMaderaProducciones = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        InventarioMaderaProduccion inventarioMaderaProduccion = (InventarioMaderaProduccion) extraerObject(rs);
                        inventarioMaderaProducciones.add(inventarioMaderaProduccion);
                    }
                }
            }catch(Exception e){
                inventarioMaderaProducciones = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return inventarioMaderaProducciones;
    }
    
    @Override
    public void eliminar(Object objeto) throws Exception{
        InventarioMaderaProduccion inventarioMaderaProduccion = (InventarioMaderaProduccion) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM INVENTARIO_MADERA_PRODUCCION WHERE id_madera = ?");
            st.setString(1,inventarioMaderaProduccion.getId_madera());
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception{
        List<InventarioMaderaProduccion> inventarioMaderaProducciones;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM INVENTARIO_MADERA_PRODUCCION WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                inventarioMaderaProducciones = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        InventarioMaderaProduccion inventarioMaderaProduccion = (InventarioMaderaProduccion) extraerObject(rs);
                        inventarioMaderaProducciones.add(inventarioMaderaProduccion);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return inventarioMaderaProducciones;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        int dVolumen = 3;   // cantidad de decimaes para el volumen
        int dMonto = 2;     // cantidad de Decimales para el Costo
        InventarioMaderaProduccion inventario = new InventarioMaderaProduccion();
        inventario.setId_administrador(rs.getString("id_administrador"));
        inventario.setId_madera(rs.getString("id_madera"));
        inventario.setNum_piezas(rs.getInt("num_piezas"));
        BigDecimal VolumenTotal = rs.getBigDecimal("volumen_total");
        BigDecimal montoTotal = rs.getBigDecimal("monto_total");
        VolumenTotal = VolumenTotal.setScale(dVolumen, BigDecimal.ROUND_DOWN);  // Redondeamos a 3 decimales
        montoTotal = montoTotal.setScale(dMonto, BigDecimal.ROUND_DOWN);        // Redondeamos a 2 decimales
        inventario.setVolumen_total(VolumenTotal);
        inventario.setMonto_total(montoTotal);
        return inventario;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objecto) throws SQLException {
        InventarioMaderaProduccion inventarioMaderaProduccion = (InventarioMaderaProduccion) objecto;
        st.setString(1,inventarioMaderaProduccion.getId_madera());    
        st.setInt(2,inventarioMaderaProduccion.getNum_piezas());
        return st;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
