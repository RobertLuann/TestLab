package com.ufersa.testlab.entities;

public abstract class Questao {
    private String codigo;
    private TipoQuestao tipo;
    private String enunciado;
    private Disciplina disciplina;
    private String assunto;
    private Integer dificuldade;

    // Construtores
    public Questao(String codigo) {
        setCodigo(codigo);
    }

    public Questao(String codigo, TipoQuestao tipo, String enunciado, Disciplina disciplina, String assunto, Integer dificuldade) {
        setCodigo(codigo);
        setTipo(tipo);
        setEnunciado(enunciado);
        setDisciplina(disciplina);
        setAssunto(assunto);
        setDificuldade(dificuldade);
    }

    // Setters
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setTipo(TipoQuestao tipo) { this.tipo = tipo; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public void setAssunto(String assunto) {
        if (!disciplina.getAssuntos().contains(assunto)) {
            throw new IllegalArgumentException("Assunto inválido!");
        }
        this.assunto = assunto;
    }
    public void setDificuldade(Integer dificuldade) {
        if (dificuldade < 1 || dificuldade > 5) {
            System.out.println("dificuldade invalida: " + getDificuldade());          
            throw new IllegalArgumentException("Dificuldade inválida!");
        }
        this.dificuldade = dificuldade;
    }

    // Getters
    public String getCodigo() { return this.codigo; }
    public TipoQuestao getTipo() { return tipo; }
    public String getEnunciado() { return this.enunciado; }
    public Disciplina getDisciplina() { return this.disciplina; }
    public String getAssunto() { return this.assunto; }
    public Integer getDificuldade() { return this.dificuldade; }

    public abstract void getQuestao();
}