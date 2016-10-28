<%-- 
    Document   : costoMaderas
    Created on : 27-sep-2016, 15:50:30
    Author     : lmarcoss
--%>

<%@page import="entidades.CostoMadera"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <CostoMadera> costoMaderas = (List<CostoMadera>) request.getAttribute("costoMaderas");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Costo madera venta</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
            <form method="POST" action="/aserradero/CostoMaderaController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_madera">madera</option>
                            <option value="monto_volumen">Costo por volumen</option>
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
                        <th>Madera</th>
                        <th>Costo por volumen</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (CostoMadera costoMadera : costoMaderas) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/MaderaClasificacionController?action=buscar_maderaClasificacion&id_madera="+costoMadera.getId_madera()+"\">"+costoMadera.getId_madera()+"</a></td>"
                                +"<td>"+costoMadera.getMonto_volumen()+"</td>"
                                +"<td><a href=\"/aserradero/CostoMaderaController?action=modificar&id_madera="+costoMadera.getId_madera()+"\">Actualizar</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/CostoMaderaController?action=eliminar&id_madera="+costoMadera.getId_madera()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar nuevo costo madera venta" onClick=" window.location.href='/aserradero/CostoMaderaController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
