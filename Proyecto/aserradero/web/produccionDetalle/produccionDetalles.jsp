<%-- 
    Document   : produccionDetalles
    Created on : 28/09/2016, 12:50:22 PM
    Author     : Marcos
--%>

<%@page import="entidades.ProduccionDetalle"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <ProduccionDetalle> produccionDetalles = (List<ProduccionDetalle>) request.getAttribute("produccionDetalles");
    String mensaje=(String) request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Personas</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/ProduccionDetalleController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_produccion">Id produccion</option>
                            <option value="id_madera">Id madera</option>
                            <option value="num_piezas">Num piezas</option>
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
            <table>
                    <tr>
                        <th>N°</th>
                        <th>Id produccion</th>
                        <th>Id madera</th>
                        <th>Num piezas</th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    <%
                        int i=0;
                        for (ProduccionDetalle produccionDetalle : produccionDetalles) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/ProduccionMaderaController?action=buscar_produccion&id_produccion="+produccionDetalle.getId_produccion()+"\">"+produccionDetalle.getId_produccion()+"</a></td>"    
                                
                                +"<td><a href=\"/aserradero/MaderaClasificacionController?action=buscar_maderaClasificacion&id_madera="+produccionDetalle.getId_madera()+"\">"+produccionDetalle.getId_madera()+"</a></td>"
                                +"<td>"+produccionDetalle.getNum_piezas()+"</td>"
                                +"<td><a href=\"/aserradero/ProduccionDetalleController?action=modificar&id_produccion="+produccionDetalle.getId_produccion()+"&id_madera="+produccionDetalle.getId_madera()+"\">Actualizar</a></td>"    
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/ProduccionDetalleController?action=eliminar&id_produccion="+produccionDetalle.getId_produccion()+"&id_madera="+produccionDetalle.getId_madera()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar produccion detalle" onClick=" window.location.href='/aserradero/ProduccionDetalleController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
