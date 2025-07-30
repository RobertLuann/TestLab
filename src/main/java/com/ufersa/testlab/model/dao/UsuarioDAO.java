package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Usuario;
import com.ufersa.testlab.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class UsuarioDAO {
    // A implementação do Singleton permanece, mas agora corrigida.
    public static UsuarioDAO instance;

    // O construtor agora é vazio e não cria mais conexões.
    private UsuarioDAO() {}

    public static synchronized UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    // Cada método agora gerencia seu próprio EntityManager, usando o padrão correto.
    public void cadastrarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Usuario buscarPorID(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Usuario buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Não é um erro, apenas significa que não encontrou. Retornar null é o esperado.
            return null;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Usuario> listarUsuarios() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deletarUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Garante que a entidade está no estado "managed" antes de remover
            if (!em.contains(usuario)) {
                usuario = em.merge(usuario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // O método limparCache() não é mais necessário, pois cada operação usa um EntityManager novo e limpo.
    // public void limparCache() { ... }
}