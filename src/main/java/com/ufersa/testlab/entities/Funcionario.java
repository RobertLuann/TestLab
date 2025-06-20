package com.ufersa.testlab.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FUNCIONARIO")
public class Funcionario extends Usuario{
    public Funcionario() {}
    public Funcionario(String nome, String email, String senha) {
        super(nome, email, senha);
    }
}
