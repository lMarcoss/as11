<%--
    Document   : maderaentradas
    Created on : 26/09/2016, 11:48:27 PM
    Author     : rcortes
--%>

<%@page import="entidades.InventarioMaderaRollo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <InventarioMaderaRollo> listaInventario = (List<InventarioMaderaRollo>) request.getAttribute("listaInventario");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Maderas en rollo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
            
        <% if(!listaInventario.isEmpty()){%>
        <!-- ************************* Resultado Consulta-->
        <h1>Inventario madera en rollo</h1>
        <div>
            <table class="table-condensed">
                    <tr>
                        <th>N°</th>
                        <th>Número de piezas</th>                        
                        <th>Volumen total</th>
                    </tr>
                    <%
                        int i=0;
                        for (InventarioMaderaRollo inventario : listaInventario) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+inventario.getNum_piezas()+"</td>"
                                +"<td>"+inventario.getVolumen_total()+"</td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>            
        </div><!-- Resultado Consulta-->
        <%}else{
            out.print("<br><br><br><h2>No hay inventario</h2>");
        }%>
    </body>
</html>
