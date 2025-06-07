package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaSeguradoEmpresa extends JFrame {

    private SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();
    private JTextField txtCnpj;
    private JTextField txtNome;
    private JTextField txtFaturamento;
    private JFormattedTextField txtDataAbertura;
    private JTextField txtLogradouro;
    private JTextField txtNumero;
    private JTextField txtComplemento;
    private JTextField txtPais;
    private JTextField txtEstado;
    private JTextField txtCidade;
    private JFormattedTextField txtCep;
    private JTextField txtBonus;
    private JTextField txtEhLocadoraDeVeiculos;

    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnIncluirAlterar;
    private JButton btnCancelar;
    private JButton btnLimpar;
    private JButton btnExcluir;
    private JButton btnVoltar;

    public TelaSeguradoEmpresa() {
        initComponents();
        setTitle("CRUD Segurado Empresa");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        JLabel lblCnpj = new JLabel("CNPJ (ex: 00.000.000/0000-00)");
        txtCnpj = new JTextField(11);
        btnNovo = new JButton("Novo");
        btnBuscar = new JButton("Buscar");

        headerPanel.add(lblCnpj);
        headerPanel.add(txtCnpj);
        headerPanel.add(btnNovo);
        headerPanel.add(btnBuscar);

        JPanel personalDataPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        personalDataPanel.setBorder(BorderFactory.createTitledBorder("Dados da Empresa"));

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();
        txtNome.setEnabled(false);

        JLabel lblDataAbertura = new JLabel("Data Abertura:");
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            txtDataAbertura = new JFormattedTextField(dateMask);
        } catch (Exception e) {
            txtDataAbertura = new JFormattedTextField();
        }
        txtDataAbertura.setEnabled(false);

        JLabel lblFaturamento = new JLabel("Faturamento:");
        txtFaturamento = new JTextField();
        txtFaturamento.setEnabled(false);

        JLabel lblBonus = new JLabel("Bônus:");
        txtBonus = new JTextField();
        txtBonus.setEnabled(false);

        JLabel lblEhLocadoraDeVeiculos = new JLabel("Eh Locadora de Veiculos:");
        txtEhLocadoraDeVeiculos = new JTextField();
        txtEhLocadoraDeVeiculos.setEnabled(false);

        personalDataPanel.add(lblNome);
        personalDataPanel.add(txtNome);
        personalDataPanel.add(lblDataAbertura);
        personalDataPanel.add(txtDataAbertura);
        personalDataPanel.add(lblFaturamento);
        personalDataPanel.add(txtFaturamento);
        personalDataPanel.add(lblBonus);
        personalDataPanel.add(txtBonus);
        personalDataPanel.add(lblEhLocadoraDeVeiculos);
        personalDataPanel.add(txtEhLocadoraDeVeiculos);

        JPanel addressPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        addressPanel.setBorder(BorderFactory.createTitledBorder("Endereço"));

        JLabel lblLogradouro = new JLabel("Logradouro:");
        txtLogradouro = new JTextField();
        txtLogradouro.setEnabled(false);

        JLabel lblCep = new JLabel("CEP:");
        try {
            MaskFormatter cepMask = new MaskFormatter("#####-###");
            cepMask.setPlaceholderCharacter('_');
            txtCep = new JFormattedTextField(cepMask);
        } catch (Exception e) {
            txtCep = new JFormattedTextField();
        }
        txtCep.setEnabled(false);

        JLabel lblNumero = new JLabel("Número:");
        txtNumero = new JTextField();
        txtNumero.setEnabled(false);

        JLabel lblComplemento = new JLabel("Complemento:");
        txtComplemento = new JTextField();
        txtComplemento.setEnabled(false);

        JLabel lblPais = new JLabel("País:");
        txtPais = new JTextField();
        txtPais.setEnabled(false);

        JLabel lblEstado = new JLabel("Estado:");
        txtEstado = new JTextField();
        txtEstado.setEnabled(false);

        JLabel lblCidade = new JLabel("Cidade:");
        txtCidade = new JTextField();
        txtCidade.setEnabled(false);

        addressPanel.add(lblLogradouro);
        addressPanel.add(txtLogradouro);
        addressPanel.add(lblCep);
        addressPanel.add(txtCep);
        addressPanel.add(lblNumero);
        addressPanel.add(txtNumero);
        addressPanel.add(lblComplemento);
        addressPanel.add(txtComplemento);
        addressPanel.add(lblPais);
        addressPanel.add(txtPais);
        addressPanel.add(lblEstado);
        addressPanel.add(txtEstado);
        addressPanel.add(lblCidade);
        addressPanel.add(txtCidade);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnIncluirAlterar = new JButton("Incluir");
        btnIncluirAlterar.setEnabled(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setEnabled(false);

        btnLimpar = new JButton("Limpar");

        btnExcluir = new JButton("Excluir");
        btnExcluir.setEnabled(false);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(220, 220, 220));

        buttonPanel.add(btnIncluirAlterar);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnLimpar);
        buttonPanel.add(btnVoltar);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(personalDataPanel);
        centerPanel.add(addressPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setupListeners();
    }

    private void setupListeners() {
        btnNovo.addActionListener(e -> novoAction());
        btnBuscar.addActionListener(e -> buscarAction());
        btnIncluirAlterar.addActionListener(e -> incluirAlterarAction());
        btnCancelar.addActionListener(e -> cancelarAction());
        btnExcluir.addActionListener(e -> excluirAction());
        btnLimpar.addActionListener(e -> limparAction());
        btnVoltar.addActionListener(e -> voltarAction());
    }

    private void voltarAction() {
        this.dispose();
        new Home().setVisible(true);
    }

    private void novoAction() {
        String cnpj = txtCnpj.getText().replaceAll("[^0-9]", "");

        String cnpjError = mediator.validarCnpj(cnpj);
        if (cnpjError != null) {
            JOptionPane.showMessageDialog(this, cnpjError, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (mediator.buscarSeguradoEmpresa(cnpj) != null) {
            JOptionPane.showMessageDialog(this, "Segurado com este CNPJ já existe", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        enableFields(true);
        btnIncluirAlterar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnBuscar.setEnabled(false);
        txtCnpj.setEnabled(false);
        btnIncluirAlterar.setText("Incluir");
    }

    private void buscarAction() {
        String cnpj = txtCnpj.getText().replaceAll("[^0-9]", "");

        String cnpjError = mediator.validarCnpj(cnpj);
        if (cnpjError != null) {
            JOptionPane.showMessageDialog(this, cnpjError, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CNPJ deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SeguradoEmpresa segurado = mediator.buscarSeguradoEmpresa(cnpj);
        if (segurado == null) {
            JOptionPane.showMessageDialog(this, "Segurado não encontrado", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        txtNome.setText(segurado.getNome());
        txtDataAbertura.setText(formatDate(segurado.getDataAbertura()));
        txtFaturamento.setText(String.valueOf(segurado.getFaturamento()));
        txtBonus.setText(segurado.getBonus() != null ? segurado.getBonus().toString() : "");

        if (segurado instanceof SeguradoEmpresa) {
            SeguradoEmpresa empresa = (SeguradoEmpresa) segurado;
            txtEhLocadoraDeVeiculos.setText(String.valueOf(empresa.isEhLocadoraDeVeiculos()));
        }

        Endereco endereco = segurado.getEndereco();
        if (endereco != null) {
            txtLogradouro.setText(endereco.getLogradouro());
            txtCep.setText(formatCep(endereco.getCep()));
            txtNumero.setText(endereco.getNumero());
            txtComplemento.setText(endereco.getComplemento());
            txtPais.setText(endereco.getPais());
            txtEstado.setText(endereco.getEstado());
            txtCidade.setText(endereco.getCidade());
        }

        enableFields(true);
        btnIncluirAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnBuscar.setEnabled(false);
        txtCnpj.setEnabled(false);
        btnIncluirAlterar.setText("Alterar");
    }

    private void incluirAlterarAction() {
        try {
            String cnpj = txtCnpj.getText().replaceAll("[^0-9]", "");

            String cpfError = mediator.validarCnpj(cnpj);
            if (cpfError != null) {
                JOptionPane.showMessageDialog(this, cpfError, "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nome = txtNome.getText().trim();
            String dataAberStr = txtDataAbertura.getText().replaceAll("[^0-9]", "");

            if (dataAberStr.length() != 8) {
                JOptionPane.showMessageDialog(this, "Data de abertura inválida", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate dataAbertura = parseDate(dataAberStr);
            double faturamento = Double.parseDouble(txtFaturamento.getText());
            BigDecimal bonus = txtBonus.getText().isEmpty() ?
                    BigDecimal.ZERO : new BigDecimal(txtBonus.getText());

            String ehLocadoraStr = txtEhLocadoraDeVeiculos.getText().trim().toLowerCase();
            boolean ehLocadoraDeVeiculos;

            if (ehLocadoraStr.equals("true") || ehLocadoraStr.equals("false")) {
                ehLocadoraDeVeiculos = Boolean.parseBoolean(ehLocadoraStr);
            } else {
                JOptionPane.showMessageDialog(this, "Valor inválido para locadora de veículos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtLogradouro.getText().isEmpty() ||
                    txtCep.getText().replaceAll("[^0-9]", "").length() != 8 ||
                    txtNumero.getText().isEmpty() ||
                    txtPais.getText().isEmpty() ||
                    txtEstado.getText().isEmpty() ||
                    txtCidade.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Endereço incompleto ou inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Endereco endereco = new Endereco(
                    txtLogradouro.getText(),
                    txtCep.getText().replaceAll("[^0-9]", ""),
                    txtNumero.getText(),
                    txtComplemento.getText(),
                    txtPais.getText(),
                    txtEstado.getText(),
                    txtCidade.getText()
            );

            SeguradoEmpresa segurado = new SeguradoEmpresa(
                    nome, endereco, dataAbertura, bonus, cnpj, faturamento, ehLocadoraDeVeiculos
            );

            String msg;
            if (btnIncluirAlterar.getText().equals("Incluir")) {
                msg = mediator.incluirSeguradoEmpresa(segurado);
            } else {
                msg = mediator.alterarSeguradoEmpresa(segurado);
            }

            if (msg != null) {
                JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                String successMsg = btnIncluirAlterar.getText().equals("Incluir") ?
                        "Inclusão realizada com sucesso!" : "Alteração realizada com sucesso!";

                JOptionPane.showMessageDialog(this, successMsg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data de abertura inválida", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarAction() {
        resetForm();
    }

    private void excluirAction() {
        String cnpj = txtCnpj.getText().replaceAll("[^0-9]", "");

        String cnpjError = mediator.validarCnpj(cnpj);
        if (cnpjError != null) {
            JOptionPane.showMessageDialog(this, cnpjError, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CNPJ deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String msg = mediator.excluirSeguradoEmpresa(cnpj);
        if (msg != null) {
            JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            resetForm();
        }
    }

    private void limparAction() {
        if (txtCnpj.isEnabled()) {
            txtCnpj.setText("");
        }
        txtNome.setText("");
        txtDataAbertura.setText("");
        txtFaturamento.setText("");
        txtBonus.setText("");
        txtEhLocadoraDeVeiculos.setText("");
        txtLogradouro.setText("");
        txtCep.setText("");
        txtNumero.setText("");
        txtComplemento.setText("");
        txtPais.setText("");
        txtEstado.setText("");
        txtCidade.setText("");
    }

    private void resetForm() {
        enableFields(false);
        btnIncluirAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnBuscar.setEnabled(true);
        txtCnpj.setEnabled(true);
        btnIncluirAlterar.setText("Incluir");
        limparAction();
    }

    private void enableFields(boolean enabled) {
        txtNome.setEnabled(enabled);
        txtDataAbertura.setEnabled(enabled);
        txtFaturamento.setEnabled(enabled);
        txtBonus.setEnabled(enabled);
        txtEhLocadoraDeVeiculos.setEnabled(enabled);
        txtLogradouro.setEnabled(enabled);
        txtCep.setEnabled(enabled);
        txtNumero.setEnabled(enabled);
        txtComplemento.setEnabled(enabled);
        txtPais.setEnabled(enabled);
        txtEstado.setEnabled(enabled);
        txtCidade.setEnabled(enabled);
    }

    private String formatDate(LocalDate date) {
        if (date == null) return "";
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date);
    }

    private String formatCep(String cep) {
        if (cep == null || cep.length() != 8) return cep;
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.length() != 8) {
            throw new DateTimeParseException("Formato inválido", dateStr, 0);
        }
        int dia = Integer.parseInt(dateStr.substring(0, 2));
        int mes = Integer.parseInt(dateStr.substring(2, 4));
        int ano = Integer.parseInt(dateStr.substring(4, 8));
        return LocalDate.of(ano, mes, dia);
    }
}