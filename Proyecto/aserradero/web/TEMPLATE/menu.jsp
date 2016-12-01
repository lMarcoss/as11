<%--
    Document   : header
    Created on : 30-sep-2016, 1:42:10
    Author     : lmarcoss
--%>

<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="menu_respons">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">Aserradero</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="submenu">
                    <a href="#" id="registros">Registros</a>
                    <ul class="dropdown-menu">
                        <li id="municipios"><a href="/aserradero/MunicipioController?action=listar">Municipios</a>
                            <ul class="dropdown-menu">
                                <li><a href="/aserradero/MunicipioController?action=listar">Nuevo municipio</a></li>
                            </ul>
                        </li>
                        <li id="localidades"><a href="/aserradero/LocalidadController?action=listar">Localidades</a>
                            <ul class="dropdown-menu">
                                <li><a href="/aserradero/LocalidadController?action=nuevo">Nueva localidad</a></li>
                            </ul>
                        </li>
                        <li id="personas"><a href="/aserradero/PersonaController?action=listar">Personas</a>
                            <ul class="dropdown-menu">
                                <li><a href="/aserradero/PersonaController?action=nuevo">Nueva persona</a></li>
                            </ul>
                        </li>
                        <li id="clientes"><a href="/aserradero/ClienteController?action=listar">Clientes</a>
                            <ul class="dropdown-menu">
                                <li><a href="/aserradero/ClienteController?action=nuevo">Nuevo cliente</a></li>
                            </ul>
                        </li>
                        <li id="proveedor"><a href="/aserradero/ProveedorController?action=listar">Proveedor</a>
                            <ul class="dropdown-menu">
                                <li><a href="/aserradero/ProveedorController?action=nuevo">Nuevo proveedor</a></li>
                            </ul>
                        </li>
                        <li id="vehiculos"><a href="/aserradero/VehiculoController?action=listar">Vehículos</a>
                            <ul class="dropdown-menu">
                                <li><a href="/aserradero/VehiculoController?action=nuevo">Nuevo vehículo</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>
                <li  id="empleados" class="submenu">
                    <a href="#">Empleados</a>
                    <ul class="dropdown-menu">
                        <li id="empleado"><a href="/aserradero/EmpleadoController?action=listar">Registros</a></li>
                        <li id="pago_empleado"><a href="/aserradero/PagoEmpleadoController?action=listar">Pago empleado</a></li>
                        <li id="administrador"><a href="/aserradero/AdministradorController?action=listar">Administradores</a></li>
                    </ul>
                </li>
                <li  id="compras" class="submenu">
                    <a href="#">Madera en rollo</a>
                    <!--<a href="/aserradero/CompraController?action=listar">Entrada de madera</a>-->
                    <ul class="dropdown-menu">
                        <li class="detalle_compra"><a href="/aserradero/EntradaMaderaRolloController?action=listar_entrada">Entrada</a></li>
                        <li class="detalle_compra"><a href="/aserradero/SalidaMaderaRolloController?action=listar_salida">Salida</a></li>
                        <!--<li class="pagos_copra"><a href="/aserradero/PagoCompraController?action=listar">Pagos compra</a></li>-->
                        <li class="inventario_madera_entrada"><a href="/aserradero/InventarioMaderaEntradaController?action=listar">Inventario</a></li>
                        <li class="costo_madera_entrada"><a href="/aserradero/ClasificacionMaderaEntradaController?action=listar">Clasficación</a></li>
                        <li class="costo_madera_entrada"><a href="/aserradero/PagoCompraController?action=listar">Pagos compra</a></li>
                    </ul>
                </li>
                <li  id="produccion" class="submenu">
                    <a href="#">Madera aserrada</a>
                    <ul class="dropdown-menu">
                        <li id="produccion_madera"><a href="/aserradero/ProduccionMaderaController?action=listar">Entrada</a></li>
                        <li id="inventario_produccion"><a href="/aserradero/InventarioMaderaProduccionController?action=listar">Inventario</a></li>
                        <li id="clasificacion_madera"><a href="/aserradero/MaderaClasificacionController?action=listar">Clasificación</a></li>
                        <li id="costo_madera_produccion"><a href="/aserradero/CostoMaderaController?action=listar">Costo clasificación</a></li>
                    </ul>
                </li>
                <li  id="ventas">
