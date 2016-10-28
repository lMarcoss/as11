package controlador;

import dao.EmpleadoCRUD;
import dao.ProduccionMaderaCRUD;
import entidades.Empleado;
import entidades.ProduccionMadera;
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
public class ProduccionMaderaController extends HttpServlet {

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
        ProduccionMadera produccionMaderaEC; //Enviar al CRUD
        ProduccionMaderaCRUD produccionMaderaCRUD;
        switch(action){
            case "nuevo":
                try {
                    EmpleadoCRUD empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>)empleadoCRUD.listar();
                    request.setAttribute("empleados",empleados);
                    RequestDispatcher view = request.getRequestDispatcher("produccionMadera/nuevoProduccionMadera.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProduccionMaderas(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarProduccionMaderas(request, response,"Lista de produccion madera");
                break;
            case "eliminar":
                produccionMaderaEC = new ProduccionMadera();
                produccionMaderaEC.setId_produccion(request.getParameter("id_produccion"));
                produccionMaderaCRUD = new ProduccionMaderaCRUD();
                try {
                    produccionMaderaCRUD.eliminar(produccionMaderaEC);
                    listarProduccionMaderas(request, response,"eliminado");
                } catch (Exception ex) {
                    listarProduccionMaderas(request, response,"error_eliminar");
                    Logger.getLogger(ProduccionMaderaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_produccion":
                String id_produccionMadera = request.getParameter("id_produccion");
                buscarProduccionMaderaPorId(request, response, id_produccionMadera);
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
        ProduccionMadera produccionMadera;
        ProduccionMaderaCRUD produccionMaderaCRUD;
        switch(action){
            case "nuevo":
                produccionMadera = extraerProduccionMaderaForm(request);
                produccionMaderaCRUD = new ProduccionMaderaCRUD();
                try {
                    produccionMaderaCRUD.registrar(produccionMadera);
                    listarProduccionMaderas(request, response,"registrado");
                } catch (Exception ex) {
                    listarProduccionMaderas(request, response,"error_registrar");
                    Logger.getLogger(ProduccionMaderaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <ProduccionMadera> produccionMaderas;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                produccionMaderaCRUD = new ProduccionMaderaCRUD();
                try {
                    produccionMaderas = (List<ProduccionMadera>)produccionMaderaCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("produccionMaderas",produccionMaderas);
                    RequestDispatcher view = request.getRequestDispatcher("produccionMadera/produccionMaderas.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarProduccionMaderas(request, response, "error_buscar_campo");
                    Logger.getLogger(ProduccionMaderaController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarProduccionMaderas(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<ProduccionMadera> produccionMaderas;
        ProduccionMaderaCRUD produccionMaderaCrud = new ProduccionMaderaCRUD();
        try {
            produccionMaderas = (List<ProduccionMadera>)produccionMaderaCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("produccionMaderas",produccionMaderas);
            //Enviar mensaje
            request.setAttribute("mensaje",mensaje);
         
            RequestDispatcher view = request.getRequestDispatcher("produccionMadera/produccionMaderas.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(ProduccionMaderaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarProduccionMaderaPorId(HttpServletRequest request, HttpServletResponse response,String id_produccionMadera) {
        List<ProduccionMadera> produccionMaderas;
        ProduccionMaderaCRUD produccionMaderaCrud = new ProduccionMaderaCRUD();
        try {
            produccionMaderas = (List<ProduccionMadera>)produccionMaderaCrud.buscarPorId(id_produccionMadera);
            //Enviamos las listas al jsp
            request.setAttribute("produccionMaderas",produccionMaderas);
            RequestDispatcher view = request.getRequestDispatcher("produccionMadera/produccionMaderas.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            listarProduccionMaderas(request, response, "error_buscar_id");
            System.out.println(ex);
            Logger.getLogger(ProduccionMaderaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Extraer datos del formulario
    private ProduccionMadera extraerProduccionMaderaForm(HttpServletRequest request) {
        ProduccionMadera produccionMadera = new ProduccionMadera();
        produccionMadera.setFecha(Date.valueOf(request.getParameter("fecha")));
        produccionMadera.setId_produccion(request.getParameter("id_produccion"));
        produccionMadera.setId_empleado(request.getParameter("id_empleado"));
        return produccionMadera;
    }
}