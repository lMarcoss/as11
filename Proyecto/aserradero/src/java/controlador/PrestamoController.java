package controlador;

import dao.EmpleadoCRUD;
import dao.PersonaCRUD;
import dao.PrestamoCRUD;
import entidades.Empleado;
import entidades.Persona;
import entidades.Prestamo;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
public class PrestamoController extends HttpServlet {

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
        Prestamo prestamoEC; //Enviar al CRUD
        Prestamo prestamo; //Respuesta del CRUD
        PrestamoCRUD prestamoCRUD;
        switch(action){
            case "nuevo":
                try {
                    // enviamos la lista de personas que pueden ser prestadores
                    PersonaCRUD personaCRUD = new PersonaCRUD();
                    List<Persona> personas = (List<Persona>)personaCRUD.listar();
                    request.setAttribute("personas",personas);
                    
                    //Consultamos la lista de administradores para asignarlos al prestador
                    EmpleadoCRUD empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>) empleadoCRUD.listar();
                    request.setAttribute("empleados", empleados);
                    
                    RequestDispatcher view = request.getRequestDispatcher("prestamo/nuevoPrestamo.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPrestamos(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar": // se muestran los préstamos por fecha e interes
                listarPrestamos(request, response,"");
                break;
            case "listar_total": // se muestra el total de prestamo por persona
                listarPrestamoPorPersona(request, response,"");
                break;
            case "modificar":
                prestamoEC = new Prestamo();
                prestamoEC.setId_prestamo(Integer.valueOf(request.getParameter("id_prestamo")));
                prestamoCRUD = new PrestamoCRUD();
                try {
                    // enviamos la lista de personas que pueden ser prestadores
                    PersonaCRUD personaCRUD = new PersonaCRUD();
                    List<Persona> personas = (List<Persona>)personaCRUD.listar();
                    request.setAttribute("personas",personas);
                    
                    prestamo = (Prestamo) prestamoCRUD.modificar(prestamoEC);
                    request.setAttribute("prestamo",prestamo);
                    RequestDispatcher view = request.getRequestDispatcher("prestamo/actualizarPrestamo.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPrestamos(request, response, "error_modificar");
                    Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                prestamoEC = new Prestamo();
                prestamoEC.setId_prestamo(Integer.valueOf(request.getParameter("id_prestamo")));
                prestamoCRUD = new PrestamoCRUD();
                try {
                    prestamoCRUD.eliminar(prestamoEC);
                    listarPrestamos(request, response,"eliminado");
                } catch (Exception ex) {
                    listarPrestamos(request, response,"error_eliminar");
                    Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_prestamo":
                String id_prestamo = request.getParameter("id_prestamo");
                buscarPrestamoPorId(request, response, id_prestamo);
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
        // variables
        Prestamo prestamo;
        PrestamoCRUD prestamoCRUD;
        //variables de búsqueda
        String nombre_campo;
        String dato;
        switch(action){
            case "nuevo":
                prestamo = extraerPrestamoForm(request);
                prestamoCRUD = new PrestamoCRUD();
                try {
                    prestamoCRUD.registrar(prestamo);
                    listarPrestamos(request, response,"registrado");
                } catch (Exception ex) {
                    listarPrestamos(request, response,"error_registrar");
                    Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                prestamo = extraerPrestamoForm(request);
                prestamoCRUD = new PrestamoCRUD();
                try {
                    prestamoCRUD.actualizar(prestamo);
                    listarPrestamos(request, response,"actualizado");
                } catch (Exception ex) {
                    listarPrestamos(request, response,"error_actualizar");
                    Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Prestamo> prestamos;
                nombre_campo = request.getParameter("nombre_campo");
                dato = request.getParameter("dato");
                prestamoCRUD = new PrestamoCRUD();
                try {
                    prestamos = (List<Prestamo>)prestamoCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("prestamos",prestamos);
                    RequestDispatcher view = request.getRequestDispatcher("prestamo/prestamos.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPrestamos(request, response, "error_buscar_campo");
                    Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_interes_total":
                buscarMontoPPersona(request, response,"buscar_interes_total");
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
    
    private void listarPrestamos(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<Prestamo> prestamos;
        PrestamoCRUD prestamoCrud = new PrestamoCRUD();
        try {
            prestamos = (List<Prestamo>)prestamoCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("prestamos",prestamos);
            //Enviar mensaje
            request.setAttribute("mensaje",mensaje);
         
            RequestDispatcher view = request.getRequestDispatcher("prestamo/prestamos.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarPrestamoPorId(HttpServletRequest request, HttpServletResponse response,String id_prestamo) {
        List<Prestamo> prestamos;
        PrestamoCRUD prestamoCrud = new PrestamoCRUD();
        try {
            prestamos = (List<Prestamo>)prestamoCrud.buscarPorId(id_prestamo);
            //Enviamos las listas al jsp
            request.setAttribute("prestamos",prestamos);
            RequestDispatcher view = request.getRequestDispatcher("prestamo/prestamos.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            listarPrestamos(request, response, "error_buscar_id");
            System.out.println(ex);
            Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Extraer datos del formulario
    private Prestamo extraerPrestamoForm(HttpServletRequest request) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId_prestamo(Integer.valueOf(request.getParameter("id_prestamo")));
        prestamo.setFecha(Date.valueOf(request.getParameter("fecha")));
        prestamo.setId_prestador(request.getParameter("id_prestador"));
        prestamo.setId_empleado(request.getParameter("id_empleado"));
        prestamo.setMonto(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto")))));
        prestamo.setInteres(Integer.valueOf(request.getParameter("interes")));
        return prestamo;
    }

    private void listarPrestamoPorPersona(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<Prestamo> prestamos;
        PrestamoCRUD prestamoCrud = new PrestamoCRUD();
        try {
            prestamos = (List<Prestamo>)prestamoCrud.listarPrestamoPorPersona();
            //Enviamos las listas al jsp
            request.setAttribute("prestamos",prestamos);
            //Enviar mensaje
            request.setAttribute("mensaje",mensaje);
         
            RequestDispatcher view = request.getRequestDispatcher("prestamo/montoPorPersona.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarMontoPPersona(HttpServletRequest request, HttpServletResponse response, String buscar_interes_total) {
        List <Prestamo> prestamos;
        String nombre_campo = request.getParameter("nombre_campo");
        String dato = request.getParameter("dato");
        PrestamoCRUD prestamoCRUD = new PrestamoCRUD();
        try {
            prestamos = (List<Prestamo>)prestamoCRUD.buscarMontoPorPersona(nombre_campo, dato);
            request.setAttribute("prestamos",prestamos);
            RequestDispatcher view = request.getRequestDispatcher("prestamo/montoPorPersona.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            listarPrestamos(request, response, "error_buscar_campo");
            Logger.getLogger(PrestamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
