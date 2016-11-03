$(function(){
    $("#agregar_venta_mayoreo").off("click");
    $("#agregar_venta_mayoreo").on("click", function(e) {
        /*Función para agregar elementos a la cola de venta*/       
        var id_venta=$("#id_venta").val();
        var volumen = $("#volumen").val();
        var id_madera = $("#id_madera").val();
        var num_piezas = $("#num_piezas").val();
        var monto = $("#monto").val();             
        $.ajax({
            url: 'VentasAjaxController',
            type: 'POST',
            data: {'accion':"add_venta_mayoreo",'id_venta':id_venta,'id_madera':id_madera,'volumen':volumen,'num_piezas':num_piezas,'monto':monto},
            dataType: 'json'
        }).done(function (result){
                if(result.success==="true"){
                    alert(result.success);
                  //alertify.success(result.msj);
                }else{
                    alert(result.success);
                  //alertify.error(data.msj);
                  $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
                }
            });
        $("#monto").val('');
        $("#volumen").val('');
        $("#num_detalle").val('');
        $("#volumen_unitaria").val('');
        $("#costo_volumen").val('');
        $("#id_madera").val('');
        $("#num_piezas").val('');
        $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
        $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
    });
    //en caso de que se elimine una fila
    $(".eliminar-vm").off("click");
    $(".eliminar-vm").on("click", function(e) {
        /* Función para eliminar productos de la cola de venta*/
        var id = $(this).attr("id");
        var id_venta=$("#id_venta").val();
        var volumen = $("#volumen").val();
        var id_madera = $("#id_madera").val();
        var num_piezas = $("#num_piezas").val();
        var monto = $("#monto").val();
        $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
        $.ajax({
            url: 'VentasAjaxController',
            type: 'POST',
            data: {'accion':"del_venta_mayoreo",'id_venta':id_venta,'id_madera':id,'volumen':0,'num_piezas':0,'monto':0},
            dataType: 'json'
        }).done(function(data){
            if(data.success===true){
                alert(data.success);
                //alertify.success(data.msj);
                $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
            }else{
                alert(data.success);
                //alertify.error(data.msj);
                $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
            }
        });
        $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
        $(".detalle-producto").load('ventaMayoreo/detalleVentaMayoreo.jsp');
    });
});
