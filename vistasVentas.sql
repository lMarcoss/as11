use aserradero;

-- vistas --------------------------------------------------------------------------------------------------



-- Vista para reportes y ticket de ventas por paquete
-- DROP VIEW IF EXISTS VISTA_VENTAS_POR_PAQUETE;
-- CREATE VIEW VISTA_VENTAS_POR_PAQUETE AS
-- SELECT 
-- 		fecha,
-- 		VENTA.id_venta,
-- 		id_cliente,
--         (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as cliente,
-- 		(SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as direccion_cliente,
--         VENTA.id_empleado,
--         (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_empleado,1,18) LIMIT 1) as empleado,
--         estatus,
--         numero_paquete,
--         MADERA_ASERRADA_CLASIF.id_madera,
--         grueso,
--         ancho,
--         largo,
--         MADERA_ASERRADA_CLASIF.volumen as volumen_unitario,
--         ROUND((VENTA_PAQUETE.monto /VENTA_PAQUETE.volumen),2) as costo_volumen,
--         num_piezas,
--         VENTA_PAQUETE.volumen as volumen_total,
--         VENTA_PAQUETE.monto as costo_total,
--         tipo_madera
-- FROM VENTA,VENTA_PAQUETE,MADERA_ASERRADA_CLASIF
-- WHERE VENTA.id_venta = VENTA_PAQUETE.id_venta AND
-- 		VENTA_PAQUETE.id_madera = MADERA_ASERRADA_CLASIF.id_madera AND
--         VENTA.tipo_venta='Paquete';

-- VISTA para reportes y ticket de ventas por mayoreo
DROP VIEW IF EXISTS VISTA_VENTAS_POR_MAYOREO;
CREATE VIEW VISTA_VENTAS_POR_MAYOREO AS
SELECT fecha,
		VENTA.id_venta,
        id_cliente,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as cliente,
        (select id_jefe from CLIENTE where id_cliente = VENTA.id_cliente LIMIT 1) as id_jefe,
		(SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as direccion_cliente,
		VENTA.id_empleado, 
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_empleado,1,18) LIMIT 1) as empleado,
		estatus,
        MADERA_ASERRADA_CLASIF.id_madera as id_madera,
        grueso_f,
        ancho_f,
        largo_f,
        MADERA_ASERRADA_CLASIF.volumen as volumen_unitario,
        ROUND((VENTA_MAYOREO.monto /VENTA_MAYOREO.volumen),2) as costo_volumen,
        num_piezas,
        VENTA_MAYOREO.volumen AS volumen_total,
        VENTA_MAYOREO.monto as costo_total,
        tipo_madera
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
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_cliente,1,18) LIMIT 1) as cliente,
        (SELECT direccion FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_cliente,1,18) LIMIT 1) as direccion_cliente,
        id_empleado,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(VENTA.id_empleado,1,18) LIMIT 1) as empleado,
        (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = VENTA.id_empleado LIMIT 1) AS id_jefe,
        estatus,
        tipo,
        monto,
        observacion
FROM VENTA,VENTA_EXTRA
WHERE VENTA.id_venta = VENTA_EXTRA.id_venta AND tipo_venta = 'Extra';

-- vista para consultar datos del cliente para el ticket
-- DROP VIEW IF EXISTS VISTA_CLIENTE_TICKET;
-- CREATE VIEW VISTA_CLIENTE_TICKET AS
-- SELECT 
-- 	VENTA.id_venta,
--     VENTA.fecha,
--     VENTA.tipo_venta,
--     CLIENTE.id_jefe,
--     CLIENTE.id_cliente,
--     PERSONA.id_persona, 
-- 	concat(nombre," ", apellido_paterno, " ", apellido_materno) as cliente,
-- 	direccion,
--     LOCALIDAD.nombre_localidad as localidad,
--     MUNICIPIO.nombre_municipio as municipio,
--     MUNICIPIO.estado
-- FROM VENTA,CLIENTE,PERSONA,LOCALIDAD,MUNICIPIO
-- WHERE VENTA.id_cliente = CLIENTE.id_cliente AND
-- 		CLIENTE.id_persona = PERSONA.id_persona AND
--             PERSONA.nombre_localidad = LOCALIDAD.nombre_localidad AND
--             LOCALIDAD.nombre_municipio = MUNICIPIO.nombre_municipio;

SELECT * FROM VENTA_PAQUETE;
SELECT monto FROM VISTA_VENTA_PAQUETE WHERE id_venta = '1483053023248';
SELECT * FROM VENTA_PAQUETE where id_venta = '1483053023248';
SELECT * FROM VISTA_VENTA_PAQUETE where id_venta = '1483053586825';
SELECT * FROM VENTA_PAQUETE WHERE id_venta = '1483053586825';
SELECT sum(monto) AS monto FROM VENTA_PAQUETE WHERE id_venta = '1483053586825' GROUP BY id_venta;
SELECT * FROM VENTA_PAQUETE;

SELECT  id_madera,grueso,ancho,largo, volumen_unitario,num_piezas,costo_volumen,volumen_total,costo_total FROM VISTA_VENTAS_POR_PAQUETE WHERE id_venta = '1483054391693' AND numero_paquete = 1;

SELECT * 
FROM VENTA_PAQUETE AS V,MADERA_ASERRADA_CLASIF AS C
WHERE V.id_madera = C.id_madera AND V.id_administrador = C.id_administrador;

