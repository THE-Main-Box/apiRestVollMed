ALTER TABLE medicos ADD ativo boolean;

UPDATE medicos SET ativo = true WHERE ativo IS NULL;

ALTER TABLE medicos ALTER COLUMN ativo SET NOT NULL;