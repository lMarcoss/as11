package controlador;

import dao.AdministradorCRUD;
import dao.PersonaCRUD;
import entidades.Administrador;
import entidades.Persona;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AdministradorController extends HttpServlet {

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
        Administrador administradorEC; //Enviar al CRUD
        Administrador administrador; //Respuesta del CRUD
        AdministradorCRUD administradorCRUD;
        switch(action){
            case "nuevo":
                try {
                    PersonaCRUD personaCRUD = new PersonaCRUD();
                    List<Persona> personas;
                    personas = (List<Persona>) personaCRUD.listar();
                    request.setAttribute("personas",personas);
                    
                    RequestDispatcher view = request.getRequestDispatcher("administrador/nuevoAdministrador.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarAdministradores(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarAdministradores(request, response,"Lista Administradores");
                break;
            case "modificar":
                administradorEC = new Administrador();
                administradorEC.setId_administrador(request.getParameter("id_administrador"));
                administradorCRUD = new AdministradorCRUD();
                try {
                    administrador = (Administrador) administradorCRUD.modificar(administradorEC);
                    request.setAttribute("administrador",administrador);
                    RequestDispatcher view = request.getRequestDispatcher("administrador/actualizarAdministrador.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarAdministradores(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                administradorEC = extraerAdministradorForm(request);
                administradorCRUD = new AdministradorCRUD();
                try {
                    administradorCRUD.eliminar(administradorEC);
                    listarAdministradores(request, response,"eliminado");
                } catch (Exception ex) {
                    listarAdministradores(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        
        String action = request.getParameter("action");
        Administrador administrador;
        AdministradorCRUD administradorCRUD;
        switch(action){
            case "nuevo":
                administrador = extraerAdministradorForm(request);
                administradorCRUD = new AdministradorCRUD();
                try {
                    administradorCRUD.registrar(administrador);
                    listarAdministradores(request, response,"registrado");
                } catch (Exception ex) {
                    listarAdministradores(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                administrador = extraerAdministradorForm(request);
                administradorCRUD = new AdministradorCRUD();
                try {
                    administradorCRUD.actualizar(administrador);
                    listarAdministradores(request, response,"actualizado");
                } catch (Exception ex) {
                    listarAdministradores(request, response,"error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Administrador> administradores;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                administradorCRUD = new AdministradorCRUD();
                try {
                    administradores = (List<Administrador>) administradorCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("administradores",administradores);
                    RequestDispatcher view = request.getRequestDispatcher("administrador/administradores.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarAdministradores(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarAdministradores(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<Administrador> administradores;
        AdministradorCRUD administradorCrud = new AdministradorCRUD();
        try {
            administradores = (List<Administrador>) administradorCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("administradores",administradores);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("administrador/administradores.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private Administrador extraerAdministradorForm(HttpServletRequest request) {
        Administrador administrador = new Administrador();
        administrador.setId_administrador(request.getParameter("id_administrador"));
        return administrador;
    }
}
