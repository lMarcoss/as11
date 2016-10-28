<%-- 
    Document   : nuevoPagoRenta
    Created on : 25/09/2016, 09:32:46 PM
    Author     : rcortes
--%>

<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%String error = (String)request.getAttribute("error");%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
            <form action="/aserradero/PagoRentaController?action=nuevo" method="POST">
                <h3>Agregar nuevo pago de renta</h3>
                <fieldset id="user-details">
                    <table>                        
                        <tr>
                            <td style="padding-left: 10px;"><label for="fecha">Fecha</label></td>
                            <td style="padding-left: 10px;"><input type="date" name="fecha" required="" placeholder="AAAA/MM/DD"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="nombre_persona">Nombre</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="nombre_persona"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="id_empleado">Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" title="Si no existe el empleado que busca, primero agreguelo en la lista de empleado">
                                    <option></option>
                                    <%
                                        List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");                                        
                                        try{
                                            for (Empleado empleado : empleados) {
                                            out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");
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
                            <td style="padding-left: 10px;"><input type="number" step=".01" name="monto" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label for="observacion">Observacion</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="observacion" /></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/PagoRentaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->  
    </body>
</html>
