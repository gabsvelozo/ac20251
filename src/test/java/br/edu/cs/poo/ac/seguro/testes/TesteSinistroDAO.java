package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.*;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TesteSinistroDAO extends TesteDAO{
    private SinistroDAO dao = new SinistroDAO();

    @Test
    public void teste01() {
        String numero = "S0001";
        Sinistro s = new Sinistro(new Veiculo("Z1234567", 2024, null, CategoriaVeiculo.ESPORTIVO),
                LocalDateTime.now(), LocalDateTime.now(), "admin", new BigDecimal("5000"), TipoSinistro.COLISAO);
        cadastro.incluir(s, numero);
        Assertions.assertNotNull(dao.buscar(numero));
    }

    @Test
    public void teste02() {
        String numero = "S0002";
        Sinistro s = new Sinistro(new Veiculo("Y1234567", 2023, null, CategoriaVeiculo.BASICO),
                LocalDateTime.now(), LocalDateTime.now(), "admin", new BigDecimal("3000"), TipoSinistro.FURTO);
        cadastro.incluir(s, numero);
        Assertions.assertNull(dao.buscar("S9999"));
    }

    @Test
    public void teste03() {
        String numero = "S0003";
        Sinistro s = new Sinistro(new Veiculo("X1234567", 2022, null, CategoriaVeiculo.BASICO),
                LocalDateTime.now(), LocalDateTime.now(), "admin", new BigDecimal("2000"), TipoSinistro.INCENDIO);
        cadastro.incluir(s, numero);
        Assertions.assertTrue(dao.excluir(numero));
    }

    @Test
    public void teste04() {
        Assertions.assertFalse(dao.excluir("S404"));
    }

    @Test
    public void teste05() {
        String numero = "S0005";
        Sinistro s = new Sinistro(new Veiculo("W1234567", 2021, null, CategoriaVeiculo.BASICO),
                LocalDateTime.now(), LocalDateTime.now(), "admin", new BigDecimal("2500"), TipoSinistro.DEPREDACAO);
        s.setNumero(numero);
        Assertions.assertTrue(dao.incluir(s));
        Assertions.assertNotNull(dao.buscar(numero));
    }

    @Test
    public void teste06() {
        String numero = "S0006";
        Sinistro s = new Sinistro(new Veiculo("V1234567", 2020, null, CategoriaVeiculo.ESPORTIVO),
                LocalDateTime.now(), LocalDateTime.now(), "admin", new BigDecimal("2700"), TipoSinistro.COLISAO);
        s.setNumero(numero);
        cadastro.incluir((Serializable) s, numero);
        Assertions.assertFalse(dao.incluir(s)); // duplicado
    }

    @Test
    public void teste07() {
        String numero = "S0007";
        Sinistro novo = new Sinistro(new Veiculo("U1234567", 2019, null, CategoriaVeiculo.BASICO),
                LocalDateTime.now(), LocalDateTime.now(), "admin", new BigDecimal("1900"), TipoSinistro.INCENDIO);
        novo.setNumero(numero);
        Assertions.assertFalse(dao.alterar(novo));
    }

    @Test
    public void teste08() {
        String numero = "S0008";
        Sinistro original = new Sinistro(new Veiculo("T1234567", 2018, null, CategoriaVeiculo.BASICO),
                LocalDateTime.now(), LocalDateTime.now(), "admin", new BigDecimal("2100"), TipoSinistro.DEPREDACAO);
        original.setNumero(numero);
        cadastro.incluir(original, numero);

        Sinistro novo = new Sinistro(new Veiculo("T1234567", 2018, null, CategoriaVeiculo.ESPORTIVO),
                LocalDateTime.now(), LocalDateTime.now(), "admin2", new BigDecimal("2300"), TipoSinistro.FURTO);
        novo.setNumero(numero);
        Assertions.assertTrue(dao.alterar(novo));
    }

    @Override
    protected Class<?> getClasse() {
        return Sinistro.class;
    }
}
