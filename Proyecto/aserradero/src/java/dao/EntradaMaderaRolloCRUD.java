package dao;

import entidades.EntradaMaderaRollo;
import interfaces.OperacionesCRUD;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rcortes
 * Modificador por lmarcoss
 */
public class EntradaMaderaRolloCRUD  extends Conexion implements OperacionesCRUD{
    @Override
    public void registrar(Object objeto) throws Exception {
        EntradaMaderaRollo entrada = (EntradaMaderaRollo) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO ENTRADA_MADERA_ROLLO (fecha,id_proveedor,id_chofer,id_empleado,num_piezas,volumen_primario,costo_primario,volumen_secundario,costo_secundario,volumen_terciario,costo_terciario,id_pago ) values(?,?,?,?,?,?,null,?,null,?,null,?)");
            st = cargarObject(st, entrada);
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
        List<EntradaMaderaRollo> entradas = null;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO")){
                entradas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        EntradaMaderaRollo entrada = (EntradaMaderaRollo) extraerObject(rs);
                        entradas.add(entrada);
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
        return entradas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        EntradaMaderaRollo entradaM = (EntradaMaderaRollo) objeto;
        EntradaMaderaRollo entrada = null;
        this.abrirConexion();
        try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_entrada = ?")) {
            st.setInt(1, entradaM.getId_entrada());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entrada = (EntradaMaderaRollo) extraerObject(rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return entrada;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        EntradaMaderaRollo entrada = (EntradaMaderaRollo) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("UPDATE ENTRADA_MADERA_ROLLO SET num_piezas = ?, volumen_primario = ?, volumen_secundario = ?, volumen_terciario = ? WHERE id_entrada = ?");
            st.setInt(1, entrada.getNum_piezas());
            st.setBigDecimal(2, entrada.getVolumen_primario());
            st.setBigDecimal(3, entrada.getVolumen_secundario());
            st.setBigDecimal(4, entrada.getVolumen_terciario());
            st.setInt(5, entrada.getId_entrada());
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
        EntradaMaderaRollo entrada = (EntradaMaderaRollo) objeto;
        try {
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM ENTRADA_MADERA_ROLLO WHERE id_entrada = ?");
            st.setInt(1, entrada.getId_entrada());
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
        List<EntradaMaderaRollo> entradas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO WHERE "+nombre_campo+" like ?")){
                st.setString(1, "%"+dato+"%");
                entradas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        EntradaMaderaRollo entrada = (EntradaMaderaRollo) extraerObject(rs);
                        entradas.add(entrada);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return entradas;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        int dMonto = 2;     // cantidad de Decimales para el Costo
        int dVolumen = 3;   // cantidad de decimaes para el volumen
        EntradaMaderaRollo entrada = new EntradaMaderaRollo();
        entrada.setId_entrada(rs.getInt("id_entrada"));
        entrada.setFecha(rs.getDate("fecha"));
        entrada.setId_proveedor(rs.getString("id_proveedor"));
        entrada.setProveedor(rs.getString("proveedor"));
        entrada.setId_chofer(rs.getString("id_chofer"));
        entrada.setChofer(rs.getString("chofer"));
        entrada.setId_empleado(rs.getString("id_empleado"));
        entrada.setEmpleado(rs.getString("empleado"));
        entrada.setId_jefe(rs.getString("id_jefe"));
        entrada.setNum_piezas(rs.getInt("num_piezas"));
        entrada.setVolumen_primario(rs.getBigDecimal("volumen_primario"));
        entrada.setCosto_primario(rs.getBigDecimal("costo_primario"));
        entrada.setVolumen_secundario(rs.getBigDecimal("volumen_secundario"));
        entrada.setCosto_secundario(rs.getBigDecimal("costo_secundario"));
        entrada.setVolumen_terciario(rs.getBigDecimal("volumen_terciario"));
        entrada.setCosto_terciario(rs.getBigDecimal("costo_terciario"));
        BigDecimal VolumenTotal = rs.getBigDecimal("volumen_total");
        BigDecimal montoTotal = rs.getBigDecimal("monto_total");
        VolumenTotal = VolumenTotal.setScale(dVolumen, BigDecimal.ROUND_DOWN);
        montoTotal = montoTotal.setScale(dMonto, BigDecimal.ROUND_DOWN);
        entrada.setVolumen_total(VolumenTotal);
        entrada.setMonto_total(montoTotal);
        entrada.setId_pago(rs.getInt("id_pago"));
        
        return entrada;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        EntradaMaderaRollo entrada = (EntradaMaderaRollo) objeto;
        st.setDate(1, entrada.getFecha());
        st.setString(2, entrada.getId_proveedor());
        st.setString(3, entrada.getId_chofer());        
        st.setString(4, entrada.getId_empleado());
        st.setInt(5, entrada.getNum_piezas());
        st.setBigDecimal(6, entrada.getVolumen_primario());
        st.setBigDecimal(7, entrada.getVolumen_secundario());
        st.setBigDecimal(8, entrada.getVolumen_terciario());
        st.setInt(9, 0);
        return st;
    }
}
