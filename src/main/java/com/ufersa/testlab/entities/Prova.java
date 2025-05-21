package com.ufersa.testlab.entities;
import java.util.Date;

public class Prova {
    private long id;
    private String disciplina;
    private Questoes questoes;
    private Date dataCriacao;
//construtor
public Prova(long id, String disciplina, Questoes questoes, Date dataCriacao) {
    setId(id);
    setDisciplina(disciplina);
    setQuestoes(questoes);
    setDataCriacao(dataCriacao);
}

    // Set
    public void setId(long id) {
        this.id = id;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setQuestoes(Questoes questoes) {
        this.questoes = questoes;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // Get
    public Long getId() {
        return this.id;
    }

    public String getDisciplina() {
        return this.disciplina;
    }

    public Questoes getQuestoes() {
        return this.questoes;
    }

    public Date getDataCriacao() {
        return this.dataCriacao;
    }

    // exibe dados da prova
    public void getProva() {
        System.out.println("Id: " + getId());
        System.out.println("Disciplina: " + getDisciplina());
        System.out.println("Questões: " + getQuestoes());
        System.out.println("Data de criação: " + getDataCriacao());
    }
}

