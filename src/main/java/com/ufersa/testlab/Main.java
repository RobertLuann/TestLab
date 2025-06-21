package com.ufersa.testlab;

import com.ufersa.testlab.entities.Disciplina;
import com.ufersa.testlab.entities.Usuario;
import com.ufersa.testlab.entities.Gerente;
import com.ufersa.testlab.entities.Funcionario;
import com.ufersa.testlab.entities.Questao;
import com.ufersa.testlab.entities.TipoQuestao;
import com.ufersa.testlab.entities.QuestaoMultiplaEscolha;
import com.ufersa.testlab.entities.QuestaoDissertativa;

import com.ufersa.testlab.services.UsuarioService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Usuarios
        UsuarioService usuarioService = new UsuarioService();

        usuarioService.cadastrarUsuario("Gerente", "gerente@gmail.com", "gerente123", true);
        System.out.println(usuarioService.buscarPorId(1L) instanceof Gerente);
    }
}
