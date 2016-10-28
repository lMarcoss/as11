<%-- 
    Document   : clientes
    Created on : 27-sep-2016, 1:03:55
    Author     : lmarcoss
--%>

<%@page import="entidades.Cliente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Clientes</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"/>
        
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/ClienteController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="cliente">Cliente</option>
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
                        <th>Cliente</th>
                        <th>Jefe</th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (Cliente cliente : clientes) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+cliente.getId_cliente()+"\">"+cliente.getCliente()+"</a></td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+cliente.getId_jefe()+"\">"+cliente.getJefe()+"</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/ClienteController?action=eliminar&id_cliente="+cliente.getId_cliente()+"&id_jefe="+cliente.getId_jefe()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar cliente" onClick=" window.location.href='/aserradero/ClienteController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
