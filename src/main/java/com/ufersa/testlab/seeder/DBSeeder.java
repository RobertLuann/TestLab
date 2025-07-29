package com.ufersa.testlab.seeder;

import com.ufersa.testlab.model.dao.*;
import com.ufersa.testlab.model.entities.*;

import java.time.LocalDateTime;
import java.util.List;

public class DBSeeder {

    // O seeder tambem chama a mesma instancia
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final QuestaoDAO questaoDAO = new QuestaoDAO();
    private final ProvaDAO provaDAO = new ProvaDAO();


    public void run() {
        System.out.println("Executando o Seeder...");
        seedUsuarios();
        seedDisciplinas();
        seedQuestoes();
        seedProvas();
        System.out.println("Seeder finalizado.");
    }

    private void seedUsuarios() {
        System.out.println("Semeando usuários...");
        if (usuarioDAO.listarUsuarios().isEmpty()) {
            Gerente admin = new Gerente();
            admin.setNome("Gerente");
            admin.setEmail("gerente@testlab.com");
            admin.setSenha("gerente123");
            usuarioDAO.cadastrarUsuario(admin);

            Funcionario professor = new Funcionario();
            professor.setNome("Funcionario");
            professor.setEmail("funcionario@testlab.com");
            professor.setSenha("funcionario123");
            usuarioDAO.cadastrarUsuario(professor);

            System.out.println("Usuários criados com sucesso.");
        } else {
            System.out.println("Tabela de usuários já populada.");
        }
    }

    private void seedDisciplinas() {
        System.out.println("Semeando disciplinas...");
        if (disciplinaDAO.listarDisciplinas().isEmpty()) {
            Disciplina d1 = new Disciplina("DCA0101", "Programação Orientada a Objetos");
            Disciplina d2 = new Disciplina("MAT0102", "Cálculo Vetorial");
            Disciplina d3 = new Disciplina("DCA0203", "Estrutura de Dados");

            disciplinaDAO.cadastrarDisciplina(d1);
            disciplinaDAO.cadastrarDisciplina(d2);
            disciplinaDAO.cadastrarDisciplina(d3);

            System.out.println("Disciplinas criadas com sucesso.");
        } else {
            System.out.println("Tabela de disciplinas já populada.");
        }
    }

    private void seedQuestoes() {
        System.out.println("Semeando questões...");
        if (questaoDAO.listarTodas().isEmpty()) {
            QuestaoDissertativa q1 = new QuestaoDissertativa(
                    "QD001", "Explique o conceito de Polimorfismo em POO.",
                    "DCA0101", "POO", 3L,
                    "Polimorfismo é a capacidade de um objeto poder ser referenciado de várias formas."
            );
            questaoDAO.cadastrarQuestao(q1);

            List<Alternativa> alternativasQ2 = List.of(
                    new Alternativa("O(n)", false),
                    new Alternativa("O(log n)", false),
                    new Alternativa("O(n^2)", true),
                    new Alternativa("O(1)", false)
            );
            QuestaoMultiplaEscolha q2 = new QuestaoMultiplaEscolha(
                    "QME001", "Qual a complexidade de tempo do algoritmo Bubble Sort no pior caso?",
                    "DCA0203", "Algoritmos de Ordenação", 4L,
                    alternativasQ2, 2L
            );
            questaoDAO.cadastrarQuestao(q2);

            List<Alternativa> alternativasQ3 = List.of(
                    new Alternativa("Herança", false),
                    new Alternativa("Encapsulamento", true),
                    new Alternativa("Abstração", false)
            );
            QuestaoMultiplaEscolha q3 = new QuestaoMultiplaEscolha(
                    "QME002", "Qual pilar da POO visa proteger os dados de um objeto, expondo apenas operações seguras?",
                    "DCA0101", "Pilares da POO", 2L,
                    alternativasQ3, 1L
            );
            questaoDAO.cadastrarQuestao(q3);

            System.out.println("Questões criadas com sucesso.");
        } else {
            System.out.println("Tabela de questões já populada.");
        }
    }

    private void seedProvas() {
        System.out.println("Semeando provas...");
        if (provaDAO.listarTodas().isEmpty()) {
            Prova provaPOO = new Prova();
            provaPOO.setTitulo("Prova 1 de POO");
            provaPOO.setDataCriacao(LocalDateTime.now());

            // Pega questões do banco para adicionar à prova
            Questao q1 = questaoDAO.buscarPorCodigo("QD001");
            Questao q3 = questaoDAO.buscarPorCodigo("QME002");

            if (q1 != null && q3 != null) {
                provaPOO.getQuestoes().add(q1);
                provaPOO.getQuestoes().add(q3);
                provaDAO.cadastrarProva(provaPOO);
                System.out.println("Prova de POO criada com sucesso.");
            }
        } else {
            System.out.println("Tabela de provas já populada.");
        }
    }
}
