package controlador;


import dao.AdministradorCRUD;
import dao.ClienteCRUD;
import dao.PersonaCRUD;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Persona;
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
public class ClienteController extends HttpServlet {

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
        Cliente clienteEC; //Enviar al CRUD
        Cliente cliente; //Respuesta del CRUD
        ClienteCRUD clienteCRUD;
        PersonaCRUD personaCRUD;
        switch(action){
            case "nuevo":
                personaCRUD = new PersonaCRUD();
                try {
                    //consultamos la lista de personas para registrarlos como cliente
                    List<Persona> personas;
                    personas = personaCRUD.listar();
                    request.setAttribute("personas",personas);
                    
                    //Consultamos la lista de administradores para asignarlos como jefe
                    AdministradorCRUD administradorCRUD = new AdministradorCRUD();
                    List<Administrador> administradores;
                    administradores = (List<Administrador>) administradorCRUD.listar();
                    request.setAttribute("administradores", administradores);
                    
                    RequestDispatcher view = request.getRequestDispatcher("cliente/nuevoCliente.jsp");
                    view.forward(request,response);
                } catch (ServletException | IOException ex) {
                    System.out.println(ex);
                    listarClientes(request, response,"error_nuevo");
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    listarClientes(request, response,"error_nuevo");
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "listar":
                listarClientes(request, response,"");
                break;
            case "modificar":
                clienteEC = extraerClavesCliente(request,response);
                clienteCRUD = new ClienteCRUD();
                try {
                    cliente = (Cliente) clienteCRUD.modificar(clienteEC);
                    request.setAttribute("cliente",cliente);
                    RequestDispatcher view = request.getRequestDispatcher("cliente/actualizarCliente.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarClientes(request, response,"error_modificar");
                    System.out.println(ex);
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "eliminar":
                clienteEC = extraerClavesCliente(request, response);
                clienteCRUD = new ClienteCRUD();
                try {
                    clienteCRUD.eliminar(clienteEC);
                    listarClientes(request, response,"eliminado");
                } catch (Exception ex) {
                    listarClientes(request, response,"error_eliminar");
                    System.out.println(ex);
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
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
        request.setCharacterEncoding("UTF-8");// Forzar a usar codificación UTF-8 iso-8859-1
        String action = request.getParameter("action");
        Cliente cliente;
        ClienteCRUD clienteCRUD;
        switch(action){
            case "nuevo":
                cliente = extraerClienteForm(request);
                clienteCRUD = new ClienteCRUD();
                try {
                    clienteCRUD.registrar(cliente);
                    listarClientes(request, response,"registrado");
                } catch (Exception ex) {
                    listarClientes(request, response,"error_registrar");
                    System.out.println(ex);
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "actualizar":
                cliente = extraerClienteForm(request);
                clienteCRUD = new ClienteCRUD();
                try {
                    clienteCRUD.actualizar(cliente);
                    listarClientes(request, response,"actualizado");
                } catch (Exception ex) {
                    listarClientes(request, response,"error_actualizar");
                    System.out.println(ex);
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "buscar":
                List <Cliente> clientes;
                String nombre_campo = request.getParameter("nombre_campo");
                String dato = request.getParameter("dato");
                clienteCRUD = new ClienteCRUD();
                try {
                    clientes = (List<Cliente>)clienteCRUD.buscar(nombre_campo, dato);
                    request.setAttribute("clientes",clientes);
                    RequestDispatcher view = request.getRequestDispatcher("cliente/clientes.jsp");
                    view.forward(request,response);
                } catch (Exception ex) {
                    listarClientes(request, response,"error_buscar_campo");
                    System.out.println(ex);
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void listarClientes(HttpServletRequest request, HttpServletResponse response,String mensaje) {
        List<Cliente> clientes;
        ClienteCRUD clienteCRUD = new ClienteCRUD();
        try {
            clientes = (List<Cliente>)clienteCRUD.listar();
            //Enviamos las listas al jsp
            request.setAttribute("clientes",clientes);
            request.setAttribute("mensaje",mensaje);
            RequestDispatcher view = request.getRequestDispatcher("cliente/clientes.jsp");
            view.forward(request,response);
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Extraer datos del formulario
    private Cliente extraerClienteForm(HttpServletRequest request) {
        Cliente cliente = new Cliente();
        cliente.setId_persona(request.getParameter("id_persona"));
        cliente.setId_jefe(request.getParameter("id_jefe"));
        return cliente;
    }

    private Cliente extraerClavesCliente(HttpServletRequest request, HttpServletResponse response) {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(request.getParameter("id_cliente"));
        cliente.setId_jefe(request.getParameter("id_jefe"));
        return cliente;
    }

}
