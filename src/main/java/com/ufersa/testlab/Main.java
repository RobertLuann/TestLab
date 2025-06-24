package com.ufersa.testlab;

import com.ufersa.testlab.model.entities.Alternativa;
import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.model.services.DisciplinaService;
import com.ufersa.testlab.model.services.QuestaoService;
import com.ufersa.testlab.model.services.UsuarioService;
import java.util.List;
import com.ufersa.testlab.model.services.ProvaService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Usuarios
        // UsuarioService usuarioService = new UsuarioService();

        // usuarioService.cadastrarUsuario("Gerente", "gerente@gerencia.com", "123456", true);
        // System.out.println(usuarioService.buscarPorId(1L).getNome());
        // for (Usuario u : usuarioService.listarUsuarios()) {
        //    System.out.println(u.getNome());
        // }
        // usuarioService.atualizarUsuario(1L, "Gerente Maior", "gerente2@gerencia.com", "funcinario123");
        // usuarioService.deletarUsuario(1L);

        // Disciplinas
        // DisciplinaService disciplinaService = new DisciplinaService();


        // disciplinaService.cadastrarDisciplina("DEF123", "Banco de Dados", List.of("SQL", "PostgreSQL"));
        // System.out.println(disciplinaService.buscarPorCodigo("ABC123").getNome());
        // for (Disciplina d : disciplinaService.listarDisciplinas()) {
        //    System.out.println(d.getNome());
        // }
        // disciplinaService.atualizarDisciplina("ABC123", "Discreta", List.of("Cifra de Cesar", "Conjuntos Numericos"));
        // disciplinaService.deletarDisciplina("AC123");

        // Questoes
        // QuestaoService questaoService =  new QuestaoService();

        // questaoService.cadastrarQuestaoDissertativa("MAN123", "Questao 1 ae", "DEF123", "Cifra de Cesar", 3L, "Ta certo");
        List<Alternativa> alternativas = List.of(
                new Alternativa("A", "Explicação da A"),
                new Alternativa("B", "Explicação da B"),
                new Alternativa("C", "Explicação da C")
        );

        // questaoService.cadastrarQuestaoMultiplaEscolha("DEF123", "Questao 2 amigao", "DEF123", "PIF", 5L, alternativas, 1L);
        // System.out.println(questaoService.buscarPorCodigo("EII321").getEnunciado());
        // for (Questao q : questaoService.listarQuestao()) {
        //    System.out.println(q.getEnunciado());
        // }
        // questaoService.atualizarQuestaoDissertativa("MAN123", "Questao 3 é", "ABC123", "PIF", 4L, "É sim");
        // questaoService.atualizarQuestaoMultiplaEscolha("EII321", "Questao 4 bro", "ABC123", "Cifra de Cesar", 5L, List.of(new Alternativa("A", "Amigao"), new Alternativa("B", "Endoidou")), 1L);
        // for (Questao q : questaoService.buscarPorDificuldade(2L)) {
        //      System.out.println(q.getEnunciado());
        // }
        // questaoService.deletarQuestao("EII321");

        // Prova
        ProvaService provaService = new ProvaService();

        // Prova prova = new Prova("Prova 3", questaoService.listarQuestoes(), disciplinaService.buscarPorCodigo("DEF123"));
        // provaService.cadastrarProva(prova);
        // System.out.println(provaService.buscarProvaPorId(2L).getTitulo());
        // System.out.println(provaService.buscarPorTitulo("Prova 3").getDisciplina().getNome());
        // for (Prova prova : provaService.listarTodasProvas()) {
        //    System.out.println(prova.getTitulo());
        // }
        // provaService.buscarPorDisciplina(disciplinaService.buscarPorCodigo("DEF123"));
        // Prova prova = provaService.buscarProvaPorId(2L);
        // prova.setTitulo("Prova 5");
        // provaService.atualizarProva(prova);
    }
}