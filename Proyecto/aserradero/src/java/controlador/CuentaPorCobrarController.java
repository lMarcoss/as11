package controlador;

import dao.CuentaPorCobrarCRUD;
import entidades.CuentaPorCobrar;
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
 * @author Marcos
 */
public class CuentaPorCobrarController extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1

        //Acción a realizar
        String action = request.getParameter("action");
        switch (action) {
            /**
             * *************** Respuestas a métodos POST *********************
             */
            case "buscar_cliente":
                buscarCCCliente(request, response, action); //Buscar cuenta por cobrar de clientes
                break;
            case "buscar_proveedor":
                buscarCCProveedor(request, response, action); //Buscar cuenta por cobrar de proveedores
                break;
            /**
             * *************** Respuestas a métodos GET *********************
             */
            case "listar_proveedores":
                listarCCProveedor(request, response, "error_buscar_proveedor"); // listar cuenta por cobrar de proveedores
                break;
            case "listar_clientes":
                listarCCCliente(request, response, "error_buscar_cliente"); // listar cuenta por cobrar de clientes
                break;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
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

    
    private void buscarCCCliente(HttpServletRequest request, HttpServletResponse response, String action) throws Exception {
        List <CuentaPorCobrar> cuentaPorCobrares;
        String nombre_campo = request.getParameter("nombre_campo");
        String dato = request.getParameter("dato");
        
        CuentaPorCobrarCRUD cuentaPorCobrarCRUD = new CuentaPorCobrarCRUD();
        
        try {
            cuentaPorCobrares = (List<CuentaPorCobrar>) cuentaPorCobrarCRUD.buscarCCCliente(nombre_campo, dato);
            mostrarCuentaPorCobrar(request,response,cuentaPorCobrares,"clientes");
        } catch (IOException | ServletException ex) {
            listarCCCliente(request, response,"error_buscar_cliente");
            System.out.println(ex);
            Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarCCProveedor(HttpServletRequest request, HttpServletResponse response, String action) throws Exception {
        List <CuentaPorCobrar> cuentaPorCobrares;
        String nombre_campo = request.getParameter("nombre_campo");
        String dato = request.getParameter("dato");
        
        CuentaPorCobrarCRUD cuentaPorCobrarCRUD = new CuentaPorCobrarCRUD();
        
        try {
            cuentaPorCobrares = (List<CuentaPorCobrar>) cuentaPorCobrarCRUD.buscarCCProveedor(nombre_campo, dato);
            mostrarCuentaPorCobrar(request,response,cuentaPorCobrares,"proveedores");
        } catch (IOException | ServletException ex) {
            listarCCProveedor(request, response,"error_buscar_proveedor");
            System.out.println(ex);
            Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarCCProveedor(HttpServletRequest request, HttpServletResponse response, String action) throws Exception {
        try {
            CuentaPorCobrarCRUD cuentaPorCobrarCrud = new CuentaPorCobrarCRUD();
            List<CuentaPorCobrar> cuentaPorCobrares = (List<CuentaPorCobrar>) cuentaPorCobrarCrud.listarCuentasPorCobrarProveedor();
            mostrarCuentaPorCobrar(request, response, cuentaPorCobrares, action);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarCCCliente(HttpServletRequest request, HttpServletResponse response, String action) throws Exception {
        CuentaPorCobrarCRUD cuentaPorCobrarCrud = new CuentaPorCobrarCRUD();
        try {
            List<CuentaPorCobrar> cuentaPorCobrares = (List<CuentaPorCobrar>) cuentaPorCobrarCrud.listarCuentasPorCobrarCliente();
            mostrarCuentaPorCobrar(request, response, cuentaPorCobrares, action);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarCuentaPorCobrar(HttpServletRequest request, HttpServletResponse response, List<CuentaPorCobrar> cuentaPorCobrares, String action) throws IOException, ServletException {
        request.setAttribute("cuentaPorCobrares", cuentaPorCobrares);
        request.setAttribute("mensaje", action);
        RequestDispatcher view = request.getRequestDispatcher("cuentaPorCobrar/cuentaPorCobrares.jsp");
        view.forward(request, response);
    }
}
