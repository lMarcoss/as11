/*
 * Funcion para asignar el maximo de piezas y volumen para las salidas madera rollo
 */
function salidaMaderaRolloPermitido(){
    
    var pieza_existente = document.getElementById('pieza_existente').value;
    var volumen_existente = document.getElementById('volumen_existente').value;
    
    document.getElementById('num_piezas').max = pieza_existente;
    document.getElementById('volumen_total').max = volumen_existente;
    
    return null;
}
function salidaVolumenMinimoPermitido(){
    // establecemos el minimo de volumen si se acaba el inventario
    var num_piezas = document.getElementById('num_piezas').value;
    
    if(num_piezas != ""){
        var pieza_existente = document.getElementById('pieza_existente').value;
        var volumen_existente = document.getElementById('volumen_existente').value;
        if(num_piezas == pieza_existente){
            document.getElementById('volumen_total').min = volumen_existente;
        }else{
            document.getElementById('volumen_total').min = 0.001;
        }
    }
    return null;
}