/* global alertify */

$(function(){    
    $("#agregar_venta_mayoreo").off("click");
    $("#agregar_venta_mayoreo").on("click", function(e) {
        /*Función para agregar elementos a la cola de venta*/
//        var id_administrador=$("#id_administrador").val();
        var id_venta=$("#id_venta").val();
        var volumen = $("#volumen").val();
        var id_madera = $("#id_madera").val();
        var num_piezas = $("#num_piezas").val();
        var monto = $("#monto").val();             
        var tipo_madera = $("#tipo_madera").val();             
        $.ajax({
            url: 'VentasAjaxController',
            type: 'POST',
            data: {'accion':"add_venta_mayoreo",'id_venta':id_venta,'id_madera':id_madera,'volumen':volumen,'num_piezas':num_piezas,'monto':monto,'tipo_madera':tipo_madera},
            dataType: 'json'
        }).done(function (data){            
            if(data.success==="true"){                    
                alertify.success("El producto fue agregado con éxito");
//                $("#id_venta").val('');
                $("#monto").val('');
                $("#volumen").val('');
                $("#num_detalle").val('');
                $("#volumen_unitaria").val('');
                $("#costo_volumen").val('');
                $("#id_madera").val('');
                $("#num_piezas").val('');
                $(".detalle-producto").load('moduloVenta/ventaMayoreo/detalleVentaMayoreo.jsp');
            }else{
                alertify.error("El producto no puede ser agregado a lista");
                $(".detalle-producto").load('moduloVenta/ventaMayoreo/detalleVentaMayoreo.jsp');
            }
        });                
        $(".detalle-producto").load('moduloVenta/ventaMayoreo/detalleVentaMayoreo.jsp');
    });
    //en caso de que se elimine una fila
    $(".eliminar-vm").off("click");
    $(".eliminar-vm").on("click", function(e) {
        /* Función para eliminar productos de la cola de venta*/
        var id = $(this).attr("id");
        var id_venta=$("#id_venta").val();
        var tipo_madera = $(this).attr("name");
//        var volumen = $("#volumen").val();
//        var id_madera = $("#id_madera").val();
//        var num_piezas = $("#num_piezas").val();
//        var monto = $("#monto").val();
//        var tipo = $("#tipo_madera").val(); // tipo_madera
        $(".detalle-producto").load('moduloVenta/ventaMayoreo/detalleVentaMayoreo.jsp');
        $.ajax({
            url: 'VentasAjaxController',
            type: 'POST',
            data: {'accion':"del_venta_mayoreo",'id_venta':id_venta,'id_madera':id,'tipo_madera':tipo_madera},
            dataType: 'json'           
        }).done(function(data){
            if(data.success==="true"){                
                alertify.success("El producto fue borrado de la lista");
                $(".detalle-producto").load('moduloVenta/ventaMayoreo/detalleVentaMayoreo.jsp');
            }else{                
                alertify.error("El producto no pudo ser borrado");
                $(".detalle-producto").load('moduloVenta/ventaMayoreo/detalleVentaMayoreo.jsp');
            }
        });        
        $(".detalle-producto").load('moduloVenta/ventaMayoreo/detalleVentaMayoreo.jsp');
    });
});
