package controlador;

import dao.AdministradorCRUD;
import dao.PersonaCRUD;
import entidades.Administrador;
import entidades.Persona;
import java.io.IOException;
import java.math.BigDecimal;
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
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1

        //Acción a realizar
        String action = request.getParameter("action");
        switch (action) {
            /**
             * *************** Respuestas a métodos POST *********************
             */
            case "insertar":
                registrarAdministrador(request, response, action);
                break;
            case "actualizar":
                actualizarAdministrador(request, response, action);
                break;
            case "buscar":
                buscarAdministrador(request, response, action);
                break;
            /**
             * *************** Respuestas a métodos GET *********************
             */
            case "nuevo":
                prepararNuevoAdministrador(request, response);
                break;
            case "listar":
                listarAdministrador(request, response, action);
                break;
            case "modificar":
                modificarAdministrador(request, response);
                break;
            case "eliminar":
                eliminarAdministrador(request, response);
                break;
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
        processRequest(request, response);
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
        processRequest(request, response);
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


    // Extraer datos del formulario
    private Administrador extraerAdministradorForm(HttpServletRequest request) {
        Administrador administrador = new Administrador();
        administrador.setId_administrador(request.getParameter("id_administrador"));
        administrador.setCuenta_inicial(BigDecimal.valueOf((Double.valueOf(request.getParameter("cuenta_inicial")))));
        return administrador;
    }

    private void registrarAdministrador(HttpServletRequest request, HttpServletResponse response, String action) {
        Administrador administrador = extraerAdministradorForm(request);
        AdministradorCRUD administradorCRUD = new AdministradorCRUD();
        try {
            administradorCRUD.registrar(administrador);
            //enviar mensaje -> registrado
            response.sendRedirect("/aserradero/AdministradorController?action=listar");
        } catch (Exception ex) {
            listarAdministrador(request, response, "error_registrar");
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarAdministrador(HttpServletRequest request, HttpServletResponse response, String action) {
        Administrador administrador = extraerAdministradorForm(request);
        AdministradorCRUD administradorCRUD = new AdministradorCRUD();
        try {
            administradorCRUD.actualizar(administrador);
            //enviar mensaje -> actualizado
            response.sendRedirect("/aserradero/AdministradorController?action=listar");
        } catch (Exception ex) {
            listarAdministrador(request, response, "error_actualizar");
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarAdministrador(HttpServletRequest request, HttpServletResponse response, String action) {
        try {
            List<Administrador> administradores;
            String nombre_campo = request.getParameter("nombre_campo");
            String dato = request.getParameter("dato");
            AdministradorCRUD administradorCRUD = new AdministradorCRUD();
            administradores = (List<Administrador>) administradorCRUD.buscar(nombre_campo, dato);
            mostrarListaAdministrador(request, response, administradores, action);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarListaAdministrador(HttpServletRequest request, HttpServletResponse response, List<Administrador> listaAdministrador, String action) {
        request.setAttribute("mensaje", action);
        request.setAttribute("listaAdministrador", listaAdministrador);
        RequestDispatcher view = request.getRequestDispatcher("administrador/listarAdministrador.jsp");
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            System.err.println("No se pudo mostrar la listaAdministrador");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepararNuevoAdministrador(HttpServletRequest request, HttpServletResponse response) {
        try {
            PersonaCRUD personaCRUD = new PersonaCRUD();
            List<Persona> personas;
            personas = (List<Persona>) personaCRUD.listar();
            request.setAttribute("personas", personas);

            RequestDispatcher view = request.getRequestDispatcher("administrador/nuevoAdministrador.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarAdministrador(request, response, "error_nuevo");
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarAdministrador(HttpServletRequest request, HttpServletResponse response, String action) {
        List<Administrador> administradores;
        AdministradorCRUD administradorCrud = new AdministradorCRUD();
        try {
            administradores = (List<Administrador>) administradorCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("administradores", administradores);
            //Enviamos mensaje
            request.setAttribute("mensaje", action);
            RequestDispatcher view = request.getRequestDispatcher("administrador/administradores.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarAdministrador(HttpServletRequest request, HttpServletResponse response) {
        Administrador administradorEC = new Administrador();
        administradorEC.setId_administrador(request.getParameter("id_administrador"));
        AdministradorCRUD administradorCRUD = new AdministradorCRUD();
        try {
            Administrador administrador = (Administrador) administradorCRUD.modificar(administradorEC);
            request.setAttribute("administrador", administrador);
            RequestDispatcher view = request.getRequestDispatcher("administrador/actualizarAdministrador.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarAdministrador(request, response, "error_modificar");
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarAdministrador(HttpServletRequest request, HttpServletResponse response) {
        Administrador administradorEliminar = new Administrador();
        administradorEliminar.setId_administrador(request.getParameter("id_administrador"));

        AdministradorCRUD administradorCRUD = new AdministradorCRUD();
        try {
            administradorCRUD.eliminar(administradorEliminar);
            //enviar mensaje -> eliminado
            response.sendRedirect("/aserradero/AdministradorController?action=listar");
        } catch (Exception ex) {
            listarAdministrador(request, response, "error_eliminar");
            System.out.println(ex);
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
