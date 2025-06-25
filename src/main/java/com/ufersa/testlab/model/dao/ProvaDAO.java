package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Prova;
import jakarta.persistence.*;
import java.util.List;

public class ProvaDAO {

    private final EntityManagerFactory emf;

    public ProvaDAO() {
        this.emf = Persistence.createEntityManagerFactory("TestLab");
    }

    public void cadastrarProva(Prova prova) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(prova);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deletarProva(Prova prova) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Prova managedProva = em.merge(prova);
            em.remove(managedProva);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Prova buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Prova.class, id);
        } finally {
            em.close();
        }
    }

    public Prova buscarPorTitulo(String titulo) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p WHERE p.titulo = :titulo", Prova.class);
            query.setParameter("titulo", titulo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Prova> buscarPorDisciplina(String codigoDisciplina) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p WHERE p.disciplina.codigo = :codigo", Prova.class);
            query.setParameter("codigo", codigoDisciplina);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Prova> listarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p", Prova.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Prova atualizarProva(Prova prova) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Prova updatedProva = em.merge(prova);
            transaction.commit();
            return updatedProva;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void close() {
        if (this.emf != null && this.emf.isOpen()) {
            this.emf.close();
        }
    }
}