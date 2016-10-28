<%-- 
    Document   : municipios
    Created on : 13-sep-2016, 13:13:32
    Author     : lmarcoss
--%>

<%@page import="entidades.Localidad"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Nuevo</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/LocalidadController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="nombre_localidad">Localidad</option>
                            <option value="nombre_municipio">Municipio</option>
                            <option value="telefono">Teléfono</option>
                        </select>
                        </td>
                        <td><input type="text" name="dato" placeholder="Escriba su búsqueda"></td>
                        <td colspan="2"><input type="submit" value="Buscar"></td>
                    </tr>
                </table>
            </form>
        </div> <!-- Fin opción de búsqueda-->
        
        <!-- ************************* Resultado Consulta-->
        <div>
            <table class="table-condensed">
                    <tr>
                        <th>N°</th>
                        <th>Localidad</th>
                        <th>Municipio</th>
                        <th>Teléfono</th>
                    </tr>
                    <%
                        int i=0;
                        for (Localidad localidad : localidades) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+localidad.getNombre_localidad()+"</td>"
                                +"<td><a href=\"/aserradero/MunicipioController?action=buscar_municipio&nombre_municipio="+localidad.getNombre_municipio()+"\">"+localidad.getNombre_municipio()+"</a></td>"
                                +"<td>"+localidad.getTelefono()+"</td>"
                                +"<td><a href=\"/aserradero/LocalidadController?action=modificar&nombre_localidad="+localidad.getNombre_localidad()+"\">Actualizar</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/LocalidadController?action=eliminar&nombre_localidad="+localidad.getNombre_localidad()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar localidad" onClick=" window.location.href='/aserradero/LocalidadController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
