
-- vistas --------------------------------------------------------------------------------------------------

use aserradero;

-- Vista para reportes y ticket de ventas por paquete
DROP VIEW IF EXISTS VISTA_VENTAS_POR_PAQUETE;
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
        MADERA_ASERRADA_CLASIF.id_madera,
        grueso,
        ancho,
        largo,
        MADERA_ASERRADA_CLASIF.volumen as volumen_unitario,
        ROUND((VENTA_PAQUETE.monto /VENTA_PAQUETE.volumen),2) as costo_volumen,
        num_piezas,
        VENTA_PAQUETE.volumen as volumen_total,
        VENTA_PAQUETE.monto as costo_total
FROM VENTA,VENTA_PAQUETE,MADERA_ASERRADA_CLASIF
WHERE VENTA.id_venta = VENTA_PAQUETE.id_venta AND
		VENTA_PAQUETE.id_madera = MADERA_ASERRADA_CLASIF.id_madera AND
        VENTA.tipo_venta='Paquete';

-- VISTA para reportes y ticket de ventas por mayoreo
DROP VIEW IF EXISTS VISTA_VENTAS_POR_MAYOREO;
CREATE VIEW VISTA_VENTAS_POR_MAYOREO AS
SELECT fecha,
		VENTA.id_venta,
        id_cliente,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18)) as cliente,
        (select id_jefe from CLIENTE where id_cliente = VENTA.id_cliente) as id_jefe,
		(SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18)) as direccion_cliente,
		id_empleado, 
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18)) as empleado,
		estatus,
        MADERA_ASERRADA_CLASIF.id_madera as id_madera,
        grueso,
        ancho,
        largo,
        MADERA_ASERRADA_CLASIF.volumen as volumen_unitario,
        ROUND((VENTA_MAYOREO.monto /VENTA_MAYOREO.volumen),2) as costo_volumen,
        num_piezas,
        VENTA_MAYOREO.volumen AS volumen_total,
        VENTA_MAYOREO.monto as costo_total
FROM VENTA,VENTA_MAYOREO,MADERA_ASERRADA_CLASIF
WHERE VENTA.id_venta = VENTA_MAYOREO.id_venta AND
		VENTA_MAYOREO.id_madera = MADERA_ASERRADA_CLASIF.id_madera AND
        VENTA.tipo_venta='Mayoreo';

-- Vista para reportes y ticket de ventas extras
DROP VIEW IF EXISTS VISTA_VENTAS_EXTRA;
CREATE VIEW VISTA_VENTAS_EXTRA AS
SELECT fecha,
		VENTA.id_venta,
        id_cliente,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_cliente,1,18)) as cliente,
        (SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_cliente,1,18)) as direccion_cliente,
        id_empleado,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_empleado,1,18)) as empleado,
        (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = VENTA.id_empleado) AS id_jefe,
        estatus,
        tipo,
        monto,
        observacion
FROM VENTA,VENTA_EXTRA
WHERE VENTA.id_venta = VENTA_EXTRA.id_venta AND tipo_venta = 'Extra';

-- vista para consultar datos del cliente para el ticket
DROP VIEW IF EXISTS VISTA_CLIENTE_TICKET;
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