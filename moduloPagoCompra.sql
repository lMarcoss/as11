-- DELETE FROM ENTRADA_MADERA_ROLLO WHERE id_entrada = 1;
-- describe ENTRADA_MADERA_ROLLO;
USE aserradero;
CREATE TABLE PAGO_COMPRA(
	id_pago				INT NOT NULL AUTO_INCREMENT,
    fecha 				DATE,
    id_proveedor		CHAR(26) NOT NULL,
    monto_pago 			DECIMAL(15,2),
    monto_por_pagar		DECIMAL(15,2),
 	PRIMARY KEY (id_pago),
    FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor))ENGINE=InnoDB;

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
    DECLARE _monto_compra decimal(15,2); -- Monto total de las nuevas compras
    DECLARE _monto_total decimal(15,2); -- para guardar la suma
    
    -- monto pendiente por pagar: Se utiliza si existe cuenta por pagar: ya que en cada pago se actualiza cuenta por pagar
    IF EXISTS(SELECT id_persona FROM CUENTA_POR_PAGAR WHERE id_persona = _id_proveedor)THEN
		SELECT monto INTO _monto_por_pagar FROM CUENTA_POR_PAGAR WHERE id_persona = _id_proveedor;
	ELSE 
		SET _monto_por_pagar = 0;
    END IF;
    
    -- monto de las nuevas compras
    if exists (SELECT id_proveedor FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0 AND id_proveedor = _id_proveedor) then
		SELECT SUM(monto_total) INTO _monto_compra FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0 AND id_proveedor = _id_proveedor;
	else 
		set _monto_compra = 0;
    end if;
    
    -- Solo se hacen pagos si hay compras
	IF(_monto_compra = 0)THEN 
		return 0;
	ELSE -- sumamos los dos montos consultados
		SET _monto_total = _monto_por_pagar + _monto_compra;
        return _monto_total;
    END IF;
END;//
DELIMITER ;

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

-- Procedimiento para obtener cuenta por cobrar al proveedor
DROP FUNCTION IF EXISTS OBTENER_CUENTA_POR_COBRAR;
DELIMITER //
CREATE FUNCTION OBTENER_CUENTA_POR_COBRAR (_id_proveedor CHAR(26))
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _cuenta_cobrar decimal(15,2); -- Cuenta por cobrar al proveedor
    
    -- Si existe cuenta por cobrar al proveedor
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = _id_proveedor) THEN
        SELECT monto INTO _cuenta_cobrar FROM CUENTA_POR_COBRAR WHERE id_persona = _id_proveedor;
	ELSE 
		SET _cuenta_cobrar = 0;
    END IF;

    return _cuenta_cobrar;
END;//
DELIMITER ;


-- Disparador para actualizar cuenta por cobrar despues de realizar pago compra
DROP TRIGGER IF EXISTS ACTUALIZAR_CUENTA_POR_COBRAR;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_CUENTA_POR_COBRAR  AFTER INSERT ON PAGO_COMPRA
FOR EACH ROW
BEGIN
    DECLARE _monto_pago					decimal(15,2);
    DECLARE _monto_por_pagar			decimal(15,2);
    DECLARE _cuenta_cobrar_existente	decimal(15,2);
    
    SET _monto_pago = NEW.monto_pago;
    SET _monto_por_pagar = NEW.monto_por_pagar;
    
    -- Consultamos cuenta por cobrar al proveedor
    SET _cuenta_cobrar_existente = (SELECT OBTENER_CUENTA_POR_COBRAR(NEW.id_proveedor));
    
    
    -- Si cuentas por cobrar es mayor al pago, se actualiza y si no se elimina
    IF(_cuenta_cobrar_existente > _monto_pago)THEN		
        UPDATE CUENTA_POR_COBRAR SET monto = monto - _monto_pago WHERE id_persona = NEW.id_proveedor;
	ELSE 
		IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_proveedor) THEN 
			DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = NEW.id_proveedor;
        END IF;
    END IF;
    
    -- Si hay un monto pendiente al hacer pago se va en cuentas por pagar:
    -- Si existe el registro se actualiza y si no se inserta
    IF(_monto_por_pagar > 0) THEN 
		INSERT INTO CUENTA_POR_PAGAR SET
				CUENTA_POR_PAGAR.id_persona = NEW.id_proveedor,
				CUENTA_POR_PAGAR.monto = _monto_por_pagar
			ON DUPLICATE KEY UPDATE 
				CUENTA_POR_PAGAR.monto = CUENTA_POR_PAGAR.monto + _monto_por_pagar;
    END IF;
    
