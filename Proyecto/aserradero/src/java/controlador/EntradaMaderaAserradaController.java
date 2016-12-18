package controlador;

import controlador.registros.PersonaController;
import controlador.empleado.EmpleadoController;
import dao.empleado.EmpleadoCRUD;
import dao.MaderaAserradaClasifCRUD;
import dao.EntradaMaderaAserradaCRUD;
import entidades.empleado.Empleado;
import entidades.MaderaAserradaClasif;
import entidades.EntradaMaderaAserrada;
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
 * @author lmarcoss
 */
public class EntradaMaderaAserradaController extends HttpServlet {

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
        EntradaMaderaAserrada entradaMaderaAserradaEC; //Enviar al CRUD
        EntradaMaderaAserradaCRUD entradaMaderaAserradaCRUD;
        switch(action){
            case "nuevo":
                try {
                    //Enviamos la lista de empleados
                    EmpleadoCRUD empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);
                    
                    //enviamos lista de maderaClasificación al jsp
                    MaderaAserradaClasifCRUD maderaClasificacionCRUD= new MaderaAserradaClasifCRUD();
                    List<MaderaAserradaClasif> clasificaciones = (List<MaderaAserradaClasif>)maderaClasificacionCRUD.listar();
                    request.setAttribute("clasificaciones",clasificaciones);
                    
                    RequestDispatcher view = request.getRequestDispatcher("entradaMaderaAserrada/nuevoEntradaMaderaAserrada.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEntradaMaderaAserrada(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarEntradaMaderaAserrada(request, response,"Lista de produccion madera");
                break;
            case "modificar":
                entradaMaderaAserradaEC = new EntradaMaderaAserrada();
                entradaMaderaAserradaEC.setId_entrada(Integer.valueOf(request.getParameter("id_entrada")));
                entradaMaderaAserradaCRUD = new EntradaMaderaAserradaCRUD();
                
                try {
                    //enviamos la entradaMaderaAserrada a modificar
                    EntradaMaderaAserrada entrada = (EntradaMaderaAserrada) entradaMaderaAserradaCRUD.modificar(entradaMaderaAserradaEC);
                    request.setAttribute("entrada",entrada);
                    RequestDispatcher view = request.getRequestDispatcher("entradaMaderaAserrada/actualizarEntradaMaderaAserrada.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    System.err.println(ex);
                    listarEntradaMaderaAserrada(request, response, "error_modificar");
                    Logger.getLogger(EntradaMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                entradaMaderaAserradaEC = new EntradaMaderaAserrada();
                entradaMaderaAserradaEC.setId_entrada(Integer.valueOf(request.getParameter("id_entrada")));
                entradaMaderaAserradaCRUD = new EntradaMaderaAserradaCRUD();
                try {
                    entradaMaderaAserradaCRUD.eliminar(entradaMaderaAserradaEC);
                    //enviar mensaje -> eliminado
                    response.sendRedirect("/aserradero/EntradaMaderaAserradaController?action=listar");
                } catch (Exception ex) {
                    listarEntradaMaderaAserrada(request, response,"error_eliminar");
                    Logger.getLogger(EntradaMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_produccion":
                String fecha = request.getParameter("fecha");
                buscarEntradaMaderaAserradaPorFecha(request, response, fecha);
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
        EntradaMaderaAserrada entradaMaderaAserrada;
        EntradaMaderaAserradaCRUD entradaMaderaAserradaCRUD;
        switch(action){
            case "nuevo":
                entradaMaderaAserrada = extraerEntradaMaderaAserradaForm(request);
                entradaMaderaAserradaCRUD = new EntradaMaderaAserradaCRUD();
                try {
                    entradaMaderaAserradaCRUD.registrar(entradaMaderaAserrada);
                    //enviar mensaje -> registrado
                    response.sendRedirect("/aserradero/EntradaMaderaAserradaController?action=listar");
                } catch (Exception ex) {
                    listarEntradaMaderaAserrada(request, response,"error_registrar");
                    Logger.getLogger(EntradaMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                entradaMaderaAserrada = extraerEntradaMaderaAserradaForm(request);
                entradaMaderaAserradaCRUD = new EntradaMaderaAserradaCRUD();
                try {
                    entradaMaderaAserradaCRUD.actualizar(entradaMaderaAserrada);
                    //enviar mensaje -> actualizado
                    response.sendRedirect("/aserradero/EntradaMaderaAserradaController?action=listar");
                } catch (Exception ex) {
                    listarEntradaMaderaAserrada(request, response,"error_actualizar");
                    Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <EntradaMaderaAserrada> listaMEAserrada;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                entradaMaderaAserradaCRUD = new EntradaMaderaAserradaCRUD();
                try {
                    listaMEAserrada = (List<EntradaMaderaAserrada>)entradaMaderaAserradaCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("listaMEAserrada",listaMEAserrada);
                    RequestDispatcher view = request.getRequestDispatcher("entradaMaderaAserrada/listarEntradaMaderaAserrada.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEntradaMaderaAserrada(request, response, "error_buscar_campo");
                    Logger.getLogger(EntradaMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarEntradaMaderaAserrada(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<EntradaMaderaAserrada> listaMEAserrada;
        EntradaMaderaAserradaCRUD entradaMaderaAserradaCrud = new EntradaMaderaAserradaCRUD();
        try {
            listaMEAserrada = (List<EntradaMaderaAserrada>)entradaMaderaAserradaCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("listaMEAserrada",listaMEAserrada);
            //Enviar mensaje
            request.setAttribute("mensaje",mensaje);
         
            RequestDispatcher view = request.getRequestDispatcher("entradaMaderaAserrada/listarEntradaMaderaAserrada.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(EntradaMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarEntradaMaderaAserradaPorFecha(HttpServletRequest request, HttpServletResponse response,String fecha) {
        List<EntradaMaderaAserrada> listaMEAserrada;
        EntradaMaderaAserradaCRUD entradaMaderaAserradaCRUD = new EntradaMaderaAserradaCRUD();
        try {
            //consultamos la lista de maderas producidas por fecha
            listaMEAserrada = (List<EntradaMaderaAserrada>)entradaMaderaAserradaCRUD.buscarPorFecha(fecha);
            //enviamos la lista a la vista
            request.setAttribute("listaMEAserrada",listaMEAserrada);

            RequestDispatcher view = request.getRequestDispatcher("entradaMaderaAserrada/nuevoEntradaMaderaAserrada.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            listarEntradaMaderaAserrada(request, response, "error_buscar_fecha");
            Logger.getLogger(EntradaMaderaAserradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Extraer datos del formulario
    private EntradaMaderaAserrada extraerEntradaMaderaAserradaForm(HttpServletRequest request) {
        EntradaMaderaAserrada entradaMaderaAserrada = new EntradaMaderaAserrada();
        entradaMaderaAserrada.setId_entrada(Integer.valueOf(request.getParameter("id_entrada")));
        entradaMaderaAserrada.setFecha(Date.valueOf(request.getParameter("fecha")));
        entradaMaderaAserrada.setId_madera(request.getParameter("id_madera"));
        entradaMaderaAserrada.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        entradaMaderaAserrada.setId_empleado(request.getParameter("id_empleado"));
        return entradaMaderaAserrada;
    }
}