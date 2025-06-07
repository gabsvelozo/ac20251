package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
import br.edu.cs.poo.ac.seguro.mediators.DadosSinistro;
import br.edu.cs.poo.ac.seguro.mediators.SinistroMediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class TelaSinistro extends JFrame {

    private SinistroMediator mediator = SinistroMediator.getInstancia();
    private JTextField txtPlaca;
    private JFormattedTextField txtDataHora;
    private JComboBox<String> cmbTipoSinistro;
    private JTextField txtUsuarioRegistro;
    private JFormattedTextField txtValorSinistro;
    private JButton btnIncluir;
    private JButton btnLimpar;
    private JButton btnVoltar;

    public TelaSinistro() {
        initComponents();
        setTitle("Inclusão de Sinistro");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("INCLUSÃO DE SINISTRO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPlaca = new JLabel("Placa do Veículo:");
        txtPlaca = new JTextField(15);
        addRow(gbc, formPanel, lblPlaca, txtPlaca, 0);

        JLabel lblDataHora = new JLabel("Data/Hora do Sinistro (dd/MM/yyyy HH:mm):");
        try {
            MaskFormatter dateTimeMask = new MaskFormatter("##/##/#### ##:##");
            dateTimeMask.setPlaceholderCharacter('_');
            txtDataHora = new JFormattedTextField(dateTimeMask);
            txtDataHora.setColumns(15);
        } catch (Exception e) {
            txtDataHora = new JFormattedTextField();
        }
        addRow(gbc, formPanel, lblDataHora, txtDataHora, 1);

        JLabel lblTipoSinistro = new JLabel("Tipo de Sinistro:");
        cmbTipoSinistro = new JComboBox<>();
        TipoSinistro[] tipos = TipoSinistro.values();
        Arrays.sort(tipos, Comparator.comparing(TipoSinistro::getNome));
        for (TipoSinistro tipo : tipos) {
            cmbTipoSinistro.addItem(tipo.getNome());
        }
        addRow(gbc, formPanel, lblTipoSinistro, cmbTipoSinistro, 2);

        JLabel lblUsuarioRegistro = new JLabel("Usuário do Registro:");
        txtUsuarioRegistro = new JTextField(15);
        addRow(gbc, formPanel, lblUsuarioRegistro, txtUsuarioRegistro, 3);

        JLabel lblValorSinistro = new JLabel("Valor do Sinistro:");
        txtValorSinistro = new JFormattedTextField(NumberFormat.getNumberInstance());
        txtValorSinistro.setColumns(15);
        addRow(gbc, formPanel, lblValorSinistro, txtValorSinistro, 4);

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

        btnIncluir.addActionListener(e -> incluirSinistro());
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

    private void incluirSinistro() {
        DadosSinistro dados = new DadosSinistro();

        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Placa deve ser informada", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dados.setPlaca(placa);

        String dataHoraStr = txtDataHora.getText().trim();
        if (dataHoraStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data/Hora deve ser informada", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dados.setDataHoraSinistro(LocalDateTime.parse(dataHoraStr, formatter));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data/hora inválido! Use dd/MM/yyyy HH:mm",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipoSelecionado = (String) cmbTipoSinistro.getSelectedItem();
        for (TipoSinistro tipo : TipoSinistro.values()) {
            if (tipo.getNome().equals(tipoSelecionado)) {
                dados.setCodigoTipoSinistro(tipo.getCodigo());
                break;
            }
        }

        String usuario = txtUsuarioRegistro.getText().trim();
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuário deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dados.setUsuarioRegistro(usuario);

        String valorTexto = txtValorSinistro.getText().trim();
        if (valorTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Valor deve ser informado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String valorStr = valorTexto.replace(".", "").replace(",", ".");
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "Valor deve ser maior que zero", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dados.setValorSinistro(valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido! Use números decimais",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String numeroSinistro = mediator.incluirSinistro(dados, LocalDateTime.now());
            JOptionPane.showMessageDialog(this,
                    "Sinistro incluído com sucesso! Anote o número do sinistro: " + numeroSinistro,
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (ExcecaoValidacaoDados ex) {
            StringBuilder erros = new StringBuilder();
            for (String erro : ex.getMensagens()) {
                erros.append("• ").append(erro).append("\n");
            }
            JOptionPane.showMessageDialog(this,
                    "Erros de validação:\n" + erros.toString(),
                    "Erro na Inclusão", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtPlaca.setText("");
        txtDataHora.setText("");
        cmbTipoSinistro.setSelectedIndex(0);
        txtUsuarioRegistro.setText("");
        txtValorSinistro.setText("");
        txtPlaca.requestFocus();
    }

    private void voltarAction() {
        this.dispose();
        new Home().setVisible(true);
    }
}