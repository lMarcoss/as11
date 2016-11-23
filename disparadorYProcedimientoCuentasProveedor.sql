use aserradero;
-- Disparador para insertar datos en la tabla cuentas por pagar cada que se hace un anticipoProveedor
-- verifica si hay se tiene cuenta pro cobrar con el cliente para restarle y si no, entonces inserta en cuentas por pagar
DROP TRIGGER IF EXISTS UPDATE_CUENTAS_AFTER_ANTICIPO_PROVEEDOR;
DELIMITER //
CREATE TRIGGER UPDATE_CUENTAS_AFTER_ANTICIPO_PROVEEDOR  AFTER INSERT ON ANTICIPO_PROVEEDOR
FOR EACH ROW
BEGIN
	CALL UPDATE_CUENTAS_AFTER_ANTICIPO_PROVEEDOR(NEW.fecha,NEW.id_proveedor,NEW.id_empleado,NEW.monto_anticipo);
END;//
DELIMITER ;

-- Procedimiento de actualización de cuentas para anticipo proveedores
DROP PROCEDURE IF EXISTS UPDATE_CUENTAS_AFTER_ANTICIPO_PROVEEDOR;
DELIMITER //
CREATE PROCEDURE UPDATE_CUENTAS_AFTER_ANTICIPO_PROVEEDOR (IN fecha_a DATE,IN id_proveedor_a CHAR(26),IN id_empleado_a CHAR(26), IN monto_anticipo_a DECIMAL(15,2))
BEGIN
    -- si debemos dinero al proveedor se paga con el anticipo
	IF EXISTS (SELECT id_persona FROM CUENTA_POR_PAGAR WHERE id_persona = id_proveedor_a) THEN
		-- Si la cantidad que le debemos al proveedor es menor al anticipo entonces lo demas se va en cuentas por cobrar
		IF (SELECT monto FROM CUENTA_POR_PAGAR WHERE id_persona = id_proveedor_a) - monto_anticipo_a < 0 THEN
			INSERT INTO CUENTA_POR_COBRAR SET
				CUENTA_POR_COBRAR.id_persona = id_proveedor_a,
				CUENTA_POR_COBRAR.monto = ABS((SELECT monto FROM CUENTA_POR_PAGAR WHERE id_persona = id_proveedor_a) - monto_anticipo_a)
			ON DUPLICATE KEY UPDATE 
				CUENTA_POR_COBRAR.monto= CUENTA_POR_COBRAR.monto+ABS((SELECT monto FROM CUENTA_POR_PAGAR WHERE id_persona = id_proveedor_a) - monto_anticipo_a);
            
			DELETE FROM CUENTA_POR_PAGAR WHERE id_persona = id_proveedor_a;
		ELSE -- y si no es menor entonces se resta 
			UPDATE CUENTA_POR_PAGAR SET CUENTA_POR_PAGAR.monto = CUENTA_POR_PAGAR.monto - monto_anticipo_a WHERE CUENTA_POR_PAGAR.id_persona = id_proveedor_a;
		END IF;
        
        -- eliminamos la cuenta del proveedor en cuentas por pagar si es cero
        IF (SELECT monto FROM CUENTA_POR_PAGAR WHERE id_persona = id_proveedor_a) = 0 THEN
			DELETE FROM CUENTA_POR_PAGAR WHERE id_persona = id_proveedor_a;
		END IF;
        
	ELSE -- si no debemos dinero al proveedor se inserta como cuenta por cobrar
		INSERT INTO CUENTA_POR_COBRAR SET
				CUENTA_POR_COBRAR.id_persona = id_proveedor_a,
				CUENTA_POR_COBRAR.monto = monto_anticipo_a
			ON DUPLICATE KEY UPDATE 
				CUENTA_POR_COBRAR.monto= CUENTA_POR_COBRAR.monto + monto_anticipo_a;
    END IF;
END;//
DELIMITER ;

