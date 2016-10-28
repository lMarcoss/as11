<%-- 
    Document   : actualizarProduccionMadera
    Created on : 28-sep-2016, 9:48:54
    Author     : lmarcoss
--%>

<%@page import="java.util.List"%>
<%@page import="entidades.Empleado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List <Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="keywords" content="free html5, free template, free bootstrap, free website template, html5, css3, mobile first, responsive" />
        
        <!-- Bootstrap  -->
	<link rel="stylesheet" href="/aserradero/css/menu/bootstrap.css">
	<!-- Theme style  -->
	<link rel="stylesheet" href="/aserradero/css/menu/style.css">
        <link rel="stylesheet" href="/aserradero/css/formulario.css">
        <script languaje="javascript" type="text/javascript"> 
            window.history.go(1); 
        </script> 
    </head>
    <body>
        <!-- **************************************** Barra de menú-->
        <div id="fh5co-page">
            <header id="fh5co-header" role="banner">
                <div class="container">
                    <div class="header-inner">
                        <nav role="navigation">
                            <ul>
                                <!--<li class="active"><a href="/aserradero/balance/balance.html">Balance general</a></li>-->
                                <li><a href="/aserradero/UsuarioController?action=listar">Usuarios</a></li>
                                <li><a href="/aserradero/MunicipioController?action=listar">Municipios</a></li>
                                <li><a href="/aserradero/LocalidadController?action=listar">Localidades</a></li>
                                <li><a href="/aserradero/PersonaController?action=listar">Personas</a></li>
                                <li><a href="/aserradero/EmpleadoController?action=listar">Empleados</a></li>
                                <li><a href="/aserradero/VentaController?action=listar">Ventas</a></li>
                                <li><a href="/aserradero/VentaExtraController?action=listar">Ventas extras</a></li>
                                <li><a href="/aserradero/VentaMayoreoController?action=listar">Ventas por mayoreo</a></li>
                                <li class="cta"><a href="/aserradero/Iniciar?action=cerrar_sesion">Salir</a></li>
                                <!--<li class="cta"><a href="/aserradero/sesion/cerrarSesion.jsp">Salir</a></li>-->
                            </ul>
                        </nav>
                    </div>
                </div>
            </header>
        </div>
        <!-- **************************************** Fin Barra de menú-->
        <div>
            <form action="/aserradero/ProduccionMaderaController?action=nuevo" method="post" id="formregistro">
                <h3>Registrar producción</h3>
                <fieldset id="user-details">
                    <table>
                        <tr>
                            <td style="padding-left: 10px;"><label>Fecha:</label></td>
                            <td style="padding-left: 10px;">
                                <input type="date" name="fecha" required=""/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Id producción</label></td>
                            <td style="padding-left: 10px;"><input type="text" name="id_produccion" pattern="[A-Za-z].{2,}" title="Sólo letras"/></td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><label>Empleado</label></td>
                            <td style="padding-left: 10px;">
                                <select name="id_empleado" required="" id="id_empleado">
                                    <option></option>
                                    <%
                                        for (Empleado empleado : empleados) {
                                            out.print("<option value='"+empleado.getId_empleado()+"'>"+empleado.getEmpleado()+"</option>");
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/ProduccionMaderaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
