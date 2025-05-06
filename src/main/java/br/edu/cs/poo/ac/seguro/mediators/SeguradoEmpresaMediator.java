package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
//import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
//import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;
//import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCnpjValido;

public class SeguradoEmpresaMediator {
    private static SeguradoEmpresaMediator instancia;
    private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    private SeguradoEmpresaDAO dao = new SeguradoEmpresaDAO();

    public static SeguradoEmpresaMediator getInstancia() {
        if (instancia == null) {
            instancia = new SeguradoEmpresaMediator();
        }
        return instancia;
    }

    public String validarCnpj(String cnpj) {
        if (ehNuloOuBranco(cnpj)) {
            return "CNPJ deve ser informado";
        }
        if (!StringUtils.temSomenteNumeros(cnpj)) {
            return "CNPJ deve conter apenas números";
        }
        if (cnpj.length() != 14) {
            return "CNPJ deve ter 14 caracteres";
        }

        if (cnpj.chars().distinct().count() == 1) {
            return "CNPJ inválido.";
        }

        // Cálculo dos dígitos verificadores
        int[] pesosPrimeiro = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundo  = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesosPrimeiro[i];
            }
            int digito1 = soma % 11;
            digito1 = (digito1 < 2) ? 0 : 11 - digito1;

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesosSegundo[i];
            }
            int digito2 = soma % 11;
            digito2 = (digito2 < 2) ? 0 : 11 - digito2;

            if (digito1 == Character.getNumericValue(cnpj.charAt(12)) &&
                    digito2 == Character.getNumericValue(cnpj.charAt(13))) {
                return null;
            } else {
                return "CNPJ com dígito inválido";
            }

        } catch (NumberFormatException e) {
            return "CNPJ inválido.";
        }
    }


    public String validarFaturamento(double faturamento) {
        if (faturamento <= 0) {
            return "Faturamento deve ser maior que zero";
        }
        return null;
    }

    public String excluirSeguradoEmpresa(String cnpj) {
        if (ehNuloOuBranco(cnpj)) {
            return "CNPJ deve ser informado.";
        }

        SeguradoEmpresa encontrado = buscarSeguradoEmpresa(cnpj);
        if (encontrado == null) {
            return "CNPJ do segurado empresa não existente";
        }

        dao.excluir(cnpj);
        return null;
    }


    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        if (ehNuloOuBranco(cnpj)) {
            return null;
        }
        return dao.buscar(cnpj);
    }

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        if (seg == null) {
            return "Segurado inválido.";
        }

        String msg = seguradoMediator.validarNome(seg.getNome());
        if (!ehNuloOuBranco(msg)) {
            return msg;
        }
        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (!ehNuloOuBranco(msg)) {
            return msg;
        }
        msg = seguradoMediator.validarDataCriacao(seg.getDataAbertura());
        if (!ehNuloOuBranco(msg)) {
            return "Data da abertura deve ser informada";
        }
        msg = validarCnpj(seg.getCnpj());
        if (!ehNuloOuBranco(msg)) {
            return msg;
        }
        msg = validarFaturamento(seg.getFaturamento());
        if (!ehNuloOuBranco(msg)) {
            return msg;
        }
        return null;
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (!ehNuloOuBranco(msg)) {
            return msg;
        }
        if (dao.buscar(seg.getCnpj()) != null) {
            return "CNPJ do segurado empresa já existente";
        }
        boolean sucesso = dao.incluir(seg);
        if (!sucesso) {
            return "Erro ao incluir segurado empresa.";
        }
        return null;
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }

        if (buscarSeguradoEmpresa(seg.getCnpj()) == null) {
            return "CNPJ do segurado empresa não existente";
        }

        dao.alterar(seg);
        return null;
    }
}
