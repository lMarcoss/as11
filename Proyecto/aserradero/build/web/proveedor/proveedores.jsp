<%-- 
    Document   : proveedores
    Created on : 27-sep-2016, 0:12:12
    Author     : lmarcoss
--%>

<%@page import="entidades.Proveedor"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Proveedores</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/ProveedorController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="proveedor">Proveedor</option>
                            <option value="jefe">Jefe</option>
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
                        <th>Proveedor</th>
                        <th>Jefe</th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (Proveedor proveedor : proveedores) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+proveedor.getId_proveedor()+"\">"+proveedor.getProveedor()+"</a></td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+proveedor.getId_jefe()+"\">"+proveedor.getJefe()+"</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/ProveedorController?action=eliminar&id_proveedor="+proveedor.getId_proveedor()+"&id_jefe="+proveedor.getId_jefe()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar proveedor" onClick=" window.location.href='/aserradero/ProveedorController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
