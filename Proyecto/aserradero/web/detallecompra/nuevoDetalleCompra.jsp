<%--
    Document   : nuevoDetalleCompra
    Created on : 26/09/2016, 09:15:09 PM
    Author     : rcortes
--%>

<%@page import="entidades.Compra"%>
<%@page import="entidades.CostoMaderaCompra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <form action="/aserradero/DetalleCompraController?action=nuevo" method="POST">
                <h3>Agregar</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_compra">Id compra</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_compra" required="" title="Si no existe la que busca, primero agréguelo en la lista de Costo Madera Compra">
                                    <option></option>
                                    <%
                                        List <Compra> compras = (List<Compra>) request.getAttribute("compras");                                        
                                        try{
                                            for (Compra compra : compras) {
                                            out.print("<option value='"+compra.getId_compra()+"'>"+"Compra "+compra.getId_compra()+" realizado el "+compra.getFecha()+"</option>");
                                        }
                                        }catch(Exception e){
                                            System.out.println(e);
                                        }                                    
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="clasificacion">Clasificación</label></td>
                            <td style="padding-left: 10px;">
                                <select name="clasificacion" id="clasificacion" required="" title="Si no existe la que busca, primero agréguelo en la lista de Costo Madera Compra" onblur="seleccionarCostoMaderaCompra()">
                                    <option></option>
                                    <%
                                        List <CostoMaderaCompra> costomaderacompras = (List<CostoMaderaCompra>) request.getAttribute("costomaderacompras");                                        
                                        try{
                                            for (CostoMaderaCompra costomaderacompra : costomaderacompras) {
                                            out.print("<option value='"+costomaderacompra.getClasificacion()+"'>"+costomaderacompra.getClasificacion()+"</option>");
                                        }
                                        }catch(Exception e){
                                            System.out.println(e);
                                        }                                    
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Costo por volumen</label></td>
                            <td style="padding-left: 10px;">
                                <select name="costo_volumen" id="costo_volumen" required="" title="Si no existe la que busca, primero agréguelo en la lista de Costo Madera Compra" disabled="">
                                    <option></option>
                                    <%    
                                    for (CostoMaderaCompra costomaderacompra : costomaderacompras)
                                        out.print("<option value='"+costomaderacompra.getCosto()+"'>"+costomaderacompra.getCosto()+"</option>");  
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="volumen">Volumen</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="volumen" id="volumen" step=".001" min="0.001" max="99999.999"  required="" onblur="calcularMontoTotal()"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="monto">Monto</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".01" name="monto" id="monto" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/DetalleCompraController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
