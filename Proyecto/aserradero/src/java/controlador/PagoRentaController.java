package controlador;

import dao.EmpleadoCRUD;
import dao.PagoRentaCRUD;
import entidades.Empleado;
import entidades.PagoRenta;
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
public class PagoRentaController extends HttpServlet {

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
        String action = request.getParameter("action");
        PagoRenta pagorentaEC ;
        PagoRenta pagorenta;
        PagoRentaCRUD pagorentaCRUD;
        EmpleadoCRUD empleadoCRUD;
        switch(action){
            case "nuevo":
                try{
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);                    
                    RequestDispatcher view = request.getRequestDispatcher("pagorenta/nuevoPagoRenta.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    System.out.println(ex);
                    listarPagoRentas(request, response, "error_nuevo");
                    Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
            case "listar":
                listarPagoRentas(request, response,"");
                break;

            case "modificar":
                pagorentaEC = new PagoRenta();
                pagorentaEC.setId_pago_renta(request.getParameter("id_pago_renta"));
                pagorentaCRUD = new PagoRentaCRUD();
                try {
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);
                    pagorenta = (PagoRenta) pagorentaCRUD.modificar(pagorentaEC);
                    request.setAttribute("pagorenta",pagorenta);
                    RequestDispatcher view = request.getRequestDispatcher("pagorenta/actualizarPagoRenta.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagoRentas(request, response, "error_modificar");
                    Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                pagorentaEC = new PagoRenta();
                pagorentaEC.setId_pago_renta(request.getParameter("id_pago_renta"));
                pagorentaCRUD = new PagoRentaCRUD();
                try {
                    pagorentaCRUD.eliminar(pagorentaEC);
                    listarPagoRentas(request, response,"eliminado");
                } catch (Exception e) {
                    listarPagoRentas(request, response, "error_eliminar");
                    Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, e);
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
        request.setCharacterEncoding("UTF-8");        ;
        String action = request.getParameter("action");
        PagoRenta pagorenta;
        PagoRentaCRUD pagorentacrud;        
        switch (action){   
          case "nuevo":
              pagorenta = extraerPagoRentaForm(request);
              pagorentacrud = new PagoRentaCRUD();
              try {
                  pagorentacrud.registrar(pagorenta);
                  listarPagoRentas(request, response,"registrado");
              } catch (Exception ex) {
                  listarPagoRentas(request, response,"error_registrar");
                  Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
              }
              break;
          case "actualizar":
              pagorenta = extraerPagoRentaForm(request);
              pagorentacrud = new PagoRentaCRUD();
              try {
                  pagorentacrud.actualizar(pagorenta);
                  listarPagoRentas(request, response,"actualizado");
              } catch (Exception ex) {
                  listarPagoRentas(request, response, "error_actualizar");
                  System.out.println(ex);
                  Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
              }
              break;
          case "buscar":
              List <PagoRenta> pagosrenta;
              String nombre_campo = request.getParameter("nombre_campo");
              String dato = request.getParameter("dato");
              pagorentacrud = new PagoRentaCRUD();
              try {
                  pagosrenta = (List<PagoRenta>)pagorentacrud.buscar(nombre_campo, dato);
                  request.setAttribute("pagosrenta",pagosrenta);
                  RequestDispatcher view = request.getRequestDispatcher("pagorenta/pagosrenta.jsp");
                  view.forward(request,response);
              } catch (Exception ex) {
                  listarPagoRentas(request, response, "error_buscar_campo");
                  Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarPagoRentas(HttpServletRequest request, HttpServletResponse response,String mensaje){
        List<PagoRenta> pagosrenta;
        PagoRentaCRUD pagorentacrud =new PagoRentaCRUD();
        String forward;
        try {
            pagosrenta = (List<PagoRenta>)pagorentacrud.listar();
            request.setAttribute("pagosrenta", pagosrenta);
            request.setAttribute("mensaje", mensaje);
            forward="pagorenta/pagosrenta.jsp";
            RequestDispatcher view=request.getRequestDispatcher(forward);
                view.forward(request, response);
        }catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private PagoRenta extraerPagoRentaForm(HttpServletRequest request){
      PagoRenta pagorenta = new PagoRenta();
      pagorenta.setId_pago_renta(request.getParameter("id_pago_renta"));
      pagorenta.setFecha(request.getParameter("fecha"));
      pagorenta.setNombre_persona(request.getParameter("nombre_persona"));
      pagorenta.setId_empleado(request.getParameter("id_empleado"));
      pagorenta.setMonto(Float.valueOf(request.getParameter("monto")));
      pagorenta.setObservacion(request.getParameter("observacion"));

      return pagorenta;
    }
}
