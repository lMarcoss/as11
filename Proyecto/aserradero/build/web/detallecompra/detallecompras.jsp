<%--
    Document   : DetalleCompras
    Created on : 26/09/2016, 09:14:39 PM
    Author     : rcortes
--%>

<%@page import="entidades.DetalleCompra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <DetalleCompra> detallecompras = (List<DetalleCompra>) request.getAttribute("detallecompras");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Detalle compra</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/DetalleCompraController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                                <option value="id_compra">Id compra</option>
                                <option value="clasificacion">Clasificación</option>
                                <option value="volumen">Volumen</option>
                                <option value="monto">Monto</option>
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
                        <th>Id compra</th>
                        <th>Clasificación</th>
                        <th>Volumen</th>
                        <th>Monto</th>
                    </tr>
                    <%
                        int i=0;
                        for (DetalleCompra detallecompra : detallecompras) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+detallecompra.getId_compra()+"</td>"
                                +"<td>"+detallecompra.getClasificacion()+"</td>"
                                +"<td>"+detallecompra.getVolumen()+"</td>"
                                +"<td>"+detallecompra.getMonto()+"</td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/DetalleCompraController?action=eliminar&id_compra="+detallecompra.getId_compra()+"&clasificacion="+detallecompra.getClasificacion()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar detalle" onClick=" window.location.href='/aserradero/DetalleCompraController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
