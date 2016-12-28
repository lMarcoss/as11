USE aserradero;

-- Submódulo Anticipo cliente-- Submódulo Anticipo cliente-- Submódulo Anticipo cliente
-- Submódulo Anticipo cliente-- Submódulo Anticipo cliente-- Submódulo Anticipo cliente

-- vista de anticipo clientes
DROP VIEW IF EXISTS VISTA_ANTICIPO_CLIENTE;
CREATE VIEW VISTA_ANTICIPO_CLIENTE AS
SELECT
	id_anticipo_c,
    fecha,
    id_cliente,
    (SELECT CONCAT(nombre, ' ', apellido_paterno, ' ', apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(A.id_cliente,1,18) LIMIT 1) AS cliente,
    id_empleado,
    (SELECT CONCAT(nombre, ' ', apellido_paterno, ' ', apellido_materno) FROM PERSONA WHERE id_persona = SUBSTRING(A.id_empleado,1,18) LIMIT 1) AS empleado,
    monto_anticipo,
    (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = A.id_empleado LIMIT 1) AS id_jefe
FROM ANTICIPO_CLIENTE AS A;

-- Submódulo Anticipo proveedor-- Submódulo Anticipo proveedor-- Submódulo Anticipo proveedor
-- Submódulo Anticipo proveedor-- Submódulo Anticipo proveedor-- Submódulo Anticipo proveedor

-- vista de anticipo proveedores
DROP VIEW IF EXISTS VISTA_ANTICIPO_PROVEEDOR;
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



-- funcion para consultar $ costo total de madera en rollo por cada proveedor (pagados y no pagados)
-- Se resta el dinero de pagos
DROP FUNCTION IF EXISTS C_MADERA_ENTRADA_ROLLO;
DELIMITER //
CREATE FUNCTION C_MADERA_ENTRADA_ROLLO (_id_proveedor VARCHAR(30))
RETURNS DECIMAL(20,2)
BEGIN
	DECLARE _monto_madera DECIMAL(20,2);
    DECLARE _pago DECIMAL(20,2);
    
    -- consultamos si han entrado madera del proveedor
    IF EXISTS (SELECT id_proveedor FROM ENTRADA_M_ROLLO WHERE id_proveedor = _id_proveedor LIMIT 1) THEN 
		-- consultamos el monto total de las maderas que han entrado
        SELECT SUM(costo_total) INTO _monto_madera FROM VISTA_ENTRADA_M_ROLLO WHERE id_proveedor = _id_proveedor GROUP BY id_proveedor;
	ELSE -- No hay entradas
		SET _monto_madera = 0;
    END IF;
    
    -- Consultamos si hay pagos al proveedor
    IF EXISTS(SELECT id_proveedor FROM PAGO_COMPRA WHERE id_proveedor = _id_proveedor) THEN
		SELECT SUM(monto_pago) INTO _pago FROM PAGO_COMPRA WHERE id_proveedor =_id_proveedor GROUP BY id_proveedor;
	ELSE
		SET _pago = 0;
    END IF;

    
    RETURN (_monto_madera - _pago);
END;//
DELIMITER ;

SELECT * FROM PAGO_COMPRA;

-- funcion para consultar $ monto total de los anticipos dados al proveedor
DROP FUNCTION IF EXISTS C_ANTICIPO_PROVEEDOR;
DELIMITER //
CREATE FUNCTION C_ANTICIPO_PROVEEDOR (_id_proveedor VARCHAR(30))
RETURNS DECIMAL(20,2)
BEGIN
	DECLARE _monto_total DECIMAL(20,2);
    
	-- consultamos si han entrado madera del proveedor
    IF EXISTS (SELECT id_proveedor FROM ANTICIPO_PROVEEDOR WHERE id_proveedor = _id_proveedor LIMIT 1) THEN 
		-- consultamos el monto total de los anticipo
        SELECT SUM(monto_anticipo) INTO _monto_total FROM ANTICIPO_PROVEEDOR WHERE id_proveedor = _id_proveedor GROUP BY id_proveedor;
		-- retornamos el monto total
        RETURN _monto_total;
	ELSE 
		RETURN 0;
    END IF;
END;//
DELIMITER ;

-- Muestra cuentas por cobrar y por pagar a los proveedores
-- : los negativos representan cuenta por pagar
-- : los positivos son cuentas por cobrar
DROP VIEW IF EXISTS CUENTAS_PROVEEDOR;
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
	id_proveedor AS id_persona, -- se renombra para utilizar la misma clase con cuentas del cliente
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

SELECT * FROM ENTRADA_M_ROLLO;

SELECT * FROM C_POR_COBRAR_PROVEEDOR;
SELECT * FROM C_POR_PAGAR_PROVEEDOR;