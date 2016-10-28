package controlador;

import dao.CostoMaderaEntradaCRUD;
import entidades.CostoMaderaEntrada;
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
public class CostoMaderaEntradaController extends HttpServlet {

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
        CostoMaderaEntrada costoMaderaEntradaEC ;
        CostoMaderaEntrada costoMaderaEntrada;
        CostoMaderaEntradaCRUD costoMaderaEntradaCRUD;
        switch(action){
            case "listar":
                listarCostoMaderaEntradas(request, response,"Costos de madera compra");
                break;

            case "modificar":
                costoMaderaEntradaEC = new CostoMaderaEntrada();
                costoMaderaEntradaEC.setClasificacion(request.getParameter("clasificacion"));
                costoMaderaEntradaCRUD = new CostoMaderaEntradaCRUD();
                try {
                    costoMaderaEntrada = (CostoMaderaEntrada) costoMaderaEntradaCRUD.modificar(costoMaderaEntradaEC);
                    request.setAttribute("costoMaderaEntrada",costoMaderaEntrada);
                    RequestDispatcher view = request.getRequestDispatcher("costoMaderaEntrada/actualizarCostoMaderaEntrada.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCostoMaderaEntradas(request, response,"error_modificar");
                    Logger.getLogger(CostoMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                costoMaderaEntradaEC = new CostoMaderaEntrada();
                costoMaderaEntradaEC.setClasificacion(request.getParameter("clasificacion"));
                costoMaderaEntradaCRUD = new CostoMaderaEntradaCRUD();
                try {
                    costoMaderaEntradaCRUD.eliminar(costoMaderaEntradaEC);
                    listarCostoMaderaEntradas(request, response,"eliminado");
                } catch (Exception e) {
                    listarCostoMaderaEntradas(request, response,"error_eliminar");
                    Logger.getLogger(CostoMaderaEntradaController.class.getName()).log(Level.SEVERE, null, e);
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
        CostoMaderaEntrada costoMaderaEntrada;
        CostoMaderaEntradaCRUD costoMaderaEntradacrud;
        switch (action){
          case "nuevo":
              costoMaderaEntrada = extraerCompraForm(request);
              costoMaderaEntradacrud = new CostoMaderaEntradaCRUD();
              try {
                  costoMaderaEntradacrud.registrar(costoMaderaEntrada);
                  listarCostoMaderaEntradas(request, response,"registrado");
              } catch (Exception ex) {
                  listarCostoMaderaEntradas(request, response,"error_registrar");
                  Logger.getLogger(CostoMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
              }
              break;
          case "actualizar":
              costoMaderaEntrada = extraerCompraForm(request);
              costoMaderaEntradacrud = new CostoMaderaEntradaCRUD();
              try {
                  costoMaderaEntradacrud.actualizar(costoMaderaEntrada);
                  listarCostoMaderaEntradas(request, response,"actualizado");
              } catch (Exception ex) {
                  listarCostoMaderaEntradas(request, response,"error_actualizar");
                  System.out.println(ex);
                  Logger.getLogger(CostoMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
              }
              break;
          case "buscar":
              List <CostoMaderaEntrada> costoMaderaEntradas;
              String nombre_campo = request.getParameter("nombre_campo");
              String dato = request.getParameter("dato");
              costoMaderaEntradacrud = new CostoMaderaEntradaCRUD();
              try {
                  costoMaderaEntradas = (List<CostoMaderaEntrada>)costoMaderaEntradacrud.buscar(nombre_campo, dato);
                  request.setAttribute("costoMaderaEntradas",costoMaderaEntradas);
                  RequestDispatcher view = request.getRequestDispatcher("costoMaderaEntrada/costoMaderaEntradas.jsp");
                  view.forward(request,response);
              } catch (Exception ex) {
                  listarCostoMaderaEntradas(request, response,"error_buscar_campo");
                  Logger.getLogger(CostoMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarCostoMaderaEntradas(HttpServletRequest request, HttpServletResponse response,String mensaje){
        List<CostoMaderaEntrada> costoMaderaEntradas;
        CostoMaderaEntradaCRUD costoMaderaEntradacrud =new CostoMaderaEntradaCRUD();
        String forward;
        try {
            costoMaderaEntradas = (List<CostoMaderaEntrada>)costoMaderaEntradacrud.listar();
            request.setAttribute("costoMaderaEntradas", costoMaderaEntradas);
            request.setAttribute("mensaje", mensaje);
            forward="costoMaderaEntrada/costoMaderaEntradas.jsp";
            RequestDispatcher view=request.getRequestDispatcher(forward);
                view.forward(request, response);
        }catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(CostoMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private CostoMaderaEntrada extraerCompraForm(HttpServletRequest request){
      CostoMaderaEntrada costoMaderaEntrada = new CostoMaderaEntrada();
      costoMaderaEntrada.setClasificacion(request.getParameter("clasificacion"));
      costoMaderaEntrada.setCosto(Float.valueOf(request.getParameter("costo")));
      return costoMaderaEntrada;
    }
}
