package controlador.registros;

import controlador.PagoRentaController;
import dao.empleado.EmpleadoCRUD;
import dao.registros.VehiculoCRUD;
import entidades.empleado.Empleado;
import entidades.registros.Vehiculo;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
 * @author rcortes
 */
public class VehiculoController extends HttpServlet {

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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            //Llegan url
            String action = request.getParameter("action");
            Vehiculo vehiculoEC; //Enviar al CRUD
            Vehiculo vehiculo; //Respuesta del CRUD
            VehiculoCRUD vehiculocrud;
            EmpleadoCRUD empleadoCRUD;
            switch (action) {
                case "nuevo":
                    try {
                        empleadoCRUD = new EmpleadoCRUD();
                        List<Empleado> empleados;
                        empleados = (List<Empleado>) empleadoCRUD.listar("");
                        request.setAttribute("empleados", empleados);
                        RequestDispatcher view = request.getRequestDispatcher("vehiculo/nuevoVehiculo.jsp");
                        view.forward(request, response);
                    } catch (Exception ex) {
                        listarVehiculos(request, response, "error_nuevo");
                        System.out.println(ex);
                        Logger.getLogger(PagoRentaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "listar":
                    listarVehiculos(request, response, "lista de vehículos de la empresa");
                    break;
                case "modificar":
                    vehiculoEC = new Vehiculo();
                    vehiculoEC.setId_vehiculo(request.getParameter("id_vehiculo"));
                    vehiculocrud = new VehiculoCRUD();
                    try {
                        empleadoCRUD = new EmpleadoCRUD();
                        List<Empleado> empleados;
                        empleados = (List<Empleado>) empleadoCRUD.listar("");
                        request.setAttribute("empleados", empleados);
                        vehiculo = (Vehiculo) vehiculocrud.modificar(vehiculoEC);
                        request.setAttribute("vehiculo", vehiculo);
                        RequestDispatcher view = request.getRequestDispatcher("vehiculo/actualizarVehiculo.jsp");
                        view.forward(request, response);
                    } catch (Exception ex) {
                        listarVehiculos(request, response, "error_modificar");
                        Logger.getLogger(VehiculoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "eliminar":
                    vehiculoEC = new Vehiculo();
                    vehiculoEC.setId_vehiculo(request.getParameter("id_vehiculo"));
                    vehiculocrud = new VehiculoCRUD();
                    try {
                        vehiculocrud.eliminar(vehiculoEC);
                        listarVehiculos(request, response, "eliminado");
                    } catch (Exception ex) {
                        listarVehiculos(request, response, "error_eliminar");
                        Logger.getLogger(VehiculoController.class.getName()).log(Level.SEVERE, null, ex);
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //Llegan formularios de tipo post
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            Vehiculo vehiculo;
            VehiculoCRUD vehiculocrud;
            switch (action) {
                case "nuevo":
                    vehiculo = extraerVehiculoForm(request);
                    vehiculocrud = new VehiculoCRUD();
                    try {
                        vehiculocrud.registrar(vehiculo);
                        listarVehiculos(request, response, "registrado");
                    } catch (Exception ex) {
                        listarVehiculos(request, response, "error_registrar");
                        Logger.getLogger(VehiculoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "actualizar":
                    vehiculo = extraerVehiculoForm(request);
                    vehiculocrud = new VehiculoCRUD();
                    try {
                        vehiculocrud.actualizar(vehiculo);
                        listarVehiculos(request, response, "actualizado");
                    } catch (Exception ex) {
                        listarVehiculos(request, response, "error_actualizar");
                        Logger.getLogger(VehiculoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "buscar":
                    List<Vehiculo> vehiculos;
                    String nombre_campo = request.getParameter("nombre_campo");
                    String dato = request.getParameter("dato");
                    vehiculocrud = new VehiculoCRUD();
                    try {
                        vehiculos = (List<Vehiculo>) vehiculocrud.buscar(nombre_campo, dato);
                        request.setAttribute("vehiculos", vehiculos);
                        RequestDispatcher view = request.getRequestDispatcher("vehiculo/vehiculos.jsp");
                        view.forward(request, response);
                    } catch (Exception ex) {
                        listarVehiculos(request, response, "error_buscar_campo");
                        Logger.getLogger(VehiculoController.class.getName()).log(Level.SEVERE, null, ex);
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
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>
        //Mostrar lista de vehiculos
    

    private void listarVehiculos(HttpServletRequest request, HttpServletResponse response, String mensaje) {
        List<Vehiculo> vehiculos;
        VehiculoCRUD vehiculocrud = new VehiculoCRUD();
        try {
            vehiculos = (List<Vehiculo>) vehiculocrud.listar("");
            //Enviamos las listas al jsp
            request.setAttribute("vehiculos", vehiculos);
            //enviamos mensaje
            request.setAttribute("mensaje", mensaje);
            RequestDispatcher view = request.getRequestDispatcher("vehiculo/vehiculos.jsp");
            view.forward(request, response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(VehiculoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Extraer datos del formulario
    private Vehiculo extraerVehiculoForm(HttpServletRequest request) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId_vehiculo(request.getParameter("id_vehiculo"));
        vehiculo.setMatricula(request.getParameter("matricula"));
        vehiculo.setTipo(request.getParameter("tipo"));
        vehiculo.setColor(request.getParameter("color"));
        vehiculo.setCarga_admitida(request.getParameter("carga_admitida"));
        vehiculo.setMotor(request.getParameter("motor"));
        vehiculo.setModelo(request.getParameter("modelo"));
        vehiculo.setCosto(BigDecimal.valueOf((Double.valueOf(request.getParameter("costo")))));
        vehiculo.setId_empleado(request.getParameter("id_empleado"));
        return vehiculo;
    }
}
