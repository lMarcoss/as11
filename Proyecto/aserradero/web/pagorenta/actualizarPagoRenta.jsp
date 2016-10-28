<%-- 
    Document   : actualizarPagoRenta
    Created on : 25/09/2016, 10:15:35 PM
    Author     : rcortes
--%>

<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page import="entidades.PagoRenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%PagoRenta pagorenta = (PagoRenta) request.getAttribute("pagorenta");%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/PagoRentaController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_pago_renta">Id pago de renta</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_pago_renta" value="<%= pagorenta.getId_pago_renta()%>"  readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" value="<%=pagorenta.getFecha()%>" /></td> 
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="nombre_persona">Nombre</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_persona" value="<%=pagorenta.getNombre_persona()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_empleado">Id empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el empleado que busca, primero agreguelo en la lista de empleados">                                    
                                    <%
                                        List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
                                        try{
                                            for (Empleado empleado : empleados) {
                                                if(pagorenta.getId_empleado().equals(empleado.getId_empleado())){
                                                    out.print("<option selected=\"selected\" value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");   
                                                }else
                                                {
                                                    out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");   
                                                }
                                        }
                                        }catch(Exception e){
                                            System.out.println(e);
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>                        
                        <tr>
                            <td style="padding-left: 10px;"><label for="monto">Monto</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.01" name="monto" value="<%=pagorenta.getMonto()%>" min="0.0" max="99999999.99"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="observacion">Observacion</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="observacion" value="<%=pagorenta.getObservacion()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PagoRentaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
