<%-- 
    Document   : nuevoPagoCompra
    Created on : 28-sep-2016, 9:51:06
    Author     : lmarcoss
--%>

<%@page import="entidades.Compra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <form action="/aserradero/PagoCompraController?action=nuevo&id_pago=1" method="post" id="formregistro">
                <h3>Agregar pago compra</h3>
                <fieldset id="user-details">
                    <table>
                         <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id compra:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_compra" required="" title="Si no existe la compra que busca, primero agreguelo en la lista de compras">
                                    <option></option>
                                    <%
                                        List <Compra> compras = (List<Compra>) request.getAttribute("compras");
                                        for (Compra compra : compras) {
                                            out.print("<option value='"+compra.getId_compra()+"'>"+compra.getId_compra()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                       
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="monto" type="number" min='0.01' max='99999999.99' step=".01" required=""/>                             
                            </td>
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
