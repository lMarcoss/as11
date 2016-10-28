<%--
    Document   : nuevoOtroGasto
    Created on : 26/09/2016, 03:18:11 PM
    Author     : rcortes
--%>

<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
%>
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
        <div>
            <form action="/aserradero/OtroGastoController?action=nuevo" method="post" id="formregistro">
                <h3>Agregar pago</h3>
                <fieldset id="user-details">
                    <table>                        
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" required="" placeholder="AAAA/MM/DD"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_empleado">Id empleado:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el empleado que busca, primero agreguelo en la lista de empleado">
                                    <option></option>
                                    <%
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
                            <td style="padding-left: 10px;"><label for="nombre_gasto">Nombre de gasto:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_gasto" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="monto">Monto:</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.01" name="monto" required="" min="0.0" max="99999999.99"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="observacion">Observación:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="observacion" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/OtroGastoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
