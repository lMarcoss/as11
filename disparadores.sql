



-- Disparador para insertar datos en la tabla cuentas por pagar cada que se hace un anticipoCliente
-- verifica si hay se tiene cuenta pro cobrar con el cliente para restarle y si no, entonces inserta en cuentas por pagar
DROP TRIGGER IF EXISTS INSERTAR_CUENTA_POR_PAGAR;
DELIMITER //
CREATE TRIGGER INSERTAR_CUENTA_POR_PAGAR  AFTER INSERT ON ANTICIPO_CLIENTE
FOR EACH ROW
BEGIN
	-- si el cliente debe dinero, entonce se cobra 
	IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente) THEN
		-- Si la cantidad que el cliente debe es menor al anticipo entonces lo demas se va en cuentas por pagar
		IF (SELECT monto FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente) - NEW.monto_anticipo < 0 THEN
			INSERT INTO CUENTA_POR_PAGAR SET
				CUENTA_POR_PAGAR.id_persona = NEW.id_cliente,
				CUENTA_POR_PAGAR.monto = ABS((SELECT monto FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente) - NEW.monto_anticipo)
			ON DUPLICATE KEY UPDATE 
				CUENTA_POR_PAGAR.monto= CUENTA_POR_PAGAR.monto+ABS((SELECT monto FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente) - NEW.monto_anticipo);
            
			DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente;
		ELSE -- y si no es menor entonces se resta 
			UPDATE CUENTA_POR_COBRAR SET CUENTA_POR_COBRAR.monto = CUENTA_POR_COBRAR.monto - NEW.monto_anticipo WHERE CUENTA_POR_COBRAR.id_persona = NEW.id_cliente;
		END IF;
        
        -- eliminamos la cuenta del cliente en cuentas por cobrar si es cero
        IF (SELECT monto FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente) = 0 THEN
			DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente;
		END IF;
        
	ELSE -- si el cliente no debe dinero entonces se inserta como cuenta por pagar
		INSERT INTO CUENTA_POR_PAGAR SET
				CUENTA_POR_PAGAR.id_persona = NEW.id_cliente,
				CUENTA_POR_PAGAR.monto = NEW.monto_anticipo
			ON DUPLICATE KEY UPDATE 
				CUENTA_POR_PAGAR.monto= CUENTA_POR_PAGAR.monto + NEW.monto_anticipo;
    END IF;
END;//
DELIMITER ;

DROP TRIGGER IF EXISTS ACTUALIZAR_CUENTAS;

-- Disparador para actualizar cuentas (Cuentas por pagar y por cobrar)
DELIMITER //
CREATE TRIGGER ACTUALIZAR_CUENTAS  BEFORE UPDATE ON ANTICIPO_CLIENTE
FOR EACH ROW
BEGIN
	DECLARE cuenta_pagar_existente decimal(10,2);
    DECLARE cuenta_cobrar_existente decimal(10,2);
	DECLARE cuenta_pagar_nuevo decimal(10,2);
    DECLARE cuenta_cobrar_nuevo decimal(10,2);
    
    -- Consultamos cuenta por pagar
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_PAGAR WHERE id_persona = new.id_cliente) THEN
		SELECT monto INTO cuenta_pagar_existente FROM CUENTA_POR_PAGAR WHERE id_persona = NEW.id_cliente;
    ELSE 
		SET cuenta_pagar_existente = 0;
	END IF;
    -- restamos a cuenta existente el valor antiguo de anticipo
    set cuenta_pagar_nuevo = cuenta_pagar_existente - OLD.monto_anticipo;
    
    IF(cuenta_pagar_nuevo<0) THEN
		IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = new.id_cliente) THEN
			UPDATE CUENTA_POR_COBRAR SET monto = monto + ABS(cuenta_pagar_nuevo) WHERE id_persona = NEW.id_cliente;
		ELSE 
			INSERT INTO CUENTA_POR_COBRAR VALUES (NEW.id_cliente,ABS(cuenta_pagar_nuevo));
		END IF;
        DELETE FROM CUENTA_POR_PAGAR WHERE id_persona = NEW.id_cliente;
    END IF;
    
    -- Si existe cuenta por cobrar
    
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_cliente) THEN 
		SELECT monto INTO cuenta_cobrar_existente FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_cliente;
        IF(cuenta_cobrar_existente > NEW.monto_anticipo) THEN
			UPDATE CUENTA_POR_COBRAR SET monto = monto - NEW.monto_anticipo WHERE id_persona = NEW.id_cliente;
		ELSE IF(cuenta_cobrar_existente = NEW.monto_anticipo) THEN
			DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_cliente;
			ELSE IF(cuenta_cobrar_existente < NEW.monto_anticipo) THEN
				-- SI EXISTE CUENTA POR PAGAR ACTUALIZAMOS Y SI NO INSERTAMOS LA RESTA
                
			END IF;
        END IF;
        
	ELSE 
		INSERT INTO CUENTA_POR_COBRAR VALUES (NEW.id_cliente,ABS(cuenta_pagar_nuevo));
	END IF;
    
