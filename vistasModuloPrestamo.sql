use aserradero;
DROP VIEW IF EXISTS VISTA_PRESTAMO ;
CREATE VIEW VISTA_PRESTAMO AS
SELECT
	id_prestamo,
    fecha,
    id_prestador,
    (SELECT CONCAT(nombre, " ", apellido_paterno, " ", apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(PRESTAMO.id_prestador,1,18)) AS prestador,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(PRESTAMO.id_empleado,1,18))as empleado,
    (select id_jefe FROM EMPLEADO WHERE id_empleado = id_empleado limit 1) as id_administrador,
    monto_prestamo,
    interes,
    ROUND((monto_prestamo * (interes/100)),2) AS interes_mensual,
    ROUND((monto_prestamo - (SELECT MONTO_A_PAGAR(id_prestamo))),2) AS monto_pagado,
    ROUND((SELECT MONTO_A_PAGAR(id_prestamo)),2) AS monto_por_pagar
FROM PRESTAMO;
SELECT * FROM PRESTAMO;
SELECT * FROM VISTA_PRESTAMO;
DROP VIEW IF EXISTS PRESTAMO_TOTAL_PERSONA;
CREATE VIEW PRESTAMO_TOTAL_PERSONA AS
SELECT 
	id_administrador,
	id_prestador,
    prestador,
    SUM(monto_prestamo) as monto_total,
    SUM(interes_mensual) as interes_total
FROM VISTA_PRESTAMO
GROUP BY id_administrador, id_prestador, prestador;


SELECT * FROM PAGO_PRESTAMO;
SELECT * FROM VISTA_PAGO_PRESTAMO;