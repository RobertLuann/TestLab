package com.ufersa.testlab.model.enums;

public enum Dificuldade {
    FACIL("Fácil"),
    MEDIO("Médio"),
    DIFICIL("Difícil");

    private final String descricao;

    Dificuldade(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}