package com.ufersa.testlab.model.entities;

public class QuestaoDissertativa extends Questao {
    private String resposta;

    public QuestaoDissertativa(String codigo, String enunciado, Disciplina disciplina, String assunto, Integer dificuldade, String resposta) {
        super(codigo, TipoQuestao.DISSERTATIVA, enunciado, disciplina, assunto, dificuldade);
        setResposta(resposta);
    }

    //setter e getter
    public void setResposta (String resposta) { this.resposta = resposta; }
    public String getResposta () { return resposta; }

    @Override
    public void getQuestao() {
        if (getTipo() == TipoQuestao.DISSERTATIVA) {
            System.out.println("Codigo: " + getCodigo());
            System.out.println("Tipo: " + getTipo());
            System.out.println("Enunciado: " + getEnunciado());
            System.out.println("Resposta: " + getResposta());
            System.out.println("Disciplina: " + getDisciplina());
            System.out.println("Assunto: " + getAssunto());
            System.out.println("Dificuldade: " + getDificuldade() + "\n");
        }
    }
}