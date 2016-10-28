<%-- 
    Document   : ticketVentaMayoreo
    Created on : 02-oct-2016, 15:56:25
    Author     : lmarcoss
--%>

<%@page import="ticketVenta.DatosClienteTicket"%>
<%@page import="ticketVenta.Madera"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Madera> listaMaderaVendida = (List<Madera>) request.getAttribute("listaMaderaVendida");
    DatosClienteTicket datosCliente = (DatosClienteTicket)request.getAttribute("datosCliente");
    String monto_total = (String) request.getAttribute("monto_total");    
    String tipo_ticket = (String) request.getAttribute("tipo_ticket");    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ticket venta por mayoreo</title>
        <link rel="stylesheet" href="/aserradero/css/menu/bootstrap.css">
    </head>
    <body>
        <div class="container">
            <header style="text-align: center;">
                <p>ASERRADERO ANTONIO CORTES HERNÁNDEZ</p>
                <p>COMPRA-VENTA DE PRODUCTOS FORESTALES<p>
                <p>MADERA EN ROLLO Y ASERRADA</p>
            </header>
            <div>
                <table style="width: 100%;">
                    <tr>
                        <td>Cliente: <%= datosCliente.getCliente()%></td>
                        <td>Municipio: <%= datosCliente.getMunicipio()%></td>
                    </tr>
                    <tr>
                        <td>Dirección: <%= datosCliente.getDireccion()%></td>
                        <td>Estado: <%= datosCliente.getEstado()%></td>
                    </tr>
                    <tr>
                        <td>Localidad: <%= datosCliente.getLocalidad()%></td>
                        <td>Fecha: <%= datosCliente.getFecha()%></td>
                    </tr>
                </table>
            </div>
            <br>
            <div class="table-responsive">
                <table class="table table-hover">
                    <tr>
                        <th>Madera</th>
                        <th>Grueso</th>
                        <th>Ancho</th>
                        <th>Largo</th>
                        <th>Volumen</th>
                        <th>Num piezas</th>
                        <th>Volumen total</th>
                        <th>Costo volumen</th>
                        <th>Costo total</th>
                    </tr>
                    <%
                        for(Madera madera: listaMaderaVendida){
                            out.print("<tr>");
                            out.print("<td>"+madera.getId_madera()+"</td>");
                            out.print("<td>"+madera.getGrueso()+"</td>");
                            out.print("<td>"+madera.getAncho()+"</td>");
                            out.print("<td>"+madera.getLargo()+"</td>");
                            out.print("<td>"+madera.getVolumen_unitario()+"</td>");
                            out.print("<td>"+madera.getNum_piezas()+"</td>");
                            out.print("<td>"+madera.getVolumen_total()+"</td>");
                            if(tipo_ticket.equals("costo")){
                                out.print("<td>"+madera.getCosto_volumen()+"</td>");
                                out.print("<td>"+madera.getCosto_total()+"</td>");
                            }
                            out.print("</tr>");
                        }
                   %>
                    <tr>
                        <td colspan="6"></td>
                        <td>Total a pagar:</td>
                    <%
                        if(tipo_ticket.equals("costo")){
                            out.print("<td>"+monto_total+"</td>");
                        }
                    %>
                    </tr>
                </table>      
            </div>
        </div>
    </body>
</html>
