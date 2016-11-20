<%-- 
    Document   : nuevoPagoCompra
    Created on : 19-nov-2016, 22:27:07
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidadesVirtuales.MontoPagoCompra"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <MontoPagoCompra> listaMontoPagoCompra = (List<MontoPagoCompra>) request.getAttribute("listaMontoPagoCompra");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <script src="/aserradero/js/selectorMontoPorPagar.js"></script>
        
        <title>Nuevo pago compra</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PagoCompraController?action=insertar" method="post" id="formregistro">
                <h3>Agregar pago</h3>
                <fieldset id="user-details">
                    <table>                        
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" required="" placeholder="AAAA/MM/DD"/></td>
                        </tr>
                        <%
                            if(!listaMontoPagoCompra.isEmpty()){
                        %>
                        <tr>
                            <td style="padding-left: 10px;"><label>Proveedor:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_proveedor" id="id_proveedor" required="" title="Seleccione un proveedor" onblur="seleccionarMontoPorPagar()">
                                    <option></option>
                                    <%
                                        for(MontoPagoCompra pago: listaMontoPagoCompra){
                                            out.print("<option value='"+pago.getId_proveedor()+"'>"+pago.getProveedor()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Cuenta pendiente:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="cuenta_pendiente" id="cuenta_pendiente" required="" disabled="">
                                    <option></option>
                                    <%
                                        for(MontoPagoCompra pago: listaMontoPagoCompra){
                                            out.print("<option value='"+pago.getMonto_por_pagar()+"'>"+pago.getMonto_por_pagar()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <%
                            }else{
                                out.println("<p style='color:red;'>No hay compras/cuentas pendientes para pagar. Presione el botón cancelar</p>");
                            }
                        %>
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
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
