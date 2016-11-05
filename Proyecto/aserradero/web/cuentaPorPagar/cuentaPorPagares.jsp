<%-- 
    Document   : cuentaPorPagares
    Created on : 27/09/2016, 09:02:40 AM
    Author     : Marcos
--%>

<%@page import="entidades.CuentaPorPagar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <CuentaPorPagar> cuentaPorPagares = (List<CuentaPorPagar>) request.getAttribute("cuentaPorPagares");
    String mensaje=(String) request.getAttribute("mensaje");
    String tabla=(String) request.getAttribute("tabla");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Cuentas por pagar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        <input type="hidden" name="tabla" id="tabla" value="<%=tabla%>"
        
        <!-- ************************** opción de búsqueda-->
        <form method="POST" action="/aserradero/CuentaPorPagarController?action=buscar&tabla=<%=tabla%>">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value='"<%=tabla%>"'><%=tabla%></option>
                            <option value="monto">Monto</option>
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
                        <th><%=tabla%></th>
                        <th>Monto </th>
                    </tr>
                    <%
                        int i=0;
                        for (CuentaPorPagar cuentaPorPagar : cuentaPorPagares) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+cuentaPorPagar.getId_persona()+"\">"+cuentaPorPagar.getPersona()+"</a></td>"    
                                +"<td>"+cuentaPorPagar.getMonto()+"</td>"
//                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/CuentaPorPagarController?action=eliminar&id_cliente="+cuentaPorPagar.getId_persona()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
