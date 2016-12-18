package controlador;

import dao.MaderaAserradaClasifCRUD;
import entidades.MaderaAserradaClasif;
import java.io.IOException;
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
public class MaderaAserradaClasifController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1

        //Acción a realizar
        String action = request.getParameter("action");
        switch (action) {
            /**
             * *************** Respuestas a métodos POST *********************
             */
            case "insertar":
                registrarMaderaAserradaClasif(request, response, action);
                break;
            case "actualizar":
                actualizarMaderaAserradaClasif(request, response, action);
                break;
            case "buscar":
                buscarMaderaAserradaClasif(request, response, action);
                break;
            /**
             * *************** Respuestas a métodos GET *********************
             */
            case "nuevo":
                prepararNuevoMaderaAserradaClasif(request, response);
                break;
            case "listar":
                listarMaderaAserradaClasif(request, response, action);
                break;
            case "modificar":
                modificarMaderaAserradaClasif(request, response);
                break;
            case "eliminar":
                eliminarMaderaAserradaClasif(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private void registrarMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response, String action) {
        MaderaAserradaClasif mAClasif = extraerMaderaAserradaClasifForm(request, action);
        MaderaAserradaClasifCRUD mAClasifCRUD = new MaderaAserradaClasifCRUD();
        try {
            mAClasifCRUD.registrar(mAClasif);
            response.sendRedirect("/aserradero/MaderaAserradaClasifController?action=listar"); // para evitar acciones repetidas al actualizar página
            //listarMaderaAserradaClasif(request, response, "registrado");
        } catch (Exception ex) {
            listarMaderaAserradaClasif(request, response, "error_registrar");
            Logger.getLogger(MaderaAserradaClasifController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MaderaAserradaClasif extraerMaderaAserradaClasifForm(HttpServletRequest request, String action) {
        MaderaAserradaClasif mAClasif = new MaderaAserradaClasif();
        mAClasif.setId_madera(request.getParameter("id_madera"));
        mAClasif.setGrueso(BigDecimal.valueOf((Double.valueOf(request.getParameter("grueso")))));
        mAClasif.setAncho(BigDecimal.valueOf((Double.valueOf(request.getParameter("ancho")))));
        mAClasif.setLargo(BigDecimal.valueOf((Double.valueOf(request.getParameter("largo")))));
        mAClasif.setVolumen(BigDecimal.valueOf((Double.valueOf(request.getParameter("volumen")))));
        mAClasif.setCosto_por_volumen(BigDecimal.valueOf((Double.valueOf(request.getParameter("costo_por_volumen")))));
        return mAClasif;
    }

    private void actualizarMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response, String action) {
        MaderaAserradaClasif mAClasif = extraerMaderaAserradaClasifForm(request, action);
        MaderaAserradaClasifCRUD mAClasifCRUD = new MaderaAserradaClasifCRUD();
        try {
            mAClasifCRUD.actualizar(mAClasif);
            response.sendRedirect("/aserradero/MaderaAserradaClasifController?action=listar"); // para evitar acciones repetidas al actualizar página
        } catch (Exception ex) {
            listarMaderaAserradaClasif(request, response, "error_actualizar");
            Logger.getLogger(MaderaAserradaClasifController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response, String action) {
        List<MaderaAserradaClasif> listaMaderaAserradaClasif;
        String nombre_campo = request.getParameter("nombre_campo"); //Nombre del campo asociado a la búsqueda
        String dato = request.getParameter("dato");                 // Valor a buscar en el campo
        MaderaAserradaClasifCRUD mAClasifCRUD = new MaderaAserradaClasifCRUD();
        try {
            listaMaderaAserradaClasif = (List<MaderaAserradaClasif>) mAClasifCRUD.buscar(nombre_campo, dato);
            listarMaderaAserradaClasif(request, response, listaMaderaAserradaClasif, action);
        } catch (Exception ex) {
            listarMaderaAserradaClasif(request, response, "error_buscar_campo");
            Logger.getLogger(MaderaAserradaClasifController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response, List<MaderaAserradaClasif> listaMaderaAserradaClasif, String action) {
        request.setAttribute("mensaje", action);
        request.setAttribute("listaMaderaAserradaClasif", listaMaderaAserradaClasif);
        RequestDispatcher view = request.getRequestDispatcher("maderaAserradaClasif/listarMaderaAserradaClasif.jsp");
        try {
            view.forward(request, response);
        } catch (ServletException | IOException ex) {
            System.err.println("No se pudo mostrar la lista de clasificacion de madera aserrada");
            Logger.getLogger(MaderaAserradaClasifController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepararNuevoMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response) {
        try {    
            response.sendRedirect("maderaAserradaClasif/nuevoMaderaAserradaClasif.jsp");
        } catch (IOException ex) {
            listarMaderaAserradaClasif(request, response, "error_buscar_campo");
            Logger.getLogger(MaderaAserradaClasifController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response, String action) {
        List<MaderaAserradaClasif> listaMaderaAserradaClasif;
        MaderaAserradaClasifCRUD mAClasifCRUD = new MaderaAserradaClasifCRUD();
        try {
            listaMaderaAserradaClasif = (List<MaderaAserradaClasif>) mAClasifCRUD.listar("");
            listarMaderaAserradaClasif(request, response, listaMaderaAserradaClasif, action);
        } catch (Exception ex) {            
            System.out.println(ex);
            Logger.getLogger(MaderaAserradaClasifController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificarMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response) {
        MaderaAserradaClasifCRUD mAClasifCRUD = new MaderaAserradaClasifCRUD();
        MaderaAserradaClasif mAClasif = new MaderaAserradaClasif();
        //Obtenemos el id_madera a modificar
        mAClasif.setId_madera(request.getParameter("id_madera"));
        try {
            mAClasif = (MaderaAserradaClasif) mAClasifCRUD.modificar(mAClasif);
            request.setAttribute("mAClasif", mAClasif);
            RequestDispatcher view = request.getRequestDispatcher("maderaAserradaClasif/actualizarMaderaAserradaClasif.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            listarMaderaAserradaClasif(request, response, "error_modificar");
            Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarMaderaAserradaClasif(HttpServletRequest request, HttpServletResponse response) {
        MaderaAserradaClasifCRUD mAClasifCRUD = new MaderaAserradaClasifCRUD();
        MaderaAserradaClasif mAClasif = new MaderaAserradaClasif();
        mAClasif.setId_madera(request.getParameter("id_madera"));
        try {
            mAClasifCRUD.eliminar(mAClasif);
            response.sendRedirect("/aserradero/MaderaAserradaClasifController?action=listar"); // para evitar acciones repetidas al actualizar página
        } catch (Exception ex) {
            listarMaderaAserradaClasif(request, response, "error_eliminar");
            Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
