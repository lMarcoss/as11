$(function(){
    $("#agregar_Ven_Ex").off("click");//Agregar producto a venta extra
    $("#agregar_Ven_Ex").on("click", function(e) {
        var id_venta=$("#id_venta").val();
        var tipo = $("#tipo").val();
        var monto = $("#monto").val();
        var observacion = $("#observacion").val();
        $.ajax({
            url: 'VentasAjaxController',
            type: 'post',
            data: {'accion':"add_venta_extra",'id_venta':id_venta,'tipo':tipo,'monto':monto,'observacion':observacion},
            dataType: 'json'
        }).done(function(data){
            if(data.success===true){
                $(".detalle-producto").load('ventaExtra/detalleVentaExtra.jsp');
                $("#monto").val('');
                alertify.success(data.msj);                    
            }else{
                $(".detalle-producto").load('ventaExtra/detalleVentaExtra.jsp');
                alertify.error(data.msj);
            }
        });
        $(".detalle-producto").load('ventaExtra/detalleVentaExtra.jsp');
    });
});
