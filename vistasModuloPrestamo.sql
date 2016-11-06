use aserradero;
DROP VIEW IF EXISTS VISTA_PRESTAMO ;
CREATE VIEW VISTA_PRESTAMO AS
SELECT
	id_prestamo,
    fecha,
    id_persona,
    (SELECT CONCAT(nombre, " ", apellido_paterno, " ", apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(PRESTAMO.id_persona,1,18)) AS persona,
    id_administrador,
    monto,
    interes,
    (monto * (interes/100)) AS interes_mensual
FROM PRESTAMO;
