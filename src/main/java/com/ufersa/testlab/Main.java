package com.ufersa.testlab;

import com.ufersa.testlab.entities.Disciplina;
import com.ufersa.testlab.entities.Usuario;
import com.ufersa.testlab.repositories.BancodeTeste;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Banco de dados de Teste
        BancodeTeste bd = new BancodeTeste();

        // Testes das funções do banco com Usuario
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

        // Testes das funções do banco com Disciplina
        Disciplina matematica = new Disciplina("ABC123", "Matematica", new String[] {"Algebra", "Probabilidade", "Derivação"});
        bd.cadastrarDisciplina(matematica);
        bd.dadosDisciplina(bd.pegarDisciplinaCodigo("ABC123"));
        Disciplina poo = new Disciplina("CBA321");
        poo.setNome("POO");
        poo.setAssuntos(new String[] {"Classes", "Atributos e Métodos"});
        bd.cadastrarDisciplina(poo);
        for (Disciplina disc : bd.listarDisciplinas()) bd.dadosDisciplina(disc);
        bd.atualizarDisciplina("CBA321", null, new String[] {"JavaFX", "JDBC", "JPA"});
        bd.dadosDisciplina(bd.pegarDisciplinaCodigo("CBA321"));
        bd.deletarDisciplina("CBA321");
        bd.dadosDisciplina(bd.pegarDisciplinaCodigo("CBA321"));
    }
}