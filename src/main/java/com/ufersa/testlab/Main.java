package com.ufersa.testlab;

import com.ufersa.testlab.entities.Usuario;
import com.ufersa.testlab.repositories.BancodeTeste;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Banco de dados de Teste
        BancodeTeste bd = new BancodeTeste();

        Usuario usuarioUm = new Usuario("Robert", "teste@ufersa.com", "123321", "ADMIN");
        bd.cadastrarUsuario(usuarioUm);

        Usuario usuarioDois = new Usuario();
        usuarioDois.setId();
        usuarioDois.setNome("Zé");
        usuarioDois.setEmail("ze@ufersa.com");
        usuarioDois.setSenha("seuze");
        usuarioDois.setCargo("FUNCIONARIO");
        bd.cadastrarUsuario(usuarioDois);

        for (Usuario usuario : bd.listarUsuarios()) bd.dadosUsuario(usuario);

        bd.dadosUsuario(bd.pegarUsuarioId(1L));
        bd.dadosUsuario(bd.pegarUsuarioId(2L));
        bd.deletarUsuario(2L);
        bd.dadosUsuario(bd.pegarUsuarioId(2L));
        bd.atualizarUsuario(1L, "Bruce", null, null, null);
    }
}