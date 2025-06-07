package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.mediators.ApoliceMediator;
import br.edu.cs.poo.ac.seguro.mediators.DadosVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.RetornoInclusaoApolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;

public class TelaApolice extends JFrame {

    private final ApoliceMediator mediator = ApoliceMediator.getInstancia();

    // Componentes da interface
    private JTextField txtCpfCnpj;
    private JTextField txtPlaca;
    private JSpinner spnAno;
    private JFormattedTextField txtValorMaximo;
    private JComboBox<String> cbCategoria;
    private JButton btnIncluir;
    private JButton btnLimpar;

    public TelaApolice() {
        super("Inclusão de Apólice");
        configurarLayout();
        configurarEventos();
        configurarJanela();
    }

    private void configurarLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: CPF/CNPJ
        gbc.gridy = 0;
        panel.add(new JLabel("CPF/CNPJ:"), gbc);

        gbc.gridx = 1;
        txtCpfCnpj = new JTextField(20);
        panel.add(txtCpfCnpj, gbc);

        // Linha 1: Placa
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Placa:"), gbc);

        gbc.gridx = 1;
        txtPlaca = new JTextField(10);
        panel.add(txtPlaca, gbc);

        // Linha 2: Ano
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Ano (2020-2025):"), gbc);

        gbc.gridx = 1;
        spnAno = new JSpinner(new SpinnerNumberModel(2020, 2020, 2025, 1));
        panel.add(spnAno, gbc);

        // Linha 3: Valor Máximo Segurado
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Valor Máximo Segurado:"), gbc);

        gbc.gridx = 1;
        txtValorMaximo = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        txtValorMaximo.setColumns(15);
        panel.add(txtValorMaximo, gbc);

        // Linha 4: Categoria
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(new JLabel("Categoria:"), gbc);

        gbc.gridx = 1;
        cbCategoria = new JComboBox<>();
        carregarCategorias();
        panel.add(cbCategoria, gbc);

        // Linha 5: Botões
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnIncluir = new JButton("Incluir");
        btnLimpar = new JButton("Limpar");
        botoesPanel.add(btnIncluir);
        botoesPanel.add(btnLimpar);
        panel.add(botoesPanel, gbc);

        add(panel);
    }

    private void carregarCategorias() {
        // Ordena categorias alfabeticamente pelo nome
        CategoriaVeiculo[] categorias = CategoriaVeiculo.values();
        Arrays.sort(categorias, Comparator.comparing(CategoriaVeiculo::getNome));

        for (CategoriaVeiculo categoria : categorias) {
            cbCategoria.addItem(categoria.getNome());
        }
    }

    private void configurarEventos() {
        // Botão Incluir
        btnIncluir.addActionListener(e -> {
            DadosVeiculo dados = coletarDados();
            if (dados != null) {
                RetornoInclusaoApolice retorno = mediator.incluirApolice(dados);
                tratarRetorno(retorno);
            }
        });

        // Botão Limpar
        btnLimpar.addActionListener(e -> limparCampos());
    }

    private DadosVeiculo coletarDados() {
        try {
            String cpfOuCnpj = txtCpfCnpj.getText().trim();
            String placa = txtPlaca.getText().trim();
            int ano = (int) spnAno.getValue();

            // Conversão do valor monetário
            BigDecimal valorMaximo = parseValor(txtValorMaximo.getText());

            // Obter código da categoria selecionada
            String nomeCategoria = (String) cbCategoria.getSelectedItem();
            int codigoCategoria = obterCodigoCategoria(nomeCategoria);

            return new DadosVeiculo(cpfOuCnpj, placa, ano, valorMaximo, codigoCategoria);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro na conversão de dados: " + ex.getMessage(),
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private BigDecimal parseValor(String texto) {
        texto = texto.replace("R$", "").trim().replace(".", "").replace(",", ".");
        return new BigDecimal(texto);
    }

    private int obterCodigoCategoria(String nomeCategoria) {
        for (CategoriaVeiculo categoria : CategoriaVeiculo.values()) {
            if (categoria.getNome().equals(nomeCategoria)) {
                return categoria.getCodigo();
            }
        }
        return 0;
    }

    private void tratarRetorno(RetornoInclusaoApolice retorno) {
        if (retorno.getMensagemErro() == null) {
            JOptionPane.showMessageDialog(this,
                    "Apólice incluída com sucesso! Anote o número da apólice: " + retorno.getNumeroApolice(),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    retorno.getMensagemErro(),
                    "Erro na Inclusão",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtCpfCnpj.setText("");
        txtPlaca.setText("");
        spnAno.setValue(2020);
        txtValorMaximo.setValue(null);
        cbCategoria.setSelectedIndex(0);
        txtCpfCnpj.requestFocus();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaApolice::new);
    }
}
