package com.ufersa.testlab;

import com.ufersa.testlab.model.entities.Alternativa;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.model.services.DisciplinaService;
import com.ufersa.testlab.model.services.QuestaoService;
import com.ufersa.testlab.model.services.UsuarioService;
import java.util.List;
import com.ufersa.testlab.model.entities.*;
import com.ufersa.testlab.model.service.ProvaService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Usuarios
        // UsuarioService usuarioService = new UsuarioService();

        // usuarioService.cadastrarUsuario("Gerente 2", "gerente2@gerencia.com", "123456", true);
        // System.out.println(usuarioService.buscarPorId(1L).getNome());
        // for (Usuario u : usuarioService.listarUsuarios()) {
        //    System.out.println(u.getNome());
        // }
        // usuarioService.atualizarUsuario(1L, "Gerente 3", "gerente2@gerencia.com", "funcinario123");
        // usuarioService.deletarUsuario(1L);

        // Disciplinas
        // DisciplinaService disciplinaService = new DisciplinaService();


        // disciplinaService.cadastrarDisciplina("DEF123", "Banco de Dados", List.of("SQL", "PostgreSQL"));
        // System.out.println(disciplinaService.buscarPorCodigo("ABC123").getNome());
        // for (Disciplina d : disciplinaService.listarDisciplinas()) {
        //    System.out.println(d.getNome());
        // }
        // disciplinaService.atualizarDisciplina("ABC123", "Discreta", List.of("Cifra de Cesar", "Conjuntos Numericos"));
        //disciplinaService.deletarDisciplina("AC123");
       // Questoes
        QuestaoService questaoService =  new QuestaoService();

        // questaoService.cadastrarQuestaoDissertativa("MAN123", "Questao 1 ae", "ABC123", "Cifra de Cesar", 3L, "Ta certo");
        List<Alternativa> alternativas = List.of(
                new Alternativa("A", "Explicação da A"),
                new Alternativa("B", "Explicação da B"),
                new Alternativa("C", "Explicação da C")
        );
        // questaoService.cadastrarQuestaoMultiplaEscolha("EII321", "Questao 2 amigao", "ABC123", "PIF", 5L, alternativas, 1L);
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


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("testlab-pu");
        try {
        ProvaService provaService = new ProvaService(factory);

        Prova novaProva = new Prova("Prova de Conhecimentos Gerais");
        // adicionar questões
            provaService.criarProva(novaProva);

        } catch (Exception e) {
        e.printStackTrace();
        } finally {
                if (factory != null) factory.close();
        }
    }
}