<%--
    Document   : nuevoCostoMaderaCompra
    Created on : 26/09/2016, 11:14:36 PM
    Author     : rcortes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <form action="/aserradero/CostoMaderaEntradaController?action=nuevo" method="POST">
                <h3>Agregar</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="clasificacion">Clasificación</label></td>
                            <td style="padding-left: 10px;">
                                <select name="clasificacion">
                                    <option value="Primario">Primario</option>
                                    <option value="Secundario">Secundario</option>
                                    <option value="Terciario">Terciario</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="costo">Costo</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".01" name="costo" min="0.01" max="999999.99"/></td>
                        </tr>                        
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/CostoMaderaEntradaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
