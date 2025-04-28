package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class SeguradoMediator {
    private static SeguradoMediator instancia;

    public String validarNome(String nome) {
        return null;
    }
    public String validarEndereco(Endereco endereco) {
        return null;
    }
    public String validarDataCriacao(LocalDate dataCriacao) {
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
