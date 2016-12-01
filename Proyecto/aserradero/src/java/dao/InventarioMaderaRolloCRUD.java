package dao;

import entidades.InventarioMaderaRollo;
import interfaces.OperacionesCRUD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rcortes Modificado por lmarcoss
 */
public class InventarioMaderaRolloCRUD extends Conexion implements OperacionesCRUD {

    @Override
    public void registrar(Object objeto) throws Exception {
        //El inventario se se registra con triggers
    }

    @Override
    public <T> List listar() throws Exception {
        List<InventarioMaderaRollo> inventariomaderaentradas;
        try {
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM INVENTARIO_MADERA_ROLLO WHERE num_piezas > 0")) {
                inventariomaderaentradas = new ArrayList<>();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        InventarioMaderaRollo inventariomaderaentrada = (InventarioMaderaRollo) extraerObject(rs);
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
        } finally {
            cerrarConexion();
        }
        return inventariomaderaentradas;
    }

    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        InventarioMaderaRollo inventariomaderaentrada = new InventarioMaderaRollo();
        inventariomaderaentrada.setId_administrador(rs.getString("id_administrador"));
        inventariomaderaentrada.setNum_piezas(rs.getInt("num_piezas"));
        inventariomaderaentrada.setVolumen_total(rs.getBigDecimal("volumen_total"));
        return inventariomaderaentrada;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        return null;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        //El inventario se actualiza con triggers
    }

    @Override
    public void eliminar(Object objeto) throws Exception {
    }

    @Override
    public <T> List buscar(String nombre_campo, String dato) throws Exception {
        return null;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        return null;
    }
}
