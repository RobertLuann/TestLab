package com.ufersa.testlab.model.entities;

import jakarta.persistence.*;
import org.apache.commons.validator.routines.EmailValidator;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
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
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O email não pode ser nulo ou vazio.");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("O formato do email fornecido é inválido.");
        }
        this.email = email;
    }
    public void setSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia.");
        }
        this.senha = senha;
    }

    // Getters
    public Long getId() { return this.id; }
    public String getNome() { return this.nome; }
    public String getEmail() { return this.email; }
    public String getSenha() { return this.senha; }
}
