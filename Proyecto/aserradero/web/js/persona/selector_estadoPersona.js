/*Seleccionar el estado que pertenece una localidad*/
function seleccionarEstadoLocalidad() {
    var localidad = document.getElementById('localidad');
    var localidad_seleccionada = localidad.selectedIndex;

    document.getElementById('estado').selectedIndex = localidad_seleccionada;
    return null;
}