package com.ufersa.testlab.entities;


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

    @ElementCollection
    @CollectionTable(
            name = "disciplina_assunto",
            joinColumns = @JoinColumn(name = "disciplina_assunto")
    )
    @Column(nullable = false)
    private final List<String> assuntos = new ArrayList<>();

    // Construtores
    public Disciplina() {}

    public Disciplina(String codigo, String nome) {
        setCodigo(codigo);
        setNome(nome);
    }

    // Setters
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setAssunto(String assunto) {
        this.assuntos.add(assunto);
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
