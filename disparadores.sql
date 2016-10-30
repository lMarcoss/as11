



-- Disparador para insertar datos en la tabla cuentas por pagar cada que se hace un anticipoCliente
-- verifica si hay se tiene cuenta pro cobrar con el cliente para restarle y si no, entonces inserta en cuentas por pagar
DROP TRIGGER IF EXISTS CUENTA_POR_PAGAR;
DELIMITER //
CREATE TRIGGER CUENTA_POR_PAGAR  AFTER INSERT ON ANTICIPO_CLIENTE
FOR EACH ROW
BEGIN
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
    END IF;
END;//
DELIMITER ;

-- Disparador para actualizar el inventario de madera producida cada que se inserta datos en produccionDetalle
DELIMITER //
CREATE TRIGGER INVENTARIO_MADERA_PRODUCCION  AFTER INSERT ON PRODUCCION_DETALLE
FOR EACH ROW
BEGIN
INSERT INTO INVENTARIO_MADERA_PRODUCCION SET
                  INVENTARIO_MADERA_PRODUCCION.id_madera=NEW.id_madera,
                  INVENTARIO_MADERA_PRODUCCION.num_piezas=NEW.num_piezas
	ON DUPLICATE KEY UPDATE 
      INVENTARIO_MADERA_PRODUCCION.num_piezas= INVENTARIO_MADERA_PRODUCCION.num_piezas+NEW.num_piezas;
END;//
DELIMITER ;

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
