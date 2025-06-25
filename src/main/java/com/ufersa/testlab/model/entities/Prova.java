package com.ufersa.testlab.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prova")
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @ManyToMany
    @JoinTable(
            name = "prova_questao",
            joinColumns = @JoinColumn(name = "prova_codigo"),
            inverseJoinColumns = @JoinColumn(name = "questao_codigo")
    )
    private List<Questao> questoes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "codigo_disciplina")
    private Disciplina disciplina;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    public Prova() {}

    public Prova(String titulo, List<Questao> questoes, Disciplina disciplina) {
        this.titulo = titulo;
        this.questoes = questoes;
        this.disciplina = disciplina;
        this.dataCriacao = LocalDateTime.now();
    }

    public void adicionarQuestao(Questao questao) {
        if (this.questoes == null) {
            this.questoes = new ArrayList<>();
        }
        this.questoes.add(questao);
    }

    public void removerQuestao(Questao questao) {
        if (this.questoes != null) {
            this.questoes.remove(questao);
        }
    }

    //Getters/Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public List<Questao> getQuestoes() {
        return questoes;
    }
    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }
    public Disciplina getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}