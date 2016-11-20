package controlador;

import dao.PagoCompraCRUD;
import entidades.PagoCompra;
import entidadesVirtuales.MontoPagoCompra;
import java.io.IOException;
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

        //Acción a realizar
        String action = request.getParameter("action");
        switch (action) {
            /**
             * *************** Respuestas a métodos POST *********************
             */
            case "insertar":
                registrarPagoCompra(request, response, action);
                break;
            case "actualizar":
                actualizarPagoCompra(request, response, action);
                break;
            case "buscar":
                buscarPagoCompra(request, response, action);
                break;
            /**
             * *************** Respuestas a métodos GET *********************
             */
            case "nuevo":
                prepararNuevoPagoCompra(request, response);
                break;
            case "listar":
                listarPagoCompra(request, response, action);
                break;
            case "modificar":
                modificarPagoCompra(request, response);
                break;
            case "eliminar":
                eliminarPagoCompra(request, response);
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

    private void registrarPagoCompra(HttpServletRequest request, HttpServletResponse response, String action) {
        PagoCompra pagoCompra = extraerPagoCompraCompraForm(request, action);
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            pagoCompraCRUD.registrar(pagoCompra);
            listarPagoCompra(request, response, "registrado");
        } catch (Exception ex) {
            listarPagoCompra(request, response, "error_registrar");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PagoCompra extraerPagoCompraCompraForm(HttpServletRequest request, String action) {
        PagoCompra pagoCompra = new PagoCompra();
        if (action.equals("actualizar")) {
            // Se ejecuta sólo en el caso de actualizar
            pagoCompra.setId_pago(Integer.valueOf(request.getParameter("id_pago")));
        }
        pagoCompra.setFecha(Date.valueOf(request.getParameter("fecha")));
        pagoCompra.setId_proveedor(request.getParameter("id_proveedor"));
        pagoCompra.setMonto_pago(Float.valueOf(request.getParameter("monto_pago")));
        pagoCompra.setMonto_por_pagar(Float.valueOf(request.getParameter("monto_por_pagar")));
        return pagoCompra;
    }

    private void actualizarPagoCompra(HttpServletRequest request, HttpServletResponse response, String action) {
        PagoCompra pagoCompra = extraerPagoCompraCompraForm(request, action);
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            pagoCompraCRUD.actualizar(pagoCompra);
            listarPagoCompra(request, response, "actualizado");
        } catch (Exception ex) {
            listarPagoCompra(request, response, "error_actualizar");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarPagoCompra(HttpServletRequest request, HttpServletResponse response, String action) {
        List<PagoCompra> listaPagoCompra;
        String nombre_campo = request.getParameter("nombre_campo"); //Nombre del campo asociado a la búsqueda
        String dato = request.getParameter("dato");                 // Valor a buscar en el campo
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            listaPagoCompra = (List<PagoCompra>) pagoCompraCRUD.buscar(nombre_campo, dato);
            mostrarPagoCompras(request, response, listaPagoCompra, action);
        } catch (Exception ex) {
            listarPagoCompra(request, response, "error_buscar_campo");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarPagoCompras(HttpServletRequest request, HttpServletResponse response, List<PagoCompra> listaPagoCompra, String action) {
        request.setAttribute("mensaje", action);
        request.setAttribute("listaPagoCompra", listaPagoCompra);
        RequestDispatcher view = request.getRequestDispatcher("pagoCompra/listarPagoCompra.jsp");
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            System.err.println("No se pudo mostrar la lista de pago compras");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepararNuevoPagoCompra(HttpServletRequest request, HttpServletResponse response) {
        String administrador = "PAXA20160913HOCSXN";
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        List<MontoPagoCompra> listaMontoPagoCompra;
        try {
            listaMontoPagoCompra = (List<MontoPagoCompra>) pagoCompraCRUD.listarMontoPagoCompra(administrador);
            request.setAttribute("listaMontoPagoCompra", listaMontoPagoCompra);
            RequestDispatcher view = request.getRequestDispatcher("pagoCompra/nuevoPagoCompra.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarPagoCompra(request, response, "error_nuevo");
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarPagoCompra(HttpServletRequest request, HttpServletResponse response, String action) {
        List<PagoCompra> listaPagoCompras;
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        try {
            listaPagoCompras = (List<PagoCompra>) pagoCompraCRUD.listar();
            mostrarPagoCompras(request, response, listaPagoCompras, action);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PagoCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarPagoCompra(HttpServletRequest request, HttpServletResponse response) {
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        PagoCompra pagoCompra = new PagoCompra();
        pagoCompra.setId_pago(Integer.valueOf(request.getParameter("id_pago")));
        try {
            pagoCompra = (PagoCompra) pagoCompraCRUD.modificar(pagoCompra);
            request.setAttribute("pagoCompra", pagoCompra);
            RequestDispatcher view = request.getRequestDispatcher("pagoCompra/actualizarPagoCompra.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarPagoCompra(request, response, "error_modificar");
            Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarPagoCompra(HttpServletRequest request, HttpServletResponse response) {
        PagoCompraCRUD pagoCompraCRUD = new PagoCompraCRUD();
        PagoCompra pagoCompra = new PagoCompra();
        pagoCompra.setId_pago(Integer.valueOf(request.getParameter("id_pago")));
        try {
            pagoCompraCRUD.eliminar(pagoCompra);
            listarPagoCompra(request, response, "eliminado");
        } catch (Exception ex) {
            listarPagoCompra(request, response, "error_eliminar");
            Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
