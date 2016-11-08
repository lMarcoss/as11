package controlador;

import dao.ClienteCRUD;
import dao.EmpleadoCRUD;
import dao.MaderaClasificacionCRUD;
import dao.VentaCRUD;
import dao.VentaMayoreoCRUD;
import entidades.Cliente;
import entidades.Empleado;
import entidades.Venta;
import entidades.VentaMayoreo;
import entidadesVirtuales.CostoMaderaClasificacion;
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
public class VentaMayoreoController extends HttpServlet {

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
        VentaMayoreo ventaMayoreoEC; //Enviar al CRUD
        VentaMayoreo ventaMayoreo; //Respuesta del CRUD
        VentaMayoreoCRUD ventaMayoreoCRUD;
        switch(action){
            case "nuevo":
                try {
                    //enviar lista de ventas al jsp
                    VentaCRUD ventaCRUD= new VentaCRUD();
                    List<Venta> ventas;
                    ventas = (List<Venta>)ventaCRUD.listarVentasMayoreo();
                    request.setAttribute("ventas",ventas);
                    //enviamos el id de la venta
                    Integer next_id_venta=ventaCRUD.SiguienteIDVenta();
                    request.setAttribute("siguienteventa", next_id_venta);
                    
                    //Enviamos la lista de clientes
                    ClienteCRUD clienteCRUD= new ClienteCRUD();
                    List<Cliente> clientes = (List<Cliente>)clienteCRUD.listar();
                    request.setAttribute("clientes",clientes);
                    
                    //Enviamos la lista de empleados
                    EmpleadoCRUD empleadoCRUD= new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>)empleadoCRUD.listarEmpleadoPorRoll("Empleado");
                    request.setAttribute("empleados",empleados);
                    //enviamos lista de maderaClasificación al jsp
                    MaderaClasificacionCRUD maderaClasificacionCRUD= new MaderaClasificacionCRUD();
                    List<CostoMaderaClasificacion> costoMaderaClasificaciones;
                    costoMaderaClasificaciones = (List<CostoMaderaClasificacion>)maderaClasificacionCRUD.listarCostoMaderaClasif();
                    request.setAttribute("costoMaderaClasificaciones",costoMaderaClasificaciones);
                    
                    RequestDispatcher view = request.getRequestDispatcher("ventaMayoreo/nuevoVentaMayoreo.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaMayoreos(request, response,"error_nuevo");
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case "listar":
                listarVentaMayoreos(request, response,"ventas por mayoreo");
                break;
            case "modificar":
                ventaMayoreoEC = new VentaMayoreo();
                ventaMayoreoEC.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
                ventaMayoreoEC.setId_madera(request.getParameter("id_madera"));
                ventaMayoreoCRUD = new VentaMayoreoCRUD();
                try {
                    //Enviamos clasificacion de madera con su costo
                    MaderaClasificacionCRUD maderaClasificacionCRUD= new MaderaClasificacionCRUD();
                    List<CostoMaderaClasificacion> costoMaderaClasificaciones;
                    costoMaderaClasificaciones = (List<CostoMaderaClasificacion>)maderaClasificacionCRUD.listarCostoMaderaClasif();
                    request.setAttribute("costoMaderaClasificaciones",costoMaderaClasificaciones);
                    //enviamos la venta a actualizar
                    ventaMayoreo = (VentaMayoreo) ventaMayoreoCRUD.modificar(ventaMayoreoEC);
                    request.setAttribute("ventaMayoreo",ventaMayoreo);
                    RequestDispatcher view = request.getRequestDispatcher("ventaMayoreo/actualizarVentaMayoreo.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaMayoreos(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(VentaMayoreoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                ventaMayoreoEC = new VentaMayoreo();
                ventaMayoreoEC.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
                ventaMayoreoEC.setId_madera(request.getParameter("id_madera"));
                ventaMayoreoCRUD = new VentaMayoreoCRUD();
                try {
                    ventaMayoreoCRUD.eliminar(ventaMayoreoEC);
                    listarVentaMayoreos(request, response,"eliminado");
                } catch (Exception ex) {
                    listarVentaMayoreos(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(VentaMayoreoController.class.getName()).log(Level.SEVERE, null, ex);
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
        VentaMayoreo ventaMayoreo;
        VentaMayoreoCRUD ventaMayoreoCRUD;
        switch(action){
            case "nuevo":
                ventaMayoreo = extraerVentaMayoreoForm(request);
                ventaMayoreoCRUD = new VentaMayoreoCRUD();
                try {
                    ventaMayoreoCRUD.registrar(ventaMayoreo);
                    listarVentaMayoreos(request, response,"registrado");
                } catch (Exception ex) {
                    listarVentaMayoreos(request, response,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(VentaMayoreoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                ventaMayoreo = extraerVentaMayoreoForm(request);
                ventaMayoreoCRUD = new VentaMayoreoCRUD();
                try {
                    ventaMayoreoCRUD.actualizar(ventaMayoreo);
                    listarVentaMayoreos(request, response,"actualizado");
                } catch (Exception ex) {
                    listarVentaMayoreos(request, response, "error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(VentaMayoreoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <VentaMayoreo> ventaMayoreos;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                ventaMayoreoCRUD = new VentaMayoreoCRUD();
                try {
                    ventaMayoreos = (List<VentaMayoreo>)ventaMayoreoCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("ventaMayoreos",ventaMayoreos);
                    RequestDispatcher view = request.getRequestDispatcher("ventaMayoreo/ventaMayoreos.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentaMayoreos(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(VentaMayoreoController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarVentaMayoreos(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<VentaMayoreo> ventaMayoreos;
        VentaMayoreoCRUD ventaMayoreoCrud = new VentaMayoreoCRUD();
        try {
            ventaMayoreos = (List<VentaMayoreo>)ventaMayoreoCrud.listar();
            //Enviamos las listas al jsp
            request.setAttribute("ventaMayoreos",ventaMayoreos);
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("ventaMayoreo/ventaMayoreos.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(VentaMayoreoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private VentaMayoreo extraerVentaMayoreoForm(HttpServletRequest request) {
        VentaMayoreo ventaMayoreo = new VentaMayoreo();
        ventaMayoreo.setId_venta(Integer.valueOf(request.getParameter("id_venta")));
        ventaMayoreo.setId_madera(request.getParameter("id_madera"));
        ventaMayoreo.setNum_piezas(Integer.valueOf(request.getParameter("num_piezas")));
        ventaMayoreo.setVolumen(Float.valueOf(request.getParameter("volumen")));
        ventaMayoreo.setMonto(Float.valueOf(request.getParameter("monto")));
        return ventaMayoreo;
    }
}
