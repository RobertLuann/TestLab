package com.ufersa.testlab;

import com.ufersa.testlab.entities.Disciplina;
import com.ufersa.testlab.entities.Usuario;
import com.ufersa.testlab.entities.Gerente;
import com.ufersa.testlab.entities.Funcionario;
import com.ufersa.testlab.entities.Questao;
import com.ufersa.testlab.entities.TipoQuestao;
import com.ufersa.testlab.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.entities.QuestaoDissertativa;

import com.ufersa.testlab.services.DisciplinaService;
import com.ufersa.testlab.services.UsuarioService;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Usuarios
        // UsuarioService usuarioService = new UsuarioService();

        // usuarioService.cadastrarUsuario("Gerente 2", "gerente2@gerencia.com", "123456", true);
        // System.out.println(usuarioService.buscarPorId(2L) instanceof Gerente);
        // for (Usuario u : usuarioService.listarUsuarios()) {
        //    System.out.println(u.getNome());
        // }
        // usuarioService.atualizarUsuario(1L, "Gerente 3", "gerente2@gerencia.com", "funcinario123");
        // usuarioService.deletarUsuario(1L);

        // Disciplinas
        DisciplinaService disciplinaService = new DisciplinaService();

        // disciplinaService.cadastrarDisciplina("DEF123", "Banco de Dados", List.of("SQL", "PostgreSQL"));
        // System.out.println(disciplinaService.buscarPorCodigo("AC123").getNome());
        // for (Disciplina d : disciplinaService.listarDisciplinas()) {
        //    System.out.println(d.getNome());
        // }
        // disciplinaService.atualizarDisciplina("ABC123", "Discreta", List.of("Cifra de Cesar", "Conjuntos Numericos"));
        disciplinaService.deletarDisciplina("AC123");
    }
}
