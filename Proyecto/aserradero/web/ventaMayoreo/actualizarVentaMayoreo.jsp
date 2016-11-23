<%-- 
    Document   : actualizarVentaMayoreo
    Created on : 27-sep-2016, 12:36:56
    Author     : lmarcoss
--%>

<%@page import="entidadesVirtuales.CostoMaderaClasificacion"%>
<%@page import="java.util.List"%>
<%@page import="entidades.VentaMayoreo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <CostoMaderaClasificacion> costoMaderaClasificaciones = (List<CostoMaderaClasificacion>) request.getAttribute("costoMaderaClasificaciones");
    VentaMayoreo ventaMayoreo = (VentaMayoreo) request.getAttribute("ventaMayoreo");
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
        
        
        <div>
            <form action="/aserradero/VentaMayoreoController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar venta Mayoreo</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id venta:</label></td>
                            <td style="padding-left: 10px;">
                                <input type="text" name="id_venta" readonly="" value="<%=ventaMayoreo.getId_venta()%>"/>
                            </td>
                        </tr>
                        <%
                            for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                if(costoMaderaClasificacion.getId_madera().equals(ventaMayoreo.getId_madera())){

                                    out.print("<tr><td style='padding-left: 10px;'><label>Madera:</label></td><td style='padding-left: 10px;'><input name='id_madera' id='id_madera' value='"+costoMaderaClasificacion.getId_madera()+"' readonly=''/></td></tr>");
                                    out.print("<tr><td>volumen unitaria<select name='volumen_unitaria' id='volumen_unitaria' readonly=''><option value='"+costoMaderaClasificacion.getVolumen()+"'>"+costoMaderaClasificacion.getVolumen()+"</option></select></td>");
                                    out.print("<td>Costo volumen<select name='costo_volumen' id='costo_volumen' readonly=''><option value='"+costoMaderaClasificacion.getMonto_volumen()+"'>"+costoMaderaClasificacion.getMonto_volumen()+"</option></select></td></tr>");
                                }
                            }
                        %>
                        <tr>
                            <td style="padding-left: 10px;"><label>Número de piezas:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" id="num_piezas" value="<%=ventaMayoreo.getNum_piezas()%>" required="" title="Escribe la cantidad de piezas" onblur="calcularVolumenTotal()"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen:</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.001" min="0.001" max="99999.999" name="volumen" id="volumen" value="<%=ventaMayoreo.getVolumen()%>" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.001" min="0.001" max="99999999.99" name="monto" id="monto" value="<%=ventaMayoreo.getMonto()%>" required="" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/VentaMayoreoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
