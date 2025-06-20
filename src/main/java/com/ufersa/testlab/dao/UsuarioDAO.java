package com.ufersa.testlab.dao;

import com.ufersa.testlab.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class UsuarioDAO {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestLab");

    public void cadastrarUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public Usuario buscarPorID(Long id) {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = em.find(Usuario.class, id);
        em.close();
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> usuarios = em.createQuery("FROM usuario", Usuario.class).getResultList();
        em.close();
        return usuarios;
    }

    public void atualizarUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public void deletarUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(usuario);
        em.getTransaction().commit();
        em.close();
    }
}
