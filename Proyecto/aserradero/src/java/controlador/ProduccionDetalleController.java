package controlador;

import dao.MaderaClasificacionCRUD;
import dao.ProduccionDetalleCRUD;
import dao.ProduccionMaderaCRUD;
import entidades.MaderaClasificacion;
import entidades.ProduccionDetalle;
import entidades.ProduccionMadera;
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
public class ProduccionDetalleController extends HttpServlet {

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
        ProduccionDetalle produccionDetalleEC; //Enviar al CRUD
        ProduccionDetalle produccionDetalle; //Respuesta del CRUD
        ProduccionDetalleCRUD produccionDetalleCRUD;
        switch(action){
            case "nuevo":
                try {
                    ProduccionMaderaCRUD produccionMaderaCRUD= new ProduccionMaderaCRUD();
                    List<ProduccionMadera> produccionMaderas;
                    produccionMaderas = (List<ProduccionMadera>)produccionMaderaCRUD.listar();
                    request.setAttribute("produccionMaderas",produccionMaderas);
                    
                    MaderaClasificacionCRUD maderaClasificacionCRUD= new MaderaClasificacionCRUD();
                    List<MaderaClasificacion> maderaClasificaciones;
                    maderaClasificaciones = (List<MaderaClasificacion>)maderaClasificacionCRUD.listar();
                    request.setAttribute("maderaClasificaciones",maderaClasificaciones);
                    
                    RequestDispatcher view = request.getRequestDispatcher("produccionDetalle/nuevoProduccionDetalle.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProduccionDetalles(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(ProduccionDetalleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarProduccionDetalles(request, response,"Lista anticipo produccionMaderas");
                break;
                
            case "modificar":
                produccionDetalleEC = new ProduccionDetalle();
                produccionDetalleEC.setId_produccion(request.getParameter("id_produccion"));
                produccionDetalleEC.setId_madera(request.getParameter("id_madera"));
                produccionDetalleCRUD = new ProduccionDetalleCRUD();
                try {
                    produccionDetalle = (ProduccionDetalle) produccionDetalleCRUD.modificar(produccionDetalleEC);
                    request.setAttribute("produccionDetalle",produccionDetalle);
                    RequestDispatcher view = request.getRequestDispatcher("produccionDetalle/actualizarProduccionDetalle.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProduccionDetalles(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(ProduccionDetalleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                produccionDetalleEC= new ProduccionDetalle ();
                produccionDetalleEC.setId_produccion(request.getParameter("id_produccion"));
                produccionDetalleEC.setId_madera(request.getParameter("id_madera"));
                produccionDetalleCRUD = new ProduccionDetalleCRUD();
                try {
                    produccionDetalleCRUD.eliminar(produccionDetalleEC);
                    listarProduccionDetalles(request, response,"eliminado");
                } catch (Exception ex) {
                    listarProduccionDetalles(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(ProduccionDetalleController.class.getName()).log(Level.SEVERE, null, ex);
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
        ProduccionDetalle produccionDetalle;
        ProduccionDetalleCRUD produccionDetalleCRUD;
        switch(action){
            case "nuevo":
                produccionDetalle = extraerProduccionDetalleForm(request);
                produccionDetalleCRUD = new ProduccionDetalleCRUD();
                try {
                    produccionDetalleCRUD.registrar(produccionDetalle);
                    listarProduccionDetalles(request, response,"registrado");
                } catch (Exception ex) {
                    listarProduccionDetalles(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(ProduccionDetalleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                produccionDetalle = extraerProduccionDetalleForm(request);
                produccionDetalleCRUD = new ProduccionDetalleCRUD();
                try {
                    produccionDetalleCRUD.actualizar(produccionDetalle);
                    listarProduccionDetalles(request, response,"actualizado");
                } catch (Exception ex) {
                    listarProduccionDetalles(request, response,"error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(ProduccionDetalleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <ProduccionDetalle> produccionDetalles;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                produccionDetalleCRUD = new ProduccionDetalleCRUD();
                try {
                    produccionDetalles = (List<ProduccionDetalle>) produccionDetalleCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("produccionDetalles",produccionDetalles);
                    RequestDispatcher view = request.getRequestDispatcher("produccionDetalle/produccionDetalles.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProduccionDetalles(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(ProduccionDetalleController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void listarProduccionDetalles(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<ProduccionDetalle> produccionDetalles;
        ProduccionDetalleCRUD produccionDetalleCrud = new ProduccionDetalleCRUD();
        try {
            produccionDetalles = (List<ProduccionDetalle>) produccionDetalleCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("produccionDetalles",produccionDetalles);
            //Enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("produccionDetalle/produccionDetalles.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(ProduccionDetalleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private ProduccionDetalle extraerProduccionDetalleForm(HttpServletRequest request) {
        ProduccionDetalle produccionDetalle = new ProduccionDetalle();
        produccionDetalle.setId_produccion(request.getParameter("id_produccion"));
        produccionDetalle.setId_madera(request.getParameter("id_madera"));
        produccionDetalle.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        return produccionDetalle;
    }
}
