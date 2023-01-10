package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuGui extends JFrame{
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JTable table1;
    private JButton hospedesButton;
    private JButton limpezasButton;
    private JButton empregadosButton;
    private JButton faturacaoButton;
    private JPanel reservaPanel;
    private JPanel clientePanel;
    private JPanel limpezaPanel;
    private JPanel empregadoPanel;
    private JPanel faturacaoPanel;
    private JButton reservasButton;
    private JTable table2;
    private JTextField nomeField;
    private JLabel nomeLabel;
    private JTextField telefoneField;
    private JLabel telefoneLabel;
    private JTextField nifField;
    private JLabel nifLabel;
    private JButton salvarButton;
    private JButton editarButton;
    private JButton recuarButton;
    private JTextField pesquisarClienteNifField;
    private JLabel pesquisarClienteNifLabel;
    private JTable table3;
    private JTextField nifField1;
    private JTextField idEmpregadoField;
    private JLabel nifLabel2;
    private JButton salvarButton1;
    private JButton recuarButton1;
    private JTextField pesquisarReservaNifField;
    private JTextField numeroQuartoField;
    private JLabel numeroQuartoLabel;
    private JTextField dataField;
    private JLabel pesquisarReservaNIFLabel;
    private JTable table4;
    private JTextField textField3;
    private JLabel pesquisarFaturaNIF;
    private JButton recuarButton2;
    private JTable table5;
    private JTextField nomeField1;
    private JTextField moradaField;
    private JComboBox cargoComboBox;
    private JTextField telefoneField1;
    private JTextField nifField2;
    private JTextField horaEntradaField;
    private JTextField horasaidaField;
    private JPasswordField passwordField;
    private JButton salvarButton2;
    private JButton editarButton1;
    private JButton recuarButton3;
    private JLabel cargoField;
    private JLabel moradaLabel;
    private JLabel telefoneLabel1;
    private JLabel nifLabel1;
    private JLabel horaEntradaLabel;
    private JLabel horaSaidaLabel;
    private JLabel passwordLabel;
    private JTable table6;
    private JTextField idEmpregadoField1;
    private JTextField quartoLabelField;
    private JTextField dataHoraField;
    private JTextField pesquisarEmpregadoField;
    private JTextField pesquisarQuartoField;
    private JTextField pesquisarDataField;
    private JButton salvarButton3;
    private JButton editarButton2;
    private JButton recuarButton4;
    private JLabel pesquisarDataLabel;
    private JLabel pesquisarQuartoLabel;
    private JLabel pesquisarEmpregadoLabel;
    private JLabel idEmpregadoLabel;
    private JLabel quartoLabel;
    private JLabel dataHoraLabel;
    private JLabel idEmpregadoLabel1;
    private JLabel dataLabel;
    private JLabel nomeLabel1;

    public MenuGui(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setSize(dim.width-this.getSize().width +1 , dim.height-this.getSize().height);// full screen
        this.setLocation(dim.width/3-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        this.setSize(1000,600);


        hospedesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                clientePanel.setVisible(true);
            }
        });
        recuarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clientePanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        reservasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                reservaPanel.setVisible(true);
            }
        });
        recuarButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                reservaPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        faturacaoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                faturacaoPanel.setVisible(true);
            }
        });
        recuarButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                faturacaoPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        recuarButton3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                empregadoPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        empregadosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                empregadoPanel.setVisible(true);
            }
        });
        limpezasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                limpezaPanel.setVisible(true);
            }
        });
        recuarButton4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limpezaPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
    }
}


