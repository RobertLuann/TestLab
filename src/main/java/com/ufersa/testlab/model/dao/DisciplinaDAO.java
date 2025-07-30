package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.util.JPAUtil; // Importa a classe utilitária
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class DisciplinaDAO {

    // As variáveis de instância para emf e em foram removidas.

    public void cadastrarDisciplina(Disciplina disciplina) {
        // Padrão: Obter EntityManager, usar e fechar.
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Disciplina buscarPorCodigo(String codigo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Disciplina.class, codigo);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Disciplina> listarDisciplinas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Disciplina d ORDER BY d.nome", Disciplina.class).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void atualizarDisciplina(Disciplina disciplina) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deletarDisciplina(Disciplina disciplina) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Garante que a entidade está no estado "managed" antes de remover
            if (!em.contains(disciplina)) {
                disciplina = em.merge(disciplina);
            }
            em.remove(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Disciplina> buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT d FROM Disciplina d WHERE d.nome LIKE :nome";
            TypedQuery<Disciplina> query = em.createQuery(jpql, Disciplina.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}