<%-- 
    Document   : detallesPrestamo
    Created on : 19-nov-2016, 15:18:29
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.Prestamo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Prestamo> prestamos = (List<Prestamo>) request.getAttribute("prestamos");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Préstamos</title>
        <script>
            $(document).ready(function ($){
                 $("#registros").css("background","#448D00");
                 $("#personas").css("background","#448D00");
            });
        </script>
        
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/PrestamoController?action=buscar_interes_total">
                <table class="table-condensed">
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="persona">Persona</option>
                            <option value="monto_total">Monto</option>
                            <option value="interes_total">Interés</option>
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
                        <th>Prestador</th>
                        <th>Monto total</th>
                        <th>Interes mensual</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (Prestamo prestamo : prestamos) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+prestamo.getPrestador()+"</td>"
                                +"<td>"+prestamo.getMonto()+"</td>"
                                +"<td>"+prestamo.getInteres_mesual()+"</td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Registrar préstamo" onClick=" window.location.href='/aserradero/PrestamoController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
    