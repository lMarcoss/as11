<%-- 
    Document   : municipios
    Created on : 13-sep-2016, 13:13:32
    Author     : lmarcoss
--%>

<%@page import="entidades.Municipio"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Municipio> municipios = (List<Municipio>) request.getAttribute("municipios");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Municipios</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"

        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/MunicipioController?action=buscar">
                <table class="table-condensed">
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="nombre_municipio">Municipio</option>
                            <option value="estado">Estado</option>
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
                        <th>Municipio</th>
                        <th>Estado</th>
                        <th>Teléfono</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                        int i=0;
                        for (Municipio municipio : municipios) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+municipio.getNombre_municipio()+"</td>"
                                +"<td>"+municipio.getEstado()+"</td>"
                                +"<td>"+municipio.getTelefono()+"</td>"
                                +"<td><a href=\"/aserradero/MunicipioController?action=modificar&nombre_municipio="+municipio.getNombre_municipio()+"\">Actualizar</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/MunicipioController?action=eliminar&nombre_municipio="+municipio.getNombre_municipio()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar municipio" onClick=" window.location.href='/aserradero/municipio/nuevoMunicipio.jsp' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
