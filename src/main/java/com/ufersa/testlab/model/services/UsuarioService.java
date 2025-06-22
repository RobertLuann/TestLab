package com.ufersa.testlab.model.services;

import com.ufersa.testlab.model.entities.Funcionario;
import com.ufersa.testlab.model.entities.Gerente;
import com.ufersa.testlab.model.entities.Usuario;
import com.ufersa.testlab.model.dao.UsuarioDAO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrarUsuario(String nome, String email, String senha, boolean eGerente) {
        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres.");
        }

        Usuario emailUsado = buscarPorEmail(email);

        if (emailUsado != null) {
            throw new EntityExistsException("Email já está em uso.");
        } else {
            if (eGerente) {
                Gerente g = new Gerente(nome, email, senha);
                usuarioDAO.cadastrarUsuario(g);
            } else {
                Funcionario f = new Funcionario(nome, email, senha);
                usuarioDAO.cadastrarUsuario(f);
            }
        }
    }

    public Usuario buscarPorId(Long id) {
        Usuario usuario = usuarioDAO.buscarPorID(id);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        if (usuarios.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário cadastrado.");
        }
        return usuarios;
    }

    public void atualizarUsuario(Long id, String nome, String email, String senha) {
        Usuario usuario = buscarPorId(id);

        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo.");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres.");
        }

        Usuario emailUsado = buscarPorEmail(email);

        if (emailUsado == null) {
        } else if (!emailUsado.getId().equals(id)) {
            throw new EntityExistsException("Email já está em uso.");
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        usuarioDAO.atualizarUsuario(usuario);
    }

    public void deletarUsuario(Long id) {
        Usuario u = buscarPorId(id);

        usuarioDAO.deletarUsuario(u);
    }
}
