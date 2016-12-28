<%--
    Document   : maderaentradas
    Created on : 26/09/2016, 11:48:27 PM
    Author     : rcortes
--%>

<%@page import="java.math.MathContext"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="entidades.maderaRollo.InventarioMaderaRollo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<InventarioMaderaRollo> listaInventario = (List<InventarioMaderaRollo>) request.getAttribute("listaInventario");
    String mensaje = (String) request.getAttribute("mensaje");
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

        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>">

        <% if (!listaInventario.isEmpty()) {%>
        <!-- ************************* Resultado Consulta-->
        <h1>Inventario madera en rollo</h1>
        <div>
            <table class="table-condensed">
                <tr>
                    <th>Clasificación</th>
                    <th>piezas</th>
                    <th>Volúmen</th>
                    <th>Costo por volumen</th>
                    <th>Costo total</th>
                    <!--                    <th>piezas secundario</th>
                                        <th>Vol. secundario</th>
                                        <th>Costo secundario</th>
                                        <th>piezas terciario</th>
                                        <th>Vol. terciario</th>
                                        <th>Costo terciario</th>
                                        <th>Total piezas</th>
                                        <th>Vol. total</th>
                                        <th>Costo total</th>-->
                </tr>
                <%
                    for (InventarioMaderaRollo inventario : listaInventario) {
                        out.print("<tr>"
                                + "<td>Primario</td>"
                                + "<td>" + inventario.getNum_pieza_primario() + "</td>"
                                + "<td>" + inventario.getVolumen_primario() + "</td>"
                                + "<td>" + inventario.getCosto_primario() + "</td>"
                                + "<td>" + inventario.getCosto_total_primario() + "</td>"
                                + "</tr><tr>"
                                + "<td>Secundario</td>"
                                + "<td>" + inventario.getNum_pieza_secundario() + "</td>"
                                + "<td>" + inventario.getVolumen_secundario() + "</td>"
                                + "<td>" + inventario.getCosto_secundario() + "</td>"
                                + "<td>" + inventario.getCosto_total_secundario() + "</td>"
                                + "</tr><tr>"
                                + "<td>Terciario</td>"
                                + "<td>" + inventario.getNum_pieza_terciario() + "</td>"
                                + "<td>" + inventario.getVolumen_terciario() + "</td>"
                                + "<td>" + inventario.getCosto_terciario() + "</td>"
                                + "<td>" + inventario.getCosto_total_terciario() + "</td>"
                                + "</tr><tr>"
                                + "<td>Total</td>"
                                + "<td>" + inventario.getNum_pieza_total() + "</td>"
                                + "<td>" + inventario.getVolumen_total() + "</td>"
                                + "<td></td>"
                                + "<td>" + inventario.getCosto_total() + "</td>"
                                + "</tr>");
                    }
                %>
            </table>            
        </div><!-- Resultado Consulta-->
        <%} else {%>
        <h3 style="color: red;">No hay inventario</h3>
        <%}%>
    </body>
</html>
