
package controlador.maderaAserrada;

import dao.maderaAserrada.InventarioMaderaAserradaCRUD;
import entidades.maderaAserrada.InventarioMaderaAserrada;
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
 * @author Marcos
 */
public class InventarioMaderaAserradaController extends HttpServlet {

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
        InventarioMaderaAserrada inventarioMaderaAserradaEC; //Enviar al CRUD
        InventarioMaderaAserradaCRUD inventarioMaderaAserradaCRUD;
        switch(action){
            case "listar":
                listarInventarioMaderaAserrada(request, response,"Lista Inventario Madera Produccion");
                break;
            case "eliminar":
                inventarioMaderaAserradaEC = new InventarioMaderaAserrada();
                inventarioMaderaAserradaEC.setId_madera(request.getParameter("id_madera"));
                inventarioMaderaAserradaCRUD = new InventarioMaderaAserradaCRUD();
                try {
                    inventarioMaderaAserradaCRUD.eliminar(inventarioMaderaAserradaEC);
                    listarInventarioMaderaAserrada(request, response,"eliminado");
                } catch (Exception ex) {
                    listarInventarioMaderaAserrada(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(InventarioMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
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
        InventarioMaderaAserradaCRUD inventarioMaderaAserradaCRUD;
        switch(action){
            case "buscar":
                List <InventarioMaderaAserrada> inventarioMaderaAserrada;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                inventarioMaderaAserradaCRUD = new InventarioMaderaAserradaCRUD();
                try {
                    inventarioMaderaAserrada = (List<InventarioMaderaAserrada>) inventarioMaderaAserradaCRUD.buscar(nombre_campo, dato,"");
                    request.setAttribute("inventarioMaderaAserrada",inventarioMaderaAserrada);
                    RequestDispatcher view = request.getRequestDispatcher("inventarioMaderaAserrada/inventarioMaderaAserrada.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarInventarioMaderaAserrada(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(InventarioMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
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
     private void listarInventarioMaderaAserrada(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<InventarioMaderaAserrada> inventarioMaderaAserrada;
        InventarioMaderaAserradaCRUD inventarioMaderaAserradaCrud = new InventarioMaderaAserradaCRUD();
        try {
            inventarioMaderaAserrada = (List<InventarioMaderaAserrada>)inventarioMaderaAserradaCrud.listar("");
            //Enviamos las listas al jsp
            request.setAttribute("inventarioMaderaAserrada",inventarioMaderaAserrada);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("inventarioMaderaAserrada/inventarioMaderaAserrada.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(InventarioMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private InventarioMaderaAserrada extraerInventarioMaderaProduccionForm(HttpServletRequest request) {
        InventarioMaderaAserrada inventarioMaderaAserrada = new InventarioMaderaAserrada();
        inventarioMaderaAserrada.setId_madera(request.getParameter("id_madera"));
        inventarioMaderaAserrada.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        return inventarioMaderaAserrada;
    }
}
