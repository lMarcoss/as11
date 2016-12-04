package controlador;

import dao.EntradaMaderaRolloCRUD;
import dao.EmpleadoCRUD;
import dao.ProveedorCRUD;
import entidades.EntradaMaderaRollo;
import entidades.Empleado;
import entidades.Proveedor;
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
 * @author rcortes
 */
public class EntradaMaderaRolloController extends HttpServlet {

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
        EntradaMaderaRollo entradaMaderaRolloEC ;
        EntradaMaderaRollo entrada;
        EntradaMaderaRolloCRUD entradaMaderaRolloCRUD;
        EmpleadoCRUD empleadoCRUD;
        ProveedorCRUD proveedorCRUD;
        switch(action){
            case "nuevo_entrada":
                try{
                    //enviamos la lista de empleados
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>) empleadoCRUD.listarEmpleadoPorRoll("Empleado");
                    request.setAttribute("empleados",empleados);
                    
                    //enviamos la lista de proveedores
                    proveedorCRUD = new ProveedorCRUD();
                    List<Proveedor> proveedores = (List<Proveedor>)proveedorCRUD.listar();
                    request.setAttribute("proveedores", proveedores);
                    
                    //Enviamos la lista de choferes
                    List<Empleado> choferes = (List<Empleado>) empleadoCRUD.listarEmpleadoPorRoll("Chofer");
                    request.setAttribute("choferes",choferes);
                    
                    //Enviamos la fecha actual 
                    Date fechaActual = Date.valueOf(LocalDate.now());
                    request.setAttribute("fechaActual",fechaActual);
                    RequestDispatcher view = request.getRequestDispatcher("entradaMaderaRollo/nuevoEntradaMaderaRollo.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEntradaMaderas(request, response, "error_nuevo");
                    System.out.println(ex);
                    Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar_entrada":
                listarEntradaMaderas(request, response,"entrada");
                break;
            
            case "modificar":
                entradaMaderaRolloEC = new EntradaMaderaRollo();
                entradaMaderaRolloEC.setId_entrada(Integer.valueOf(request.getParameter("id_entrada")));
                entradaMaderaRolloCRUD = new EntradaMaderaRolloCRUD();
                try {
                    //enviamos la entradaMaderaRollo a modificar
                    entrada = (EntradaMaderaRollo) entradaMaderaRolloCRUD.modificar(entradaMaderaRolloEC);
                    request.setAttribute("entrada",entrada);
                    
                    //enviamos la lista de proveedores
                    proveedorCRUD = new ProveedorCRUD();
                    List<Proveedor> proveedores = (List<Proveedor>)proveedorCRUD.listar();
                    request.setAttribute("proveedores", proveedores);
                    
                    //Enviamos la lista de choferes
                    empleadoCRUD = new EmpleadoCRUD();
                    List<Empleado> choferes = (List<Empleado>) empleadoCRUD.listarEmpleadoPorRoll("Chofer");
                    request.setAttribute("choferes",choferes);
                    
                    RequestDispatcher view = request.getRequestDispatcher("entradaMaderaRollo/actualizarEntradaMaderaRollo.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEntradaMaderas(request, response, "error_modificar");
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                entradaMaderaRolloEC = new EntradaMaderaRollo();
                entradaMaderaRolloEC.setId_entrada(Integer.valueOf(request.getParameter("id_entrada")));
                entradaMaderaRolloCRUD = new EntradaMaderaRolloCRUD();
                try {
                    entradaMaderaRolloCRUD.eliminar(entradaMaderaRolloEC);
                    //enviar mensaje -> eliminado
                    response.sendRedirect("/aserradero/EntradaMaderaRolloController?action=listar_entrada");
                } catch (Exception e) {
                    listarEntradaMaderas(request, response,"error_eliminar");
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, e);
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
        EntradaMaderaRollo entradaMaderaRollo;
        EntradaMaderaRolloCRUD entradaMaderaRolloCRUD;
        switch (action){
            case "nuevo_entrada":
                entradaMaderaRollo = extraerEntradaMaderaForm(request);
                entradaMaderaRolloCRUD = new EntradaMaderaRolloCRUD();
                try {
                    entradaMaderaRolloCRUD.registrar(entradaMaderaRollo);
                    //enviar mensaje -> registrado
                    response.sendRedirect("/aserradero/EntradaMaderaRolloController?action=listar_entrada");
                } catch (Exception ex) {
                    listarEntradaMaderas(request, response, "error_registrar_entrada_madera_rollo");
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                entradaMaderaRollo = extraerEntradaMaderaForm(request);
                entradaMaderaRolloCRUD = new EntradaMaderaRolloCRUD();
                try {
                    entradaMaderaRolloCRUD.actualizar(entradaMaderaRollo);
                    //enviar mensaje -> actualizado
                    response.sendRedirect("/aserradero/EntradaMaderaRolloController?action=listar_entrada");
                } catch (Exception ex) {
                    listarEntradaMaderas(request, response,"error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <EntradaMaderaRollo> entradas;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                entradaMaderaRolloCRUD = new EntradaMaderaRolloCRUD();
                try {
                    entradas = (List<EntradaMaderaRollo>)entradaMaderaRolloCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("entradas",entradas);
                    RequestDispatcher view = request.getRequestDispatcher("entradaMaderaRollo/entradaMaderaRollos.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarEntradaMaderas(request, response, "error_buscar_campo");
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
    private void listarEntradaMaderas(HttpServletRequest request, HttpServletResponse response,String mensaje){
        List<EntradaMaderaRollo> entradas;
        EntradaMaderaRolloCRUD entradaMaderaRolloCRUD = new EntradaMaderaRolloCRUD();
        try {
            //enviamos la lista de entradasMadera
            entradas = (List<EntradaMaderaRollo>)entradaMaderaRolloCRUD.listar();
            request.setAttribute("entradas", entradas);
            //Enviamos el mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("entradaMaderaRollo/entradaMaderaRollos.jsp");
            view.forward(request, response);            
        }catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(EntradaMaderaRolloController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private EntradaMaderaRollo extraerEntradaMaderaForm(HttpServletRequest request){
        EntradaMaderaRollo entrada = new EntradaMaderaRollo();
        entrada.setId_entrada(Integer.valueOf(request.getParameter("id_entrada")));  
        entrada.setFecha(Date.valueOf(request.getParameter("fecha")));
        entrada.setId_proveedor(request.getParameter("id_proveedor"));
        entrada.setId_chofer(request.getParameter("id_chofer"));
        entrada.setId_empleado(request.getParameter("id_empleado"));
        entrada.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        entrada.setVolumen_primario(BigDecimal.valueOf((Double.valueOf(request.getParameter("volumen_primario")))));
        entrada.setVolumen_secundario(BigDecimal.valueOf((Double.valueOf(request.getParameter("volumen_secundario")))));
        entrada.setVolumen_terciario(BigDecimal.valueOf((Double.valueOf(request.getParameter("volumen_terciario")))));
      return entrada;
    }
}