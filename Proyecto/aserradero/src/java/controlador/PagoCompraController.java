package controlador;

import dao.EntradaMaderaRolloCRUD;
import dao.PagoCompraCRUD;
import entidades.EntradaMaderaRollo;
import entidades.PagoCompra;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
public class PagoCompraController extends HttpServlet {

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
        PagoCompra pagoCompraEC; //Enviar al CRUD
        PagoCompra pagoCompra; //Respuesta del CRUD
        PagoCompraCRUD pagoCompraCRUD;
        switch(action){
            case "nuevo":
                EntradaMaderaRolloCRUD compraCRUD= new EntradaMaderaRolloCRUD();
                List<EntradaMaderaRollo> compras;
                try {
                    compras = (List<EntradaMaderaRollo>)compraCRUD.listar();
                    request.setAttribute("compras",compras);
                    RequestDispatcher view = request.getRequestDispatcher("pagoCompra/nuevoPagoCompra.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagoCompras(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarPagoCompras(request, response,"Lista pago compras");
                break;
            case "modificar":
                pagoCompraEC = new PagoCompra();
                pagoCompraEC.setFecha(Date.valueOf(request.getParameter("fecha")));
                pagoCompraEC.setId_compra(request.getParameter("id_compra"));
                pagoCompraCRUD = new PagoCompraCRUD();
                try {
                    pagoCompra = (PagoCompra) pagoCompraCRUD.modificar(pagoCompraEC);
                    request.setAttribute("pagoCompra",pagoCompra);
                    
                    RequestDispatcher view = request.getRequestDispatcher("pagoCompra/actualizarPagoCompra.jsp");
                    
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagoCompras(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                pagoCompraEC= new PagoCompra ();
                pagoCompraEC.setId_compra(request.getParameter("id_compra"));
                pagoCompraEC.setFecha(Date.valueOf(request.getParameter("fecha")));
                pagoCompraCRUD = new PagoCompraCRUD();
                try {
                    pagoCompraCRUD.eliminar(pagoCompraEC);
                    listarPagoCompras(request, response,"eliminado");
                } catch (Exception ex) {
                    listarPagoCompras(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
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
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        String action = request.getParameter("action");
        PagoCompra pagoCompra;
        PagoCompraCRUD pagoCompraCRUD;
        switch(action){
            case "nuevo":
                pagoCompra = extraerPagoCompraForm(request);
                pagoCompraCRUD = new PagoCompraCRUD();
                try {
                    pagoCompraCRUD.registrar(pagoCompra);
                    listarPagoCompras(request, response,"registrado");
                } catch (Exception ex) {
                    listarPagoCompras(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                pagoCompra = extraerPagoCompraForm(request);
                pagoCompraCRUD = new PagoCompraCRUD();
                
//                if (pagoCompra.getPago().equals("Anticipado")){
                    try {
                        pagoCompraCRUD.actualizar(pagoCompra);
                    } catch (Exception ex) {
                        listarPagoCompras(request, response, "error_pago");
                        Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                }
//                if (pagoCompra.getPago().equals("Normal")){
//                    try {
//                        pagoCompraCRUD.actualizar(pagoCompra);
//                        listarPagoCompras(request, response,"pagado");
//                    } catch (Exception ex) {
//                        listarPagoCompras(request, response,"error_pago");
//                        System.out.println(ex);
//                        Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }else{
//                    try {
//                        if (pagoCompraCRUD.consultaProveedor(pagoCompra) && pagoCompra.getPago().equals("Anticipado")){
//                            if(pagoCompraCRUD.pagarCompra(pagoCompra)){
//                                try {
//                                    pagoCompraCRUD.actualizar(pagoCompra);
//                                    listarPagoCompras(request, response,"pagado");
//                                } catch (Exception ex) {
//                                    listarPagoCompras(request, response,"error_pago");
//                                    System.out.println(ex);
//                                    Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            }
//                            else{
//                                listarPagoCompras(request, response,"monto_inalcanzable_proveedor");
//                            }
//                        }else{
//                            listarPagoCompras(request, response, "cuenta_proveedor_no_encontrado");
//                        }
//                    } catch (Exception ex) {
//                        listarPagoCompras(request, response, "error_pago");
//                        Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
                break;
            case "buscar":
                List <PagoCompra> pagoCompras;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                pagoCompraCRUD = new PagoCompraCRUD();
                try {
                    pagoCompras = (List<PagoCompra>) pagoCompraCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("pagoCompras",pagoCompras);
                    RequestDispatcher view = request.getRequestDispatcher("pagoCompra/pagoCompras.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarPagoCompras(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarPagoCompras(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<PagoCompra> pagoCompras;
        PagoCompraCRUD pagoCompraCrud = new PagoCompraCRUD();
        try {
            pagoCompras = (List<PagoCompra>) pagoCompraCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("pagoCompras",pagoCompras);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("pagoCompra/pagoCompras.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private PagoCompra extraerPagoCompraForm(HttpServletRequest request) {
        PagoCompra pagoCompra = new PagoCompra();
        pagoCompra.setFecha(Date.valueOf(request.getParameter("fecha")));
        pagoCompra.setId_compra(request.getParameter("id_compra"));
        pagoCompra.setMonto(Float.valueOf(request.getParameter("monto")));
        pagoCompra.setPago(request.getParameter("pago"));
        return pagoCompra;
    }
}
