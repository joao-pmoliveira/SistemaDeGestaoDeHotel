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
import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

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
    private JTextField clienteNomeField;
    private JLabel nomeLabel;
    private JTextField clienteTelefoneField;
    private JLabel telefoneLabel;
    private JTextField clienteNIFField;
    private JLabel nifLabel;
    private JButton buttonAdicionarCliente;
    private JButton recuarButtonCliente;
    private JTextField pesquisarClienteNifField;
    private JLabel pesquisarClienteNifLabel;
    private JTable table3;
    private JTextField clienteNIFReservaField;
    private JTextField empregadoIDReservaField;
    private JLabel nifLabel2;
    private JButton buttonAdicionarReserva;
    private JButton recuarButtonReserva;
    private JTextField pesquisarReservaClienteNIFField;
    private JTextField quartosIDReservaField;
    private JLabel numeroQuartoLabel;
    private JTextField dataInicialReservaField;
    private JLabel pesquisarReservaNIFLabel;
    private JTable table4;
    private JTextField pesquisarFaturaNifField;
    private JLabel pesquisarFaturaNifLabel;
    private JButton recuarButtonFaturacao;
    private JTable table5;
    private JTextField empregadoNomeField;
    private JTextField empregadoMoradaField;
    private JComboBox cargoComboBox;
    private JTextField empregadoTelefoneField;
    private JTextField empregadoNIFField;
    private JTextField empregadoHoraEntradaField;
    private JTextField empregadoHoraSaidaField;
    private JPasswordField empregadoPasswordField;
    private JButton buttonAdicionarEmpregado;
    private JButton recuarButtonEmpregado;
    private JLabel cargoField;
    private JLabel moradaLabel;
    private JLabel telefoneLabel1;
    private JLabel nifLabel1;
    private JLabel horaEntradaLabel;
    private JLabel horaSaidaLabel;
    private JLabel passwordLabel;
    private JTable table6;
    private JTextField empregadoIDField;
    private JTextField quartoIDField;
    private JTextField dataHoraField;
    private JTextField pesquisarEmpregadoField;
    private JTextField pesquisarQuartoField;
    private JTextField pesquisarDataField;
    private JButton buttonAdicionarRegistoLimpeza;
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
    private JTextField dataFinalReservaField;
    private JLabel dataFinalLabel;
    private JTextField empregadoSalarioField;
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
        buttonAdicionarReserva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String resultado = "";
                try{
                    int clienteNIF = Integer.parseInt(clienteNIFReservaField.getText());
                    int empregadoID = Integer.parseInt(empregadoIDReservaField.getText());
                    String[] quartosInseridos = quartosIDReservaField.getText().split(",");

                    HashSet<Integer> quartosIDs = new HashSet<>();
                    for(String quartoInserido : quartosInseridos){
                        int quartoID = Integer.parseInt(quartoInserido.trim());
                        quartosIDs.add(quartoID);
                    }

                    String dataInput = dataInicialReservaField.getText();
                    dataInput = dataInput.trim();
                    String[] data = dataInput.split("-");
                    int ano = Integer.parseInt(data[0]);
                    int mes = Integer.parseInt(data[1]);
                    int dia = Integer.parseInt(data[2]);
                    LocalDate dataInicial = LocalDate.of(ano, mes, dia);
                    dataInput = dataFinalReservaField.getText();
                    data = dataInput.split("-");
                    ano = Integer.parseInt(data[0]);
                    mes = Integer.parseInt(data[1]);
                    dia = Integer.parseInt(data[2]);
                    LocalDate dataFinal = LocalDate.of(ano, mes, dia);
                    HashSet<LocalDate> datas = GestorDeDatas.obterDatasEntreDuasDatas(dataInicial, dataFinal);

                    gestorDeReserva.adicionarReserva(clienteNIF, empregadoID, datas, quartosIDs, gestorDeBaseDeDados);
                    resultado = "Reserva adicionada com sucesso!";
                } catch (NumberFormatException exception){
                    resultado = "Erro no parsing";
                } catch (DateTimeException exception){
                    resultado = "Erro a lidar com as datas";
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                }
                JOptionPane.showMessageDialog(GUI.frame, resultado);
            }
        });
        buttonAdicionarCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String resultado = "";
                try{
                    int clienteNIF = Integer.parseInt(clienteNIFReservaField.getText());
                    System.out.println(clienteNIF);
                    String clienteNome = clienteNomeField.getText().trim();
                    int clienteTelefone = Integer.parseInt(clienteTelefoneField.getText());
                    gestorDeClientes.adicionarCliente(clienteNIF, clienteNome, clienteTelefone, gestorDeBaseDeDados);
                    resultado = "Cliente adicionado com sucesso!";
                } catch (NumberFormatException exception){
                    resultado = "Erro no parsing";
                } catch (DateTimeException exception){
                    resultado = "Erro a lidar com as datas";
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                }
                JOptionPane.showMessageDialog(GUI.frame, resultado);
            }
        });

        buttonAdicionarRegistoLimpeza.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String resultado = "";

                try{
                    String dataHoraInput = dataHoraField.getText().trim();

                    String[] data = dataHoraInput.split("-");
                    int ano = Integer.parseInt(data[0]);
                    int mes = Integer.parseInt(data[1]);
                    int dia = Integer.parseInt(data[2]);

                    LocalDate dataRegisto = LocalDate.of(ano, mes, dia);

                    int quartoID = Integer.parseInt(quartoIDField.getText().trim());
                    int empregadoID = Integer.parseInt(empregadoIDField.getText().trim());

                    gestorDeLimpeza.adicionarRegisto(dataRegisto.toString(), quartoID, empregadoID, gestorDeBaseDeDados);
                    resultado = "Registo adicionado com sucesso!";
                } catch (NumberFormatException exception){
                    resultado = "Erro no parsing";
                } catch (DateTimeException exception){
                    resultado = "Erro a lidar com as datas";
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                }
                JOptionPane.showMessageDialog(GUI.frame, resultado);
            }
        });


        buttonAdicionarEmpregado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String resultado = "";
                try{
                    String empregadoNome = empregadoNomeField.getText().trim();
                    int empregadoCargoID = 1;
                    String empregadoMorada = empregadoMoradaField.getText().trim();
                    int empregadoTelefone = Integer.parseInt(empregadoTelefoneField.getText().trim());
                    int empregadoNIF = Integer.parseInt(empregadoNIFField.getText().trim());
                    float empregadoSalario= Float.parseFloat(empregadoSalarioField.getText().trim());
                    String horaEntradaInput = empregadoHoraEntradaField.getText().trim();
                    String horaSaidaInput = empregadoHoraSaidaField.getText().trim();
                    String[] horaInput = horaEntradaInput.split(":");
                    int horas = Integer.parseInt(horaInput[0]);
                    int minutos = Integer.parseInt(horaInput[1]);
                    LocalTime empregadoHoraEntrada = LocalTime.of(horas, minutos);
                    horaInput = horaSaidaInput.split(":");
                    horas = Integer.parseInt(horaInput[0]);
                    minutos = Integer.parseInt(horaInput[1]);
                    LocalTime empregadoHoraSaida = LocalTime.of(horas, minutos);
                    String empregadoPassword = String.valueOf(empregadoPasswordField.getPassword());

                    gestorDeEmpregados.adicionarEmpregado(empregadoNome, empregadoCargoID, empregadoMorada, empregadoTelefone,
                            empregadoNIF, empregadoSalario, empregadoHoraEntrada, empregadoHoraSaida, empregadoPassword, gestorDeBaseDeDados);
                    resultado = "Empregado adicionado com sucesso!";
                } catch (NumberFormatException exception){
                    resultado = "Erro no parsing";
                } catch (DateTimeException exception){
                    resultado = "Erro a lidar com as datas";
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                }
                JOptionPane.showMessageDialog(GUI.frame, resultado);
            }
        });
    }
}


