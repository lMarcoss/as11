<%--
    Document   : costomaderacompras
    Created on : 26/09/2016, 11:14:22 PM
    Author     : rcortes
--%>

<%@page import="entidades.ClasificacionMaderaRollo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    List <ClasificacionMaderaRollo> clasificacionMaderaEntradas = (List<ClasificacionMaderaRollo>) request.getAttribute("clasificacionMaderaEntradas");
    String mensaje = (String)request.getAttribute("mensaje"); 
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        <input hidden="" name="mensaje" id="mensaje" value="<%=mensaje%>">
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/ClasificacionMaderaRolloController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                                <option value="clasificacion">Clasificación</option>
                                <option value="costo">costo</option>
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
                        <th>Clasificación</th>
                        <th>Costo</th>
                    </tr>
                    <%
                        int i=0;
                        for (ClasificacionMaderaRollo clasificacion : clasificacionMaderaEntradas) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+clasificacion.getClasificacion()+"</td>"
                                +"<td>"+clasificacion.getCosto()+"</td>"
                                +"<td><a href=\"/aserradero/ClasificacionMaderaRolloController?action=modificar&clasificacion="+clasificacion.getClasificacion()+"\">Actualizar</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/ClasificacionMaderaRolloController?action=eliminar&clasificacion="+clasificacion.getClasificacion()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar" onClick=" window.location.href='/aserradero/clasificacionMaderaEntrada/nuevoClasificacionMaderaEntrada.jsp' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
