package com.ufersa.testlab.model.services;

import com.ufersa.testlab.model.dao.DisciplinaDAO;
import com.ufersa.testlab.model.entities.Disciplina;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class DisciplinaService {
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    public void cadastrarDisciplina(String codigo, String nome, List<String> assuntos) {
        if (codigo.isBlank()) {
            throw new IllegalArgumentException("Código não pode ser vazio.");
        }

        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        for (String a : assuntos) {
            if (a.isBlank()) {
                throw new IllegalArgumentException("Assunto não pode ser vazio.");
            }
        }

        Disciplina disciplinaExiste = buscarPorCodigoClasse(codigo);

        if (disciplinaExiste != null) {
            throw new EntityExistsException("Já existe outra disciplina com esse código.");
        }

        Disciplina disciplina = new Disciplina(codigo, nome, assuntos);
        disciplinaDAO.cadastrarDisciplina(disciplina);
    }

    public Disciplina buscarPorCodigo(String codigo) {
        Disciplina disciplina = disciplinaDAO.buscarPorCodigo(codigo);
        if (disciplina == null) {
            throw new EntityNotFoundException("Disciplina não encontrado.");
        }
        return disciplina;
    }

    private Disciplina buscarPorCodigoClasse(String codigo) {
        return disciplinaDAO.buscarPorCodigo(codigo);
    }

    public List<Disciplina> listarDisciplinas() {
        List<Disciplina> disciplinas = disciplinaDAO.listarDisciplinas();
        if (disciplinas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma disciplina cadastrada.");
        }
        return disciplinas;
    }

    public List<Disciplina> findByName(String nome) {
        if (nome == null || nome.trim().length() < 2) {
            return new ArrayList<>();
        }

        return disciplinaDAO.buscarPorNome(nome);
    }

    public void atualizarDisciplina(String codigo, String nome, List<String> assuntos) {
        Disciplina disciplina = buscarPorCodigo(codigo);

        if (disciplina != null) {
            if (nome.isBlank()) {
                throw new IllegalArgumentException("Nome não pode ser vazio.");
            }

            for (String a : assuntos) {
                if (a.isBlank()) {
                    throw new IllegalArgumentException("Assunto não pode ser vazio.");
                }
            }

            disciplina.setNome(nome);
            disciplina.deleteAssuntos();
            disciplina.setAssuntos(assuntos);

            disciplinaDAO.atualizarDisciplina(disciplina);
        }
    }

    public void deletarDisciplina(String codigo) {
        Disciplina d = buscarPorCodigo(codigo);
        if (d != null) {
            disciplinaDAO.deletarDisciplina(d);
        }
    }
}
