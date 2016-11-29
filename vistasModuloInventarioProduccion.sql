use aserradero;

DROP VIEW IF EXISTS VISTA_INVENTARIO_PRODUCION;
CREATE VIEW VISTA_INVENTARIO_PRODUCION AS
SELECT
	id_administrador,
    IP.id_madera,
    num_piezas,
    (num_piezas * volumen) AS volumen_total,
    (num_piezas * volumen * costo_por_volumen) AS monto_total
FROM INVENTARIO_MADERA_PRODUCCION AS IP, MADERA_ASERRADA_CLASIF AS MAC WHERE IP.id_madera = MAC.id_madera;

