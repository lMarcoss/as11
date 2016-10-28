<%-- 
    Document   : cuentaPorCobrares
    Created on : 28/09/2016, 12:13:27 AM
    Author     : Marcos
--%>

<%@page import="entidades.CuentaPorCobrar"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <CuentaPorCobrar> cuentaPorCobrares = (List<CuentaPorCobrar>) request.getAttribute("cuentaPorCobrares");
    String mensaje=(String) request.getAttribute("mensaje");
    String tabla=(String) request.getAttribute("tabla");
%>
<!DOCTYPE html>
<html>
     <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Cuentas por cobrar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        <input type="hidden" name="tabla" id="tabla" value="<%=tabla%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/CuentaPorCobrarController?action=buscar&tabla=<%=tabla%>">
                <table class="table-condensed">
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
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        
                        int i=0;
                        for (CuentaPorCobrar cuentaPorCobrar : cuentaPorCobrares) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+cuentaPorCobrar.getId_persona().substring(0, 18)+"\">"+cuentaPorCobrar.getPersona()+"</a></td>"
                                +"<td>"+cuentaPorCobrar.getMonto()+"</td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/CuentaPorCobrarController?action=eliminar&id_proveedor="+cuentaPorCobrar.getId_persona().substring(0, 18)+"';};\">Eliminar</a></td>"
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
