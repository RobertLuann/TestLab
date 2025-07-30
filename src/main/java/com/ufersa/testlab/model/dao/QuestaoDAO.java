package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      
    public List<Questao> buscarPorFiltros(String disciplina, String assunto, Long dificuldade) {
        StringBuilder jpql = new StringBuilder("SELECT q FROM Questao q WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (disciplina != null && !disciplina.isBlank()) {
            jpql.append(" AND q.codigoDisciplina LIKE :disciplina");
            params.put("disciplina", "%" + disciplina + "%");
        }
        if (assunto != null && !assunto.isBlank()) {
            jpql.append(" AND q.assunto LIKE :assunto");
            params.put("assunto", "%" + assunto + "%");
        }
        if (dificuldade != null) {
            jpql.append(" AND q.dificuldade = :dificuldade");
            params.put("dificuldade", dificuldade);
        }

        TypedQuery<Questao> query = em.createQuery(jpql.toString(), Questao.class);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
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