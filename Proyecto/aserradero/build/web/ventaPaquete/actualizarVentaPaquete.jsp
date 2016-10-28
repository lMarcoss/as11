<%-- 
    Document   : actualizarVentaPaquete
    Created on : 28-sep-2016, 9:56:38
    Author     : lmarcoss
--%>

<%@page import="entidades.VentaPaquete"%>
<%@page import="entidadesVirtuales.CostoMaderaClasificacion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <CostoMaderaClasificacion> costoMaderaClasificaciones = (List<CostoMaderaClasificacion>) request.getAttribute("costoMaderaClasificaciones");
    VentaPaquete ventaPaquete = (VentaPaquete) request.getAttribute("ventaPaquete");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!--formulario de registro-->
        <div>
            <form action="/aserradero/VentaPaqueteController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar venta paquete</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id venta:</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_venta" value="<%=ventaPaquete.getId_venta()%>" readonly=""/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Número de Paquete</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="numero_paquete" value="<%=ventaPaquete.getNumero_paquete()%>" min="1" max="99999999999" title="Sólo número" readonly=""/></td>
                            <td></td>
                        </tr>
                        <%
                            for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                if(costoMaderaClasificacion.getId_madera().equals(ventaPaquete.getId_madera())){

                                    out.print("<tr><td style='padding-left: 10px;'><label>Madera:</label></td><td style='padding-left: 10px;'><input name='id_madera' id='id_madera' value='"+costoMaderaClasificacion.getId_madera()+"' readonly=''/></td></tr>");
                                    out.print("<tr><td>volumen unitaria<select name='volumen_unitaria' id='volumen_unitaria' readonly=''><option value='"+costoMaderaClasificacion.getVolumen()+"'>"+costoMaderaClasificacion.getVolumen()+"</option></select></td>");
                                    out.print("<td>Costo volumen<select name='costo_volumen' id='costo_volumen' readonly=''><option value='"+costoMaderaClasificacion.getMonto_volumen()+"'>"+costoMaderaClasificacion.getMonto_volumen()+"</option></select></td></tr>");
                                }
                            }
                        %>
                        <tr>
                            <td style="padding-left: 10px;"><label>Número de piezas:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" id="num_piezas" value="<%=ventaPaquete.getNum_piezas()%>" min="1" max="9999" required="" title="Escribe la cantidad de piezas" onblur="calcularVolumenTotal()"/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="volumen" id="volumen" value="<%=ventaPaquete.getVolumen()%>" step="0.001" min="0.001" max="99999.999"  required="" readonly=""/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="monto" id="monto" value="<%=ventaPaquete.getMonto()%>" step="0.01" min="0.01" max="99999999.99"  required="" readonly=""/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/VentaPaqueteController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
