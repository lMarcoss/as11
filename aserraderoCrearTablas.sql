-- Crear tablas proyecto aserradero
-- Base de datos aserradero
drop database if exists aserradero;
CREATE DATABASE aserradero CHARACTER SET utf8 COLLATE utf8_general_ci;
USE aserradero;

CREATE TABLE MUNICIPIO(
	nombre_municipio	VARCHAR(45) NOT NULL,
    estado 				VARCHAR(60),
	telefono 			CHAR(10),
	PRIMARY KEY (nombre_municipio))ENGINE=InnoDB;

CREATE TABLE LOCALIDAD(
	nombre_localidad	VARCHAR(45) NOT NULL,
	nombre_municipio	VARCHAR(45) NOT NULL,
	telefono 			CHAR(10),
	PRIMARY KEY (nombre_localidad),
	FOREIGN KEY (nombre_municipio) REFERENCES MUNICIPIO (nombre_municipio) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE PERSONA(
	id_persona			CHAR(18) NOT NULL,
	nombre				VARCHAR(30) NOT NULL,
	apellido_paterno	VARCHAR(30) NOT NULL,
	apellido_materno	VARCHAR(30),
	localidad			VARCHAR(45),
    direccion			VARCHAR(60),
	sexo				ENUM('H','M'),
	fecha_nacimiento	DATE,
	telefono			CHAR(10),
	primary key(id_persona),
	FOREIGN KEY (localidad) REFERENCES LOCALIDAD (nombre_localidad) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE ADMINISTRADOR(
	id_administrador	VARCHAR(18) NOT NULL,
	PRIMARY KEY (id_administrador),
	FOREIGN KEY (id_administrador) REFERENCES PERSONA (id_persona) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;
    
CREATE TABLE EMPLEADO(
	id_empleado 	VARCHAR(26) NOT NULL,
    id_persona		CHAR(18) NOT NULL,
    id_jefe 		VARCHAR(18) NOT NULL,
	roll			ENUM('Administrador','Empleado','Vendedor','Chofer'),	
	estatus			ENUM('Activo','Inactivo'),
	PRIMARY KEY (id_empleado,id_jefe,roll),
	FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_jefe) REFERENCES ADMINISTRADOR (id_administrador) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

 -- CREATE TABLE EMPLEADO_JEFE(id_empleado 	VARCHAR(18) NOT NULL,id_jefe			VARCHAR(18) NOT NULL,PRIMARY KEY (id_empleado,id_jefe),FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE,	FOREIGN KEY (id_jefe) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;


CREATE	TABLE PAGO_EMPLEADO(
	id_pago_empleado INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto 			DECIMAL(10,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_pago_empleado,id_empleado),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE USUARIO(
	id_empleado 		VARCHAR(26) NOT NULL,
	nombre_usuario 		VARCHAR(30),
    contrasenia			varchar(50) NOT NULL,
	metodo 				ENUM('sha1'), -- metodo para encriptar contraseña
    email			VARCHAR(50),
    PRIMARY KEY(nombre_usuario),
    FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

-- entrada
CREATE TABLE PROVEEDOR(
	id_proveedor 	VARCHAR(26) NOT NULL,
	id_persona 		VARCHAR(18) NOT NULL,
	id_jefe			VARCHAR(18) NOT NULL,
	PRIMARY KEY (id_proveedor,id_jefe),
	FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_jefe) REFERENCES ADMINISTRADOR (id_administrador) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE COSTO_MADERA_ENTRADA(
	clasificacion	ENUM('Primario','Secundario','Terciario') NOT NULL,
	costo 			DECIMAL(8,2),
	PRIMARY KEY (clasificacion))ENGINE=InnoDB;

CREATE TABLE INVENTARIO_MADERA_ENTRADA(
	id_administrador	VARCHAR(18) NOT NULL,
	num_piezas		INT,
    volumen_total	DECIMAL(15,3),
	PRIMARY KEY (id_administrador),
    FOREIGN KEY (id_administrador) REFERENCES ADMINISTRADOR (id_administrador) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE ENTRADA_MADERA_ROLLO( -- entrada_madera	
	id_entrada	 		INT NOT NULL AUTO_INCREMENT,
	fecha 				DATE,
    id_proveedor		CHAR(26) NOT NULL,
    id_chofer			CHAR(26) NOT NULL,
	id_empleado 		CHAR(26) NOT NULL,
	num_piezas			INT,
    volumen_primario	DECIMAL(10,3),	-- cantidad de volumen primaria
    costo_primario		DECIMAL(10,2),	-- costo volumen primario
    volumen_secundario	DECIMAL(10,3),	-- cantidad de volumen primaria
    costo_secundario	DECIMAL(10,2),	-- cantidad de volumen primaria
    volumen_terciario	DECIMAL(10,3),
    costo_terciario		DECIMAL(10,2),	-- cantidad de volumen primaria
	PRIMARY KEY (id_entrada),
    FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_chofer) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE SALIDA_MADERA_ROLLO( -- entrada_madera	
	id_salida	 		INT NOT NULL AUTO_INCREMENT,
	fecha 				DATE,
	id_empleado 		CHAR(26) NOT NULL,
	num_piezas			INT,
    volumen_total	DECIMAL(10,3),	-- cantidad de volumen primaria
	PRIMARY KEY (id_salida),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;
-- CREATE TABLE DETALLE_COMPRA(id_compra		CHAR(9) NOT NULL,clasificacion	ENUM('Primario','Secundario','Terciario') NOT NULL,volumen			DECIMAL(8,3),monto 			DECIMAL(10,2),PRIMARY KEY (id_compra,clasificacion),FOREIGN KEY (id_compra) REFERENCES COMPRA (id_compra) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (clasificacion) REFERENCES COSTO_MADERA_COMPRA (clasificacion) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;	

-- CREATE TABLE PAGO_COMPRA(fecha		DATE,id_compra	CHAR(7) NOT NULL,monto 		DECIMAL(10,2),pago 		ENUM('Anticipado','Normal'),PRIMARY KEY (fecha,id_compra),	FOREIGN KEY (id_compra) REFERENCES COMPRA (id_compra) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

-- producción
CREATE TABLE PRODUCCION_MADERA(
	fecha 			DATE,
	id_produccion	CHAR(10) NOT NULL,
	id_empleado 	CHAR(26) NOT NULL,
	PRIMARY KEY (id_produccion),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE MADERA_CLASIFICACION(
	id_madera	VARCHAR(20) NOT NULL,
	grueso		DECIMAL(6,2),
	ancho		DECIMAL(6,2),
	largo		DECIMAL(6,2),
	volumen		DECIMAL(8,3),
	primary key(id_madera))ENGINE=InnoDB;

-- CREATE TABLE PRODUCCION_DETALLE(
-- 	id_produccion	CHAR(10) NOT NULL,
-- 	id_madera    	VARCHAR(20) NOT NULL,
-- 	num_piezas 		INT,
-- 	PRIMARY KEY (id_produccion,id_madera),
-- 	FOREIGN KEY (id_produccion) REFERENCES PRODUCCION_MADERA (id_produccion) ON DELETE CASCADE ON UPDATE CASCADE,
-- 	FOREIGN KEY (id_madera) REFERENCES MADERA_CLASIFICACION (id_madera) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

-- CREATE TABLE INVENTARIO_MADERA_PRODUCCION(
-- 	id_madera	VARCHAR(20) NOT NULL,
-- 	num_piezas	INT,
-- 	PRIMARY KEY(id_madera),
-- 	FOREIGN KEY (id_madera) REFERENCES MADERA_CLASIFICACION (id_madera) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE CLIENTE(
	id_cliente 	CHAR(26) NOT NULL,
    id_persona 	CHAR(18) NOT NULL,
	id_jefe		CHAR(18),
	PRIMARY KEY(id_cliente,id_jefe),
	FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_jefe) REFERENCES ADMINISTRADOR (id_administrador) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;


CREATE TABLE VENTA(
	fecha 		DATE,
	id_venta 	INT NOT NULL AUTO_INCREMENT,
	id_cliente 	CHAR(26) NOT NULL,
	id_empleado CHAR(26),
	estatus 	ENUM('Pagado','Sin pagar'),
	tipo_venta 	ENUM('Paquete','Mayoreo','Extra'),
    tipo_pago 	ENUM('Anticipado','Normal'),
	PRIMARY KEY(id_venta),
	FOREIGN KEY (id_cliente) REFERENCES CLIENTE (id_cliente) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE VENTA_MAYOREO(
	id_venta 	INT NOT NULL,
	id_madera 	VARCHAR(20),
	num_piezas	INT,
	volumen 	DECIMAL(8,3),
	monto		DECIMAL(10,2),
	PRIMARY KEY(id_venta,id_madera),
	FOREIGN KEY (id_venta) REFERENCES VENTA (id_venta) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_madera) REFERENCES MADERA_CLASIFICACION (id_madera) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE VENTA_PAQUETE(
	id_venta 		INT NOT NULL,
	numero_paquete	INT,
	id_madera 		VARCHAR(20),
	num_piezas		INT,
	volumen 		DECIMAL(8,3),
	monto		DECIMAL(10,2),
	PRIMARY KEY(id_venta,numero_paquete,id_madera),
	FOREIGN KEY (id_venta) REFERENCES VENTA (id_venta) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_madera) REFERENCES MADERA_CLASIFICACION (id_madera) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE VENTA_EXTRA(
	id_venta 		INT NOT NULL,
	tipo 			VARCHAR(50),
	monto			DECIMAL(10,2),
	observacion		VARCHAR(100),
	PRIMARY KEY(id_venta,tipo),
	FOREIGN KEY (id_venta) REFERENCES VENTA (id_venta) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE COSTO_MADERA(
	id_madera 		VARCHAR(20),
	monto_volumen	DECIMAL(10,2),
	PRIMARY KEY(id_madera),
	FOREIGN KEY (id_madera) REFERENCES MADERA_CLASIFICACION (id_madera) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE	TABLE PAGO_RENTA(
	id_pago_renta	INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	nombre_persona	VARCHAR(50),
	id_empleado 	VARCHAR(26) NOT NULL,
	monto 			DECIMAL(10,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_pago_renta),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;
-- INICIAR id_pago_renta con 1
-- ALTER TABLE PAGO_RENTA AUTO_INCREMENT=1;
-- insertar dato en PAGO RENTA: NO SE INSERTA EL ID_PAGO_RENTA
-- INSERT INTO Persons (FirstName,LastName) VALUES ('Lars','Monsen');

CREATE	TABLE PAGO_LUZ(
	id_pago_luz		INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto 			DECIMAL(10,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_pago_luz),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE	TABLE OTRO_GASTO(
	id_gasto		INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_empleado 	VARCHAR(26) NOT NULL,
	nombre_gasto	VARCHAR(250),
	monto 			DECIMAL(10,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_gasto),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE ANTICIPO_CLIENTE(
	id_anticipo_c	INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_cliente 		CHAR(26) NOT NULL,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto_anticipo	DECIMAL(10,2),
	PRIMARY KEY(id_anticipo_c),
	FOREIGN KEY (id_cliente) REFERENCES CLIENTE (id_cliente) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE ANTICIPO_PROVEEDOR(
	id_anticipo_p	INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_proveedor	CHAR(26) NOT NULL,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto_anticipo	DECIMAL(10,2),
	PRIMARY KEY(id_anticipo_p),
	FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;


CREATE TABLE CUENTA_POR_PAGAR(
	id_persona 	CHAR(26) NOT NULL, -- puede ser id_proveedor o id_cliente
	monto		DECIMAL(10,2),
	PRIMARY KEY(id_persona))ENGINE=InnoDB;

CREATE TABLE CUENTA_POR_COBRAR(
	id_persona 	CHAR(26) NOT NULL, -- puede ser id_cliente o id_proveedor
	monto		DECIMAL(10,2),
	PRIMARY KEY(id_persona))ENGINE=InnoDB;


CREATE TABLE VEHICULO(
	id_vehiculo		INT NOT NULL AUTO_INCREMENT,
	matricula		VARCHAR(20) NOT NULL,
	tipo			VARCHAR(20),
	color			VARCHAR(20),
	carga_admitida	VARCHAR(20),
	motor			VARCHAR(20),
	modelo			VARCHAR(20),
	costo 			DECIMAL(10,2),
	id_empleado		VARCHAR(26) NOT NULL,
	PRIMARY KEY(id_vehiculo),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

-- Disparador para insertar un administrador como empleado su jefe será él mismo
DELIMITER //
CREATE TRIGGER EMPLEADO  AFTER INSERT ON ADMINISTRADOR
FOR EACH ROW
BEGIN
INSERT INTO EMPLEADO SET
				EMPLEADO.id_empleado = (select concat (NEW.id_administrador,substring(NEW.id_administrador,1,8))),
				EMPLEADO.id_persona = NEW.id_administrador,
				EMPLEADO.id_jefe = NEW.id_administrador,
				EMPLEADO.roll = 'Empleado',
                EMPLEADO.estatus= 'Activo';
END;//
DELIMITER ;

-- Insertamos datos ficticios
INSERT INTO MUNICIPIO (nombre_municipio, estado,telefono) VALUES 
("Miahuatlan de porfirio diaz","Oaxaca",'9876543210'),
("Santa Cruz Mixtepec","Oaxaca",'9876543211'),
("Oaxaca de Juarez","Oaxaca",'9876543212'),
('Santa Cruz Xitla', "Oaxaca",'951901872');

INSERT INTO LOCALIDAD (nombre_localidad,nombre_municipio,telefono) VALUES 
("San Mateo Mixtepec","Santa Cruz Mixtepec",'9870654321'),
("Santa Cruz Monjas","Miahuatlan de porfirio diaz",'9870654322'),
('Xitla', 'Santa Cruz Xitla', '4435628711');

INSERT INTO PERSONA (id_persona,nombre,apellido_paterno,apellido_materno,localidad,sexo,fecha_nacimiento,telefono) VALUES 
("MASL19931106HOCRNN","Leonardo","Marcos","Santiago","San Mateo Mixtepec","H","1993-11-06","9876543210"),
("COXN20160915HOCRXX","Noe","Cortes","","Santa Cruz Monjas","H","2016-09-15","1234567890"),
("MAXP20160916HOCRXD","Pedro","Martinez","","Santa Cruz Monjas","H","2016-09-16","1234567890"),
("PAXA20160913HOCSXN","Antonio","Pascual","","Santa Cruz Monjas","H","2016-09-13","1234567890"),
("PEXF20160910HOCRXR","Francisco","Perez","","Santa Cruz Monjas","H","2016-09-10","1234567890");

-- lista de administradores
CREATE VIEW PERSONAL_ADMINISTRADOR AS
SELECT id_administrador, (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_administrador)as nombre FROM ADMINISTRADOR;
SELECT * FROM PERSONAL_ADMINISTRADOR;

-- lista a todo el personal Cliente id_cliente y nombre completo, id_jefe y nombre completo
CREATE VIEW PERSONAL_CLIENTE AS
SELECT id_cliente,
		id_persona,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = CLIENTE.id_persona) as cliente,
        id_jefe,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_jefe) as jefe
	FROM CLIENTE,ADMINISTRADOR 
	WHERE CLIENTE.id_jefe = id_administrador;
SELECT * FROM PERSONAL_CLIENTE;

-- lista a todo el personal Proveedor id_proveedor y nombre completo, id_jefe y nombre completo
CREATE VIEW PERSONAL_PROVEEDOR AS 
SELECT id_proveedor,
		id_persona,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = PROVEEDOR.id_persona) as proveedor,
		id_jefe,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_jefe) as jefe
	FROM PROVEEDOR,ADMINISTRADOR WHERE PROVEEDOR.id_jefe = id_administrador;
SELECT * FROM PERSONAL_PROVEEDOR;

-- lista de empleados
CREATE VIEW PERSONAL_EMPLEADO AS
SELECT id_empleado,
		id_persona,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE PERSONA.id_persona = EMPLEADO.id_persona) as empleado,
        id_jefe,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_jefe) as jefe, 
        roll,estatus FROM EMPLEADO,ADMINISTRADOR WHERE id_jefe = id_administrador;
SELECT * FROM PERSONAL_EMPLEADO;

-- lista de vehículos con nombre completo del empleado
CREATE VIEW VISTA_VEHICULO AS
SELECT id_vehiculo, 
		matricula, 
        tipo, 
        color, 
        carga_admitida, 
        motor, 
        modelo, 
        costo, 
        VEHICULO.id_empleado, 
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM EMPLEADO,PERSONA where EMPLEADO.id_persona = PERSONA.id_persona and EMPLEADO.id_empleado = VEHICULO.id_empleado) as empleado, 
        (select id_jefe FROM EMPLEADO,ADMINISTRADOR WHERE EMPLEADO.id_jefe = ADMINISTRADOR.id_administrador and EMPLEADO.id_empleado = VEHICULO.id_empleado) as id_jefe
	FROM VEHICULO;
SELECT * FROM VISTA_VEHICULO;

-- DROP VIEW VISTA_ENTRADA_MADERA;
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
    (volumen_primario+volumen_secundario+volumen_terciario) as volumen_total,
    (volumen_primario * costo_primario + volumen_secundario * costo_secundario + volumen_terciario * costo_terciario) as monto_total
FROM ENTRADA_MADERA_ROLLO;
SELECT * FROM VISTA_ENTRADA_MADERA_ROLLO;

-- Disparador para insertar en inventario entrada cada que se inserta en entrada madera
DROP TRIGGER IF EXISTS INVENTARIO_MADERA_ENTRADA;
DELIMITER //
CREATE TRIGGER INVENTARIO_MADERA_ENTRADA BEFORE INSERT ON ENTRADA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	DECLARE _id_administrador VARCHAR(18);	
    DECLARE _costo_primario DECIMAL(10,2);
    DECLARE _costo_secundario DECIMAL(10,2);
    DECLARE _costo_terciario DECIMAL(10,2);
    
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


-- Lista de pago a empleados
CREATE VIEW VISTA_PAGO_EMPLEADO AS 
SELECT id_pago_empleado,
		fecha,
        EMPLEADO.id_empleado AS id_empleado,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = EMPLEADO.id_persona)as empleado,
        EMPLEADO.id_jefe AS id_jefe,
        monto,
        observacion 
FROM PAGO_EMPLEADO,EMPLEADO WHERE PAGO_EMPLEADO.id_empleado = EMPLEADO.id_empleado;
SELECT * FROM VISTA_PAGO_EMPLEADO;

-- Actualizar inventario cada que se modifica una entrada de madera
DELIMITER //
CREATE TRIGGER ACTUALIZAR_ENTRADA_MADERA_ROLLO BEFORE UPDATE ON ENTRADA_MADERA_ROLLO
FOR EACH ROW
BEGIN
	DECLARE _id_administrador VARCHAR(18);	
    DECLARE _costo_primario DECIMAL(10,2);
    DECLARE _costo_secundario DECIMAL(10,2);
    DECLARE _costo_terciario DECIMAL(10,2);
    DECLARE _volumen_total_old DECIMAL(10,3);
    DECLARE _monto_total_old DECIMAL(10,2);
    
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
    UPDATE INVENTARIO_MADERA_ENTRADA -- actualizamos inventario si existe inventario asociado al administrador
			SET num_piezas = (num_piezas - OLD.num_piezas), 
            volumen_total = (volumen_total - _volumen_total_old)
            WHERE id_administrador = _id_administrador;
    
	-- actualizamos inventario con los nuevos valores
	UPDATE INVENTARIO_MADERA_ENTRADA -- actualizamos inventario si existe inventario asociado al administrador
		SET num_piezas = (num_piezas + NEW.num_piezas), 
		volumen_total = (volumen_total + NEW.volumen_primario+NEW.volumen_secundario+NEW.volumen_terciario)
		WHERE id_administrador = _id_administrador;
END;//
DELIMITER ;