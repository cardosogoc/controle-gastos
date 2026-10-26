CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT fk_categoria_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_categoria_usuario_nome_tipo
        UNIQUE (usuario_id, nome, tipo),

    CONSTRAINT chk_categoria_tipo
        CHECK (tipo IN ('RECEITA', 'DESPESA'))
);