package com.ufersa.testlab.factory;

import com.ufersa.testlab.model.entities.Alternativa;
import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.model.entities.QuestaoDissertativa;
import com.ufersa.testlab.model.entities.QuestaoMultiplaEscolha;

import java.util.List;

// Cria uma factory para lidar com a criação de questões de cada tipo
public class QuestaoFactory {
    public static QuestaoDissertativa criarQuestaoDissertativa(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, String resposta, Disciplina disciplina) {
        return new QuestaoDissertativa(codigo, enunciado, codigoDisciplina, assunto, dificuldade, resposta, disciplina);
    }

    public static QuestaoMultiplaEscolha criarQuestaoMultiplaEscolha(String codigo, String enunciado, String codigoDisciplina, String assunto, Long dificuldade, List<Alternativa> alternativas, Long gabarito, Disciplina disciplina) {
        return new QuestaoMultiplaEscolha(codigo, enunciado, codigoDisciplina, assunto, dificuldade, alternativas, gabarito, disciplina);
    }
}
