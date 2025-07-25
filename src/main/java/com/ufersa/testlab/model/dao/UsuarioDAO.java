package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

public class UsuarioDAO {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestLab");
    private final EntityManager em = emf.createEntityManager();

    public void cadastrarUsuario(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public Usuario buscarPorID(Long id) {
        return em.find(Usuario.class, id);
    }

    public Usuario buscarPorEmail(String email) {
        Usuario usuario = null;
        try {
            usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException e) {
            System.out.println("Usuario nao encontrado.");
        }
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public void atualizarUsuario(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void deletarUsuario(Usuario usuario) {
        em.getTransaction().begin();
        em.remove(usuario);
        em.getTransaction().commit();
    }

    public void limparCache() {
        em.clear();
    }
}
