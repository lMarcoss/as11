USE aserradero;

DROP VIEW IF EXISTS VISTA_ENTRADA_MADERA_ROLLO;
CREATE VIEW VISTA_ENTRADA_MADERA_ROLLO AS
SELECT 
	id_entrada,
    fecha,
    id_proveedor,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) from PERSONA where id_persona = SUBSTRING(id_proveedor,1,18)) as proveedor,
    id_chofer,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) from PERSONA where id_persona = SUBSTRING(id_chofer,1,18)) as chofer,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) from PERSONA where id_persona = SUBSTRING(id_empleado,1,18)) as empleado,
    (select id_jefe from EMPLEADO where EMPLEADO.id_empleado = ENTRADA_MADERA_ROLLO.id_empleado limit 1) as id_jefe ,
    num_piezas,
    volumen_primario,
    costo_primario,
    volumen_secundario,
    costo_secundario,
    volumen_terciario,
    costo_terciario,
    ROUND((volumen_primario + volumen_secundario + volumen_terciario),3) as volumen_total,
    ROUND((volumen_primario * costo_primario + volumen_secundario * costo_secundario + volumen_terciario * costo_terciario),2) as monto_total,
    id_pago
FROM ENTRADA_MADERA_ROLLO;

-- Disparador para insertar costos de madera entrada
DROP TRIGGER IF EXISTS MODIFICAR_ENTRADA_M_ROLLO;
DELIMITER //
CREATE TRIGGER MODIFICAR_ENTRADA_M_ROLLO BEFORE INSERT ON ENTRADA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	DECLARE _id_administrador VARCHAR(18);	
    DECLARE _costo_primario DECIMAL(15,2);
    DECLARE _costo_secundario DECIMAL(15,2);
    DECLARE _costo_terciario DECIMAL(15,2);
    
    -- Verificamos si existe los costos de clasificación madera entrada
    IF NOT EXISTS (SELECT costo FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Primario') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Clasificación primaria no existe';
    END IF;
    IF NOT EXISTS (SELECT costo FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Secundario') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Clasificación secundaria no existe';
    END IF;
    IF NOT EXISTS (SELECT costo FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Terciario') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Clasificación terciaria no existe';
    END IF;
    
	-- consultamos los costos de cada tipo de volumen;
    SELECT costo INTO _costo_primario FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Primario';
    SELECT costo INTO _costo_secundario FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Secundario';
    SELECT costo INTO _costo_terciario FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Terciario';
	
    -- actualizamos los costo de volumen madera en cada EntradaMadera
    SET NEW.costo_primario = _costo_primario;
    SET NEW.costo_secundario = _costo_secundario;
    SET NEW.costo_terciario = _costo_terciario;
    
END;//
DELIMITER ;

DROP VIEW IF EXISTS VISTA_SALIDA_MADERA_ROLLO;
CREATE VIEW VISTA_SALIDA_MADERA_ROLLO AS
SELECT 
	id_salida,
    fecha,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18)) as empleado,
	(select id_jefe from EMPLEADO where id_empleado = SALIDA_MADERA_ROLLO.id_empleado limit 1) as id_jefe,
    num_piezas,
    volumen_total
	FROM SALIDA_MADERA_ROLLO;
    
-- Disparador para restar inventario en cada salida de madera en rollo
-- DROP TRIGGER IF EXISTS RESTAR_INV_MADERA_ROLLO;
-- DELIMITER //
-- CREATE TRIGGER RESTAR_INV_MADERA_ROLLO BEFORE INSERT ON SALIDA_MADERA_ROLLO
-- FOR EACH ROW
-- BEGIN
	
-- 	DECLARE _id_administrador VARCHAR(18);
--     DECLARE _num_piezas_existente INT;
--     DECLARE _volumen_total_existente DECIMAL(15,3);
--     -- consultamos el jefe del empleado que registra
--     SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado limit 1;
    
--     -- Consultamos inventario existente
--     SELECT num_piezas INTO _num_piezas_existente FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
--     SELECT volumen_total INTO _volumen_total_existente FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
    
-- 	IF EXISTS (SELECT id_administrador FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador) THEN
--         IF(_num_piezas_existente >= NEW.num_piezas and _volumen_total_existente >= NEW.volumen_total)THEN
-- 			UPDATE INVENTARIO_MADERA_ENTRADA -- actualizamos inventario si existe inventario asociado al administrador Y el número de piezas es alcansable
-- 				SET num_piezas = (num_piezas - NEW.num_piezas), 
-- 				volumen_total = (volumen_total - NEW.volumen_total)
-- 				WHERE id_administrador = _id_administrador;
-- 		ELSE 
-- 			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay inventario';
--         END IF;
-- 	ELSE 
-- 		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay inventario';
--     END IF;
-- END;//
-- DELIMITER ;

-- Actualizar datos cada que se modifica una entrada de madera
DROP TRIGGER IF EXISTS ACTUALIZAR_ENTRADA_MADERA_ROLLO;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_ENTRADA_MADERA_ROLLO BEFORE UPDATE ON ENTRADA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	DECLARE _id_administrador VARCHAR(18);	
    DECLARE _costo_primario DECIMAL(15,2);
    DECLARE _costo_secundario DECIMAL(15,2);
    DECLARE _costo_terciario DECIMAL(15,2);
    DECLARE _monto_total_new DECIMAL(15,2);			-- monto para actualizar pago compra
    DECLARE _monto_total_old DECIMAL(15,2);			-- monto para actualizar pago compra
    DECLARE _volumen_total_old DECIMAL(15,3);
    
    -- Verificamos los valores antiguos
    SET _volumen_total_old = OLD.volumen_primario + OLD.volumen_secundario + OLD.volumen_terciario;
    SET _monto_total_old = OLD.volumen_primario * OLD.costo_primario + OLD.volumen_secundario * OLD.costo_secundario + OLD.volumen_terciario * OLD.costo_terciario;
    
    -- Los costos no cambian al actualizar los datos: ya que es costo unitario y de la fecha en que se hizo la venta
    SET NEW.costo_primario = OLD.costo_primario;
    SET NEW.costo_secundario = OLD.costo_secundario;
    SET NEW.costo_terciario = OLD.costo_terciario;
    
    
    -- consultamos el jefe del empleado que registra
    -- SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado limit 1;
    
    -- Restamos los valores antiguos en inventario madera rollo
   --  UPDATE INVENTARIO_MADERA_ENTRADA
			-- SET num_piezas = (num_piezas - OLD.num_piezas), 
   --          volumen_total = (volumen_total - _volumen_total_old)
   --          WHERE id_administrador = _id_administrador;
    
	-- actualizamos inventario con los nuevos valores
	-- UPDATE INVENTARIO_MADERA_ENTRADA 
	-- 	SET num_piezas = (num_piezas + NEW.num_piezas), 
	-- 	volumen_total = (volumen_total + NEW.volumen_primario + NEW.volumen_secundario + NEW.volumen_terciario)
	-- 	WHERE id_administrador = _id_administrador;
        
	-- Actualizamos la tabla PAGO_COMPRA Si la venta se ha pagado ya
    SET _monto_total_new = ROUND((NEW.volumen_primario * NEW.costo_primario + NEW.volumen_secundario * NEW.costo_secundario + NEW.volumen_terciario * NEW.costo_terciario),2);
    SET _monto_total_old = ROUND((OLD.volumen_primario * OLD.costo_primario + OLD.volumen_secundario * OLD.costo_secundario + OLD.volumen_terciario * OLD.costo_terciario),2);
    IF(NEW.id_pago > 0)THEN 
		CALL ACTUALIZAR_PAGO_COMPRA(NEW.id_pago, _monto_total_new, _monto_total_old); /** No se ha terminado este metodo: falta cuando cuenta por pagar es cero**/
    END IF;
END;//
DELIMITER ;

-- Actualizar inventario cada que se modifica una salida de madera
DROP TRIGGER IF EXISTS ACTUALIZAR_SALIDA_MADERA_ROLLO;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_SALIDA_MADERA_ROLLO BEFORE UPDATE ON SALIDA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	-- DECLARE _id_administrador VARCHAR(18);	
    -- DECLARE _num_piezas_disponible INT;	-- numero de piezas disponible en inventario
    -- DECLARE _volumen_disponible DECIMAL(15,3); 			-- volumen disponible en inventario
    
    -- consultamos el jefe del empleado que registra
    -- SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado limit 1;
    
    -- Consultamos inventario disponible
    -- SELECT num_piezas INTO _num_piezas_disponible FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
    -- SELECT volumen_total INTO _volumen_disponible FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
    
    -- si el inventario es inalcansable se termina la transacción
  --   IF((_num_piezas_disponible < NEW.num_piezas-OLD.num_piezas) or (_volumen_disponible<NEW.volumen_total-OLD.volumen_total))THEN
		-- SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Inventario inalcansable';
  --   END IF;
    
    -- Sumamos los valores antiguos en inventario madera rollo
   --  UPDATE INVENTARIO_MADERA_ENTRADA -- actualizamos inventario si existe inventario asociado al administrador
			-- SET num_piezas = (num_piezas + OLD.num_piezas), 
   --          volumen_total = (volumen_total + OLD.volumen_total)
   --          WHERE id_administrador = _id_administrador;
    
	-- actualizamos inventario restando los nuevos valores
	-- UPDATE INVENTARIO_MADERA_ENTRADA 
	-- 	SET num_piezas = (num_piezas - NEW.num_piezas), 
	-- 	volumen_total = (volumen_total - NEW.volumen_total)
	-- 	WHERE id_administrador = _id_administrador;
END;//
DELIMITER ;

-- ACTUALIZAR PAGO COMPRA
DROP PROCEDURE IF EXISTS ACTUALIZAR_PAGO_COMPRA;
DELIMITER //
CREATE PROCEDURE ACTUALIZAR_PAGO_COMPRA(IN _id_pago INT, IN _monto_total_new DECIMAL(15,2), IN _monto_total_old DECIMAL(15,2))
BEGIN
    DECLARE monto_compra_existente DECIMAL(15,2);	-- monto existente a modificar de pago compra 
    DECLARE nuevo_monto DECIMAL(15,2);			-- nuevo monto para desplazar al existente
    
	IF EXISTS (SELECT monto_por_pagar FROM PAGO_COMPRA WHERE id_pago = _id_pago) THEN
		SELECT monto_por_pagar INTO monto_compra_existente FROM PAGO_COMPRA WHERE id_pago = _id_pago;
        -- si el nuevo monto total de la nueva entrada es mayor al valor anterior: restamos el nuevo valor;
		IF (_monto_total_new < _monto_total_old) THEN
			SET nuevo_monto = _monto_total_old - _monto_total_new;
            UPDATE PAGO_COMPRA SET monto_por_pagar = nuevo_monto WHERE id_pago = _id_pago;
		ELSE
			IF(_monto_total_new > _monto_total_old)THEN
				SET nuevo_monto = _monto_total_new - _monto_total_old;
                UPDATE PAGO_COMPRA SET monto_por_pagar = monto_por_pagar + nuevo_monto WHERE id_pago = _id_pago;
            END IF;
		END IF;
    END IF;
END;//
DELIMITER ;


DROP VIEW IF EXISTS TOTAL_ENTRADA_MADERA_ROLLO;
CREATE VIEW TOTAL_ENTRADA_MADERA_ROLLO AS
SELECT 
	id_jefe AS id_administrador,
    SUM(num_piezas) AS num_piezas,
    SUM(volumen_total) AS volumen_total
FROM VISTA_ENTRADA_MADERA_ROLLO
GROUP BY id_jefe;

DROP VIEW IF EXISTS TOTAL_SALIDA_MADERA_ROLLO;
CREATE VIEW TOTAL_SALIDA_MADERA_ROLLO AS
SELECT 
	id_jefe AS id_administrador,
    SUM(num_piezas) AS num_piezas,
    SUM(volumen_total) AS volumen_total
FROM VISTA_SALIDA_MADERA_ROLLO
GROUP BY id_jefe;

-- funcion para consultar numero de piezas de madera rollo han salido: se ha enviado para aserrar
DROP FUNCTION IF EXISTS C_MADERA_ASERRADA_SALIDA_PIEZA;
DELIMITER //
CREATE FUNCTION C_MADERA_ASERRADA_SALIDA_PIEZA (_id_administrador VARCHAR(30))
RETURNS INT
BEGIN
	DECLARE _num_piezas INT;
    
	-- consultamos si han salido madera rollo
    IF EXISTS (SELECT num_piezas FROM TOTAL_SALIDA_MADERA_ROLLO WHERE id_administrador = _id_administrador) THEN 
		SELECT num_piezas INTO _num_piezas FROM TOTAL_SALIDA_MADERA_ROLLO WHERE id_administrador = _id_administrador;
        RETURN _num_piezas;
	ELSE -- No existe cuenta por cobrar al proveedor
		RETURN 0;
    END IF;
END;//
DELIMITER ;

-- funcion para consultar el volumen total de madera rollo salida 
DROP FUNCTION IF EXISTS C_MADERA_ASERRADA_SALIDA_VOL;
DELIMITER //
CREATE FUNCTION C_MADERA_ASERRADA_SALIDA_VOL (_id_administrador VARCHAR(30))
RETURNS DECIMAL(15,3)
BEGIN
	DECLARE _volumen_total DECIMAL(15,3);
    
	-- consultamos si han salido madera rollo
    IF EXISTS (SELECT volumen_total FROM TOTAL_SALIDA_MADERA_ROLLO WHERE id_administrador = _id_administrador) THEN 
		SELECT volumen_total INTO _volumen_total FROM TOTAL_SALIDA_MADERA_ROLLO WHERE id_administrador = _id_administrador;
        RETURN _volumen_total;
	ELSE -- si no existe salida 
		RETURN 0;
    END IF;
END;//
DELIMITER ;


DROP VIEW IF EXISTS INVENTARIO_MADERA_ROLLO;
CREATE VIEW INVENTARIO_MADERA_ROLLO AS 
SELECT 
	ENTRADA.id_administrador,
    (ENTRADA.num_piezas - (SELECT C_MADERA_ASERRADA_SALIDA_PIEZA(ENTRADA.id_administrador))) AS num_piezas,
    ROUND((ENTRADA.volumen_total - (SELECT C_MADERA_ASERRADA_SALIDA_VOL(ENTRADA.id_administrador))),3) AS volumen_total
FROM TOTAL_ENTRADA_MADERA_ROLLO AS ENTRADA;