END;//
DELIMITER ;

SELECT * FROM CUENTA_POR_COBRAR;
describe PAGO_COMPRA;



DESCRIBE PAGO_COMPRA;
DESCRIBE VISTA_PAGO_COMPRA;
describe OTRO_GASTO;
SELECT * FROM ADMINISTRADOR;
select * from PROVEEDOR;
select * from VISTA_PAGO_COMPRA;
update ENTRADA_MADERA_ROLLO set id_pago = 0 where id_entrada = 1;

SELECT * FROM VISTA_MONTO_PAGO_COMPRA;

SELECT * FROM VISTA_MONTO_PAGO_COMPRA where monto_por_pagar > 0;

SELECT * FROM VISTA_PAGO_COMPRA;	

select * from PAGO_COMPRA;
SELECT * from VISTA_ENTRADA_MADERA_ROLLO;

DESCRIBE CUENTA_POR_COBRAR;


DESCRIBE ENTRADA_MADERA_ROLLO;
DESCRIBE  ANTICIPO_PROVEEDOR;
select * from VISTA_ENTRADA_MADERA_ROLLO;
select * from VISTA_INVENTARIO_PRODUCION;
SELECT * FROM INVENTARIO_MADERA_PRODUCCION;
select OBTENER_MONTO_NUEVO_PAGO('PEXF20160910HOCRXRPAXA2016');

INSERT INTO PAGO_COMPRA (fecha,id_proveedor,monto_pago,monto_por_pagar) VALUES (curdate(),'PEXF20160910HOCRXRPAXA2016',99999999,5101822915.68);
SELECT * FROM ENTRADA_MADERA_ROLLO;
drop table PAGO_COMPRA;


SELECT SUM(monto_total) FROM VISTA_ENTRADA_MADERA_ROLLO WHERE id_pago = 0;

SELECT * FROM PAGO_COMPRA order by id_pago desc limit 1;
SELECT max(id_pago),id_proveedor,monto_por_pagar FROM PAGO_COMPRA;
SELECT * FROM PAGO_COMPRA;

describe PAGO_COMPRA;

SELECT * FROM ENTRADA_MADERA_ROLLO;
SELECT * FROM PAGO_COMPRA;

-- Disparador para actualizar cuentas pr pagar al proveedor o cuentas por cobrar dependiendo del cambio
-- de pago compra
DROP TRIGGER IF EXISTS ACTUALIZAR_CUENTAS_PAGO_COMRA;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_CUENTAS_PAGO_COMRA AFTER UPDATE ON PAGO_COMPRA
FOR EACH ROW
BEGIN
	-- Restamos cuentas por pagar proveedor si es suficiente
    CALL RESTAR_CUENTA_PROVEEDOR(NEW.id_proveedor,OLD.monto_por_pagar);
    
	-- Insertamos el nuevo valor en cuentas por pagar
    CALL INSERTAR_CUENTA_PAGAR_PROVEEDOR(NEW.id_proveedor,NEW.monto_por_pagar);
END;//
DELIMITER ;
select * from PAGO_COMPRA;


