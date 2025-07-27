package com.ufersa.testlab.model.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Alternativa {
    private String texto;
    private boolean correta; // novo campo adicional

    public Alternativa() {}

    public Alternativa(String texto, boolean correta) {
        setTexto(texto);
        setCorreta(correta);
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("O enunciado da alternativa não pode ser nulo ou vazio.");
        }
        this.texto = texto;
    }

    public boolean getCorreta() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }
}
