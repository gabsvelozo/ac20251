package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.*;
import lombok.Getter;
/*
public class ApoliceMediator {
    private static ApoliceMediator apoliceoMediator = ApoliceMediator.getInstancia();
    @Getter
    private static ApoliceMediator instancia = new ApoliceMediator();
    private SeguradoPessoaDAO daoSegPes;
    private SeguradoEmpresaDAO daoSegEmp;
    private VeiculoDAO daoVel;
    private ApoliceDAO daoApo;
    private SinistroDAO daoSin;

    private ApoliceMediator() {}

    public static void setApoliceoMediator(ApoliceMediator apoliceoMediator) {
        ApoliceMediator.apoliceoMediator = apoliceoMediator;
    }

    public RetornoInclusaoApolice incluirApolice(DadosVeiculo dados) {
        if (dados == null || dados.getPlaca() == null || dados.getPlaca().isBlank()) {
            return new RetornoInclusaoApolice(null, "Dados do veículo inválidos");
        }

        String cpfOuCnpj = dados.getCpfOuCnpj();
        String placa = dados.getPlaca();
        int anoAtual = LocalDate.now().getYear();

        // Buscar veículo
        Veiculo veiculo = daoVel.buscar(placa);

        // Obter segurado
        SeguradoPessoa pessoa = null;
        SeguradoEmpresa empresa = null;

        if (cpfOuCnpj.length() == 11) {
            pessoa = daoSegPes.buscar(cpfOuCnpj);
            if (pessoa == null) {
                return new RetornoInclusaoApolice(null, "Segurado pessoa não encontrado");
            }
        } else if (cpfOuCnpj.length() == 14) {
            empresa = daoSegEmp.buscar(cpfOuCnpj);
            if (empresa == null) {
                return new RetornoInclusaoApolice(null, "Segurado empresa não encontrado");
            }
        } else {
            return new RetornoInclusaoApolice(null, "CPF ou CNPJ inválido");
        }

        BigDecimal valorMaxPermitido = obterValorMaximoPermitido(dados.getAno(), dados.getCodigoCategoria());
        if (dados.getValorMaximoSegurado().compareTo(valorMaxPermitido) > 0) {
            return new RetornoInclusaoApolice(null, "Valor máximo segurado excede o permitido");
        }

        String numeroApolice;
        if (cpfOuCnpj.length() == 11) {
            numeroApolice = anoAtual + "000" + cpfOuCnpj + placa;
        } else {
            numeroApolice = anoAtual + cpfOuCnpj + placa;
        }

        if (daoApo.buscar(numeroApolice) != null) {
            return new RetornoInclusaoApolice(null, "Apólice já cadastrada");
        }

        if (veiculo == null) {
            CategoriaVeiculo cat = new CategoriaVeiculo(dados.getCodigoCategoria());
            veiculo = new Veiculo(placa, dados.getAno(), cat, pessoa, empresa); // use null para o não aplicável
            daoVel.inserir(veiculo);
        } else {
            veiculo.setSeguradoPessoa(pessoa);
            veiculo.setSeguradoEmpresa(empresa);
            daoVel.alterar(veiculo);
        }

        // Cálculo do prêmio
        BigDecimal vpa = dados.getValorMaximoSegurado().multiply(BigDecimal.valueOf(0.03));
        BigDecimal vpb = vpa;
        if (empresa != null && empresa.isLocadora()) {
            vpb = vpa.multiply(BigDecimal.valueOf(1.2));
        }
        BigDecimal bonus = pessoa != null ? pessoa.getBonus() : empresa.getBonus();
        BigDecimal vpc = vpb.subtract(bonus.divide(BigDecimal.TEN));
        BigDecimal premio = vpc.compareTo(BigDecimal.ZERO) > 0 ? vpc : BigDecimal.ZERO;
        premio = premio.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal franquia = vpb.multiply(BigDecimal.valueOf(1.3)).setScale(2, BigDecimal.ROUND_HALF_UP);

        // Criar e salvar apólice
        LocalDate inicioVigencia = LocalDate.now();
        Apolice apolice = new Apolice(numeroApolice, veiculo, dados.getValorMaximoSegurado(), premio, franquia, inicioVigencia);
        daoApo.inserir(apolice);

        // Bonificação
        boolean temSinistro = false;
        for (Sinistro s : daoSin.buscarTodos()) {
            if (s.getVeiculo().equals(veiculo)) {
                int anoSinistro = s.getDataHora().getYear();
                if (anoSinistro == inicioVigencia.minusYears(1).getYear()) {
                    temSinistro = true;
                    break;
                }
            }
        }
        if (!temSinistro) {
            BigDecimal bonificacao = premio.multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (pessoa != null) {
                pessoa.setBonus(pessoa.getBonus().add(bonificacao));
                daoSegPes.alterar(pessoa);
            } else {
                empresa.setBonus(empresa.getBonus().add(bonificacao));
                daoSegEmp.alterar(empresa);
            }
        }

        return new RetornoInclusaoApolice(numeroApolice, null);
    }

    public Apolice buscarApolice(String numero) {
        if (numero == null || numero.isBlank()) {
            return null;
        }

        return daoApo.buscar(numero);
    }

    public String excluirApolice(String numero) {
        if (numero == null || numero.isBlank()) {
            return "Número da apólice inválido";
        }

        Apolice apolice = daoApo.buscar(numero);
        if (apolice == null) {
            return "Apólice não encontrada";
        }

        LocalDate dataVigencia = apolice.getDataInicioVigencia();
        int anoVigencia = dataVigencia.getYear();
        Veiculo veiculo = apolice.getVeiculo();

        for (Sinistro sin : daoSin.buscarTodos()) {
            if (sin.getVeiculo().equals(veiculo) &&
                    sin.getDataHora().getYear() == anoVigencia) {
                return "Existe sinistro para este veículo no mesmo ano da apólice";
            }
        }

        daoApo.excluir(numero);
        return null;
    }

    private String validarTodosDadosVeiculo(DadosVeiculo dados) {
        return null;
    }
    private String validarCpfCnpjValorMaximo(DadosVeiculo dados) {
        return null;
    }
    private BigDecimal obterValorMaximoPermitido(int ano, int codigoCat) {
        return null;
    }
}*/