USE aserradero;




-- Procedimiento para obtener monto del pago de las compras a un proveedor sumando cuenta pendiente del ultimo pago si existe
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

-- Procedimiento para obtener cuenta por cobrar al proveedor
DROP FUNCTION IF EXISTS EXISTE_ENTRADA_MADERA;
DELIMITER //
CREATE FUNCTION EXISTE_ENTRADA_MADERA (_id_proveedor CHAR(26))
RETURNS INT
BEGIN
	DECLARE _existe INT; -- Existe entradas pendientes por pagar
    
    -- Si existen entradas pendientes por pagar
    IF EXISTS (SELECT id_entrada FROM ENTRADA_MADERA_ROLLO WHERE id_pago = 0 LIMIT 1) THEN
        SET _existe = 1;
	ELSE 
		SET _existe = 0;
    END IF;

    return _existe;
END;//
DELIMITER ;

--  Se utiliza para consultar las compras pendientes para pagar
DROP VIEW IF EXISTS VISTA_MONTO_PAGO_COMPRA;
CREATE VIEW VISTA_MONTO_PAGO_COMPRA AS
SELECT 
    id_proveedor,
    (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(PROVEEDOR.id_proveedor,1,18)) as proveedor,
    (SELECT OBTENER_MONTO_NUEVO_PAGO(id_proveedor)) monto_por_pagar, -- monto por pagar hasta la fecha del pago
    (SELECT OBTENER_CUENTA_POR_COBRAR(id_proveedor)) AS cuenta_por_cobrar,
    (SELECT EXISTE_ENTRADA_MADERA(id_proveedor)) AS existe_entrada, -- si existe regresa un 1 y si no regresa 0
    id_jefe as id_administrador
FROM PROVEEDOR;
SELECT * FROM VISTA_MONTO_PAGO_COMPRA WHERE existe_entrada > 0;
