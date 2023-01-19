package gui;

import basededados.GestorDeBaseDeDados;
import cliente.GestorDeClientes;
import empregado.GestorDeEmpregados;
import limpeza.GestorDeLimpeza;
import quarto.GestorDeQuartos;
import reserva.GestorDeReserva;
import utils.GestorDeDatas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MenuGui extends JFrame{
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JTable table1;
    private JButton clienteButton;
    private JButton limpezaButton;
    private JButton empregadoButton;
    private JButton faturacaoButton;
    private JPanel reservaPanel;
    private JPanel clientePanel;
    private JPanel limpezaPanel;
    private JPanel empregadoPanel;
    private JPanel faturacaoPanel;
    private JButton reservaButton;
    private JTable table2;
    private JTextField nomeField;
    private JLabel nomeLabel;
    private JTextField telefoneField;
    private JLabel telefoneLabel;
    private JTextField nifField;
    private JLabel nifLabel;
    private JButton guardarButtonCliente;
    private JButton recuarButtonCliente;
    private JTextField pesquisarClienteNifField;
    private JLabel pesquisarClienteNifLabel;
    private JTable table3;
    private JTextField nifField1;
    private JTextField idEmpregadoField;
    private JLabel nifLabel2;
    private JButton guardarButtonReserva;
    private JButton recuarButtonReserva;
    private JTextField pesquisarReservaNifField;
    private JTextField numeroQuartoField;
    private JLabel numeroQuartoLabel;
    private JTextField dataInicialField;
    private JLabel pesquisarReservaNIFLabel;
    private JTable table4;
    private JTextField pesquisarFaturaNifField;
    private JLabel pesquisarFaturaNifLabel;
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
    private JButton guardarButtonEmpregado;
    private JButton recuarButtonEmpregado;
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
    private JButton guardarButtonLimpeza;
    private JButton recuarButtonLimpeza;
    private JLabel pesquisarDataLabel;
    private JLabel pesquisarQuartoLabel;
    private JLabel pesquisarEmpregadoLabel;
    private JLabel idEmpregadoLabel;
    private JLabel quartoLabel;
    private JLabel dataHoraLabel;
    private JLabel idEmpregadoLabel1;
    private JLabel dataInicialLabel;
    private JLabel nomeLabel1;
    private JTextField dataFinalField;
    private JLabel dataFinalLabel;
    private JTextField salarioField;
    private JLabel salarioLabel;
    private JButton pesquisarReservaNifButton;
    private JButton pesquisarClienteNifButton;
    private JButton pesquisarLimpezaButton;
    private JButton pesquisarFaturaNIFButton;

    public MenuGui(String title, GestorDeBaseDeDados gestorDeBaseDeDados) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setSize(dim.width-this.getSize().width +1 , dim.height-this.getSize().height);// full screen
        this.setLocation(dim.width/3-this.getSize().width/2, dim.height/3-this.getSize().height/2);
        this.setSize(1000,600);

        GestorDeClientes gestorDeClientes = new GestorDeClientes();
        GestorDeEmpregados gestorDeEmpregados = new GestorDeEmpregados();
        GestorDeLimpeza gestorDeLimpeza = new GestorDeLimpeza();
        GestorDeQuartos gestorDeQuartos = new GestorDeQuartos();
        GestorDeReserva gestorDeReserva = new GestorDeReserva();
        GestorDeDatas gestorDeDatas = new GestorDeDatas();

        clienteButton.addMouseListener(new MouseAdapter() {
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
        reservaButton.addMouseListener(new MouseAdapter() {
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
        recuarButtonEmpregado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                empregadoPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        empregadoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                empregadoPanel.setVisible(true);
            }
        });
        limpezaButton.addMouseListener(new MouseAdapter() {
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
        guardarButtonReserva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int nif = Integer.parseInt(nifField1.getText()); //NIF

                String quartosInseridos = numeroQuartoField.getText(); //QUARTOS
                String[] quartos = quartosInseridos.split(",");
                HashSet<Integer> quartosAReservar = new HashSet<>();
                for(String quarto : quartos){
                    quarto = quarto.trim();
                    int quartoID = Integer.parseInt(quarto);
                    quartosAReservar.add(quartoID);
                }

                //int[] quartosIDs = quartosAReservar.stream().mapToInt(i -> i).toArray();

                String dataInicalInput = dataInicialField.getText(); //DATAS
                Date dataInicial = GestorDeDatas.validarData(dataInicalInput);
                String dataFinalInput = dataFinalField.getText();
                Date dataFinal = GestorDeDatas.validarData(dataFinalInput);

                HashSet<LocalDate> datas = GestorDeDatas.obterDatasEntreDuasDatas(
                        GestorDeDatas.converterDateParaLocalDate(dataInicial),
                        GestorDeDatas.converterDateParaLocalDate(dataFinal));

                int id = Integer.parseInt(idEmpregadoField.getText()); //ID
                gestorDeReserva.adicionarReserva(nif, id, datas, quartosAReservar, gestorDeBaseDeDados);
            }
        });
        guardarButtonCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int clienteNif = Integer.parseInt(nifField.getText());// NIF
                if(clienteNif < 1){
                    System.out.println("NIF do cliente inválido!");
                    return;
                }

                String clienteNome = nomeField.getText(); //NOME
                if(clienteNome.isEmpty()){
                    System.out.println("Nome inválido!");
                    return;
                }

                int clienteTelefone = Integer.parseInt(telefoneField.getText()); //TELEFONE

                gestorDeClientes.adicionarCliente(clienteNif, clienteNome, clienteTelefone, gestorDeBaseDeDados);

            }
        });


        guardarButtonLimpeza.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String dataRegistoInput = dataHoraField.getText(); //DATA REGISTO
                Date dataRegisto = GestorDeDatas.validarData(dataRegistoInput);
                if(dataRegisto == null) {
                    System.out.println("Data do Registo inválida!");
                    return;
                }



                int idQuarto = Integer.parseInt(quartoLabelField.getText()); //QUARTO ID
                if(idQuarto < 1) {
                    JOptionPane.showMessageDialog(GUI.frame, "Quarto ID inválido!");
                    return;
                }

                int idEmpregado = Integer.parseInt(idEmpregadoField1.getText()); //EMPREGADO ID
                if(idEmpregado< 1) {
                    JOptionPane.showMessageDialog(GUI.frame, "Empregado inválido!");
                    return;
                }

                gestorDeLimpeza.adicionarRegisto(dataRegistoInput, idQuarto, idEmpregado, gestorDeBaseDeDados);
            }
        });


        guardarButtonEmpregado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String empregadoNome = nomeField1.getText(); //NOME
                if(empregadoNome.isEmpty()){
                    System.out.println("Nome inválido!");
                }

                int empregadoCargo = Integer.parseInt(cargoField.getText()); //CARGO
                if(empregadoCargo < 1 || empregadoCargo > 3 ){
                    System.out.println("Cargo inválido!");
                    return;
                }

                String empregadoMorada = moradaField.getText();; //MORADA
                if(empregadoMorada.isEmpty()){
                    System.out.println("Morada inválida!");
                    return;
                }

                int telefone = Integer.parseInt(telefoneField1.getText()); //MUDAR.
                /*if(telefone.matches()) {
                    System.out.println("Telefone inválido");
                    return;
                }
                int empregadoTelefone = Integer.parseInt(telefone); //TELEFONE */


                int empregadoNif = Integer.parseInt(nifField2.getText()); //NIF
                if(empregadoNif < 1) {
                    System.out.println("Empregado ID inválido!");
                    return;
                }

                float empregadoSalario = Float.parseFloat(salarioField.getText()); //SALARIO
                if (empregadoSalario <= 0f) {
                    System.out.println("Salário inválido");
                    return;
                }

                String horaEntrada = horaEntradaField.getText(); //HORA ENTRADA
                //FALTA LIDAR COM ERRO



                String horaSaida = horasaidaField.getText(); //HORA SAIDA
                //FALTA LIDAR COM ERRO

                String password = Arrays.toString(passwordField.getPassword()); //PASSWORD
                //FALTA LIDAR COM ERRO

               // gestorDeEmpregados.adicionarEmpregado(empregadoNome, empregadoCargo, empregadoMorada, telefone, empregadoNif, empregadoSalario, horaEntrada, horaSaida, password, gestorDeBaseDeDados);
            }
        });
    }
}


