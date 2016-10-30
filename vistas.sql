
-- vistas --------------------------------------------------------------------------------------------------

use aserradero;


-- Vista para CostoMaderaClasificación
CREATE VIEW COSTO_MADERA_CLASIFICACION AS 
SELECT COSTO_MADERA.id_madera AS id_madera, grueso, ancho, largo,volumen, monto_volumen
	FROM MADERA_CLASIFICACION
	INNER JOIN COSTO_MADERA WHERE MADERA_CLASIFICACION.id_madera = COSTO_MADERA.id_madera;

-- lista cuentas por cobrar proveedor
CREATE VIEW CUENTA_POR_COBRAR_PROVEEDOR AS
SELECT CUENTA_POR_COBRAR.id_persona,
	(select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(CUENTA_POR_COBRAR.id_persona,1,18)) as persona,
    PROVEEDOR.id_jefe as id_jefe,
    monto
	FROM CUENTA_POR_COBRAR,PROVEEDOR WHERE CUENTA_POR_COBRAR.id_persona=id_proveedor;
SELECT * FROM CUENTA_POR_COBRAR_PROVEEDOR;

-- lista de cuentas por cobrar clientes
CREATE VIEW CUENTA_POR_COBRAR_CLIENTE AS
SELECT CUENTA_POR_COBRAR.id_persona,
	(select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(CUENTA_POR_COBRAR.id_persona,1,18)) as persona,
    CLIENTE.id_jefe as id_jefe,
    monto
	FROM CUENTA_POR_COBRAR,CLIENTE WHERE CUENTA_POR_COBRAR.id_persona=id_cliente;
SELECT * FROM CUENTA_POR_COBRAR_CLIENTE;

-- lista de cuentas por pagar proveedores
CREATE VIEW CUENTA_POR_PAGAR_PROVEEDOR AS
SELECT CUENTA_POR_PAGAR.id_persona,
		(select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(CUENTA_POR_PAGAR.id_persona,1,18)) as persona,
        PROVEEDOR.id_jefe,
        monto
	FROM CUENTA_POR_PAGAR,PROVEEDOR WHERE CUENTA_POR_PAGAR.id_persona = PROVEEDOR.id_proveedor;
SELECT * FROM CUENTA_POR_PAGAR_PROVEEDOR;

-- lista cuentas por pagar a clientes
CREATE VIEW CUENTA_POR_PAGAR_CLIENTE AS
SELECT CUENTA_POR_PAGAR.id_persona,
		(select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(CUENTA_POR_PAGAR.id_persona,1,18)) as persona,
        CLIENTE.id_jefe,
        monto
	FROM CUENTA_POR_PAGAR,CLIENTE WHERE CUENTA_POR_PAGAR.id_persona = CLIENTE.id_cliente;
SELECT * FROM CUENTA_POR_PAGAR_CLIENTE;

-- Vista para reportes y ticket de ventas por paquete
CREATE VIEW VISTA_VENTAS_POR_PAQUETE AS
SELECT 
		fecha,
		VENTA.id_venta,
		id_cliente,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18)) as cliente,
		(SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18)) as direccion_cliente,
        id_empleado,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18)) as empleado,
        estatus,
        numero_paquete,
        COSTO_MADERA.id_madera,
        grueso,
        ancho,
        largo,
        MADERA_CLASIFICACION.volumen as volumen_unitario,
        monto_volumen AS costo_volumen,
        num_piezas,
        VENTA_PAQUETE.volumen as volumen_total,
        VENTA_PAQUETE.monto as costo_total
FROM VENTA,VENTA_PAQUETE,MADERA_CLASIFICACION,COSTO_MADERA 
WHERE VENTA.id_venta = VENTA_PAQUETE.id_venta AND
		VENTA_PAQUETE.id_madera = MADERA_CLASIFICACION.id_madera AND
        MADERA_CLASIFICACION.id_madera = COSTO_MADERA.id_madera AND
        VENTA.tipo_venta='Paquete';

