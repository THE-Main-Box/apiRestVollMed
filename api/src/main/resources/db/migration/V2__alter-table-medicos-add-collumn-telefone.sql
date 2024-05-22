ALTER TABLE medicos ADD telefone VARCHAR(20);

UPDATE medicos SET telefone = 'sem telefone' WHERE telefone IS NULL;

ALTER TABLE medicos ALTER COLUMN telefone SET NOT NULL;