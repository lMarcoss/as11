<%-- 
    Document   : ticketVentaPaquete
    Created on : 02-oct-2016, 15:56:42
    Author     : lmarcoss
--%>

<%@page import="entidades.venta.VentaPaquete"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="ticketVenta.DatosClienteTicket"%>
<%@page import="ticketVenta.Madera"%>
<%@page import="ticketVenta.Paquete"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DatosClienteTicket datosCliente = (DatosClienteTicket) request.getAttribute("datosCliente");
    //listas
    List<Paquete> listaPaquetesMadera = (List<Paquete>) request.getAttribute("listaPaquetesMadera");
    List<Paquete> listaPaquetesAmarre = (List<Paquete>) request.getAttribute("listaPaquetesAmarre");
    List<VentaPaquete> listaMadera = (List<VentaPaquete>) request.getAttribute("listaMadera");
    //Costos
    BigDecimal costo_madera = (BigDecimal) request.getAttribute("costo_madera");
    BigDecimal costo_amarre = (BigDecimal) request.getAttribute("costo_amarre");
    BigDecimal costo_venta = (BigDecimal) request.getAttribute("costo_venta");
    //Volumen
    BigDecimal volumen_madera = (BigDecimal) request.getAttribute("volumen_madera");
    BigDecimal volumen_amarre = (BigDecimal) request.getAttribute("volumen_amarre");
    BigDecimal volumen_venta = (BigDecimal) request.getAttribute("volumen_venta");
    String id_venta = (String) request.getAttribute("id_venta");
    String tipo_ticket = (String) request.getAttribute("tipo_ticket");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ticket venta por paquete</title>
        <link rel="stylesheet" href="/aserradero/dist/css/bootstrap.css">
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
                    <tr>
                        <td>Id_venta: <%= id_venta%></td>
                        <td></td>
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
                        <th>Vol. unitario</th>
                        <th>Costo volumen</th>
                        <th>N° piezas</th>
                        <th>Vol. total</th>
                        <th>Total</th>
                    </tr>
                    <%
                        // Lista de madera tipo Madera
                        for (Paquete paquete : listaPaquetesMadera) {
                            out.print("<tr>");
                            out.print("<td rowspan='" + (paquete.getListaMadera().size() + 1) + "'>" + paquete.getNumero_paquete() + "</td>");
                            out.print("</tr>");
                            for (Madera madera : paquete.getListaMadera()) {
                                out.print("<tr>");
                                out.print("<td>" + madera.getId_madera() + "</td>");
                                out.print("<td>" + madera.getGrueso_f() + "</td>");
                                out.print("<td>" + madera.getAncho_f() + "</td>");
                                out.print("<td>" + madera.getLargo_f() + "</td>");
                                out.print("<td>" + madera.getVolumen_unitario() + "</td>");
                                if (tipo_ticket.equals("costo")) {
                                    out.print("<td>" + madera.getCosto_volumen() + "</td>");
                                } else {
                                    out.print("<td></td>");
                                }
                                out.print("<td>" + madera.getNum_piezas() + "</td>");
                                out.print("<td>" + madera.getVolumen_total() + "</td>");
                                if (tipo_ticket.equals("costo")) {
                                    out.print("<td>" + madera.getCosto_total() + "</td>");
                                } else {
                                    out.print("<td></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("<tr>"
                                    + "<td colspan='7'></td>"
                                    + "<td>subtotal(Paquete): </td>");
                            out.print("<td>" + paquete.getVolumen_total_paquete() + "</td>");
                            if (tipo_ticket.equals("costo")) {
                                out.print("<td>" + paquete.getMonto_total_paquete() + "</td>");
                            } else {
                                out.print("<td></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr>"
                                + "<td colspan='7'></td>"
                                + "<td><b>subtotal (Madera): </b></td>");
                        out.print("<td><b>" + volumen_madera + "</b></td>");
                        if (tipo_ticket.equals("costo")) {
                            out.print("<td><b>" + costo_madera + "</b></td>");
                        } else {
                            out.print("<td></td>");
                        }
                        out.print("</tr>");
                    %>

                    <%
                        if (!listaPaquetesAmarre.isEmpty()) {
                            out.print("<tr>");
                            out.print("<td colspan='10'><center><b>Amarre</b></center></td>");
                            out.print("</tr>");

                            // Lista de madera tipo amarre
                            for (Paquete paquete : listaPaquetesAmarre) {
                                out.print("<tr>");
                                out.print("<td rowspan='" + (paquete.getListaMadera().size() + 1) + "'>" + paquete.getNumero_paquete() + "</td>");
                                out.print("</tr>");
                                for (Madera madera : paquete.getListaMadera()) {
                                    out.print("<tr>");
                                    out.print("<td>" + madera.getId_madera() + "</td>");
                                    out.print("<td>" + madera.getGrueso_f() + "</td>");
                                    out.print("<td>" + madera.getAncho_f() + "</td>");
                                    out.print("<td>" + madera.getLargo_f() + "</td>");
                                    out.print("<td>" + madera.getVolumen_unitario() + "</td>");
                                    if (tipo_ticket.equals("costo")) {
                                        out.print("<td>" + madera.getCosto_volumen() + "</td>");
                                    } else {
                                        out.print("<td></td>");
                                    }
                                    out.print("<td>" + madera.getNum_piezas() + "</td>");
                                    out.print("<td>" + madera.getVolumen_total() + "</td>");
                                    if (tipo_ticket.equals("costo")) {
                                        out.print("<td>" + madera.getCosto_total() + "</td>");
                                    } else {
                                        out.print("<td></td>");
                                    }
                                    out.print("</tr>");
                                }
                                out.print("<tr>"
                                        + "<td colspan='7'></td>"
                                        + "<td>subtotal (Paquete): </td>");
                                out.print("<td>" + paquete.getVolumen_total_paquete() + "</td>");
                                if (tipo_ticket.equals("costo")) {
                                    out.print("<td>" + paquete.getMonto_total_paquete() + "</td>");
                                } else {
                                    out.print("<td></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("<tr>"
                                    + "<td colspan='7'></td>"
                                    + "<td><b>subtotal (Amarre): </b></td>");
                            out.print("<td><b>" + volumen_amarre + "</b></td>");
                            if (tipo_ticket.equals("costo")) {
                                out.print("<td><b>" + costo_amarre + "</b></td>");
                            } else {
                                out.print("<td></td>");
                            }
                            out.print("</tr>");
                        }
                    %>
                    <tr>
                        <td colspan="7"></td>
                        <td><b>Total:</b></td>
                        <%
                            out.print("<td><b>" + volumen_venta + "</b></td>");
                            if (tipo_ticket.equals("costo")) {
                                out.print("<td><b>" + costo_venta + "</b></td>");
                            } else {
                                out.print("<td></td>");
                            }
                        %>
                    </tr>
                </table>
                <%
                    if (!listaMadera.isEmpty()) {
                %>
                <div class="table-responsive" style="width: 50%;">
                    <table class="table table-hover">
                        <%
                            out.print("<tr>");
                            out.print("<td colspan='4'><center><b>Total madera por clasificación</b></center></td>");
                            out.print("</tr>");
                        %>
                        <tr>
                            <th>Madera</th>
                            <th>Num. piezas</th>
                            <th>Volumen total</th>
                            <th>Costo total</th>
                        </tr>
                        <%
                            // Lista de madera por clasificación
                            for (VentaPaquete madera : listaMadera) {
                                out.print("<tr>");
                                out.print("<td>" + madera.getId_madera() + "</td>");
                                out.print("<td>" + madera.getNum_piezas() + "</td>");
                                out.print("<td>" + madera.getVolumen() + "</td>");
                                if (tipo_ticket.equals("costo")) {
                                    out.print("<td>" + madera.getMonto() + "</td>");
                                } else {
                                    out.print("<td></td>");
                                }
                                out.print("</tr>");
                            }
                        %>
                        <tr>
                            <td></td>
                            <td><b>Total</b></td>
                            <td><b><%= volumen_venta%></b></td>
                            <%if (tipo_ticket.equals("costo")) {%>
                            <td><b><%= costo_venta%></b></td>
                            <%} else {%>
                            <td></td>
                            <%}%>
                        </tr>
                    </table>
                </div>
                <%}%>
            </div>
        </div>
    </body>
</html>
