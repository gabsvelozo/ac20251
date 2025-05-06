package br.edu.cs.poo.ac.seguro.mediators;

//import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;
import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCpfValido;

public class SeguradoPessoaMediator {
    //private SeguradoMediator seguradoMediator = new SeguradoMediator();
    //private SeguradoPessoaDAO seguradoPessoaDAO;
    private static SeguradoPessoaMediator instancia;

    public String validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return "CPF deve ser informado";
        }

        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        if (cpfNumerico.length() != 11) {
            return "CPF deve ter 11 caracteres";
        }

        if (todosDigitosIguais(cpfNumerico)) {
            return "CPF com dígito inválido";
        }

        int digito1 = calcularDigito(cpfNumerico.substring(0, 9), 10);
        if (digito1 != Character.getNumericValue(cpfNumerico.charAt(9))) {
            return "CPF com dígito inválido";
        }

        int digito2 = calcularDigito(cpfNumerico.substring(0, 10), 11);
        if (digito2 != Character.getNumericValue(cpfNumerico.charAt(10))) {
            return "CPF com dígito inválido";
        }
        return null;
    }

    private boolean todosDigitosIguais(String cpf) {
        char primeiroDigito = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != primeiroDigito) {
                return false;
            }
        }
        return true;
    }

    private int calcularDigito(String str, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += Character.getNumericValue(str.charAt(i)) * pesoInicial--;
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    public String validarRenda(double renda) {
        if(renda < 0){
            return "Renda deve ser maior ou igual à zero";
        }
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
