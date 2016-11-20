<%-- 
    Document   : produccionMaderas
    Created on : 28-sep-2016, 9:48:27
    Author     : lmarcoss
--%>
<%@page import="entidades.ProduccionMadera"%>
<%@page import="java.util.List"%>
<%
    List <ProduccionMadera> produccionMaderas = (List<ProduccionMadera>) request.getAttribute("produccionMaderas");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Producción madera</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/ProduccionMaderaController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="fecha">Fecha</option>
                            <option value="id_madera">Id madera</option>
                            <option value="num_piezas">Número de piezas</option>
                            <option value="empleado">Empleado</option>
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
                        <th>Id_madera</th>
                        <th>Núm. piezas</th>
                        <th>Empleado</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (ProduccionMadera produccionMadera : produccionMaderas) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+produccionMadera.getFecha()+"</td>"
                                +"<td>"+produccionMadera.getId_madera()+"</td>"
                                +"<td>"+produccionMadera.getNum_piezas()+"</td>"
                                +"<td>"+produccionMadera.getEmpleado()+"</td>"
                                        
//                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+produccionMadera.getId_empleado().substring(0,18)+"\">"+produccionMadera.getEmpleado()+"</a></td>"
                                +"<td><a href=\"/aserradero/ProduccionMaderaController?action=modificar&id_produccion="+produccionMadera.getId_produccion()+"\">Modificar</a></td>"
//                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/ProduccionMaderaController?action=eliminar&id_produccion="+produccionMadera.getId_produccion()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar entrada" onClick=" window.location.href='/aserradero/ProduccionMaderaController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
