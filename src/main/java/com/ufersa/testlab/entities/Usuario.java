package com.ufersa.testlab.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    public Usuario() {}

    public Usuario(String nome, String email, String senha) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
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

    // Getters
    public Long getId() { return this.id; }
    public String getNome() { return this.nome; }
    public String getEmail() { return this.email; }
    public String getSenha() { return this.senha; }
}
