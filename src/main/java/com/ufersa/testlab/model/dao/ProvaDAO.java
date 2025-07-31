package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Prova;
import com.ufersa.testlab.model.entities.Questao;
import com.ufersa.testlab.model.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.util.JPAUtil;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProvaDAO {

    private final JPAUtil jpaUtil = JPAUtil.getInstancia();

    public void cadastrarProva(Prova prova) {

        jpaUtil.executeInTransaction(em -> em.persist(prova));
    }

    public void deletarProva(Prova prova) {
        jpaUtil.executeInTransaction(em -> {
            Prova managedProva = em.merge(prova);
            em.remove(managedProva);
        });
    }

    public Prova buscarPorId(Long id) {
        return jpaUtil.executeQuery(em -> em.find(Prova.class, id));
    }

    public Prova buscarPorTitulo(String titulo) {
        return jpaUtil.executeQuery(em -> {
            try {
                TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p WHERE p.titulo = :titulo", Prova.class);
                query.setParameter("titulo", titulo);
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        });
    }

    public List<Prova> buscarPorDisciplina(String codigoDisciplina) {
        return jpaUtil.executeQuery(em -> {
            TypedQuery<Prova> query = em.createQuery("SELECT p FROM Prova p WHERE p.disciplina.codigo = :codigo", Prova.class);
            query.setParameter("codigo", codigoDisciplina);
            return query.getResultList();
        });
    }

    public List<Prova> listarTodas() {
        return jpaUtil.executeQuery(em -> {
            String jpql = "SELECT DISTINCT p FROM Prova p LEFT JOIN FETCH p.questoes";
            List<Prova> provas = em.createQuery(jpql, Prova.class).getResultList();

            for (Prova prova : provas) {
                for (Questao questao : prova.getQuestoes()) {
                    if (questao instanceof QuestaoMultiplaEscolha) {
                        ((QuestaoMultiplaEscolha) questao).getAlternativas().size();
                    }
                }
            }
            return provas;
        });
    }

    public Prova atualizarProva(Prova prova) {
        jpaUtil.executeInTransaction(em -> em.merge(prova));
        return prova; // Retorna o objeto original, que o merge pode ter atualizado.
    }

    public void salvar(Prova prova) {
        cadastrarProva(prova);
    }
}