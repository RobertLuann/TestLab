package com.ufersa.testlab.entities;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    public static Long qtdUsuario = 0L;

    // Construtores
    public Usuario() {
        qtdUsuario++;
    }
    public Usuario(String nome, String email, String senha, String cargo) {
        qtdUsuario++;
        setId();
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setCargo(cargo);
    }

    // Setters
    public void setId() {
        this.id = qtdUsuario;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    // Getters
    public Long getId() { return this.id; }
    public String getNome() { return this.nome; }
    public String getEmail() { return this.email; }
    public String getCargo() { return this.cargo; }

    // Métodos
    public void getUsuario() {
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Email: " + getEmail());
        System.out.println("Cargo: " + getCargo());
    }
}