-- VISTA para reportes y ticket de ventas por mayoreo
CREATE VIEW VISTA_VENTAS_POR_MAYOREO AS
SELECT fecha,
		VENTA.id_venta,
        id_cliente,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18)) as cliente,
        (select id_jefe from CLIENTE where id_cliente = VENTA.id_cliente) as id_jefe,
		(SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18)) as direccion_cliente,
		id_empleado, 
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18)) as empleado,	
		estatus,
        COSTO_MADERA.id_madera as id_madera,
        grueso,
        ancho,
        largo,
        MADERA_CLASIFICACION.volumen as volumen_unitario,
        monto_volumen as costo_volumen,
        num_piezas,
        VENTA_MAYOREO.volumen AS volumen_total,
        VENTA_MAYOREO.monto as costo_total
FROM VENTA,VENTA_MAYOREO,MADERA_CLASIFICACION,COSTO_MADERA
WHERE VENTA.id_venta = VENTA_MAYOREO.id_venta AND
		VENTA_MAYOREO.id_madera = MADERA_CLASIFICACION.id_madera AND
        MADERA_CLASIFICACION.id_madera = COSTO_MADERA.id_madera AND
        VENTA.tipo_venta='Mayoreo';
-- Vista para reportes y ticket de ventas extras
CREATE VIEW VISTA_VENTAS_EXTRA AS
SELECT fecha,
		VENTA.id_venta,
        id_cliente,(select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_cliente) as cliente,
        (SELECT direccion FROM PERSONA WHERE id_persona = id_cliente) as direccion_cliente,
        id_empleado,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_empleado) as empleado,
        estatus,
        tipo,
        monto,
        observacion
FROM VENTA,VENTA_EXTRA
WHERE VENTA.id_venta = VENTA_EXTRA.id_venta AND tipo_venta = 'Extra';

-- vista para consultar datos del cliente para el ticket
CREATE VIEW VISTA_CLIENTE_TICKET AS
SELECT VENTA.id_venta,VENTA.fecha,VENTA.tipo_venta,CLIENTE.id_jefe,CLIENTE.id_cliente,PERSONA.id_persona, 
	concat(nombre," ", apellido_paterno, " ", apellido_materno) as cliente,
	direccion,
    localidad,
    MUNICIPIO.nombre_municipio as municipio,
    estado
	FROM VENTA,CLIENTE,PERSONA,LOCALIDAD,MUNICIPIO
	WHERE VENTA.id_cliente = CLIENTE.id_cliente AND
		CLIENTE.id_persona = PERSONA.id_persona AND
            PERSONA.localidad = LOCALIDAD.nombre_localidad AND
            LOCALIDAD.nombre_municipio = MUNICIPIO.nombre_municipio;
SELECT * FROM VISTA_CLIENTE_TICKET;

-- vista para reporte de compras
CREATE VIEW COMPRA_REPORTE AS
SELECT fecha,id_compra,num_piezas,id_proveedor,(SELECT concat(nombre,' ',apellido_paterno, ' ',apellido_materno) from PERSONA where SUBSTRING(COMPRA.id_proveedor,1,18)= PERSONA.id_persona )as nombre_proveedor,
id_chofer,(SELECT concat(nombre,' ',apellido_paterno, ' ',apellido_materno) from PERSONA where SUBSTRING(COMPRA.id_chofer,1,18)=PERSONA.id_persona)as nombre_chofer,
id_empleado,(SELECT concat(nombre,' ',apellido_paterno, ' ',apellido_materno) from PERSONA where SUBSTRING(COMPRA.id_empleado,1,18)=PERSONA.id_persona)as nombre_empleado,
(SELECT sum(volumen) from DETALLE_COMPRA WHERE COMPRA.id_compra = DETALLE_COMPRA.id_compra AND clasificacion = 'Primario') AS vol_primario, 
(SELECT volumen from DETALLE_COMPRA WHERE COMPRA.id_compra = DETALLE_COMPRA.id_compra AND clasificacion = 'Secundario') AS vol_secundario,
                                    (SELECT sum(volumen) from DETALLE_COMPRA WHERE COMPRA.id_compra = DETALLE_COMPRA.id_compra AND clasificacion = 'Terciario')AS vol_terciario,(select sum(monto) from DETALLE_COMPRA where COMPRA.id_compra=DETALLE_COMPRA.id_compra) as monto_total
                                    from COMPRA;

USE aserradero;

SELECT * FROM COMPRA_REPORTE;

