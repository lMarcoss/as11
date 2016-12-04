package controlador;

import dao.AnticipoClienteCRUD;
import dao.ClienteCRUD;
import dao.EmpleadoCRUD;
import entidades.AnticipoCliente;
import entidades.Cliente;
import entidades.Empleado;
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
public class AnticipoClienteController extends HttpServlet {

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
        AnticipoCliente anticipoClienteEC; //Enviar al CRUD
        AnticipoCliente anticipoCliente; //Respuesta del CRUD
        AnticipoClienteCRUD anticipoClienteCRUD;
        switch(action){
            case "nuevo":
                try {
                    ClienteCRUD clienteCRUD= new ClienteCRUD();
                    List<Cliente> clientes;
                    clientes = (List<Cliente>)clienteCRUD.listar();
                    request.setAttribute("clientes",clientes);
                    
                    EmpleadoCRUD empleadoCRUD= new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);
                    
                    RequestDispatcher view = request.getRequestDispatcher("anticipoCliente/nuevoAnticipoCliente.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarAnticipoClientes(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarAnticipoClientes(request, response,"Lista anticipo clientes");
                break;
            case "modificar":
                anticipoClienteEC = new AnticipoCliente();
                anticipoClienteEC.setId_anticipo_c(Integer.valueOf(request.getParameter("id_anticipo_c")));
                anticipoClienteCRUD = new AnticipoClienteCRUD();
                try {
                    anticipoCliente = (AnticipoCliente) anticipoClienteCRUD.modificar(anticipoClienteEC);
                    request.setAttribute("anticipoCliente",anticipoCliente);
                    RequestDispatcher view = request.getRequestDispatcher("anticipoCliente/actualizarAnticipoCliente.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarAnticipoClientes(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(AnticipoClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                anticipoClienteEC= new AnticipoCliente ();
                anticipoClienteEC.setId_anticipo_c(Integer.valueOf(request.getParameter("id_anticipo_c")));
                anticipoClienteCRUD = new AnticipoClienteCRUD();
                try {
                    anticipoClienteCRUD.eliminar(anticipoClienteEC);
                    listarAnticipoClientes(request, response,"eliminado");
                } catch (Exception ex) {
                    listarAnticipoClientes(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(AnticipoClienteController.class.getName()).log(Level.SEVERE, null, ex);
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
        AnticipoCliente anticipoCliente;
        AnticipoClienteCRUD anticipoClienteCRUD;
        switch(action){
            case "nuevo":
                anticipoCliente = extraerAnticipoClienteForm(request);
                anticipoClienteCRUD = new AnticipoClienteCRUD();
                try {
                    anticipoClienteCRUD.registrar(anticipoCliente);
                    listarAnticipoClientes(request, response,"registrado");
                } catch (Exception ex) {
                    listarAnticipoClientes(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(AnticipoClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                anticipoCliente = extraerAnticipoClienteForm(request);
                anticipoClienteCRUD = new AnticipoClienteCRUD();
                try {
                    anticipoClienteCRUD.actualizar(anticipoCliente);
                    listarAnticipoClientes(request, response,"actualizado");
                } catch (Exception ex) {
                    listarAnticipoClientes(request, response,"error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(AnticipoClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <AnticipoCliente> anticipoClientes;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                anticipoClienteCRUD = new AnticipoClienteCRUD();
                try {
                    anticipoClientes = (List<AnticipoCliente>) anticipoClienteCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("anticipoClientes",anticipoClientes);
                    RequestDispatcher view = request.getRequestDispatcher("anticipoCliente/anticipoClientes.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarAnticipoClientes(request, response,"error_buscar_campo");
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
    
    private void listarAnticipoClientes(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<AnticipoCliente> anticipoClientes;
        AnticipoClienteCRUD anticipoClienteCrud = new AnticipoClienteCRUD();
        try {
            anticipoClientes = (List<AnticipoCliente>) anticipoClienteCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("anticipoClientes",anticipoClientes);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("anticipoCliente/anticipoClientes.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(AnticipoClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private AnticipoCliente extraerAnticipoClienteForm(HttpServletRequest request) {
        AnticipoCliente anticipoCliente = new AnticipoCliente();
        anticipoCliente.setId_anticipo_c(Integer.valueOf(request.getParameter("id_anticipo_c")));
        anticipoCliente.setFecha(Date.valueOf(request.getParameter("fecha")));
        anticipoCliente.setId_cliente(request.getParameter("id_cliente"));
        anticipoCliente.setId_empleado(request.getParameter("id_empleado"));
        anticipoCliente.setMonto_anticipo(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto_anticipo")))));
 
        return anticipoCliente;
    }

}
