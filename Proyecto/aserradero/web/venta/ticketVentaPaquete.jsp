<%-- 
    Document   : ticketVentaPaquete
    Created on : 02-oct-2016, 15:56:42
    Author     : lmarcoss
--%>

<%@page import="ticketVenta.DatosClienteTicket"%>
<%@page import="ticketVenta.Madera"%>
<%@page import="ticketVenta.Paquete"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Paquete> listaPaquetes = (List<Paquete>) request.getAttribute("listaPaquetes");
    DatosClienteTicket datosCliente = (DatosClienteTicket)request.getAttribute("datosCliente");
    String monto_total = (String) request.getAttribute("monto_total");
    String tipo_ticket = (String) request.getAttribute("tipo_ticket");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ticket venta por paquete</title>
        <link rel="stylesheet" href="/aserradero/css/menu/bootstrap.css">
<!--        <script type="text/javascript">
            window.onunload = window.onbeforeunload = function(){
                document.location.target = "_blank";
                document.location.href="/aserradero/VentaController?action=listar";
            };
        </script>-->
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
                        <th>Paquete</th>
                        <th>Madera</th>
                        <th>Grueso</th>
                        <th>Ancho</th>
                        <th>Largo</th>
                        <th>Volumen</th>
                        <th>N° piezas</th>
                        <th>Vol. total</th>
                        <th>Costo volumen</th>
                        <th>Total</th>
                    </tr>
                    <%
                    for(Paquete paquete: listaPaquetes){
                        out.print("<tr>");
                        out.print("<td rowspan='"+(paquete.getListaMadera().size()+1)+"'>"+paquete.getNumero_paquete()+"</td>");
                        out.print("</tr>");
                        for(Madera madera:paquete.getListaMadera()){
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
                        out.print("<tr>"
                                + "<td colspan='8'></td>"
                                + "<td>subtotal: </td>");
                        if(tipo_ticket.equals("costo")){
                            out.print("<td>"+paquete.getMonto_total_paquete()+"</td>");
                        }
                        out.print("</tr>");
                        
                    }
                   %>
                    <tr>
                        <td colspan="8"></td>
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
