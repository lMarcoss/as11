USE aserradero;

DROP TABLE IF EXISTS PAGO_PRESTAMO;
CREATE TABLE PAGO_PRESTAMO(
	id_pago 		INT NOT NULL AUTO_INCREMENT,
    id_prestamo 	INT,
    fecha 			DATE,
    id_empleado		VARCHAR(26),
    monto_pago		DECIMAL(15,2),
    PRIMARY KEY(id_pago, id_prestamo),
    FOREIGN KEY(id_prestamo) REFERENCES PRESTAMO (id_prestamo))ENGINE=InnoDB;


-- funcion para consultar el monto por pagar de un prestamo
DROP FUNCTION IF EXISTS MONTO_A_PAGAR;
DELIMITER //
CREATE FUNCTION MONTO_A_PAGAR (_id_prestamo INT)
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _monto_prestamo		 	decimal(15,2);
	DECLARE _monto_pagar		 	decimal(15,2);
    DECLARE _monto_pagado		 	decimal(15,2);
        
    -- consultamos el monto del prestamo
    SELECT monto_prestamo INTO _monto_prestamo FROM PRESTAMO WHERE id_prestamo = _id_prestamo;
    
	-- Existe cuenta por cobrar al proveedor?
    IF EXISTS (SELECT monto_pagado FROM VISTA_PRESTAMO_PAGADO WHERE id_prestamo = _id_prestamo limit 1) THEN 
		SELECT monto_pagado INTO _monto_pagado FROM VISTA_PRESTAMO_PAGADO WHERE id_prestamo = _id_prestamo limit 1;
        SET _monto_pagar = _monto_prestamo - _monto_pagado;
        RETURN _monto_pagar;
	ELSE -- No existe cuenta por cobrar al proveedor
		RETURN _monto_prestamo;
    END IF;
END;//
DELIMITER ;

DROP VIEW IF EXISTS VISTA_PAGO_PRESTAMO;
CREATE VIEW VISTA_PAGO_PRESTAMO AS
SELECT 	
		PP.id_pago,
		PP.id_prestamo,
        PP.fecha,
        (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = PP.id_empleado limit 1) AS id_administrador,
        PP.id_empleado,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(PP.id_empleado,1,18))as empleado,
        P.id_prestador,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(P.id_prestador,1,18))as prestador,
        P.monto_prestamo,
        PP.monto_pago,        
        ROUND((SELECT MONTO_A_PAGAR(PP.id_prestamo)),2) as monto_por_pagar
FROM PAGO_PRESTAMO AS PP,PRESTAMO AS P WHERE PP.id_prestamo = P.id_prestamo;


DROP VIEW IF EXISTS VISTA_PRESTAMO_PAGADO;
CREATE VIEW VISTA_PRESTAMO_PAGADO AS
SELECT 
    id_prestamo,
    SUM(monto_pago) as monto_pagado
FROM PAGO_PRESTAMO
GROUP BY id_prestamo;


