<%-- 
    Document   : ventaMayoreos
    Created on : 27-sep-2016, 12:36:07
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.VentaMayoreo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <VentaMayoreo> ventaMayoreos = (List<VentaMayoreo>) request.getAttribute("ventaMayoreos");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Ventas por mayoreo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/VentaMayoreoController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_venta">Id venta</option>
                            <option value="id_madera">Madera</option>
                            <option value="num_piezas">Número piezas</option>
                            <option value="volumen">Volumen</option>
                            <option value="monto">Monto</option>
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
                    <th>Madera</th>
                    <th>Núm piezas</th>
                    <th>Volúmen</th>
                    <th>Monto</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                    int i=0;
                    for (VentaMayoreo ventaMayoreo : ventaMayoreos) {
                        out.print("<tr>"
                            +"<td>"+(i+1)+"</td>"
                            +"<td><a href=\"/aserradero/VentaController?action=buscar_venta&id_venta="+ventaMayoreo.getId_venta()+"\">"+ventaMayoreo.getId_venta()+"</a></td>"
                            +"<td><a href=\"/aserradero/MaderaClasificacionController?action=buscar_maderaClasificacion&id_madera="+ventaMayoreo.getId_madera()+"\">"+ventaMayoreo.getId_madera()+"</a></td>"
                            +"<td>"+ventaMayoreo.getNum_piezas()+"</td>"
                            +"<td>"+ventaMayoreo.getVolumen()+"</td>"
                            +"<td>"+ventaMayoreo.getMonto()+"</td>"
                            +"<td><a href=\"/aserradero/VentaMayoreoController?action=modificar&id_venta="+ventaMayoreo.getId_venta()+"&id_madera="+ventaMayoreo.getId_madera()+"\">Actualizar</a></td>"
                            + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/VentaMayoreoController?action=eliminar&id_venta="+ventaMayoreo.getId_venta()+"&id_madera="+ventaMayoreo.getId_madera()+"';};\">Eliminar</a></td>"
                        + "</tr>" );
                        i++;
                    }
                %>
            </table>
            <div>
                <input type="button" value="Agregar venta venta Mayoreo" onClick=" window.location.href='/aserradero/VentaMayoreoController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
