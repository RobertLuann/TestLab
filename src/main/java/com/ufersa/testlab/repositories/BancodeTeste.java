package com.ufersa.testlab.repositories;

import com.ufersa.testlab.entities.Disciplina;
import com.ufersa.testlab.entities.Usuario;
import com.ufersa.testlab.entities.Questao;
import com.ufersa.testlab.entities.QuestaoDissertativa;
import com.ufersa.testlab.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.entities.TipoQuestao;

import java.util.ArrayList;
import java.util.List;

public class BancodeTeste {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Disciplina> disciplinas = new ArrayList<>();
    private final List<Questao> questoes = new ArrayList<>();
    private List<Questao> busca = new ArrayList<>();
    

    // Tabela Usuario
    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario pegarUsuarioId(Long id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        System.out.println("Usuário não encontrado.");
        return null;
    }

    public void dadosUsuario(Usuario u) {
        if (!(u == null)) {
            u.getUsuario();
        }
    }

    public void atualizarUsuario(Long id, String nome, String email, String senha, String cargo) {
        Usuario u = pegarUsuarioId(id);
        if (u == null) {
            System.out.println("Usuário não encontrado para atualização.");
            return;
        }

        if (nome != null && !nome.isBlank()) u.setNome(nome);
        if (email != null && !email.isBlank()) u.setEmail(email);
        if (senha != null && !senha.isBlank()) u.setSenha(senha);
        if (cargo != null && !cargo.isBlank()) u.setCargo(cargo);
        System.out.println("Usuário atualizado com sucesso.");
    }

