package com.ufersa.testlab.repositories;

import com.ufersa.testlab.entities.Disciplina;
import com.ufersa.testlab.entities.Usuario;
import java.util.ArrayList;
import java.util.List;

public class BancodeTeste {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Disciplina> disciplinas = new ArrayList<>();

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

    // Simulação tabela Disciplina
    public void cadastrarDisciplina(Disciplina disc) {
        disciplinas.add(disc);
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    public Disciplina pegarDisciplinaCodigo(String codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equals(codigo)) {
                return disciplina;
            }
        }
        System.out.println("Disciplina não encontrada.");
        return null;
    }

    public void dadosDisciplina(Disciplina d) {
        if (!(d == null)) {
            d.getDisciplina();
        }
    }

    public void atualizarDisciplina(String codigo, String nome, String[] assuntos) {
        Disciplina disc = pegarDisciplinaCodigo(codigo);
        if (disc == null) {
            System.out.println("Disciplina não encontrada para atualização.");
            return;
        }

        if (codigo != null && !codigo.isBlank()) disc.setCodigo(codigo);
        if (nome != null && !nome.isBlank()) disc.setNome(nome);
        if (assuntos != null) disc.setAssuntos(assuntos);
        System.out.println("Disciplina atualizado com sucesso.");
    }

    public void deletarDisciplina(String codigo) {
        boolean removido = disciplinas.removeIf(disciplina -> disciplina.getCodigo().equals(codigo));
        if (removido) {
            System.out.println("Disciplina apagada com sucesso.");
        } else {
            System.out.println("Disciplina não encontrada.");
        }
    }

}