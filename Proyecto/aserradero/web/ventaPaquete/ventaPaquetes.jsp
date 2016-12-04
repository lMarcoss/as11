<%-- 
    Document   : produccionDetalles
    Created on : 28-sep-2016, 9:55:51
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.VentaPaquete"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <VentaPaquete> ventaPaquetes = (List<VentaPaquete>) request.getAttribute("ventaPaquetes");
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
                            <option value="numero_paquete">Paquete</option>
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
                    <th>Paquete</th>
                    <th>Madera</th>
                    <th>Núm piezas</th>
                    <th>Volúmen</th>
                    <th>Monto</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                    int i=0;
                    for (VentaPaquete ventaPaquete : ventaPaquetes) {
                        out.print("<tr>"
                            +"<td>"+(i+1)+"</td>"
                            +"<td><a href=\"/aserradero/VentaController?action=buscar_venta&id_venta="+ventaPaquete.getId_venta()+"\">"+ventaPaquete.getId_venta()+"</a></td>"
                            +"<td>"+ventaPaquete.getNumero_paquete()+"</td>"    
                            +"<td><a href=\"/aserradero/MaderaClasificacionController?action=buscar_maderaClasificacion&id_madera="+ventaPaquete.getId_madera()+"\">"+ventaPaquete.getId_madera()+"</a></td>"
                            +"<td>"+ventaPaquete.getNum_piezas()+"</td>"
                            +"<td>"+ventaPaquete.getVolumen()+"</td>"
                            +"<td>"+ventaPaquete.getMonto()+"</td>"
                            +"<td><a href=\"/aserradero/VentaPaqueteController?action=modificar&id_venta="+ventaPaquete.getId_venta()+"&numero_paquete="+ventaPaquete.getNumero_paquete()+"&id_madera="+ventaPaquete.getId_madera()+"\">Actualizar</a></td>"
                            +"<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/VentaPaqueteController?action=eliminar&id_venta="+ventaPaquete.getId_venta()+"&numero_paquete="+ventaPaquete.getNumero_paquete()+"&id_madera="+ventaPaquete.getId_madera()+"';};\">Eliminar</a></td>"
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
