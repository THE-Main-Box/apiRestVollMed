-- Migração para corrigir a referência na tabela 'cancelamento_consultas'
ALTER TABLE cancelamento_consultas
DROP CONSTRAINT fk_cancelamento_consultas_consulta_id;

ALTER TABLE cancelamento_consultas
ADD COLUMN consulta_id BIGINT;

ALTER TABLE cancelamento_consultas
ADD CONSTRAINT fk_cancelamento_consultas_consulta_id
FOREIGN KEY (consulta_id) REFERENCES consultas(id);
