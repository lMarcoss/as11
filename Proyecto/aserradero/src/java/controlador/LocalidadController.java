
package controlador;

import dao.LocalidadCRUD;
import dao.MunicipioCRUD;
import entidades.Localidad;
import entidades.Municipio;
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
public class LocalidadController extends HttpServlet {

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
        Localidad localidadEC; //Enviar al CRUD
        Localidad localidad; //Respuesta del CRUD
        LocalidadCRUD localidadCRUD;
        switch(action){
            case "nuevo":
                MunicipioCRUD municipioCRUD = new MunicipioCRUD();
                List<Municipio> municipios;
                try {
                    municipios = (List<Municipio>)municipioCRUD.listar();
                    request.setAttribute("municipios",municipios);
                    RequestDispatcher view = request.getRequestDispatcher("localidad/nuevoLocalidad.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarLocalidades(request, response,"error_nuevo");
                    Logger.getLogger(LocalidadController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarLocalidades(request, response,"");
                break;
            case "modificar":
                localidadEC = new Localidad();
                localidadEC.setNombre_localidad(request.getParameter("nombre_localidad"));
                localidadCRUD = new LocalidadCRUD();
                try {
                    localidad = (Localidad) localidadCRUD.modificar(localidadEC);
                    request.setAttribute("localidad",localidad);
                    RequestDispatcher view = request.getRequestDispatcher("localidad/actualizarLocalidad.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    System.out.println(ex);
                    listarLocalidades(request, response, "error_modificar");
                    Logger.getLogger(LocalidadController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                localidadEC = new Localidad();
                localidadEC.setNombre_localidad(request.getParameter("nombre_localidad"));
                localidadCRUD = new LocalidadCRUD();
                try {
                    localidadCRUD.eliminar(localidadEC);
                    listarLocalidades(request, response,"eliminado");
                } catch (Exception ex) {
                    System.out.println(ex);
                    listarLocalidades(request, response, "error_eliminar");
                    Logger.getLogger(LocalidadController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_localidad":
                String nombre_localidad = request.getParameter("nombre_localidad");
                buscarLocalidad(request, response, nombre_localidad);
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
        Localidad localidad;
        LocalidadCRUD localidadCRUD;
        switch(action){
            case "nuevo":
                localidad = extraerLocalidadForm(request);
                localidadCRUD = new LocalidadCRUD();
                try {
                    localidadCRUD.registrar(localidad);
                    listarLocalidades(request, response,"registrado");
                } catch (Exception ex) {
                    listarLocalidades(request, response, "error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(LocalidadController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                localidad = extraerLocalidadForm(request);
                localidadCRUD = new LocalidadCRUD();
                try {
                    localidadCRUD.actualizar(localidad);
                    listarLocalidades(request, response,"actualizado");
                } catch (Exception ex) {
                    listarLocalidades(request, response, "error_actualizar");
                    Logger.getLogger(LocalidadController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Localidad> localidades;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                localidadCRUD = new LocalidadCRUD();
                try {
                    localidades = (List<Localidad>)localidadCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("localidades",localidades);
                    RequestDispatcher view = request.getRequestDispatcher("localidad/localidades.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarLocalidades(request, response, "error_buscar_campo");
                    Logger.getLogger(LocalidadController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void listarLocalidades(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<Localidad> localidades;
        LocalidadCRUD localidadCrud = new LocalidadCRUD();
        try {
            localidades = (List<Localidad>)localidadCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("localidades",localidades);
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("localidad/localidades.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(LocalidadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private Localidad extraerLocalidadForm(HttpServletRequest request) {
        Localidad localidad = new Localidad();
        localidad.setNombre_localidad(request.getParameter("nombre_localidad"));
        localidad.setNombre_municipio(request.getParameter("nombre_municipio"));
        localidad.setTelefono_localidad(request.getParameter("telefono"));
        return localidad;
    }

    private void buscarLocalidad(HttpServletRequest request, HttpServletResponse response, String nombre_localidad) {
        List<Localidad> localidades;
        LocalidadCRUD localidadCrud = new LocalidadCRUD();
        try {
            localidades = (List<Localidad>)localidadCrud.buscarLocalidad(nombre_localidad);
            //Enviamos las listas al jsp
            request.setAttribute("localidades",localidades);
            RequestDispatcher view = request.getRequestDispatcher("localidad/localidades.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            listarLocalidades(request, response, "error_buscar_localidad");
            System.out.println(ex);
            Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
