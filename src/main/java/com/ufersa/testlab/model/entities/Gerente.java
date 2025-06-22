package com.ufersa.testlab.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GERENTE")
public class Gerente extends Usuario{
    public Gerente() {}
    public Gerente(String nome, String email, String senha) {
        super(nome, email, senha);
    }
}
