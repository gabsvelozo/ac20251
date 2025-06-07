package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class TelaApolice extends JFrame {

    private ApoliceMediator mediator = ApoliceMediator.getInstancia();
    private JTextField txtCpfCnpj;
    private JTextField txtPlaca;
    private JTextField txtAno;
    private JFormattedTextField txtValorMaximo;
    private JComboBox<String> cbCategoria;
    private JButton btnIncluir;
    private JButton btnLimpar;
    private JButton btnVoltar;

    public TelaApolice() {
        initComponents();
        setTitle("Inclusão de Apólice");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("INCLUSÃO DO APÓLICE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCpfCnpj = new JLabel("CPF ou CNPJ");
        txtCpfCnpj = new JFormattedTextField(NumberFormat.getNumberInstance());
        addRow(gbc, formPanel, lblCpfCnpj, txtCpfCnpj, 0);

        JLabel lblPlaca = new JLabel("Placa do Veículo:");
        txtPlaca = new JTextField(15);
        addRow(gbc, formPanel, lblPlaca, txtPlaca, 1);

        JLabel lblAno = new JLabel("Ano do Veículo:");
        txtAno = new JTextField(15);
        addRow(gbc, formPanel, lblAno, txtAno, 2);

        JLabel lblValorMaximo = new JLabel("Valor do Maximo:");
        txtValorMaximo = new JFormattedTextField(NumberFormat.getNumberInstance());
        txtValorMaximo.setColumns(15);
        addRow(gbc, formPanel, lblValorMaximo, txtValorMaximo, 3);

        JLabel lblCategoriaVeiculo = new JLabel("Tipo de Sinistro:");
        cbCategoria = new JComboBox<>();
        CategoriaVeiculo[] tipos = CategoriaVeiculo.values();
        Arrays.sort(tipos, Comparator.comparing(CategoriaVeiculo::getNome));
        for (CategoriaVeiculo tipo : tipos) {
            cbCategoria.addItem(tipo.getNome());
        }
        addRow(gbc, formPanel, lblCategoriaVeiculo, cbCategoria, 4);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnIncluir = new JButton("Incluir");
        btnLimpar = new JButton("Limpar");
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(220, 220, 220));

        buttonPanel.add(btnIncluir);
        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnVoltar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnIncluir.addActionListener(e -> incluirApolice());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> voltarAction());
    }

    private void addRow(GridBagConstraints gbc, JPanel panel, JLabel label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void incluirApolice() {

        String cpfCnpj = txtCpfCnpj.getText().trim();
        if (cpfCnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF ou CNPJ deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Placa do veículo deve ser informada", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int ano;
        String anoTexto = txtAno.getText().trim();
        if (anoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ano do veículo deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            ano = Integer.parseInt(anoTexto);
            if (ano < 1900 || ano > LocalDate.now().getYear()) {
                JOptionPane.showMessageDialog(this, "Ano do veículo inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ano do veículo inválido! Use apenas números", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal valorMaximo;
        String valorTexto = txtValorMaximo.getText().trim();
        if (valorTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Valor máximo deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String valorStr = valorTexto.replace(".", "").replace(",", ".");
            valorMaximo = new BigDecimal(valorStr);
            if (valorMaximo.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Valor máximo deve ser maior que zero", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException | ArithmeticException e) {
            JOptionPane.showMessageDialog(this, "Valor máximo inválido! Use números decimais", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigoCategoria = -1;
        String categoriaSelecionada = (String) cbCategoria.getSelectedItem();
        for (CategoriaVeiculo categoria : CategoriaVeiculo.values()) {
            if (categoria.getNome().equals(categoriaSelecionada)) {
                codigoCategoria = categoria.getCodigo();
                break;
            }
        }

        if (codigoCategoria == -1) {
            JOptionPane.showMessageDialog(this, "Categoria de veículo inválida", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DadosVeiculo dados = new DadosVeiculo(cpfCnpj, placa, ano, valorMaximo, codigoCategoria);

        // Chamada ao mediator
        RetornoInclusaoApolice retorno = mediator.incluirApolice(dados);

        if (retorno.getMensagemErro() != null) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao incluir apólice:\n" + retorno.getMensagemErro(),
                    "Erro na Inclusão", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Apólice incluída com sucesso!\nNúmero da apólice: " + retorno.getNumeroApolice(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos(); // método que limpa os campos do formulário
        }
    }

    private void limparCampos() {
        txtCpfCnpj.setText("");
        txtPlaca.setText("");
        txtAno.setText("");
        txtValorMaximo.setText("");
        cbCategoria.setSelectedIndex(0);
    }

    private void voltarAction() {
        this.dispose();
        new Home().setVisible(true);
    }

    /*private void configurarLayout() {
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
    }*/
}
