/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entidades.InventarioMaderaEntrada;
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
public class InventarioMaderaEntradaCRUD extends Conexion implements OperacionesCRUD{
    @Override
    public void registrar(Object objeto) throws Exception {
//        InventarioMaderaEntrada inventariomaderaentrada = (InventarioMaderaEntrada) objeto;
//        try {
//            this.abrirConexion();
//            PreparedStatement st = this.conexion.prepareStatement(""
//                    + "INSERT INTO INVENTARIO_MADERA_ENTRADA (clasificacion, volumen) values (?,?)");
//            st = cargarObject(st, inventariomaderaentrada);
//            st.executeUpdate();
//        }catch(Exception e){
//            System.err.println(e);
//            throw e;
//        }finally{
//            this.cerrarConexion();
//        }
    }

    @Override
    public <T> List listar() throws Exception {
        List<InventarioMaderaEntrada> inventariomaderaentradas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM INVENTARIO_MADERA_ENTRADA;")){
                inventariomaderaentradas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        InventarioMaderaEntrada inventariomaderaentrada = (InventarioMaderaEntrada) extraerObject(rs);
                        inventariomaderaentradas.add(inventariomaderaentrada);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } catch (Exception e) {
                inventariomaderaentradas = null;
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }finally{
            cerrarConexion();
        }
        return inventariomaderaentradas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
//        InventarioMaderaEntrada inventariomaderaentradaM = (InventarioMaderaEntrada) objeto;
//        InventarioMaderaEntrada inventariomaderaentrada = null;
//        this.abrirConexion();
//        try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM INVENTARIO_MADERA_ENTRADA WHERE clasificacion = ?")) {
//            st.setString(1, inventariomaderaentradaM.getClasificacion());
//            try (ResultSet rs = st.executeQuery()) {
//                while (rs.next()) {
//                    inventariomaderaentrada = (InventarioMaderaEntrada) extraerObject(rs);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return inventariomaderaentrada;
        return null;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
//        InventarioMaderaEntrada inventariomaderaentrada = (InventarioMaderaEntrada) objeto;
//        try {
//            this.abrirConexion();
//            PreparedStatement st = this.conexion.prepareStatement("UPDATE INVENTARIO_MADERA_ENTRADA SET volumen = ? WHERE clasificacion = ?");
//            st.setString(1, String.valueOf(inventariomaderaentrada.getVolumen()));
//            st.setString(2, String.valueOf(inventariomaderaentrada.getClasificacion()));
//            st.executeUpdate();
//        }catch(Exception e){
//            System.out.println(e);
//            throw e;
//        }finally{
//            this.cerrarConexion();
//        }
    }

    @Override
    public void eliminar(Object objeto) throws Exception {
//        InventarioMaderaEntrada inventariomaderaentrada = (InventarioMaderaEntrada) objeto;
//        try {
//            this.abrirConexion();
//            PreparedStatement st = this.conexion.prepareStatement("DELETE FROM INVENTARIO_MADERA_ENTRADA WHERE clasificacion = ?");
//            st.setString(1, inventariomaderaentrada.getClasificacion());
//            st.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e);
//            throw e;
//        }finally{
//            this.cerrarConexion();
//        }
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
//        List<InventarioMaderaEntrada> inventariomaderaentradas;
//        try {
//            this.abrirConexion();
//            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM INVENTARIO_MADERA_ENTRADA WHERE "+nombre_campo+" like ?")){
//                st.setString(1, "%"+dato+"%");
//                inventariomaderaentradas = new ArrayList<>();
//                try (ResultSet rs = st.executeQuery()){
//                    while (rs.next()) {
//                        InventarioMaderaEntrada inventariomaderaentrada = (InventarioMaderaEntrada) extraerObject(rs);
//                        inventariomaderaentradas.add(inventariomaderaentrada);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            throw e;
//        }finally{
//            this.cerrarConexion();
//        }
//        return inventariomaderaentradas;
        return null;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        InventarioMaderaEntrada inventariomaderaentrada = new InventarioMaderaEntrada();
        inventariomaderaentrada.setNum_piezas(rs.getInt("num_piezas"));        
        inventariomaderaentrada.setVolumen_total(rs.getFloat("volumen_total"));
        return inventariomaderaentrada;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        InventarioMaderaEntrada inventariomaderaentrada = (InventarioMaderaEntrada) objeto;
        st.setInt(1,inventariomaderaentrada.getNum_piezas());
        st.setFloat(2,inventariomaderaentrada.getVolumen_total());
        return st;
    }
}
