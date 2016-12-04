package controlador;

import dao.EmpleadoCRUD;
import dao.PagoEmpleadoCRUD;
import entidades.Empleado;
import entidades.PagoEmpleado;
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
 * @author Marcos
 */
public class PagoEmpleadoController extends HttpServlet {

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
        PagoEmpleado pagoEmpleadoEC; //Enviar al CRUD
        PagoEmpleado pagoEmpleado; //Respuesta del CRUD
        PagoEmpleadoCRUD pagoEmpleadoCRUD;
        switch(action){
            case "nuevo":
                try {
                    // Enviamos la lista de empleados
                    EmpleadoCRUD empleadoCRUD= new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);
                    
                    RequestDispatcher view = request.getRequestDispatcher("pagoEmpleado/nuevoPagoEmpleado.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagoEmpleados(request, response,"error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarPagoEmpleados(request, response,"Lista pago empleados");
                break;
            case "modificar":
                pagoEmpleadoEC = new PagoEmpleado();
                pagoEmpleadoEC.setId_pago_empleado(Integer.valueOf(request.getParameter("id_pago_empleado")));
                pagoEmpleadoCRUD = new PagoEmpleadoCRUD();
                try {
                    //enviamos el pagoEmpleado a modificar
                    pagoEmpleado = (PagoEmpleado) pagoEmpleadoCRUD.modificar(pagoEmpleadoEC);
                    request.setAttribute("pagoEmpleado",pagoEmpleado);
                    
                    // Enviamos la lista de empleados
                    EmpleadoCRUD empleadoCRUD= new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);
                    
                    RequestDispatcher view = request.getRequestDispatcher("pagoEmpleado/actualizarPagoEmpleado.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagoEmpleados(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(PagoEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                pagoEmpleadoEC= new PagoEmpleado ();
                pagoEmpleadoEC.setId_pago_empleado(Integer.valueOf(request.getParameter("id_pago_empleado")));
                
                pagoEmpleadoCRUD = new PagoEmpleadoCRUD();
                try {
                    pagoEmpleadoCRUD.eliminar(pagoEmpleadoEC);
                    listarPagoEmpleados(request, response,"eliminado");
                } catch (Exception ex) {
                    listarPagoEmpleados(request, response, "error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(PagoEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
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
        PagoEmpleado pagoEmpleado;
        PagoEmpleadoCRUD pagoEmpleadoCRUD;
        switch(action){
            case "nuevo":
              pagoEmpleado = extraerPagoEmpleadoForm(request);
                pagoEmpleadoCRUD = new PagoEmpleadoCRUD();
                try {
                    pagoEmpleadoCRUD.registrar(pagoEmpleado);
                    listarPagoEmpleados(request, response,"registrado");
                } catch (Exception ex) {
                    listarPagoEmpleados(request, response,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(PagoEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                pagoEmpleado = extraerPagoEmpleadoForm(request);
                pagoEmpleadoCRUD = new PagoEmpleadoCRUD();
                try {
                    pagoEmpleadoCRUD.actualizar(pagoEmpleado);
                    listarPagoEmpleados(request, response,"actualizado");
                } catch (Exception ex) {
                    listarPagoEmpleados(request, response,"error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(PagoEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                 List <PagoEmpleado> pagoEmpleados;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                pagoEmpleadoCRUD = new PagoEmpleadoCRUD();
                try {
                    pagoEmpleados = (List<PagoEmpleado>) pagoEmpleadoCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("pagoEmpleados",pagoEmpleados);
                    RequestDispatcher view = request.getRequestDispatcher("pagoEmpleado/pagoEmpleados.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagoEmpleados(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(AnticipoClienteController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarPagoEmpleados(HttpServletRequest request, HttpServletResponse response, String mensaje) {
     List<PagoEmpleado> pagoEmpleados;
     PagoEmpleadoCRUD pagoEmpleadoCrud = new PagoEmpleadoCRUD();
     try {
         pagoEmpleados = (List<PagoEmpleado>) pagoEmpleadoCrud.listar();
         //Enviamos las listas al jsp
         request.setAttribute("pagoEmpleados",pagoEmpleados);
         //Enviamos mensaje
         request.setAttribute("mensaje", mensaje);
         RequestDispatcher view = request.getRequestDispatcher("pagoEmpleado/pagoEmpleados.jsp");
         view.forward(request,response);
     } catch (Exception ex) {
         System.out.println(ex);
         Logger.getLogger(PagoEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    
    // Extraer datos del formulario
    private PagoEmpleado extraerPagoEmpleadoForm(HttpServletRequest request) {
        PagoEmpleado pagoEmpleado = new PagoEmpleado();
        pagoEmpleado.setId_pago_empleado(Integer.valueOf(request.getParameter("id_pago_empleado")));
        pagoEmpleado.setFecha(Date.valueOf(request.getParameter("fecha")));
        pagoEmpleado.setId_empleado(request.getParameter("id_empleado"));
        pagoEmpleado.setMonto(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto")))));
        pagoEmpleado.setObservacion(request.getParameter("observacion"));
        return pagoEmpleado;
    }
    

}
