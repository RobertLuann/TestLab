package com.ufersa.testlab.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prova", uniqueConstraints = @UniqueConstraint(columnNames = "titulo"))
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
    @JoinColumn(name = "codigo_disciplina", referencedColumnName = "codigo")
    private Disciplina disciplina;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    public Prova() {}

    public Prova(String titulo, List<Questao> questoes, Disciplina disciplina) {
        setTitulo(titulo);
        setQuestoes(questoes);
        setDisciplina(disciplina);
        this.dataCriacao = LocalDateTime.now();
    }

    public void adicionarQuestao(Questao questao) {
        this.questoes.add(questao);
    }

    // Getters/Setters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

    public void removeQuestoes() {
        this.questoes.clear();
    }
}