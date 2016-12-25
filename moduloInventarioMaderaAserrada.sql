USE aserradero;

-- Vista de clasificación de madera en rollo
DROP VIEW IF EXISTS V_MADERA_ASERRADA_CLASIF;
CREATE VIEW V_MADERA_ASERRADA_CLASIF AS 
SELECT 
	id_administrador,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(id_empleado,1,18) LIMIT 1) as empleado,
    id_madera,
    grueso,
    ancho,
    largo,
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


SELECT 
	EMA.id_administrador,EMA.id_madera,SUM(EMA.num_piezas)
FROM ENTRADA_MADERA_ASERRADA AS EMA, MADERA_ASERRADA_CLASIF AS MAC
WHERE EMA.id_madera = MAC.id_madera
GROUP BY id_administrador,id_madera;


SELECT id_madera,SUM(num_piezas) FROM ENTRADA_MADERA_ASERRADA GROUP BY id_madera;

SELECT id_administrador,id_madera,SUM(num_piezas) FROM ENTRADA_MADERA_ASERRADA  GROUP BY id_administrador,id_madera ;SELECT * FROM ENTRADA_MADERA_ASERRADA;
SELECT id_madera,SUM(num_piezas) FROM ENTRADA_MADERA_ASERRADA GROUP BY id_madera;
SELECT * FROM ENTRADA_MADERA_ASERRADA;
-- MADERA ASERRADA VENDIDA
DROP VIEW IF EXISTS MADERA_A_VENDIDA;
CREATE VIEW MADERA_A_VENDIDA AS;
SELECT 
	MAC.id_administrador, VM.id_madera AS id_madera, costo_por_volumen, num_piezas, VM.volumen AS volumen, monto, tipo_madera
FROM VENTA AS V,VENTA_MAYOREO AS VM, MADERA_ASERRADA_CLASIF AS MAC
WHERE V.id_venta = VM.id_venta AND VM.id_madera = MAC.id_madera
UNION
SELECT 
	MAC.id_administrador, VP.id_madera AS id_madera, costo_por_volumen, num_piezas, VP.volumen AS volumen, monto, tipo_madera
FROM VENTA AS V,VENTA_PAQUETE AS VP, MADERA_ASERRADA_CLASIF AS MAC
WHERE V.id_venta = VP.id_venta AND VP.id_madera = MAC.id_madera;


SELECT * FROM MADERA_ASERRADA_CLASIF;


SELECT * FROM ENTRADA_MADERA_ASERRADA;

SELECT * FROM V_ENTRADA_M_ASERRADA;
-- Consulta las maderas vendidas en ventas por paquete
DROP VIEW IF EXISTS MADERA_VENDIDA_PAQUETE;
CREATE VIEW MADERA_VENDIDA_PAQUETE AS
SELECT id_madera, SUM(num_piezas) AS num_piezas FROM VENTA_PAQUETE GROUP BY id_madera;

-- Consulta las maderas vendidas en ventas por mayoreo
DROP VIEW IF EXISTS MADERA_VENDIDA_MAYOREO;
CREATE VIEW MADERA_VENDIDA_MAYOREO AS
SELECT id_madera, SUM(num_piezas) AS num_piezas FROM VENTA_MAYOREO GROUP BY id_madera;

-- union de maderas vendidas en ventas por paquete y ventas por mayoreo
DROP VIEW IF EXISTS MADERA_VENDIDA_PAQ_Y_MAY;
CREATE VIEW MADERA_VENDIDA_PAQ_Y_MAY AS
(SELECT * FROM MADERA_VENDIDA_PAQUETE) UNION (SELECT * FROM MADERA_VENDIDA_MAYOREO);

-- Consulta todas las maderas vendidas tanto en venta por paquete como en venta por mayoreo
DROP VIEW IF EXISTS MADERA_VENDIDA;
CREATE VIEW MADERA_VENDIDA AS 
SELECT id_madera,SUM(num_piezas) AS num_piezas FROM MADERA_VENDIDA_PAQ_Y_MAY GROUP BY id_madera;


-- Consulta el total de todas las clasificaciones de madera aserrada: produccion
DROP VIEW IF EXISTS MADERA_ASERRADA_PROD;
CREATE VIEW MADERA_ASERRADA_PROD AS
SELECT id_madera,SUM(num_piezas) AS num_piezas FROM ENTRADA_MADERA_ASERRADA GROUP BY id_madera ;

-- funcion para consultar cantidad de madera vendidad a partir de su id_madera
DROP FUNCTION IF EXISTS C_MADERA_VENDIDA;
DELIMITER //
CREATE FUNCTION C_MADERA_VENDIDA (_id_madera VARCHAR(30))
RETURNS INT
BEGIN
	DECLARE _num_piezas INT;
    
    IF EXISTS (SELECT id_madera FROM MADERA_VENDIDA WHERE id_madera = _id_madera) THEN 
		SELECT num_piezas INTO _num_piezas FROM MADERA_VENDIDA WHERE id_madera = _id_madera;
        RETURN _num_piezas;
	ELSE 
		RETURN 0;
    END IF;
END;//
DELIMITER ;

DROP VIEW IF EXISTS INVENTARIO_MADERA_ASERRADA;
CREATE VIEW INVENTARIO_MADERA_ASERRADA AS
SELECT 
	DISTINCT(MAP.id_madera) AS id_madera,
    (MAP.num_piezas - (SELECT(C_MADERA_VENDIDA(MAP.id_madera)))) AS num_piezas,
    MAC.volumen as volumen_unitario,
    ROUND((((MAP.num_piezas - (SELECT(C_MADERA_VENDIDA(MAP.id_madera))))) * MAC.volumen),3) AS volumen_total,
    MAC.costo_por_volumen,
    ROUND(((MAP.num_piezas - (SELECT(C_MADERA_VENDIDA(MAP.id_madera)))) * MAC.volumen * MAC.costo_por_volumen),2) AS costo_total
FROM MADERA_ASERRADA_PROD AS  MAP, MADERA_ASERRADA_CLASIF AS MAC
WHERE MAP.id_madera = MAC.id_madera;

SELECT * 
FROM VENTA as V,VENTA_PAQUETE AS VP, VENTA_MAYOREO AS VM
WHERE V.id_venta = VP.id_venta ;


