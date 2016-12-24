<%-- 
    Document   : produccionDetalles
    Created on : 28-sep-2016, 9:55:51
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.Venta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Venta> ventaPaquetes = (List<Venta>) request.getAttribute("ventaPaquetes");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Ventas por paquete</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/VentaPaqueteController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                                <option value="id_venta">Id venta</option>
                                <option value="fecha">Fecha</option>
                                <option value="cliente">Cliente</option>
                                <option value="empleado">Empleado</option>
                                <option value="estatus">Estatus</option>
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
                    <th>Fecha</th>
                    <th>Cliente</th>
                    <th>Empleado</th>
                    <th>Estatus</th>
                    <th>Tipo pago</th>
                    <th>Monto</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                    int i=0;
                    for (Venta venta : ventaPaquetes) {
                        out.print("<tr>"
                            +"<td>"+(i+1)+"</td>"
                            +"<td>"+venta.getId_venta()+"</td>"
                            +"<td>"+venta.getFecha()+"</td>"
                            +"<td>"+venta.getCliente()+"</td>"
                            +"<td>"+venta.getEmpleado()+"</td>"
                            +"<td>"+venta.getEstatus()+"</td>"
                            +"<td>"+venta.getTipo_pago()+"</td>"
                            +"<td>"+venta.getMonto()+"</td>"
                            +"<td><a href=\"/aserradero/VentaPaqueteController?action=detalle&id_venta="+venta.getId_venta()+"\">Detalle venta</a></td>"
                            + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/VentaPaqueteController?action=eliminar&id_venta="+venta.getId_venta()+"&tipo_venta="+venta.getTipo_venta()+"';};\">Eliminar</a></td>"
                        + "</tr>" );
                        i++;
                    }
                %>
            </table>
            <div>
                <input type="button" value="Agregar venta paquete" onClick=" window.location.href='/aserradero/VentaPaqueteController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
