WITH uniq AS (SELECT DISTINCT ON (fecha_registro) id as cantidad
FROM   entrada_socio
WHERE fecha_registro BETWEEN (SELECT fecha_inicio_membresia FROM membresia_datos md, membresia_usuario mu WHERE mu.socio_id=9 AND mu.id=md.membresia_socio_id) and (SELECT fecha_fin_membresia FROM membresia_datos md, membresia_usuario mu WHERE mu.socio_id=9 AND mu.id=md.membresia_socio_id)
AND socio_id=9
ORDER  BY fecha_registro DESC)
SELECT COUNT(*) FROM uniq;