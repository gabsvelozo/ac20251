package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;
//import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
//import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;

public class SeguradoMediator {
    private static SeguradoMediator instancia;

    public String validarNome(String nome) {
        if(StringUtils.ehNuloOuBranco(nome)){
            return "O nome é obrigatório";
        }
        return null;
    }
    public String validarEndereco(Endereco endereco) {
        if(endereco == null){
            return "Endereço deve ser informado";
        }
        return null;
    }

    public String validarDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null) {
            return "Data da abertura deve ser informada";
        }
        if (dataCriacao.isAfter(LocalDate.now())) {
            return "Data de criação não pode ser futura.";
        }
        return null;
    }
    public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
        return null;
    }

    public static SeguradoMediator getInstancia() {
        if (instancia == null) {
            instancia = new SeguradoMediator();
        }
        return instancia;
    }
}
