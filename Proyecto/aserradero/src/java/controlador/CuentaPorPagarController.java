package controlador;

import dao.CuentaPorPagarCRUD;
import entidades.CuentaPorPagar;
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
 * @author lmarcoss
 */
public class CuentaPorPagarController extends HttpServlet {

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
        String tabla = request.getParameter("tabla");
        CuentaPorPagar cuentaPorPagarEC; //Enviar al CRUD
        CuentaPorPagar cuentaPorPagar; //Respuesta del CRUD
        CuentaPorPagarCRUD cuentaPorPagarCRUD;
        switch(action){
            case "listar_clientes":
                listarCuentaPorPagares(request, response,"cliente","Lista cuenta por pagar");
                break;
            case "listar_proveedores":
                listarCuentaPorPagares(request, response,"proveedor","Lista cuenta por pagar");
                break;
            case "modificar":
                cuentaPorPagarEC = new CuentaPorPagar();
                cuentaPorPagarEC.setId_persona(request.getParameter("id_cliente"));
                cuentaPorPagarCRUD = new CuentaPorPagarCRUD();
                try {
                    cuentaPorPagar = (CuentaPorPagar) cuentaPorPagarCRUD.modificar(cuentaPorPagarEC);
                    request.setAttribute("cuentaPorPagar",cuentaPorPagar);
                    request.setAttribute("tabla",tabla);
                    RequestDispatcher view = request.getRequestDispatcher("cuentaPorPagar/actualizarCuentaPorPagar.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCuentaPorPagares(request, response,tabla,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorPagarController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                cuentaPorPagarEC = new CuentaPorPagar();
                cuentaPorPagarEC.setId_persona(request.getParameter("id_cliente"));
                cuentaPorPagarCRUD = new CuentaPorPagarCRUD();
                try {
                    cuentaPorPagarCRUD.eliminar(cuentaPorPagarEC);
                    listarCuentaPorPagares(request, response,tabla,"eliminado");
                } catch (Exception ex) {
                    listarCuentaPorPagares(request, response,tabla,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorPagarController.class.getName()).log(Level.SEVERE, null, ex);
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
        String tabla = request.getParameter("tabla"); // Tabla en la que se va a ser la consulta
        CuentaPorPagar cuentaPorPagar;
        CuentaPorPagarCRUD cuentaPorPagarCRUD;
        switch(action){
            case "nuevo":
                cuentaPorPagar = extraerCuentaPorPagarForm(request);
                cuentaPorPagarCRUD = new CuentaPorPagarCRUD();
                try {
                    cuentaPorPagarCRUD.registrar(cuentaPorPagar);
                    listarCuentaPorPagares(request, response,tabla,"registrado");
                } catch (Exception ex) {
                    listarCuentaPorPagares(request, response,tabla,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorPagarController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <CuentaPorPagar> cuentaPorPagares;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                cuentaPorPagarCRUD = new CuentaPorPagarCRUD();
                try {
                    cuentaPorPagares = (List<CuentaPorPagar>) cuentaPorPagarCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("cuentaPorPagares",cuentaPorPagares);
                    request.setAttribute("tabla",tabla);
                    RequestDispatcher view = request.getRequestDispatcher("cuentaPorPagar/cuentaPorPagares.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCuentaPorPagares(request, response,tabla,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(CuentaPorPagarController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarCuentaPorPagares(HttpServletRequest request, HttpServletResponse response, String tabla, String mensaje) {
        List<CuentaPorPagar> cuentaPorPagares = null;
        CuentaPorPagarCRUD cuentaPorPagarCrud = new CuentaPorPagarCRUD();
        try {
            if(tabla.equals("cliente")){
                cuentaPorPagares = (List<CuentaPorPagar>)cuentaPorPagarCrud.listarClientes();
            }else if(tabla.equals("proveedor")){
                cuentaPorPagares = (List<CuentaPorPagar>)cuentaPorPagarCrud.listarProveedores();
            }
            //Enviamos las listas al jsp
            request.setAttribute("cuentaPorPagares",cuentaPorPagares);
            request.setAttribute("tabla",tabla);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("cuentaPorPagar/cuentaPorPagares.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(CuentaPorPagarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private CuentaPorPagar extraerCuentaPorPagarForm(HttpServletRequest request) {
        CuentaPorPagar cuentaPorPagar = new CuentaPorPagar();
        cuentaPorPagar.setId_persona(request.getParameter("id_cliente"));
        cuentaPorPagar.setMonto(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto")))));
        return cuentaPorPagar;
    }

}
