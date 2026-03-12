/*
* Elabora un programa para diseñar una calculadora como se muestra en la página 72 
* del material "Unidad VII Java.pdf", programar su funcionamiento correcto como calculadora
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Calculadora extends JFrame implements ActionListener {

    private JPanel gui;
    GridBagConstraints gbConstraints = new GridBagConstraints();
    private JTextArea txtInput;
    private JButton btnUno, btnDos, btnTres, btnCuatro, btnCinco, btnSeis, btnSiete, btnOcho, btnNueve, btnCero;
    private JButton btnSuma, btnResta, btnMultiplicacion, btnDivision, btnIgual, btnPunto, btnC;
    
    protected int operation = 0;
    protected double current_number = 0;
    
    public Calculadora() {
        initComponents();
    }

    private void initComponents() {
        this.gui = new JPanel(new BorderLayout(5, 5));
        this.gui.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.txtInput = new JTextArea(2, 20);
        this.txtInput.setEditable(false);
        this.txtInput.setFont(new Font("Arial", Font.BOLD, 24));
        this.gui.add(this.txtInput, BorderLayout.NORTH);

        JFrame frame = new JFrame("Calculadora");
        frame.setContentPane(this.gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panelButtons = new JPanel(new GridBagLayout());

        this.btnC = new JButton("C");
        this.btnC.addActionListener(this);
        this.btnC.setPreferredSize(new Dimension(70, 50));
        this.btnC.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.weightx = 1.0;
        gbConstraints.weighty = 1.0;
        gbConstraints.insets = new Insets(2, 2, 2, 2);
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        panelButtons.add(this.btnC, gbConstraints);

        this.btnDivision = new JButton("/");
        this.btnDivision.addActionListener(this);
        this.btnDivision.setPreferredSize(new Dimension(70, 50));
        this.btnDivision.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        panelButtons.add(this.btnDivision, gbConstraints);

        this.btnMultiplicacion = new JButton("*");
        this.btnMultiplicacion.addActionListener(this);
        this.btnMultiplicacion.setPreferredSize(new Dimension(70, 50));
        this.btnMultiplicacion.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 0;
        panelButtons.add(this.btnMultiplicacion, gbConstraints);

        this.btnResta = new JButton("-");
        this.btnResta.addActionListener(this);
        this.btnResta.setPreferredSize(new Dimension(70, 50));
        this.btnResta.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 3;
        gbConstraints.gridy = 0;
        panelButtons.add(this.btnResta, gbConstraints);

        this.btnSiete = new JButton("7");
        this.btnSiete.addActionListener(this);
        this.btnSiete.setPreferredSize(new Dimension(70, 50));
        this.btnSiete.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        panelButtons.add(this.btnSiete, gbConstraints);

        this.btnOcho = new JButton("8");
        this.btnOcho.addActionListener(this);
        this.btnOcho.setPreferredSize(new Dimension(70, 50));
        this.btnOcho.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 1;
        panelButtons.add(this.btnOcho, gbConstraints);

        this.btnNueve = new JButton("9");
        this.btnNueve.addActionListener(this);
        this.btnNueve.setPreferredSize(new Dimension(70, 50));
        this.btnNueve.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 1;
        panelButtons.add(this.btnNueve, gbConstraints);

        this.btnSuma = new JButton("+");
        this.btnSuma.addActionListener(this);
        this.btnSuma.setPreferredSize(new Dimension(70, 50));
        this.btnSuma.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridheight = 2;
        gbConstraints.gridx = 3;
        gbConstraints.gridy = 1;
        panelButtons.add(this.btnSuma, gbConstraints);

        this.btnCuatro = new JButton("4");
        this.btnCuatro.addActionListener(this);
        this.btnCuatro.setPreferredSize(new Dimension(70, 50));
        this.btnCuatro.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridheight = 1;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 2;
        panelButtons.add(this.btnCuatro, gbConstraints);

        this.btnCinco = new JButton("5");
        this.btnCinco.addActionListener(this);
        this.btnCinco.setPreferredSize(new Dimension(70, 50));
        this.btnCinco.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 2;
        panelButtons.add(this.btnCinco, gbConstraints);

        this.btnSeis = new JButton("6");
        this.btnSeis.addActionListener(this);
        this.btnSeis.setPreferredSize(new Dimension(70, 50));
        this.btnSeis.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 2;
        panelButtons.add(this.btnSeis, gbConstraints);

        this.btnUno = new JButton("1");
        this.btnUno.addActionListener(this);
        this.btnUno.setPreferredSize(new Dimension(70, 50));
        this.btnUno.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 3;
        panelButtons.add(this.btnUno, gbConstraints);

        this.btnDos = new JButton("2");
        this.btnDos.addActionListener(this);
        this.btnDos.setPreferredSize(new Dimension(70, 50));
        this.btnDos.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 1;
        gbConstraints.gridy = 3;
        panelButtons.add(this.btnDos, gbConstraints);

        this.btnTres = new JButton("3");
        this.btnTres.addActionListener(this);
        this.btnTres.setPreferredSize(new Dimension(70, 50));
        this.btnTres.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 3;
        panelButtons.add(this.btnTres, gbConstraints);

        this.btnIgual = new JButton("=");
        this.btnIgual.addActionListener(this);
        this.btnIgual.setPreferredSize(new Dimension(70, 50));
        this.btnIgual.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridheight = 2;
        gbConstraints.gridx = 3;
        gbConstraints.gridy = 3;
        panelButtons.add(this.btnIgual, gbConstraints);

        this.btnCero = new JButton("0");
        this.btnCero.addActionListener(this);
        this.btnCero.setPreferredSize(new Dimension(70, 50));
        this.btnCero.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridheight = 1;
        gbConstraints.gridwidth = 2;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 4;
        panelButtons.add(btnCero, gbConstraints);

        this.btnPunto = new JButton(".");
        this.btnPunto.addActionListener(this);
        this.btnPunto.setPreferredSize(new Dimension(70, 50));
        this.btnPunto.setFont(new Font("Arial", Font.BOLD, 18));
        gbConstraints.gridwidth = 1;
        gbConstraints.gridx = 2;
        gbConstraints.gridy = 4;
        panelButtons.add(this.btnPunto, gbConstraints);

        this.txtInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (96 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '0');
                } else if (97 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '1');
                } else if (98 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '2');
                } else if (99 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '3');
                } else if (100 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '4');
                } else if (101 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '5');
                } else if (102 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '6');
                } else if (103 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '7');
                } else if (104 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '8');
                } else if (105 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '9');
                } else if (106 == e.getKeyCode()) {
                    operation = 4;
                    current_number = Double.parseDouble(txtInput.getText());
                    txtInput.setText("");
                } else if (111 == e.getKeyCode()) {
                    operation = 3;
                    current_number = Double.parseDouble(txtInput.getText());
                    txtInput.setText("");
                } else if (109 == e.getKeyCode()) {
                    operation = 1;
                    current_number = Double.parseDouble(txtInput.getText());
                    txtInput.setText("");
                } else if (107 == e.getKeyCode()) {
                    operation = 2;
                    current_number = Double.parseDouble(txtInput.getText());
                    txtInput.setText("");
                } else if (32 == e.getKeyCode()) {
                    txtInput.setText("");
                } else if (110 == e.getKeyCode()) {
                    txtInput.setText(txtInput.getText() + '.');
                } else if (61 == e.getKeyCode() && operation != 0 || 10 == e.getKeyCode() && operation != 0) {
                    double result = 0;
                    switch (operation) {
                        case 1 -> result = current_number - Double.parseDouble(txtInput.getText());
                        case 2 -> result = current_number + Double.parseDouble(txtInput.getText());
                        case 3 -> result = current_number / Double.parseDouble(txtInput.getText());
                        case 4 -> result = current_number * Double.parseDouble(txtInput.getText());
                    }
                    txtInput.setText(Double.toString(result));
                    operation = 0;
                }

            }
        });

        this.gui.add(panelButtons, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (btnUno == e.getSource()) {
            txtInput.setText(txtInput.getText() + '1');
        } else if (btnDos == e.getSource()) {
            txtInput.setText(txtInput.getText() + '2');
        } else if (btnTres == e.getSource()) {
            txtInput.setText(txtInput.getText() + '3');
        } else if (btnCuatro == e.getSource()) {
            txtInput.setText(txtInput.getText() + '4');
        } else if (btnCinco == e.getSource()) {
            txtInput.setText(txtInput.getText() + '5');
        } else if (btnSeis == e.getSource()) {
            txtInput.setText(txtInput.getText() + '6');
        } else if (btnSiete == e.getSource()) {
            txtInput.setText(txtInput.getText() + '7');
        } else if (btnOcho == e.getSource()) {
            txtInput.setText(txtInput.getText() + '8');
        } else if (btnNueve == e.getSource()) {
            txtInput.setText(txtInput.getText() + '9');
        } else if (btnCero == e.getSource()) {
            txtInput.setText(txtInput.getText() + '0');
        } else if (btnMultiplicacion == e.getSource()) {
            if (!txtInput.getText().isEmpty()) {
                operation = 4;
                current_number = Double.parseDouble(txtInput.getText());
                txtInput.setText("");
            }
        } else if (btnDivision == e.getSource()) {
            if (!txtInput.getText().isEmpty()) {
                operation = 3;
                current_number = Double.parseDouble(txtInput.getText());
                txtInput.setText("");
            }
        } else if (btnResta == e.getSource()) {
            if (!txtInput.getText().isEmpty()) {
                operation = 1;
                current_number = Double.parseDouble(txtInput.getText());
                txtInput.setText("");
            }
        } else if (btnSuma == e.getSource()) {
            if (!txtInput.getText().isEmpty()) {
                operation = 2;
                current_number = Double.parseDouble(txtInput.getText());
                txtInput.setText("");
            }
        } else if (btnC == e.getSource()) {
            txtInput.setText("");
            operation = 0;
            current_number = 0;
        } else if (btnPunto == e.getSource()) {
            if (!txtInput.getText().contains(".")) {
                txtInput.setText(txtInput.getText() + '.');
            }
        } else if (btnIgual == e.getSource() && operation != 0) {
            if (!txtInput.getText().isEmpty()) {
                double result = 0;
                switch (operation) {
                    case 1 -> result = current_number - Double.parseDouble(txtInput.getText());
                    case 2 -> result = current_number + Double.parseDouble(txtInput.getText());
                    case 3 -> result = current_number / Double.parseDouble(txtInput.getText());
                    case 4 -> result = current_number * Double.parseDouble(txtInput.getText());
                }
                txtInput.setText(Double.toString(result));
                operation = 0;
                current_number = 0;
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(Calculadora::new);
    }
}