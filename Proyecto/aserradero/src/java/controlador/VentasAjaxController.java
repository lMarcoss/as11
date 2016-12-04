package controlador;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entidades.VentaExtra;
import entidades.VentaMayoreo;
import entidades.VentaPaquete;
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
        response.getWriter().write("hola");
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
        String id_venta=request.getParameter("id_venta");
        String accion=request.getParameter("accion");
        JsonArray jArray = new JsonArray();
        JsonObject jsonreturn =new JsonObject();
        PrintWriter out = response.getWriter();
        HttpSession sesion_ajax = request.getSession(true);//Instanciamos la sesión
        System.out.println(accion);
        switch(accion){
            case "add_venta_extra":
                String tipo=request.getParameter("tipo");
                float Monto_Ex=Float.valueOf(request.getParameter("monto"));
                String observacion=request.getParameter("observacion");
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
                    jsonreturn.addProperty("success", "true");
                    out.print(jsonreturn.toString());
                    sesion_ajax.setAttribute("detalle_venta_extra", VentaExt);
                } catch (Exception e) {
                    jsonreturn.addProperty("success", "false");
                    out.print(jsonreturn.toString());
                    System.out.println(e);
                }
            break;
            case "del_venta_extra":
                tipo=request.getParameter("tipo");
                boolean banderaex=false;
                ArrayList<VentaExtra> VentaExt = sesion_ajax.getAttribute("detalle_venta_extra") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle_venta_extra");
                if(VentaExt!=null){
                    for(VentaExtra a:VentaExt){
                        if(a.getTipo().equals(tipo)){
                            VentaExt.remove(a);
                            banderaex=true;
                            break;
                        }
                    }
                }
                if(banderaex){
                 jsonreturn.addProperty("success", "true");   
                }else{
                 jsonreturn.addProperty("success", "false");   
                }                
                out.print(jsonreturn.toString());
                sesion_ajax.setAttribute("detalle_venta_extra", VentaExt);
            break;
            case "add_venta_mayoreo":
                String Madera=request.getParameter("id_madera");
                float volumen=Float.valueOf(request.getParameter("volumen"));
                Integer num_piezas=Integer.valueOf(request.getParameter("num_piezas"));
                float Monto=Float.valueOf(request.getParameter("monto"));
                try {
                    ArrayList<VentaMayoreo>  VentaMay = sesion_ajax.getAttribute("detalle_venta_mayoreo") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle_venta_mayoreo");
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
                    jsonreturn.addProperty("success", "true");
                    out.print(jsonreturn.toString());
                    sesion_ajax.setAttribute("detalle_venta_mayoreo", VentaMay);
                } catch (Exception e) {
                    jsonreturn.addProperty("success", "false");
                    out.print(jsonreturn.toString());
                    System.out.println(e);
                }
            break;
            case "del_venta_mayoreo":
                Madera=request.getParameter("id_madera");
                boolean borradoM=false;
                ArrayList<VentaMayoreo> VentaMay = sesion_ajax.getAttribute("detalle_venta_mayoreo") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle_venta_mayoreo");
                if(VentaMay!=null){
                    for(VentaMayoreo a:VentaMay){
                        if(a.getId_madera().equals(Madera)){
                            VentaMay.remove(a);
                            borradoM=true;
                            break;
                        }
                    }
                }
                sesion_ajax.setAttribute("detalle_venta_mayoreo", VentaMay);
                if(borradoM){
                    jsonreturn.addProperty("success", "true");
                }else{
                    jsonreturn.addProperty("success", "false");
                }
                out.print(jsonreturn.toString());                
            break;
            case "add_venta_paquete":                
                Madera=request.getParameter("id_madera");
                volumen=Float.valueOf(request.getParameter("volumen"));
                num_piezas=Integer.valueOf(request.getParameter("num_piezas"));
                Monto=Float.valueOf(request.getParameter("monto"));
                Integer numero_paquete=Integer.valueOf(request.getParameter("numero_paquete"));
                try {
                    ArrayList<VentaPaquete>  VentaPaq = sesion_ajax.getAttribute("detalle_venta_paquete") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle_venta_paquete");
                    boolean bandera=false;
                    if(VentaPaq.size()>0){
                        for(VentaPaquete a:VentaPaq){
                            if(a.getId_madera().equals(Madera) && (a.getNumero_paquete()==numero_paquete)){
                                a.setNum_piezas(a.getNum_piezas()+num_piezas);
                                a.setMonto(a.getMonto()+Monto);
                                a.setVolumen(a.getVolumen()+volumen);
                                bandera=true;
                                break;
                            }
                        }
                    }
                    if(!bandera){
                     VentaPaq.add(new VentaPaquete(id_venta,numero_paquete,Madera,num_piezas,volumen,Monto));
                    }                    
                    sesion_ajax.setAttribute("detalle_venta_paquete", VentaPaq);                                                            
                    jsonreturn.addProperty("success", "true");                
                    out.print(jsonreturn.toString());
                } catch (Exception e) {
                    jsonreturn.addProperty("success", "false");                
                    out.print(jsonreturn.toString());
                    System.out.println(e);
                }
            break;
            case "del_venta_paquete":
                Madera=request.getParameter("id_madera");
                boolean borrado=false;
                numero_paquete=Integer.valueOf(request.getParameter("numero_paquete"));
                System.out.println(Madera);
                System.out.println(numero_paquete);                
                ArrayList<VentaPaquete> VentaPaq = sesion_ajax.getAttribute("detalle_venta_paquete") == null ? new ArrayList<>() : (ArrayList) sesion_ajax.getAttribute("detalle_venta_paquete");
                if(VentaPaq!=null){
                    for(VentaPaquete a:VentaPaq){
                        if((a.getId_madera().equals(Madera))&&(a.getNumero_paquete()==numero_paquete)){
                            VentaPaq.remove(a);
                            borrado=true;
                            System.out.println("removido.");
                            break;
                        }
                    }
                }
                sesion_ajax.setAttribute("detalle_venta_paquete", VentaPaq);
                if(borrado){
                    jsonreturn.addProperty("success", "true");
                }else{
                    jsonreturn.addProperty("success", "false");
                }               
                out.print(jsonreturn.toString());
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
