<%-- 
    Document   : nuevoPrestamo
    Created on : 06-nov-2016, 0:57:31
    Author     : lmarcoss
--%>

<%@page import="entidades.Administrador"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Persona> personas = (List<Persona>) request.getAttribute("personas");
    List <Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp"%>
        <%@ include file="/TEMPLATE/headNuevo.jsp"%>
        <script src="/aserradero/js/fechaActual.js"></script>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
         <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PrestamoController?action=nuevo" method="post" id="formregistro">
                <h3>Registrar préstamo</h3>
                <fieldset id="user-details">
                    <table>
                        <input type="hidden" name="id_prestamo" value="1"readonly="" required="">
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" id="fecha" required="" maxlength="10"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Persona:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_persona" required="">
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
                            <td style="padding-left: 10px;"><label>Administrador:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_administrador" required="">
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
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="monto" step="0.01" min="0.01" max="99999999.99"  required=""></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>% de interés mensual:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="interes" step="1" min="1" max="100"  required=""><label>%</label></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PrestamoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" id="registrar" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
