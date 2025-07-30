package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.model.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProvaDAO {

    // Construtor vazio, pois não há mais variáveis de instância para inicializar
    public ProvaDAO() {}

    public void cadastrarProva(Prova prova) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(prova);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deletarProva(Prova prova) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Prova managedProva = em.merge(prova);
            em.remove(managedProva);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Prova buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Prova.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Prova buscarPorTitulo(String titulo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p WHERE p.titulo = :titulo", Prova.class);
            query.setParameter("titulo", titulo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prova> buscarPorDisciplina(String codigoDisciplina) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p WHERE p.disciplina.codigo = :codigo", Prova.class);
            query.setParameter("codigo", codigoDisciplina);
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prova> listarTodas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Etapa 1: Busca as Provas e suas Questões.
            // Removemos o JOIN FETCH para as alternativas daqui para evitar o erro.
            String jpql = "SELECT DISTINCT p FROM Prova p LEFT JOIN FETCH p.questoes";
            List<Prova> provas = em.createQuery(jpql, Prova.class).getResultList();

            // Etapa 2: Para cada prova encontrada, inicializamos as alternativas das questões de múltipla escolha.
            // Isso é feito enquanto a conexão com o banco ainda está aberta.
            for (Prova prova : provas) {
                for (Questao questao : prova.getQuestoes()) {
                    if (questao instanceof QuestaoMultiplaEscolha) {
                        // Acessar a lista de alternativas força o Hibernate a carregá-la.
                        // O .size() é uma forma simples de fazer isso.
                        ((QuestaoMultiplaEscolha) questao).getAlternativas().size();
                    }
                }
            }
            return provas;

        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Prova atualizarProva(Prova prova) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Prova updatedProva = em.merge(prova);
            transaction.commit();
            return updatedProva;
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // O método 'salvar' é idêntico ao 'cadastrarProva'
    public void salvar(Prova prova) {
        cadastrarProva(prova);
    }
}