package gui;

import basededados.GestorDeBaseDeDados;
import cliente.Cliente;
import cliente.GestorDeClientes;
import empregado.GestorDeEmpregados;
import limpeza.GestorDeLimpeza;
import limpeza.RegistoDeLimpeza;
import quarto.GestorDeQuartos;
import reserva.GestorDeReserva;
import reserva.Reserva;
import utils.GestorDeDatas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MenuGui extends JFrame{
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JTable table1;
    private JButton buttonClienteTab;
    private JButton buttonLimpezaTab;
    private JButton buttonEmpregadoTab;
    private JButton buttonFaturacaoTab;
    private JPanel reservaPanel;
    private JPanel clientePanel;
    private JPanel limpezaPanel;
    private JPanel empregadoPanel;
    private JPanel faturacaoPanel;
    private JButton buttonReservaTab;
    private JTable table2;
    private JTextField clienteNomeClienteField;
    private JLabel nomeLabel;
    private JTextField clienteTelefoneClienteField;
    private JLabel telefoneLabel;
    private JTextField clienteNIFClienteField;
    private JLabel nifLabel;
    private JButton buttonAdicionarCliente;
    private JButton buttonRecuarDeCliente;
    private JTextField clienteNIFClienteProcuraField;
    private JLabel pesquisarClienteNifLabel;
    private JTable table3;
    private JTextField clienteNIFReservaField;
    private JTextField empregadoIDReservaField;
    private JLabel nifLabel2;
    private JButton buttonAdicionarReserva;
    private JButton buttonRecuarDeReserva;
    private JTextField clienteNIFReservaProcuraField;
    private JTextField quartosIDReservaField;
    private JLabel numeroQuartoLabel;
    private JTextField dataInicialReservaField;
    private JLabel pesquisarReservaNIFLabel;
    private JTable table4;
    private JTextField reservaIDFaturaProcuraField;
    private JLabel pesquisarFaturaNifLabel;
    private JButton recuarButtonFaturacao;
    private JTable table5;
    private JTextField empregadoNomeEmpregadoField;
    private JTextField empregadoMoradaEmpregadoField;
    private JComboBox cargoComboBox;
    private JTextField empregadoTelefoneEmpregadoField;
    private JTextField empregadoNIFEmpregadoField;
    private JTextField empregadoHoraEntradaEmpregadoField;
    private JTextField empregadoHoraSaidaEmpregadoField;
    private JPasswordField empregadoPasswordEmpregadoField;
    private JButton buttonAdicionarEmpregado;
    private JButton buttonRecuarDeEmpregado;
    private JLabel cargoField;
    private JLabel moradaLabel;
    private JLabel telefoneLabel1;
    private JLabel nifLabel1;
    private JLabel horaEntradaLabel;
    private JLabel horaSaidaLabel;
    private JLabel passwordLabel;
    private JTable table6;
    private JTextField empregadoIDLimpezaField;
    private JTextField quartoIDLimpezaField;
    private JTextField dataLimpezaField;
    private JTextField empregadoIDLimpezaProcuraField;
    private JTextField quartoIDLimpezaProcuraField;
    private JTextField dataLimpezaProcuraField;
    private JButton buttonAdicionarRegistoLimpeza;
    private JButton buttonRecuarDeLimpeza;
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
    private JTextField empregadoSalarioEmpregadoField;
    private JLabel salarioLabel;
    private JButton buttonClienteNIFProcuraReserva;
    private JButton buttonClienteNIFProcuraCliente;
    private JButton buttonDataProcuraLimpeza;
    private JButton pesquisarFaturaNIFButton;
    private JButton buttonQuartoIDProcuraLimpeza;
    private JButton buttonEmpregadoIDProcuraLimpeza;

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

        buttonClienteTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                clientePanel.setVisible(true);
            }
        });
        buttonRecuarDeCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clientePanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        buttonReservaTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                reservaPanel.setVisible(true);
            }
        });
        buttonRecuarDeReserva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                reservaPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        buttonFaturacaoTab.addMouseListener(new MouseAdapter() {
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
        buttonRecuarDeEmpregado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                empregadoPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        buttonEmpregadoTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                empregadoPanel.setVisible(true);
            }
        });
        buttonLimpezaTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuPanel.setVisible(false);
                limpezaPanel.setVisible(true);
            }
        });
        buttonRecuarDeLimpeza.addMouseListener(new MouseAdapter() {
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
                    String clienteNIFInput = clienteNIFReservaField.getText().trim();
                    String empregadoIDInput = empregadoIDReservaField.getText().trim();
                    String quartosIDInput = quartosIDReservaField.getText().trim();
                    String dataInicialInput = dataInicialReservaField.getText().trim();
                    String dataFinalInput = dataFinalReservaField.getText().trim();

                    if(clienteNIFInput.isEmpty() || empregadoIDInput.isEmpty() || quartosIDInput.isEmpty() ||
                        dataInicialInput.isEmpty() || dataFinalInput.isEmpty())
                        throw new InvalidParameterException("Campo(s) Vazio(s)!");

                    int clienteNIF = Integer.parseInt(clienteNIFInput);
                    int empregadoID = Integer.parseInt(empregadoIDInput);
                    String[] quartosInseridos = quartosIDInput.split(",");

                    HashSet<Integer> quartosIDs = new HashSet<>();
                    for(String quartoInserido : quartosInseridos){
                        int quartoID = Integer.parseInt(quartoInserido.trim());
                        quartosIDs.add(quartoID);
                    }

                    String[] data = dataInicialInput.split("-");
                    int ano = Integer.parseInt(data[0]);
                    int mes = Integer.parseInt(data[1]);
                    int dia = Integer.parseInt(data[2]);
                    LocalDate dataInicial = LocalDate.of(ano, mes, dia);

                    data = dataFinalInput.split("-");
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
                    String clienteNIFInput = clienteNIFClienteField.getText().trim();
                    String clienteNomeInput = clienteNomeClienteField.getText().trim();
                    String clienteTelefoneInput = clienteTelefoneClienteField.getText().trim();

                    if(clienteNIFInput.isEmpty() || clienteNomeInput.isEmpty() || clienteTelefoneInput.isEmpty())
                        throw new InvalidParameterException("Campo(s) Vazio(s)!");

                    int clienteNIF = Integer.parseInt(clienteNIFInput);
                    int clienteTelefone = Integer.parseInt(clienteTelefoneInput);

                    gestorDeClientes.adicionarCliente(clienteNIF, clienteNomeInput, clienteTelefone, gestorDeBaseDeDados);

                    resultado = "Cliente adicionado com sucesso!";
                } catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                } catch (DateTimeException exception){
                    resultado = "Erro a lidar com as datas" + "\n" + exception.getMessage();
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
                    String dataHoraInput = dataLimpezaField.getText().trim();

                    String[] data = dataHoraInput.split("-");
                    int ano = Integer.parseInt(data[0]);
                    int mes = Integer.parseInt(data[1]);
                    int dia = Integer.parseInt(data[2]);

                    LocalDate dataRegisto = LocalDate.of(ano, mes, dia);

                    int quartoID = Integer.parseInt(quartoIDLimpezaField.getText().trim());
                    int empregadoID = Integer.parseInt(empregadoIDLimpezaField.getText().trim());

                    gestorDeLimpeza.adicionarRegisto(dataRegisto.toString(), quartoID, empregadoID, gestorDeBaseDeDados);
                    resultado = "Registo adicionado com sucesso!";
                } catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                } catch (DateTimeException exception){
                    resultado = "Erro a lidar com as datas" + "\n" + exception.getMessage();
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
                    String empregadoNomeInput = empregadoNomeEmpregadoField.getText().trim();
                    String empregadoCargoIDInput;
                    String empregadoTelefoneInput = empregadoTelefoneEmpregadoField.getText().trim();
                    String empregadoNIFInput = empregadoNIFEmpregadoField.getText().trim();
                    String empregadoSalarioInput = empregadoSalarioEmpregadoField.getText().trim();
                    String empregadoMoradaInput = empregadoMoradaEmpregadoField.getText().trim();
                    String empregadoHoraEntradaInput = empregadoHoraEntradaEmpregadoField.getText().trim();
                    String empregadoHoraSaidaInput = empregadoHoraSaidaEmpregadoField.getText().trim();
                    String empregadoPasswordInput = String.valueOf(empregadoPasswordEmpregadoField.getPassword());

                    if( empregadoNomeInput.isEmpty() || empregadoTelefoneInput.isEmpty() || empregadoNIFInput.isEmpty() ||
                        empregadoSalarioInput.isEmpty() || empregadoMoradaInput.isEmpty() || empregadoHoraEntradaInput.isEmpty() ||
                        empregadoHoraSaidaInput.isEmpty() || empregadoPasswordInput.isEmpty())
                        throw new InvalidParameterException("Campo(s) Vazio(s)!");

                    int empregadoCargoID = 1;
                    int empregadoTelefone = Integer.parseInt(empregadoTelefoneInput);
                    int empregadoNIF = Integer.parseInt(empregadoNIFInput);
                    float empregadoSalario= Float.parseFloat(empregadoSalarioInput);

                    String[] horaInput = empregadoHoraEntradaInput.split(":");
                    int horas = Integer.parseInt(horaInput[0]);
                    int minutos = Integer.parseInt(horaInput[1]);
                    LocalTime empregadoHoraEntrada = LocalTime.of(horas, minutos);

                    horaInput = empregadoHoraSaidaInput.split(":");
                    horas = Integer.parseInt(horaInput[0]);
                    minutos = Integer.parseInt(horaInput[1]);
                    LocalTime empregadoHoraSaida = LocalTime.of(horas, minutos);

                    gestorDeEmpregados.adicionarEmpregado(empregadoNomeInput, empregadoCargoID, empregadoMoradaInput, empregadoTelefone,
                            empregadoNIF, empregadoSalario, empregadoHoraEntrada, empregadoHoraSaida, empregadoPasswordInput, gestorDeBaseDeDados);
                    resultado = "Empregado adicionado com sucesso!";
                } catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                } catch (DateTimeException exception){
                    resultado = "Erro a lidar com as datas" + "\n" + exception.getMessage();
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                }
                JOptionPane.showMessageDialog(GUI.frame, resultado);
            }
        });
        buttonClienteNIFProcuraReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = "";
                try{
                    String clienteNIFInput = clienteNIFReservaProcuraField.getText().trim();

                    if(clienteNIFInput.isEmpty())
                        throw new InvalidParameterException("Preenche NIF do cliente para procurar!");

                    int clienteNIF = Integer.parseInt(clienteNIFInput);

                    List<Reserva> reservas = gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF,gestorDeBaseDeDados);
                    System.out.println(reservas);
                }catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                }
            }


        });
        buttonClienteNIFProcuraCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = "";
                try{
                    String clienteNIFInput = clienteNIFReservaProcuraField.getText().trim();

                    if(clienteNIFInput.isEmpty())
                        throw new InvalidParameterException("Preenche NIF do cliente para procurar!");

                    int clienteNIF = Integer.parseInt(clienteNIFInput);

                    Cliente cliente = gestorDeClientes.procurarClientePorNIF(clienteNIF, gestorDeBaseDeDados);
                    System.out.println(cliente);
                }catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                }
            }
        });
        buttonQuartoIDProcuraLimpeza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = "";
                try{
                    String quartoIDInput = quartoIDLimpezaProcuraField.getText().trim();

                    if(quartoIDInput.isEmpty())
                        throw new InvalidParameterException("Preenche ID do quarto para procurar!");

                    int quartoID = Integer.parseInt(quartoIDInput);

                    List<RegistoDeLimpeza> registosDeLimpeza = gestorDeLimpeza.procurarRegistosPorQuarto(quartoID, gestorDeBaseDeDados);
                    for (RegistoDeLimpeza registo : registosDeLimpeza){
                        System.out.println(registo);
                    }
                }catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                }
            }
        });
        buttonEmpregadoIDProcuraLimpeza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = "";
                try{
                    String empregadoIDInput = empregadoIDLimpezaProcuraField.getText().trim();

                    if(empregadoIDInput.isEmpty())
                        throw new InvalidParameterException("Preenche ID do quarto para procurar!");

                    int empregadoID = Integer.parseInt(empregadoIDInput);

                    List<RegistoDeLimpeza> registosDeLimpeza = gestorDeLimpeza.procurarRegistosPorEmpregadoId(empregadoID, gestorDeBaseDeDados);
                    for (RegistoDeLimpeza registo : registosDeLimpeza){
                        System.out.println(registo);
                    }
                }catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                }
            }
        });
        buttonDataProcuraLimpeza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = "";
                try{
                    String dataInput = dataLimpezaProcuraField.getText().trim();

                    if(dataInput.isEmpty())
                        throw new InvalidParameterException("Preenche ID do quarto para procurar!");

                    String[] dataComponentes = dataInput.split("-");
                    int ano = Integer.parseInt(dataComponentes[0]);
                    int mes = Integer.parseInt(dataComponentes[1]);
                    int dia = Integer.parseInt(dataComponentes[2]);
                    LocalDate data = LocalDate.of(ano,mes,dia);



                    List<RegistoDeLimpeza> registosDeLimpeza = gestorDeLimpeza.procurarRegistosPorData(data.toString(), gestorDeBaseDeDados);
                    for (RegistoDeLimpeza registo : registosDeLimpeza){
                        System.out.println(registo);
                    }
                }catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                }
            }
        });
    }
}


