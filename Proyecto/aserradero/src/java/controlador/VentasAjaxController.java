/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entidades.VentaExtra;
import entidades.VentaMayoreo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author rcortes
 */
public class VentasAjaxController extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VentasAjaxController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VentasAjaxController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        processRequest(request, response);
        request.setCharacterEncoding("UTF-8");
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
        response.setContentType("text/xml");
        response.setContentType("application/json");
        processRequest(request, response);
        request.setCharacterEncoding("UTF-8");
        VentaMayoreo VM= new VentaMayoreo();
        Integer id_venta=Integer.valueOf(request.getParameter("id_venta"));
        String accion=request.getParameter("accion");
        JsonObject jsonreturn =new JsonObject();
        PrintWriter out = response.getWriter();             
        HttpSession sesion_ajax = request.getSession(true);//Instanciamos la sesión
        System.out.println(accion);
        switch(accion){
            case "add_venta_extra":
                String tipo=request.getParameter("tipo");
                float Monto_Ex=Float.valueOf(request.getParameter("monto"));
                String observacion=request.getParameter("observacion");
                System.out.println(tipo);
                System.out.println(Monto_Ex);
                System.out.println(observacion);
                try {
                    ArrayList<VentaExtra> VentaExt = sesion_ajax.getAttribute("detalle_venta_extra") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle_venta_extra");
                    boolean bandera=false;
                    if(VentaExt.size()>0){
                        for(VentaExtra a:VentaExt){
                            if(a.getTipo().equals(tipo)){
                                a.setMonto(a.getMonto()+Monto_Ex);                                
                                bandera=true;
                                break;
                            }
                        }
                    }
                    if(!bandera){
                     VentaExt.add(new VentaExtra(id_venta,tipo,Monto_Ex,observacion));   
                    }
                    jsonreturn.addProperty("success", true);
                    response.getWriter().print("{success: true}"); 
                    jsonreturn.addProperty("msj", "errors");
                    out.print(jsonreturn.toString());
                    out.close();
                    sesion_ajax.setAttribute("detalle_venta_extra", VentaExt);
                } catch (Exception e) {
                    jsonreturn.addProperty("success", false);                    
                    System.out.println(e);
                }
            break;
            case "del_venta_extra":
                tipo=request.getParameter("tipo");
                ArrayList<VentaExtra> VentaExt = sesion_ajax.getAttribute("detalle_venta_extra") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle_venta_extra");
                if(VentaExt!=null){
                    for(VentaExtra a:VentaExt){
                        if(a.getTipo().equals(tipo)){
                            VentaExt.remove(a);
                            break;
                        }
                    }
                }
                out.print(jsonreturn.toString());
                out.close();
                sesion_ajax.setAttribute("detalle_venta_extra", VentaExt);
            break;    
            case "add_venta_mayoreo":
                String Madera=request.getParameter("id_madera");
                float volumen=Float.valueOf(request.getParameter("volumen"));
                Integer num_piezas=Integer.valueOf(request.getParameter("num_piezas"));
                float Monto=Float.valueOf(request.getParameter("monto"));
                try {
                    ArrayList<VentaMayoreo>  VentaMay = sesion_ajax.getAttribute("detalle") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle");
                    boolean bandera=false;
                    if(VentaMay.size()>0){
                        for(VentaMayoreo a:VentaMay){
                            if(a.getId_madera().equals(Madera)){
                                a.setNum_piezas(a.getNum_piezas()+num_piezas);
                                a.setMonto(a.getMonto()+Monto);
                                a.setVolumen(a.getVolumen()+volumen);
                                bandera=true;
                                break;
                            }
                        }
                    }
                    if(!bandera){
                     VentaMay.add(new VentaMayoreo(id_venta,Madera,num_piezas,volumen,Monto));   
                    }
                    jsonreturn.addProperty("success", true);
                    jsonreturn.addProperty("msj", "errors");
                    out.print(jsonreturn.toString());
                    out.close();
                    sesion_ajax.setAttribute("detalle", VentaMay);
                } catch (Exception e) {
                    jsonreturn.addProperty("success", false);
                    System.out.println(e);
                }
            break;    
            case "del_venta_mayoreo":
                Madera=request.getParameter("id_madera");
                ArrayList<VentaMayoreo> VentaMay = sesion_ajax.getAttribute("detalle") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle");
                if(VentaMay!=null){
                    for(VentaMayoreo a:VentaMay){
                        if(a.getId_madera().equals(Madera)){
                            VentaMay.remove(a);
                            break;
                        }
                    }
                }
                out.print(jsonreturn.toString());
                out.close();
                sesion_ajax.setAttribute("detalle", VentaMay);
            break;
            default:break;
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

}
