package controlador;

import dao.empleado.EmpleadoCRUD;
import dao.PagoLuzCRUD;
import entidades.empleado.Empleado;
import entidades.PagoLuz;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author rcortes
 */
public class PagoLuzController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        //Llegan url
        String action = request.getParameter("action");
        PagoLuz pagoLuzEC; //Enviar al CRUD
        PagoLuzCRUD pagoLuzCRUD;
        EmpleadoCRUD empleadoCRUD;
        switch (action) {
            case "nuevo":
                try {
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>) empleadoCRUD.listar();
                    request.setAttribute("empleados", empleados);
                    RequestDispatcher view = request.getRequestDispatcher("pagoLuz/nuevoPagoLuz.jsp");
                    view.forward(request, response);
                } catch (Exception ex) {
                    listarPagosLuz(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarPagosLuz(request, response, "");
                break;
            case "modificar":
                pagoLuzEC = new PagoLuz();
                pagoLuzEC.setId_pago_luz(request.getParameter("id_pago_luz"));
                pagoLuzCRUD = new PagoLuzCRUD();
                try {
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>) empleadoCRUD.listar();
                    request.setAttribute("empleados", empleados);
                    PagoLuz pagoLuz = (PagoLuz) pagoLuzCRUD.modificar(pagoLuzEC);
                    request.setAttribute("pagoLuz", pagoLuz);
                    RequestDispatcher view = request.getRequestDispatcher("pagoLuz/actualizarPagoLuz.jsp");
                    view.forward(request, response);
                } catch (Exception ex) {
                    listarPagosLuz(request, response, "error_modificar");
                    Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                pagoLuzEC = new PagoLuz();
                pagoLuzEC.setId_pago_luz(request.getParameter("id_pago_luz"));
                pagoLuzCRUD = new PagoLuzCRUD();
                try {
                    pagoLuzCRUD.eliminar(pagoLuzEC);
                    //enviar mensaje -> eliminado
                    response.sendRedirect("/aserradero/PagoLuzController?action=listar");
                } catch (Exception ex) {
                    listarPagosLuz(request, response, "error_eliminar");
                    Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        PagoLuz pagoLuz;
        PagoLuzCRUD pagoLuzCRUD;
        switch (action) {
            case "nuevo":
                pagoLuz = extraerPagoLuzForm(request);
                pagoLuzCRUD = new PagoLuzCRUD();
                try {
                    pagoLuzCRUD.registrar(pagoLuz);
                    //enviar mensaje -> registrado
                    response.sendRedirect("/aserradero/PagoLuzController?action=listar");
                } catch (Exception ex) {
                    listarPagosLuz(request, response, "error_registrar");
                    Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                pagoLuz = extraerPagoLuzForm(request);
                pagoLuzCRUD = new PagoLuzCRUD();
                try {
                    pagoLuzCRUD.actualizar(pagoLuz);
                    //enviar mensaje -> actualizado
                    response.sendRedirect("/aserradero/PagoLuzController?action=listar");
                } catch (Exception ex) {
                    listarPagosLuz(request, response, "error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List<PagoLuz> pagosluz;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                pagoLuzCRUD = new PagoLuzCRUD();
                try {
                    pagosluz = (List<PagoLuz>) pagoLuzCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("pagosluz", pagosluz);
                    RequestDispatcher view = request.getRequestDispatcher("pagoLuz/pagosluz.jsp");
                    view.forward(request, response);
                } catch (Exception ex) {
                    listarPagosLuz(request, response, "error_buscar_campo");
                    Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
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

    //Mostrar lista de los pagos de luz
    private void listarPagosLuz(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<PagoLuz> pagosluz;
        PagoLuzCRUD pagoLuzcrud = new PagoLuzCRUD();
        try {
            pagosluz = (List<PagoLuz>) pagoLuzcrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("pagosluz", pagosluz);
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view;
            view = request.getRequestDispatcher("pagoLuz/pagosluz.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Extraer datos del formulario
    private PagoLuz extraerPagoLuzForm(HttpServletRequest request) {
        PagoLuz pagoLuz = new PagoLuz();
        pagoLuz.setId_pago_luz(request.getParameter("id_pago_luz"));
        pagoLuz.setFecha(request.getParameter("fecha"));
        pagoLuz.setId_empleado(request.getParameter("id_empleado"));
        pagoLuz.setMonto(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto")))));
        pagoLuz.setObservacion(request.getParameter("observacion"));
        return pagoLuz;
    }
}
