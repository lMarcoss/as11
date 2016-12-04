package controlador;

import dao.LocalidadCRUD;
import dao.PersonaCRUD;
import entidades.Localidad;
import entidades.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lmarcoss
 */
public class PersonaController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        //Llegan url
        String action = request.getParameter("action");
        Persona personaEC; //Enviar al CRUD
        Persona persona; //Respuesta del CRUD
        PersonaCRUD personaCRUD;
        switch(action){
            case "nuevo":
                try {
                    LocalidadCRUD localidadCRUD = new LocalidadCRUD();
                    List<Localidad> localidades;
                    localidades = (List<Localidad>)localidadCRUD.listar();
                    request.setAttribute("localidades",localidades);
                    RequestDispatcher view = request.getRequestDispatcher("persona/nuevoPersona.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPersonas(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarPersonas(request, response,"");
                break;
            case "modificar":
                personaEC = new Persona();
                personaEC.setId_persona(request.getParameter("id_persona"));
                personaCRUD = new PersonaCRUD();
                try {
                    persona = (Persona) personaCRUD.modificar(personaEC);
                    request.setAttribute("persona",persona);
                    RequestDispatcher view = request.getRequestDispatcher("persona/actualizarPersona.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPersonas(request, response, "error_modificar");
                    Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                personaEC = new Persona();
                personaEC.setId_persona(request.getParameter("id_persona"));
                personaCRUD = new PersonaCRUD();
                try {
                    personaCRUD.eliminar(personaEC);
                    listarPersonas(request, response,"eliminado");
                } catch (Exception ex) {
                    listarPersonas(request, response,"error_eliminar");
                    Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_persona":
                String id_persona = request.getParameter("id_persona");
                buscarPersonaPorId(request, response, id_persona);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Llegan formularios de tipo post
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        String action = request.getParameter("action");
        Persona persona;
        PersonaCRUD personaCRUD;
        switch(action){
            case "nuevo":
                persona = extraerPersonaForm(request);
                personaCRUD = new PersonaCRUD();
                try {
                    personaCRUD.registrar(persona);
                    listarPersonas(request, response,"registrado");
                } catch (Exception ex) {
                    listarPersonas(request, response,"error_registrar");
                    Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                persona = extraerPersonaForm(request);
                personaCRUD = new PersonaCRUD();
                try {
                    personaCRUD.actualizar(persona);
                    listarPersonas(request, response,"actualizado");
                } catch (Exception ex) {
                    listarPersonas(request, response,"error_actualizar");
                    Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Persona> personas;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                personaCRUD = new PersonaCRUD();
                try {
                    personas = (List<Persona>)personaCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("personas",personas);
                    RequestDispatcher view = request.getRequestDispatcher("persona/personas.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPersonas(request, response, "error_buscar_campo");
                    Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private void listarPersonas(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<Persona> personas;
        PersonaCRUD personaCrud = new PersonaCRUD();
        try {
            personas = (List<Persona>)personaCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("personas",personas);
            //Enviar mensaje
            request.setAttribute("mensaje",mensaje);
         
            RequestDispatcher view = request.getRequestDispatcher("persona/personas.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarPersonaPorId(HttpServletRequest request, HttpServletResponse response,String id_persona) {
        List<Persona> personas;
        PersonaCRUD personaCrud = new PersonaCRUD();
        try {
            personas = (List<Persona>)personaCrud.buscarPorId(id_persona);
            //Enviamos las listas al jsp
            request.setAttribute("personas",personas);
            RequestDispatcher view = request.getRequestDispatcher("persona/personas.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            listarPersonas(request, response, "error_buscar_id");
            System.out.println(ex);
            Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Extraer datos del formulario
    private Persona extraerPersonaForm(HttpServletRequest request) {
        Persona persona = new Persona();
        persona.setId_persona(request.getParameter("id_persona"));
        persona.setNombre(request.getParameter("nombre"));
        persona.setApellido_paterno(request.getParameter("apellido_paterno"));
        persona.setApellido_materno(request.getParameter("apellido_materno"));
        persona.setLocalidad(request.getParameter("localidad"));
        persona.setDireccion(request.getParameter("direccion"));
        persona.setSexo(request.getParameter("sexo"));
        persona.setFecha_nacimiento(Date.valueOf(request.getParameter(("fecha_nacimiento"))));
        persona.setTelefono(request.getParameter("telefono"));
        return persona;
    }

}
