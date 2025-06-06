package org.cesarschool.telas;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CRUDSeguradoPessoa {
    private JPanel panelMain;
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtRenda;
    private JTextField txtEndereco;
    private JTextField txtNascimento;
    private JTextField txtBonus;
    private JTextField txtLogradouro;
    private JTextField txtCep;
    private JTextField txtNumero;
    private JTextField txtComplemento;
    private JTextField txtPais;
    private JTextField txtEstado;
    private JTextField txtCidade;
    private JButton btnBuscar;
    private JButton btnIncluir;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    private SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CRUDSeguradoPessoa() {
        btnBuscar.addActionListener(e -> buscar());
        btnIncluir.addActionListener(e -> incluir());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());
    }

    private void buscar() {
        String cpf = txtCpf.getText();
        SeguradoPessoa seg = mediator.buscarSeguradoPessoa(cpf);
        if (seg == null) {
            JOptionPane.showMessageDialog(null, "Segurado não encontrado.");
            return;
        }

        txtNome.setText(seg.getNome());
        txtEndereco.setText(seg.getEndereco().toString());
        txtRenda.setText(String.valueOf(seg.getRenda()));
        txtNascimento.setText(seg.getDataNascimento().format(formatter));
        txtBonus.setText(String.valueOf(seg.getBonus()));
    }

    private void incluir() {
        SeguradoPessoa seg = montarSegurado();
        String msg = mediator.incluirSeguradoPessoa(seg);
        mostrarMensagem(msg);
    }

    private void alterar() {
        SeguradoPessoa seg = montarSegurado();
        String msg = mediator.alterarSeguradoPessoa(seg);
        mostrarMensagem(msg);
    }

    private void excluir() {
        String cpf = txtCpf.getText();
        String msg = mediator.excluirSeguradoPessoa(cpf);
        mostrarMensagem(msg);
        if (msg == null) limpar();
    }

    private void limpar() {
        txtCpf.setText("");
        txtNome.setText("");
        txtEndereco.setText("");
        txtRenda.setText("");
        txtNascimento.setText("");
        txtBonus.setText("");
    }

    private SeguradoPessoa montarSegurado() {
        String nome = txtNome.getText();
        Endereco endereco = new Endereco(
                txtLogradouro.getText(),
                txtCep.getText(),
                txtNumero.getText(),
                txtComplemento.getText(),
                txtPais.getText(),
                txtEstado.getText(),
                txtCidade.getText()
        );
        LocalDate nascimento = LocalDate.parse(txtNascimento.getText(), formatter);
        String cpf = txtCpf.getText();
        double renda = Double.parseDouble(txtRenda.getText());
        BigDecimal bonus = new BigDecimal(txtBonus.getText());

        return new SeguradoPessoa(nome, endereco, nascimento, bonus, cpf, renda);
    }

    private void mostrarMensagem(String msg) {
        if (msg == null) {
            JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CRUD Segurado Pessoa");
        frame.setContentPane(new CRUDSeguradoPessoa().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
