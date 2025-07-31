package com.ufersa.testlab.model.services;

import com.ufersa.testlab.factory.QuestaoFactory;
import com.ufersa.testlab.model.dao.DisciplinaDAO;
import com.ufersa.testlab.model.dao.QuestaoDAO;
import com.ufersa.testlab.model.entities.*;

import java.util.List;

public class QuestaoService {
    private final QuestaoDAO questaoDAO = new QuestaoDAO();
    private final DisciplinaService disciplinaService = new DisciplinaService();

    public QuestaoService() {}

    public void cadastrarQuestaoDissertativa(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, String resposta) {
        if (questaoDAO.buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Código já utilizado.");
        }

        Disciplina disciplina = disciplinaService.buscarPorCodigo(codigoDisciplina);

        // Utiliza o factory para criar a questao
        QuestaoDissertativa q = QuestaoFactory.criarQuestaoDissertativa(codigo, enunciado, codigoDisciplina, assunto, dificuldade, resposta, disciplina);
        questaoDAO.cadastrarQuestao(q);
    }

    public void cadastrarQuestaoMultiplaEscolha(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, List<Alternativa> alternativas, Long gabarito) {
        if (questaoDAO.buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Código já utilizado.");
        }

        if (gabarito < 0 || gabarito >= alternativas.size()) {
            throw new IllegalArgumentException("Índice do gabarito inválido.");
        }

        Disciplina disciplina = disciplinaService.buscarPorCodigo(codigoDisciplina);

        // Utiliza o factory para criar a questão
        QuestaoMultiplaEscolha q = QuestaoFactory.criarQuestaoMultiplaEscolha(codigo, enunciado, codigoDisciplina, assunto, dificuldade, alternativas, gabarito, disciplina);
        questaoDAO.cadastrarQuestao(q);
    }

    public Questao buscarPorCodigo(String codigo) {
        Questao q = questaoDAO.buscarPorCodigo(codigo);
        if (q == null) {
            throw new IllegalArgumentException("Questão não encontrada.");
        }
        return q;
    }

    public List<Questao> buscarPorFiltros(String disciplina, String assunto, Long dificuldade) {
        return questaoDAO.buscarPorFiltros(disciplina, assunto, dificuldade);
    }

    public List<Questao> listarQuestoes() {
        return questaoDAO.listarTodas();
    }

    public void atualizarQuestaoDissertativa(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, String resposta) {
        Questao q = buscarPorCodigo(codigo);

        if (!(q instanceof QuestaoDissertativa dissertativa)) {
            throw new IllegalArgumentException("A questão não é do tipo dissertativa.");
        }

        dissertativa.setEnunciado(enunciado);
        dissertativa.setCodigoDisciplina(codigoDisciplina);
        dissertativa.setAssunto(assunto);
        dissertativa.setDificuldade(dificuldade);
        dissertativa.setResposta(resposta);

        questaoDAO.atualizarQuestao(dissertativa); // usa `merge` se necessário
    }

    public void atualizarQuestaoMultiplaEscolha(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, List<Alternativa> alternativas, Long gabarito) {
        Questao q = buscarPorCodigo(codigo);

        if (!(q instanceof QuestaoMultiplaEscolha escolha)) {
            throw new IllegalArgumentException("A questão não é do tipo multipla escolha.");
        }

        escolha.setEnunciado(enunciado);
        escolha.setCodigoDisciplina(codigoDisciplina);
        escolha.setAssunto(assunto);
        escolha.setDificuldade(dificuldade);
        escolha.limparAlternativas();
        escolha.setAlternativas(alternativas);
        escolha.setGabarito(gabarito);

        questaoDAO.atualizarQuestao(escolha);
    }

    public void deletarQuestao(String codigo) {
        Questao q = buscarPorCodigo(codigo);
        questaoDAO.deletarQuestao(q);
    }
}
