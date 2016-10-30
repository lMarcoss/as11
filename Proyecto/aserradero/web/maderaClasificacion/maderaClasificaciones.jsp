<%-- 
    Document   : maderaClasificaciones
    Created on : 27-sep-2016, 2:44:58
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.MaderaClasificacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <MaderaClasificacion> maderaClasificaciones = (List<MaderaClasificacion>) request.getAttribute("maderaClasificaciones");
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
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/MaderaClasificacionController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_madera">madera</option>
                            <option value="greso">Grueso</option>
                            <option value="ancho">Ancho</option>
                            <option value="largo">Largo</option>
                            <option value="volumen">Volumen</option>
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
                        <th>Madera</th>
                        <th>Grueso</th>
                        <th>Ancho</th>
                        <th>Largo</th>
                        <th>Volumen</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (MaderaClasificacion maderaClasificacion : maderaClasificaciones) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/CostoMaderaController?action=buscar_madera&id_madera="+maderaClasificacion.getId_madera()+"\">"+maderaClasificacion.getId_madera()+"</a></td>"
                                +"<td>"+maderaClasificacion.getGrueso()+"</td>"
                                +"<td>"+maderaClasificacion.getAncho()+"</td>"
                                +"<td>"+maderaClasificacion.getLargo()+"</td>"
                                +"<td>"+maderaClasificacion.getVolumen()+"</td>"
                                +"<td><a href=\"/aserradero/MaderaClasificacionController?action=modificar&id_madera="+maderaClasificacion.getId_madera()+"\">Actualizar</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/MaderaClasificacionController?action=eliminar&id_madera="+maderaClasificacion.getId_madera()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Registrar nueva clasificación" onClick=" window.location.href='/aserradero/MaderaClasificacionController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
