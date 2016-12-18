USE aserradero;

DROP VIEW IF EXISTS VISTA_USUARIO;

CREATE VIEW VISTA_USUARIO AS
SELECT 
	nombre_usuario,
    contrasenia,
    metodo,
    email,
    EMPLEADO.id_empleado AS id_empleado,
    id_persona,
    id_jefe,
    rol,
    estatus
FROM USUARIO,EMPLEADO
WHERE USUARIO.id_empleado = EMPLEADO.id_empleado;


-- INSERT INTO USUARIO (id_empleado,nombre_usuario,contrasenia,metodo,email) VALUES 
-- ('COXN20160915HOCRXXMASL1993','cortes',SHA1('cortes'),'sha1','markos_193@hotmail.com');

-- DELETE FROM USUARIO WHERE nombre_usuario = 'cortes';
-- SELECT * FROM VISTA_USUARIO;
-- SELECT * FROM EMPLEADO;