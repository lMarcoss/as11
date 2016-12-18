package controlador;

import dao.AdministradorCRUD;
import dao.EmpleadoCRUD;
import dao.PersonaCRUD;
import entidades.Administrador;
import entidades.Empleado;
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
public class EmpleadoController extends HttpServlet {

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
        Empleado empleadoEC; //Enviar al CRUD
        Empleado empleado; //Respuesta del CRUD
        EmpleadoCRUD empleadoCRUD;
        PersonaCRUD personaCRUD;
        switch(action){
            case "nuevo":
                try {
                    //Enviamos las personas a seleccionar para asignarlos como empleado
                    personaCRUD = new PersonaCRUD();
                    List<Persona> personas;
                    personas = (List<Persona>) personaCRUD.listar();
                    request.setAttribute("personas",personas);
                    
                    //Consultamos la lista de administradores para asignarlos como jefe
                    AdministradorCRUD administradorCRUD = new AdministradorCRUD();
                    List<Administrador> administradores;
                    administradores = (List<Administrador>) administradorCRUD.listar();
                    request.setAttribute("administradores", administradores);
                    
                    RequestDispatcher view = request.getRequestDispatcher("empleado/nuevoEmpleado.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEmpleados(request, response,"error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarEmpleados(request, response,"Lista empleados");
                break;
            case "modificar":
                empleadoEC = extraerClavesEmpleado(request,response);
                empleadoCRUD = new EmpleadoCRUD();
                try {                    
                    //Enviamos empleado a modificar
                    empleado = (Empleado) empleadoCRUD.modificar(empleadoEC);
                    request.setAttribute("empleado",empleado);
                    RequestDispatcher view = request.getRequestDispatcher("empleado/actualizarEmpleado.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEmpleados(request, response, "error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                empleadoEC = extraerClavesEmpleado(request, response);
                empleadoCRUD = new EmpleadoCRUD();
                try {
                    empleadoCRUD.eliminar(empleadoEC);
                    //enviar mensaje -> eliminado
                    response.sendRedirect("/aserradero/EmpleadoController?action=listar");
                } catch (Exception ex) {
                    listarEmpleados(request, response, "error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
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
        Empleado empleado;
        EmpleadoCRUD empleadoCRUD;
        switch(action){
            case "nuevo":
                empleado = extraerEmpleadoForm(request);
                empleadoCRUD = new EmpleadoCRUD();
                try {
                    empleadoCRUD.registrar(empleado);
                    //enviar mensaje -> registrado
                    response.sendRedirect("/aserradero/EmpleadoController?action=listar");
                } catch (Exception ex) {
                    listarEmpleados(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                empleado = extraerEmpleadoForm(request);
                empleadoCRUD = new EmpleadoCRUD();
                try {
                    empleadoCRUD.actualizar(empleado);
                    //enviar mensaje -> actualizado
                    response.sendRedirect("/aserradero/EmpleadoController?action=listar");
                } catch (Exception ex) {
                    listarEmpleados(request, response, "error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Empleado> empleados;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                empleadoCRUD = new EmpleadoCRUD();
                try {
                    empleados = (List<Empleado>)empleadoCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("empleados",empleados);
                    RequestDispatcher view = request.getRequestDispatcher("empleado/empleados.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEmpleados(request, response, "error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<Empleado> empleados;
        EmpleadoCRUD empleadoCrud = new EmpleadoCRUD();
        try {
            empleados = (List<Empleado>)empleadoCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("empleados",empleados);
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("empleado/empleados.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private Empleado extraerEmpleadoForm(HttpServletRequest request) {
        Empleado empleado = new Empleado();
        empleado.setId_empleado(request.getParameter("id_empleado"));
        empleado.setId_persona(request.getParameter("id_persona"));
        empleado.setId_jefe(request.getParameter("id_jefe"));
        empleado.setRol(request.getParameter("rol"));
        empleado.setEstatus(request.getParameter("estatus"));
        return empleado;
    }

    private Empleado extraerClavesEmpleado(HttpServletRequest request, HttpServletResponse response) {
        Empleado empleado = new Empleado();
        empleado.setId_empleado(request.getParameter("id_empleado"));
        empleado.setId_jefe(request.getParameter("id_jefe"));
        empleado.setRol(request.getParameter("rol"));
        return empleado;
    }

}
