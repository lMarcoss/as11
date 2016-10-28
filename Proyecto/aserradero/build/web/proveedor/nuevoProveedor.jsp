<%-- 
    Document   : nuevoProveedor
    Created on : 27-sep-2016, 0:12:42
    Author     : lmarcoss
--%>

<%@page import="entidades.Administrador"%>
<%@page import="entidades.Persona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/ProveedorController?action=nuevo" method="post" id="formregistro">
                <h3>Agregar proveedor</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id empleado:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_persona" required="" title="Si no existe la persona que busca, primero agreguelo en la lista de personas">
                                    <option></option>
                                    <%
                                        List <Persona> personas = (List<Persona>) request.getAttribute("personas");
                                        for (Persona persona : personas) {
                                            out.print("<option value='"+persona.getId_persona()+"'>"+persona.getNombre()+" "+persona.getApellido_paterno()+" "+persona.getApellido_materno()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Jefe:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_jefe" required="" title="Si no existe la persona que busca, primero agreguelo en la lista de empleados jefe">
                                    <option></option>
                                    <%
                                        List <Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
                                        for (Administrador administrador : administradores) {
                                            out.print("<option value='"+administrador.getId_administrador()+"'>"+administrador.getNombre()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/ProveedorController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
