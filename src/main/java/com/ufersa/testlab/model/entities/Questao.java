package com.ufersa.testlab.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_questao", discriminatorType = DiscriminatorType.STRING)
public abstract class Questao {
    @Id
    private String codigo;

    @Column(nullable = false)
    private String enunciado;

    @Column(name = "codigo_disciplina", insertable = false, updatable = false)
    private String codigoDisciplina;

    @Column(nullable = false)
    private String assunto;

    @Column(nullable = false)
    private Long dificuldade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_disciplina", nullable = false)
    private Disciplina disciplina;

    // Construtores
    public Questao() {}

    public Questao(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, Disciplina disciplina) {
        setCodigo(codigo);
        setEnunciado(enunciado);
        setCodigoDisciplina(codigoDisciplina);
        setAssunto(assunto);
        setDificuldade(dificuldade);
        setDisciplina(disciplina);
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
    public void setDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula ou vazia.");
        }
        this.disciplina = disciplina;
    }
    public Disciplina getDisciplinaObject() {
        return this.disciplina;
    }
    // Getters
    public String getCodigo() { return this.codigo; }
    public String getEnunciado() { return this.enunciado; }
    public String getDisciplina() { return this.codigoDisciplina; }
    public String getAssunto() { return this.assunto; }
    public Long getDificuldade() { return this.dificuldade; }

    public abstract void getQuestao();

    public abstract List<Alternativa> getAlternativas();
}

