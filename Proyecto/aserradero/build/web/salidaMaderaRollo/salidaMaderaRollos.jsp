<%-- 
    Document   : salidaMaderaRollos
    Created on : 27-oct-2016, 22:46:40
    Author     : lmarcoss
--%>

<%@page import="entidades.SalidaMaderaRollo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    List <SalidaMaderaRollo> salidas = (List<SalidaMaderaRollo>) request.getAttribute("salidas");
    String mensaje = (String)request.getAttribute("mensaje"); 
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Salidas</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/SalidaMaderaRolloController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                                <option value="fecha">Fecha</option>
                                <option value="empleado">Empleado</option>
                                <option value="num_piezas">Número de piezas</option>
                                <option value="volumen_total">Volumen</option>
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
                        <th>Fecha</th>
                        <th>Núm. piezas</th>
                        <th>Volumen</th>
                        <th>Empleado</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%                        
                        int i=0;
                        for (SalidaMaderaRollo salida : salidas) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+salida.getFecha()+"</td>"
                                +"<td>"+salida.getNum_piezas()+"</td>"
                                +"<td>"+salida.getVolumen_total()+"</td>"
                                +"<td>"+salida.getEmpleado()+"</td>"
                                +"<td><a href=\"/aserradero/SalidaMaderaRolloController?action=modificar&id_salida="+salida.getId_salida()+"\">Actualizar</a></td>"
//                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/SalidaMaderaRolloController?action=eliminar&id_compra="+compra.getId_compra()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Registrar salida" onClick=" window.location.href='/aserradero/SalidaMaderaRolloController?action=nuevo_salida'">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
