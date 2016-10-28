<%-- 
    Document   : pagoCompras
    Created on : 28-sep-2016, 9:50:35
    Author     : lmarcoss
--%>

<%@page import="entidades.PagoCompra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <PagoCompra> pagoCompras = (List<PagoCompra>) request.getAttribute("pagoCompras");
    String mensaje=(String) request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Compras</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/PagoCompraController?action=buscar">
                <table class="table-condensed">
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="fecha">Fecha</option>
                            <option value="id_compra">Id compra</option>
                            <option value="id_pago">Id pago</option>
                            <option value="monto">Monto</option>
                        </select>
                        </td>
                        <td><input type="search" name="dato" placeholder="Escriba su búsqueda"></td>
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
                        <th>Fecha</th>
                        <th>Id compra</th>
                        <th>Monto</th>
                        <th>Pago</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (PagoCompra pagoCompra : pagoCompras) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+pagoCompra.getFecha()+"</td>"
                                +"<td><a href=\"/aserradero/CompraController?action=buscar_compra&id_compra="+pagoCompra.getId_compra()+"\">"+pagoCompra.getId_compra()+"</a></td>"    
                                +"<td>"+pagoCompra.getMonto()+"</td>"
                                +"<td>"+pagoCompra.getPago()+"</td>" );
                                if (pagoCompra.getPago()==null){
                                    out.print("<td><a href=\"/aserradero/PagoCompraController?action=modificar&fecha="+pagoCompra.getFecha()+"&id_compra="+pagoCompra.getId_compra()+"\">Pagar</a></td>");
                                }
                                out.print("<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/PagoCompraController?action=eliminar&id_compra="+pagoCompra.getId_compra()+"&fecha="+pagoCompra.getFecha()+"';};\">Eliminar</a></td></tr>");
                            i++;
                        }
                    %>
            </table>
            <!--Pago compra se agrega automáticamente al agregar compra -->
        </div><!-- Resultado Consulta-->
    </body>
</html>
