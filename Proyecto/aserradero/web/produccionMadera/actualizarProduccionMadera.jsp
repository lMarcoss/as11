<%-- 
    Document   : actualizarProduccionMadera
    Created on : 28-sep-2016, 9:48:54
    Author     : lmarcoss
--%>

<%@page import="entidades.ProduccionMadera"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ProduccionMadera produccion = (ProduccionMadera) request.getAttribute("produccion");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!--Formulario de registro-->
        <div>
            <form action="/aserradero/ProduccionMaderaController?action=actualizar" method="post" id="formregistro">
                <h3>Registrar producción</h3>
                <fieldset id="user-details">
                    <table>
                        <input type="hidden" name="id_produccion" value="<%=produccion.getId_produccion()%>" readonly=""/>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;">
                                <input type="date" name="fecha" value="<%=produccion.getFecha()%>" readonly="" required=""/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Madera:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_madera" required="" id="id_madera" disabled="">
                                    <option value="<%=produccion.getId_madera()%>"><%=produccion.getId_madera()%></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Número de piezas:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" id="num_piezas" value="<%=produccion.getNum_piezas()%>" min="1" max="999" required="" title="Escribe la cantidad de piezas"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" id="id_empleado">
                                    <option value="<%=produccion.getId_empleado()%>"><%=produccion.getEmpleado()%></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/ProduccionMaderaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
