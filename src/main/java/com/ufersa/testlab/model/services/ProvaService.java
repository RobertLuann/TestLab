package com.ufersa.testlab.model.services;

import com.ufersa.testlab.model.dao.ProvaDAO;
import com.ufersa.testlab.model.entities.Disciplina;
import com.ufersa.testlab.model.entities.Prova;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class ProvaService {

    private final ProvaDAO provaDAO = new ProvaDAO();
    public ProvaService() {}

    public void cadastrarProva(Prova prova) {
        if (prova.getTitulo() == null || prova.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título da prova é obrigatório.");
        }
        if (prova.getQuestoes().isEmpty()) {
            throw new IllegalArgumentException("Precisa ter pelo menos uma questão.");
        }
        buscarPorTituloClasse(prova.getTitulo());

        provaDAO.cadastrarProva(prova);
    }


    public void excluirProva(Long id) {
        Prova prova = buscarProvaPorId(id);
        provaDAO.deletarProva(prova);
    }

    public Prova buscarProvaPorId(Long id) {
        Prova prova = provaDAO.buscarPorId(id);
        if (prova == null) {
            throw new EntityNotFoundException("Prova não existe.");
        }
        return prova;
    }

    public Prova buscarProvaPorDisciplina(Long id) {
        Prova prova = provaDAO.buscarPorId(id);
        if (prova == null) {
            throw new EntityNotFoundException("Prova não existe.");
        }
        return prova;
    }

    public List<Prova> listarTodasProvas() {
        List<Prova> provas = provaDAO.listarTodas();
        if (provas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma prova encontrada.");
        }
        return provas;
    }

    public Prova buscarPorTitulo(String titulo) {
        Prova prova = provaDAO.buscarPorTitulo(titulo);
        if (prova == null) {
            throw new EntityNotFoundException("Prova não existe");
        }
        return prova;
    }

    private Prova buscarPorTituloClasse(String titulo) {
        Prova prova = provaDAO.buscarPorTitulo(titulo);
        if (prova != null) {
            throw new EntityExistsException("Já tem uma prova com esse titulo.");
        }
        return prova;
    }

    public List<Prova> buscarPorDisciplina(Disciplina disciplina) {
        List<Prova> provas = provaDAO.buscarPorDisciplina(disciplina.getCodigo());
        if (provas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma prova encontrada.");
        }
        return provas;
    }

    public void atualizarProva(Prova prova) {
        buscarProvaPorId(prova.getId());

        if (prova.getTitulo() == null || prova.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título da prova é obrigatório.");
        }
        if (prova.getQuestoes().isEmpty()) {
            throw new IllegalArgumentException("Precisa ter pelo menos uma questão.");
        }
        try {
            if (!buscarPorTitulo(prova.getTitulo()).getId().equals(prova.getId())) {
                throw new EntityExistsException("Já existe uma prova com esse titulo.");
            }
        } catch(EntityNotFoundException ignored) {}

        provaDAO.atualizarProva(prova);
    }
}