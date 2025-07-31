package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;


public class QuestaoDAO {
    private final JPAUtil jpaUtil = JPAUtil.getInstancia();

    public void cadastrarQuestao(Questao questao) {
        jpaUtil.executeInTransaction(em -> em.persist(questao));
    }

    public Questao buscarPorCodigo(String codigo) {
        return jpaUtil.executeQuery(em -> em.find(Questao.class, codigo));
    }

    public List<Questao> listarTodas() {
        return jpaUtil.executeQuery(em ->
                em.createQuery("SELECT q FROM Questao q", Questao.class).getResultList()
        );
    }

    // METODO RESTAURADO E CORRIGIDO
    public List<Questao> buscarPorDisciplina(String codigoDisciplina) {
        return jpaUtil.executeQuery(em -> {
            String jpql = "SELECT q FROM Questao q WHERE q.disciplina.codigo = :codigoDisciplina";
            return em.createQuery(jpql, Questao.class)
                    .setParameter("codigoDisciplina", codigoDisciplina)
                    .getResultList();
        });
    }

    // METODO RESTAURADO E CORRIGIDO
    public List<Questao> buscarPorAssunto(String assunto) {
        return jpaUtil.executeQuery(em ->
                em.createQuery("SELECT q FROM Questao q WHERE q.assunto LIKE :assunto", Questao.class)
                        .setParameter("assunto", "%" + assunto + "%")
                        .getResultList()
        );
    }

    // MÉTODO RESTAURADO E CORRIGIDO
    public List<Questao> buscarPorDificuldade(Long dificuldade) {
        return jpaUtil.executeQuery(em ->
                em.createQuery("SELECT q FROM Questao q WHERE q.dificuldade = :dificuldade", Questao.class)
                        .setParameter("dificuldade", dificuldade)
                        .getResultList()
        );
    }

    public void atualizarQuestao(Questao questao) {
        jpaUtil.executeInTransaction(em -> em.merge(questao));
    }

    public void deletarQuestao(Questao questao) {
        jpaUtil.executeInTransaction(em -> {
            Questao managedQuestao = em.merge(questao); // Garante que a entidade está gerenciada
            em.remove(managedQuestao);
        });
    }

    public List<Questao> buscarQuestoesAleatorias(Disciplina disciplina, int dificuldade, int quantidade) {
        return jpaUtil.executeQuery(em -> {
            String jpql = "SELECT q FROM Questao q WHERE q.disciplina = :disciplina AND q.dificuldade = :dificuldade";
            TypedQuery<Questao> query = em.createQuery(jpql, Questao.class);
            query.setParameter("disciplina", disciplina);
            query.setParameter("dificuldade", (long) dificuldade);

            List<Questao> questoesEncontradas = query.getResultList();
            Collections.shuffle(questoesEncontradas);

            // Retorna a sublista se a quantidade encontrada for maior que a solicitada
            return questoesEncontradas.size() > quantidade
                    ? questoesEncontradas.subList(0, quantidade)
                    : questoesEncontradas;
        });
    }
}