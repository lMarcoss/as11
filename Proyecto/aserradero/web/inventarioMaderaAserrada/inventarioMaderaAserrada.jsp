<%-- 
    Document   : inventarioMaderaAserrada
    Created on : 28/09/2016, 04:11:42 PM
    Author     : Marcos
--%>

<%@page import="entidades.InventarioMaderaAserrada"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <InventarioMaderaAserrada> inventarioMaderaAserrada = (List<InventarioMaderaAserrada>) request.getAttribute("inventarioMaderaAserrada");
    String mensaje=(String) request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Inventario</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/InventarioMaderaAserradaController?action=buscar">
                <table class="table-condensed">
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_madera">Id madera</option>
                            <option value="num_piezas">Num piezas</option>
                            <option value="volumen_total">Volumen total</option>
                            <option value="costo_total">Costo total</option>
                        </select>
                        </td>
                        <td><input type="text" name="dato" placeholder="Escriba su búsqueda"></td>
                        <td colspan="2"><input type="submit" value="Buscar"></td>
                    </tr>
                </table>
            </form>
        </div> <!-- Fin opción de búsqueda-->
        
        <!-- ************************* Resultado Consulta-->
        <div>
            <table class="table-condensed">
                    <tr>
                        <th>N°</th>
                        <th>Id madera</th>
                        <th>Num. piezas</th>
                        <th>Volumen unitario</th>
                        <th>Volumen total</th>
                        <th>Costo por volumen</th>
                        <th>Costo total</th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    <%
                       
                        int i=0;
                        for (InventarioMaderaAserrada inventario : inventarioMaderaAserrada) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/MaderaClasificacionController?action=buscar_maderaClasificacion&id_madera="+inventario.getId_madera()+"\">"+inventario.getId_madera()+"</a></td>"
                                +"<td>"+inventario.getNum_piezas()+"</td>"
                                +"<td>"+inventario.getVolumen_unitario()+"</td>"
                                +"<td>"+inventario.getVolumen_total()+"</td>"
                                +"<td>"+inventario.getCosto_por_volumen()+"</td>"
                                +"<td>"+inventario.getCosto_total()+"</td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
