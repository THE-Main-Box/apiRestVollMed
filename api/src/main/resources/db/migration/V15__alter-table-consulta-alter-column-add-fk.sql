ALTER TABLE consultas
ADD CONSTRAINT fk_cancelamento_consulta_id
FOREIGN KEY (cancelamento_consulta_id) REFERENCES cancelamento_consultas(id);