package com.ufersa.testlab.model.dao;

import com.ufersa.testlab.model.entities.Usuario;
import com.ufersa.testlab.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class UsuarioDAO {
    public static UsuarioDAO instance;

    private UsuarioDAO() {}

    private final JPAUtil jpaUtil = JPAUtil.getInstancia();

    public static synchronized UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    // Cada método agora gerencia seu próprio EntityManager, usando o padrão correto.
    public void cadastrarUsuario(Usuario usuario) {
        jpaUtil.executeInTransaction(em -> em.persist(usuario));
    }

    public Usuario buscarPorID(Long id) {
        return jpaUtil.executeQuery(em -> em.find(Usuario.class, id));
    }

    public Usuario buscarPorEmail(String email) {
        return jpaUtil.executeQuery(em -> {
            try {
                return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null; // A lógica de tratamento de exceção fica aqui dentro.
            }
        });
    }

    public List<Usuario> listarUsuarios() {
        return jpaUtil.executeQuery(em ->
                em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList()
        );
    }

    public void atualizarUsuario(Usuario usuario) {
        jpaUtil.executeInTransaction(em -> em.merge(usuario));
    }

    public void deletarUsuario(Usuario usuario) {
        jpaUtil.executeInTransaction(em -> {
            // Garante que a entidade está no estado "managed" antes de remover
            Usuario managedUsuario = em.merge(usuario);
            em.remove(managedUsuario);
        });
    }

}