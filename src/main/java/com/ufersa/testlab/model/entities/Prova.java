package com.ufersa.testlab.model.entities;

import java.util.Date;
import java.util.List;

public class Prova {
    private long id;
    private String disciplina;
    private Questoes questoes;
    private Date dataCriacao;

    // Construtor
    public Prova(long id, String disciplina, Questoes questoes, Date dataCriacao) {
        setId(id);
        setDisciplina(disciplina);
        setQuestoes(questoes);
        setDataCriacao(dataCriacao);
    }

    // Setters
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

    // Getters
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

    // Exibe dados da prova
    public void getProva() {
        System.out.println("Id: " + getId());
        System.out.println("Disciplina: " + getDisciplina());
        System.out.println("Questões: " + getQuestoes());
        System.out.println("Data de criação: " + getDataCriacao());
    }
    //metodo que cria prova
    public static Prova criarProva(long id, String disciplina, Questoes questoes, Date dataCriacao) {
        return new Prova(id, disciplina, questoes, dataCriacao);
    }

    //metodo que deleta prova
    public static boolean deletarProva(List<Prova> provas, long id) {
        return provas.removeIf(prova -> prova.getId() == id);
    }

}