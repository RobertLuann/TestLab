package com.ufersa.testlab.model.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Alternativa {
    private String texto;
    private String afirmativa; // novo campo adicional

    public Alternativa() {}

    public Alternativa(String texto, String afirmativa) {
        this.texto = texto;
        this.afirmativa = afirmativa;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAfirmativa() {
        return afirmativa;
    }

    public void setAfirmativa(String afirmativa) {
        this.afirmativa = afirmativa;
    }
}
