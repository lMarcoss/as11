<%-- 
    Document   : actualizarPrestamo
    Created on : 06-nov-2016, 0:57:42
    Author     : lmarcoss
--%>

<%@page import="entidades.Persona"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Administrador"%>
<%@page import="entidades.Prestamo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Prestamo prestamo = (Prestamo) request.getAttribute("prestamo");
    List <Persona> personas = (List<Persona>) request.getAttribute("personas");
    List <Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp"%>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
         <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PrestamoController?action=actualizar" method="post" id="formregistro">
                <h3>Modificar préstamo</h3>
                <fieldset id="user-details">
                    <table>
                        <input type="hidden" name="id_prestamo" value="<%=prestamo.getId_prestamo()%>"readonly="" required="">
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" id="fecha" value="<%=prestamo.getFecha()%>" required="" maxlength="10" readonly=""></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Persona:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_persona" required="">
                                    <%
                                        for (Persona persona : personas) {
                                            if(prestamo.getId_persona().substring(0, 18).equals(persona.getId_persona())){
                                                out.print("<option selected=\"\" value='"+persona.getId_persona()+"'>"+persona.getNombre()+" "+persona.getApellido_paterno()+" "+persona.getApellido_materno()+"</option>");
                                            }else{
                                                out.print("<option value='"+persona.getId_persona()+"'>"+persona.getNombre()+" "+persona.getApellido_paterno()+" "+persona.getApellido_materno()+"</option>");
                                            }
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
                                            if(prestamo.getId_administrador().equals(administrador.getId_administrador())){
                                                out.print("<option selected=\"\" value='"+administrador.getId_administrador()+"'>"+administrador.getNombre()+"</option>");
                                            }else{
                                                out.print("<option value='"+administrador.getId_administrador()+"'>"+administrador.getNombre()+"</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="monto" value="<%=prestamo.getMonto()%>" step="0.01" min="0.01" max="99999999.99"  required=""></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>% de interés mensual:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="interes" value="<%=prestamo.getInteres()%>" step="1" min="1" max="100"  required=""></td>
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
