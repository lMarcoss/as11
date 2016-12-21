<%--
    Document   : nuevoPrestamo
    Created on : 06-nov-2016, 0:57:31
    Author     : lmarcoss
--%>

<%@page import="entidades.Empleado"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Persona> personas = (List<Persona>) request.getAttribute("personas");
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp"%>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Nuevo</title>
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
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h2>NUEVO PRÉSTAMO</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Rellene los campos de manera correcta</h3>
                        </div>
                        <div class="panel-body">
                            <form action="/aserradero/PrestamoController?action=nuevo" method="post" id="formregistro">
                                <input type="hidden" name="id_prestamo" value="1"readonly="" required="">
                                <div class="form-group">
                                    <label class="control-label">Fecha:</label></td>
                                    <input class="form-control" type="date" name="fecha" id="fecha" required="" maxlength="10">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Persona:</label>
                                    <select class="form-control" name="id_prestador" required="">
                                        <option></option>
                                        <%
                                            for (Persona persona : personas) {
                                                out.print("<option value='"+persona.getId_persona()+"'>"+persona.getNombre()+" "+persona.getApellido_paterno()+" "+persona.getApellido_materno()+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Administrador:</label>
                                    <select class="form-control" name="id_empleado" required="">
                                        <option></option>
                                        <%
                                            for (Empleado empleado : empleados) {
                                                out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Monto:</label>
                                    <input class="form-control" type="number" name="monto_prestamo" step="0.01" min="0.01" max="99999999.99"  required="">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">% de interés mensual:</label>
                                    <input class="form-control" type="number" name="interes" step="1" min="1" max="100"  required="">
                                </div>
                                <div class="form-group pull-right">
                                    <a href="/aserradero/PrestamoController?action=listar"><input class="btn btn-warning" type="button" value="Cancelar"/></a>
                                    <input type="submit" id="registrar" class="btn btn-success" value="Guardar"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
