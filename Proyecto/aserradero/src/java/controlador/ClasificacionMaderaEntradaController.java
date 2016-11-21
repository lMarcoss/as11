package controlador;

import dao.ClasificacionMaderaEntradaCRUD;
import entidades.ClasificacionMaderaEntrada;
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
public class ClasificacionMaderaEntradaController extends HttpServlet {

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
        String action = request.getParameter("action");
        ClasificacionMaderaEntrada clasificacionMaderaEntradaEC ;
        ClasificacionMaderaEntrada clasificacionMaderaEntrada;
        ClasificacionMaderaEntradaCRUD clasificacionMaderaEntradaCRUD;
        switch(action){
            case "listar":
                listarClasificacionMaderaEntradas(request, response,"Clasificacions de madera compra");
                break;

            case "modificar":
                clasificacionMaderaEntradaEC = new ClasificacionMaderaEntrada();
                clasificacionMaderaEntradaEC.setClasificacion(request.getParameter("clasificacion"));
                clasificacionMaderaEntradaCRUD = new ClasificacionMaderaEntradaCRUD();
                try {
                    clasificacionMaderaEntrada = (ClasificacionMaderaEntrada) clasificacionMaderaEntradaCRUD.modificar(clasificacionMaderaEntradaEC);
                    request.setAttribute("clasificacionMaderaEntrada",clasificacionMaderaEntrada);
                    RequestDispatcher view = request.getRequestDispatcher("clasificacionMaderaEntrada/actualizarClasificacionMaderaEntrada.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarClasificacionMaderaEntradas(request, response,"error_modificar");
                    Logger.getLogger(ClasificacionMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                clasificacionMaderaEntradaEC = new ClasificacionMaderaEntrada();
                clasificacionMaderaEntradaEC.setClasificacion(request.getParameter("clasificacion"));
                clasificacionMaderaEntradaCRUD = new ClasificacionMaderaEntradaCRUD();
                try {
                    clasificacionMaderaEntradaCRUD.eliminar(clasificacionMaderaEntradaEC);
                    listarClasificacionMaderaEntradas(request, response,"eliminado");
                } catch (Exception e) {
                    listarClasificacionMaderaEntradas(request, response,"error_eliminar");
                    Logger.getLogger(ClasificacionMaderaEntradaController.class.getName()).log(Level.SEVERE, null, e);
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
        response.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        ClasificacionMaderaEntrada clasificacionMaderaEntrada;
        ClasificacionMaderaEntradaCRUD clasificacionMaderaEntradacrud;
        switch (action){
          case "nuevo":
              clasificacionMaderaEntrada = extraerClasificacionMaderaEntradaForm(request);
              clasificacionMaderaEntradacrud = new ClasificacionMaderaEntradaCRUD();
              try {
                  clasificacionMaderaEntradacrud.registrar(clasificacionMaderaEntrada);
                  listarClasificacionMaderaEntradas(request, response,"registrado");
              } catch (Exception ex) {
                  listarClasificacionMaderaEntradas(request, response,"error_registrar");
                  Logger.getLogger(ClasificacionMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
              }
              break;
          case "actualizar":
              clasificacionMaderaEntrada = extraerClasificacionMaderaEntradaForm(request);
              clasificacionMaderaEntradacrud = new ClasificacionMaderaEntradaCRUD();
              try {
                  clasificacionMaderaEntradacrud.actualizar(clasificacionMaderaEntrada);
                  listarClasificacionMaderaEntradas(request, response,"actualizado");
              } catch (Exception ex) {
                  listarClasificacionMaderaEntradas(request, response,"error_actualizar");
                  System.out.println(ex);
                  Logger.getLogger(ClasificacionMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
              }
              break;
          case "buscar":
              List <ClasificacionMaderaEntrada> clasificacionMaderaEntradas;
              String nombre_campo = request.getParameter("nombre_campo");
              String dato = request.getParameter("dato");
              clasificacionMaderaEntradacrud = new ClasificacionMaderaEntradaCRUD();
              try {
                  clasificacionMaderaEntradas = (List<ClasificacionMaderaEntrada>)clasificacionMaderaEntradacrud.buscar(nombre_campo, dato);
                  request.setAttribute("clasificacionMaderaEntradas",clasificacionMaderaEntradas);
                  RequestDispatcher view = request.getRequestDispatcher("clasificacionMaderaEntrada/clasificacionMaderaEntradas.jsp");
                  view.forward(request,response);
              } catch (Exception ex) {
                  listarClasificacionMaderaEntradas(request, response,"error_buscar_campo");
                  Logger.getLogger(ClasificacionMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarClasificacionMaderaEntradas(HttpServletRequest request, HttpServletResponse response,String mensaje){
        List<ClasificacionMaderaEntrada> clasificacionMaderaEntradas;
        ClasificacionMaderaEntradaCRUD clasificacionMaderaEntradacrud =new ClasificacionMaderaEntradaCRUD();
        String forward;
        try {
            clasificacionMaderaEntradas = (List<ClasificacionMaderaEntrada>)clasificacionMaderaEntradacrud.listar();
            request.setAttribute("clasificacionMaderaEntradas", clasificacionMaderaEntradas);
            request.setAttribute("mensaje", mensaje);
            forward="clasificacionMaderaEntrada/clasificacionMaderaEntradas.jsp";
            RequestDispatcher view=request.getRequestDispatcher(forward);
                view.forward(request, response);
        }catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(ClasificacionMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private ClasificacionMaderaEntrada extraerClasificacionMaderaEntradaForm(HttpServletRequest request){
      ClasificacionMaderaEntrada clasificacionMaderaEntrada = new ClasificacionMaderaEntrada();
      clasificacionMaderaEntrada.setClasificacion(request.getParameter("clasificacion"));
      clasificacionMaderaEntrada.setCosto(BigDecimal.valueOf((Double.valueOf(request.getParameter("costo")))));
      return clasificacionMaderaEntrada;
    }
}
