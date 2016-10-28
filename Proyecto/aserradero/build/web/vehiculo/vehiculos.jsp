<%--
    Document   : vehiculos
    Created on : 26/09/2016, 04:46:44 PM
    Author     : rcortes
--%>

<%@page import="entidades.Vehiculo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Vehiculo> vehiculos = (List<Vehiculo>) request.getAttribute("vehiculos");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Vehículos</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/VehiculoController?action=buscar">
                <table>
                    <tr>
                      <td>
                        <select name="nombre_campo" >
                          <option value="matricula">Matrícula</option>
                          <option value="tipo">Tipo</option>
                          <option value="color">Color</option>
                          <option value="carga_admitida">Carga máxima</option>
                          <option value="motor">Motor</option>
                          <option value="modelo">Modelo</option>
                          <option value="costo">Costo</option>
                          <option value="empleado">Empleado</option>
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
                    <th>Matricula</th>
                    <th>Tipo</th>
                    <th>Color</th>
                    <th>Carga admitida</th>
                    <th>Motor</th>
                    <th>Modelo</th>
                    <th>Costo</th>
                    <th>Empleado</th>
                </tr>
                <%
                    int i=0;
                    for (Vehiculo vehiculo : vehiculos) {
                        out.print("<tr>"
                            +"<td>"+(i+1)+"</td>"                            
                            +"<td>"+vehiculo.getMatricula()+"</td>"
                            +"<td>"+vehiculo.getTipo()+"</td>"
                            +"<td>"+vehiculo.getColor()+"</td>"
                            +"<td>"+vehiculo.getCarga_admitida()+"</td>"
                            +"<td>"+vehiculo.getMotor()+"</td>"
                            +"<td>"+vehiculo.getModelo()+"</td>"
                            +"<td>"+vehiculo.getCosto()+"</td>"
                            +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+vehiculo.getId_empleado()+"\">"+vehiculo.getEmpleado()+"</a></td>"
                            +"<td><a href=\"/aserradero/VehiculoController?action=modificar&id_vehiculo="+vehiculo.getId_vehiculo()+"\">Actualizar</a></td>"
                            + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/VehiculoController?action=eliminar&id_vehiculo="+vehiculo.getId_vehiculo()+"';};\">Eliminar</a></td>"
                        + "</tr>" );
                        i++;
                    }
                %>
            </table>
            <div>
                <input type="button" value="Agregar vehiculo" onClick=" window.location.href='/aserradero/VehiculoController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
