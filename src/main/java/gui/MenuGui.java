package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuGui extends JFrame{
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JTable table1;
    private JButton clientesButton;
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
    private JButton salvarButtonCliente;
    private JButton editarButtonCliente;
    private JButton recuarButtonCliente;
    private JTextField pesquisarClienteNifField;
    private JLabel pesquisarClienteNifLabel;
    private JTable table3;
    private JTextField nifField1;
    private JTextField idEmpregadoField;
    private JLabel nifLabel2;
    private JButton salvarButtonReserva;
    private JButton recuarButtonReserva;
    private JTextField pesquisarReservaNifField;
    private JTextField numeroQuartoField;
    private JLabel numeroQuartoLabel;
    private JTextField dataField;
    private JLabel pesquisarReservaNIFLabel;
    private JTable table4;
    private JTextField textField3;
    private JLabel pesquisarFaturaNIF;
    private JButton recuarButtonFaturacao;
    private JTable table5;
    private JTextField nomeField1;
    private JTextField moradaField;
    private JComboBox cargoComboBox;
    private JTextField telefoneField1;
    private JTextField nifField2;
    private JTextField horaEntradaField;
    private JTextField horasaidaField;
    private JPasswordField passwordField;
    private JButton salvarButtonEmpregado;
    private JButton editarButtonEmpregado;
    private JButton recuarButtonEmprgado;
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
    private JButton salvarButtonLimpeza;
    private JButton editarButtonLimpeza;
    private JButton recuarButtonLimpeza;
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


        clientesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                clientePanel.setVisible(true);
            }
        });
        recuarButtonCliente.addMouseListener(new MouseAdapter() {
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
        recuarButtonReserva.addMouseListener(new MouseAdapter() {
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
        recuarButtonFaturacao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                faturacaoPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        recuarButtonEmprgado.addMouseListener(new MouseAdapter() {
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
        recuarButtonLimpeza.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limpezaPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
    }
}


