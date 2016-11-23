<%-- 
    Document   : nuevoPagoCompra
    Created on : 19-nov-2016, 22:27:07
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidadesVirtuales.VistaMontoPagoCompra"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <VistaMontoPagoCompra> listaMontoPagoCompra = (List<VistaMontoPagoCompra>) request.getAttribute("listaMontoPagoCompra");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <script src="/aserradero/js/selectorMontoPorPagar.js"></script>
        <title>Nuevo pago</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PagoCompraController?action=insertar" method="post" id="formregistro">
                <h3>Registrar pago</h3>
                <fieldset id="user-details">
                    <table>
                        <%
                            if(!listaMontoPagoCompra.isEmpty()){
                        %>
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" required="" placeholder="AAAA/MM/DD"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Proveedor:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_proveedor" id="id_proveedor" required="" title="Seleccione un proveedor" onblur="seleccionarMontoPorPagar()">
                                    <option></option>
                                    <%
                                        for(VistaMontoPagoCompra pago: listaMontoPagoCompra){
                                            out.print("<option value='"+pago.getId_proveedor()+"'>"+pago.getProveedor()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto por pagar:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="cuenta_pendiente" id="cuenta_pendiente" required="" disabled="">
                                    <option></option>
                                    <%
                                        for(VistaMontoPagoCompra pago: listaMontoPagoCompra){
                                            out.print("<option value='"+pago.getMonto_por_pagar()+"'>"+pago.getMonto_por_pagar()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Cuenta por cobrar (anticipo):</label></td>
                            <td style="padding-left: 10px;">
                                <select name="cuenta_por_cobrar" id="cuenta_por_cobrar" required="" disabled="">
                                    <option></option>
                                    <%
                                        for(VistaMontoPagoCompra pago: listaMontoPagoCompra){
                                            out.print("<option value='"+pago.getCuenta_por_cobrar()+"'>"+pago.getCuenta_por_cobrar()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto a pagar:</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.01" name="monto_pago" id="monto_pago" min="0.01" max="" required="" onblur="seleccionarMontoPorPagar()"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto pendiente (por pagar):</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.01" name="monto_por_pagar" id="monto_por_pagar" min="0.00" max="" required="" readonly=""></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PagoCompraController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"></td>
                        </tr>
                        <%
                            }else{
                                out.println("<p style='color:red;'>No hay compras pendientes por pagar.</p>");
                        %>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PagoCompraController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar" disabled=""></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
