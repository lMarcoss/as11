<%-- 
    Document   : actualizarProduccionDetalle
    Created on : 28/09/2016, 03:34:56 PM
    Author     : Marcos
--%>

<%@page import="entidades.ProduccionDetalle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%ProduccionDetalle produccionDetalle = (ProduccionDetalle) request.getAttribute("produccionDetalle");%>
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
            <form action="/aserradero/ProduccionDetalleController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id produccion:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_produccion" value="<%=produccionDetalle.getId_produccion()%>" readonly=""/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id madera:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_madera" value="<%=produccionDetalle.getId_madera()%>" readonly=""/>
                            </td>
                        </tr>S
                  
                        <tr>
                            <td style="padding-left: 10px;"><label>Piezas:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="num_piezas" type="number" value="<%=produccionDetalle.getNum_piezas()%>" step="any" required=""/>                             
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
