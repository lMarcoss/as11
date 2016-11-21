function seleccionarMontoPorPagar(){
    //Verificamos el proveedor seleccionado
    var id_proveedor = document.getElementById('id_proveedor');
    var cuenta_pendiente = document.getElementById('cuenta_pendiente').value;
    var id_seleccionado = id_proveedor.selectedIndex;
    
    //Selecciona el monto asociado al proveedor seleccionado
    document.getElementById('cuenta_pendiente').selectedIndex = id_seleccionado;
    
    //Selecciona cuenta por cobrar asociado al proveedor
    document.getElementById('cuenta_por_cobrar').selectedIndex = id_seleccionado;
    var cuenta_por_cobrar = document.getElementById('cuenta_por_cobrar').value;
    // El máximo monto que se puede pagar es el monto asociado al proveedor 
    document.getElementById('monto_pago').max = cuenta_pendiente;
    // El minimo pago sera el de cuenta pr cobrar: lo que el proveedor debe
    document.getElementById('monto_pago').min = cuenta_por_cobrar;
    
    // Calcula el monto para cuenta por pagar
    if(document.getElementById('monto_pago').value){
        var monto_pago = document.getElementById('monto_pago').value;
    }else{
        monto_pago = 0;
    }
    var maxMontoPorPagar = cuenta_pendiente - monto_pago;
    document.getElementById('monto_por_pagar').max = maxMontoPorPagar.toFixed(2);
    document.getElementById('monto_por_pagar').min = maxMontoPorPagar.toFixed(2);
    var monto_por_pagar = cuenta_pendiente - document.getElementById('monto_pago').value;
    document.getElementById('monto_por_pagar').value = monto_por_pagar.toFixed(2);
    
    // Si no se seleccionó ninguno 
    if(id_seleccionado < 1){
        document.getElementById('monto_pago').value = 0;
        document.getElementById('monto_pago').max = 0;
        document.getElementById('monto_por_pagar').value = 0;
    }
    return null;
}