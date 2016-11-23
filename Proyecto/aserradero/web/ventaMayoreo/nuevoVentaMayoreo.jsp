<%--
    Document   : nuevoVentaMayoreo
    Created on : 27-sep-2016, 12:36:30
    Author     : lmarcoss
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entidades.Cliente"%>
<%@page import="entidades.Empleado"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.Date"%>
<%@page import="entidadesVirtuales.CostoMaderaClasificacion"%>
<%@page import="entidades.Venta"%>
<%@page import="java.util.List"%>
<%@page import="entidades.VentaMayoreo"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Date fecha = Date.valueOf(LocalDate.now());
    List <Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
    List <Venta> ventas = (List<Venta>) request.getAttribute("ventas");
    String id_nVenta = String.valueOf(request.getAttribute("siguienteventa"));
    List <CostoMaderaClasificacion> costoMaderaClasificaciones = (List<CostoMaderaClasificacion>) request.getAttribute("costoMaderaClasificaciones");

%>
<%
    HttpSession sesion_ajax = request.getSession(true);
    sesion_ajax.setAttribute("detalle_venta_mayoreo", null);
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">NUEVA VENTA MAYOREO</h1>
                    </div>
                </div>
                <div class="col-md-12">
                  <div class="panel panel-primary"><!-- Panel principal -->
                      <div class="panel-heading">
                          <h3 class="panel-title">Rellene los campos de manera correcta</h3>
                      </div>
                      <div class="panel-body" id="PanelPrincipal">
                            <div class="col-lg-12">
                                <form action="/aserradero/VentaController?action=nuevo" method="post" id="formregistro"><!-- Formulario de venta -->
                                    <div class="form-group col-md-2"><!-- agrupar inputs -->
                                        <input name="tipo_venta" value="mayoreo" type="hidden"/>
                                        <label class="control-label">Fecha:</label>
                                        <input class="form-control" type="date" name="fecha" value="<%=fecha%>" required="" />
                                    </div>
                                    <div class="col-md-2 form-group">
                                        <label class="control-label">Id venta:</label>
                                        <input class="form-control" type="text" value="<%=id_nVenta%>" name="id_venta" id="id_venta" required="" title="escribe un identificador para la venta"/>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label class="control-label">Cliente</label>
                                        <select class="form-control" name="id_cliente" required="">
                                            <option></option>
                                            <%
                                                for (Cliente cliente : clientes) {
                                                    out.print("<option value='"+cliente.getId_cliente()+"'>"+cliente.getCliente()+"</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-md-2 form-group">
                                        <label class="control-label">Empleado:</label>
                                        <select class="form-control" name="id_empleado" required="">
                                        <option></option>
                                        <%
                                            for (Empleado empleado : empleados) {
                                                out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");
                                            }
                                        %>
                                      </select>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label class="control-label">Estatus:</label>
                                        <input  name="estatus" value="Sin pagar" id="estatus" class="form-control" required="" readonly=""/>
                                    </div>
                                     <div class="form-group pull-right col-md-2"><!-- agrupar inputs -->
                                        <input type="hidden" value="Mayoreo" name="tipo_venta" />
                                        <input type="submit" class="btn btn-block btn-success margen-boton" value="Guardar venta"/>
                                        <a href="/aserradero/VentaController?action=listar"><input class="btn btn-block btn-warning" type="button" value="Cancelar"/></a>
                                    </div><!-- Fin div group -->
                                </form><!-- Formulario de venta -->
                                <div class="col-lg-12">
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Madera:</label>
                                        <select class="form-control" name="id_madera" required="" id="id_madera" onblur="seleccionarCostoMaderaVenta()">
                                            <option></option>
                                            <%
                                                for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                                    out.print("<option value='"+costoMaderaClasificacion.getId_madera()+"'>"+costoMaderaClasificacion.getId_madera()+"</option>");
                                                }
                                            %>
                                        </select>
                                        <label class="control-label" >volumen unitario</label>
                                        <select name="volumen_unitaria" class="form-control" id="volumen_unitaria" readonly="" disabled="">
                                            <option></option>
                                            <%
                                                for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                                    out.print("<option value='"+costoMaderaClasificacion.getMonto_volumen()+"'>"+costoMaderaClasificacion.getMonto_volumen()+"</option>");
                                                    out.print("<option value='"+costoMaderaClasificacion.getVolumen()+"'>"+costoMaderaClasificacion.getVolumen()+"</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label" >Costo volumen</label>
                                        <select name="costo_volumen" class="form-control" id="costo_volumen" readonly="" disabled="">
                                            <option></option>
                                            <%
                                                for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                                    out.print("<option value='"+costoMaderaClasificacion.getMonto_volumen()+"'>"+costoMaderaClasificacion.getMonto_volumen()+"</option>");
                                                }
                                            %>
                                        </select>
                                        <label class="control-label" >Número de piezas:</label>
                                        <input type="number" class="form-control" name="num_piezas" id="num_piezas" min="1" max="999" required="" title="Escribe la cantidad de piezas" onblur="calcularVolumenTotal()"/>
                                    </div>
                                    <div class="col-md-3 form-group">
                                        <label class="control-label" >Volumen:</label>
                                        <input class="form-control" type="number" name="volumen" id="volumen" step="0.001" min="0.001" max="99999.999" required="" readonly="" disabled=""/>
                                        <label class="control-label" >Monto:</label>
                                        <input type="number" name="monto" class="form-control" id="monto" step="0.01" min="0.01" max="99999999.99"  required="" readonly=""/>
                                    </div>
                                    <div class="col-md-3">
                                        <br><br><br>
                                        <input id="agregar_venta_mayoreo" type="button" class="btn btn-info col-md-9" value="Agregar producto"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!-- panel fin-->
                    <div class="panel panel-info"><!-- Lista de productos agregador al carrito -->
                        <div class="panel-heading">
                            <h3 class="panel-title">Productos</h3>
                        </div>
                        <div class="panel-body detalle-producto">
                            <%
                                ArrayList<VentaMayoreo> VentaMay = (ArrayList<VentaMayoreo>) sesion_ajax.getAttribute("detalle");
                                if(((sesion_ajax.getAttribute("detalle"))!=null)){
                                    if(VentaMay.size()>0){//Si la cantida de productos agregados es mayor a cero
                                        out.print("<table class='table'>");
                                        out.print("<tshead>");
                                        out.print("<tr>");
                                        out.print("<th>Madera</th>");
                                        out.print("<th>Número de piezas</th>");
                                        out.print("<th>Volumen</th>");
                                        out.print("<th>Monto</th>");
                                        out.print("<th></th>");
                                        out.print("</tr>");
                                        out.print("</thead>");
                                        out.print("<tbody>");//Inicia el cuerpo de la tabla
                                        for(VentaMayoreo a:VentaMay){
                                            out.print("<tr>");
                                            out.print("<td>"+a.getId_madera()+"</td>");
                                            out.print("<td>"+a.getNum_piezas()+"</td>");
                                            out.print("<td>"+a.getVolumen()+"</td>");
                                            out.print("<td>"+a.getMonto()+"</td>");
                                            out.print("<td><input type='button' value='Eliminar' /></td>");
                                            out.print("</tr>");
                                        }
                                        out.print("</tbody>");
                                        out.print("</table>");
                                    }else{
                                        out.print("<h3 class='panel-title'>No hay registros agregados</h3>");
                                    }
                                }else{
                                    out.print("<h3 class='panel-title'>No hay registros agregados</h3>");
                                }
                            %>
                        </div><!-- Fin de cuerpo de listado -->
                    </div><!-- fin listado de productos -->
                </div><!-- col-md-12 fin -->
            </div><!-- container fluid -->
        </div><!--page wrapper fin -->
    </body>
</html>
