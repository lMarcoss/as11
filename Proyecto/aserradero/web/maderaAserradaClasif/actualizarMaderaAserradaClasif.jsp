<%-- 
    Document   : actualizarMaderaClasificacion
    Created on : 27-sep-2016, 2:45:22
    Author     : lmarcoss
--%>

<%@page import="entidades.MaderaAserradaClasif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    MaderaAserradaClasif maderaClasificacion = (MaderaAserradaClasif) request.getAttribute("mAClasif");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Actualizar</title>
        <script src="/aserradero/js/actualizarVolMaderaClasif.js"></script>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
         <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/MaderaAserradaClasifController?action=actualizar" method="post" id="formregistro">
                <h3>Modificar datos de madera de producción</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Madera:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_madera" value="<%=maderaClasificacion.getId_madera()%>" title="sólo lectura" required="" readonly=""></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Grueso:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="grueso" id="grueso" step=".01" min="0.01" max="9999.99" value="<%=maderaClasificacion.getGrueso()%>"  title="Sólo números" required="" onblur="actualizarVolumenMaderaClasif()"/></td>
                        </tr>
                        
                        <tr>
                            <td style="padding-left: 10px;"><label>Ancho:</label></td>
                            <td style="padding-left: 10px;"><input type="number"  name="ancho" step=".01" min="0.01" max="9999.99" id="ancho" value="<%=maderaClasificacion.getAncho()%>" title="Sólo números" required="" onblur="actualizarVolumenMaderaClasif()"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Largo:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="largo" id="largo" step=".01" min="0.01" max="9999.99" value="<%=maderaClasificacion.getAncho()%>" title="Sólo números" required="" onblur="actualizarVolumenMaderaClasif()"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="volumen" id="volumen" step=".001" min="0.001" max="99999.999" value="<%=maderaClasificacion.getVolumen()%>" title="Sólo números" readonly="" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Costo por volumen</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="costo_por_volumen" step=".001" min="0.01" max="99999.99" value="<%=maderaClasificacion.getCosto_por_volumen()%>" title="Sólo números" required="" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/MaderaAserradaClasifController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->  
    </body>
</html>