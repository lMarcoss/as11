-- DELETE FROM ENTRADA_MADERA_ROLLO WHERE id_entrada = 1;
-- describe ENTRADA_MADERA_ROLLO;
USE aserradero;
CREATE TABLE PAGO_COMPRA(
	id_pago				INT NOT NULL AUTO_INCREMENT,
    fecha 				DATE,
    id_proveedor		CHAR(26) NOT NULL,
    monto_pago 			DECIMAL(10,2),
    monto_por_pagar		DECIMAL(10,2),
 	PRIMARY KEY (id_pago),
    FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor))ENGINE=InnoDB;

-- Disparador para actualizar los id_pago en la tabla ENTRADA_MADERA_ROLLO: 
-- Se actualizan todos los que tienen como id_pago = 0 con el nuevo id_pago de PAGO_COMPRA
DROP TRIGGER IF EXISTS PAGO_COMRA;
DELIMITER //
CREATE TRIGGER PAGO_COMRA AFTER INSERT ON PAGO_COMPRA
FOR EACH ROW
BEGIN
	-- actualizamos todos los registros que tienen como id_pago = 0
	UPDATE ENTRADA_MADERA_ROLLO SET id_pago = NEW.id_pago WHERE id_pago = 0 AND id_proveedor = new.id_proveedor;
END;//
DELIMITER ;

-- Procedimiento de actualización de cuentas para anticipo clientes
DROP FUNCTION IF EXISTS OBTENER_MONTO_NUEVO_PAGO;
DELIMITER //
CREATE FUNCTION OBTENER_MONTO_NUEVO_PAGO (_id_proveedor CHAR(26))
RETURNS DECIMAL(10,2)
BEGIN
	DECLARE _monto_por_pagar decimal(10,2); -- Monto pendiente del último pago
    DECLARE _monto_compra decimal(10,2); -- Monto total de las nuevas compras
    DECLARE _monto_total decimal(10,2); -- para guardar la suma
    
    -- monto pendiente del último pago: si no existe entonces es cero
    IF EXISTS (SELECT PAGO_COMPRA.monto_por_pagar FROM PAGO_COMPRA WHERE id_proveedor = _id_proveedor) THEN
		SELECT PAGO_COMPRA.monto_por_pagar INTO _monto_por_pagar FROM PAGO_COMPRA WHERE id_proveedor = _id_proveedor order by id_pago desc limit 1;
	ELSE 
		SET _monto_por_pagar = 0;
    END IF;
    
    
    -- monto de las nuevas compras
    if exists (SELECT id_proveedor FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0 AND id_proveedor = _id_proveedor) then
		SELECT SUM(monto_total) INTO _monto_compra FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0 AND id_proveedor = _id_proveedor;
	else 
		set _monto_compra = 0;
    end if;
    
    -- sumamos los dos montos consultados
    SET _monto_total = _monto_por_pagar + _monto_compra;
    return _monto_total;
END;//
DELIMITER ;

select OBTENER_MONTO_NUEVO_PAGO('PEXF20160910HOCRXRPAXA2016');

INSERT INTO PAGO_COMPRA (fecha,id_proveedor,monto_pago,monto_por_pagar) VALUES (curdate(),'PEXF20160910HOCRXRPAXA2016',423,12);
SELECT * FROM ENTRADA_MADERA_ROLLO;
drop table PAGO_COMPRA;


SELECT SUM(monto_total) FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0;

SELECT * FROM PAGO_COMPRA order by id_pago desc limit 1;
SELECT max(id_pago),id_proveedor,monto_por_pagar FROM PAGO_COMPRA;
SELECT * FROM PAGO_COMPRA;

SELECT MAX(id_pago) FROM PAGO_COMPRA;
CREATE VIEW VISTA_PAGO_COMPRA AS
SELECT 
	id_pago,
    fecha,
    id_proveedor,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(PAGO_COMPRA.id_proveedor,1,18)) as proveedor,
    (SELECT id_jefe FROM PROVEEDOR WHERE id_proveedor = PAGO_COMPRA.id_proveedor)AS id_administrador,
    monto_pago,
    monto_por_pagar
FROM PAGO_COMPRA;

DESCRIBE PAGO_COMPRA;
DESCRIBE VISTA_PAGO_COMPRA;
describe OTRO_GASTO;
SELECT * FROM ADMINISTRADOR;
select * from PROVEEDOR;
select * from VISTA_PAGO_COMPRA;
update ENTRADA_MADERA_ROLLO set id_pago = 0 where id_entrada = 1;

DROP VIEW IF EXISTS VISTA_MONTO_PAGO_COMPRA;
CREATE VIEW VISTA_MONTO_PAGO_COMPRA AS
SELECT 
    id_proveedor,
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(PROVEEDOR.id_proveedor,1,18)) as proveedor,
    (SELECT OBTENER_MONTO_NUEVO_PAGO(id_proveedor)) monto_por_pagar,
    id_jefe as id_administrador
FROM PROVEEDOR;
SELECT * FROM PROVEEDOR;

SELECT * FROM VISTA_MONTO_PAGO_COMPRA where monto_por_pagar > 0;

SELECT * FROM VISTA_PAGO_COMPRA;

select * from PAGO_COMPRA;
SELECT * from VISTA_ENTRADA_MADERA_ROLLO;