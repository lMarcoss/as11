<%-- 
    Document   : nuevoAdministrador
    Created on : 16-oct-2016, 23:48:46
    Author     : lmarcoss
--%>

<%@page import="entidades.Persona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Persona> personas = (List<Persona>) request.getAttribute("personas");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Nuevo administrador</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/AdministradorController?action=nuevo" method="post" id="formregistro">
                <h3>Agregar nuevo administrador</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Administrador:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_administrador" required="" title="Si no existe la persona que busca, primero agreguelo en la lista de personas">
                                    <option></option>
                                    <%
                                        for (Persona persona : personas) {
                                            out.print("<option value='"+persona.getId_persona()+"'>"+persona.getNombre()+" "+persona.getApellido_paterno()+" "+persona.getApellido_materno()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/AdministradorController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
