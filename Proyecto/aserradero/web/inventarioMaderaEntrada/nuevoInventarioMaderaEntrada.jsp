<%--
    Document   : nuevo<%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">MaderaEntrada
    Created on : 26/09/2016, 11:48:51 PM
    Author     : rcortes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
            <form action="/aserradero/InventarioMaderaEntradaController?action=nuevo" method="POST">
                <h3>Agregar nuevo inventario madera compra</h3>
                <fieldset id="user-details">
                    <table>
                      <tr>
                          <td style="padding-left: 10px;"><label for="clasificacion">Clasificación</label></td>
                          <td style="padding-left: 10px;">
                              <select name="clasificacion">
                                  <option>Primario</option>
                                  <option>Secundario</option>
                                  <option>Terciario</option>
                              </select>
                          </td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="volumen">Volumen</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="volumen" /></td>
                      </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="num_piezas">Num. piezas</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="num_piezas" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/InventarioMaderaEntradaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
