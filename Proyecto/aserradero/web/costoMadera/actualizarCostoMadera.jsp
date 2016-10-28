<%-- 
    Document   : actualizarCostoMadera
    Created on : 27-sep-2016, 15:50:43
    Author     : lmarcoss
--%>

<%@page import="entidades.CostoMadera"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CostoMadera costoMadera = (CostoMadera) request.getAttribute("costoMadera");
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
            <form action="/aserradero/CostoMaderaController?action=actualizar" method="post" id="formregistro">
                <h3>Cambiar costo madera para venta</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Madera:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_madera" value="<%=costoMadera.getId_madera()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Costo por volúmen:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="monto_volumen" value="<%=costoMadera.getMonto_volumen()%>" step=".01" min="0.01" max="999.99"  title="Sólo números" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/CostoMaderaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->  
    </body>
</html>
