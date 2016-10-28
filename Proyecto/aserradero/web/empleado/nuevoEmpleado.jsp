<%-- 
    Document   : nuevoPersona
    Created on : 24-sep-2016, 14:22:52
    Author     : lmarcoss
--%>

<%@page import="entidades.Administrador"%>
<%@page import="entidades.Persona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Persona> personas = (List<Persona>) request.getAttribute("personas");
    List <Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
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
            <form action="/aserradero/EmpleadoController?action=nuevo" method="post" id="formregistro">
                <h3>Agregar empelado</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado:</label></td>
                            <input type="hidden" name="id_empleado" value="" readonly=""> <!--Se calcula en el CRUD-->
                            <td style="padding-left: 10px;">
                                <!-- Seleccionar persona que se va a asignar como empleado-->
                                <select name="id_persona" required="" title="Si no existe la persona que busca, primero agreguelo en la lista de personas">
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
                            <td style="padding-left: 10px;"><label>Roll:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="roll" required="">
                                    <option></option>
                                    <option value="Empleado">Empleado</option>
                                    <option value="Chofer">Chofer</option>
                                    <option value="Vendedor">Vendedor</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Estaus</label></td>
                            <td>
                                <select name="estatus" required="">
                                    <option></option>
                                    <option value="Activo">Activo</option>
                                    <option value="Inactivo">Inactivo</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Jefe:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_jefe" required="" title="Si no existe la persona que busca, primero agreguelo en la lista de administradores">
                                    <option></option>
                                    <%
                                        for (Administrador administrador : administradores) {
                                            out.print("<option value='"+administrador.getId_administrador()+"'>"+administrador.getNombre()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/EmpleadoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
