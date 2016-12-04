USE aserradero;

-- muestra todos los anticipos a proveedores
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

-- muestra el monto total de los anticipo proveedores:
CREATE VIEW MONTO_TOTAL_ANTICIPO AS
SELECT
    id_proveedor,
    SUM(monto_anticipo) as monto_anticipo
FROM VISTA_ANTICIPO_PROVEEDOR
GROUP BY id_proveedor;

-- muestra el monto total de las entrada de madera en rollo: $ costo total
CREATE VIEW MONTO_TOTAL_ENTRADA_MADERA AS
SELECT 
	id_proveedor,
    SUM(monto_total) AS monto_total
FROM VISTA_ENTRADA_MADERA_ROLLO
GROUP BY id_proveedor;

-- funcion para consultar $ monto total de las maderas en rollo entrada por cada proveedor
DROP FUNCTION IF EXISTS C_MADERA_ENTRADA_ROLLO;
DELIMITER //
CREATE FUNCTION C_MADERA_ENTRADA_ROLLO (_id_proveedor VARCHAR(30))
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _monto_total DECIMAL(15,2);
    
	-- consultamos si han entrado madera del proveedor
    IF EXISTS (SELECT id_proveedor FROM MONTO_TOTAL_ENTRADA_MADERA WHERE id_proveedor = _id_proveedor LIMIT 1) THEN 
		-- consultamos el monto total de las maderas que han entrado
        SELECT monto_total INTO _monto_total FROM MONTO_TOTAL_ENTRADA_MADERA WHERE id_proveedor = _id_proveedor;
		-- retornamos el monto total
        RETURN _monto_total;
	ELSE -- No hay entradas
		RETURN 0;
    END IF;
END;//
DELIMITER ;

-- funcion para consultar $ monto total de los anticipos dados al proveedor
DROP FUNCTION IF EXISTS C_ANTICIPO_PROVEEDOR;
DELIMITER //
CREATE FUNCTION C_ANTICIPO_PROVEEDOR (_id_proveedor VARCHAR(30))
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _monto_total DECIMAL(15,2);
    
	-- consultamos si han entrado madera del proveedor
    IF EXISTS (SELECT id_proveedor FROM MONTO_TOTAL_ANTICIPO WHERE id_proveedor = _id_proveedor LIMIT 1) THEN 
		-- consultamos el monto total de los anticipo
        SELECT monto_anticipo INTO _monto_total FROM MONTO_TOTAL_ANTICIPO WHERE id_proveedor = _id_proveedor;
        
		-- retornamos el monto total
        RETURN _monto_total;
	ELSE -- No hay entradas
		RETURN 0;
    END IF;
END;//
DELIMITER ;

-- Muestra cuentas por cobrar y por pagar a los proveedores
-- : los negativos representan cuenta por pagar
-- : los positivos son cuentas por cobrar
CREATE VIEW CUENTAS_PROVEEDOR AS 
SELECT
	id_proveedor,
    proveedor,
    id_jefe,
    ROUND(((SELECT C_ANTICIPO_PROVEEDOR(id_proveedor))-(SELECT C_MADERA_ENTRADA_ROLLO(id_proveedor))),2) AS monto
FROM PERSONAL_PROVEEDOR;

-- Cuentas por pagar a proveedores
DROP VIEW IF EXISTS C_POR_PAGAR_PROVEEDOR;
CREATE VIEW C_POR_PAGAR_PROVEEDOR AS
SELECT
	id_proveedor AS id_persona,
    proveedor AS persona,
    id_jefe,
    ABS(monto) AS monto
FROM CUENTAS_PROVEEDOR WHERE monto < 0;

-- Cuentas por cobrar a proveedores
DROP VIEW IF EXISTS C_POR_COBRAR_PROVEEDOR;
CREATE VIEW C_POR_COBRAR_PROVEEDOR AS
SELECT
	id_proveedor AS id_persona,
    proveedor AS persona,
    id_jefe,
    ABS(monto) AS monto
FROM CUENTAS_PROVEEDOR WHERE monto > 0;