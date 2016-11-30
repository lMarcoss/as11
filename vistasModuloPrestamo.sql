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
    ROUND((ROUND((SELECT MONTO_A_PAGAR(id_prestamo)),2) * (interes/100)),2) AS interes_mensual,
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


-- funcion para generar el id_del prestador al insertar un prestamo
DROP FUNCTION IF EXISTS GENERAR_ID;
DELIMITER //
CREATE FUNCTION GENERAR_ID (_id VARCHAR(26), _id_empleado VARCHAR(26))
RETURNS VARCHAR(26)
BEGIN
	DECLARE _id_administrador	VARCHAR(26);
    DECLARE _id_nuevo			VARCHAR(26);
        
    -- consultamos el id del administrador
    SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = _id_empleado LIMIT 1;
    
    -- contatenamos el _id con las primeras 8 caracteres del id del administrador
    SET _id_nuevo = CONCAT(_id, SUBSTRING(_id_administrador, 1, 8));
    
	RETURN _id_nuevo;
END;//
DELIMITER ;

-- disparador para generar el id_del prestador antes de insertar un prestamo
DROP TRIGGER IF EXISTS CREAR_ID;
DELIMITER //
CREATE TRIGGER CREAR_ID BEFORE INSERT ON PRESTAMO
FOR EACH ROW
BEGIN
	SET NEW.id_prestador = (SELECT GENERAR_ID(NEW.id_prestador, NEW.id_empleado));
END;//
DELIMITER ;