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
delete from INVENTARIO_MADERA_ENTRADA where id=1;
SELECT * FROM ENTRADA_MADERA;
describe INVENTARIO_MADERA_ENTRADA;

SELECT * FROM ENTRADA_MADERA_ROLLO;
