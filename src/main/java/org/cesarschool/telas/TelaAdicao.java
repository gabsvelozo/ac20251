package org.cesarschool.telas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaAdicao {
    private JPanel mainPanel;
    private JTextField txtPrimeiroNumero;
    private JTextField txtSegundoNumero;
    private JTextField txtResultado;
    private JButton btnSomar;
    private JButton btnLimpar;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tela Adição");
            frame.setContentPane(new TelaAdicao().mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public TelaAdicao() {
        btnSomar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double n1 = Double.parseDouble(txtPrimeiroNumero.getText());
                    double n2 = Double.parseDouble(txtSegundoNumero.getText());
                    txtResultado.setText(String.valueOf(n1 + n2));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Digite números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtPrimeiroNumero.setText("");
                txtSegundoNumero.setText("");
                txtResultado.setText("");
            }
        });
    }
}
