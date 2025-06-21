package com.ufersa.testlab.services;

import com.ufersa.testlab.dao.DisciplinaDAO;
import com.ufersa.testlab.entities.Disciplina;
import com.ufersa.testlab.entities.Usuario;

import java.util.List;

public class DisciplinaService {
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    public void cadastrarDisciplina(String codigo, String nome, List<String> assuntos) {
        Disciplina disciplina = new Disciplina(codigo, nome);
        assuntos.forEach(disciplina::setAssunto);
        disciplinaDAO.cadastrarDisciplina(disciplina);
    }

    public Disciplina buscarPorCodigo(String codigo) {
        return disciplinaDAO.buscarPorCodigo(codigo);
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinaDAO.listarDisciplinas();
    }

    public void atualizarDisciplina(String codigo, String nome, List<String> assuntos) {
        Disciplina disciplina = disciplinaDAO.buscarPorCodigo(codigo);

        if (disciplina != null) {
            // TO-DO: validar os novos dados
            disciplina.setNome(nome);
            disciplina.deleteAssuntos();
            assuntos.forEach(disciplina::setAssunto);

            disciplinaDAO.atualizarDisciplina(disciplina);
        }
    }

    public void deletarDisciplina(String codigo) {
        Disciplina d = disciplinaDAO.buscarPorCodigo(codigo);
        if (d != null) {
            disciplinaDAO.deletarDisciplina(d);
        }
    }
}
