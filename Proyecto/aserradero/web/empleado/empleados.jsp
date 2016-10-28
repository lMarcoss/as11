<%-- 
    Document   : empleados
    Created on : 24-sep-2016, 14:04:34
    Author     : lmarcoss
--%>

<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Empleados</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/EmpleadoController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="empleado">Empleado</option>
                            <option value="roll">Roll</option>
                            <option value="estatus">Status</option>
                            <option value="jefe">Jefe</option>
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
                        <th>Empleado</th>
                        <th>Roll</th>
                        <th>Status</th>
                        <th>Jefe</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        
                        int i=0;
                        for (Empleado empleado : empleados) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+empleado.getId_empleado()+"\">"+empleado.getEmpleado()+"</a></td>"
                                +"<td>"+empleado.getRoll()+"</td>"
                                +"<td>"+empleado.getEstatus()+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+empleado.getId_jefe()+"\">"+empleado.getJefe()+"</a></td>"
                                +"<td><a href=\"/aserradero/EmpleadoController?action=modificar&id_empleado="+empleado.getId_empleado()+"&roll="+empleado.getRoll()+"\">Actualizar</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/EmpleadoController?action=eliminar&id_empleado="+empleado.getId_empleado()+"&id_jefe="+empleado.getId_jefe()+"&roll="+empleado.getRoll()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar empleado" onClick=" window.location.href='/aserradero/EmpleadoController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
