<%-- 
    Document   : nuevoSalidaMadera
    Created on : 27-oct-2016, 20:43:44
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="entidades.Empleado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
    Date fechaActual = (Date) request.getAttribute("fechaActual");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
            <form action="/aserradero/SalidaMaderaRolloController?action=nuevo_salida" method="POST">
                <h3>Registrar salida</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=fechaActual%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el datospersona que busca, primero agreguelo en la lista de datospersona">
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
                            <td style="padding-left: 10px;"><label>Num. piezas</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" min="1" max="9999" required=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen total</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".001" name="volumen_total" min="0.001" max="9999999.999" required=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/SalidaMaderaRolloController?action=listar_salida"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
