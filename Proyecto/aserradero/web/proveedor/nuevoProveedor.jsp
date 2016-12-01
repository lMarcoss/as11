<%--
    Document   : nuevoProveedor
    Created on : 27-sep-2016, 0:12:42
    Author     : lmarcoss
--%>

<%@page import="entidades.Administrador"%>
<%@page import="entidades.Persona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <title>Nuevo</title>
        <script>
            $(document).ready(function ($){
                 $("#registros").css("background","#448D00");
                 $("#proveedor").css("background","#448D00");
            });
        </script>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        <div class="container" style="margin-top: 60px;">
            <div class="row">
                <div class="col-md-12">
                    <h2>REGISTRO DE UN NUEVO PROVEEDOR</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Rellene los campos de manera correcta</h3>
                        </div>
                        <div class="panel-body">
                            <form action="/aserradero/ProveedorController?action=nuevo" method="post" id="formregistro">
                                <div class="form-group">
                                    <label class="control-label">Id empleado:</label>
                                    <select class="form-control" name="id_persona" required="" title="Si no existe la persona que busca, primero agreguelo en la lista de personas">
                                        <option></option>
                                        <%
                                            List <Persona> personas = (List<Persona>) request.getAttribute("personas");
                                            for (Persona persona : personas) {
                                                out.print("<option value='"+persona.getId_persona()+"'>"+persona.getNombre()+" "+persona.getApellido_paterno()+" "+persona.getApellido_materno()+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Jefe:</label>
                                    <select class="form-control" name="id_jefe" required="" title="Si no existe la persona que busca, primero agreguelo en la lista de empleados jefe">
                                        <option></option>
                                        <%
                                            List <Administrador> administradores = (List<Administrador>) request.getAttribute("administradores");
                                            for (Administrador administrador : administradores) {
                                                out.print("<option value='"+administrador.getId_administrador()+"'>"+administrador.getNombre()+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <a href="/aserradero/ProveedorController?action=listar"><input class="btn btn-warning" type="button" value="Cancelar"/></a>
                                    <input class="btn btn-success" type="submit" value="Guardar"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>        
    </body>
</html>
