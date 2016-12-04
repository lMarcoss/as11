<%-- 
    Document   : actualizarVenta
    Created on : 21-sep-2016, 21:48:48
    Author     : lmarcoss
--%>

<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.Date"%>
<%@page import="entidades.Venta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Venta venta = (Venta) request.getAttribute("venta");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <div>
            <form action="/aserradero/VentaController?action=cobrar" method="post" id="formregistro">
                <h3>Actualizar datos venta</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=venta.getFecha()%>" min="1920-01-01" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id venta:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_venta" value="<%=venta.getId_venta()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id cliente</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_cliente" value="<%=venta.getId_cliente()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id empleado:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_empleado" value="<%=venta.getId_empleado()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Estatus:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="estatus" value="<%=venta.getEstatus()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Tipo pago:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="tipo_pago" required="" title="Seleccione tipo de pago">
                                    <option></option>
                                    <option value="Anticipado">Pago anticipado</option>
                                    <option value="Normal">Pago inmediato</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Tipo venta:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="tipo_venta" value="<%=venta.getTipo_venta()%>" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Tipo ticket:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="tipo_ticket" required="" title="Seleccione tipo de ticket">
                                    <option></option>
                                    <option value="costo">Mostrar costo</option>
                                    <option value="sin_costo">Sin costo</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/VentaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Generar ticket"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
