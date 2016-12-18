/*Seleccionar el estado que pertenece una localidad al registrar una persona*/
function seleccionarEstadoLocalidad() {
    var localidad = document.getElementById('localidad');
    var localidad_seleccionada = localidad.selectedIndex;

    document.getElementById('estado').selectedIndex = localidad_seleccionada;
    return null;
}