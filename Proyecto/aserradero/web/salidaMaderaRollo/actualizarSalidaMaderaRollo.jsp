<%-- 
    Document   : actualizarSalidaMaderaRollo
    Created on : 28/10/2016, 05:09:40 PM
    Author     : li-906
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="entidades.InventarioMaderaRollo"%>
<%@page import="java.util.List"%>
<%@page import="entidades.SalidaMaderaRollo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SalidaMaderaRollo salida = (SalidaMaderaRollo) request.getAttribute("salidaMaderaRollo");
    List <InventarioMaderaRollo> inventarioMR = (List<InventarioMaderaRollo>) request.getAttribute("inventarioMR");
    int pieza_existente = 0;
    BigDecimal volumen_existente = BigDecimal.valueOf(0.000);
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Actualizar</title>
        <script src="/aserradero/js/actualizarSalidaMaderaRollo.js"></script>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <div>
            <!-- ******************* Formulario de registro-->
            <form action="/aserradero/SalidaMaderaRolloController?action=actualizar" method="POST">
                <h3>Modificar salida madera rollo</h3>
                <fieldset id="user-details">
                    <table>
                        <input type="hidden" name="id_salida" value="<%=salida.getId_salida()%>" readonly=""/>
                        <input type="hidden" name="pieza_actualizar" id="pieza_actualizar" value="<%=salida.getNum_piezas()%>" readonly=""/>
                        <input type="hidden" name="volumen_actualizar" id="volumen_actualizar" value="<%=salida.getVolumen_total()%>" readonly=""/>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=salida.getFecha()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el datospersona que busca, primero agreguelo en la lista de datospersona">
                                            <option value="<%=salida.getId_empleado()%>" selected=""><%=salida.getEmpleado()%></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Piezas en existencia</label></td>
                            <td style="padding-left: 10px;">
                                <select name="pieza_existente" id="pieza_existente" disabled="">
                                    <%
                                        for (InventarioMaderaRollo inventario : inventarioMR) {
                                            out.print("<option value='"+inventario.getNum_piezas()+"'>"+inventario.getNum_piezas()+"</option>");
                                            pieza_existente = inventario.getNum_piezas();
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen existente</label></td>
                            <td style="padding-left: 10px;">
                                <select name="volumen_existente" id="volumen_existente" disabled="">
                                    <%
                                        for (InventarioMaderaRollo inventario : inventarioMR) {
                                            out.print("<option value='"+inventario.getVolumen_total()+"'>"+inventario.getVolumen_total()+"</option>");
                                            volumen_existente = inventario.getVolumen_total();
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Num. piezas</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" id="num_piezas" value="<%=salida.getNum_piezas()%>" min="1" max="9999" required="" onblur="actualizarVolumenPermitido(),actualizarCantidadPiezasPermitida()"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen total</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".001" name="volumen_total" id="volumen_total" value="<%=salida.getVolumen_total()%>" min="0.001" max="9999999.999" required="" onblur="actualizarCantidadPiezasPermitida(),actualizarVolumenPermitido()"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/SalidaMaderaRolloController?action=listar_salida"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
