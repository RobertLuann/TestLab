package com.ufersa.testlab.services;

import com.ufersa.testlab.entities.Funcionario;
import com.ufersa.testlab.entities.Gerente;
import com.ufersa.testlab.entities.Usuario;
import com.ufersa.testlab.dao.UsuarioDAO;

import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrarUsuario(String nome, String email, String senha, boolean eGerente) {
        // TO-DO: validar os dados do Usuario
        if (eGerente) {
            Gerente g = new Gerente(nome, email, senha);
            usuarioDAO.cadastrarUsuario(g);
        } else {
            Funcionario f = new Funcionario(nome, email, senha);
            usuarioDAO.cadastrarUsuario(f);
        }
    }

    public Usuario buscarPorId(Long id) {
        Usuario u = usuarioDAO.buscarPorID(id);
        // TO-DO: verificar se o usuario é nulo
        return u;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public void atualizarUsuario(Long id, String nome, String email, String senha) {
        Usuario usuario = usuarioDAO.buscarPorID(id);

        if (usuario != null) {
            // TO-DO: validar os novos dados
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);

            usuarioDAO.atualizarUsuario(usuario);
        }
    }

    public void deletarUsuario(Long id) {
        Usuario u = usuarioDAO.buscarPorID(id);
        // TO-DO: fazer melhor tratamento de exceções
        if (u != null) {
            usuarioDAO.deletarUsuario(u);
        }
    }
}
