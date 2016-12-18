package dao;

import entidades.Persona;
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
public class PersonaCRUD extends Conexion implements OperacionesCRUD{

    @Override
    public void registrar(Object objeto) throws Exception {
        Persona persona = (Persona) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st = this.conexion.prepareStatement(
                    "INSERT INTO PERSONA (id_persona,nombre,apellido_paterno,apellido_materno,localidad,direccion,sexo,fecha_nacimiento,telefono) VALUES (?,?,?,?,?,?,?,?,?)");
            st = cargarObject(st, persona);
            st.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
    }

    @Override
    public <T> List listar(String id_jefe) throws Exception {
        List<Persona> personas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONA")) {
                personas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Persona persona = (Persona) extraerObject(rs);
                        personas.add(persona);
                    }
                }
            }catch(Exception e){
                personas = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return personas;
    }

    @Override
    public Object modificar(Object objeto) throws Exception {
        Persona personaM = (Persona) objeto;
        Persona persona = null;
        this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONA WHERE id_persona = ?")) {
                st.setString(1, personaM.getId_persona());
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        persona = (Persona) extraerObject(rs);
                    }
                }
            }
        return persona;
    }

    @Override
    public void actualizar(Object objeto) throws Exception {
        Persona persona = (Persona) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("UPDATE PERSONA SET nombre = ?,apellido_paterno = ?,apellido_materno = ?,localidad = ?, direccion = ?, sexo = ?,fecha_nacimiento = ?, telefono = ? WHERE id_persona = ?");
            st.setString(1,persona.getNombre());
            st.setString(2,persona.getApellido_paterno());
            st.setString(3,persona.getApellido_materno());
            st.setString(4,persona.getLocalidad());
            st.setString(5,persona.getDireccion());
            st.setString(6,persona.getSexo());
            st.setDate(7,persona.getFecha_nacimiento());
            st.setString(8,persona.getTelefono());
            st.setString(9,persona.getId_persona());
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
        Persona persona = (Persona) objeto;
        try{
            this.abrirConexion();
            PreparedStatement st= this.conexion.prepareStatement("DELETE FROM PERSONA WHERE id_persona = ?");
            st.setString(1,persona.getId_persona());
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
        List<Persona> personas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONA WHERE "+nombre_campo+" like ?")) {
                st.setString(1, "%"+dato+"%");
                personas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Persona persona = (Persona) extraerObject(rs);
                        personas.add(persona);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return personas;
    }
    
    @Override
    public Object extraerObject(ResultSet rs) throws SQLException {
        Persona persona = new Persona();
        persona.setId_persona(rs.getString("id_persona"));
        persona.setNombre(rs.getString("nombre"));
        persona.setApellido_paterno(rs.getString("apellido_paterno"));
        persona.setApellido_materno(rs.getString("apellido_materno"));
        persona.setLocalidad(rs.getString("localidad"));
        persona.setDireccion(rs.getString("direccion"));
        persona.setSexo(rs.getString("sexo"));
        persona.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        persona.setTelefono(rs.getString("telefono"));
        return persona;
    }

    @Override
    public PreparedStatement cargarObject(PreparedStatement st, Object objeto) throws SQLException {
        Persona persona = (Persona) objeto;
        st.setString(1,persona.getId_persona());
        st.setString(2,persona.getNombre());
        st.setString(3,persona.getApellido_paterno());
        st.setString(4,persona.getApellido_materno());
        st.setString(5,persona.getLocalidad());
        st.setString(6,persona.getDireccion());
        st.setString(7,persona.getSexo());
        st.setDate(8,persona.getFecha_nacimiento());
        st.setString(9,persona.getTelefono());
        return st;
    }
    
    public List<Persona> buscarPorId(String id_persona) throws Exception {
        List<Persona> personas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM PERSONA WHERE id_persona = ?")) {
                st.setString(1, id_persona);
                personas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Persona persona = (Persona) extraerObject(rs);
                        personas.add(persona);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        }
        return personas;
    }
    public <T> List listarPersonasParaAdmin() throws Exception {
        List<Persona> personas;
        try{
            this.abrirConexion();
            try (PreparedStatement st = this.conexion.prepareStatement(
                    "SELECT * FROM PERSONA WHERE id_persona NOT IN (SELECT SUBSTRING(id_administrador,1,18) FROM ADMINISTRADOR)")) {
                personas = new ArrayList();
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Persona persona = (Persona) extraerObject(rs);
                        personas.add(persona);
                    }
                }
            }catch(Exception e){
                personas = null;
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }finally{
            this.cerrarConexion();
        } 
        return personas;
    }
}
