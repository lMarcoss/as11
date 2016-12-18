package controlador;

import ticketVenta.DatosClienteTicket;
import dao.registros.ClienteCRUD;
import dao.empleado.EmpleadoCRUD;
import dao.VentaCRUD;
import dao.VentaExtraCRUD;
import dao.VentaMayoreoCRUD;
import dao.VentaPaqueteCRUD;
import entidades.registros.Cliente;
import entidades.empleado.Empleado;
import entidades.Venta;
import entidades.VentaExtra;
import entidades.VentaMayoreo;
import entidades.VentaPaquete;
import ticketVenta.Madera;
import ticketVenta.Paquete;
import ticketVenta.DatosVentaExtra;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lmarcoss
 */
public class VentaController extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        //Crear ventas 
        
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
        Venta ventaEC; //Enviar al CRUD
        Venta venta; //Respuesta del CRUD
        RequestDispatcher view;
        VentaCRUD ventaCRUD;
        String id_venta;
        switch(action){
            case "nuevo":
                try {
                    //Enviamos la lista de clientes
                    ClienteCRUD clienteCRUD= new ClienteCRUD();
                    List<Cliente> clientes = (List<Cliente>)clienteCRUD.listar("");
                    request.setAttribute("clientes",clientes);
                    
                    //Enviamos la lista de empleados
                    EmpleadoCRUD empleadoCRUD= new EmpleadoCRUD();
                    List<Empleado> empleados = (List<Empleado>)empleadoCRUD.listarEmpleadoPorRol("Empleado");
                    request.setAttribute("empleados",empleados);
                    
                    view = request.getRequestDispatcher("venta/nuevoVenta.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentas(request, response,"error_nuevo");
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case "listar":
                listarVentas(request, response,"Lista de ventas");
                break;
            case "cobrar_venta":
                ventaEC = new Venta();
                ventaEC.setId_venta(request.getParameter("id_venta"));
                ventaCRUD = new VentaCRUD();
                try {
                    venta = (Venta) ventaCRUD.modificar(ventaEC);
                    request.setAttribute("venta",venta);
                    view = request.getRequestDispatcher("venta/actualizarVenta.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentas(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                ventaEC = new Venta();
                ventaEC.setId_venta(request.getParameter("id_venta"));
                ventaCRUD = new VentaCRUD();
                try {
                    ventaCRUD.eliminar(ventaEC);
                    listarVentas(request, response,"eliminado");
                } catch (Exception ex) {
                    listarVentas(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar_venta":
                id_venta = request.getParameter("id_venta");
                buscarVentaPorId(request, response, id_venta);
                break;
            case "ver_ticket":
                venta = new Venta();
                venta.setId_venta(request.getParameter("id_venta"));
                venta.setId_cliente(request.getParameter("id_cliente"));
                venta.setTipo_venta(request.getParameter("tipo_venta"));
                String tipo_ticket= request.getParameter("tipo_ticket");
                ventaCRUD = new VentaCRUD();
                try {
                    if(ventaCRUD.ventaPagado(venta)){
                        System.out.println("Venta está pagada en el Controlador");
                        mostrarTicket(request, response, venta, tipo_ticket);
                    }else{
                        System.out.println("Venta no pagada");
                        listarVentas(request, response, "error_ticket");
                    }
                } catch (Exception ex) {
                    listarVentas(request, response, "error_ticket");
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
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
        String tipo_venta = request.getParameter("tipo_venta");
        Venta venta;
        VentaCRUD ventaCRUD;
        switch(action){
            case "nuevo":
                venta = extraerVentaForm(request);
                ventaCRUD = new VentaCRUD();
                try {
                    ventaCRUD.registrar(venta);
                    switch(tipo_venta){//Este tipo de venta es mayoreo y proviene de ajax
                        case "mayoreo":
                            //VentaMayoreo VM=new VentaMayoreo();
                            HttpSession sesion_ajax = request.getSession(true);
                            ArrayList<VentaMayoreo> VentaMay = (ArrayList<VentaMayoreo>) sesion_ajax.getAttribute("detalle_venta_mayoreo");
                            VentaMayoreoCRUD ventaMayoreoCRUD;
                            ventaMayoreoCRUD = new VentaMayoreoCRUD();
                            for(VentaMayoreo a:VentaMay){
                                ventaMayoreoCRUD.registrar(a);
                            }
                            response.sendRedirect("/aserradero/VentaMayoreoController?action=listar");
                            break;
                        case "extra":
                            sesion_ajax = request.getSession(true);
                            ArrayList<VentaExtra> VentaExt = (ArrayList<VentaExtra>) sesion_ajax.getAttribute("detalle_venta_extra");                            
                            VentaExtraCRUD VentaExtraCrud;
                            VentaExtraCrud = new VentaExtraCRUD();
                            for(VentaExtra a:VentaExt){
                                VentaExtraCrud.registrar(a);
                            }
                            response.sendRedirect("/aserradero/VentaExtraController?action=listar");
                            break;
                        case "paquete":
                            sesion_ajax = request.getSession(true);
                            ArrayList<VentaPaquete> VentaPaq = (ArrayList<VentaPaquete>) sesion_ajax.getAttribute("detalle_venta_paquete");
                            VentaPaqueteCRUD VentaPaqueteCrud;
                            VentaPaqueteCrud = new VentaPaqueteCRUD();
                            for(VentaPaquete a:VentaPaq){
                                VentaPaqueteCrud.registrar(a);
                            }
                            response.sendRedirect("/aserradero/VentaPaqueteController?action=listar");
                            break;
                        default:break;
                    }
                } catch (Exception ex) {
                    listarVentas(request, response,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Venta> ventas;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                ventaCRUD = new VentaCRUD();
                try {
                    ventas = (List<Venta>)ventaCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("ventas",ventas);
                    RequestDispatcher view = request.getRequestDispatcher("venta/ventas.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarVentas(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "cobrar":
                try {
                    cobrarVenta(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarVentas(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<Venta> ventas;
        VentaCRUD ventaCrud = new VentaCRUD();
        try {
            ventas = (List<Venta>)ventaCrud.listar("");
            //Enviamos las listas al jsp
            request.setAttribute("ventas",ventas);
            //enviamos mensaje al jsp
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("venta/ventas.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private Venta extraerVentaForm(HttpServletRequest request) {
        Venta venta = new Venta();
        venta.setFecha(Date.valueOf(request.getParameter("fecha")));
        venta.setId_venta(request.getParameter("id_venta"));
        venta.setId_cliente(request.getParameter("id_cliente"));
        venta.setId_empleado(request.getParameter("id_empleado"));
        venta.setEstatus(request.getParameter("estatus"));
        venta.setTipo_venta(request.getParameter("tipo_venta"));
        return venta;
    }

    private void buscarVentaPorId(HttpServletRequest request, HttpServletResponse response, String id_venta) {
        List<Venta> ventas;
        VentaCRUD ventaCrud = new VentaCRUD();
        try {
            ventas = (List<Venta>)ventaCrud.buscarPorId(id_venta);
            //Enviamos las listas al jsp
            request.setAttribute("ventas",ventas);
            RequestDispatcher view = request.getRequestDispatcher("venta/ventas.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cobrarVenta(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Venta venta = new Venta();
        venta.setId_venta(request.getParameter("id_venta"));
        venta.setId_cliente(request.getParameter("id_cliente"));
        venta.setTipo_venta(request.getParameter("tipo_venta"));
        venta.setTipo_pago(request.getParameter("tipo_pago"));
        String tipo_ticket = request.getParameter("tipo_ticket");
        venta.setEstatus("Pagado");
        VentaCRUD ventaCRUD = new VentaCRUD();
        switch (venta.getTipo_pago()) {
            case "Normal":
                try {
                    ventaCRUD.actualizar(venta);
                    mostrarTicket(request, response, venta, tipo_ticket);
                } catch (Exception ex) {
                    listarVentas(request, response, "error_cobro");
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case "Anticipado":
                
                ventaCRUD.actualizar(venta);
                mostrarTicket(request, response, venta, tipo_ticket);
//                switch (venta.getTipo_venta()) {//dependiendo del tipo de venta se resta inventario y se genera ticket
//                    case "Paquete":
//                        ventaCRUD.pagarVentaPaqueteAnticipado(venta);
//                        break;
//                    case "Mayoreo":
//                        System.out.println("Venta por mayoreo");
//                        ventaCRUD.pagarVentaMayoreoAnticipado(venta);
//                        System.out.println("Se ha pagado en controlador");
//                        break;
//                    default:
//                        if(ventaCRUD.pagarVentaExtra(venta)){
//                            venta.setEstatus("Pagado");
//                            ventaCRUD.actualizar(venta);
//                            restarInventarioMaderaProduccion(venta);
//                            mostrarTicket(request, response, venta);
//                        }else{
//                            listarVentas(request, response, "monto_inalcanzable_cliente");
//                        }
//                        break;
//                }
//                mostrarTicket(request, response, venta);
                    
//                } catch (Exception ex) {
//                    listarVentas(request, response, "error_cobro");
//                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
//                }   break;
                // Insertamos el monto de la venta en cuentas por cobrar
               // ventaCRUD.CuentaPorCobrar(venta); 
                break;
            default:
                break;
        }
    }
    private void mostrarTicket(HttpServletRequest request, HttpServletResponse response, Venta venta, String tipo_ticket) throws ServletException, IOException, Exception {
        VentaCRUD ventaCRUD = new VentaCRUD();
        DatosClienteTicket datosCliente;
        float monto;
        String monto_total;
        String pagina = "";
        switch (venta.getTipo_venta()) {
            case "Mayoreo":
                //Consultamos lista de maderas vendidas
                datosCliente = (DatosClienteTicket) ventaCRUD.consultaDatosCliente(venta);
                request.setAttribute("datosCliente",datosCliente);
                
                //Enviamos la lista de maderaVendida
                List<Madera> listaMaderaVendida = (List<Madera>)ventaCRUD.consultaMaderasVendidasMayoreo(venta);
                monto = ventaCRUD.consultarMontoTotalVentaMayoreo(venta);
                request.setAttribute("listaMaderaVendida",listaMaderaVendida);
                
                //Enviamos el monto total de la venta
                monto_total = String.valueOf(monto);
                request.setAttribute("monto_total",monto_total);
                request.setAttribute("tipo_ticket",tipo_ticket);
                pagina = "ticketVentaMayoreo";
                break;
            case "Paquete":
                datosCliente = (DatosClienteTicket) ventaCRUD.consultaDatosCliente(venta);
                List<Paquete> listaPaquetes = ventaCRUD.consultaPaquetesMaderaVendida(venta);
                monto = ventaCRUD.consultarMontoTotalVentaPaquete(venta);
                monto_total = String.valueOf(monto);
                //enviamos información al jsp
                request.setAttribute("listaPaquetes",listaPaquetes);
                request.setAttribute("datosCliente",datosCliente);
                request.setAttribute("monto_total",monto_total);
                request.setAttribute("tipo_ticket",tipo_ticket);
                pagina = "ticketVentaPaquete";
                break;
            case "Extra":
                datosCliente = (DatosClienteTicket) ventaCRUD.consultaDatosCliente(venta);
                List<DatosVentaExtra> listaDatosVentaExtra = (List<DatosVentaExtra>) ventaCRUD.consultaDatosVentaExtra(venta);
                monto = ventaCRUD.consultarMontoTotalVentaExtra(venta);
                monto_total = String.valueOf(monto);
                //enviamos información al jsp
                request.setAttribute("listaDatosVentaExtra",listaDatosVentaExtra);
                request.setAttribute("datosCliente",datosCliente);
                request.setAttribute("monto_total",monto_total);
                request.setAttribute("tipo_ticket",tipo_ticket);
                pagina = "ticketVentaExtra";
                break;
        }
        RequestDispatcher view = request.getRequestDispatcher("venta/"+pagina+".jsp");
        view.forward(request,response);
    }

    private void restarInventarioMaderaProduccion(Venta venta) {
        
        switch(venta.getTipo_venta()){
            case "Paquete":
                /*
                Codigo para restar madera en InventarioMadera: crear metodo en VentaCRUD
                */
                break;
            case "Mayoreo":
                break;
                /*
                Codigo para restar madera en InventarioMadera: crear metodo en VentaCRUD
                */
            default:// venta extra
                /*
                Codigo para restar madera en InventarioMadera: crear metodo en VentaCRUD
                */
                break;
        }
        
    }

}
