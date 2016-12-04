<%--
    Document   : proveedores
    Created on : 27-sep-2016, 0:12:12
    Author     : lmarcoss
--%>

<%@page import="entidades.Proveedor"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Proveedores</title>
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
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>">
        <div class="container" style="margin-top:60px;">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">LISTADO DE PROVEEDORES</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Si el vehículo no aparece, agréguelo</h3>
                        </div>
                        <div class="panel-body">
                            <div class="form-group form-busc" ><!-- Formulario para realizar búsquedas en la base de datos -->
                                <form method="POST" action="/aserradero/ProveedorController?action=buscar">
                                    <select name="nombre_campo" class="input-busc">
                                        <option value="id_proveedor">Id proveedor</option>
                                        <option value="id_jefe">Id jefe</option>
                                    </select>
                                    <input type="text" name="dato" placeholder="Escriba su búsqueda" class="input-busc">
                                    <input type="submit" value="Buscar" class="btn btn-success">
                                </form>
                            </div><!-- Fin formulario de búsqueda -->
                            <table id="tabla" class="display cell-border" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                      <th>N°</th>
                                       <th>Id Proveedor</th>
                                       <th>Id jefe</th>
                                       <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                  <%
                                        int i=0;
                                        for (Proveedor proveedor : proveedores) {
                                            out.print("<tr>"
                                                +"<td>"+(i+1)+"</td>"
                                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+proveedor.getId_proveedor()+"\">"+proveedor.getProveedor()+"</a></td>"
                                                +"<td><a href=\"/aserradero/PersonaController?action=buscar_persona&id_persona="+proveedor.getId_jefe()+"\">"+proveedor.getJefe()+"</a></td>"
                                                + "<td><a class=\"btn btn-danger\" href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/ProveedorController?action=eliminar&id_proveedor="+proveedor.getId_proveedor()+"&id_jefe="+proveedor.getId_jefe()+"';};\">Eliminar</a></td>"
                                            + "</tr>" );
                                            i++;
                                        }
                                    %>
                                </tbody>
                            </table><!-- Fin de tabla -->
                            <div class="agregar_element"><!-- Botón agregar elementos -->
                              <input class="btn btn-primary" type="button" value="Agregar proveedor" onClick=" window.location.href='/aserradero/ProveedorController?action=nuevo' ">
                            </div><!-- Fin Agregar elementos-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
