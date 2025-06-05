package br.edu.cs.poo.ac.seguro.excecoes;

import java.util.ArrayList;
import java.util.List;

public class ExcecaoValidacaoDados extends Exception {
    private List<String> mensagens;

    public ExcecaoValidacaoDados() {
        this.mensagens = new ArrayList<>();
    }

    public ExcecaoValidacaoDados(String mensagem) {
        this();
        this.mensagens.add(mensagem);
    }

    public void adicionarMensagem(String mensagem) {
        this.mensagens.add(mensagem);
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public boolean temErros() {
        return !mensagens.isEmpty();
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }
}