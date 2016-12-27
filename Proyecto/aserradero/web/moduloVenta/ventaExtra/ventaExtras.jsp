<%-- 
    Document   : ventaExtras
    Created on : 21-sep-2016, 23:43:15
    Author     : lmarcoss
--%>

<%@page import="entidades.venta.Venta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Venta> ventaExtras = (List<Venta>) request.getAttribute("ventaExtras");
    String mensaje = (String) request.getAttribute("mensaje");
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
                            <option value="fecha">Fecha</option>
                            <option value="cliente">Cliente</option>
                            <option value="estatus">Estatus</option>
                            <option value="tipo">Tipo</option>
                            <option value="monto">Monto</option>
                            <option value="observacion">Observacion</option>
                            <option value="empleado">Registró</option>
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
                <th>Fecha</th>
                <th>Cliente</th>
                <th>Estatus</th>
                <th>Tipo pago</th>
                <th>Monto</th>
                <th>Registró</th>
                <th></th>
                <th></th>
            </tr>
            <%
                int i = 0;
                for (Venta venta : ventaExtras) {
                    out.print("<tr>"
                            + "<td>" + (i + 1) + "</td>"
                            + "<td>" + venta.getFecha() + "</td>"
                            + "<td>" + venta.getCliente() + "</td>"
                            + "<td>" + venta.getEstatus() + "</td>"
                            + "<td>" + venta.getTipo_pago() + "</td>"
                            + "<td>" + venta.getMonto() + "</td>"
                            + "<td>" + venta.getEmpleado() + "</td>"
                            + "<td><a href=\"/aserradero/VentaExtraController?action=detalle&id_venta=" + venta.getId_venta() + "\">Detalle venta</a></td>"
//                            + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/VentaExtraController?action=eliminar&id_venta=" + venta.getId_venta() + "&tipo_venta=" + venta.getTipo_venta() + "';};\">Eliminar</a></td>"
                            + "</tr>");
                    i++;
                }
            %>
        </table>
        <div>
            <input type="button" value="Agregar venta extra" onClick=" window.location.href = '/aserradero/VentaExtraController?action=nuevo'">
        </div>
    </div><!-- Resultado Consulta-->
</body>
</html>
