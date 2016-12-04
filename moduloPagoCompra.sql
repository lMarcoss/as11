USE aserradero;

-- Disparador para actualizar los id_pago en la tabla ENTRADA_MADERA_ROLLO: 
-- Se actualizan todos los que tienen como id_pago = 0 con el nuevo id_pago de PAGO_COMPRA
DROP TRIGGER IF EXISTS ACTUALIZAR_ENTRADA_MADERA;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_ENTRADA_MADERA AFTER INSERT ON PAGO_COMPRA
FOR EACH ROW
BEGIN
	-- actualizamos todos los registros que tienen como id_pago = 0
	UPDATE ENTRADA_MADERA_ROLLO SET id_pago = NEW.id_pago WHERE id_pago = 0 AND id_proveedor = new.id_proveedor;
END;//
DELIMITER ;

-- Procedimiento para obtener monto del pago de las comprar a un proveedor sumando cuenta pendiente del ultimo pago si existe
DROP FUNCTION IF EXISTS OBTENER_MONTO_NUEVO_PAGO;
DELIMITER //
CREATE FUNCTION OBTENER_MONTO_NUEVO_PAGO (_id_proveedor CHAR(26))
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _monto_por_pagar decimal(15,2); -- Monto pendiente del último pago
    DECLARE _hay_compra decimal(15,2); -- Monto total de las nuevas compras
    
    -- monto pendiente por pagar al proveedor
    IF EXISTS(SELECT id_persona FROM C_POR_PAGAR_PROVEEDOR WHERE id_persona = _id_proveedor)THEN
		SELECT monto INTO _monto_por_pagar FROM C_POR_PAGAR_PROVEEDOR WHERE id_persona = _id_proveedor;
	ELSE 
		SET _monto_por_pagar = 0;
    END IF;
    
    -- monto de las nuevas compras
    if exists (SELECT id_proveedor FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0 AND id_proveedor = _id_proveedor) then
		SET _hay_compra = 1;
	else 
		set _hay_compra = 0;
    end if;
    
    -- Solo se hacen pagos si hay compras
	IF(_hay_compra = 1)THEN 
		return _monto_por_pagar;
	ELSE
        RETURN 0;
    END IF;
END;//
DELIMITER ;
SELECT * FROM PROVEEDOR;
SELECT SUM(monto_total) FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0 AND id_proveedor = 'COXN20160915HOCRXXMASL1993';
SELECT monto FROM C_POR_PAGAR_PROVEEDOR WHERE id_persona = 'COXN20160915HOCRXXMASL1993';
SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO;
-- Muestra todos los pagos que se han hecho
DROP VIEW IF EXISTS VISTA_PAGO_COMPRA;
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

-- Procedimiento para obtener cuenta por cobrar al proveedor
DROP FUNCTION IF EXISTS OBTENER_CUENTA_POR_COBRAR;
DELIMITER //
CREATE FUNCTION OBTENER_CUENTA_POR_COBRAR (_id_proveedor CHAR(26))
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _cuenta_cobrar decimal(15,2); -- Cuenta por cobrar al proveedor
    
    -- Si existe cuenta por cobrar al proveedor
    IF EXISTS (SELECT id_persona FROM C_POR_COBRAR_PROVEEDOR WHERE id_persona = _id_proveedor) THEN
        SELECT monto INTO _cuenta_cobrar FROM C_POR_COBRAR_PROVEEDOR WHERE id_persona = _id_proveedor;
	ELSE 
		SET _cuenta_cobrar = 0;
    END IF;

    return _cuenta_cobrar;
END;//
DELIMITER ;

--  Se utiliza para consultar las comprar pendientes para pagar
DROP VIEW IF EXISTS VISTA_MONTO_PAGO_COMPRA;
CREATE VIEW VISTA_MONTO_PAGO_COMPRA AS
SELECT 
    id_proveedor,
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(PROVEEDOR.id_proveedor,1,18)) as proveedor,
    (SELECT OBTENER_MONTO_NUEVO_PAGO(id_proveedor)) monto_por_pagar, -- Si su valor es cero: no hay compras y no se hacen pagos
    (SELECT OBTENER_CUENTA_POR_COBRAR(id_proveedor)) AS cuenta_por_cobrar,
    id_jefe as id_administrador
FROM PROVEEDOR;

SELECT * FROM C_POR_PAGAR_PROVEEDOR;
SELECT * FROM PROVEEDOR;
SELECT OBTENER_CUENTA_POR_COBRAR('COXN20160915HOCRXXMASL1993');

SELECT * FROM VISTA_MONTO_PAGO_COMPRA;
SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO;

SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO;