package org.cesarschool.telas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaExemploCadastro
        extends JFrame {
    private JPanel contentPane;
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtRenda;
    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnIncluirAlterar;
    private JButton btnCancelar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    private EntidadeMediator mediator = new EntidadeMediator();

    public TelaExemploCadastro
            () {
        setTitle("Tela de Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        setSize(600, 350);
        setLocationRelativeTo(null);
        initListeners();
        resetarEstadoInicial();
    }

    private void initListeners() {
        btnNovo.addActionListener(e -> {
            Entidade ent = mediator.buscar(txtCodigo.getText());
            if (ent != null) {
                JOptionPane.showMessageDialog(null, "Entidade já existente!");
            } else {
                habilitarEdicao(true);
                btnIncluirAlterar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                txtCodigo.setEnabled(false);
            }
        });

        btnBuscar.addActionListener(e -> {
            Entidade ent = mediator.buscar(txtCodigo.getText());
            if (ent == null) {
                JOptionPane.showMessageDialog(null, "Entidade não existente!");
            } else {
                txtNome.setText(ent.getNome());
                txtRenda.setText(String.valueOf(ent.getRenda()));
                habilitarEdicao(true);
                btnIncluirAlterar.setText("Alterar");
                btnIncluirAlterar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                txtCodigo.setEnabled(false);
            }
        });

        btnIncluirAlterar.addActionListener(e -> {
            double renda;
            try {
                renda = Double.parseDouble(txtRenda.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Renda deve ser um número!");
                return;
            }

            Entidade ent = new Entidade(txtCodigo.getText(), txtNome.getText(), renda);
            String msg;
            String msgOk;

            if (btnIncluirAlterar.getText().equals("Incluir")) {
                msg = mediator.incluir(ent);
                msgOk = "Inclusão realizada com sucesso";
            } else {
                msg = mediator.alterar(ent);
                msgOk = "Alteração realizada com sucesso";
            }

            if (msg != null) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                resetarEstadoInicial();
                JOptionPane.showMessageDialog(null, msgOk);
            }
        });

        btnExcluir.addActionListener(e -> {
            String msg = mediator.excluir(txtCodigo.getText());
            if (msg != null) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                resetarEstadoInicial();
                JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!");
            }
        });

        btnCancelar.addActionListener(e -> resetarEstadoInicial());

        btnLimpar.addActionListener(e -> {
            if (txtCodigo.isEnabled()) txtCodigo.setText("");
            txtNome.setText("");
            txtRenda.setText("");
        });
    }

    private void habilitarEdicao(boolean habilitar) {
        txtNome.setEnabled(habilitar);
        txtRenda.setEnabled(habilitar);
    }

    private void resetarEstadoInicial() {
        txtCodigo.setEnabled(true);
        txtCodigo.setText("");
        txtNome.setText("");
        txtRenda.setText("");

        habilitarEdicao(false);

        btnNovo.setEnabled(true);
        btnBuscar.setEnabled(true);
        btnIncluirAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnIncluirAlterar.setText("Incluir");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaExemploCadastro
                    tela = new TelaExemploCadastro
                    ();
            tela.setVisible(true);
        });
    }
}
