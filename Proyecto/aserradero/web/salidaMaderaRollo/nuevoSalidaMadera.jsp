<%-- 
    Document   : nuevoSalidaMadera
    Created on : 27-oct-2016, 20:43:44
    Author     : lmarcoss
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="entidades.InventarioMaderaRollo"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="entidades.Empleado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
    List <InventarioMaderaRollo> inventarioMR = (List<InventarioMaderaRollo>) request.getAttribute("inventarioMR");
    Date fechaActual = (Date) request.getAttribute("fechaActual");
    int pieza_existente = 0;
    BigDecimal volumen_existente = BigDecimal.valueOf(0.000);
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Nuevo</title>
        <script src="/aserradero/js/salidaMaderaRollo.js"></script>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        <div>
        <%if(!inventarioMR.isEmpty()){%>
            <!-- ******************* Formulario de registro-->
            <form action="/aserradero/SalidaMaderaRolloController?action=nuevo_salida" method="POST">
                <h3>Registrar salida</h3>
                <fieldset id="user-details">
                    <table>
                        <input type="hidden" name="id_salida" value="1"/>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=fechaActual%>" onblur="salidaMaderaRolloPermitido()"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el datospersona que busca, primero agreguelo en la lista de datospersona">
                                    <option></option>
                                    <%
                                        for (Empleado empleado : empleados) {
                                            out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");
                                        }
                                    %>
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
                            <td style="padding-left: 10px;"><label>Num. piezas (salida)</label></td>
                            <td style="padding-left: 10px;"><input type="number" name="num_piezas" id="num_piezas" min="1" max="" required="" onblur="salidaVolumenMinimoPermitido(),salidaMaderaRolloPermitido()"></td>
                        <tr>
                            <td style="padding-left: 10px;"><label>Volumen total (salida)</label></td>
                            <td style="padding-left: 10px;"><input type="number" step=".001" name="volumen_total" id="volumen_total" min="" max="" required="" onblur="salidaMaderaRolloPermitido(),salidaVolumenMinimoPermitido()"></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/SalidaMaderaRolloController?action=listar_salida"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        <%}else{%>
            <br>
            <br>
            <h2>No se puede registrar salida, No hay inventario</h2>
            <a href="/aserradero/SalidaMaderaRolloController?action=listar_salida"><input type="button" value="Aceptar"/></a>
        <%}%>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
