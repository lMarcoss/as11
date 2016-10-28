<%-- 
    Document   : nuevoCostoMadera
    Created on : 27-sep-2016, 15:50:53
    Author     : lmarcoss
--%>

<%@page import="entidades.MaderaClasificacion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <MaderaClasificacion> maderaClasificaciones = (List<MaderaClasificacion>) request.getAttribute("maderaClasificaciones");
%>
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
        <div>
            <form action="/aserradero/CostoMaderaController?action=nuevo" method="post" id="formregistro">
                <h3>Nuevo costo madera para venta</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Madera:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_madera" required="">
                                    <option></option>
                                    <%
                                        for (MaderaClasificacion maderaClasificacion : maderaClasificaciones) {
                                            out.print("<option value='"+maderaClasificacion.getId_madera()+"'>"+maderaClasificacion.getId_madera()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Costo por volúmen:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="monto_volumen" step=".01" min="0.01" max="999.99"  title="Sólo números" required="" /></td>
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
