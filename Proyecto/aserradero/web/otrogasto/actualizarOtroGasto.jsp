<%--
    Document   : actualizarOtroGasto
    Created on : 26/09/2016, 03:36:38 PM
    Author     : rcortes
--%>
<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page import="entidades.OtroGasto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%OtroGasto otrogasto = (OtroGasto) request.getAttribute("otrogasto");%>
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
            <form action="/aserradero/OtroGastoController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_gasto">Id gasto</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_gasto" value="<%= otrogasto.getId_gasto()%>"  readonly=""/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="fecha" value="<%=otrogasto.getFecha()%>" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_empleado">Id empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el empleado que busca, primero agreguelo en la lista de empleados">                                    
                                    <%
                                        List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
                                        try{
                                            for (Empleado empleado : empleados) {
                                                if(otrogasto.getId_empleado().equals(empleado.getId_empleado())){
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
                            <td style="padding-left: 10px;"><label for="nombre_gasto">Nombre gasto</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_gasto" value="<%=otrogasto.getNombre_gasto()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="monto">Monto</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="monto" value="<%=otrogasto.getMonto()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="observacion">Observacion</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="observacion" value="<%=otrogasto.getObservacion()%>"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/OtroGastoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
