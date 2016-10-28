/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function ($){
    var tamdivtabla=$("#tabla").width();
    var tamPPrin=$("#PanelPrincipal").width();
    var margin_PP=(tamPPrin-tamdivtabla)/2;
     $(".dataTables_wrapper").css("width",tamdivtabla);
     $(".dataTables_wrapper").css("margin","0 auto");     
     $(".form-busc").css("width",tamdivtabla);
     $(".agregar_element").css("width",tamdivtabla);
});

