package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;
import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCpfValido;

public class SeguradoPessoaMediator {
    private SeguradoMediator seguradoMediator = new SeguradoMediator();
    private SeguradoPessoaDAO seguradoPessoaDAO;
    private static SeguradoPessoaMediator instancia;

    public String validarCpf(String cpf) {
        if(!temSomenteNumeros(cpf)) {
            return "O CPF possui dígito inválido.";
        }
        if(cpf.length() != 11){
            return "O CPF deve ter 11 dígitos.";
        }
        if(!ehCpfValido(cpf)){
            return "O CPF é inválido.";
        }
        if(ehNuloOuBranco(cpf)){
            return "O CPF deve ser informado.";
        }
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
            return "Segurado inválido.";
        }
        if (!ehCpfValido(seg.getCpf())) {
            return "CPF inválido.";
        }
        if (ehNuloOuBranco(seg.getNome())) {
            return "Nome é obrigatório.";
        }
        return null;
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
