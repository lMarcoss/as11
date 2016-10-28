<%-- 
    Document   : inventarioMaderaProducciones
    Created on : 28/09/2016, 04:11:42 PM
    Author     : Marcos
--%>

<%@page import="entidades.InventarioMaderaProduccion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <InventarioMaderaProduccion> inventarioMaderaProducciones = (List<InventarioMaderaProduccion>) request.getAttribute("inventarioMaderaProducciones");
    String mensaje=(String) request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Personas</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/InventarioMaderaProduccionController?action=buscar">
                <table class="table-condensed">
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_madera">Id madera</option>
                            <option value="num_piezas">Num piezas</option>
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
                        <th>Id madera</th>
                        <th>Piezas </th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    <%
                       
                        int i=0;
                        for (InventarioMaderaProduccion inventarioMaderaProduccion : inventarioMaderaProducciones) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td><a href=\"/aserradero/MaderaClasificacionController?action=buscar_maderaClasificacion&id_madera="+inventarioMaderaProduccion.getId_madera()+"\">"+inventarioMaderaProduccion.getId_madera()+"</a></td>"
                                +"<td>"+inventarioMaderaProduccion.getNum_piezas()+"</td>"
                                
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/InventarioMaderaProduccionController?action=eliminar&id_madera="+inventarioMaderaProduccion.getId_madera()+"';};\">Eliminar</a></td>"
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
