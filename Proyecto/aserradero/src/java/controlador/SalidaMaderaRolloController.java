package controlador;

import dao.EmpleadoCRUD;
import dao.InventarioMaderaRolloCRUD;
import dao.SalidaMaderaRolloCRUD;
import entidades.Empleado;
import entidades.InventarioMaderaRollo;
import entidades.SalidaMaderaRollo;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
public class SalidaMaderaRolloController extends HttpServlet {

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
        String action = request.getParameter("action");
        SalidaMaderaRollo salidaMaderaRolloEC ;
        SalidaMaderaRollo salidaMaderaRollo;
        SalidaMaderaRolloCRUD salidaMaderaRolloCRUD;
        EmpleadoCRUD empleadoCRUD;
        switch(action){
            case "nuevo_salida":
                try{
                    //enviamos la lista de empleados
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>) empleadoCRUD.listarEmpleadoPorRoll("Empleado");
                    request.setAttribute("empleados",empleados);
                    
                    //Enviamos el inventario de madera rollo
                    InventarioMaderaRolloCRUD inventarioMRCRUD = new InventarioMaderaRolloCRUD();
                    List<InventarioMaderaRollo> inventarioMR = (List<InventarioMaderaRollo>) inventarioMRCRUD.listar();
                    request.setAttribute("inventarioMR",inventarioMR);
                    
                    //Enviamos la fecha actual 
                    Date fechaActual = Date.valueOf(LocalDate.now());
                    request.setAttribute("fechaActual",fechaActual);
                    RequestDispatcher view = request.getRequestDispatcher("salidaMaderaRollo/nuevoSalidaMadera.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarSalidaMaderaRollos(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar_salida":
                listarSalidaMaderaRollos(request, response,"salida");
                break;
            case "modificar":
                salidaMaderaRolloEC = new SalidaMaderaRollo();
                salidaMaderaRolloEC.setId_salida(Integer.valueOf(request.getParameter("id_salida")));
                salidaMaderaRolloCRUD = new SalidaMaderaRolloCRUD();
                try {
                    //enviamos la salidaMaderaRollo a modificar
                    salidaMaderaRollo = (SalidaMaderaRollo) salidaMaderaRolloCRUD.modificar(salidaMaderaRolloEC);
                    request.setAttribute("salidaMaderaRollo",salidaMaderaRollo);
                                    
                    //enviamos el inventario de madera rollo
                    InventarioMaderaRolloCRUD inventarioMRCRUD = new InventarioMaderaRolloCRUD();
                    List<InventarioMaderaRollo> inventarioMR = (List<InventarioMaderaRollo>) inventarioMRCRUD.listar();
                    request.setAttribute("inventarioMR",inventarioMR);
                    
                    RequestDispatcher view = request.getRequestDispatcher("salidaMaderaRollo/actualizarSalidaMaderaRollo.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarSalidaMaderaRollos(request, response, "error_modificar");
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
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
        response.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        SalidaMaderaRollo salidaMaderaRollo;
        SalidaMaderaRolloCRUD salidaMaderaRolloCRUD;
        switch (action){
            case "nuevo_salida":
                salidaMaderaRollo = extraerSalidaMaderaRolloForm(request);
                salidaMaderaRolloCRUD = new SalidaMaderaRolloCRUD();
                try {
                    salidaMaderaRolloCRUD.registrar(salidaMaderaRollo);
                    response.sendRedirect("/aserradero/SalidaMaderaRolloController?action=listar_salida"); // para evitar acciones repetidas al actualizar página
                } catch (Exception ex) {
                    listarSalidaMaderaRollos(request, response, "inventario_entrada_inalcansable");
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                salidaMaderaRollo = extraerSalidaMaderaRolloForm(request);
                salidaMaderaRolloCRUD = new SalidaMaderaRolloCRUD();
                try {
                    salidaMaderaRolloCRUD.actualizar(salidaMaderaRollo);
//                    request.getSession().setAttribute("mensaje", "valor_param"); // Enviar parametro
                    response.sendRedirect("/aserradero/SalidaMaderaRolloController?action=listar_salida"); // para evitar acciones repetidas al actualizar página
                } catch (Exception ex) {
                    listarSalidaMaderaRollos(request, response,"inventario_entrada_inalcansable");
                    System.out.println(ex);
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <SalidaMaderaRollo> salidas;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                salidaMaderaRolloCRUD = new SalidaMaderaRolloCRUD();
                try {
                    salidas = (List<SalidaMaderaRollo>)salidaMaderaRolloCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("salidas",salidas);
                    RequestDispatcher view = request.getRequestDispatcher("salidaMaderaRollo/salidaMaderaRollos.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarSalidaMaderaRollos(request, response, "error_buscar_campo");
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void listarSalidaMaderaRollos(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<SalidaMaderaRollo> salidas;
        SalidaMaderaRolloCRUD salidaCRUD = new SalidaMaderaRolloCRUD();
        try {
            //enviamos la lista de entradasMadera
            salidas = (List<SalidaMaderaRollo>)salidaCRUD.listar();
            request.setAttribute("salidas", salidas);
            //Enviamos el mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("salidaMaderaRollo/salidaMaderaRollos.jsp");
            view.forward(request, response);            
        }catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private SalidaMaderaRollo extraerSalidaMaderaRolloForm(HttpServletRequest request) {
        SalidaMaderaRollo salida = new SalidaMaderaRollo();
        salida.setId_salida(Integer.valueOf(request.getParameter("id_salida")));
        salida.setFecha(Date.valueOf(request.getParameter("fecha")));
        salida.setId_empleado(request.getParameter("id_empleado"));
        salida.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        salida.setVolumen_total(BigDecimal.valueOf((Double.valueOf(request.getParameter("volumen_total")))));
        return salida;
    }

}
