use aserradero;


--  actualizar valores de los inventarios en la actualización de los datos
DROP TRIGGER IF EXISTS MODIFICAR_VENTA_MAYOREO;
DELIMITER //
CREATE TRIGGER MODIFICAR_VENTA_MAYOREO  BEFORE UPDATE ON VENTA_MAYOREO
FOR EACH ROW
BEGIN
	DECLARE id_ventaOLD	INT;
    DECLARE id_maderaOLD	VARCHAR(20);
	DECLARE num_piezasOLD	INT;
	DECLARE volumenOLD 		DECIMAL(8,3);
	DECLARE montoOLD		DECIMAL(10,2);
    -- capturamos los valores antiguos
	SET id_maderaOLD = OLD.id_madera;
	SET num_piezasOLD = OLD.num_piezas;
	SET volumenOLD = OLD.volumen;
	SET montoOLD = OLD.monto;
     -- Modificamos inventarioMaderaProduccion
	CALL MOFICAR_INVENTARIO_MADERA_PROD(id_maderaOLD,num_piezasOLD);
    -- modificamos cuentas por cobrar, por pagar o cuenta efectiva dependiendo del tipo de pago
    CALL REVERTIR_PAGO(id_ventaOLD,montoOLD,NEW.monto);
END;//
DELIMITER ;
