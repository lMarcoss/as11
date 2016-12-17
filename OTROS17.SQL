
-- Disparador para consultar administrador y asignarlo a la variable id_administrador antes de insertar en la tabla COSTO_MADERA_ENTRADA
DROP TRIGGER IF EXISTS COSTO_MADERA_ENTRADA;
DELIMITER //
CREATE TRIGGER COSTO_MADERA_ENTRADA  BEFORE INSERT ON COSTO_MADERA_ENTRADA
FOR EACH ROW
BEGIN
	DECLARE id_administrador VARCHAR(18);
        
    -- Consultamos el id_administrador
	SELECT id_jefe INTO id_administrador FROM EMPLEADO WHERE id_empleado = NEW.id_empleado LIMIT 1;
    
    -- asignamos el id_administrador al New.id_administrador: para que se inserte en la BD al hacer la transacción de insert
    SET NEW.id_administrador = id_administrador;
END;//
DELIMITER ;


-- Disparador para consultar administrador y asignarlo a la variable id_administrador antes de insertar en la tabla MADERA_ASERRADA_CLASIF
DROP TRIGGER IF EXISTS MADERA_ASERRADA_CLASIF;
DELIMITER //
CREATE TRIGGER MADERA_ASERRADA_CLASIF BEFORE INSERT ON MADERA_ASERRADA_CLASIF
FOR EACH ROW
BEGIN
	DECLARE id_administrador VARCHAR(18);
        
    -- Consultamos el id_administrador
	SELECT id_jefe INTO id_administrador FROM EMPLEADO WHERE id_empleado = NEW.id_empleado LIMIT 1;
    
    -- asignamos el id_administrador al New.id_administrador: para que se inserte en la BD al hacer la transacción de insert
    SET NEW.id_administrador = id_administrador;
END;//
DELIMITER ;


-- INSERT INTO ADMINISTRADOR (id_administrador) VALUES ('MASL19931106HOCRNN');
-- INSERT INTO CLIENTE (id_cliente, id_persona, id_jefe) VALUES
-- ('COXN20160915HOCRXXMASL1993', 'COXN20160915HOCRXX', 'MASL19931106HOCRNN'),
-- ('MAXP20160916HOCRXDMASL1993', 'MAXP20160916HOCRXD', 'MASL19931106HOCRNN');


INSERT INTO MADERA_ASERRADA_CLASIF (id_administrador,id_empleado,id_madera,grueso,ancho,largo,volumen,costo_por_volumen) VALUES 
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"clase12",0.75,12,8.25,6.187,15),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"clase10",0.75,10,8.25,5.156,14),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"clase8",0.75,8,8.25,4.125,13),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"clase6",0.75,6,8.25,3.093,12),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"clase4",0.75,4,8.25,2.062,11),

('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"tercera12",0.75,12,8.25,6.187,5),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"tercera10",0.75,10,8.25,5.156,5),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"tercera8",0.75,8,8.25,4.125,5),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"tercera6",0.75,6,8.25,3.093,5),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"tercera4",0.75,4,6.25,2.062,5),

('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"cuarta12",0.75,12,8.25,6.187,7),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"cuarta10",0.75,10,8.25,5.156,7),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"cuarta8",0.75,8,8.25,4.125,7),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"cuarta6",0.75,6,8.25,3.093,7),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"cuarta4",0.75,4,6.25,2.062,7),

('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"quinta12",0.75,12,8.25,6.187,3),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"quinta10",0.75,10,8.25,5.156,3),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"quinta8",0.75,8,8.25,4.125,3),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"quinta6",0.75,6,8.25,3.093,3),
('MASL19931106HOCRNN','MASL19931106HOCRNNMASL1993',"quinta4",0.75,4,6.25,2.062,3);


-- SELECT * FROM PERSONAL_ADMINISTRADOR;



-- SELECT * FROM PERSONAL_EMPLEADO;




-- SELECT * FROM VISTA_PAGO_EMPLEADO;

CREATE VIEW VISTA_ENTRADA_MADERA_ASERRADA AS
SELECT 
	id_entrada,
    fecha,
    id_madera,
    num_piezas,
    id_empleado,
    (select concat (nombre,' ',apellido_paterno,' ',apellido_materno) from PERSONA where id_persona = SUBSTRING(ENTRADA_MADERA_ASERRADA.id_empleado,1,18)) as empleado,
    (select id_jefe from EMPLEADO where id_empleado = ENTRADA_MADERA_ASERRADA.id_empleado) as id_jefe
FROM ENTRADA_MADERA_ASERRADA;


CREATE VIEW VISTA_ANTICIPO_CLIENTE AS 
SELECT id_anticipo_c,
		fecha,
        id_cliente,
        (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(ANTICIPO_CLIENTE.id_cliente,1,18)) as cliente,
        id_empleado,
        (SELECT concat (nombre,' ',apellido_paterno,' ',apellido_materno) FROM PERSONA WHERE PERSONA.id_persona = SUBSTRING(ANTICIPO_CLIENTE.id_empleado,1,18)) as empleado,
        (SELECT id_jefe FROM EMPLEADO WHERE id_empleado = ANTICIPO_CLIENTE.id_empleado) as id_jefe,
        monto_anticipo
FROM ANTICIPO_CLIENTE;