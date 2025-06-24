package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Prova;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProvaDAO {

    private EntityManager em;

    public ProvaDAO(EntityManager em) {
        this.em = em;
    }

    public Prova salvar(Prova prova) {
        return em.merge(prova);
    }

    public void excluir(Prova prova) {
        em.remove(prova);
    }

    public Prova buscarPorId(Long id) {
        return em.find(Prova.class, id);
    }

    public Prova buscarPorTitulo(String titulo) {
        try {
            TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p WHERE p.titulo = :titulo", Prova.class);
            query.setParameter("titulo", titulo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna nulo se não encontrar o que se espera
        }
    }

    public List<Prova> listarTodas() {
        String jpql = "SELECT p FROM Prova p";
        TypedQuery<Prova> query = em.createQuery(jpql, Prova.class);
        return query.getResultList();
    }
}