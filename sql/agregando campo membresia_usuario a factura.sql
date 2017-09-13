alter table factura add column id_membresia_usuario integer 
 ALTER TABLE factura ADD CONSTRAINT fk_usuario_membresia
   FOREIGN KEY (id_membresia_usuario) 
   REFERENCES "membresia_usuario"(id);