-- Disparador para actualizar cuentas (Cuentas por pagar y por cobrar, Cuando se modifica el anticipo: controla los errores de anticipo)
DROP TRIGGER IF EXISTS ACTUALIZAR_CUENTAS_PROVEEDOR;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_CUENTAS_PROVEEDOR  BEFORE UPDATE ON ANTICIPO_PROVEEDOR
FOR EACH ROW
BEGIN
	DECLARE cuenta_pagar_existente decimal(15,2);
    DECLARE cuenta_cobrar_existente decimal(15,2);
    DECLARE cuenta_pagar_nuevo decimal(15,2);
    DECLARE cuenta_cobrar_nuevo decimal(15,2);
    
    -- Consultamos cuenta por pagar
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_PAGAR WHERE id_persona = new.id_proveedor) THEN
		SELECT monto INTO cuenta_pagar_existente FROM CUENTA_POR_PAGAR WHERE id_persona = NEW.id_proveedor;
        DELETE FROM CUENTA_POR_PAGAR WHERE id_persona = NEW.id_proveedor;
    ELSE 
		SET cuenta_pagar_existente = 0;
	END IF;
    
	-- Consultamos cuenta por cobrar
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_proveedor) THEN
		SELECT monto INTO cuenta_cobrar_existente FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_proveedor;
        DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_proveedor;
    ELSE 
		SET cuenta_cobrar_existente = 0;
	END IF;
    
    -- Si hay cuenta por pagar sumamos el anticipo antiguo
    IF(cuenta_pagar_existente > 0 AND cuenta_cobrar_existente = 0) THEN 
        SET cuenta_pagar_nuevo = cuenta_pagar_existente + OLD.monto_anticipo;
        IF(cuenta_pagar_nuevo > 0) THEN 
			INSERT INTO CUENTA_POR_PAGAR VALUES (NEW.id_proveedor,ABS(cuenta_pagar_nuevo));
		ELSE IF(cuenta_pagar_nuevo < 0) THEN
				INSERT INTO CUENTA_POR_COBRAR VALUES (NEW.id_proveedor,ABS(cuenta_pagar_nuevo));
			END IF;
		END IF;
	-- Si hay cuenta por cobrar le restamos el anticipo antiguo
	ELSE IF(cuenta_pagar_existente = 0 AND cuenta_cobrar_existente > 0) THEN
			SET cuenta_cobrar_nuevo = cuenta_cobrar_existente - OLD.monto_anticipo;
			IF(cuenta_cobrar_nuevo > 0)THEN
				INSERT INTO CUENTA_POR_COBRAR VALUES (NEW.id_proveedor,ABS(cuenta_cobrar_nuevo));
			ELSE IF(cuenta_cobrar_nuevo < 0)THEN
					INSERT INTO CUENTA_POR_PAGAR VALUES (NEW.id_proveedor,ABS(cuenta_cobrar_nuevo));
				END IF;
			END IF;
		-- si cxp y cxc estan en cero la modificacion se inserta en cuenta por pagar para despues actualizarse con el nuevo valor de anticipo
		ELSE IF(cuenta_pagar_existente = 0 AND cuenta_cobrar_existente = 0) THEN 
				SET cuenta_pagar_nuevo = OLD.monto_anticipo;
				INSERT INTO CUENTA_POR_PAGAR VALUES (NEW.id_proveedor,ABS(cuenta_pagar_nuevo));
			END IF;
		END IF;
	END IF;
    
    -- insertamos la modificación como nuevo: para actualizar cuentas por pagar y por cobrar
    CALL UPDATE_CUENTAS_AFTER_ANTICIPO_PROVEEDOR(NEW.fecha,NEW.id_proveedor,NEW.id_empleado,NEW.monto_anticipo);
    
END;//
DELIMITER ;


CREATE VIEW VISTA_ANTICIPO_PROVEEDOR AS
SELECT id_anticipo_P,
		fecha,
        id_proveedor,
        (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(ANTICIPO_PROVEEDOR.id_proveedor,1,18)) as proveedor,
        id_empleado,
        (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(ANTICIPO_PROVEEDOR.id_empleado,1,18)) as empleado,
        (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = ANTICIPO_PROVEEDOR.id_empleado) as id_jefe,
        monto_anticipo
FROM ANTICIPO_PROVEEDOR;
-- SELECT * FROM VISTA_ANTICIPO_PROVEEDOR;