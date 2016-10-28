
package controlador;

import dao.InventarioMaderaProduccionCRUD;
import entidades.InventarioMaderaProduccion;
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
public class InventarioMaderaProduccionController extends HttpServlet {

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
        InventarioMaderaProduccion inventarioMaderaProduccionEC; //Enviar al CRUD
        InventarioMaderaProduccionCRUD inventarioMaderaProduccionCRUD;
        switch(action){
            case "listar":
                listarInventarioMaderaProducciones(request, response,"Lista Inventario Madera Produccion");
                break;
            case "eliminar":
                inventarioMaderaProduccionEC = new InventarioMaderaProduccion();
                inventarioMaderaProduccionEC.setId_madera(request.getParameter("id_madera"));
                inventarioMaderaProduccionCRUD = new InventarioMaderaProduccionCRUD();
                try {
                    inventarioMaderaProduccionCRUD.eliminar(inventarioMaderaProduccionEC);
                    listarInventarioMaderaProducciones(request, response,"eliminado");
                    
                } catch (Exception ex) {
                    listarInventarioMaderaProducciones(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(InventarioMaderaProduccionController.class.getName()).log(Level.SEVERE, null, ex);
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
        InventarioMaderaProduccion inventarioMaderaProduccion;
        InventarioMaderaProduccionCRUD inventarioMaderaProduccionCRUD;
        switch(action){
            case "nuevo":
                inventarioMaderaProduccion = extraerInventarioMaderaProduccionForm(request);
                inventarioMaderaProduccionCRUD = new InventarioMaderaProduccionCRUD();
                try {
                    inventarioMaderaProduccionCRUD.registrar(inventarioMaderaProduccion);
                    listarInventarioMaderaProducciones(request, response,"registrado");
                } catch (Exception ex) {
                    listarInventarioMaderaProducciones(request, response,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(InventarioMaderaProduccionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <InventarioMaderaProduccion> inventarioMaderaProducciones;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                inventarioMaderaProduccionCRUD = new InventarioMaderaProduccionCRUD();
                try {
                    inventarioMaderaProducciones = (List<InventarioMaderaProduccion>) inventarioMaderaProduccionCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("inventarioMaderaProducciones",inventarioMaderaProducciones);
                    RequestDispatcher view = request.getRequestDispatcher("inventarioMaderaProduccion/inventarioMaderaProducciones.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarInventarioMaderaProducciones(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(InventarioMaderaProduccionController.class.getName()).log(Level.SEVERE, null, ex);
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
     private void listarInventarioMaderaProducciones(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<InventarioMaderaProduccion> inventarioMaderaProducciones;
        InventarioMaderaProduccionCRUD inventarioMaderaProduccionCrud = new InventarioMaderaProduccionCRUD();
        try {
            inventarioMaderaProducciones = (List<InventarioMaderaProduccion>)inventarioMaderaProduccionCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("inventarioMaderaProducciones",inventarioMaderaProducciones);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("inventarioMaderaProduccion/inventarioMaderaProducciones.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(InventarioMaderaProduccionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private InventarioMaderaProduccion extraerInventarioMaderaProduccionForm(HttpServletRequest request) {
        InventarioMaderaProduccion inventarioMaderaProduccion = new InventarioMaderaProduccion();
        inventarioMaderaProduccion.setId_madera(request.getParameter("id_madera"));
        inventarioMaderaProduccion.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        return inventarioMaderaProduccion;
    }
}
