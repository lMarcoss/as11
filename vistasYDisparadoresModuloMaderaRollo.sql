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
    (select id_jefe from EMPLEADO where EMPLEADO.id_empleado = ENTRADA_MADERA_ROLLO.id_empleado) as id_jefe,
    num_piezas,
    volumen_primario,
    costo_primario,
    volumen_secundario,
    costo_secundario,
    volumen_terciario,
    costo_terciario,
    (volumen_primario + volumen_secundario + volumen_terciario) as volumen_total,
    (volumen_primario * costo_primario + volumen_secundario * costo_secundario + volumen_terciario * costo_terciario) as monto_total,
    id_pago
FROM ENTRADA_MADERA_ROLLO;
SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO;

-- Disparador para insertar en inventario entrada cada que se inserta en entrada madera
DROP TRIGGER IF EXISTS INVENTARIO_MADERA_ENTRADA;
DELIMITER //
CREATE TRIGGER INVENTARIO_MADERA_ENTRADA BEFORE INSERT ON ENTRADA_MADERA_ROLLO
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
    SET NEW.costo_secundario = _costo_terciario;
    SET NEW.costo_terciario = _costo_terciario;
    
    
    -- consultamos el jefe del empleado que registra
    SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado;
    
    
	IF EXISTS (SELECT id_administrador FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador) THEN
        UPDATE INVENTARIO_MADERA_ENTRADA -- actualizamos inventario si existe inventario asociado al administrador
			SET num_piezas = (num_piezas + NEW.num_piezas), 
            volumen_total = (volumen_total + NEW.volumen_primario+NEW.volumen_secundario+NEW.volumen_terciario)
            WHERE id_administrador = _id_administrador;
    ELSE -- si no existe inventario asociado al administrador: insertamos
        INSERT INTO INVENTARIO_MADERA_ENTRADA VALUES(_id_administrador,NEW.num_piezas,(NEW.volumen_primario+NEW.volumen_secundario+NEW.volumen_terciario));
    END IF;
END;//
DELIMITER ;

DROP VIEW IF EXISTS VISTA_SALIDA_MADERA_ROLLO;
CREATE VIEW VISTA_SALIDA_MADERA_ROLLO AS
SELECT 
	id_salida,
    fecha,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18)) as empleado,
	(select id_jefe from EMPLEADO where id_empleado = SALIDA_MADERA_ROLLO.id_empleado) as id_jefe,
    num_piezas,
    volumen_total
	FROM SALIDA_MADERA_ROLLO;
    
select * from VISTA_SALIDA_MADERA_ROLLO;

-- Disparador para restar inventario en cada salida de madera en rollo
DROP TRIGGER IF EXISTS RESTAR_INV_MADERA_ROLLO;
DELIMITER //
CREATE TRIGGER RESTAR_INV_MADERA_ROLLO BEFORE INSERT ON SALIDA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	
	DECLARE _id_administrador VARCHAR(18);
    DECLARE _num_piezas_existente INT;
    DECLARE _volumen_total_existente DECIMAL(15,3);
    -- consultamos el jefe del empleado que registra
    SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado;
    -- Consultamos inventario existente
    SELECT num_piezas INTO _num_piezas_existente FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
    SELECT volumen_total INTO _volumen_total_existente FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
    
	IF EXISTS (SELECT id_administrador FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador) THEN
        IF(_num_piezas_existente >= NEW.num_piezas and _volumen_total_existente >= NEW.volumen_total)THEN
			UPDATE INVENTARIO_MADERA_ENTRADA -- actualizamos inventario si existe inventario asociado al administrador Y el número de piezas es alcansable
				SET num_piezas = (num_piezas - NEW.num_piezas), 
				volumen_total = (volumen_total - NEW.volumen_total)
				WHERE id_administrador = _id_administrador;
		ELSE 
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay inventario';
        END IF;
	ELSE 
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No hay inventario';
    END IF;
END;//
DELIMITER ;

