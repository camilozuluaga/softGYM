alter table pago_membresia add column id_caja integer ;
ALTER TABLE pago_membresia ADD CONSTRAINT fk_caja_membre
   FOREIGN KEY (id_caja) 
   REFERENCES "caja"(id);