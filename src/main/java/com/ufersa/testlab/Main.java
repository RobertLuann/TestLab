package com.ufersa.testlab;

import com.ufersa.testlab.entities.Usuario;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Usuario usuarioUm = new Usuario("Robert", "teste@ufersa.com", "123321", "ADMIN");
        usuarioUm.getUsuario();

        Usuario usuarioDois = new Usuario();
        usuarioDois.setId();
        usuarioDois.setNome("Zé");
        usuarioDois.setEmail("ze@ufersa.com");
        usuarioDois.setSenha("seuze");
        usuarioDois.setCargo("FUNCIONARIO");
        usuarioDois.getUsuario();
    }
}