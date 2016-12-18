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
