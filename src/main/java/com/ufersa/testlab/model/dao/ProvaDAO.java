package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Prova;
import jakarta.persistence.*;

import java.util.List;

public class ProvaDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestLab");

    private final EntityManager em = emf.createEntityManager();

    public ProvaDAO() {}

    public void cadastrarProva(Prova prova) {
        em.getTransaction().begin();
        em.persist(prova);
        em.getTransaction().commit();
    }

    public void deletarProva(Prova prova) {
        em.getTransaction().begin();
        em.remove(prova);
        em.getTransaction().commit();
    }

    public Prova buscarPorId(Long id) {
        return em.find(Prova.class, id);
    }

    public Prova buscarPorTitulo(String titulo) {
        try {
            return em.createQuery("SELECT p FROM Prova p WHERE p.titulo = :titulo", Prova.class)
                    .setParameter("titulo", titulo)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    public List<Prova> buscarPorDisciplina(String codigoDisciplina) {
        return em.createQuery(
                        "SELECT p FROM Prova p WHERE p.disciplina.codigo = :codigo", Prova.class)
                .setParameter("codigo", codigoDisciplina)
                .getResultList();
    }

    public List<Prova> listarTodas() {
        String jpql = "SELECT p FROM Prova p";
        TypedQuery<Prova> query = em.createQuery(jpql, Prova.class);
        return query.getResultList();
    }

    public void atualizarProva(Prova prova) {
        em.getTransaction().begin();
        em.merge(prova);
        em.getTransaction().commit();
    }
}