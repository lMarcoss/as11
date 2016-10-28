<%-- 
    Document   : nuevoProduccionMadera
    Created on : 28-sep-2016, 9:48:39
    Author     : lmarcoss
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
        
        <div>
            <form action="/aserradero/ProduccionMaderaController?action=nuevo" method="post" id="formregistro">
                <h3>Registrar producción</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;">
                                <input type="date" name="fecha" required=""/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id producción</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_produccion" pattern="[A-Za-z].{2,}" title="Sólo letras"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" id="id_empleado">
                                    <option></option>
                                    <%
                                        for (Empleado empleado : empleados) {
                                            out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/ProduccionMaderaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
