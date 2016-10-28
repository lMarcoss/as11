<%-- 
    Document   : personas
    Created on : 16-sep-2016, 19:02:24
    Author     : lmarcoss
--%>

<%@page import="entidades.Persona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Persona> personas = (List<Persona>) request.getAttribute("personas");
    String mensaje = (String)request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/head.jsp" %>
        <title>Personas</title>
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
        
        <input type="hidden" name="mensaje" id="mensaje" value="<%=mensaje%>"
        
        <!-- ************************** opción de búsqueda-->
        <div>
            <form method="POST" action="/aserradero/PersonaController?action=buscar">
                <table>
                    <tr>
                        <td>
                            <select name="nombre_campo" >
                            <option value="id_persona">Id persona</option>
                            <option value="nombre">Nombre</option>
                            <option value="apellido_paterno">Apellido paterno</option>
                            <option value="apellido_materno">Apellido materno</option>
                            <option value="localidad">Localidad</option>
                            <option value="direccion">Dirección</option>
                            <option value="sexo">Sexo</option>
                            <option value="fecha_nacimiento">Fecha de nacimiento</option>
                            <option value="telefono">Telefono</option>
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
                        <th>Id persona</th>
                        <th>Nombre</th>
                        <th>Apellido paterno</th>
                        <th>Apellido materno</th>
                        <th>Localidad</th>
                        <th>Dirección</th>
                        <th>Sexo</th>
                        <th>Fecha de nacimiento</th>
                        <th>Telefono</th>
                    </tr>
                    <%
//                        List <Persona> personas = (List<Persona>) request.getAttribute("personas");
                        int i=0;
                        for (Persona persona : personas) {
                            out.print("<tr>"
                                +"<td>"+(i+1)+"</td>"
                                +"<td>"+persona.getId_persona()+"</td>"
                                +"<td>"+persona.getNombre()+"</td>"
                                +"<td>"+persona.getApellido_paterno()+"</td>"
                                +"<td>"+persona.getApellido_materno()+"</td>"
                                +"<td><a href=\"/aserradero/LocalidadController?action=buscar_localidad&nombre_localidad="+persona.getLocalidad()+"\">"+persona.getLocalidad()+"</a></td>"
                                +"<td>"+persona.getDireccion()+"</td>"
                                +"<td>"+persona.getSexo()+"</td>"
                                +"<td>"+persona.getFecha_nacimiento()+"</td>"
                                +"<td>"+persona.getTelefono()+"</td>"
                                +"<td><a href=\"/aserradero/PersonaController?action=modificar&id_persona="+persona.getId_persona()+"\">Actualizar</a></td>"
                                + "<td><a href=\"javascript:if (confirm('¿Estás seguro de eliminar?')){parent.location='/aserradero/PersonaController?action=eliminar&id_persona="+persona.getId_persona()+"';};\">Eliminar</a></td>"
                            + "</tr>" );
                            i++;
                        }
                    %>
            </table>
            <div>
                <input type="button" value="Agregar persona" onClick=" window.location.href='/aserradero/PersonaController?action=nuevo' ">
            </div>
        </div><!-- Resultado Consulta-->
    </body>
</html>
