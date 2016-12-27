use aserradero;

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
    tipo_pago
FROM VENTA
ORDER BY fecha DESC;

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
    V.tipo_pago,
    sum(VE.monto) as monto
FROM VENTA as V, VENTA_EXTRA as VE
WHERE V.id_venta = VE.id_venta
GROUP BY id_venta;


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
    V.tipo_pago, 
    sum(VM.monto) as monto
FROM VENTA as V, VENTA_MAYOREO as VM
WHERE V.id_venta = VM.id_venta
GROUP BY id_venta;

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
    V.tipo_pago, 
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

