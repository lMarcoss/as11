<%--
    Document   : nuevoVehiculo
    Created on : 26/09/2016, 05:30:43 PM
    Author     : rcortes
--%>

<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
            <form action="/aserradero/VehiculoController?action=nuevo" method="POST">
                <h3>Agregar nuevo vehículo</h3>
                <fieldset id="user-details">
                    <table>                       
                        <tr>
                            <td style="padding-left: 10px;"><label for="matricula">Matrícula</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="matricula" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="tipo">Tipo</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="tipo"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="color">Color</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="color" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="carga_admitida">Carga admitida</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="carga_admitida" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="motor">Motor</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="motor" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="modelo">Modelo</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="modelo" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="costo">Costo</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".01" name="costo" min="0.0"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_empleado">Id empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el empleado que busca, primero agreguelo en la lista de empleado">
                                    <option></option>
                                    <%
                                        List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
                                        try{
                                            for (Empleado empleado : empleados) {
                                            out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");
                                        }
                                        }catch(Exception e){
                                            System.out.println(e);
                                        }
                                        
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/VehiculoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
