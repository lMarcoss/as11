use aserradero;

-- Datos generales de cada venta
DROP VIEW IF EXISTS VISTA_VENTA;
CREATE VIEW VISTA_VENTA AS 
SELECT
	id_venta,
    fecha,
    id_cliente,
    id_empleado,
    (SELECT id_jefe FROM EMPLEADO where id_empleado = VENTA.id_empleado LIMIT 1) as id_jefe,
    estatus,
    tipo_venta,
    pago
FROM VENTA
ORDER BY fecha DESC;

-- detalle de cada venta extra
DROP VIEW IF EXISTS VISTA_VENTA_EXTRA;
CREATE VIEW VISTA_VENTA_EXTRA AS
SELECT 
	V.id_venta, 
    V.fecha, 
    V.id_cliente, 
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) AS cliente,
    V.id_empleado, 
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) AS empleado,
    (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = V.id_empleado LIMIT 1) AS id_jefe,
    V.estatus,
    V.tipo_venta,
    V.pago,
    sum(VE.monto) as monto
FROM VENTA as V, VENTA_EXTRA as VE
WHERE V.id_venta = VE.id_venta
GROUP BY id_venta;

-- Detalle de cada venta por mayoreo
DROP VIEW IF EXISTS VISTA_VENTA_MAYOREO;
CREATE VIEW VISTA_VENTA_MAYOREO AS
SELECT 
	V.id_venta, 
    V.fecha, 
    V.id_cliente, 
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) AS cliente,
    V.id_empleado, 
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) AS empleado,
    (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = V.id_empleado  LIMIT 1) AS id_jefe,
    V.estatus,
    V.tipo_venta,
    V.pago, 
    sum(VM.monto) as monto
FROM VENTA as V, VENTA_MAYOREO as VM
WHERE V.id_venta = VM.id_venta
GROUP BY id_venta;

-- Detalle de cada venta por paquete
DROP VIEW IF EXISTS VISTA_VENTA_PAQUETE;
CREATE VIEW VISTA_VENTA_PAQUETE AS
SELECT 
	V.id_venta, 
    V.fecha, 
    V.id_cliente, 
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) AS cliente,
    V.id_empleado, 
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) AS empleado,
    (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = V.id_empleado LIMIT 1) AS id_jefe,
    V.estatus, 
    V.tipo_venta,
    V.pago, 
    sum(VP.monto) as monto
FROM VENTA as V, VENTA_PAQUETE as VP
WHERE V.id_venta = VP.id_venta
GROUP BY id_venta;

-- DROP VIEW IF EXISTS VENTA_EXTRA_Y_DETALLE;
-- CREATE VIEW VENTA_EXTRA_Y_DETALLE AS
-- SELECT 
-- 	fecha,
-- 	VENTA.id_venta,
-- 	id_cliente,(select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18)  LIMIT 1) as cliente,
-- 	(SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as direccion_cliente,
-- 	id_empleado,
-- 	(select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) as empleado,
-- 	estatus,
-- 	tipo,
-- 	monto,
-- 	observacion
-- FROM VENTA,VENTA_EXTRA
-- WHERE VENTA.id_venta = VENTA_EXTRA.id_venta AND tipo_venta = 'Extra';
-- 

-- vista para consultar datos del cliente para el ticket
DROP VIEW IF EXISTS VISTA_CLIENTE_TICKET;
CREATE VIEW VISTA_CLIENTE_TICKET AS
SELECT 
	VENTA.id_venta,
    VENTA.fecha,
    VENTA.tipo_venta,
    CLIENTE.id_jefe,
    CLIENTE.id_cliente,
    PERSONA.id_persona, 
	concat(nombre," ", apellido_paterno, " ", apellido_materno) as cliente,
	direccion,
    LOCALIDAD.nombre_localidad as localidad,
    MUNICIPIO.nombre_municipio as municipio,
    MUNICIPIO.estado
FROM VENTA,CLIENTE,PERSONA,LOCALIDAD,MUNICIPIO
WHERE VENTA.id_cliente = CLIENTE.id_cliente AND
		CLIENTE.id_persona = PERSONA.id_persona AND
            PERSONA.nombre_localidad = LOCALIDAD.nombre_localidad AND
            LOCALIDAD.nombre_municipio = MUNICIPIO.nombre_municipio;
            
-- Vista para reportes y ticket de ventas por paquete
DROP VIEW IF EXISTS VISTA_VENTAS_POR_PAQUETE;
CREATE VIEW VISTA_VENTAS_POR_PAQUETE AS
SELECT 
		fecha,
		VENTA.id_venta,
		id_cliente,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as cliente,
		(SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as direccion_cliente,
        VENTA.id_empleado,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_empleado,1,18) LIMIT 1) as empleado,
        estatus,
        numero_paquete,
        MADERA_ASERRADA_CLASIF.id_madera,
        grueso_f,
        ancho_f,
        largo_f,
        MADERA_ASERRADA_CLASIF.volumen as volumen_unitario,
        ROUND((VENTA_PAQUETE.monto /VENTA_PAQUETE.volumen),2) as costo_volumen,
        num_piezas,
        VENTA_PAQUETE.volumen as volumen_total,
        VENTA_PAQUETE.monto as costo_total,
        tipo_madera
FROM VENTA,VENTA_PAQUETE,MADERA_ASERRADA_CLASIF
WHERE VENTA.id_venta = VENTA_PAQUETE.id_venta AND
		VENTA_PAQUETE.id_madera = MADERA_ASERRADA_CLASIF.id_madera AND
        VENTA.tipo_venta='Paquete';


-- SELECT * FROM VENTA_MAYOREO;
-- SELECT * FROM VENTA, VENTA_MAYOREO WHERE VENTA.id_venta = VENTA_MAYOREO.id_venta;