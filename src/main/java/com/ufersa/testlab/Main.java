package com.ufersa.testlab;

import java.util.Arrays;

import com.ufersa.testlab.entities.Disciplina;
import com.ufersa.testlab.entities.Usuario;
import com.ufersa.testlab.entities.Questao;
import com.ufersa.testlab.entities.TipoQuestao;
import com.ufersa.testlab.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.entities.QuestaoDissertativa;
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

        // Testes das funções do banco com Questao
        //Disciplina qualquer para testar questões
        Disciplina portugues = new Disciplina("AAB231", "Português", new String[] {"Literatura", "Gramatica"});
        bd.cadastrarDisciplina(portugues);

        //cria, cadastra e lista
        Questao questao1 = new QuestaoDissertativa ("789XYZ","Qual a diferença de \"mais\" e \"mas\"?", portugues, "Gramatica", 1, "\"mais\" é intensidade, \"mas\" é oposição.");
        bd.cadastrarQuestao(questao1);
        Questao questao2 = new QuestaoMultiplaEscolha ("987ZYX", "Se x é igual a 60, quanto vale 3x?", matematica, "Algebra", 2, Arrays.asList("300", "180", "90", "nenhuma das anteriores."), 1);
        bd.cadastrarQuestao(questao2);
        for (Questao quest : bd.listarQuestoes()) bd.dadosQuestao(quest);

        //atualizações
        bd.atualizarQuestao("987ZYX", TipoQuestao.MULTIPLA_ESCOLHA, null, null, null, 3, Arrays.asList("300", "190", "180"), 2, null);
        bd.atualizarQuestao("789XYZ", TipoQuestao.MULTIPLA_ESCOLHA, "Qual a probabilidade de cara em uma moeda?", matematica, "Probabilidade", 5, Arrays.asList("50%", "25%", "75%"), 0, null);
        for (Questao quest : bd.listarQuestoes()) bd.dadosQuestao(quest);
        bd.atualizarQuestao("789XYZ", TipoQuestao.DISSERTATIVA, "O que é um narrador?", portugues, "Literatura", null, null, null, "Quem narra!");
        bd.dadosQuestao(bd.pegarQuestaoCodigo("789XYZ"));
        bd.deletarQuestao("789XYZ");
        bd.dadosQuestao(bd.pegarQuestaoCodigo("789XYZ"));

        //nova questão para testar buscas
        Questao questao7 = new QuestaoMultiplaEscolha ("567ASD", "Qual a probabilidade de qualquer dia ser domingo?", matematica, "Probabilidade", 4, Arrays.asList("1/4", "2/7", "7/1", "1/7."), 3);
        bd.cadastrarQuestao(questao7);

        //buscas
        bd.buscarPorDisciplina(matematica);
        for (Questao quest : bd.listarBusca()) bd.dadosQuestao(quest);
        bd.buscarPorAssunto("Algebra");
        for (Questao quest : bd.listarBusca()) bd.dadosQuestao(quest);
        bd.buscarPorDificuldade(4);
        for (Questao quest : bd.listarBusca()) bd.dadosQuestao(quest);

    }
}