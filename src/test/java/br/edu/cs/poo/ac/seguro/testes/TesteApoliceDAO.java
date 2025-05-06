package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.Serializable;
import java.math.BigDecimal;

public class TesteApoliceDAO extends TesteDAO {
    private ApoliceDAO dao = new ApoliceDAO();

    @Test
    public void teste01() {
        String numero = "AP0001";
        Apolice ap = new Apolice(new Veiculo("A1234567", 2020, null, null, CategoriaVeiculo.BASICO),
                new BigDecimal("500"), new BigDecimal("1200"), new BigDecimal("20000"), null);
        cadastro.incluir(ap, numero);
        Assertions.assertNotNull(dao.buscar(numero));
    }

    @Test
    public void teste02() {
        String numero = "AP0002";
        Apolice ap = new Apolice(new Veiculo("B1234567", 2021, null, null, CategoriaVeiculo.ESPORTIVO),
                new BigDecimal("700"), new BigDecimal("1500"), new BigDecimal("25000"), null);
        cadastro.incluir(ap, numero);
        Assertions.assertNull(dao.buscar("AP9999"));
    }

    @Test
    public void teste03() {
        String numero = "AP0003";
        Apolice ap = new Apolice(new Veiculo("C1234567", 2022, null, null, CategoriaVeiculo.BASICO),
                new BigDecimal("300"), new BigDecimal("1100"), new BigDecimal("15000"), null);
        cadastro.incluir(ap, numero);
        Assertions.assertTrue(dao.excluir(numero));
    }

    @Test
    public void teste04() {
        Assertions.assertFalse(dao.excluir("AP404"));
    }

    @Test
    public void teste05() {
        String numero = "AP0005";
        Apolice ap = new Apolice(new Veiculo("E1234567", 2020, null, null, CategoriaVeiculo.BASICO),
                new BigDecimal("450"), new BigDecimal("1000"), new BigDecimal("18000"), null);
        ap.setNumero(numero);
        Assertions.assertTrue(dao.incluir(ap));
        Assertions.assertNotNull(dao.buscar(numero));
    }

    @Test
    public void teste06() {
        String numero = "AP0006";
        Apolice ap = new Apolice(new Veiculo("F1234567", 2019, null, null, CategoriaVeiculo.ESPORTIVO),
                new BigDecimal("600"), new BigDecimal("1600"), new BigDecimal("22000"), null);
        ap.setNumero(numero);
        cadastro.incluir(ap, numero);
        Assertions.assertFalse(dao.incluir(ap));
    }

    @Test
    public void teste07() {
        String numero = "AP0007";
        Apolice nova = new Apolice(new Veiculo("G1234567", 2018, null, null, CategoriaVeiculo.BASICO),
                new BigDecimal("350"), new BigDecimal("1300"), new BigDecimal("17000"), null);
        Assertions.assertFalse(dao.alterar(nova));
    }

    @Test
    public void teste08() {
        String numero = "AP0008";
        Apolice original = new Apolice(new Veiculo("H1234567", 2017, null, null, CategoriaVeiculo.BASICO),
                new BigDecimal("400"), new BigDecimal("1400"), new BigDecimal("19000"), null);
        original.setNumero(numero);
        cadastro.incluir(original, numero);

        Apolice nova = new Apolice(new Veiculo("H1234567", 2017, null, null, CategoriaVeiculo.ESPORTIVO),
                new BigDecimal("450"), new BigDecimal("1500"), new BigDecimal("20000"), null);
        nova.setNumero(numero);

        Assertions.assertTrue(dao.alterar(nova));
    }


    @Override
    protected Class<?> getClasse() {
        return Apolice.class;
    }
}

