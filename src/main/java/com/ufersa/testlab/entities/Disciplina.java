package com.ufersa.testlab.entities;


import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String codigo;
    private String nome;
    private final List<String> assuntos = new ArrayList<>();

    // Construtores
    public Disciplina(String codigo) {
        setCodigo(codigo);
    }
    public Disciplina(String codigo, String nome, String[] assuntos) {
        setCodigo(codigo);
        setNome(nome);
        setAssuntos(assuntos);
    }

    // Setters
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setAssuntos(String[] assuntos) {
        this.assuntos.clear();
        if (assuntos != null) {
            for (String assunto : assuntos) {
                if (assunto != null && !assunto.isBlank()) {
                    this.assuntos.add(assunto);
                }
            }
        }
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
    public void getDisciplina() {
        System.out.println("Código: " + getCodigo());
        System.out.println("Nome: " + getNome());
        System.out.println("Assuntos: ");
        for (String assunto : this.assuntos) {
            System.out.println(assunto);
        }
    }
}
