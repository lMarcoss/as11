<%-- 
    Document   : anticipoProveedores
    Created on : 27/09/2016, 01:14:24 PM
    Author     : Marcos
--%>

<%@page import="entidades.AnticipoProveedor"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <AnticipoProveedor> anticipoProveedores = (List<AnticipoProveedor>) request.getAttribute("anticipoProveedores");
    String mensaje=(String) request.getAttribute("mensaje");
%>

<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Anticipo proveedores</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/AnticipoProveedorController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_anticipo_p">Id anticipo</option>
                            <option value="fecha">Fecha</option>
                            <option value="id_proveedor">Id proveedor</option>
                            <option value="id_empleado">Id empleado</option>
                            <option value="monto_anticipo">Monto anticipo</option>
                        </select>
                        </td>
                        <td><input type="text" name="dato" placeholder="Escriba su búsqueda"></td>
                        <td colspan="2"><input type="submit" value="Buscar"></td>
                    </tr>
                </table>
            </form>
        </div> <!-- Fin opción de búsqueda-->
        
        <!-- ************************* Resultado Consulta-->
        <div>
            <table class="table-condensed">
                    <tr>
                        <th>N°</th>
                        <th>Id anticipo</th>
                        <th>Fecha</th>
                        <th>Id proveedor</th>
                        <th>Id empleado</th>
                        <th>Monto anticipo </th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    <%
                        int i=0;
                        for (AnticipoProveedor anticipoProveedor : anticipoProveedores) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+anticipoProveedor.getId_anticipo_p()+"</td>"
                                +"<td>"+anticipoProveedor.getFecha()+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+anticipoProveedor.getId_proveedor()+"\">"+anticipoProveedor.getId_proveedor()+"</a></td>"    
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+anticipoProveedor.getId_empleado()+"\">"+anticipoProveedor.getId_empleado()+"</a></td>"
                                +"<td>"+anticipoProveedor.getMonto_anticipo()+"</td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/AnticipoProveedorController?action=eliminar&id_anticipo_p="+anticipoProveedor.getId_anticipo_p()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar anticipo proveedor" onClick=" window.location.href='/aserradero/AnticipoProveedorController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
