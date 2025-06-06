package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class TelaSeguradoPessoa extends JFrame {

    private SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtRenda;
    private JFormattedTextField txtDataNascimento;
    private JTextField txtLogradouro;
    private JTextField txtNumero;
    private JTextField txtComplemento;
    private JTextField txtPais;
    private JTextField txtEstado;
    private JTextField txtCidade;
    private JFormattedTextField txtCep;
    private JTextField txtBonus;

    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnIncluirAlterar;
    private JButton btnCancelar;
    private JButton btnLimpar;
    private JButton btnExcluir;
    private JButton btnVoltar;

    public TelaSeguradoPessoa() {
        initComponents();
        setTitle("CRUD Segurado Pessoa");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        JLabel lblCpf = new JLabel("CPF (ex: 000.000.000-00)");
        txtCpf = new JTextField(11);
        btnNovo = new JButton("Novo");
        btnBuscar = new JButton("Buscar");

        headerPanel.add(lblCpf);
        headerPanel.add(txtCpf);
        headerPanel.add(btnNovo);
        headerPanel.add(btnBuscar);

        JPanel personalDataPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        personalDataPanel.setBorder(BorderFactory.createTitledBorder("Dados Pessoais"));

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();
        txtNome.setEnabled(false);

        JLabel lblDataNascimento = new JLabel("Data Nascimento:");
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            txtDataNascimento = new JFormattedTextField(dateMask);
        } catch (Exception e) {
            txtDataNascimento = new JFormattedTextField();
        }
        txtDataNascimento.setEnabled(false);

        JLabel lblRenda = new JLabel("Renda:");
        txtRenda = new JTextField();
        txtRenda.setEnabled(false);

        JLabel lblBonus = new JLabel("Bônus:");
        txtBonus = new JTextField();
        txtBonus.setEnabled(false);

        personalDataPanel.add(lblNome);
        personalDataPanel.add(txtNome);
        personalDataPanel.add(lblDataNascimento);
        personalDataPanel.add(txtDataNascimento);
        personalDataPanel.add(lblRenda);
        personalDataPanel.add(txtRenda);
        personalDataPanel.add(lblBonus);
        personalDataPanel.add(txtBonus);

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
        String cpf = txtCpf.getText().replaceAll("[^0-9]", "");

        String cpfError = mediator.validarCpf(cpf);
        if (cpfError != null) {
            JOptionPane.showMessageDialog(this, cpfError, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (mediator.buscarSeguradoPessoa(cpf) != null) {
            JOptionPane.showMessageDialog(this, "Segurado com este CPF já existe", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        enableFields(true);
        btnIncluirAlterar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnBuscar.setEnabled(false);
        txtCpf.setEnabled(false);
        btnIncluirAlterar.setText("Incluir");
    }

    private void buscarAction() {
        String cpf = txtCpf.getText().replaceAll("[^0-9]", "");

        String cpfError = mediator.validarCpf(cpf);
        if (cpfError != null) {
            JOptionPane.showMessageDialog(this, cpfError, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SeguradoPessoa segurado = mediator.buscarSeguradoPessoa(cpf);
        if (segurado == null) {
            JOptionPane.showMessageDialog(this, "Segurado não encontrado", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        txtNome.setText(segurado.getNome());
        txtDataNascimento.setText(formatDate(segurado.getDataNascimento()));
        txtRenda.setText(String.valueOf(segurado.getRenda()));
        txtBonus.setText(segurado.getBonus() != null ? segurado.getBonus().toString() : "");

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
        txtCpf.setEnabled(false);
        btnIncluirAlterar.setText("Alterar");
    }

    private void incluirAlterarAction() {
        try {
            String cpf = txtCpf.getText().replaceAll("[^0-9]", "");

            String cpfError = mediator.validarCpf(cpf);
            if (cpfError != null) {
                JOptionPane.showMessageDialog(this, cpfError, "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nome = txtNome.getText().trim();
            String dataNascStr = txtDataNascimento.getText().replaceAll("[^0-9]", "");

            if (dataNascStr.length() != 8) {
                JOptionPane.showMessageDialog(this, "Data de nascimento inválida", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate dataNascimento = parseDate(dataNascStr);
            double renda = Double.parseDouble(txtRenda.getText());
            BigDecimal bonus = txtBonus.getText().isEmpty() ?
                    BigDecimal.ZERO : new BigDecimal(txtBonus.getText());

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

            SeguradoPessoa segurado = new SeguradoPessoa(
                    nome, endereco, dataNascimento, bonus, cpf, renda
            );

            String msg;
            if (btnIncluirAlterar.getText().equals("Incluir")) {
                msg = mediator.incluirSeguradoPessoa(segurado);
            } else {
                msg = mediator.alterarSeguradoPessoa(segurado);
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
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarAction() {
        resetForm();
    }

    private void excluirAction() {
        String cpf = txtCpf.getText().replaceAll("[^0-9]", "");

        String cpfError = mediator.validarCpf(cpf);
        if (cpfError != null) {
            JOptionPane.showMessageDialog(this, cpfError, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String msg = mediator.excluirSeguradoPessoa(cpf);
        if (msg != null) {
            JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            resetForm();
        }
    }

    private void limparAction() {
        if (txtCpf.isEnabled()) {
            txtCpf.setText("");
        }
        txtNome.setText("");
        txtDataNascimento.setText("");
        txtRenda.setText("");
        txtBonus.setText("");
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
        txtCpf.setEnabled(true);
        btnIncluirAlterar.setText("Incluir");
        limparAction();
    }

    private void enableFields(boolean enabled) {
        txtNome.setEnabled(enabled);
        txtDataNascimento.setEnabled(enabled);
        txtRenda.setEnabled(enabled);
        txtBonus.setEnabled(enabled);
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