SELECT numero_paquete,SUM(costo_total) FROM VENTA_PAQUETE;
-- SELECT  AS monto_total_paquete FROM VISTA_VENTAS_POR_PAQUETE  WHERE id_venta = ? GROUP BY numero_paquete ORDER BY numero_paquete;
SELECT numero_paquete, SUM(monto) AS monto_total_paquete FROM VENTA_PAQUETE WHERE id_venta = '1483054391693' GROUP BY numero_paquete ORDER BY numero_paquete ASC;
SELECT SUM(monto) AS monto_total_paquete FROM VENTA_PAQUETE WHERE id_venta = '1483054391693' GROUP BY id_venta;
SELECT  id_madera,grueso,ancho,largo, volumen_unitario,num_piezas,costo_volumen,volumen_total,costo_total FROM VENTA_PAQUETE WHERE id_venta = '1483054391693' AND numero_paquete = 1;

-- SELECT C.id_madera, C.grueso, C.ancho, C.largo, C.volumen AS volumen_unitario, V.num_piezas, C.costo_por_volumen AS costo_volumen, ROUND((V.num_piezas * C.volumen),3) AS volumen_total, ROUND((V.num_piezas * C.volumen * C.costo_por_volumen),2) AS costo_total FROM VENTA_PAQUETE AS V,MADERA_ASERRADA_CLASIF AS C WHERE V.id_madera = C.id_madera AND V.id_administrador = C.id_administrador AND id_venta = '1483054391693' AND numero_paquete = 3;
SELECT C.id_madera, C.grueso, C.ancho, C.largo, C.volumen AS volumen_unitario, V.num_piezas, C.costo_por_volumen AS costo_volumen,tipo_madera, ROUND((V.num_piezas * C.volumen),3) AS volumen_total, ROUND((V.num_piezas * C.volumen * C.costo_por_volumen),2) AS costo_total FROM VENTA_PAQUETE AS V,MADERA_ASERRADA_CLASIF AS C WHERE V.id_madera = C.id_madera AND V.id_administrador = C.id_administrador AND id_venta = '1483054391693' AND numero_paquete = 3 AND tipo_madera = 'Madera';
        
SELECT 
	numero_paquete, 
    SUM(monto) AS monto_total_paquete 
FROM VENTA_PAQUETE 
WHERE id_venta = '1483054391693' AND tipo_madera =  'Madera'
GROUP BY numero_paquete 
ORDER BY numero_paquete ASC;

SELECT SUM(monto) FROM VENTA_PAQUETE WHERE id_venta = '1483068505882' AND tipo_madera =  'Madera' GROUP BY id_venta;
SELECT SUM(monto) AS monto FROM VENTA_PAQUETE WHERE id_venta = '1483068505882' AND tipo_madera =  'Madera';

SELECT sum(monto) AS monto FROM VENTA_PAQUETE WHERE id_venta = '1483068505882' GROUP BY id_venta;
SELECT *
FROM VENTA_PAQUETE;
SELECT * FROM MADERA_ASERRADA_CLASIF;

SELECT SUM(monto) AS monto FROM VENTA_PAQUETE WHERE id_venta = '1483068505882' AND tipo_madera =  'Amarre' GROUP BY id_venta;
SELECT * FROM VENTA_PAQUETE;


SELECT * FROM VENTA_PAQUETE,MADERA_ASERRADA_CLASIF;

SELECT 
	V.id_madera, V.num_piezas, V.volumen, V.monto
FROM VENTA_PAQUETE AS V,MADERA_ASERRADA_CLASIF AS C
WHERE V.id_madera = C.id_madera AND V.id_administrador = C.id_administrador;

SELECT V.id_madera, SUM(V.num_piezas) AS num_piezas, SUM(V.volumen) AS volumen, SUM(V.monto) AS monto FROM VENTA_PAQUETE AS V GROUP BY V.id_madera;

SELECT * FROM VENTA;

SELECT 
	id_venta,
    fecha,
    id_cliente,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as cliente,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) as empleado,
    estatus,
    tipo_venta,
    pago
FROM VENTA;

SELECT id_venta, fecha, id_cliente, (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_cliente,1,18) LIMIT 1) as cliente, id_empleado, (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) as empleado, estatus, tipo_venta, pago FROM VENTA WHERE id_venta = '1482821862299';


SELECT * FROM VISTA_VENTA;

SELECT C.id_madera, C.grueso, C.ancho, C.largo, C.volumen AS volumen_unitario, V.num_piezas, C.costo_por_volumen AS costo_volumen, tipo_madera, ROUND((V.num_piezas * C.volumen),3) AS volumen_total, ROUND((V.num_piezas * C.volumen * C.costo_por_volumen),2) AS costo_total FROM VENTA_MAYOREO AS V, MADERA_ASERRADA_CLASIF AS C WHERE V.id_madera = C.id_madera AND V.id_administrador = C.id_administrador AND tipo_madera = 'Madera';

SELECT * FROM VENTA_MAYOREO WHERE id_venta = '1483404096876';
(SELECT * FROM VISTA_VENTA_PAQUETE) UNION (SELECT * FROM VISTA_VENTA_MAYOREO) UNION (SELECT * FROM VISTA_VENTA_EXTRA);
SELECT * FROM VISTA_VENTA_MAYOREO;
SELECT * FROM VISTA_VENTA_PAQUETE;
SELECT * FROM VISTA_VENTA_EXTRA;
SELECT * 
FROM ((SELECT * FROM VISTA_VENTA_PAQUETE) UNION (SELECT * FROM VISTA_VENTA_MAYOREO) UNION (SELECT * FROM VISTA_VENTA_EXTRA)) 
WHERE id_venta = '';
SELECT * FROM  VENTA_EXTRA;

SELECT * FROM MADERA_ASERRADA_CLASIF;
SELECT * FROM INVENTARIO_M_ASERRADA