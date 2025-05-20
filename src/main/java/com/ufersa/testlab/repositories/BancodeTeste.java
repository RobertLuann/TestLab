package com.ufersa.testlab.repositories;

import com.ufersa.testlab.entities.Usuario;
import java.util.ArrayList;
import java.util.List;

public class BancodeTeste {
    private final List<Usuario> usuarios = new ArrayList<>();

    // Tabela Usuario
    public void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario pegarUsuarioId(Long id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        System.out.println("Usuário não encontrado.");
        return null;
    }

    public void dadosUsuario(Usuario u) {
        if (!(u == null)) {
            u.getUsuario();
        }
    }

    public void atualizarUsuario(Long id, String nome, String email, String senha, String cargo) {
        Usuario u = pegarUsuarioId(id);
        if (u == null) {
            System.out.println("Usuário não encontrado para atualização.");
            return;
        }

        if (nome != null && !nome.isBlank()) u.setNome(nome);
        if (email != null && !email.isBlank()) u.setEmail(email);
        if (senha != null && !senha.isBlank()) u.setSenha(senha);
        if (cargo != null && !cargo.isBlank()) u.setCargo(cargo);
        System.out.println("Usuário atualizado com sucesso.");
    }

    public void deletarUsuario(Long id) {
        boolean removido = usuarios.removeIf(usuario -> usuario.getId().equals(id));
        if (removido) {
            System.out.println("Usuário apagado com sucesso.");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
}