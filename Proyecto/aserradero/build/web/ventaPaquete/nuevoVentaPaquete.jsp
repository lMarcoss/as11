<%-- 
    Document   : nuevoProduccionDetalle
    Created on : 28-sep-2016, 9:56:07
    Author     : lmarcoss
--%>

<%@page import="entidadesVirtuales.CostoMaderaClasificacion"%>
<%@page import="entidades.MaderaClasificacion"%>
<%@page import="entidades.Venta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Venta> ventas = (List<Venta>) request.getAttribute("ventas");
    List <CostoMaderaClasificacion> costoMaderaClasificaciones = (List<CostoMaderaClasificacion>) request.getAttribute("costoMaderaClasificaciones");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!--formulario de registro-->
        <div>
            <form action="/aserradero/VentaPaqueteController?action=nuevo" method="post" id="formregistro">
                <h3>Registrar venta por paquete</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id venta:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_venta" required="" title="Si no existe el id venta, primero agregalo a la lista de ventas con tipo venta Paquete">
                                    <option></option>
                                    <%
                                        for (Venta venta : ventas) {
                                            out.print("<option value='"+venta.getId_venta()+"'>"+venta.getId_venta()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Número de Paquete</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="numero_paquete" min="1" max="99999999999" title="Sólo número" required=""/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Madera:</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_madera" required="" id="id_madera" onblur="seleccionarCostoMaderaVenta()">
                                    <option></option>
                                    <%
                                        for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                            out.print("<option value='"+costoMaderaClasificacion.getId_madera()+"'>"+costoMaderaClasificacion.getId_madera()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>volumen unitaria
                                <select name="volumen_unitaria" id="volumen_unitaria" readonly="" disabled="">
                                    <option></option>
                                    <%
                                        for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                            out.print("<option value='"+costoMaderaClasificacion.getVolumen()+"'>"+costoMaderaClasificacion.getVolumen()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                            <td>Costo volumen
                                <select name="costo_volumen" id="costo_volumen" readonly="" disabled="">
                                    <option></option>
                                    <%
                                        for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                            out.print("<option value='"+costoMaderaClasificacion.getMonto_volumen()+"'>"+costoMaderaClasificacion.getMonto_volumen()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Número de piezas:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" id="num_piezas" min="1" max="9999" required="" title="Escribe la cantidad de piezas" onblur="calcularVolumenTotal()"/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="volumen" id="volumen" step="0.001" min="0.001" max="99999.999"  required="" readonly=""/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Monto:</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="monto" id="monto" step="0.01" min="0.01" max="99999999.99"  required="" readonly=""/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/VentaPaqueteController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                            <td></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
