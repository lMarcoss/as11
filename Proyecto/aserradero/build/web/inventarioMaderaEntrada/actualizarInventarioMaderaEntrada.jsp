<%--
    Document   : actualizarInverntarioMaderaEntrada
    Created on : 26/09/2016, 11:49:08 PM
    Author     : rcortes
--%>
<%@page import="entidades.InventarioMaderaEntrada"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%InventarioMaderaEntrada inventariomaderaentrada = (InventarioMaderaEntrada) request.getAttribute("inventariomaderaentrada");%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/TEMPLATE/headNuevo.jsp" %>
        <title>Actualizar</title>
    </head>
    <body>
        <!--menu-->
        <%@ include file="/TEMPLATE/menu.jsp" %>
        
        <!-- ******************* Formulario de registro-->
        <div>
            <form action="/aserradero/InventarioMaderaEntradaController?action=actualizar" method="post" id="formregistro">
                <h3>Actualizar datos</h3>
                <fieldset id="user-details">
                    <table>
                      <tr>
                        <td style="padding-left: 10px;"><label for="clasificacion">Clasificación</label></td>
                        <td style="padding-left: 10px;">
                            <select name="clasificacion"> <%String clas=inventariomaderaentrada.getClasificacion();%>
                              <%
                                  if (clas.equals("Secundario")){
                                          out.print("<option>Primario</option>");
                                          out.print("<option selected=\"selected\" >Secundario</option>");
                                          out.print("<option>Terciario</option>");
                                      }else if(clas.equals("Primario")){
                                          out.print("<option selected=\"selected\" >Primario</option>");
                                          out.print("<option >Secundario</option>");
                                          out.print("<option>Terciario</option>");
                                      }else if(clas.equals("Terciario")){
                                          out.print("<option>Primario</option>");
                                          out.print("<option >Secundario</option>");
                                          out.print("<option selected=\"selected\" >Terciario</option>");
                                      }
                              %>

                            </select>
                        </td>
                      </tr>
                      <tr>
                          <td style="padding-left: 10px;"><label for="volumen">Volumen</label></td>
                          <td style="padding-left: 10px;"><input type="text" name="volumen" value="<%=inventariomaderaentrada.getVolumen()%>"/></td>
                      </tr>                        
                        <tr>
                            <td style="padding-left: 10px;"><a href="/aserradero/InventarioMaderaEntradaController?action=listar"><input type="button" value="Cancelar"/></a> </td>
                            <!--<td><input type="submit" value="Registrar" class="submit"/> </td>-->
                            <td style="padding-left: 10px;"><input type="submit" value="Guardar"/></td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div><!--Fin Formulario de registro-->
    </body>
</html>
