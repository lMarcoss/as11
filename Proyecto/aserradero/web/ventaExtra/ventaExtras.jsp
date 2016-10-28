<%-- 
    Document   : ventaExtras
    Created on : 21-sep-2016, 23:43:15
    Author     : lmarcoss
--%>

<%@page import="entidades.Vehiculo"%>
<%@page import="entidades.VentaExtra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <VentaExtra> ventaExtras = (List<VentaExtra>) request.getAttribute("ventaExtras");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Ventas </title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/VentaExtraController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_venta">Id venta</option>
                            <option value="tipo">Tipo</option>
                            <option value="monto">Monto</option>
                            <option value="observacion">Observacion</option>
                        </select>
                        </td>
                        <td><input type="search" name="dato" placeholder="Escriba su búsqueda"></td>
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
                    <th>Id venta</th>
                    <th>Tipo</th>
                    <th>Monto</th>
                    <th>Observacion</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                    int i=0;
                    for (VentaExtra ventaExtra : ventaExtras) {
                        out.print("<tr>"
                            +"<td>"+(i+1)+"</td>"
                            +"<td><a href=\"/aserradero/VentaController?action=buscar_venta&id_venta="+ventaExtra.getId_venta()+"\">"+ventaExtra.getId_venta()+"</a></td>"
                            +"<td>"+ventaExtra.getTipo()+"</td>"
                            +"<td>"+ventaExtra.getMonto()+"</td>"
                            +"<td>"+ventaExtra.getObservacion()+"</td>"
                            +"<td><a href=\"/aserradero/VentaExtraController?action=modificar&id_venta="+ventaExtra.getId_venta()+"&tipo="+ventaExtra.getTipo()+"\">Actualizar</a></td>"
                            + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/VentaExtraController?action=eliminar&id_venta="+ventaExtra.getId_venta()+"&tipo="+ventaExtra.getTipo()+"';};\">Eliminar</a></td>"
                        + "</tr>" );
                        i++;
                    }
                %>
            </table>
            <div>
                <input type="button" value="Agregar venta extra" onClick=" window.location.href='/aserradero/VentaExtraController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
