package com.ufersa.testlab.seeder;

import com.ufersa.testlab.model.dao.*;
import com.ufersa.testlab.model.entities.*;

import java.time.LocalDateTime;
import java.util.List;

public class DBSeeder {

    // Os DAOs continuam utilizando o padrão Singleton ou uma única instância
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
        System.out.println("Seeder finalizado com sucesso.");
    }

    private void seedUsuarios() {
        System.out.println("Semeando usuários...");
        if (usuarioDAO.listarUsuarios().isEmpty()) {
            Gerente admin = new Gerente();
            admin.setNome("Gerente Principal");
            admin.setEmail("gerente@testlab.com");
            admin.setSenha("123456");
            usuarioDAO.cadastrarUsuario(admin);

            Funcionario professor = new Funcionario();
            professor.setNome("Professor Padrão");
            professor.setEmail("funcionario@testlab.com");
            professor.setSenha("654321");
            usuarioDAO.cadastrarUsuario(professor);

            System.out.println("Usuários criados com sucesso.");
        } else {
            System.out.println("Tabela de usuários já populada.");
        }
    }

    private void seedDisciplinas() {
        System.out.println("Semeando disciplinas...");
        if (disciplinaDAO.listarDisciplinas().isEmpty()) {
            Disciplina d1 = new Disciplina("DCA0101", "Programação Orientada a Objetos", List.of("POO", "Pilares da POO", "Padrões de Design", "UML"));
            Disciplina d2 = new Disciplina("DCA0102", "Cálculo Vetorial", List.of("Vetores", "Geometria Analítica", "Derivadas Parciais"));
            Disciplina d3 = new Disciplina("DCA0203", "Estrutura de Dados", List.of("Algoritmos de Ordenação", "Estruturas em Árvore", "Grafos", "Complexidade"));
            // Tópicos de BD atualizados
            Disciplina d4 = new Disciplina("DCA0305", "Banco de Dados", List.of("Modelo Relacional", "SQL", "Normalização", "NoSQL", "Transações"));
            Disciplina d5 = new Disciplina("DCA0410", "Redes de Computadores", List.of("Modelo OSI", "Protocolos TCP/IP", "Segurança de Redes", "Redes sem Fio"));
            Disciplina d6 = new Disciplina("DCA0520", "Sistemas Operacionais", List.of("Gerenciamento de Processos", "Gerenciamento de Memória", "Sistemas de Arquivos", "Threads e Concorrência"));
            Disciplina d7 = new Disciplina("DCA0630", "Engenharia de Software I", List.of("Processos de Software", "Metodologias Ágeis", "Requisitos de Software", "Scrum"));

            disciplinaDAO.cadastrarDisciplina(d1);
            disciplinaDAO.cadastrarDisciplina(d2);
            disciplinaDAO.cadastrarDisciplina(d3);
            disciplinaDAO.cadastrarDisciplina(d4);
            disciplinaDAO.cadastrarDisciplina(d5);
            disciplinaDAO.cadastrarDisciplina(d6);
            disciplinaDAO.cadastrarDisciplina(d7);

            System.out.println("Disciplinas criadas com sucesso.");
        } else {
            System.out.println("Tabela de disciplinas já populada.");
        }
    }

    private void seedQuestoes() {
        System.out.println("Semeando questões...");
        if (questaoDAO.listarTodas().isEmpty()) {
            Disciplina poo = disciplinaDAO.buscarPorCodigo("DCA0101");
            Disciplina ed = disciplinaDAO.buscarPorCodigo("DCA0203");
            Disciplina bd = disciplinaDAO.buscarPorCodigo("DCA0305");
            Disciplina redes = disciplinaDAO.buscarPorCodigo("DCA0410");
            Disciplina so = disciplinaDAO.buscarPorCodigo("DCA0520");
            Disciplina es = disciplinaDAO.buscarPorCodigo("DCA0630");

            if (poo == null || ed == null || bd == null || redes == null || so == null || es == null) {
                System.out.println("Erro no Seeder: Disciplinas de base não encontradas. Abortando seed de questões.");
                return;
            }

            // Questões existentes (resumidas para clareza)
            QuestaoDissertativa q1 = new QuestaoDissertativa("QD001", "Explique o conceito de Polimorfismo...", poo.getCodigo(), "Pilares da POO", 4L, "...", poo);
            QuestaoMultiplaEscolha q2 = new QuestaoMultiplaEscolha("QME001", "Qual pilar da POO visa proteger os dados...", poo.getCodigo(), "Pilares da POO", 2L, List.of(new Alternativa("Herança", false), new Alternativa("Encapsulamento", true), new Alternativa("Abstração", false)), 1L, poo);
            QuestaoMultiplaEscolha q3 = new QuestaoMultiplaEscolha("QME002", "Qual padrão de projeto é usado para garantir...", poo.getCodigo(), "Padrões de Design", 3L, List.of(new Alternativa("Factory Method", false), new Alternativa("Observer", false), new Alternativa("Singleton", true)), 2L, poo);
            QuestaoMultiplaEscolha q4 = new QuestaoMultiplaEscolha("QME003", "Qual a complexidade de tempo do Bubble Sort...", ed.getCodigo(), "Algoritmos de Ordenação", 3L, List.of(new Alternativa("O(n)", false), new Alternativa("O(n log n)", false), new Alternativa("O(n^2)", true)), 2L, ed);
            QuestaoDissertativa q5 = new QuestaoDissertativa("QD002", "Diferencie uma Árvore Binária de Busca...", ed.getCodigo(), "Estruturas em Árvore", 5L, "...", ed);
            QuestaoMultiplaEscolha q6 = new QuestaoMultiplaEscolha("QME004", "Em uma estrutura de Fila (Queue), qual princípio...", ed.getCodigo(), "Complexidade", 2L, List.of(new Alternativa("LIFO", false), new Alternativa("FIFO", true), new Alternativa("HIFO", false)), 1L, ed);
            QuestaoDissertativa q7 = new QuestaoDissertativa("QD003", "Explique o que é a Terceira Forma Normal (3FN)...", bd.getCodigo(), "Normalização", 4L, "...", bd);
            QuestaoMultiplaEscolha q8 = new QuestaoMultiplaEscolha("QME005", "Qual comando SQL é usado para remover uma tabela...", bd.getCodigo(), "SQL", 2L, List.of(new Alternativa("DELETE FROM", false), new Alternativa("DROP TABLE", true), new Alternativa("TRUNCATE TABLE", false)), 1L, bd);
            QuestaoMultiplaEscolha q9 = new QuestaoMultiplaEscolha("QME006", "Qual das seguintes opções NÃO é um tipo NoSQL...", bd.getCodigo(), "NoSQL", 3L, List.of(new Alternativa("Documento", false), new Alternativa("Chave-Valor", false), new Alternativa("Relacional", true), new Alternativa("Grafos", false)), 2L, bd);
            QuestaoMultiplaEscolha q10 = new QuestaoMultiplaEscolha("QME007", "Em qual camada do Modelo OSI os Roteadores...", redes.getCodigo(), "Modelo OSI", 3L, List.of(new Alternativa("Física", false), new Alternativa("Enlace", false), new Alternativa("Rede", true)), 2L, redes);
            QuestaoDissertativa q11 = new QuestaoDissertativa("QD004", "Descreva a diferença entre TCP e UDP...", redes.getCodigo(), "Protocolos TCP/IP", 4L, "...", redes);
            QuestaoDissertativa q12 = new QuestaoDissertativa("QD005", "O que é um deadlock...", so.getCodigo(), "Gerenciamento de Processos", 5L, "...", so);
            QuestaoMultiplaEscolha q13 = new QuestaoMultiplaEscolha("QME008", "Qual técnica de gerenciamento de memória divide...", so.getCodigo(), "Gerenciamento de Memória", 3L, List.of(new Alternativa("Segmentação", false), new Alternativa("Paginação", true), new Alternativa("Particionamento", false)), 1L, so);
            QuestaoMultiplaEscolha q14 = new QuestaoMultiplaEscolha("QME009", "Qual dos seguintes papéis NÃO faz parte do Scrum...", es.getCodigo(), "Scrum", 2L, List.of(new Alternativa("Product Owner", false), new Alternativa("Scrum Master", false), new Alternativa("Gerente de Projeto", true)), 2L, es);
            QuestaoDissertativa q15 = new QuestaoDissertativa("QD006", "Compare e contraste o modelo cascata...", es.getCodigo(), "Processos de Software", 4L, "...", es);

            // ===== NOVAS QUESTÕES DE BANCO DE DADOS =====
            QuestaoMultiplaEscolha q16 = new QuestaoMultiplaEscolha("QME010", "Qual tipo de JOIN em SQL retorna todas as linhas da tabela da esquerda e as linhas correspondentes da tabela da direita?", bd.getCodigo(), "SQL", 3L, List.of(new Alternativa("INNER JOIN", false), new Alternativa("LEFT JOIN", true), new Alternativa("RIGHT JOIN", false), new Alternativa("FULL OUTER JOIN", false)), 1L, bd);
            QuestaoDissertativa q17 = new QuestaoDissertativa("QD007", "Descreva o que significa cada uma das propriedades do acrônimo ACID (Atomicidade, Consistência, Isolamento, Durabilidade) no contexto de transações de banco de dados.", bd.getCodigo(), "Transações", 5L, "ACID garante a confiabilidade...", bd);
            QuestaoMultiplaEscolha q18 = new QuestaoMultiplaEscolha("QME011", "A Segunda Forma Normal (2FN) visa eliminar qual tipo de anomalia de dependência?", bd.getCodigo(), "Normalização", 4L, List.of(new Alternativa("Dependência transitiva", false), new Alternativa("Dependência de chave estrangeira", false), new Alternativa("Dependência parcial", true)), 2L, bd);
            QuestaoDissertativa q19 = new QuestaoDissertativa("QD008", "Diferencie os subconjuntos de linguagem SQL: DDL (Data Definition Language) e DML (Data Manipulation Language), fornecendo dois exemplos de comandos para cada um.", bd.getCodigo(), "SQL", 3L, "DDL define a estrutura (CREATE, ALTER). DML manipula os dados (INSERT, UPDATE).", bd);

            // Cadastrando todas as questões
            questaoDAO.cadastrarQuestao(q1); questaoDAO.cadastrarQuestao(q2); questaoDAO.cadastrarQuestao(q3);
            questaoDAO.cadastrarQuestao(q4); questaoDAO.cadastrarQuestao(q5); questaoDAO.cadastrarQuestao(q6);
            questaoDAO.cadastrarQuestao(q7); questaoDAO.cadastrarQuestao(q8); questaoDAO.cadastrarQuestao(q9);
            questaoDAO.cadastrarQuestao(q10); questaoDAO.cadastrarQuestao(q11); questaoDAO.cadastrarQuestao(q12);
            questaoDAO.cadastrarQuestao(q13); questaoDAO.cadastrarQuestao(q14); questaoDAO.cadastrarQuestao(q15);

            // Cadastro das novas questões
            questaoDAO.cadastrarQuestao(q16); questaoDAO.cadastrarQuestao(q17); questaoDAO.cadastrarQuestao(q18);
            questaoDAO.cadastrarQuestao(q19);

            System.out.println("Questões criadas com sucesso.");
        } else {
            System.out.println("Tabela de questões já populada.");
        }
    }

    private void seedProvas() {
        System.out.println("Semeando provas...");
        if (provaDAO.listarTodas().isEmpty()) {
            Disciplina poo = disciplinaDAO.buscarPorCodigo("DCA0101");
            Disciplina ed = disciplinaDAO.buscarPorCodigo("DCA0203");
            Disciplina bd = disciplinaDAO.buscarPorCodigo("DCA0305");

            if (poo == null || ed == null || bd == null) {
                System.out.println("Não foi possível semear provas, disciplinas base não encontradas.");
                return;
            }

            // --- Prova 1: POO ---
            Prova provaPOO = new Prova();
            provaPOO.setTitulo("Prova 1 de Programação Orientada a Objetos");
            provaPOO.setDisciplina(poo);
            provaPOO.setDataCriacao(LocalDateTime.now());
            if (questaoDAO.buscarPorCodigo("QD001") != null && questaoDAO.buscarPorCodigo("QME001") != null) {
                provaPOO.adicionarQuestao(questaoDAO.buscarPorCodigo("QD001"));
                provaPOO.adicionarQuestao(questaoDAO.buscarPorCodigo("QME001"));
                provaDAO.cadastrarProva(provaPOO);
                System.out.println("Prova de POO criada com sucesso.");
            }

            // --- Prova 2: Estrutura de Dados ---
            Prova provaED = new Prova();
            provaED.setTitulo("Avaliação Final de Estrutura de Dados");
            provaED.setDisciplina(ed);
            provaED.setDataCriacao(LocalDateTime.now().minusDays(10));
            if (questaoDAO.buscarPorCodigo("QME003") != null && questaoDAO.buscarPorCodigo("QD002") != null) {
                provaED.adicionarQuestao(questaoDAO.buscarPorCodigo("QME003"));
                provaED.adicionarQuestao(questaoDAO.buscarPorCodigo("QD002"));
                provaDAO.cadastrarProva(provaED);
                System.out.println("Prova de Estrutura de Dados criada com sucesso.");
            }

            // --- Prova 3: Banco de Dados (Versão Completa e Atualizada) ---
            Prova provaBD = new Prova();
            provaBD.setTitulo("Avaliação Completa de Banco de Dados");
            provaBD.setDisciplina(bd);
            provaBD.setDataCriacao(LocalDateTime.now().minusMonths(1));

            Questao qBD1 = questaoDAO.buscarPorCodigo("QD003"); // 3FN
            Questao qBD2 = questaoDAO.buscarPorCodigo("QME005"); // DROP TABLE
            Questao qBD3 = questaoDAO.buscarPorCodigo("QD008"); // DDL vs DML
            Questao qBD4 = questaoDAO.buscarPorCodigo("QME010"); // LEFT JOIN

            if (qBD1 != null && qBD2 != null && qBD3 != null && qBD4 != null) {
                provaBD.adicionarQuestao(qBD1);
                provaBD.adicionarQuestao(qBD2);
                provaBD.adicionarQuestao(qBD3);
                provaBD.adicionarQuestao(qBD4);
                provaDAO.cadastrarProva(provaBD);
                System.out.println("Prova de Banco de Dados (Completa) criada com sucesso.");
            }

        } else {
            System.out.println("Tabela de provas já populada.");
        }
    }
}