=======
<header>
    <nav class="">
        <ul class="">
            <li class="submenu">
                <a href="#" id="registros">Registros</a>
                <ul class="children">
                    <li id="municipios"><a href="/aserradero/MunicipioController?action=listar">Municipios</a></li>
                    <li id="localidades"><a href="/aserradero/LocalidadController?action=listar">Localidades</a></li>
                    <li id="personas"><a href="/aserradero/PersonaController?action=listar">Personas</a></li>
                    <li id="clientes"><a href="/aserradero/ClienteController?action=listar">Clientes</a></li>
                    <li id="proveedor"><a href="/aserradero/ProveedorController?action=listar">Proveedor</a></li>
                    <li id="vehiculos"><a href="/aserradero/VehiculoController?action=listar">Vehículos</a></li>
                </ul>
            </li>
            <li class="submenu" id="empleados">
                <a href="#">Empleados</a>
                <ul class="children">
                    <li id="empleado"><a href="/aserradero/EmpleadoController?action=listar">Registros</a></li>
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
                    <li class="costo_madera_entrada"><a href="/aserradero/ClasificacionMaderaEntradaController?action=listar">Clasficación</a></li>
                    <li class="costo_madera_entrada"><a href="/aserradero/PagoCompraController?action=listar">Pagos compra</a></li>
                </ul>
            </li>
            <li class="submenu" id="produccion">
                <a href="#">Madera aserrada</a>
                <ul class="children">
                    <li id="produccion_madera"><a href="/aserradero/EntradaMaderaAserradaController?action=listar">Entrada</a></li>
                    <li id="inventario_produccion"><a href="/aserradero/InventarioMaderaAserradaController?action=listar">Inventario</a></li>
                    <li id="clasificacion_madera"><a href="/aserradero/MaderaAserradaClasifController?action=listar">Clasificación</a></li>
                </ul>
            </li>
            <li class="submenu" id="ventas">
                    <a href="/aserradero/VentaController?action=listar">Ventas</a>
                    <ul class="dropdown-menu">
                        <li id="ventas_extras"><a href="/aserradero/VentaExtraController?action=listar">Ventas extras</a></li>
                        <li id="ventas_mayoreo"><a href="/aserradero/VentaMayoreoController?action=listar">Ventas Mayoreo</a></li>
                        <li id="ventas_por_paquete"><a href="/aserradero/VentaPaqueteController?action=listar">Ventas por paquete</a></li>
                    </ul>
                </li>
                <li id="anticipos" class="submenu">
                    <a href="#">Anticipos</a>
                    <ul class="dropdown-menu">
                        <li  id="anticipo_proveedores"><a href="#">Proveedores</a>
                            <ul class="dropdown-menu">
                                <li id="cuentas_por_cobrar"><a href="/aserradero/AnticipoProveedorController?action=listar">Anticipo Proveedores</a></li>
                                <li id="cuentas_por_cobrar"><a href="/aserradero/CuentaPorCobrarController?action=listar_proveedores">Cuentas por cobrar proveedores</a></li>
                                <li id="cuentas_por_pagar"><a href="/aserradero/CuentaPorPagarController?action=listar_proveedores">Cuentas por pagar proveedores</a></li>
                            </ul>
                        </li>

                        <li id="anticipo_clientes"><a href="#">Clientes</a>
                            <ul class="dropdown-menu">
                                <li id="cuentas_por_cobrar"><a href="/aserradero/AnticipoClienteController?action=listar">Anticipo clientes</a></li>
                                <li id="cuentas_por_cobrar"><a href="/aserradero/CuentaPorCobrarController?action=listar_clientes">Cuentas por cobrar clientes</a></li>
                                <li id="cuentas_por_pagar"><a href="/aserradero/CuentaPorPagarController?action=listar_clientes">Cuentas por pagar clientes</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>
                <li  id="gastos" class="submenu">
                    <a href="#">Gastos</a>
                    <ul class="dropdown-menu">
                        <li id="renta"><a href="/aserradero/PagoRentaController?action=listar">Renta</a></li>
                        <li id="pago_luz"><a href="/aserradero/PagoLuzController?action=listar">Pago luz</a></li>
                        <li id="otro_gasto"><a href="/aserradero/OtroGastoController?action=listar">Otros gasto</a></li>
                    </ul>
                </li>
                <li  id="reportes" class="submenu">
                    <a href="#">Préstamo</a>
                    <ul class="dropdown-menu">
                        <li class="reporte_compra"><a href="/aserradero/PrestamoController?action=listar">Detalles</a></li>
                        <li class="reporte_compra"><a href="/aserradero/PrestamoController?action=listar_total">Préstamo por persona</a></li>
                        <li class="reporte_compra"><a href="/aserradero/PagoPrestamoController?action=listar">Pago préstamo</a></li>
                    </ul>
                </li>
                <li  id="reportes" class="submenu">
                    <a href="#">Reportes</a>
                    <ul class="dropdown-menu">
                        <!--<li class="reporte_compra"><a href="/aserradero/CompraController?action=ver_reporte">Reporte compra</a></li>-->
                        <li class="reporte_compra"><a href="/aserradero/BalanceCuentaController?action=listar">Cuenta inicial</a></li>
                        <li class="reporte_compra"><a href="/aserradero/BalanceCuentaController?action=listar">Balance cuenta</a></li>
                    </ul>
                </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- SmartMenus jQuery plugin -->
    <script type="text/javascript" src="/aserradero/dist/js_css_menu/jquery.smartmenus.js"></script>

    <!-- SmartMenus jQuery Bootstrap Addon -->
    <script type="text/javascript" src="/aserradero/dist/js_css_menu/jquery.smartmenus.bootstrap.js"></script>
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
                <a href="#">Prestamo</a>
                <ul class="children">
                    <li class="reporte_compra"><a href="/aserradero/PrestamoController?action=listar">Registros</a></li>
                    <li class="reporte_compra"><a href="/aserradero/PagoPrestamoController?action=listar">Pago préstamo</a></li>
                </ul>
            </li>
            <li class="submenu" id="reportes">
                <a href="#">Cuentas</a>
                <ul class="children">
                    <li class="reporte_compra"><a href="/aserradero/BalanceCuentaController?action=listar">Balance cuenta</a></li>
                </ul>
            </li>
            <!--<li class="cta"><a href="/aserradero/Iniciar?action=cerrar_sesion">Salir</a></li>-->
        </ul>
    </nav>
</header>

