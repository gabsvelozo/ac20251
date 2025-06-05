package br.edu.cs.poo.ac.seguro.excecoes;

import java.util.ArrayList;
import java.util.List;

public class ExcecaoValidacaoDados extends Exception {
    private List<String> mensagensErro;

    public ExcecaoValidacaoDados() {
        this.mensagensErro = new ArrayList<>();
    }

    public ExcecaoValidacaoDados(String mensagem) {
        this();
        this.mensagensErro.add(mensagem);
    }

    public void adicionarMensagem(String mensagem) {
        this.mensagensErro.add(mensagem);
    }

    public List<String> getMensagensErro() {
        return mensagensErro;
    }

    public boolean temErros() {
        return !mensagensErro.isEmpty();
    }
}