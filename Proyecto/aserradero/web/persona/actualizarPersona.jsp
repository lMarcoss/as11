<%-- 
    Document   : actualizarPersona
    Created on : 16-sep-2016, 19:48:04
    Author     : lmarcoss
--%>

<%@page import="entidades.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Persona persona = (Persona) request.getAttribute("persona");
%>
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
            <form action="/aserradero/PersonaController?action=actualizar" method="post" id="formregistro">
                <h3>Modificar datos</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id persona:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_persona" value="<%=persona.getId_persona()%>" title="sólo lectura" maxlength="18" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Nombre:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre" value="<%=persona.getNombre()%>"  pattern="[A-Za-z].{2,}" title="Sólo letras aA-zZ, al menos 4 letras" maxlength="45" required="" readonly=""/></td>
                        </tr>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label>Apellido paterno:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="apellido_paterno" value="<%=persona.getApellido_paterno()%>" pattern="[A-Za-z].{2,}" title="Sólo letras aA-zZ, al menos 3 letras" maxlength="45" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Apellido materno:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="apellido_materno" value="<%=persona.getApellido_materno()%>" pattern="[A-Za-z].{2,}" title="Sólo letras aA-zZ, al menos 3 letras" maxlength="45" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Localidad:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="localidad" value="<%=persona.getLocalidad()%>" pattern="[A-Za-z].{3,}[A-Za-z]" title="Sólo letras aA-zZ, al menos 4 letras" maxlength="45" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Dirección:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="direccion" value="<%=persona.getDireccion()%>" title="dirección" placeholder="ej. carr Oaxaca Puerto Ángel km 97" maxlength="60"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Sexo:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="sexo">
                                    <option value="<%=persona.getSexo()%>"><%=persona.getSexo()%></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha de nacimiento:</label></td>
                            <td style="padding-left: 10px;"><input type="date" value="<%=persona.getFecha_nacimiento()%>" name="fecha_nacimiento" required="" readonly=""  maxlength="10" title="Es importante la fecha de nacimiento para asignar un identificador a la persona"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Teléfono:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="telefono" value="<%=persona.getTelefono()%>" pattern="[0-9]{10}" title="10 dígitos"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PersonaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->  
    </body>
</html>
