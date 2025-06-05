package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.mediators.DadosSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class SinistroMediator {

    private VeiculoDAO daoVeiculo = new VeiculoDAO();
    private ApoliceDAO daoApolice = new ApoliceDAO();
    private SinistroDAO daoSinistro = new SinistroDAO();
    private static SinistroMediator instancia;

    public static SinistroMediator getInstancia() {
        if (instancia == null)
            instancia = new SinistroMediator();
        return instancia;
    }

    private SinistroMediator() {}

    public String incluirSinistro(DadosSinistro dados, LocalDateTime dataHoraAtual) throws ExcecaoValidacaoDados {
        ExcecaoValidacaoDados excecao = new ExcecaoValidacaoDados();

        // Validações básicas
        validarDadosBasicos(dados, dataHoraAtual, excecao);

        // Se houver erros, lança a exceção com todas as mensagens acumuladas
        if (excecao.temErros()) {
            throw excecao;
        }

        // Validações complexas (veículo, apólice, etc.)
        Veiculo veiculo = validarVeiculo(dados.getPlaca(), excecao);
        Apolice apoliceVigente = validarApoliceVigente(dados, veiculo, excecao);

        // Se houver erros nas validações complexas, lança a exceção
        if (excecao.temErros()) {
            throw excecao;
        }

        // Valida valor do sinistro vs valor máximo da apólice
        if (dados.getValorSinistro() > apoliceVigente.getValorMaximoSegurado()) {
            excecao.adicionarMensagem("Valor do sinistro excede o valor máximo segurado na apólice");
            throw excecao;
        }

        // Gerar número do sinistro
        String numeroSinistro = gerarNumeroSinistro(apoliceVigente);

        // Criar e salvar o sinistro
        Sinistro sinistro = new Sinistro(
                numeroSinistro,
                dados.getDataHoraSinistro(),
                veiculo,
                dados.getUsuarioRegistro(),
                dados.getValorSinistro(),
                dados.getCodigoTipoSinistro(),
                apoliceVigente.getNumero(),
                obterSequencial(apoliceVigente)
        );

        daoSinistro.salvar(sinistro);
        return numeroSinistro;
    }

    private void validarDadosBasicos(DadosSinistro dados, LocalDateTime dataHoraAtual,
                                     ExcecaoValidacaoDados excecao) {
        if (dados == null) {
            excecao.adicionarMensagem("Dados do sinistro não podem ser nulos");
            return;
        }

        if (dados.getDataHoraSinistro() == null) {
            excecao.adicionarMensagem("Data/hora do sinistro não pode ser nula");
        } else if (dados.getDataHoraSinistro().isAfter(dataHoraAtual)) {
            excecao.adicionarMensagem("Data/hora do sinistro deve ser menor que a atual");
        }

        if (dados.getPlaca() == null || dados.getPlaca().isBlank()) {
            excecao.adicionarMensagem("Placa do veículo não pode ser nula ou vazia");
        }

        if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().isBlank()) {
            excecao.adicionarMensagem("Usuário do registro não pode ser nulo ou vazio");
        }

        if (dados.getValorSinistro() <= 0) {
            excecao.adicionarMensagem("Valor do sinistro deve ser maior que zero");
        }

        if (dados.getCodigoTipoSinistro() == 0) {
            excecao.adicionarMensagem("Tipo de sinistro não pode ser nulo");
        }
    }

    private Veiculo validarVeiculo(String placa, ExcecaoValidacaoDados excecao) {
        Veiculo veiculo = daoVeiculo.buscarPorPlaca(placa);
        if (veiculo == null) {
            excecao.adicionarMensagem("Veículo com placa " + placa + " não cadastrado");
        }
        return veiculo;
    }

    private Apolice validarApoliceVigente(DadosSinistro dados, Veiculo veiculo,
                                          ExcecaoValidacaoDados excecao) {
        if (veiculo == null) return null;

        List<Apolice> apolices = daoApolice.buscarPorVeiculo(veiculo);
        for (Apolice apolice : apolices) {
            if (apolice.estaVigenteNaData(dados.getDataHoraSinistro())) {
                return apolice;
            }
        }

        excecao.adicionarMensagem("Não há apólice vigente para o veículo na data do sinistro");
        return null;
    }

    private String gerarNumeroSinistro(Apolice apolice) {
        int sequencial = obterSequencial(apolice);
        return String.format("S%s%03d", apolice.getNumero(), sequencial);
    }

    private int obterSequencial(Apolice apolice) {
        List<Sinistro> sinistros = daoSinistro.buscarPorApolice(apolice);
        if (sinistros.isEmpty()) {
            return 1;
        } else {
            Collections.sort(sinistros, new ComparadorSinistroSequencial());
            return sinistros.get(sinistros.size() - 1).getSequencial() + 1;
        }
    }
}