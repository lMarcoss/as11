use aserradero;
SELECT * FROM ENTRADA_MADERA;


SELECT * FROM COSTO_MADERA_ENTRADA;
SELECT * FROM ENTRADA_MADERA;
SELECT id_administrador, (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_administrador)as nombre FROM ADMINISTRADOR;

SELECT * FROM VISTA_ENTRADA_MADERA;


SELECT * FROM VISTA_ENTRADA_MADERA_COSTO;
SELECT * FROM VISTA_ENTRADA_MADERA;



SELECT * FROM INVENTARIO_MADERA_ENTRADA;
SELECT * FROM VISTA_ENTRADA_MADERA;
select id_entrada from ENTRADA_MADERA;
delete from INVENTARIO_MADERA_ENTRADA where id_administrador='PAXA20160913HOCSXN';
SELECT * FROM ENTRADA_MADERA;
describe INVENTARIO_MADERA_ENTRADA;

SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO;
SELECT * FROM VISTA_SALIDA_MADERA_ROLLO;
SELECT * FROM SALIDA_MADERA_ROLLO;
SELECT * FROM ENTRADA_MADERA_ROLLO;
DROP TRIGGER IF EXISTS ACTUALIZAR_SALIDA_MADERA_ROLLO;

-- Actualizar inventario cada que se modifica una salida de madera
DELIMITER //
CREATE TRIGGER ACTUALIZAR_SALIDA_MADERA_ROLLO BEFORE UPDATE ON SALIDA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	DECLARE _id_administrador VARCHAR(18);	
    
    -- consultamos el jefe del empleado que registra
    SELECT id_jefe INTO _id_administrador FROM EMPLEADO WHERE id_empleado = new.id_empleado;
    
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