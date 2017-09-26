CREATE TABLE membresia_restriccion_entradas
(
  id serial NOT NULL,
  membresia_id integer NOT NULL,
  usuario_sistema_id integer,
  cantidad_dias integer NOT NULL,
  fecha_registro timestamp without time zone,
  CONSTRAINT membresia_restriccion_entradas_pkey PRIMARY KEY (id ),
  CONSTRAINT membresia_restriccion_semana_entradas_id_fkey FOREIGN KEY (membresia_id)
      REFERENCES membresia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT membresia_restriccion_entradas_usuario_sistema_id_fkey FOREIGN KEY (usuario_sistema_id)
      REFERENCES usuario_sistema (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE membresia_restriccion_entradas
  OWNER TO gidsoft;

-- Trigger: auditoria on membresia_restriccion_semana

-- DROP TRIGGER auditoria ON membresia_restriccion_semana;

CREATE TRIGGER auditoria
  AFTER INSERT OR UPDATE OR DELETE
  ON membresia_restriccion_entradas
  FOR EACH ROW
  EXECUTE PROCEDURE registrar_auditoria();
