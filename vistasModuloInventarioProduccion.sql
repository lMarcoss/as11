use aserradero;

DROP VIEW IF EXISTS VISTA_INVENTARIO_PRODUCION;
CREATE VIEW VISTA_INVENTARIO_PRODUCION AS
SELECT
	id_administrador,
    IP.id_madera,
    num_piezas,
    (num_piezas * volumen) AS volumen_total,
    (num_piezas * volumen * monto_volumen) AS monto_total
FROM INVENTARIO_MADERA_PRODUCCION AS IP, MADERA_CLASIFICACION AS MC, COSTO_MADERA AS CM WHERE IP.id_madera = CM.id_madera AND IP.id_madera = MC.id_madera;

