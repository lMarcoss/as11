<%--
    Document   : actualizarPagoLuz
    Created on : 26/09/2016, 01:54:35 PM
    Author     : rcortes
--%>

<%@page import="ClasesCompuestas.DatosPersona"%>
<%@page import="java.util.List"%>
<%@page import="entidades.PagoLuz"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%PagoLuz pagoluz = (PagoLuz) request.getAttribute("pagoluz");%>
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
            <form action="/aserradero/PagoLuzController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_pago_luz">Id pago</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_pago_luz" value="<%= pagoluz.getId_pago_luz()%>"  readonly="" title="No puedes cambiar esto"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" required="" value="<%=pagoluz.getFecha()%>" readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_empleado">Id empleado</label></td>
                            <td style="padding-left: 10px;">
                                    <%
                                        List <DatosPersona> datospersonas = (List<DatosPersona>) request.getAttribute("empleados");
                                        for (DatosPersona datospersona : datospersonas) {
                                            if(pagoluz.getId_empleado().equals(datospersona.getId())){
                                                out.print("<input name='id_empleado' value='"+datospersona.getId()+"' readonly=''>");   
                                            }
                                        }
                                    %>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="monto">Monto</label></td>
                            <td style="padding-left: 10px;"><input type="number" step="0.01" name="monto" value="<%=pagoluz.getMonto()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="observacion">Observacion</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="observacion" value="<%=pagoluz.getObservacion()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PagoLuzController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
