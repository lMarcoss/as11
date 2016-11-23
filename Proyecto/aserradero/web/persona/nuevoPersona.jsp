<%-- 
    Document   : nuevoPersona
    Created on : 16-sep-2016, 19:47:45
    Author     : lmarcoss
--%>

<%@page import="entidades.Localidad"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp"%>
        <script src="/aserradero/js/id_persona.js"></script>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
         <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PersonaController?action=nuevo" method="post" id="formregistro" onsubmit="return validarPersona()">
                <h3>Agregar Persona</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id_persona:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_persona" id="id_persona" maxlength="18" placeholder="Se genera automáticamente" required readonly="" title="Se genera después de escribir fecha de nacimiento"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Nombre:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre" id="nombre" pattern="[A-Za-z].{2,}" title="Sólo letras aA-zZ, al menos 3 letras" maxlength="45" required=""/></td>
                        </tr>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label>Apellido paterno:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="apellido_paterno" id="apellido_paterno" pattern="[A-Za-z].{2,}" title="Sólo letras aA-zZ, al menos 3 letras" maxlength="45" required=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Apellido materno:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="apellido_materno" id="apellido_materno" pattern="[A-Za-z].{2,}" title="Sólo letras aA-zZ, al menos 4 letras" maxlength="45"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Dirección:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="direccion" title="dirección" placeholder="ej. carr Oaxaca Puerto Ángel km 97" maxlength="60"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Localidad:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="localidad" required="">
                                    <option></option>
                                    <%
                                        for (Localidad localidad : localidades) {
                                            out.print("<option value='"+localidad.getNombre_localidad()+"'>"+localidad.getNombre_localidad()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Estado:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="estado" id="estado" required="">
                                    <option></option>
                                    <option value="AGUASCALIENTES">AGUASCALIENTES</option>
                                    <option value="BAJA CALIFORNIA">BAJA CALIFORNIA</option>
                                    <option value="BAJA CALIFORNIA SUR">BAJA CALIFORNIA SUR</option>
                                    <option value="CAMPECHE">CAMPECHE</option>
                                    <option value="COAHUILA DE ZARAGOZA">COAHUILA</option>
                                    <option value="COLIMA">COLIMA</option>
                                    <option value="CHIAPAS">CHIAPAS</option>
                                    <option value="CHIHUAHUA">CHIHUAHUA</option>
                                    <option value="DISTRITO FEDERAL">DISTRITO FEDERAL</option>
                                    <option value="DURANGO">DURANGO</option>
                                    <option value="GUANAJUATO">GUANAJUATO</option>
                                    <option value="GUERRERO">GUERRERO</option>
                                    <option value="HIDALGO">HIDALGO</option>
                                    <option value="JALISCO">JALISCO</option>
                                    <option value="MÉXICO">MÉXICO</option>
                                    <option value="MICHOACÁN DE OCAMPO">MICHOACÁN</option>
                                    <option value="MORELOS">MORELOS</option>
                                    <option value="NAYARIT">NAYARIT</option>
                                    <option value="NUEVO LEÓN">NUEVO LEÓN</option>
                                    <option value="OAXACA">OAXACA</option>
                                    <option value="PUEBLA">PUEBLA</option>
                                    <option value="QUERÉTARO">QUERÉTARO</option>
                                    <option value="QUINTANA ROO">QUINTANA ROO</option>
                                    <option value="SAN LUIS POTOSÍ">SAN LUIS POTOSÍ</option>
                                    <option value="SINALOA">SINALOA</option>
                                    <option value="SONORA">SONORA</option>
                                    <option value="TABASCO">TABASCO</option>
                                    <option value="TAMAULIPAS">TAMAULIPAS</option>
                                    <option value="TLAXCALA">TLAXCALA</option>
                                    <option value="VERACRUZ DE IGNACIO DE LA LLAVE">VERACRUZ</option>
                                    <option value="YUCATÁN">YUCATÁN</option>
                                    <option value="ZACATECAS">ZACATECAS</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Sexo:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="sexo" id="sexo">
                                    <option value="H">Hombre</option>
                                    <option value="M">Mujer</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha de nacimiento:</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha_nacimiento" id="fecha_nacimiento" onblur="crearIdPersona()" required="" maxlength="10" title="Es importante la fecha de nacimiento para asignar un identificador a la persona"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Teléfono:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="telefono" pattern="[0-9]{10}" title="10 dígitos" placeholder="951xxxxxxx"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PersonaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" id="registrar" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->  
    </body>
</html>
