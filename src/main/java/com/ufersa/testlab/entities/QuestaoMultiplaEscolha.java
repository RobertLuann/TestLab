package com.ufersa.testlab.entities;

import java.util.List;

public class QuestaoMultiplaEscolha extends Questao {
    private List<String> opcoes;
    private Integer gabarito;

    public QuestaoMultiplaEscolha(String codigo, String enunciado, Disciplina disciplina, String assunto, Integer dificuldade, List<String> opcoes, Integer gabarito) {
        super(codigo, TipoQuestao.MULTIPLA_ESCOLHA, enunciado, disciplina, assunto, dificuldade);

        if (opcoes == null || opcoes.size() < 2) {
            throw new IllegalArgumentException("Mínimo de duas opções necessário!");
        }
        if (gabarito < 0 || gabarito >= opcoes.size()) {
            throw new IllegalArgumentException("Gabarito inválido!");
        }
        setOpcoes(opcoes);
        setGabarito(gabarito);
    }

    //setters
    public void setOpcoes(List<String> opcoes) { this.opcoes = opcoes; }
    public void setGabarito(Integer gabarito) { this.gabarito = gabarito; }

    //getters
    public List<String> getOpcoes() { return opcoes; }
    public Integer getGabarito() { return gabarito; }
    public String getOpcaoCorreta() { return opcoes.get(gabarito); }

    @Override
    public void getQuestao() {
        if (getTipo() == TipoQuestao.MULTIPLA_ESCOLHA) {
            System.out.println("Codigo: " + getCodigo());
            System.out.println("Tipo: " + getTipo());
            System.out.println("Enunciado: " + getEnunciado());
            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println((char)('A' + i) + ") " + opcoes.get(i));
            }
            System.out.println("Gabarito: " + getOpcaoCorreta());
            System.out.println("Disciplina: " + getDisciplina());
            System.out.println("Assunto: " + getAssunto());
            System.out.println("Dificuldade: " + getDificuldade() + "\n");
        }
    }
}