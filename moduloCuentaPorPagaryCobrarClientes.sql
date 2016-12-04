-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
-- Modulo pendiente
USE aserradero;

-- muestra el monto total de los anticipo clientes: $ monto total
DROP VIEW IF EXISTS MONTO_TOTAL_ANTICIPO_C;
CREATE VIEW MONTO_TOTAL_ANTICIPO_C AS
SELECT
    id_cliente,
    SUM(monto_anticipo) as monto_anticipo
FROM VISTA_ANTICIPO_CLIENTE
GROUP BY id_cliente;

-- muestra el monto total de las ventas de madera aserrada: $ monto total
CREATE VIEW MONTO_TOTAL_ENTRADA_MADERA AS
SELECT 
	id_proveedor,
    SUM(monto_total) AS monto_total
FROM VISTA_ENTRADA_MADERA_ROLLO
GROUP BY id_proveedor;

SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO;
-- funcion para consultar $ monto total de las maderas en rollo entrada por cada proveedor
DROP FUNCTION IF EXISTS C_MADERA_ENTRADA_ROLLO;
DELIMITER //
CREATE FUNCTION C_MADERA_ENTRADA_ROLLO (_id_cliente VARCHAR(30))
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _monto_total DECIMAL(15,2);
    
	-- consultamos si han entrado madera del proveedor
    IF EXISTS (SELECT id_cliente FROM MONTO_TOTAL_ENTRADA_MADERA WHERE id_cliente = _id_cliente LIMIT 1) THEN 
		-- consultamos el monto total de las maderas que han entrado
        SELECT monto_total INTO _monto_total FROM MONTO_TOTAL_ENTRADA_MADERA WHERE id_cliente = _id_cliente;
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
CREATE FUNCTION C_ANTICIPO_PROVEEDOR (_id_cliente VARCHAR(30))
RETURNS DECIMAL(15,2)
BEGIN
	DECLARE _monto_total DECIMAL(15,2);
    
	-- consultamos si han entrado madera del proveedor
    IF EXISTS (SELECT id_cliente FROM MONTO_TOTAL_ANTICIPO WHERE id_cliente = _id_cliente LIMIT 1) THEN 
		-- consultamos el monto total de los anticipo
        SELECT monto_anticipo INTO _monto_total FROM MONTO_TOTAL_ANTICIPO WHERE id_cliente = _id_cliente;
        
		-- retornamos el monto total
        RETURN _monto_total;
	ELSE -- No hay entradas
		RETURN 0;
    END IF;
END;//
DELIMITER ;

-- Muestra cuentas por cobrar y por pagar a los clientes
-- : los negativos representan cuenta por pagar
-- : los positivos son cuentas por cobrar
CREATE VIEW CUENTAS_PROVEEDOR AS 
SELECT
	id_cliente,
    proveedor,
    id_jefe,
    ROUND(((SELECT C_ANTICIPO_PROVEEDOR(id_cliente))-(SELECT C_MADERA_ENTRADA_ROLLO(id_cliente))),2) AS monto
FROM PERSONAL_PROVEEDOR;

-- Cuentas por pagar a clientes
DROP VIEW IF EXISTS C_POR_PAGAR_PROVEEDOR;
CREATE VIEW C_POR_PAGAR_PROVEEDOR AS
SELECT
	id_cliente AS id_persona,
    proveedor AS persona,
    id_jefe,
    ABS(monto) AS monto
FROM CUENTAS_PROVEEDOR WHERE monto < 0;

-- Cuentas por cobrar a clientes
DROP VIEW IF EXISTS C_POR_COBRAR_CLIENTE;
CREATE VIEW C_POR_COBRAR_CLIENTE AS
SELECT
	id_cliente AS id_persona,
    proveedor AS persona,
    id_jefe,
    ABS(monto) AS monto
FROM CUENTAS_PROVEEDOR WHERE monto > 0;