END;//
DELIMITER ;

DESCRIBE CUENTA_POR_PAGAR;

INSERT INTO CUENTA_POR_COBRAR VALUES ('COXN20160915HOCRXXPAXA2016',90000);
delete from ANTICIPO_CLIENTE where id_anticipo_c = 22;
SELECT * FROM ANTICIPO_CLIENTE;
SELECT * FROM CUENTA_POR_PAGAR;
SELECT * FROM CUENTA_POR_COBRAR;
delete from CUENTA_POR_PAGAR where id_persona = 'COXN20160915HOCRXXPAXA2016';
delete from CUENTA_POR_COBRAR where id_persona = 'COXN20160915HOCRXXPAXA2016';
-- Consultamos cuenta por cobrar
    
    
    
    
    -- if es negativo cuenta pagar nuevo lo insertamos en cuentas por cobrar
    IF(cuenta_pagar_nuevo < 0) THEN 
		IF(cuenta_cobrar_existente>0) THEN
			UPDATE CUENTA_POR_COBRAR SET monto = cuenta_cobrar_existente + ABS(cuenta_pagar_nuevo) WHERE id_persona = NEW.id_cliente;
        ELSE
			-- si el nuevo cuenta por pagar es negativo se abona esto en cuenta por cobrar y en cuenta por cobrar sería cero
			
        END IF;
        -- eliminamos la cuenta del cliente en cuentas por pagar si es cero
		DELETE FROM CUENTA_POR_PAGAR WHERE id_persona = NEW.id_cliente;
	-- ELSE
		-- si el nuevo cuenta por pagar es positivo se abona a Cuentas por pagar
       --  INSERT INTO CUENTA_POR_PAGAR VALUES (NEW.id_cliente,ABS(cuenta_pagar_nuevo));
        -- eliminamos la cuenta del cliente en cuentas por cobrar si es cero
        -- IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_cliente AND monto = 0) THEN 
		-- DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_cliente;
		-- END IF;
    END IF;
    
    -- si hay cuenta por cobrar actualizamos restando el anticipo
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_cliente) THEN 
		SELECT monto INTO cuenta_cobrar_existente FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_cliente;
        SET cuenta_cobrar_nuevo = cuenta_cobrar_existente - NEW.monto_anticipo;
        IF(cuenta_cobrar_nuevo < 0) THEN
			INSERT INTO CUENTA_POR_PAGAR VALUES (NEW.id_cliente,ABS(cuenta_cobrar_nuevo));
		ELSE 
			UPDATE CUENTA_POR_COBRAR SET monto = cuenta_cobrar_nuevo WHERE id_persona = NEW.id_cliente;
        END IF;
	ELSE -- Si no hay cuenta por cobrar insertamos en cuenta por pagar
		INSERT INTO CUENTA_POR_PAGAR VALUES (NEW.id_cliente,NEW.monto_anticipo);
	END IF;


-- Disparador para insertar pago compra al insertar datos en detalle compra
-- También actualiza inventario madera entrada
DELIMITER //
CREATE TRIGGER INVENTARIO_MADERA_ENTRADA  AFTER INSERT ON DETALLE_COMPRA
FOR EACH ROW
BEGIN
INSERT INTO INVENTARIO_MADERA_ENTRADA SET
                  INVENTARIO_MADERA_ENTRADA.clasificacion=NEW.clasificacion,
                  INVENTARIO_MADERA_ENTRADA.volumen=NEW.volumen
	ON DUPLICATE KEY UPDATE 
      INVENTARIO_MADERA_ENTRADA.volumen= INVENTARIO_MADERA_ENTRADA.volumen+NEW.volumen;
    INSERT INTO PAGO_COMPRA SET
				  PAGO_COMPRA.fecha=(SELECT fecha from COMPRA WHERE id_compra=NEW.id_compra),
                  PAGO_COMPRA.id_compra=NEW.id_compra,
                  PAGO_COMPRA.monto=NEW.monto
	ON DUPLICATE KEY UPDATE 
      PAGO_COMPRA.monto= PAGO_COMPRA.monto+NEW.monto;	
END;//
DELIMITER ;







INSERT INTO COSTO_MADERA (id_madera,monto_volumen) VALUES 
("clase12",9.55),
("clase10",8.45),
("clase8",7.35),
("clase6",6.45),
("clase4",5.00),

("tercera12",5.45),
("tercera10",4.45),
("tercera8",3.45),
("tercera6",2.45),
("tercera4",1.45),

("cuarta12",4.95),
("cuarta10",8.45),
("cuarta8",9.45),
("cuarta6",10.45),
("cuarta4",15.45),

("quinta12",25.45),
("quinta10",35.45),
("quinta8",45.45),
("quinta6",45.45),
("quinta4",75.45);

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
