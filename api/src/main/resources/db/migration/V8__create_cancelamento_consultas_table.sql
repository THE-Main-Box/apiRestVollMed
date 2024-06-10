CREATE TABLE cancelamento_consultas (
  id SERIAL PRIMARY KEY,
  consulta BIGINT NOT NULL,
  motivo VARCHAR(255) NOT NULL,
  data_hora TIMESTAMP NOT NULL,
  CONSTRAINT fk_cancelamento_consultas_consulta_id FOREIGN KEY (consulta) REFERENCES consultas(id)
);

ALTER TABLE consultas ADD COLUMN cancelada BOOLEAN DEFAULT FALSE;

