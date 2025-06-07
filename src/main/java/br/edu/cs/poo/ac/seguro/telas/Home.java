package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends javax.swing.JFrame {

    public Home() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        Color lilasClaro = Color.decode("#E6CCF5");
        getContentPane().setBackground(lilasClaro);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Seguros");
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout(10, 20));
        mainPanel.setOpaque(false);
        mainPanel.setBackground(new Color(0, 0, 0, 0));


        JLabel titleLabel = new JLabel("SISTEMA DE SEGUROS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 70, 140));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));

        JButton btnPessoa = createMenuButton("CRUD Segurado Pessoa");
        JButton btnEmpresa = createMenuButton("CRUD Segurado Empresa");
        JButton btnApolice = createMenuButton("Inclusão de Apólice");
        JButton btnSinistro = createMenuButton("Inclusão de Sinistro");

        btnPessoa.addActionListener(e -> openScreen("TelaSeguradoPessoa"));
        btnEmpresa.addActionListener(e -> openScreen("TelaSeguradoEmpresa"));
        btnApolice.addActionListener(e -> openScreen("TelaApolice"));
        btnSinistro.addActionListener(e -> openScreen("TelaSinistro"));

        buttonPanel.add(btnPessoa);
        buttonPanel.add(btnEmpresa);
        buttonPanel.add(btnApolice);
        buttonPanel.add(btnSinistro);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        pack();
        setSize(500, 400);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    private void openScreen(String screenName) {
        this.dispose();
        switch(screenName) {
            case "TelaSeguradoPessoa":
                new TelaSeguradoPessoa().setVisible(true);
                break;
            case "TelaSeguradoEmpresa":
                new TelaSeguradoEmpresa().setVisible(true);
                break;
            case "TelaApolice":
                new TelaApolice().setVisible(true);
                break;
            case "TelaSinistro":
                new TelaSinistro().setVisible(true);
                break;
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }
}