<%--
    Document   : actualizarCostoMaderaCompra
    Created on : 26/09/2016, 11:15:00 PM
    Author     : rcortes
--%>

<%@page import="entidades.ClasificacionMaderaEntrada"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ClasificacionMaderaEntrada clasificacion = (ClasificacionMaderaEntrada) request.getAttribute("clasificacionMaderaEntrada");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/ClasificacionMaderaEntradaController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                          <td style="padding-left: 10px;"><label for="clasificacion">Clasificación</label></td>
                          <td style="padding-left: 10px;"><input name="clasificacion" value="<%= clasificacion.getClasificacion() %>" readonly=""/>
                          </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="costo">Costo</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="costo" step=".01" min="0.01" max="999999.99"  value="<%=clasificacion.getCosto()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/ClasificacionMaderaEntradaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
