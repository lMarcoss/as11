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

-- lista a todo el personal Cliente id_cliente y nombre completo, id_jefe y nombre completo
DROP VIEW IF EXISTS PERSONAL_CLIENTE;
CREATE VIEW PERSONAL_CLIENTE AS
SELECT id_cliente,
		id_persona,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = CLIENTE.id_persona) as cliente,
        id_jefe,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_jefe) as jefe
	FROM CLIENTE,ADMINISTRADOR 
	WHERE CLIENTE.id_jefe = id_administrador;
-- SELECT * FROM PERSONAL_CLIENTE;

-- lista a todo el personal Proveedor id_proveedor y nombre completo, id_jefe y nombre completo
DROP VIEW IF EXISTS PERSONAL_PROVEEDOR;
CREATE VIEW PERSONAL_PROVEEDOR AS 
SELECT id_proveedor,
		id_persona,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE id_persona = PROVEEDOR.id_persona) as proveedor,
		id_jefe,
        (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) as nombre FROM PERSONA WHERE id_persona = id_jefe) as jefe
	FROM PROVEEDOR,ADMINISTRADOR WHERE PROVEEDOR.id_jefe = id_administrador;
-- SELECT * FROM PERSONAL_PROVEEDOR;

-- lista de vehículos con nombre completo del empleado
DROP VIEW IF EXISTS VISTA_VEHICULO;
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
-- SELECT * FROM VISTA_VEHICULO;