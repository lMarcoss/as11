<%--
    Document   : actualizarDetalleCompra
    Created on : 26/09/2016, 09:15:25 PM
    Author     : rcortes
--%>

<%@page import="entidades.CostoMaderaCompra"%>
<%@page import="java.util.List"%>
<%@page import="entidades.DetalleCompra"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DetalleCompra detallecompra = (DetalleCompra) request.getAttribute("detallecompra");
    List <CostoMaderaCompra> costomaderacompras = (List<CostoMaderaCompra>) request.getAttribute("costomaderacompras");                                        
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/DetalleCompraController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_compra">Id detallecompra</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_compra" value="<%=detallecompra.getId_compra()%>"  readonly=""/></td>
                        </tr>
                        <%
                            
                            try{
                                for (CostoMaderaCompra costomaderacompra : costomaderacompras) {
                                    if(detallecompra.getClasificacion().equals(costomaderacompra.getClasificacion())){
                                        out.print("<td style='padding-left: 10px;'><label>Clasificación</label></td><td style='padding-left: 10px;'><input name ='clasificacion' id='clasificacion' value='"+detallecompra.getClasificacion()+"' readonly=''/></td></tr>");
                                        out.print("<td style='padding-left: 10px;'><label>Costo por volumen</label></td><td style='padding-left: 10px;'><input name ='costo_volumen' id='costo_volumen' value='"+costomaderacompra.getCosto()+"' readonly=''/></td></tr>");
                                    }
                            }
                            }catch(Exception e){
                                System.out.println(e);
                            }                                    
                        %>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label for="volumen">Volumen</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".001" name="volumen" id="volumen" value="<%=detallecompra.getVolumen()%>" onblur="calcularMontoTotal()"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="monto">Monto</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".01" name="monto" id="monto" value="<%=detallecompra.getMonto()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/DetalleCompraController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
