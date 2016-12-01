package controlador;

import dao.InventarioMaderaEntradaCRUD;
import entidades.InventarioMaderaEntrada;
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
 * @author rcortes
 */
public class InventarioMaderaEntradaController extends HttpServlet {

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
        switch(action){
            case "listar":
                try {
                 listarInventarioMaderaEntradas(request, response,"Inventario madera en rollo");   
                } catch (Exception e) {
                    System.out.println(e);
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
    private void listarInventarioMaderaEntradas(HttpServletRequest request, HttpServletResponse response,String mensaje){
        List<InventarioMaderaEntrada> inventarioMaderaEntradas;
        InventarioMaderaEntradaCRUD inventariomaderaentradacrud = new InventarioMaderaEntradaCRUD();
        String forward;
        try {
            inventarioMaderaEntradas = (List<InventarioMaderaEntrada>)inventariomaderaentradacrud.listar();
            request.setAttribute("inventarioMaderaEntradas", inventarioMaderaEntradas);
            request.setAttribute("mensaje", mensaje);
            forward="inventarioMaderaEntrada/inventariomaderaentradas.jsp";
            RequestDispatcher view=request.getRequestDispatcher(forward);
            view.forward(request, response);
        }catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(InventarioMaderaEntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
