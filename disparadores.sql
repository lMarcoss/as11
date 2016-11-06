
-- INSERT INTO EMPLEADO_JEFE (id_empleado,id_jefe) VALUES ("MASL19931106HOCRNN","MASL19931106HOCRNN");
INSERT INTO ADMINISTRADOR VALUES ('MASL19931106HOCRNN');
-- INSERT INTO EMPLEADO VALUES ('12312312','1111','COXN20160915HOCRXX','Administrador','Activo');


-- Disparador para insertar datos en la tabla cuentas por pagar cada que se hace un anticipoCliente
-- verifica si hay se tiene cuenta pro cobrar con el cliente para restarle y si no, entonces inserta en cuentas por pagar
DROP TRIGGER IF EXISTS COBRAR_VENTA_ANTICIPADO;
DELIMITER //
CREATE TRIGGER COBRAR_VENTA_ANTICIPADO  AFTER UPDATE ON VENTA
FOR EACH ROW
BEGIN
	DECLARE totalCobrar decimal(10,2);
	IF (NEW.tipo_venta = 'Paquete' AND NEW.tipo_pago = 'Anticipado') THEN
		SELECT SUM(VENTA_PAQUETE.monto) INTO totalCobrar FROM VENTA_PAQUETE WHERE VENTA_PAQUETE.id_venta=NEW.id_venta;
        CALL COBRAR_VENTA_ANTICIPADO(totalCobrar,NEW.id_cliente);
		ELSE IF (NEW.tipo_venta = 'Mayoreo' AND NEW.tipo_pago = 'Anticipado') THEN
			SELECT SUM(VENTA_MAYOREO.monto) INTO totalCobrar FROM VENTA_MAYOREO WHERE VENTA_MAYOREO.id_venta=NEW.id_venta;
            CALL COBRAR_VENTA_ANTICIPADO(totalCobrar,NEW.id_cliente);
			ELSE IF (NEW.tipo_venta = 'Extra' AND NEW.tipo_pago = 'Anticipado') THEN
				SELECT SUM(VENTA_EXTRA.monto) INTO totalCobrar FROM VENTA_EXTRA WHERE VENTA_EXTRA.id_venta=NEW.id_venta;
                CALL COBRAR_VENTA_ANTICIPADO(totalCobrar,NEW.id_cliente);
			END IF;
		END IF;
	END IF;
END;//
DELIMITER ;

DROP PROCEDURE IF EXISTS COBRAR_VENTA_ANTICIPADO;
DELIMITER //
CREATE PROCEDURE COBRAR_VENTA_ANTICIPADO (IN totalCobrar DECIMAL(10,2),IN _id_cliente CHAR(26))
BEGIN
    DECLARE monto_disponible decimal(10,2);
    DECLARE monto_faltante decimal(10,2);
    -- Consultamos el monto a cobrar
    
    -- SI existe una cuenta por pagar al cliente
    IF EXISTS (SELECT CUENTA_POR_PAGAR.id_persona FROM CUENTA_POR_PAGAR WHERE CUENTA_POR_PAGAR.id_persona = _id_cliente) THEN
		-- Si hay cantidad suficiente en la cuenta del cliente lo descontamos
        IF (totalCobrar <= (SELECT CUENTA_POR_PAGAR.monto FROM CUENTA_POR_PAGAR WHERE CUENTA_POR_PAGAR.id_persona=_id_cliente)) THEN
			UPDATE CUENTA_POR_PAGAR SET CUENTA_POR_PAGAR.monto=CUENTA_POR_PAGAR.monto - totalCobrar WHERE CUENTA_POR_PAGAR.id_persona=_id_cliente;
		ELSE -- Si no alcanza dinero anticipado para comprar: se va a cuentas por cobrar: a favor del administrador al cliente
			-- Consulta saldo disponible
			SELECT CUENTA_POR_PAGAR.monto INTO monto_disponible FROM CUENTA_POR_PAGAR where id_persona = _id_cliente; 
			-- eliminamos la cuenta 
            DELETE FROM CUENTA_POR_PAGAR WHERE CUENTA_POR_PAGAR.id_persona=_id_cliente;
			-- si existe cuenta por cobrar del cliente entonces insertamos 
			IF EXISTS (SELECT CUENTA_POR_COBRAR.id_persona FROM CUENTA_POR_COBRAR WHERE CUENTA_POR_COBRAR.id_persona = _id_cliente) THEN
				UPDATE CUENTA_POR_COBRAR SET CUENTA_POR_COBRAR.monto = CUENTA_POR_COBRAR.monto + (totalCobrar - monto_disponible) WHERE CUENTA_POR_COBRAR.id_persona=_id_cliente;
			ELSE 
                INSERT INTO CUENTA_POR_COBRAR VALUES (_id_cliente,totalCobrar - monto_disponible);
			END IF;
		END IF;
	ELSE --  no hay cuenta por pagar al cliente
		IF EXISTS (SELECT CUENTA_POR_COBRAR.id_persona FROM CUENTA_POR_COBRAR WHERE CUENTA_POR_COBRAR.id_persona = _id_cliente) THEN
			UPDATE CUENTA_POR_COBRAR SET CUENTA_POR_COBRAR.monto = CUENTA_POR_COBRAR.monto + totalCobrar WHERE CUENTA_POR_COBRAR.id_persona=_id_cliente;
		ELSE 
			INSERT INTO CUENTA_POR_COBRAR VALUES (_id_cliente,totalCobrar);
		END IF;
	END IF;
END;//
DELIMITER ;
