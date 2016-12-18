
package controlador.registros;

import dao.registros.MunicipioCRUD;
import entidades.registros.Municipio;
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
public class MunicipioController extends HttpServlet {

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
        Municipio municipioEC; //Enviar al CRUD
        Municipio municipio; //Respuesta del CRUD
        MunicipioCRUD municipioCRUD;
        switch(action){
            case "listar":
                listarMunicipios(request, response,"");
                break;
            case "modificar":
                municipioEC = new Municipio();
                municipioEC.setNombre_municipio(request.getParameter("nombre_municipio"));
                municipioCRUD = new MunicipioCRUD();
                try {
                    municipio = municipioCRUD.modificar(municipioEC);
                    request.setAttribute("municipio",municipio);
                    RequestDispatcher view = request.getRequestDispatcher("municipio/actualizarMunicipio.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarMunicipios(request, response, "error_modificar");
                    Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                municipioEC = new Municipio();
                municipioEC.setNombre_municipio(request.getParameter("nombre_municipio"));
                municipioCRUD = new MunicipioCRUD();
                try {
                    municipioCRUD.eliminar(municipioEC);
                    listarMunicipios(request, response,"eliminado");
                } catch (Exception ex) {
                    listarMunicipios(request, response, "error_eliminar");
                    Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_municipio":
                String nombre_municipio = request.getParameter("nombre_municipio");
                try {
                    buscarMunicipio(request, response, nombre_municipio);
                } catch (Exception ex) {
                    listarMunicipios(request, response, "error_buscar");
                    Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
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
        Municipio municipio;
        MunicipioCRUD municipioCRUD;
        switch(action){
            case "nuevo":
                municipio = extraerMunicipioForm(request);
                municipioCRUD = new MunicipioCRUD();
                try {
                    municipioCRUD.registrar(municipio);
                    listarMunicipios(request, response,"registrado");
                } catch (Exception ex) {
                    listarMunicipios(request, response,"error_registrar");
                    Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                municipio = extraerMunicipioForm(request);
                municipioCRUD = new MunicipioCRUD();
                try {
                    municipioCRUD.actualizar(municipio);
                    listarMunicipios(request, response,"actualizado");
                } catch (Exception ex) {
                    listarMunicipios(request, response,"error_actualizar");
                    Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Municipio> municipios;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                municipioCRUD = new MunicipioCRUD();
                try {
                    municipios = (List<Municipio>)municipioCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("municipios",municipios);
                    RequestDispatcher view = request.getRequestDispatcher("municipio/municipios.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarMunicipios(request, response, "error_buscar_campo");
                    Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
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

    //Mostrar lista de municipios
    private void listarMunicipios(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<Municipio> municipios;
        MunicipioCRUD municipiocrud = new MunicipioCRUD();
        try {
            municipios = (List<Municipio>)municipiocrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("municipios",municipios);
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view;
            view = request.getRequestDispatcher("municipio/municipios.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(MunicipioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private Municipio extraerMunicipioForm(HttpServletRequest request) {
        Municipio municipio = new Municipio();
        municipio.setNombre_municipio(request.getParameter("nombre_municipio"));
        municipio.setEstado(request.getParameter("estado"));
        municipio.setTelefono(request.getParameter("telefono"));
        return municipio;
    }

    private void buscarMunicipio(HttpServletRequest request, HttpServletResponse response, String nombre_municipio) throws Exception{
        List<Municipio> municipios;
        MunicipioCRUD municipioCRUDCrud = new MunicipioCRUD();
        try {
            municipios = (List<Municipio>)municipioCRUDCrud.buscarMunicipio(nombre_municipio);
            //Enviamos las listas al jsp
            request.setAttribute("municipios",municipios);
            RequestDispatcher view = request.getRequestDispatcher("municipio/municipios.jsp");
            view.forward(request,response);
        } catch (ServletException | IOException ex) {
            listarMunicipios(request, response, "error_buscar_municipio");
            System.out.println(ex);
            Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
