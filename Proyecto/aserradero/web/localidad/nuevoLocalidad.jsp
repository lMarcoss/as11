<%-- 
    Document   : nuevoMunicipio
    Created on : 13-sep-2016, 17:20:34
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.Municipio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%List <Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");%>
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
        <div>
            <form action="/aserradero/LocalidadController?action=nuevo" method="post" id="formregistro">
                <h3>Agregar localidad</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="name">Nombre localidad:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_localidad"  pattern="[A-Za-z].{3,}[A-Za-z]" title="Sólo letras aA-zZ, al menos 4 letras" maxlength="45" required=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Nombre municipio:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="nombre_municipio" required="">
                                    <option></option>
                                    <%
                                        for (Municipio municipio : municipios) {
                                            out.print("<option value='"+municipio.getNombre_municipio()+"'>"+municipio.getNombre_municipio()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="telefono">Teléfono:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="telefono" pattern="[0-9]{10}" title="10 dígitos"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/LocalidadController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
        
        
    </body>
</html>
