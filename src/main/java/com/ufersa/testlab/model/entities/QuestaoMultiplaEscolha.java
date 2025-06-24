package com.ufersa.testlab.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("MULTIPLA_ESCOLHA")
public class QuestaoMultiplaEscolha extends Questao {

    @ElementCollection
    @CollectionTable(name = "alternativas", joinColumns = @JoinColumn(name = "questao_id"))
    @Column(name = "alternativa")
    private List<Alternativa> alternativas = new ArrayList<>();

    @Column(nullable = true)
    private Long gabarito; // índice da alternativa correta

    public QuestaoMultiplaEscolha() {
    }

    public QuestaoMultiplaEscolha(String codigo, String enunciado, String codigoDisciplina, String assunto,
                                  Long dificuldade, List<Alternativa> alternativas, Long gabarito) {
        super(codigo, enunciado, codigoDisciplina, assunto, dificuldade);
        setAlternativas(alternativas);
        setGabarito(gabarito);
    }

    // Setters com validação
    public void setAlternativa(Alternativa alternativa) {
        this.alternativas.add(alternativa);
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        for (Alternativa alternativa: alternativas) {
            setAlternativa(alternativa);
        }
    }

    public void setGabarito(Long gabarito) {
        if (alternativas == null || gabarito < 0 || gabarito >= alternativas.size()) {
            throw new IllegalArgumentException("Índice do gabarito inválido");
        }
        this.gabarito = gabarito;
    }

    // Getters
    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public Long getGabarito() {
        return gabarito;
    }

    public Alternativa getAlternativaCorreta() {
        return alternativas.get(gabarito.intValue());
    }

    public void limparAlternativas() {
        alternativas.clear();
    }

    @Override
    public void getQuestao() {
        System.out.println("Código: " + getCodigo());
        System.out.println("Tipo: Multipla Escolha");
        System.out.println("Enunciado: " + getEnunciado());

        for (int i = 0; i < alternativas.size(); i++) {
            System.out.println((char)('A' + i) + ") " + alternativas.get(i));
        }

        System.out.println("Gabarito: " + getAlternativaCorreta());
        System.out.println("Disciplina: " + getCodigo());
        System.out.println("Assunto: " + getAssunto());
        System.out.println("Dificuldade: " + getDificuldade());
        System.out.println();
    }
}