    public void deletarUsuario(Long id) {
        boolean removido = usuarios.removeIf(usuario -> usuario.getId().equals(id));
        if (removido) {
            System.out.println("Usuário apagado com sucesso.");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    // Simulação tabela Disciplina
    public void cadastrarDisciplina(Disciplina disc) {
        disciplinas.add(disc);
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    public Disciplina pegarDisciplinaCodigo(String codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equals(codigo)) {
                return disciplina;
            }
        }
        System.out.println("Disciplina não encontrada.");
        return null;
    }

    public void dadosDisciplina(Disciplina d) {
        if (!(d == null)) {
            d.getDisciplina();
        }
    }

    public void atualizarDisciplina(String codigo, String nome, String[] assuntos) {
        Disciplina disc = pegarDisciplinaCodigo(codigo);
        if (disc == null) {
            System.out.println("Disciplina não encontrada para atualização.");
            return;
        }

        if (codigo != null && !codigo.isBlank()) disc.setCodigo(codigo);
        if (nome != null && !nome.isBlank()) disc.setNome(nome);
        if (assuntos != null) disc.setAssuntos(assuntos);
        System.out.println("Disciplina atualizado com sucesso.");
    }

    public void deletarDisciplina(String codigo) {
        boolean removido = disciplinas.removeIf(disciplina -> disciplina.getCodigo().equals(codigo));
        if (removido) {
            System.out.println("Disciplina apagada com sucesso.");
        } else {
            System.out.println("Disciplina não encontrada.");
        }
    }

    //simulacao tabela de questoes
    public void criarQuestao(Questao quest) {
        questoes.add(quest);
    }

    public List<Questao> listarQuestoes() {
        return questoes;
    }

    public Questao pegarQuestaoCodigo(String codigo) {
        for (Questao questao : questoes) {
            if (questao.getCodigo().equals(codigo)) {
                return questao;
            }
        }
        System.out.println("Questao não encontrada.");
        return null;
    }

        public void dadosQuestao(Questao q) {
        if (!(q == null)) {
            q.getQuestao();
        }
    }

    public void atualizarQuestao(
        String codigo,
        TipoQuestao novoTipo,
        String novoEnunciado, 
        Disciplina novaDisciplina,
        String novoAssunto,
        Integer novaDificuldade,
        List<String> novasOpcoes, 
        Integer novoGabarito, 
        String novaResposta) {
    Questao quest = pegarQuestaoCodigo(codigo);
        if (quest == null) {
            System.out.println("Questão não encontrada para atualização.");
            return;
        }

        if (codigo != null && !codigo.isBlank()) quest.setCodigo(codigo);
        if (novoEnunciado != null && !novoEnunciado.isBlank()) quest.setEnunciado(novoEnunciado);
        if (novaDisciplina != null) quest.setDisciplina(novaDisciplina);
        if (novoAssunto != null && !novoAssunto.isBlank()) quest.setAssunto(novoAssunto);
        if (novaDificuldade != null) quest.setDificuldade(novaDificuldade);

        if (novoTipo != quest.getTipo()) {

            quest.setTipo(novoTipo);
            switch (novoTipo) {
            case MULTIPLA_ESCOLHA: {
                deletarQuestaoEmSilencio(codigo);
                Questao questao3 = new QuestaoMultiplaEscolha(codigo, quest.getEnunciado(), quest.getDisciplina(), quest.getAssunto(), quest.getDificuldade(), novasOpcoes, novoGabarito);
                criarQuestao(questao3);
                    break; 
            }

            case DISSERTATIVA: {
                deletarQuestaoEmSilencio(codigo); 
                Questao questao4 = new QuestaoDissertativa(codigo, quest.getEnunciado(), quest.getDisciplina(), quest.getAssunto(), quest.getDificuldade(), novaResposta);
                criarQuestao(questao4); 
                    break;
            }
            default:
                throw new IllegalArgumentException("Tipo de questão Inválido!");
            }
            
        } else {
            switch (novoTipo) {
            case MULTIPLA_ESCOLHA: {
                if (quest instanceof QuestaoMultiplaEscolha) {
                    QuestaoMultiplaEscolha questao5 = (QuestaoMultiplaEscolha) quest;
                    if (novasOpcoes != null) questao5.setOpcoes(novasOpcoes);
                    if (!(novoGabarito < 0 || novoGabarito >= novasOpcoes.size())) questao5.setGabarito(novoGabarito);
                }
                break;
            }

            case DISSERTATIVA: {
                if (quest instanceof QuestaoDissertativa) {
                    QuestaoDissertativa questao6 = (QuestaoDissertativa) quest;
                    if (novaResposta != null) questao6.setResposta(novaResposta);
                }
                break;
            }

            default:
                throw new IllegalArgumentException("Tipo de questão Inválido!");
            }
        }
    }   

    public void deletarQuestao(String codigo) {
    boolean removido = questoes.removeIf(questao -> questao.getCodigo().equals(codigo));
        if (removido) {
            System.out.println("Questão apagada com sucesso.");
        } else {
            System.out.println("Questão não encontrada.");
        }
    }

    public void deletarQuestaoEmSilencio(String codigo) {
    questoes.removeIf(questao -> questao.getCodigo().equals(codigo));
    }

    public List<Questao> listarBusca() {
        return busca;
    }
    
    public void buscarPorDisciplina(Disciplina disciplina) {
        busca.clear();
        System.out.println("Buscas para a disciplina " + disciplina.getNome() + ": ");
        for (Questao q : questoes) {
            if (q.getDisciplina().getNome().equalsIgnoreCase(disciplina.getNome())) {
                busca.add(q);
            }
        }
    }

    public void buscarPorAssunto(String assunto) {
        busca.clear();
        System.out.println("Buscas para o assunto " + assunto + ": ");;
        for (Questao q : questoes) {
            if (q.getAssunto().equalsIgnoreCase(assunto)) {
                busca.add(q);
            }
        }
    }
    public void buscarPorDificuldade(Integer dificuldade) {
        busca.clear();
        System.out.println("Buscas para a dificuldade " + dificuldade + ": ");
        for (Questao q : questoes) {
            if (q.getDificuldade() == dificuldade) {
                busca.add(q);
            }
        }
    }
}


