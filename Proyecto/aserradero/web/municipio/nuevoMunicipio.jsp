<%-- 
    Document   : nuevoMunicipio
    Created on : 13-sep-2016, 17:20:34
    Author     : lmarcoss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <form action="/aserradero/MunicipioController?action=nuevo" method="post" id="formregistro">
                <h3>Agregar municipio</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="name">Nombre municipio:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_municipio"  pattern="[A-Za-z].{3,}[A-Za-z]" title="Sólo letras aA-zZ, al menos 4 letras" maxlength="45" required=""/></td>
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
                                    <option value="COAHUILA">COAHUILA</option>
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
                                    <option value="MICHOACÁN">MICHOACÁN</option>
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
                                    <option value="VERACRUZ">VERACRUZ</option>
                                    <option value="YUCATÁN">YUCATÁN</option>
                                    <option value="ZACATECAS">ZACATECAS</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="telefono">Teléfono:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="telefono" pattern="[0-9]{10}" title="10 dígitos"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/MunicipioController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->  
    </body>
</html>
