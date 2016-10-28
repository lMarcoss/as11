<%-- 
    Document   : actualizarPagoCompra
    Created on : 28-sep-2016, 9:51:21
    Author     : lmarcoss
--%>

<%@page import="entidades.PagoCompra"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%PagoCompra pagoCompra = (PagoCompra) request.getAttribute("pagoCompra");%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Compras</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PagoCompraController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=pagoCompra.getFecha()%>" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id compra:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_compra" value="<%=pagoCompra.getId_compra()%>" readonly=""/>
                            </td>
                        </tr>  
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="monto" type="number" value="<%=pagoCompra.getMonto()%>" step="any" required=""/>                             
                            </td>
                        </tr>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label>Tipo pago:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="pago" required="">
                                    <option> </option>
                                    <option value="Anticipado">Anticipado</option>
                                    <option value="Normal">Normal</option>
                                </select>
                            </td>
                        </tr>
                        
                        
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PagoCompraController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Pagar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
