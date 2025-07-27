package com.ufersa.testlab.model.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_questao", discriminatorType = DiscriminatorType.STRING)
public abstract class Questao {
    @Id
    private String codigo;

    @Column(nullable = false)
    private String enunciado;

    @Column(nullable = false)
    private String codigoDisciplina;

    @Column(nullable = false)
    private String assunto;

    @Column(nullable = false)
    private Long dificuldade;

    // Construtores
    public Questao() {}

    public Questao(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade) {
        setCodigo(codigo);
        setEnunciado(enunciado);
        setDisciplina(codigoDisciplina);
        setAssunto(assunto);
        setDificuldade(dificuldade);
    }

    // Setters
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código não pode ser nulo ou vazio.");
        }
        this.codigo = codigo;
    }
    public void setEnunciado(String enunciado) {
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("O enunciado não pode ser nulo ou vazio.");
        }
        this.enunciado = enunciado;
    }
    public void setCodigoDisciplina(String codigoDisciplina) {
        if (codigoDisciplina == null || codigoDisciplina.isBlank()) {
            throw new IllegalArgumentException("O código da disciplina não pode ser nulo ou vazio.");
        }
        this.codigoDisciplina = codigoDisciplina;
    }
    public void setDisciplina(String codigoDisciplina) {
        if (codigoDisciplina == null || codigoDisciplina.isBlank()) {
            throw new IllegalArgumentException("O código da disciplina não pode ser nulo ou vazio.");
        }
        this.codigoDisciplina = codigoDisciplina;
    }

    public void setAssunto(String assunto) {
        if (assunto == null || assunto.isBlank()) {
            throw new IllegalArgumentException("O assunto não pode ser nulo ou vazio.");
        }
        this.assunto = assunto;
    }

    public void setDificuldade(Long dificuldade) {
        if (dificuldade == null) {
            throw new IllegalArgumentException("A dificuldade não pode ser nulo ou vazio.");
        }
        this.dificuldade = dificuldade;
    }

    // Getters
    public String getCodigo() { return this.codigo; }
    public String getEnunciado() { return this.enunciado; }
    public String getDisciplina() { return this.codigoDisciplina; }
    public String getAssunto() { return this.assunto; }
    public Long getDificuldade() { return this.dificuldade; }

    public abstract void getQuestao();
}