-- Procedimiento de modificacion de de cuentas por pagar y por cobrar deL proveedor al modificar Pago compra
DROP PROCEDURE IF EXISTS RESTAR_CUENTA_PROVEEDOR;
DELIMITER //
CREATE PROCEDURE RESTAR_CUENTA_PROVEEDOR (IN _id_proveedor CHAR(26), IN _monto DECIMAL(15,2))
BEGIN
	DECLARE _cuenta_pagar_existente 	decimal(15,2);
    DECLARE _cuenta_cobrar_nuevo			 	decimal(15,2);
    
	-- Existe cuenta por pagar al proveedor?
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_PAGAR WHERE id_persona = _id_proveedor) THEN 
		SELECT monto INTO _cuenta_pagar_existente FROM CUENTA_POR_PAGAR WHERE id_persona = _id_proveedor;
        IF(_cuenta_pagar_existente > _monto) THEN
			UPDATE CUENTA_POR_PAGAR SET monto = monto - _monto WHERE id_persona = _id_proveedor;
		ELSE 
			IF (_cuenta_pagar_existente = _monto) THEN
				DELETE FROM CUENTA_POR_PAGAR WHERE id_persona = _id_proveedor;
			ELSE 
				-- si cuenta por pagar existente es menor al monto entonces se hace la diferencia y se inserta en cuentas por cobrar
				IF (_cuenta_pagar_existente <  _monto) THEN
					SET _cuenta_cobrar_nuevo = _monto - _cuenta_pagar_existente;
                    DELETE FROM CUENTA_POR_PAGAR WHERE id_persona = _id_proveedor;
                    INSERT INTO CUENTA_POR_COBRAR (id_persona, monto) VALUES (_id_proveedor, _cuenta_cobrar_nuevo);
                END IF;
            END IF;
        END IF;
	ELSE -- No existe cuenta por pagar al proveedor
		-- Inserta en cuenta por cobrar como nuevo y si existe cuenta se actualiza
        INSERT INTO CUENTA_POR_COBRAR SET
				CUENTA_POR_COBRAR.id_persona = _id_proveedor,
				CUENTA_POR_COBRAR.monto = _monto
		ON DUPLICATE KEY UPDATE 
				CUENTA_POR_COBRAR.monto = CUENTA_POR_COBRAR.monto + _monto;
    END IF;
END;//
DELIMITER ;

SELECT * FROM CUENTA_POR_COBRAR;

-- Procedimiento para insertar cuenta por pagar al proveedor al insertar o actualizar PAGO_COMPRA
DROP PROCEDURE IF EXISTS INSERTAR_CUENTA_PAGAR_PROVEEDOR;
DELIMITER //
CREATE PROCEDURE INSERTAR_CUENTA_PAGAR_PROVEEDOR (IN _id_proveedor CHAR(26), IN _monto DECIMAL(15,2))
BEGIN
	DECLARE _cuenta_cobrar_existente 	decimal(15,2);
    DECLARE _cuenta_pagar_nuevo		 	decimal(15,2);
    
	-- Existe cuenta por cobrar al proveedor?
    IF EXISTS (SELECT id_persona FROM CUENTA_POR_COBRAR WHERE id_persona = _id_proveedor) THEN 
		SELECT monto INTO _cuenta_cobrar_existente FROM CUENTA_POR_COBRAR WHERE id_persona = _id_proveedor;
        IF(_cuenta_cobrar_existente > _monto) THEN
			UPDATE CUENTA_POR_COBRAR SET monto = monto - _monto WHERE id_persona = _id_proveedor;
		ELSE 
			IF (_cuenta_cobrar_existente = _monto) THEN
				DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = _id_proveedor;
			ELSE 
				-- si cuenta por cobrar existente es menor al monto entonces se hace la diferencia y se inserta en cuentas por pagar
				IF (_cuenta_cobrar_existente <  _monto) THEN
					SET _cuenta_pagar_nuevo = _monto - _cuenta_cobrar_existente;
                    DELETE FROM CUENTA_POR_COBRAR WHERE id_persona = _id_proveedor;
                    INSERT INTO CUENTA_POR_PAGAR (id_persona, monto) VALUES (_id_proveedor, _cuenta_pagar_nuevo);
                END IF;
            END IF;
        END IF;
	ELSE -- No existe cuenta por cobrar al proveedor
		-- Inserta en cuenta por pagar como nuevo y si existe cuenta se actualiza
        INSERT INTO CUENTA_POR_PAGAR SET
			CUENTA_POR_PAGAR.id_persona = _id_proveedor,
			CUENTA_POR_PAGAR.monto = _monto
		ON DUPLICATE KEY UPDATE 
			CUENTA_POR_PAGAR.monto = CUENTA_POR_PAGAR.monto + _monto;
    END IF;
END;//
DELIMITER ;