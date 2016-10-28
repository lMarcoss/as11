<%--
    Document   : maderaentradas
    Created on : 26/09/2016, 11:48:27 PM
    Author     : rcortes
--%>

<%@page import="entidades.InventarioMaderaEntrada"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <InventarioMaderaEntrada> inventarioMaderaEntradas = (List<InventarioMaderaEntrada>) request.getAttribute("inventarioMaderaEntradas");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Maderas en rollo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
            
        <% if(!inventarioMaderaEntradas.isEmpty()){%>
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/MaderaEntradaController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                                <option value="clasificacion">Clasificacion</option>
                                <option value="volumen">Volumen</option>
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
                        <th>Número de piezas</th>                        
                        <th>Volumen total</th>
                    </tr>
                    <%
                        int i=0;
                        for (InventarioMaderaEntrada inventariomaderaentrada : inventarioMaderaEntradas) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+inventariomaderaentrada.getNum_piezas()+"</td>"                                
                                +"<td>"+inventariomaderaentrada.getVolumen_total()+"</td>"                                
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>            
        </div><!-- Resultado Consulta-->
        <%}else{
            out.print("<p>No hay inventarios</p>");
        }%>
    </body>
</html>
