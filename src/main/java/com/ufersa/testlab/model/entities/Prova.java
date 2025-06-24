package com.ufersa.testlab.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_prova")
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @OneToMany(mappedBy = "prova", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Questao> questoes = new ArrayList<>();

    private LocalDateTime dataCriacao;

    public Prova() {
        // Construtor vazio
    }

    public Prova(String titulo) {
        this.titulo = titulo;
        this.dataCriacao = LocalDateTime.now();
    }

    public void adicionarQuestao(Questao questao) {
        this.questoes.add(questao);
        questao.setProva(this);
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

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

}