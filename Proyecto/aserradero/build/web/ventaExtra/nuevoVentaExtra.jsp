<%-- 
    Document   : nuevoVentaExtra
    Created on : 21-sep-2016, 23:43:35
    Author     : lmarcoss
--%>

<%@page import="entidades.Venta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Venta> ventas = (List<Venta>) request.getAttribute("ventas");
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
            <form action="/aserradero/VentaExtraController?action=nuevo" method="post" id="formregistro">
                <h3>Registrar venta extra</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id venta:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_venta" required="" title="Si no existe el id venta, primero agregalo a la lista de ventas con tipo venta Extra">
                                    <option></option>
                                    <%
                                        for (Venta venta : ventas) {
                                            out.print("<option value='"+venta.getId_venta()+"'>"+venta.getId_venta()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Tipo:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="tipo" required="" maxlength="50" placeholder="Ej: 2 costales de aserrin"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.01" name="monto" min="0.01" max="99999999.99" required="" placeholder="345.50"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Observación:</label></td>
                            <td style="padding-left: 10px;"><textarea name="observacion" maxlength="100" required="" title="Escriba una observacion de la venta" placeholder="ej: venta de aserrin"></textarea></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/VentaExtraController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
