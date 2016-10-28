package controlador;

import dao.EntradaMaderaCRUD;
import dao.CostoMaderaEntradaCRUD;
import dao.DetalleCompraCRUD;
import entidades.EntradaMaderaRollo;
import entidades.CostoMaderaEntrada;
import entidades.DetalleCompra;
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
public class DetalleCompraController extends HttpServlet {

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
        DetalleCompra detallecompraEC ;
        DetalleCompra detallecompra;
        DetalleCompraCRUD detallecompraCRUD;
        CostoMaderaEntradaCRUD costomaderacompracrud;
        EntradaMaderaCRUD compracrud;
        switch(action){
          case "nuevo":
              try{
                  costomaderacompracrud = new CostoMaderaEntradaCRUD();
                  List<CostoMaderaEntrada> costomaderacompras;
                  costomaderacompras = (List<CostoMaderaEntrada>)costomaderacompracrud.listar();
                  request.setAttribute("costomaderacompras",costomaderacompras);

                  compracrud = new EntradaMaderaCRUD();
                  List<EntradaMaderaRollo> compras;
                  compras = (List<EntradaMaderaRollo>)compracrud.listarComprasNoPagadas();
                  request.setAttribute("compras",compras);
                  RequestDispatcher view = request.getRequestDispatcher("detallecompra/nuevoDetalleCompra.jsp");
                  view.forward(request,response);
              } catch (Exception ex) {
                  listarDetalleCompras(request, response,"error_nuevo");
                  System.out.println(ex);
                  Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
              }
              break;
          case "listar":
              listarDetalleCompras(request, response,"Detalles compra");
              break;
          case "modificar":
              detallecompraEC = new DetalleCompra();
              detallecompraEC.setId_compra(request.getParameter("id_compra"));
              detallecompraEC.setClasificacion(request.getParameter("clasificacion"));
              detallecompraCRUD = new DetalleCompraCRUD();                    
              try {
                  costomaderacompracrud = new CostoMaderaEntradaCRUD();
                  List<CostoMaderaEntrada> costomaderacompras;
                  costomaderacompras = (List<CostoMaderaEntrada>)costomaderacompracrud.listar();
                  request.setAttribute("costomaderacompras",costomaderacompras);

                  detallecompra = (DetalleCompra) detallecompraCRUD.modificar(detallecompraEC);
                  request.setAttribute("detallecompra",detallecompra);
                  RequestDispatcher view = request.getRequestDispatcher("detallecompra/actualizarDetalleCompra.jsp");
                  view.forward(request,response);
              } catch (Exception ex) {
                  listarDetalleCompras(request, response,"error_modificar");
                  Logger.getLogger(DetalleCompraController.class.getName()).log(Level.SEVERE, null, ex);
              }
                break;
            case "eliminar":
                detallecompraEC = new DetalleCompra();
                detallecompraEC.setId_compra(request.getParameter("id_compra"));
                detallecompraEC.setClasificacion(request.getParameter("clasificacion"));
                detallecompraCRUD = new DetalleCompraCRUD();
                try {
                    detallecompraCRUD.eliminar(detallecompraEC);
                    listarDetalleCompras(request, response,"eliminado");
                } catch (Exception e) {
                    listarDetalleCompras(request, response,"error_eliminar");
                    Logger.getLogger(DetalleCompraController.class.getName()).log(Level.SEVERE, null, e);
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
    DetalleCompra detallecompra;
    DetalleCompraCRUD compracrud;
    switch (action){
      case "nuevo":
          detallecompra = extraerCompraForm(request);
          compracrud = new DetalleCompraCRUD();
          try {
              compracrud.registrar(detallecompra);
              listarDetalleCompras(request, response,"registrado");
          } catch (Exception ex) {
              listarDetalleCompras(request, response,"error_registrar");
              Logger.getLogger(DetalleCompraController.class.getName()).log(Level.SEVERE, null, ex);
          }
          break;
      case "actualizar":
          detallecompra = extraerCompraForm(request);
          compracrud = new DetalleCompraCRUD();
          try {
              compracrud.actualizar(detallecompra);
              listarDetalleCompras(request, response,"actualizado");
          } catch (Exception ex) {
              listarDetalleCompras(request, response,"error_actualizar");
              System.out.println(ex);
              Logger.getLogger(DetalleCompraController.class.getName()).log(Level.SEVERE, null, ex);
          }
          break;
      case "buscar":
          List <DetalleCompra> detallecompras;
          String nombre_campo = request.getParameter("nombre_campo");
          String dato = request.getParameter("dato");
          compracrud = new DetalleCompraCRUD();
          try {
              detallecompras = (List<DetalleCompra>)compracrud.buscar(nombre_campo, dato);
              request.setAttribute("detallecompras",detallecompras);
              RequestDispatcher view = request.getRequestDispatcher("detallecompra/detallecompras.jsp");
              view.forward(request,response);
          } catch (Exception ex) {
              listarDetalleCompras(request, response,"error_buscar_campo");
              Logger.getLogger(DetalleCompraController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarDetalleCompras(HttpServletRequest request, HttpServletResponse response,String mensaje){
        List<DetalleCompra> detallecompras;
        DetalleCompraCRUD compracrud =new DetalleCompraCRUD();
        String forward;
        try {
            detallecompras = (List<DetalleCompra>)compracrud.listar();
            request.setAttribute("detallecompras", detallecompras);
            request.setAttribute("mensaje", mensaje);
            forward="detallecompra/detallecompras.jsp";
            RequestDispatcher view=request.getRequestDispatcher(forward);
                view.forward(request, response);
        }catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(DetalleCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private DetalleCompra extraerCompraForm(HttpServletRequest request){
      DetalleCompra detallecompra = new DetalleCompra();
      detallecompra.setId_compra(request.getParameter("id_compra"));
      detallecompra.setClasificacion(request.getParameter("clasificacion"));
      detallecompra.setVolumen(Float.valueOf(request.getParameter("volumen")));
      detallecompra.setMonto(Float.valueOf(request.getParameter("monto")));
      return detallecompra;
    }

}
