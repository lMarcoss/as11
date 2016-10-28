<%-- 
    Document   : actualizarAnticipoCliente
    Created on : 26/09/2016, 07:46:55 PM
    Author     : Marcos
--%>

<%@page import="entidades.AnticipoCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%AnticipoCliente anticipoCliente = (AnticipoCliente) request.getAttribute("anticipoCliente");%>
<!DOCTYPE html>
<html>
   <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Nuevo anticipo cliente</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/AnticipoClienteController?action=actualizar" method="post" id="formregistro">
                <h3>Agregar anticipo cliente</h3>
                <fieldset id="user-details">
                    
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id anticipo:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_anticipo_c" value="<%=anticipoCliente.getId_anticipo_c()%>" readonly=""/>
                            </td>
                        </tr>
                         <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=anticipoCliente.getFecha()%>" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>cliente:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_cliente" value="<%=anticipoCliente.getId_cliente()%>" readonly=""/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>empleado:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_empleado" value="<%=anticipoCliente.getId_empleado()%>" readonly=""/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="monto_anticipo" type="number" value="<%=anticipoCliente.getMonto_anticipo()%>" step="any" required=""/>                             
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/AnticipoClienteController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
