
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
        String tabla;
        CuentaPorCobrar cuentaPorCobrarEC; //Enviar al CRUD
        CuentaPorCobrar cuentaPorCobrar; //Respuesta del CRUD
        CuentaPorCobrarCRUD cuentaPorCobrarCRUD;
        switch(action){
            case "listar_clientes":// listar cuentas por cobrar a proveedores
                listarCuentaPorCobrares(request, response,"cliente","Lista Cuenta por Cobrar");
                break;
            case "listar_proveedores":// listar cuentas por cobrar a clientes
                listarCuentaPorCobrares(request, response,"proveedor","Lista Cuenta por Cobrar");
                break;
            case "modificar":
                cuentaPorCobrarEC = new CuentaPorCobrar();
                cuentaPorCobrarEC.setId_persona(request.getParameter("id_persona"));
                tabla = request.getParameter("tabla");
                cuentaPorCobrarCRUD = new CuentaPorCobrarCRUD();
                try {
                    cuentaPorCobrar = (CuentaPorCobrar) cuentaPorCobrarCRUD.modificar(cuentaPorCobrarEC);
                    request.setAttribute("cuentaPorCobrar",cuentaPorCobrar);
                    request.setAttribute("tabla",tabla);
                    RequestDispatcher view = request.getRequestDispatcher("cuentaPorCobrar/actualizarCuentaPorCobrar.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCuentaPorCobrares(request, response,tabla,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                cuentaPorCobrarEC = new CuentaPorCobrar();
                cuentaPorCobrarEC.setId_persona(request.getParameter("id_persona"));
                tabla = request.getParameter("tabla");
                cuentaPorCobrarCRUD = new CuentaPorCobrarCRUD();
                try {
                    cuentaPorCobrarCRUD.eliminar(cuentaPorCobrarEC);
                    listarCuentaPorCobrares(request, response,tabla,"eliminado");
                } catch (Exception ex) {
                    listarCuentaPorCobrares(request, response,tabla,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
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
        // leer acción a realizar
        String action = request.getParameter("action");
        String tabla;
        CuentaPorCobrar cuentaPorCobrar;
        CuentaPorCobrarCRUD cuentaPorCobrarCRUD;
        switch(action){
            case "nuevo":
                cuentaPorCobrar = extraerCuentaPorCobrarForm(request);
                tabla = request.getParameter("tabla");
                cuentaPorCobrarCRUD = new CuentaPorCobrarCRUD();
                try {
                    cuentaPorCobrarCRUD.registrar(cuentaPorCobrar);
                    listarCuentaPorCobrares(request, response,tabla,"registrado");
                } catch (Exception ex) {
                    listarCuentaPorCobrares(request, response,tabla,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <CuentaPorCobrar> cuentaPorCobrares;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                tabla = request.getParameter("tabla");
                cuentaPorCobrarCRUD = new CuentaPorCobrarCRUD();
                try {
                    cuentaPorCobrares = (List<CuentaPorCobrar>) cuentaPorCobrarCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("cuentaPorCobrares",cuentaPorCobrares);
                    request.setAttribute("tabla",tabla);
                    RequestDispatcher view = request.getRequestDispatcher("cuentaPorCobrar/cuentaPorCobrares.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCuentaPorCobrares(request, response,tabla,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarCuentaPorCobrares(HttpServletRequest request, HttpServletResponse response,String tabla, String mensaje) {
        List<CuentaPorCobrar> cuentaPorCobrares = null;
        CuentaPorCobrarCRUD cuentaPorCobrarCrud = new CuentaPorCobrarCRUD();
        try {
            if(tabla.equals("cliente")){
                cuentaPorCobrares = (List<CuentaPorCobrar>)cuentaPorCobrarCrud.listarCuentasPorCobrarCliente();
                request.setAttribute("tabla",tabla);
            }else if (tabla.equals("proveedor")){
                cuentaPorCobrares = (List<CuentaPorCobrar>)cuentaPorCobrarCrud.listarCuentasPorCobrarProveedor();
                request.setAttribute("tabla",tabla);
            }
            //Enviamos las listas al jsp
            request.setAttribute("cuentaPorCobrares",cuentaPorCobrares);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tabla",tabla);
            RequestDispatcher view = request.getRequestDispatcher("cuentaPorCobrar/cuentaPorCobrares.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(CuentaPorCobrarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private CuentaPorCobrar extraerCuentaPorCobrarForm(HttpServletRequest request) {
        CuentaPorCobrar cuentaPorCobrar = new CuentaPorCobrar();
        cuentaPorCobrar.setId_persona(request.getParameter("id_proveedor"));
        cuentaPorCobrar.setMonto(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto")))));
        return cuentaPorCobrar;
    }

}
