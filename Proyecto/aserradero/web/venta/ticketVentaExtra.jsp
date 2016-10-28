<%-- 
    Document   : ticketVentaExtra
    Created on : 02-oct-2016, 15:56:54
    Author     : lmarcoss
--%>

<%@page import="ticketVenta.DatosClienteTicket"%>
<%@page import="ticketVenta.DatosVentaExtra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <DatosVentaExtra> listaDatosVentaExtra = (List<DatosVentaExtra>) request.getAttribute("listaDatosVentaExtra");
    DatosClienteTicket datosCliente = (DatosClienteTicket)request.getAttribute("datosCliente");
    String monto_total = (String) request.getAttribute("monto_total");    
    String tipo_ticket = (String) request.getAttribute("tipo_ticket");    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ticket venta extra</title>
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
            <div class="table-responsive" style="width: 50%;">
                <table class="table table-hover">
                    <tr>
                        <th>Tipo</th>
                        <th>observacion</th>
                        <th>monto</th>
                    </tr>
                    <%
                        for(DatosVentaExtra datosVE: listaDatosVentaExtra){
                            out.print("<tr>");
                            out.print("<td>"+datosVE.getTipo()+"</td>");
                            out.print("<td>"+datosVE.getObservacion()+"</td>");
                            if(tipo_ticket.equals("costo")){
                                out.print("<td>"+datosVE.getMonto()+"</td>");
                            }
                            out.print("</tr>");
                        }
                   %>
                    <tr>
                        <td></td>
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
