package controlador.maderaRollo;

import controlador.gasto.OtroGastoController;
import dao.maderaRollo.PagoCompraCRUD;
import entidades.maderaRollo.PagoCompra;
import entidadesVirtuales.VistaMontoPagoCompra;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author lmarcoss
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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1

        // Sesiones
        HttpSession sesion = request.getSession(false);
        String nombre_usuario = (String) sesion.getAttribute("nombre_usuario");
        String rol = (String) sesion.getAttribute("rol");
        if (nombre_usuario.equals("")) {
            response.sendRedirect("/aserradero/");
        } else if (rol.equals("Administrador") || rol.equals("Empleado") || rol.equals("Vendedor")) {
            //Acción a realizar
            String action = request.getParameter("action");
            switch (action) {
                /**
                 * *************** Respuestas a métodos POST
                 * *********************
                 */
                case "insertar":
                    registrarPagoCompra(request, response, sesion, action);
                    break;
                case "actualizar":
                    actualizarPagoCompra(request, response, sesion,action);
                    break;
                case "buscar":
                    buscarPagoCompra(request, response,sesion, action);
                    break;
                /**
                 * *************** Respuestas a métodos GET
                 * *********************
                 */
                case "nuevo":
                    prepararNuevoPagoCompra(request, response,sesion);
                    break;
                case "listar":
                    listarPagoCompra(request, response, sesion,action);
                    break;
                case "modificar":
                    modificarPagoCompra(request, response,sesion,action);
                    break;
                case "eliminar":
                    eliminarPagoCompra(request, response,sesion,action);
                    break;
            }
        } else {
            try {
                sesion.invalidate();
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("/aserradero/");
            }
            response.sendRedirect("/aserradero/");
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
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void registrarPagoCompra(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        PagoCompra pagoCompra = extraerPagoCompraForm(request, action);
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            pagoCompraCRUD.registrar(pagoCompra);
            response.sendRedirect("/aserradero/PagoCompraController?action=listar"); // para evitar acciones repetidas al actualizar página
            //listarPagoCompra(request, response, "registrado");
        } catch (Exception ex) {
            listarPagoCompra(request, response, sesion, "error_registrar");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PagoCompra extraerPagoCompraForm(HttpServletRequest request, String action) {
        PagoCompra pagoCompra = new PagoCompra();
        if (action.equals("actualizar")) {
            // Se ejecuta sólo en el caso de actualizar
            pagoCompra.setId_pago(Integer.valueOf(request.getParameter("id_pago")));
        }
        pagoCompra.setFecha(Date.valueOf(request.getParameter("fecha")));
        pagoCompra.setId_proveedor(request.getParameter("id_proveedor"));
        pagoCompra.setMonto_pago(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto_pago")))));
        pagoCompra.setMonto_por_pagar(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto_por_pagar")))));
        return pagoCompra;
    }

    private void actualizarPagoCompra(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        PagoCompra pagoCompra = extraerPagoCompraForm(request, action);
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            pagoCompraCRUD.actualizar(pagoCompra);
            response.sendRedirect("/aserradero/PagoCompraController?action=listar"); // para evitar acciones repetidas al actualizar página
        } catch (Exception ex) {
            listarPagoCompra(request, response, sesion, "error_actualizar");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarPagoCompra(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        List<PagoCompra> listaPagoCompra;
        String nombre_campo = request.getParameter("nombre_campo"); //Nombre del campo asociado a la búsqueda
        String dato = request.getParameter("dato");                 // Valor a buscar en el campo
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            listaPagoCompra = (List<PagoCompra>) pagoCompraCRUD.buscar(nombre_campo, dato, (String) sesion.getAttribute(dato));
            mostrarPagoCompras(request, response, listaPagoCompra, action);
        } catch (Exception ex) {
            listarPagoCompra(request, response, sesion, "error_buscar_campo");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarPagoCompras(HttpServletRequest request, HttpServletResponse response, List<PagoCompra> listaPagoCompra, String action) {
        request.setAttribute("mensaje", action);
        request.setAttribute("listaPagoCompra", listaPagoCompra);
        RequestDispatcher view = request.getRequestDispatcher("moduloMaderaRollo/pagoCompra/listarPagoCompra.jsp");
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            System.err.println("No se pudo mostrar la lista de pago compras");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepararNuevoPagoCompra(HttpServletRequest request, HttpServletResponse response, HttpSession sesion) {
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        List<VistaMontoPagoCompra> listaMontoPagoCompra;
        try {
            listaMontoPagoCompra = (List<VistaMontoPagoCompra>) pagoCompraCRUD.listarMontoPagoCompra((String) sesion.getAttribute("id_jefe"));
            request.setAttribute("listaMontoPagoCompra", listaMontoPagoCompra);
            RequestDispatcher view = request.getRequestDispatcher("moduloMaderaRollo/pagoCompra/nuevoPagoCompra.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarPagoCompra(request, response, sesion, "error_nuevo");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarPagoCompra(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        List<PagoCompra> listaPagoCompras;
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            listaPagoCompras = (List<PagoCompra>) pagoCompraCRUD.listar("");
            mostrarPagoCompras(request, response, listaPagoCompras, action);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarPagoCompra(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        PagoCompra pagoCompra = new PagoCompra();
        pagoCompra.setId_pago(Integer.valueOf(request.getParameter("id_pago")));
        try {
            pagoCompra = (PagoCompra) pagoCompraCRUD.modificar(pagoCompra);
            request.setAttribute("pagoCompra", pagoCompra);
            RequestDispatcher view = request.getRequestDispatcher("moduloMaderaRollo/pagoCompra/actualizarPagoCompra.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarPagoCompra(request, response, sesion, "error_modificar");
            Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarPagoCompra(HttpServletRequest request, HttpServletResponse response, HttpSession sesion, String action) {
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        PagoCompra pagoCompra = new PagoCompra();
        pagoCompra.setId_pago(Integer.valueOf(request.getParameter("id_pago")));
        try {
            pagoCompraCRUD.eliminar(pagoCompra);
            response.sendRedirect("/aserradero/PagoCompraController?action=listar"); // para evitar acciones repetidas al actualizar página
        } catch (Exception ex) {
            listarPagoCompra(request, response, sesion, action);
            Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
