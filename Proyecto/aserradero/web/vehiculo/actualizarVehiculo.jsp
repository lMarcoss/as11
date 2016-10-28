<%--
    Document   : actualizarVehiculo
    Created on : 26/09/2016, 05:30:15 PM
    Author     : rcortes
--%>

<%@page import="entidades.Empleado"%>
<%@page import="ClasesCompuestas.DatosPersona"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Vehiculo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Vehiculo vehiculo = (Vehiculo) request.getAttribute("vehiculo");
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
%>
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
            <form action="/aserradero/VehiculoController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                      <tr>
                          <td style="padding-left: 10px;"><label for="id_vehiculo">Id vehículo</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="id_vehiculo" required="" readonly="" value="<%=vehiculo.getId_vehiculo()%>"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="matricula">Matrícula</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="matricula" value="<%=vehiculo.getMatricula()%>"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="tipo">Tipo</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="tipo" value="<%=vehiculo.getTipo()%>"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="color">Color</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="color" value="<%=vehiculo.getColor()%>"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="carga_admitida">Carga admitida</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="carga_admitida" value="<%=vehiculo.getCarga_admitida()%>"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="motor">Motor</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="motor" value="<%=vehiculo.getMotor()%>"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="modelo">Modelo</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="modelo" value="<%=vehiculo.getModelo()%>"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="costo">Costo</label></td>
                          <td style="padding-left: 10px;"><input type="number" step=".01" name="costo" value="<%=vehiculo.getCosto()%>" min="0.0"/></td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="id_empleado">Id empleado</label></td>
                          <td style="padding-left: 10px;">
                              <select name="id_empleado" required="" title="Si no existe el empleado que busca, primero agreguelo en la lista de empleados">                                    
                                    <%
                                        
                                        try{
                                            for (Empleado empleado : empleados) {
                                                if(vehiculo.getId_empleado().equals(empleado.getId_empleado())){
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
                            <td style="padding-left: 10px;"><a href="/aserradero/VehiculoController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
