package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {
    private SeguradoMediator seguradoMediator = new SeguradoMediator();
    private SeguradoPessoaDAO seguradoPessoaDAO;
    private static SeguradoPessoaMediator instancia;

    public String validarCpf(String cpf) {
        return null;
    }
    public String validarRenda(double renda) {
        return null;
    }
    public String excluirSeguradoPessoa(String cpf) {
        return null;
    }
    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        return null;
    }

    public static SeguradoPessoaMediator getInstancia() {
        if (instancia == null) {
            instancia = new SeguradoPessoaMediator();
        }
        return instancia;
    }

    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        if (seg == null) {
            return "SeguradoPessoa não pode ser nulo.";
        }
        if (seg.getCpf() == null || seg.getCpf().isEmpty()) {
            return "CPF é obrigatório.";
        }
        if (seg.getNome() == null || seg.getNome().isEmpty()) {
            return "Nome é obrigatório.";
        }
        // Outros testes que você achar necessário...
        return null; // Se estiver tudo OK
    }

    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) {
            return msg;
        }
        // Código para incluir no DAO
        // seguradoPessoaDAO.incluir(seg);
        return null;
    }

    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) {
            return msg;
        }
        // Código para alterar no DAO
        // seguradoPessoaDAO.alterar(seg);
        return null;
    }

}
