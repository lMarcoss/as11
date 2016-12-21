<%--
    Document   : actualizarPrestamo
    Created on : 06-nov-2016, 0:57:42
    Author     : lmarcoss
--%>

<%@page import="entidades.Persona"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Administrador"%>
<%@page import="entidades.Prestamo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Prestamo prestamo = (Prestamo) request.getAttribute("prestamo");
    List <Persona> personas = (List<Persona>) request.getAttribute("personas");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp"%>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h2>ACTUALIZACIÓN DE DATOS</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Actualice los campos necesarios y guarde</h3>
                        </div>
                        <div class="panel-body">
                            <form action="/aserradero/PrestamoController?action=actualizar" method="post" id="formregistro">
                                <input type="hidden" name="id_prestamo" value="<%=prestamo.getId_prestamo()%>"readonly="" required="">
                                <div class="form-group">
                                    <label class="control-label">Fecha:</label>
                                    <input class="form-control" type="date" name="fecha" id="fecha" value="<%=prestamo.getFecha()%>" required="" maxlength="10" readonly="">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Persona:</label>
                                    <select class="form-control" name="id_prestador" required="">
                                        <%
                                            for (Persona persona : personas) {
                                                if(prestamo.getId_prestador().substring(0, 18).equals(persona.getId_persona())){
                                                    out.print("<option selected=\"\" value='"+prestamo.getId_prestador()+"'>"+persona.getNombre()+" "+persona.getApellido_paterno()+" "+persona.getApellido_materno()+"</option>");
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Administrador:</label>
                                    <select class="form-control" name="id_empleado" required="">
                                        <option value="<%= prestamo.getId_empleado()%>"><%= prestamo.getEmpleado()%></option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Monto:</label>
                                    <input class="form-control" type="number" name="monto_prestamo" value="<%=prestamo.getMonto_prestamo()%>" step="0.01" min="0.01" max="99999999.99"  required="">
                                </div>
                                <div class="form-group">
                                    <label class="control-label">% de interés mensual:</label>
                                    <input class="form-control" type="number" name="interes" value="<%=prestamo.getInteres()%>" step="1" min="1" max="100"  required="">
                                </div>
                                <div class="form-group pull-right">
                                    <a href="/aserradero/PrestamoController?action=listar"><input type="button" class="btn btn-warning" value="Cancelar"/></a>
                                    <input type="submit" class="btn btn-success" id="registrar" value="Guardar"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
