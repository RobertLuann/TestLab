package com.ufersa.testlab.model.entities;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disciplina")
public class Disciplina {
    @Id
    private String codigo;

    @Column(nullable = false)
    private String nome;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "disciplina_assunto",
            joinColumns = @JoinColumn(name = "disciplina_assunto")
    )
    @Column(nullable = false)
    private List<String> assuntos = new ArrayList<>();

    // Construtores
    public Disciplina() {}

    public Disciplina(String codigo, String nome, List<String> assuntos) {
        setCodigo(codigo);
        setNome(nome);
        setAssuntos(assuntos);
    }

    // Setters
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código da disciplina não pode ser nulo ou vazio.");
        }
        this.codigo = codigo;
    }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da disciplina não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }
    public void setAssuntos(List<String> assuntos) {
        if (assuntos == null) {
            throw new IllegalArgumentException("Os assuntos não podem ser nulos ou vazios.");
        }
        this.assuntos = assuntos;
    }

    // Getters
    public String getCodigo() {
        return this.codigo;
    }
    public String getNome() {
        return this.nome;
    }
    public List<String> getAssuntos() {
        return this.assuntos;
    }

    // Metodos
    public void deleteAssuntos() {
        assuntos.clear();
    }
}
