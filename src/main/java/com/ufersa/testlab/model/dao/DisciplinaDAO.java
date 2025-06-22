package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Disciplina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DisciplinaDAO {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestLab");
    private final EntityManager em = emf.createEntityManager();

    public void cadastrarDisciplina(Disciplina disciplina) {
        em.getTransaction().begin();
        em.persist(disciplina);
        em.getTransaction().commit();
    }

    public Disciplina buscarPorCodigo(String codigo) {
        return em.find(Disciplina.class, codigo);
    }

    public List<Disciplina> listarDisciplinas() {
        return em.createQuery("SELECT d FROM Disciplina d", Disciplina.class).getResultList();
    }

    public void atualizarDisciplina(Disciplina disciplina) {
        em.getTransaction().begin();
        em.merge(disciplina);
        em.getTransaction().commit();
    }

    public void deletarDisciplina(Disciplina disciplina) {
        em.getTransaction().begin();
        em.remove(disciplina);
        em.getTransaction().commit();
    }
}
