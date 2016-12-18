package controlador;

import dao.empleado.EmpleadoCRUD;
import dao.OtroGastoCRUD;
import entidades.empleado.Empleado;
import entidades.OtroGasto;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author rcortes
 */
public class OtroGastoController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
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
        request.setCharacterEncoding("UTF-8");
        //Llegan url
        String action = request.getParameter("action");
        OtroGasto otrogastoEC; //Enviar al CRUD
        OtroGasto otrogasto; //Respuesta del CRUD
        OtroGastoCRUD otrogastoCRUD;
        EmpleadoCRUD empleadoCRUD;
        switch (action) {
            case "nuevo":
                try {
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>) empleadoCRUD.listar("");
                    request.setAttribute("empleados", empleados);
                    RequestDispatcher view = request.getRequestDispatcher("otrogasto/nuevoOtroGasto.jsp");
                    view.forward(request, response);
                } catch (Exception ex) {
                    listarOtrosGastos(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarOtrosGastos(request, response, "Lista de Otros gastos");
                break;
            case "modificar":
                otrogastoEC = new OtroGasto();
                otrogastoEC.setId_gasto(Integer.valueOf(request.getParameter("id_gasto")));
                otrogastoCRUD = new OtroGastoCRUD();
                try {
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados;
                    empleados = (List<Empleado>) empleadoCRUD.listar("");
                    otrogasto = (OtroGasto) otrogastoCRUD.modificar(otrogastoEC);
                    request.setAttribute("otrogasto", otrogasto);
                    request.setAttribute("empleados", empleados);
                    RequestDispatcher view = request.getRequestDispatcher("otrogasto/actualizarOtroGasto.jsp");
                    view.forward(request, response);
                } catch (Exception ex) {
                    listarOtrosGastos(request, response, "error_modificar");
                    Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                otrogastoEC = new OtroGasto();
                otrogastoEC.setId_gasto(Integer.valueOf(request.getParameter("id_gasto")));
                otrogastoCRUD = new OtroGastoCRUD();
                try {
                    otrogastoCRUD.eliminar(otrogastoEC);
                    //enviar mensaje -> eliminado
                    response.sendRedirect("/aserradero/OtroGastoController?action=listar");
                } catch (Exception ex) {
                    listarOtrosGastos(request, response, "error_eliminar");
                    Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        OtroGasto otrogasto;
        OtroGastoCRUD otrogastoCRUD;
        switch (action) {
            case "nuevo":
                otrogasto = extraerOtroGastoForm(request, action);
                otrogastoCRUD = new OtroGastoCRUD();
                try {
                    otrogastoCRUD.registrar(otrogasto);
                    response.sendRedirect("/aserradero/OtroGastoController?action=listar");
                } catch (Exception ex) {
                    listarOtrosGastos(request, response, "error_registrar");
                    Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                otrogasto = extraerOtroGastoForm(request, action);
                otrogastoCRUD = new OtroGastoCRUD();
                try {
                    otrogastoCRUD.actualizar(otrogasto);
                    response.sendRedirect("/aserradero/OtroGastoController?action=listar");
                } catch (Exception ex) {
                    listarOtrosGastos(request, response, "error_actualizar");
                    Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List<OtroGasto> otrosgastos;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                otrogastoCRUD = new OtroGastoCRUD();
                try {
                    otrosgastos = (List<OtroGasto>) otrogastoCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("otrosgastos", otrosgastos);
                    RequestDispatcher view = request.getRequestDispatcher("otrogasto/otrosgastos.jsp");
                    view.forward(request, response);
                } catch (Exception ex) {
                    listarOtrosGastos(request, response, "error_buscar_campo");
                    Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
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
    //Mostrar lista de otrosgastos

    private void listarOtrosGastos(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<OtroGasto> otrosgastos;
        OtroGastoCRUD otrogastocrud = new OtroGastoCRUD();
        try {
            otrosgastos = (List<OtroGasto>) otrogastocrud.listar("");
            //Enviamos las listas al jsp
            request.setAttribute("otrosgastos", otrosgastos);
            //enviar mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view;
            view = request.getRequestDispatcher("otrogasto/otrosgastos.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(OtroGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Extraer datos del formulario
    private OtroGasto extraerOtroGastoForm(HttpServletRequest request, String action) {
        OtroGasto otrogasto = new OtroGasto();
        if(action.equals("actualizar")){
            otrogasto.setId_gasto(Integer.valueOf(request.getParameter("id_gasto")));
        }
        otrogasto.setFecha(Date.valueOf(request.getParameter("fecha")));
        otrogasto.setId_empleado(request.getParameter("id_empleado"));
        otrogasto.setNombre_gasto(request.getParameter("nombre_gasto"));
        otrogasto.setMonto(BigDecimal.valueOf((Double.valueOf(request.getParameter("monto")))));
        otrogasto.setObservacion(request.getParameter("observacion"));
        System.out.println("Hola");
        return otrogasto;
    }
}
