package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoEmpresaMediator {
    private SeguradoMediator seguradoMediator = new SeguradoMediator();
    private SeguradoEmpresaDAO seguradoEmpresaDAO;
    private static SeguradoEmpresaMediator instancia;

    public String validarCnpj(String cnpj) {
        return null;
    }
    public String validarFaturamento(double faturamento) {
        return null;
    }
    public String excluirSeguradoEmpresa(String cnpj) {
        return null;
    }
    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return null;
    }

    public static SeguradoEmpresaMediator getInstancia() {
        if (instancia == null) {
            instancia = new SeguradoEmpresaMediator();
        }
        return instancia;
    }

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        if (seg == null) {
            return "SeguradoPessoa não pode ser nulo.";
        }
        if (seg.getCnpj() == null || seg.getCnpj().isEmpty()) {
            return "CPF é obrigatório.";
        }
        if (seg.getNome() == null || seg.getNome().isEmpty()) {
            return "Nome é obrigatório.";
        }
        // Outros testes que você achar necessário...
        return null; // Se estiver tudo OK
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        // Código para incluir no DAO
        // seguradoPessoaDAO.incluir(seg);
        return null;
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        // Código para alterar no DAO
        // seguradoPessoaDAO.alterar(seg);
        return null;
    }
}
