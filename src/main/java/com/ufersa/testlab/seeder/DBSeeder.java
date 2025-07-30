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
    private Disciplina d1;


    public void run() {
        System.out.println("Executando o Seeder...");
        seedUsuarios();
        seedDisciplinas(); // CORRETO: com "s" no final
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
            admin.setSenha("123");
            usuarioDAO.cadastrarUsuario(admin);

            Funcionario professor = new Funcionario();
            professor.setNome("Funcionario");
            professor.setEmail("funcionario@testlab.com");
            professor.setSenha("123");
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
            Disciplina disciplinaPOO = disciplinaDAO.buscarPorCodigo("DCA0101");
            Disciplina disciplinaED = disciplinaDAO.buscarPorCodigo("DCA0203");

            if (disciplinaPOO == null || disciplinaED == null) {
                System.out.println("Erro no Seeder: Disciplinas de base não encontradas. Abortando seed de questões.");
                return;
            }

            // Questão 1
            QuestaoDissertativa q1 = new QuestaoDissertativa(
                    "QD001", "Explique o conceito de Polimorfismo em POO.",
                    disciplinaPOO.getCodigo(),
                    "POO", 3L,
                    "Polimorfismo é a capacidade de um objeto poder ser referenciado de várias formas."
            );
            q1.setDisciplina(disciplinaPOO);
            questaoDAO.cadastrarQuestao(q1);

            // Questão 2
            List<Alternativa> alternativasQ2 = List.of(
                    new Alternativa("O(n)", false),
                    new Alternativa("O(log n)", false),
                    new Alternativa("O(n^2)", true),
                    new Alternativa("O(1)", false)
            );
            QuestaoMultiplaEscolha q2 = new QuestaoMultiplaEscolha(
                    "QME001", "Qual a complexidade de tempo do algoritmo Bubble Sort no pior caso?",
                    disciplinaED.getCodigo(),
                    "Algoritmos de Ordenação", 4L,
                    alternativasQ2, 2L
            );
            q2.setDisciplina(disciplinaED);
            questaoDAO.cadastrarQuestao(q2);

            // ===== INÍCIO DA CORREÇÃO PRINCIPAL =====
            // Primeiro, crie a lista de alternativas para a q3.
            List<Alternativa> alternativasQ3 = List.of(
                    new Alternativa("Herança", false),
                    new Alternativa("Encapsulamento", true),
                    new Alternativa("Abstração", false)
            );
            // Agora, crie a QuestaoMultiplaEscolha q3 usando a lista acima.
            QuestaoMultiplaEscolha q3 = new QuestaoMultiplaEscolha(
                    "QME002", "Qual pilar da POO visa proteger os dados de um objeto, expondo apenas operações seguras?",
                    disciplinaPOO.getCodigo(),
                    "Pilares da POO", 2L,
                    alternativasQ3, 1L


            );

            q3.setDisciplina(disciplinaPOO);
            questaoDAO.cadastrarQuestao(q3);
            // ===== FIM DA CORREÇÃO PRINCIPAL =====

            System.out.println("Questões criadas com sucesso.");
        } else {
            System.out.println("Tabela de questões já populada.");
        }
    }

    private void seedProvas() {
        System.out.println("Semeando provas...");
        if (provaDAO.listarTodas().isEmpty()) {
            // Busque a disciplina antes de usá-la
            Disciplina d1 = disciplinaDAO.buscarPorCodigo("DCA0101");
            if (d1 == null) {
                System.out.println("Não foi possível semear provas, disciplina POO não encontrada.");
                return;
            }

            Prova provaPOO = new Prova(); // Use o construtor padrão
            provaPOO.setTitulo("Prova 1 de POO");
            provaPOO.setDisciplina(d1); // Associe a disciplina
            provaPOO.setDataCriacao(LocalDateTime.now());

            Questao q1 = questaoDAO.buscarPorCodigo("QD001");
            Questao q3 = questaoDAO.buscarPorCodigo("QME002");

            if (q1 != null && q3 != null) {
                provaPOO.adicionarQuestao(q1); // Use o método auxiliar da entidade
                provaPOO.adicionarQuestao(q3);
                provaDAO.cadastrarProva(provaPOO);
                System.out.println("Prova de POO criada com sucesso.");
            }
        } else {
            System.out.println("Tabela de provas já populada.");
        }
    }
}