-- Actualizar inventario cada que se modifica una entrada de madera
DROP TRIGGER IF EXISTS ACTUALIZAR_ENTRADA_MADERA_ROLLO;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_ENTRADA_MADERA_ROLLO BEFORE UPDATE ON ENTRADA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	DECLARE _id_administrador VARCHAR(18);	
    DECLARE _costo_primario DECIMAL(15,2);
    DECLARE _costo_secundario DECIMAL(15,2);
    DECLARE _costo_terciario DECIMAL(15,2);
    DECLARE _volumen_total_old DECIMAL(15,3);
    DECLARE _monto_total_old DECIMAL(15,2);
    
    -- Consultamos si existe los costos de clasificación madera entrada
    IF NOT EXISTS (SELECT costo FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Primario') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Clasificación primaria no existe';
    END IF;
    IF NOT EXISTS (SELECT costo FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Secundario') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Clasificación secundaria no existe';
    END IF;
    IF NOT EXISTS (SELECT costo FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Terciario') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Clasificación terciaria no existe';
    END IF;
    
    -- Verificamos los valores antiguos
    SET _volumen_total_old = OLD.volumen_primario + OLD.volumen_secundario + OLD.volumen_terciario;
    SET _monto_total_old = OLD.volumen_primario * OLD.costo_primario + OLD.volumen_secundario * OLD.costo_secundario + OLD.volumen_terciario * OLD.costo_terciario;
    
	-- consultamos los costos de cada tipo de volumen para nuevos valores;
    SELECT costo INTO _costo_primario FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Primario';
    SELECT costo INTO _costo_secundario FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Secundario';
    SELECT costo INTO _costo_terciario FROM COSTO_MADERA_ENTRADA WHERE clasificacion = 'Terciario';
	
    -- actualizamos los costo de volumen madera en cada EntradaMadera
    SET NEW.costo_primario = _costo_primario;
    SET NEW.costo_secundario = _costo_terciario;
    SET NEW.costo_terciario = _costo_terciario;
    
    
    -- consultamos el jefe del empleado que registra
    SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado;
    
    -- Restamos los valores antiguos en inventario madera rollo
    UPDATE INVENTARIO_MADERA_ENTRADA
			SET num_piezas = (num_piezas - OLD.num_piezas), 
            volumen_total = (volumen_total - _volumen_total_old)
            WHERE id_administrador = _id_administrador;
    
	-- actualizamos inventario con los nuevos valores
	UPDATE INVENTARIO_MADERA_ENTRADA 
		SET num_piezas = (num_piezas + NEW.num_piezas), 
		volumen_total = (volumen_total + NEW.volumen_primario+NEW.volumen_secundario+NEW.volumen_terciario)
		WHERE id_administrador = _id_administrador;
END;//
DELIMITER ;

-- Actualizar inventario cada que se modifica una salida de madera
DROP TRIGGER IF EXISTS ACTUALIZAR_SALIDA_MADERA_ROLLO;
DELIMITER //
CREATE TRIGGER ACTUALIZAR_SALIDA_MADERA_ROLLO BEFORE UPDATE ON SALIDA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	DECLARE _id_administrador VARCHAR(18);	
    DECLARE _num_piezas_disponible INT;	-- numero de piezas disponible en inventario
    DECLARE _volumen_disponible DECIMAL(15,3); 			-- volumen disponible en inventario
    
    -- consultamos el jefe del empleado que registra
    SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado;
    
    -- Consultamos inventario disponible
    SELECT num_piezas INTO _num_piezas_disponible FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
    SELECT volumen_total INTO _volumen_disponible FROM INVENTARIO_MADERA_ENTRADA WHERE id_administrador = _id_administrador;
    
    -- si el inventario es inalcansable se termina la transacción
    IF((_num_piezas_disponible < NEW.num_piezas-OLD.num_piezas) or (_volumen_disponible<NEW.volumen_total-OLD.volumen_total))THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Inventario inalcansable';
    END IF;
    
    -- Sumamos los valores antiguos en inventario madera rollo
    UPDATE INVENTARIO_MADERA_ENTRADA -- actualizamos inventario si existe inventario asociado al administrador
			SET num_piezas = (num_piezas + OLD.num_piezas), 
            volumen_total = (volumen_total + OLD.volumen_total)
            WHERE id_administrador = _id_administrador;
    
	-- actualizamos inventario restando los nuevos valores
	UPDATE INVENTARIO_MADERA_ENTRADA 
		SET num_piezas = (num_piezas - NEW.num_piezas), 
		volumen_total = (volumen_total - NEW.volumen_total)
		WHERE id_administrador = _id_administrador;
END;//
DELIMITER ;