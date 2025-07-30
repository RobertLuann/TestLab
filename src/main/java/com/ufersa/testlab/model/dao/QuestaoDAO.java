package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class QuestaoDAO {

    public void cadastrarQuestao(Questao questao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(questao);
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Questao buscarPorCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Questao.class, codigo);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Questao> listarTodas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT q FROM Questao q", Questao.class).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // MÉTODO RESTAURADO E CORRIGIDO
    public List<Questao> buscarPorDisciplina(String codigoDisciplina) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // A query foi ajustada para usar a relação `disciplina.codigo`
            String jpql = "SELECT q FROM Questao q WHERE q.disciplina.codigo = :codigoDisciplina";
            return em.createQuery(jpql, Questao.class)
                    .setParameter("codigoDisciplina", codigoDisciplina)
                    .getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // MÉTODO RESTAURADO E CORRIGIDO
    public List<Questao> buscarPorAssunto(String assunto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT q FROM Questao q WHERE q.assunto LIKE :assunto", Questao.class)
                    .setParameter("assunto", "%" + assunto + "%")
                    .getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // MÉTODO RESTAURADO E CORRIGIDO
    public List<Questao> buscarPorDificuldade(Long dificuldade) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT q FROM Questao q WHERE q.dificuldade = :dificuldade", Questao.class)
                    .setParameter("dificuldade", dificuldade)
                    .getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void atualizarQuestao(Questao questao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(questao);
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deletarQuestao(Questao questao) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(questao)) {
                questao = em.merge(questao);
            }
            em.remove(questao);
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Questao> buscarQuestoesAleatorias(Disciplina disciplina, int dificuldade, int quantidade) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT q FROM Questao q WHERE q.disciplina = :disciplina AND q.dificuldade = :dificuldade";
            TypedQuery<Questao> query = em.createQuery(jpql, Questao.class);
            query.setParameter("disciplina", disciplina);
            query.setParameter("dificuldade", (long) dificuldade);

            List<Questao> questoesEncontradas = query.getResultList();
            Collections.shuffle(questoesEncontradas);

            if (questoesEncontradas.size() > quantidade) {
                return questoesEncontradas.subList(0, quantidade);
            } else {
                return questoesEncontradas;
            }
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}