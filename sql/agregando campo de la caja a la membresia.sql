alter table membresia_usuario add column id_caja integer 
 ALTER TABLE membresia_usuario ADD CONSTRAINT fk_caja_membresia
   FOREIGN KEY (id_caja) 
   REFERENCES "caja"(id);