package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Questao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
