<%-- 
    Document   : actualizarSalidaMaderaRollo
    Created on : 28/10/2016, 05:09:40 PM
    Author     : li-906
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.SalidaMaderaRollo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SalidaMaderaRollo salida = (SalidaMaderaRollo) request.getAttribute("salidaMaderaRollo");
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
        
        <div>
            <!-- ******************* Formulario de registro-->
            <form action="/aserradero/SalidaMaderaRolloController?action=actualizar" method="POST">
                <h3>Registrar salida</h3>
                <fieldset id="user-details">
                    <table>
                        <input type="hidden" name="id_salida" value="<%=salida.getId_salida()%>" readonly=""/>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=salida.getFecha()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el datospersona que busca, primero agreguelo en la lista de datospersona">
                                    <option value="<%=salida.getId_empleado()%>" selected=""><%=salida.getEmpleado()%></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Num. piezas</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" value="<%=salida.getNum_piezas()%>" min="1" max="9999" required=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen total</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".001" name="volumen_total" value="<%=salida.getVolumen_total()%>" min="0.001" max="9999999.999" required=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/SalidaMaderaRolloController?action=listar_salida"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
