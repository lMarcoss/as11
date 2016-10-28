<%-- 
    Document   : actualizarPagoEmpleado
    Created on : 30/09/2016, 08:48:10 AM
    Author     : Marcos
--%>

<%@page import="entidades.PagoEmpleado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%PagoEmpleado pagoEmpleado = (PagoEmpleado) request.getAttribute("pagoEmpleado");%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PagoEmpleadoController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    
                    <table>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label>Id pago empleado:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_pago_empleado" value="<%=pagoEmpleado.getId_pago_empleado()%>" readonly=""/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=pagoEmpleado.getFecha()%>" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id empleado:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="id_empleado" value="<%=pagoEmpleado.getId_empleado()%>" readonly=""/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="monto" type="number" value="<%=pagoEmpleado.getMonto()%>" step="any" required=""/>                             
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Observacion:</label></td>
                            <td style="padding-left: 10px;">
                                <input name="observacion" type="text" value="<%=pagoEmpleado.getObservacion()%>" step="any" required=""/>                             
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PagoEmpleadoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
