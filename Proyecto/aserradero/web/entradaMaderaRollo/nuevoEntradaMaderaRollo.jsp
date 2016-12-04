<%--
    Document   : nuevaEntradaMaderaRollo
    Created on : 26/09/2016, 06:08:43 PM
    Author     : rcortes
--%>
<%@page import="java.sql.Date"%>
<%@page import="entidades.Proveedor"%>
<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
    List <Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
    List <Empleado> choferes = (List<Empleado>) request.getAttribute("choferes");
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
            <form action="/aserradero/EntradaMaderaRolloController?action=nuevo_entrada" method="POST">
                <h3>Agregar nueva entrada de madera</h3>
                <fieldset id="user-details">
                    <table>
                        <input type="hidden" name="id_entrada" value="1" id="id_entrada"/>
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=fechaActual%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Proveedor</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_proveedor" required="" title="Si no existe el proveedor que busca, primero agreguelo en la lista de proveedores">
                                    <option></option>
                                    <%
                                        for (Proveedor proveedor : proveedores) {
                                            out.print("<option value='"+proveedor.getId_proveedor()+"'>"+proveedor.getProveedor()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Chofer</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_chofer" required="" title="Si no existe el empleado que busca, primero agreguelo en la lista de empleados">
                                    <option></option>
                                    <%
                                        for (Empleado chofer : choferes) {
                                            out.print("<option value='"+chofer.getId_empleado()+"'>"+chofer.getEmpleado()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
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
                            <td style="padding-left: 10px;"><label>Volumen primario</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="volumen_primario" id="volumen" step=".001" min="0.000" max="9999999.999"  required="" onblur="calcularMontoTotal()"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen secundario</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="volumen_secundario" id="volumen" step=".001" min="0.000" max="9999999.999"  required="" onblur="calcularMontoTotal()"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen terciario</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="volumen_terciario" id="volumen" step=".001" min="0.000" max="9999999.999"  required="" onblur="calcularMontoTotal()"/></td>
                        </tr>
<!--                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen total</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".001" name="volumen_total" id="monto" max="" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Costo total</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".01" name="costo_total" id="monto" required="" readonly=""/></td>
                        </tr>-->
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/EntradaMaderaRolloController?action=listar_entrada"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
