USE aserradero;

-- Vista de clasificación de madera aserrada
DROP VIEW IF EXISTS V_MADERA_ASERRADA_CLASIF;
CREATE VIEW V_MADERA_ASERRADA_CLASIF AS 
SELECT 
	id_administrador,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) as empleado,
    id_madera,
    grueso,
    grueso_f,
    ancho,
    ancho_f,
    largo,
    largo_f,
    volumen,
    costo_por_volumen
FROM MADERA_ASERRADA_CLASIF;

DROP VIEW IF EXISTS V_ENTRADA_M_ASERRADA;
CREATE VIEW V_ENTRADA_M_ASERRADA AS
SELECT
	id_entrada,
    fecha,
    id_madera,
    num_piezas,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) as empleado,
    id_administrador
FROM ENTRADA_MADERA_ASERRADA;

-- funcion para consultar cantidad de madera vendidad a partir de su id_madera e id_administrador
DROP FUNCTION IF EXISTS C_MADERA_VENDIDA;
DELIMITER //
CREATE FUNCTION C_MADERA_VENDIDA (_id_admin VARCHAR(30),_id_madera VARCHAR(30))
RETURNS INT
BEGIN
	DECLARE _num_piezas_m INT; -- vendidas por mayoreo
    DECLARE _num_piezas_p INT; -- vendidas por paquete
    
    -- consultamos maderas vendidas por mayoreo
    IF EXISTS (SELECT id_madera FROM VENTA_MAYOREO WHERE id_madera = _id_madera AND id_administrador = _id_admin) THEN 
		SELECT 
			SUM(num_piezas) INTO _num_piezas_m
			FROM VENTA_MAYOREO 
			WHERE id_madera= _id_madera and id_administrador = _id_admin
			GROUP BY id_administrador, id_madera;
	ELSE 
		SET _num_piezas_m = 0;
    END IF;
    
    -- consultamos maderas vendidas por paquete
    IF EXISTS (SELECT id_madera FROM VENTA_PAQUETE WHERE id_madera = _id_madera AND id_administrador = _id_admin) THEN 
			SELECT 
				SUM(num_piezas) INTO _num_piezas_p
				FROM VENTA_PAQUETE 
				WHERE id_madera= _id_madera and id_administrador = _id_admin
				GROUP BY id_administrador, id_madera;
	ELSE 
		SET _num_piezas_p = 0;
	END IF;
    RETURN (_num_piezas_m + _num_piezas_p);
END;//
DELIMITER ;

-- vista para facilitar el inventario de madera aserrada
DROP VIEW IF EXISTS TOTAL_M_A_ENTRADA;
CREATE VIEW TOTAL_M_A_ENTRADA AS
SELECT 
	id_administrador,
    id_madera,
    SUM(num_piezas) AS num_piezas
FROM ENTRADA_MADERA_ASERRADA 
GROUP BY id_administrador,id_madera;

-- Vista de inventario madera aserrada
DROP VIEW IF EXISTS INVENTARIO_M_ASERRADA;
CREATE VIEW INVENTARIO_M_ASERRADA AS
SELECT 
	E.id_administrador,
    E.id_madera,
    (E.num_piezas - (SELECT C_MADERA_VENDIDA (E.id_administrador,E.id_madera))) AS num_piezas,
    CLAS.volumen AS volumen_unitario,
    CLAS.costo_por_volumen,
    ROUND(((E.num_piezas - (SELECT C_MADERA_VENDIDA (E.id_administrador,E.id_madera))) * CLAS.volumen),3) AS volumen_total,
    ROUND(((E.num_piezas - (SELECT C_MADERA_VENDIDA (E.id_administrador,E.id_madera))) * CLAS.volumen * CLAS.costo_por_volumen),2) AS costo_total
FROM TOTAL_M_A_ENTRADA AS E, MADERA_ASERRADA_CLASIF AS CLAS
WHERE E.id_administrador = CLAS.id_administrador AND E.id_madera = CLAS.id_madera;
