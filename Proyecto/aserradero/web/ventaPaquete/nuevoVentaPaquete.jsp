<%--
    Document   : nuevoProduccionDetalle
    Created on : 28-sep-2016, 9:56:07
    Author     : lmarcoss
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entidades.VentaPaquete"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.Date"%>
<%@page import="entidades.Empleado"%>
<%@page import="entidades.Cliente"%>
<%@page import="entidadesVirtuales.CostoMaderaClasificacion"%>
<%@page import="entidades.MaderaClasificacion"%>
<%@page import="entidades.Venta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Date fecha = Date.valueOf(LocalDate.now());
    List <Venta> ventas = (List<Venta>) request.getAttribute("ventas");
    List <CostoMaderaClasificacion> costoMaderaClasificaciones = (List<CostoMaderaClasificacion>) request.getAttribute("costoMaderaClasificaciones");
    List <Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
    String id_nVenta = String.valueOf(request.getAttribute("siguienteventa"));
    HttpSession sesion_ajax = request.getSession(true);
    sesion_ajax.setAttribute("detalle_venta_paquete", null);
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
        <!--formulario de registro-->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">REGISTRO DE VENTAS POR PAQUETE</h1>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="panel panel-primary"><!-- Panel principal -->
                        <div class="panel-heading">
                            <h3 class="panel-title">Rellene los campos de manera correcta</h3>
                        </div>
                        <div class="panel-body" id="PanelPrincipal">
                            <div class="col-md-12 bordebajo" ><!-- agrupar inputs -->
                                <form action="/aserradero/VentaController?action=nuevo" method="post" id="formregistro"><!-- Formulario de venta -->
                                    <div class="form-group col-md-2"><!-- agrupar inputs -->
                                        <input name="tipo_venta" value="paquete" type="hidden"/>
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
                                        <a href="/aserradero/VentaController?action=listar"><input class="btn btn-block btn-warning" type="submit" value="Cancelar"/></a>
                                    </div><!-- Fin div group -->
                                </form><!-- Formulario de venta -->
                            </div><!-- Fin div group -->
                            <div class="col-md-12">
                                <div class="form-group col-md-2">
                                    <label class="control-label" >Número de Paquete</label>
                                    <input type="number" class="form-control"  id="numero_paquete" name="numero_paquete" min="1" max="99999999999" title="Sólo número" required=""/>
                                </div>
                                <div class="col-md-2">
                                    <label class="control-label" >Madera:</label>
                                    <select name="id_madera" class="form-control"  required="" id="id_madera" onblur="seleccionarCostoMaderaVenta()">
                                        <option></option>
                                        <%
                                            for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                                out.print("<option value='"+costoMaderaClasificacion.getId_madera()+"'>"+costoMaderaClasificacion.getId_madera()+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>                                
                                <div class="col-md-2">
                                    <label class="control-label">volumen unitaria</label>
                                    <select name="volumen_unitaria" class="form-control"  id="volumen_unitaria" readonly="" disabled="">
                                        <option></option>
                                        <%
                                            for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                                out.print("<option value='"+costoMaderaClasificacion.getVolumen()+"'>"+costoMaderaClasificacion.getVolumen()+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label class="control-label">Costo volumen</label>
                                    <select class="form-control"  name="costo_volumen" id="costo_volumen" readonly="" disabled="">
                                        <option></option>
                                        <%
                                            for (CostoMaderaClasificacion costoMaderaClasificacion : costoMaderaClasificaciones) {
                                                out.print("<option value='"+costoMaderaClasificacion.getMonto_volumen()+"'>"+costoMaderaClasificacion.getMonto_volumen()+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label class="control-label" >Número de piezas:</label>
                                    <input type="number" class="form-control"  name="num_piezas" id="num_piezas" min="1" max="9999" required="" title="Escribe la cantidad de piezas" onblur="calcularVolumenTotal()"/>
                                </div>
                            </div>
                            <div class="col-md-12">                                
                                <div class="col-md-2">
                                    <label class="control-label" >Volumen:</label>
                                    <input type="number" class="form-control"  name="volumen" id="volumen" step="0.001" min="0.001" max="99999.999"  required="" readonly=""/>
                                </div>
                                <div class="col-md-2">
                                    <label class="control-label" >Monto:</label>
                                    <input type="number" class="form-control" name="monto" id="monto" step="0.01" min="0.01" max="99999999.99"  required="" readonly=""/>
                                </div>
                                <div class="col-md-8 pull-right">
                                    <input type="button" value="Guardar" id="agregar_venta_paquete" class="btn btn-primary centrar-btn-vp"/>
                                </div>                                
                            </div><!-- a -->
                        </div><!-- Fin de panel venta -->
                    </div><!-- panel fin-->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">Productos</h3>
                        </div>
                        <div class="panel-body detalle-producto">
                            <%                            
                            ArrayList<VentaPaquete> VentaMay = (ArrayList<VentaPaquete>) sesion_ajax.getAttribute("detalle_venta_paquete");
                            if((sesion_ajax.getAttribute("detalle_venta_paquete"))!=null){
                                if(VentaMay.size()>0){//Si la cantida de productos agregados es mayor a cero
                                    out.print("<table class='table'>");
                                    out.print("<thead>");
                                    out.print("<tr>");
                                    out.print("<th>Número paquete</th>");
                                    out.print("<th>Madera</th>");
                                    out.print("<th>Número de piezas</th>");
                                    out.print("<th>Volumen</th>");
                                    out.print("<th>Monto</th>");
                                    out.print("<th></th>");
                                    out.print("</tr>");
                                    out.print("</thead>");
                                    out.print("<tbody>");//Inicia el cuerpo de la tabla    
                                    for(VentaPaquete a:VentaMay){
                                        out.print("<tr>");
                                        out.print("<td>"+a.getNumero_paquete()+"</td>");
                                        out.print("<td>"+a.getId_madera()+"</td>");
                                        out.print("<td>"+a.getNum_piezas()+"</td>");                
                                        out.print("<td>"+a.getVolumen()+"</td>");
                                        out.print("<td>"+a.getMonto()+"</td>");
                                        out.print("<td><input type='button' value='Eliminar' class='btn btn-danger eliminar-vm' id='"+a.getId_madera()+"'/></td>");
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
                        </div>
                    </div><!-- Fin panel lista de productos -->
                </div><!-- col-md-12 fin -->
            </div><!-- container fluid -->
        </div><!--page wrapper fin -->       
    </body>
</html>