CREATE TABLE lancamento (
    id            BIGSERIAL PRIMARY KEY,
    descricao     VARCHAR(255)  NOT NULL,
    valor         NUMERIC(12,2) NOT NULL,
    data          DATE          NOT NULL,
    usuario_id    BIGINT        NOT NULL,
    categoria_id  BIGINT        NOT NULL,

    CONSTRAINT fk_lancamento_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_lancamento_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria (id),

    CONSTRAINT ck_lancamento_valor
        CHECK (valor > 0)
);

CREATE INDEX idx_lancamento_usuario_data
    ON lancamento (usuario_id, data);

CREATE INDEX idx_lancamento_categoria
    ON lancamento (categoria_id);