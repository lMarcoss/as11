package controlador;

import dao.MaderaClasificacionCRUD;
import entidades.MaderaClasificacion;
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
 * @author lmarcoss
 */
public class MaderaClasificacionController extends HttpServlet {

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
        MaderaClasificacion maderaClasificacionEC; //Enviar al CRUD
        MaderaClasificacion maderaClasificacion; //Respuesta del CRUD
        MaderaClasificacionCRUD maderaClasificacionCRUD;
        switch(action){
            case "nuevo":
                response.sendRedirect("maderaClasificacion/nuevoMaderaClasificacion.jsp");
                break;
            case "listar":
                listarMaderaClasificacions(request, response,"");
                break;
            case "modificar":
                maderaClasificacionEC = new MaderaClasificacion();
                maderaClasificacionEC.setId_madera(request.getParameter("id_madera"));
                maderaClasificacionCRUD = new MaderaClasificacionCRUD();
                try {
                    maderaClasificacion = (MaderaClasificacion) maderaClasificacionCRUD.modificar(maderaClasificacionEC);
                    request.setAttribute("maderaClasificacion",maderaClasificacion);
                    RequestDispatcher view = request.getRequestDispatcher("maderaClasificacion/actualizarMaderaClasificacion.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarMaderaClasificacions(request, response, "error_modificar");
                    Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                maderaClasificacionEC = new MaderaClasificacion();
                maderaClasificacionEC.setId_madera(request.getParameter("id_madera"));
                maderaClasificacionCRUD = new MaderaClasificacionCRUD();
                try {
                    maderaClasificacionCRUD.eliminar(maderaClasificacionEC);
                    listarMaderaClasificacions(request, response,"eliminado");
                } catch (Exception ex) {
                    listarMaderaClasificacions(request, response,"eliminar");
                    Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_maderaClasificacion":
                String id_madera = request.getParameter("id_madera");
                try {
                    buscarMaderaClasificacionPorId(request, response, id_madera);
                } catch (Exception ex) {
                    listarMaderaClasificacions(request, response, "error_buscar_id");
                    Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
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
        MaderaClasificacion maderaClasificacion;
        MaderaClasificacionCRUD maderaClasificacionCRUD;
        switch(action){
            case "nuevo":
                maderaClasificacion = extraerMaderaClasificacionForm(request);
                maderaClasificacionCRUD = new MaderaClasificacionCRUD();
                try {
                    maderaClasificacionCRUD.registrar(maderaClasificacion);
                    listarMaderaClasificacions(request, response,"registrado");
                } catch (Exception ex) {
                    listarMaderaClasificacions(request, response,"error_registrar");
                    Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                maderaClasificacion = extraerMaderaClasificacionForm(request);
                maderaClasificacionCRUD = new MaderaClasificacionCRUD();
                try {
                    maderaClasificacionCRUD.actualizar(maderaClasificacion);
                    listarMaderaClasificacions(request, response,"actualizado");
                } catch (Exception ex) {
                    listarMaderaClasificacions(request, response,"error_actualizar");
                    Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <MaderaClasificacion> maderaClasificaciones;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                maderaClasificacionCRUD = new MaderaClasificacionCRUD();
                try {
                    maderaClasificaciones = (List<MaderaClasificacion>)maderaClasificacionCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("maderaClasificaciones",maderaClasificaciones);
                    RequestDispatcher view = request.getRequestDispatcher("maderaClasificacion/maderaClasificaciones.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarMaderaClasificacions(request, response, "error_buscar_campo");
                    Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarMaderaClasificacions(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<MaderaClasificacion> maderaClasificaciones;
        MaderaClasificacionCRUD maderaClasificacionCrud = new MaderaClasificacionCRUD();
        try {
            maderaClasificaciones = (List<MaderaClasificacion>)maderaClasificacionCrud.listar();
            //Enviamos la lista al jsp
            request.setAttribute("maderaClasificaciones",maderaClasificaciones);
            
            //Enviamos mensaje al jsp
            request.setAttribute("mensaje",mensaje);
            
            RequestDispatcher view = request.getRequestDispatcher("maderaClasificacion/maderaClasificaciones.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarMaderaClasificacionPorId(HttpServletRequest request, HttpServletResponse response,String id_madera) throws Exception {
        List<MaderaClasificacion> maderaClasificaciones;
        MaderaClasificacionCRUD maderaClasificacionCrud = new MaderaClasificacionCRUD();
        try {
            maderaClasificaciones = (List<MaderaClasificacion>)maderaClasificacionCrud.buscarMaderaClasificacionPorId(id_madera);
            //Enviamos las listas al jsp
            request.setAttribute("maderaClasificaciones",maderaClasificaciones);
            RequestDispatcher view = request.getRequestDispatcher("maderaClasificacion/maderaClasificaciones.jsp");
            view.forward(request,response);
        } catch (ServletException | IOException ex) {
            listarMaderaClasificacions(request, response, "error_buscar_id");
            System.out.println(ex);
            Logger.getLogger(MaderaClasificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Extraer datos del formulario
    private MaderaClasificacion extraerMaderaClasificacionForm(HttpServletRequest request) {
        MaderaClasificacion maderaClasificacion = new MaderaClasificacion();
        maderaClasificacion.setId_madera(request.getParameter("id_madera"));
        maderaClasificacion.setGrueso(Float.valueOf(request.getParameter("grueso")));
        maderaClasificacion.setAncho(Float.valueOf(request.getParameter("ancho")));
        maderaClasificacion.setLargo(Float.valueOf(request.getParameter("largo")));
        maderaClasificacion.setVolumen(Float.valueOf(request.getParameter("volumen")));
        return maderaClasificacion;
    }

}
