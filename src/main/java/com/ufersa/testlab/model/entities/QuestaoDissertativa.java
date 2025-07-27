package com.ufersa.testlab.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DISSERTATIVA")
public class QuestaoDissertativa extends Questao {

    @Column(nullable = true)
    private String resposta;

    public QuestaoDissertativa() {}

    public QuestaoDissertativa(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, String resposta) {
        super(codigo, enunciado, codigoDisciplina, assunto, dificuldade);
        setResposta(resposta);
    }

    //setter e getter
    public void setResposta (String resposta) {
        if (resposta == null || resposta.isBlank()) {
            throw new IllegalArgumentException("O código da disciplina não pode ser nulo ou vazio.");
        }
        this.resposta = resposta;
    }
    public String getResposta () { return resposta; }

    @Override
    public void getQuestao() {
        System.out.println("Codigo: " + getCodigo());
        System.out.println("Tipo: Dissertativa");
        System.out.println("Enunciado: " + getEnunciado());
        System.out.println("Resposta: " + getResposta());
        System.out.println("Disciplina: " + getDisciplina());
        System.out.println("Assunto: " + getAssunto());
        System.out.println("Dificuldade: " + getDificuldade() + "\n");
    }
}