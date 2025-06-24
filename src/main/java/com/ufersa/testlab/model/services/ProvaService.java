package com.ufersa.testlab.model.service;

import com.ufersa.testlab.model.dao.ProvaDAO;
import com.ufersa.testlab.model.entities.Prova;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class ProvaService {

    private EntityManagerFactory factory;

    public ProvaService(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Prova criarProva(Prova prova) throws Exception {
        // Validação de Regra
        if (prova.getTitulo() == null || prova.getTitulo().trim().isEmpty()) {
            throw new Exception("O título da prova é obrigatório.");
        }

        EntityManager em = factory.createEntityManager();
        ProvaDAO dao = new ProvaDAO(em);

        // Validação de Unicidade
        if (dao.buscarPorTitulo(prova.getTitulo()) != null) {
            em.close(); // Fecha o entity manager antes de lançar a exceção
            throw new Exception("Já existe uma prova com o título: '" + prova.getTitulo() + "'");
        }

        em.close(); // Fecha o EM usado para a consulta

        try {
            em = factory.createEntityManager();
            dao = new ProvaDAO(em);

            em.getTransaction().begin();
            Prova provaSalva = dao.salvar(prova);
            em.getTransaction().commit();

            return provaSalva;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void excluirProva(Long id) {
        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            ProvaDAO dao = new ProvaDAO(em);

            em.getTransaction().begin();
            Prova prova = dao.buscarPorId(id); // Busca dentro da mesma transação
            if (prova != null) {
                dao.excluir(prova);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Prova buscarProvaPorId(Long id) {
        EntityManager em = factory.createEntityManager();
        try {
            return new ProvaDAO(em).buscarPorId(id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prova> listarTodasProvas() {
        EntityManager em = factory.createEntityManager();
        try {
            return new ProvaDAO(em).listarTodas();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}