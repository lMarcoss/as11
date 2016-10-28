<%-- 
    Document   : header
    Created on : 30-sep-2016, 1:42:10
    Author     : lmarcoss
--%>
<header>
    <nav>
        <ul>
            <li class="submenu">
                <a href="#" id="registros">Registros</a>
                <ul class="children">
                    <li id="municipios"><a href="/aserradero/MunicipioController?action=listar">hola</a></li>
                    <li id="municipios"><a href="/aserradero/MunicipioController?action=listar">Municipios</a></li>
                    <li id="localidades"><a href="/aserradero/LocalidadController?action=listar">Localidades</a></li>
                    <li id="personas"><a href="/aserradero/PersonaController?action=listar">Personas</a></li>
                    <li id="clientes"><a href="/aserradero/ClienteController?action=listar">Clientes</a></li>
                    <li id="proveedor"><a href="/aserradero/ProveedorController?action=listar">Proveedor</a></li>
                    <li id="vehiculos"><a href="/aserradero/VehiculoController?action=listar">Vehículos</a></li>
                </ul>
            </li>
            <li class="submenu" id="empleados">
                <a href="/aserradero/EmpleadoController?action=listar">Empleados</a>
                <ul class="children">
                    <!--<li id="empleados_jefe"><a href="/aserradero/EmpleadoJefeController?action=listar">Asignar jefe</a></li>-->
                    <li id="pago_empleado"><a href="/aserradero/PagoEmpleadoController?action=listar">Pago empleado</a></li>
                    <li id="administrador"><a href="/aserradero/AdministradorController?action=listar">Administradores</a></li>
                </ul>
            </li>
            <li class="submenu" id="compras">
                <a href="#">Madera en rollo</a>
                <!--<a href="/aserradero/CompraController?action=listar">Entrada de madera</a>-->
                <ul class="children">
                    <li class="detalle_compra"><a href="/aserradero/EntradaMaderaRolloController?action=listar_entrada">Entrada</a></li>
                    <li class="detalle_compra"><a href="/aserradero/SalidaMaderaRolloController?action=listar_salida">Salida</a></li>
                    <!--<li class="pagos_copra"><a href="/aserradero/PagoCompraController?action=listar">Pagos compra</a></li>-->
                    <li class="inventario_madera_entrada"><a href="/aserradero/InventarioMaderaEntradaController?action=listar">Inventario</a></li>
                    <li class="costo_madera_entrada"><a href="/aserradero/CostoMaderaEntradaController?action=listar">Clasficación</a></li>
                </ul>
            </li>
            <li class="submenu" id="ventas">
                
                <a href="/aserradero/VentaController?action=listar">Ventas</a>
                <ul class="children">
                    <li id="ventas_extras"><a href="/aserradero/VentaExtraController?action=listar">Ventas extras</a></li>
                    <li id="ventas_mayoreo"><a href="/aserradero/VentaMayoreoController?action=listar">Ventas Mayoreo</a></li>
                    <li id="ventas_por_paquete"><a href="/aserradero/VentaPaqueteController?action=listar">Ventas por paquete</a></li>
                </ul>
            </li>
            <li class="submenu" id="anticipos">
                <a href="#">Anticipos</a>
                <ul class="children">
                    <li id="anticipo_proveedores"><a href="/aserradero/AnticipoProveedorController?action=listar">Anticipo Proveedores</a></li>
                    <li id="cuentas_por_cobrar"><a href="/aserradero/CuentaPorCobrarController?action=listar_proveedores">Cuentas por cobrar proveedores</a></li>
                    <li id="cuentas_por_pagar"><a href="/aserradero/CuentaPorPagarController?action=listar_proveedores">Cuentas por pagar proveedores</a></li>
                    <li id="anticipo_clientes"><a href="/aserradero/AnticipoClienteController?action=listar">Anticipo clientes</a></li>
                    <li id="cuentas_por_cobrar"><a href="/aserradero/CuentaPorCobrarController?action=listar_clientes">Cuentas por cobrar clientes</a></li>
                    <li id="cuentas_por_pagar"><a href="/aserradero/CuentaPorPagarController?action=listar_clientes">Cuentas por pagar clientes</a></li>
                    
                    
                </ul>
            </li>
            <li class="submenu" id="produccion">
                <a href="/aserradero/ProduccionMaderaController?action=listar">Producción</a>
                <ul class="children">
                    <li id="produccio_detalle"><a href="/aserradero/ProduccionDetalleController?action=listar">Produccion detalle</a></li>
                    <li id="clasificacion_madera"><a href="/aserradero/MaderaClasificacionController?action=listar">Clasificación madera</a></li>
                    <li id="inventario_produccion"><a href="/aserradero/InventarioMaderaProduccionController?action=listar">Inventario producción</a></li>
                    <li id="costo_madera_produccion"><a href="/aserradero/CostoMaderaController?action=listar">Costo Madera producción</a></li>
                </ul>
            </li>
            <li class="submenu" id="gastos">
                <a href="#">Gastos</a>
                <ul class="children">
                    <li id="renta"><a href="/aserradero/PagoRentaController?action=listar">Renta</a></li>
                    <li id="pago_luz"><a href="/aserradero/PagoLuzController?action=listar">Pago luz</a></li>
                    <li id="otro_gasto"><a href="/aserradero/OtroGastoController?action=listar">Otros gasto</a></li>
                </ul>
            </li>
            <li class="submenu" id="reportes">
                <a href="#">Reportes</a>
                <ul class="children">
                    <li class="reporte_compra"><a href="/aserradero/CompraController?action=ver_reporte">Reporte compra</a></li>
                </ul>
            </li>
            <!--<li class="cta"><a href="/aserradero/Iniciar?action=cerrar_sesion">Salir</a></li>-->
        </ul>
    </nav>
</header>
