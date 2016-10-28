package controlador;

import dao.CostoMaderaCRUD;
import dao.MaderaClasificacionCRUD;
import entidades.CostoMadera;
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
public class CostoMaderaController extends HttpServlet {

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
        CostoMadera costoMaderaEC; //Enviar al CRUD
        CostoMadera costoMadera; //Respuesta del CRUD
        CostoMaderaCRUD costoMaderaCRUD;
        switch(action){
            case "nuevo":
                try {
                    MaderaClasificacionCRUD maderaClasificacionCRUD= new MaderaClasificacionCRUD();
                    List<MaderaClasificacion> maderaClasificaciones;
                    maderaClasificaciones = (List<MaderaClasificacion>)maderaClasificacionCRUD.listar();
                    request.setAttribute("maderaClasificaciones",maderaClasificaciones);
                    RequestDispatcher view = request.getRequestDispatcher("costoMadera/nuevoCostoMadera.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCostoMaderas(request, response, "error_nuevo");
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarCostoMaderas(request, response,"");
                break;
            case "modificar":
                costoMaderaEC = new CostoMadera();
                costoMaderaEC.setId_madera(request.getParameter("id_madera"));
                costoMaderaCRUD = new CostoMaderaCRUD();
                try {
                    costoMadera = (CostoMadera) costoMaderaCRUD.modificar(costoMaderaEC);
                    request.setAttribute("costoMadera",costoMadera);
                    RequestDispatcher view = request.getRequestDispatcher("costoMadera/actualizarCostoMadera.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCostoMaderas(request, response, "error_modificar");
                    Logger.getLogger(CostoMaderaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                costoMaderaEC = new CostoMadera();
                costoMaderaEC.setId_madera(request.getParameter("id_madera"));
                costoMaderaCRUD = new CostoMaderaCRUD();
                try {
                    costoMaderaCRUD.eliminar(costoMaderaEC);
                    listarCostoMaderas(request, response,"eliminado");
                } catch (Exception ex) {
                    listarCostoMaderas(request, response,"error_eliminar");
                    Logger.getLogger(CostoMaderaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_costoMadera":
                String id_costoMadera = request.getParameter("id_madera");
                buscarCostoMaderaPorId(request, response, id_costoMadera);
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
        //leemos la acción a realizar
        String action = request.getParameter("action");
        CostoMadera costoMadera;
        CostoMaderaCRUD costoMaderaCRUD;
        switch(action){
            case "nuevo":
                costoMadera = extraerCostoMaderaForm(request);
                costoMaderaCRUD = new CostoMaderaCRUD();
                try {
                    costoMaderaCRUD.registrar(costoMadera);
                    listarCostoMaderas(request, response,"registrado");
                } catch (Exception ex) {
                    listarCostoMaderas(request, response,"error_registrar");
                    Logger.getLogger(CostoMaderaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                costoMadera = extraerCostoMaderaForm(request);
                costoMaderaCRUD = new CostoMaderaCRUD();
                try {
                    costoMaderaCRUD.actualizar(costoMadera);
                    listarCostoMaderas(request, response,"actualizado");
                } catch (Exception ex) {
                    listarCostoMaderas(request, response,"error_actualizar");
                    Logger.getLogger(CostoMaderaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <CostoMadera> costoMaderas;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                costoMaderaCRUD = new CostoMaderaCRUD();
                try {
                    costoMaderas = (List<CostoMadera>)costoMaderaCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("costoMaderas",costoMaderas);
                    RequestDispatcher view = request.getRequestDispatcher("costoMadera/costoMaderas.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarCostoMaderas(request, response, "error_buscar_campo");
                    Logger.getLogger(CostoMaderaController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarCostoMaderas(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<CostoMadera> costoMaderas;
        CostoMaderaCRUD costoMaderaCrud = new CostoMaderaCRUD();
        try {
            costoMaderas = (List<CostoMadera>)costoMaderaCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("costoMaderas",costoMaderas);
            request.setAttribute("mensaje",mensaje);
         
            RequestDispatcher view = request.getRequestDispatcher("costoMadera/costoMaderas.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(CostoMaderaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarCostoMaderaPorId(HttpServletRequest request, HttpServletResponse response,String id_costoMadera) {
        List<CostoMadera> costoMaderas;
        CostoMaderaCRUD costoMaderaCrud = new CostoMaderaCRUD();
        try {
            costoMaderas = (List<CostoMadera>)costoMaderaCrud.buscarPorId(id_costoMadera);
            //Enviamos las listas al jsp
            request.setAttribute("costoMaderas",costoMaderas);
            RequestDispatcher view = request.getRequestDispatcher("costoMadera/costoMaderas.jsp");
            view.forward(request,response);
        } catch (ServletException | IOException ex) {
            listarCostoMaderas(request, response, "error_buscar_id");
            System.out.println(ex);
            Logger.getLogger(CostoMaderaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Extraer datos del formulario
    private CostoMadera extraerCostoMaderaForm(HttpServletRequest request) {
        CostoMadera costoMadera = new CostoMadera();
        costoMadera.setId_madera(request.getParameter("id_madera"));
        costoMadera.setMonto_volumen(Float.valueOf(request.getParameter("monto_volumen")));
        return costoMadera;
    }

}
