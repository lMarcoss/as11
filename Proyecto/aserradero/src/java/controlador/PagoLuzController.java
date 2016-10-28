package controlador;

import dao.EmpleadoCRUD;
import dao.PagoLuzCRUD;
import entidades.Empleado;
import entidades.PagoLuz;
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
        PagoLuz pagoluzEC; //Enviar al CRUD
        PagoLuzCRUD pagoluzCRUD;
        EmpleadoCRUD empleadoCRUD;
        switch(action){
            case "nuevo":
                try{
                empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);
                    RequestDispatcher view = request.getRequestDispatcher("pagoluz/nuevoPagoLuz.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagosLuz(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarPagosLuz(request, response,"");
                break;
            case "eliminar":
                pagoluzEC = new PagoLuz();
                pagoluzEC.setId_pago_luz(request.getParameter("id_pago_luz"));
                pagoluzCRUD = new PagoLuzCRUD();
                try {
                    pagoluzCRUD.eliminar(pagoluzEC);
                    listarPagosLuz(request, response,"eliminado");
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
        PagoLuz pagoluz;
        PagoLuzCRUD pagoluzCRUD;            
        switch(action){
            case "nuevo":
                  pagoluz = extraerPagoLuzForm(request);
                  pagoluzCRUD = new PagoLuzCRUD();
                  try {
                      pagoluzCRUD.registrar(pagoluz);
                      listarPagosLuz(request, response,"registrado");
                  } catch (Exception ex) {
                      listarPagosLuz(request, response,"error_registrar");
                      Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  break;
            case "buscar":
                List <PagoLuz> pagosluz;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                pagoluzCRUD = new PagoLuzCRUD();
                try {
                    pagosluz = (List<PagoLuz>)pagoluzCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("pagosluz",pagosluz);
                    RequestDispatcher view = request.getRequestDispatcher("pagoluz/pagosluz.jsp");
                    view.forward(request,response);
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
    private void listarPagosLuz(HttpServletRequest request, HttpServletResponse response,String mensaje){
      List<PagoLuz> pagosluz;
      PagoLuzCRUD pagoluzcrud = new PagoLuzCRUD();
      try {
            pagosluz = (List<PagoLuz>)pagoluzcrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("pagosluz",pagosluz);
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view;
            view = request.getRequestDispatcher("pagoluz/pagosluz.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PagoLuzController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Extraer datos del formulario
    private PagoLuz extraerPagoLuzForm(HttpServletRequest request) {
        PagoLuz pagoluz = new PagoLuz();
        pagoluz.setId_pago_luz(request.getParameter("id_pago_luz"));
        pagoluz.setFecha(request.getParameter("fecha"));
        pagoluz.setId_empleado(request.getParameter("id_empleado"));
        pagoluz.setMonto(Float.valueOf(request.getParameter("monto")));
        pagoluz.setObservacion(request.getParameter("observacion"));
        return pagoluz;
    }
}
