<%-- 
    Document   : actualizarMunicipio
    Created on : 14-sep-2016, 20:09:50
    Author     : lmarcoss
--%>

<%@page import="entidades.Localidad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%Localidad localidad = (Localidad) request.getAttribute("localidad");%>

<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/LocalidadController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar localidad</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="name">Nombre localidad:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_localidad" value="<%= localidad.getNombre_localidad()%>" pattern="[A-Za-z].{3,}[A-Za-z]" title="Sólo letras aA-zZ, al menos 4 letras" maxlength="45" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="name">Nombre municipio:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_municipio" value="<%= localidad.getNombre_municipio()%>" pattern="[A-Za-z].{3,}[A-Za-z]" title="Sólo letras aA-zZ, al menos 4 letras" maxlength="45" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="telefono">Teléfono:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="telefono" value="<%=localidad.getTelefono()%>" pattern="[0-9]{10}" title="10 dígitos"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/LocalidadController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
