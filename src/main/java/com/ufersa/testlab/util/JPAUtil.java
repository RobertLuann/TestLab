package com.ufersa.testlab.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.function.Consumer;
import java.util.function.Function;

public class JPAUtil {
    private static final JPAUtil INSTANCIA = new JPAUtil();
    private final EntityManagerFactory factory;

    private JPAUtil() {
        this.factory = Persistence.createEntityManagerFactory("TestLab");
    }

    public static JPAUtil getInstancia() {
        return INSTANCIA;
    }

    public EntityManager getEntityManager() {
        return this.factory.createEntityManager();
    }

    public void close() {
        if (this.factory != null && this.factory.isOpen()) {
            this.factory.close();
        }
    }


    //salvar, atualizar e  deletar
    public void executeInTransaction(Consumer<EntityManager> action) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            action.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro na transação", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    //buscar e listar
    public <T> T executeQuery(Function<EntityManager, T> action) {
        EntityManager em = getEntityManager();
        try {
            return action.apply(em);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}