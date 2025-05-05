package br.edu.cs.poo.ac.seguro.mediators;

//import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
//import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
//import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;
//import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCnpjValido;

public class SeguradoEmpresaMediator {
    //private SeguradoMediator seguradoMediator = new SeguradoMediator();
    //private SeguradoEmpresaDAO seguradoEmpresaDAO;
    private static SeguradoEmpresaMediator instancia;

    public static SeguradoEmpresaMediator getInstancia() {
        if (instancia == null) {
            instancia = new SeguradoEmpresaMediator();
        }
        return instancia;
    }

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

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        if (seg == null) {
            return "Segurado inválido.";
        }
        if (ValidadorCpfCnpj.ehCnpjValido(seg.getCnpj())) {
            return "CPF é obrigatório.";
        }
        if (StringUtils.ehNuloOuBranco(seg.getNome())) {
            return "Nome é obrigatório.";
        }

        return null;
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        // Código para incluir no DAO
        // seguradoEmpresaDAO.incluir(seg);
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
