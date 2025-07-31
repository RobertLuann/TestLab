package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.util.JPAUtil; // Importa a classe utilitária
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DisciplinaDAO {

    // As variáveis de instância para emf e em foram removidas.
    private final JPAUtil jpaUtil = JPAUtil.getInstancia();

    public void cadastrarDisciplina(Disciplina disciplina) {
        // Padrão: Obter EntityManager, usar e fechar.
        jpaUtil.executeInTransaction(em -> em.persist(disciplina));
    }

    public Disciplina buscarPorCodigo(String codigo) {
        return jpaUtil.executeQuery(em -> em.find(Disciplina.class, codigo));
    }

    public List<Disciplina> listarDisciplinas() {
        return jpaUtil.executeQuery(em ->
                em.createQuery("SELECT d FROM Disciplina d ORDER BY d.nome", Disciplina.class)
                        .getResultList()
        );
    }

    public void atualizarDisciplina(Disciplina disciplina) {
        jpaUtil.executeInTransaction(em -> em.merge(disciplina));

    }

    public void deletarDisciplina(Disciplina disciplina) {
        jpaUtil.executeInTransaction(em -> {
            // Garante que a entidade está no estado "managed" antes de remover.
            Disciplina managedDisciplina = em.merge(disciplina);
            em.remove(managedDisciplina);
        });
    }

    public List<Disciplina> buscarPorNome(String nome) {
        return jpaUtil.executeQuery(em -> {
            String jpql = "SELECT d FROM Disciplina d WHERE d.nome LIKE :nome";
            TypedQuery<Disciplina> query = em.createQuery(jpql, Disciplina.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        });
    }
}