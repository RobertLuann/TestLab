package com.ufersa.testlab.util; // O pacote deve ser este

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("TestLab");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}