package controlador;

import dao.AdministradorCRUD;
import dao.PersonaCRUD;
import dao.ProveedorCRUD;
import entidades.Administrador;
import entidades.Persona;
import entidades.Proveedor;
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
public class ProveedorController extends HttpServlet {

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
        Proveedor proveedorEC; //Enviar al CRUD
        Proveedor proveedor; //Respuesta del CRUD
        ProveedorCRUD proveedorCRUD;
        PersonaCRUD personaCRUD;
        switch(action){
            case "nuevo":
                try {
                    //consultamos la lista de personas para registrarlos como proveedor
                    personaCRUD = new PersonaCRUD();
                    List<Persona> personas;
                    personas = (List<Persona>)personaCRUD.listar();
                    request.setAttribute("personas",personas);
                    
                    //Consultamos la lista de administradores para asignarlos como jefe
                    AdministradorCRUD administradorCRUD = new AdministradorCRUD();
                    List<Administrador> administradores;
                    administradores = (List<Administrador>) administradorCRUD.listar();
                    request.setAttribute("administradores", administradores);
                    
                    RequestDispatcher view = request.getRequestDispatcher("proveedor/nuevoProveedor.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProveedores(request, response,"error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarProveedores(request, response,"Lista de proveedores");
                break;
            case "modificar":
                proveedorEC = extraerClavesProveedor(request,response);
                proveedorCRUD = new ProveedorCRUD();
                try {
                    proveedor = (Proveedor) proveedorCRUD.modificar(proveedorEC);
                    request.setAttribute("proveedor",proveedor);
                    RequestDispatcher view = request.getRequestDispatcher("proveedor/actualizarProveedor.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProveedores(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                proveedorEC = extraerClavesProveedor(request, response);
                proveedorCRUD = new ProveedorCRUD();
                try {
                    proveedorCRUD.eliminar(proveedorEC);
                    listarProveedores(request, response,"eliminado");
                } catch (Exception ex) {
                    listarProveedores(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
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
        //Llegan formularios de tipo post
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        String action = request.getParameter("action");
        Proveedor proveedor;
        ProveedorCRUD proveedorCRUD;
        switch(action){
            case "nuevo":
                proveedor = extraerProveedorForm(request);
                proveedorCRUD = new ProveedorCRUD();
                try {
                    proveedorCRUD.registrar(proveedor);
                    listarProveedores(request, response,"registrado");
                } catch (Exception ex) {
                    listarProveedores(request, response,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                proveedor = extraerProveedorForm(request);
                proveedorCRUD = new ProveedorCRUD();
                try {
                    proveedorCRUD.actualizar(proveedor);
                    listarProveedores(request, response,"actualizado");
                } catch (Exception ex) {
                    listarProveedores(request, response,"error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Proveedor> proveedores;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                proveedorCRUD = new ProveedorCRUD();
                try {
                    proveedores = (List<Proveedor>)proveedorCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("proveedores",proveedores);
                    RequestDispatcher view = request.getRequestDispatcher("proveedor/proveedores.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProveedores(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarProveedores(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<Proveedor> proveedores;
        ProveedorCRUD proveedorCRUD = new ProveedorCRUD();
        try {
            proveedores = (List<Proveedor>)proveedorCRUD.listar();
            //Enviamos las listas al jsp
            request.setAttribute("proveedores",proveedores);
            //enviamos mensaje
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("proveedor/proveedores.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private Proveedor extraerProveedorForm(HttpServletRequest request) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId_persona(request.getParameter("id_persona"));
        proveedor.setId_jefe(request.getParameter("id_jefe"));
        return proveedor;
    }

    private Proveedor extraerClavesProveedor(HttpServletRequest request, HttpServletResponse response) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId_proveedor(request.getParameter("id_proveedor"));
        proveedor.setId_jefe(request.getParameter("id_jefe"));
        return proveedor;
    }

}
