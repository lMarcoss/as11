<%-- 
    Document   : nuevoProduccionDetalle
    Created on : 28/09/2016, 01:28:03 PM
    Author     : Marcos
--%>

<%@page import="entidades.MaderaClasificacion"%>
<%@page import="entidades.ProduccionMadera"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Personas</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/ProduccionDetalleController?action=nuevo" method="post" id="formregistro">
                <h3>Agregar producción detalle</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>produccion:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_produccion" required="" title="Si no existe la produccion que busca, primero agreguelo en la lista de produccion detalles">
                                    <option></option>
                                    <%
                                        List <ProduccionMadera> produccionMaderas = (List<ProduccionMadera>) request.getAttribute("produccionMaderas");
                                        for (ProduccionMadera datoProduccionMadera : produccionMaderas) {
                                            out.print("<option value='"+datoProduccionMadera.getId_produccion()+"'>"+datoProduccionMadera.getId_produccion()+" </option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>madera:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_madera" required="" title="Si no existe la madera que busca, primero agreguelo en la lista de madera clasificaciones">
                                    <option></option>
                                    <%
                                        List <MaderaClasificacion> maderaClasificaciones = (List<MaderaClasificacion>) request.getAttribute("maderaClasificaciones");
                                        for (MaderaClasificacion datoMaderaClasificacion : maderaClasificaciones) {
                                            out.print("<option value='"+datoMaderaClasificacion.getId_madera()+"'>"+datoMaderaClasificacion.getId_madera()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                                             
                        <tr>
                            <td style="padding-left: 10px;"><label>Num piezas:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="num_piezas" type="number" required=""/>                             
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/ProduccionDetalleController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
