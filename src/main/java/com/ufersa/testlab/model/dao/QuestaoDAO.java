package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Questao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class QuestaoDAO {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestLab");
    private final EntityManager em = emf.createEntityManager();

    public QuestaoDAO() {}

    public void cadastrarQuestao(Questao questao) {
        em.getTransaction().begin();
        em.persist(questao);
        em.getTransaction().commit();
    }

    public Questao buscarPorCodigo(String codigo) {
        return em.find(Questao.class, codigo);
    }

    public List<Questao> listarTodas() {
        return em.createQuery("SELECT q FROM Questao q", Questao.class).getResultList();
    }

    public List<Questao> buscarPorDisciplina(String codigoDisciplina) {
        return em.createQuery("SELECT q FROM Questao q WHERE q.codigoDisciplina = :codigoDisciplina", Questao.class)
                .setParameter("codigoDisciplina", codigoDisciplina)
                .getResultList();
    }

    public List<Questao> buscarPorAssunto(String assunto) {
        return em.createQuery("SELECT q FROM Questao q WHERE q.assunto = :assunto", Questao.class)
                .setParameter("assunto", assunto)
                .getResultList();
    }

    public List<Questao> buscarPorDificuldade(Long dificuldade) {
        return em.createQuery("SELECT q FROM Questao q WHERE q.dificuldade = :dificuldade", Questao.class)
                .setParameter("dificuldade", dificuldade)
                .getResultList();
    }

    public void atualizarQuestao(Questao questao) {
        em.getTransaction().begin();
        em.merge(questao);
        em.getTransaction().commit();
    }

    public void deletarQuestao(Questao questao) {
        em.getTransaction().begin();
        em.remove(questao);
        em.getTransaction().commit();
    }
}
