package gui;

import basededados.GestorDeBaseDeDados;
import cli.cliente.Cliente;
import cli.cliente.GestorDeClientes;
import empregado.GestorDeEmpregados;
import limpeza.GestorDeLimpeza;
import limpeza.RegistoDeLimpeza;
import quarto.GestorDeQuartos;
import reserva.GestorDeReserva;
import reserva.Reserva;
import utils.GestorDeDatas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private JButton buttonRecuarDeFaturacao;
    private JTable table5;
    private JTextField empregadoNomeEmpregadoField;
    private JTextField empregadoMoradaEmpregadoField;
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
    private JButton buttonFaturacaoProcuraReservaId;
    private JButton buttonQuartoIDProcuraLimpeza;
    private JButton buttonEmpregadoIDProcuraLimpeza;
    private JScrollPane tableReservasScrollPane;
    private JScrollPane tableClientesScrollPane;
    private JScrollPane tableLimpezasScrollPane;
    private JTextField empregadoCargoIdEmpregadoField;
    private JButton buttonEmpregadoIDProcuraEmpregado;
    private JTextField empregadoIDEmpregadoProcuraField;
    private JButton buttonEmpregadoNifProcuraEmpregado;
    private JTextField empregadoNifEmpregadoProcuraField;
    private JLabel pesquisarEmpregadoID;
    private JLabel pesquisarEmpregadoNif;
    private JButton buttonQuartosTab;
    private JPanel quartoPanel;
    private JTextField fieldQuartoQuartoId;
    private JTable tableQuarto;
    private JButton buttonQuartoProcuraQuartoId;
    private JButton buttonQuartoProcuraQuartoNif;
    private JTextField fieldQuartoProcuraQuartoId;
    private JTextField fieldQuartoProcuraQuartoNif;
    private JButton buttonGuardarQuarto;
    private JButton buttonRecuarQuarto;

    private JTable tabelaReservas;
    private JTable tabelaClientes;
    private JTable tabelaLimpezas;
    private JTable tabelaEmpregados;

    private int cargoOperador;

    public MenuGui(String title, GestorDeBaseDeDados gestorDeBaseDeDados, int cargoOperador) {
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

        this.cargoOperador = cargoOperador;
        DefaultTableModel modelReservas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaReservas = new JTable(modelReservas);
        tableReservasScrollPane.getViewport().add(tabelaReservas);
        modelReservas.addColumn("ID");
        modelReservas.addColumn("Cliente");
        modelReservas.addColumn("Empregado");
        modelReservas.addColumn("Pagamento");
        modelReservas.addColumn("Preço");

        DefaultTableModel modelClientes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaClientes = new JTable(modelClientes);
        tableClientesScrollPane.getViewport().add(tabelaClientes);
        modelClientes.addColumn("NIF");
        modelClientes.addColumn("Nome");
        modelClientes.addColumn("Telefone");

        DefaultTableModel modelLimpezas = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaLimpezas = new JTable(modelLimpezas);
        tableLimpezasScrollPane.getViewport().add(tabelaLimpezas);
        modelLimpezas.addColumn("Data");
        modelLimpezas.addColumn("Quarto");
        modelLimpezas.addColumn("Empregado");

        switch (cargoOperador){
            //Rececionista
            case 1->{
                buttonClienteTab.setVisible(true);
                buttonReservaTab.setVisible(true);
                buttonEmpregadoTab.setVisible(false);
                buttonLimpezaTab.setVisible(false);
                buttonFaturacaoTab.setVisible(true);
            }
            //Limpezas
            case 2->{
                buttonClienteTab.setVisible(false);
                buttonReservaTab.setVisible(false);
                buttonEmpregadoTab.setVisible(false);
                buttonLimpezaTab.setVisible(true);
                buttonFaturacaoTab.setVisible(false);
            }
            //Recursos Humanos
            case 3->{
                buttonClienteTab.setVisible(false);
                buttonReservaTab.setVisible(false);
                buttonEmpregadoTab.setVisible(true);
                buttonLimpezaTab.setVisible(false);
                buttonFaturacaoTab.setVisible(false);
            }
        }


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
                limparCamposClientePanel();
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
                limparCamposReservaPanel();
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
        buttonRecuarDeFaturacao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limparCamposFaturacaoPanel();
                faturacaoPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        buttonRecuarDeEmpregado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limparCamposEmpregadoPanel();
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
                limparCamposLimpezaPanel();
                limpezaPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });
        buttonAdicionarReserva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String resultado;
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
                String resultado;
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
                String resultado;

                try{
                    String quartoIDInput = quartoIDLimpezaField.getText().trim();
                    String empregadoIDInput = empregadoIDLimpezaField.getText().trim();
                    String dataHoraInput = dataLimpezaField.getText().trim();

                    if(quartoIDInput.isEmpty() || empregadoIDInput.isEmpty() || dataHoraInput.isEmpty())
                        throw new InvalidParameterException("Campo(s) Vazio(s)!");

                    String[] data = dataHoraInput.split("-");
                    int ano = Integer.parseInt(data[0]);
                    int mes = Integer.parseInt(data[1]);
                    int dia = Integer.parseInt(data[2]);

                    LocalDate dataRegisto = LocalDate.of(ano, mes, dia);

                    int quartoID = Integer.parseInt(quartoIDInput);
                    int empregadoID = Integer.parseInt(empregadoIDInput);

                    gestorDeLimpeza.adicionarRegisto(dataRegisto, quartoID, empregadoID, gestorDeBaseDeDados);
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
                String resultado;
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
                String resultado;
                try{
                    String clienteNIFInput = clienteNIFReservaProcuraField.getText().trim();

                    if(clienteNIFInput.isEmpty())
                        throw new InvalidParameterException("Preenche NIF do cliente para procurar!");
                    int clienteNIF = Integer.parseInt(clienteNIFInput);

                    List<Reserva> reservas = gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF,gestorDeBaseDeDados);
                    limparCamposReservaPanel();
                    for(Reserva reserva : reservas){
                        modelReservas.addRow(new Object[]{
                                reserva.getReservaID(), reserva.getClienteNIF(), reserva.getEmpregadoID(), reserva.getEstadoPagamento(),
                                reserva.getPrecoAtual()
                        });
                    }
                    System.out.println(reservas);
                }catch (NumberFormatException exception){
                    resultado = "Erro no parsing" + "\n" + exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                } catch (InvalidParameterException exception){
                    resultado = exception.getMessage();
                    JOptionPane.showMessageDialog(GUI.frame, resultado);
                }
            }


        });
        buttonClienteNIFProcuraCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado;
                try{
                    String clienteNIFInput = clienteNIFClienteProcuraField.getText().trim();

                    if(clienteNIFInput.isEmpty())
                        throw new InvalidParameterException("Preenche NIF do cliente para procurar!");

                    int clienteNIF = Integer.parseInt(clienteNIFInput);

                    Cliente cliente = gestorDeClientes.procurarClientePorNIF(clienteNIF, gestorDeBaseDeDados);
                    limparCamposClientePanel();
                    modelClientes.addRow(new Object[]{
                            cliente.getNIF(), cliente.getNome(), cliente.getTelefone()
                    });
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
                String resultado;
                try{
                    String quartoIDInput = quartoIDLimpezaProcuraField.getText().trim();

                    if(quartoIDInput.isEmpty())
                        throw new InvalidParameterException("Preenche ID do quarto para procurar!");

                    int quartoID = Integer.parseInt(quartoIDInput);

                    List<RegistoDeLimpeza> registosDeLimpeza = gestorDeLimpeza.procurarRegistosPorQuarto(quartoID, gestorDeBaseDeDados);
                    limparCamposLimpezaPanel();
                    for (RegistoDeLimpeza registo : registosDeLimpeza){
                        modelLimpezas.addRow(new Object[]{
                                registo.getData(), registo.getQuartoId(), registo.getEmpregadoId()
                        });
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
                String resultado;
                try{
                    String empregadoIDInput = empregadoIDLimpezaProcuraField.getText().trim();

                    if(empregadoIDInput.isEmpty())
                        throw new InvalidParameterException("Preenche ID do quarto para procurar!");

                    int empregadoID = Integer.parseInt(empregadoIDInput);

                    List<RegistoDeLimpeza> registosDeLimpeza = gestorDeLimpeza.procurarRegistosPorEmpregadoId(empregadoID, gestorDeBaseDeDados);
                    limparCamposLimpezaPanel();
                    for (RegistoDeLimpeza registo : registosDeLimpeza){
                        modelLimpezas.addRow(new Object[]{
                                registo.getData(), registo.getQuartoId(), registo.getEmpregadoId()
                        });
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
                String resultado;
                try{
                    String dataInput = dataLimpezaProcuraField.getText().trim();

                    if(dataInput.isEmpty())
                        throw new InvalidParameterException("Preenche ID do quarto para procurar!");

                    String[] dataComponentes = dataInput.split("-");
                    int ano = Integer.parseInt(dataComponentes[0]);
                    int mes = Integer.parseInt(dataComponentes[1]);
                    int dia = Integer.parseInt(dataComponentes[2]);
                    LocalDate data = LocalDate.of(ano,mes,dia);

                    List<RegistoDeLimpeza> registosDeLimpeza = gestorDeLimpeza.procurarRegistosPorData(data, gestorDeBaseDeDados);
                    limparCamposLimpezaPanel();
                    for (RegistoDeLimpeza registo : registosDeLimpeza){
                        modelLimpezas.addRow(new Object[]{
                                registo.getData(), registo.getQuartoId(), registo.getEmpregadoId()
                        });
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

    private void limparCamposReservaPanel(){
        clienteNIFReservaField.setText("");
        quartosIDReservaField.setText("");
        dataInicialReservaField.setText("");
        dataFinalReservaField.setText("");
        empregadoIDReservaField.setText("");
        clienteNIFReservaProcuraField.setText("");
        ((DefaultTableModel)tabelaReservas.getModel()).setRowCount(0);
    }

    private void limparCamposClientePanel(){
        clienteNIFClienteField.setText("");
        clienteNomeClienteField.setText("");
        clienteTelefoneClienteField.setText("");
        clienteNIFClienteProcuraField.setText("");
        ((DefaultTableModel)tabelaClientes.getModel()).setRowCount(0);
    }

    private void limparCamposEmpregadoPanel(){
        empregadoMoradaEmpregadoField.setText("");
        empregadoNIFEmpregadoField.setText("");
        empregadoNomeEmpregadoField.setText("");
        empregadoPasswordEmpregadoField.setText("");
        empregadoHoraEntradaEmpregadoField.setText("");
        empregadoHoraSaidaEmpregadoField.setText("");
        empregadoTelefoneEmpregadoField.setText("");
        empregadoSalarioEmpregadoField.setText("");
    }

    private void limparCamposLimpezaPanel(){
        dataLimpezaField.setText("");
        empregadoIDLimpezaField.setText("");
        quartoIDLimpezaField.setText("");
        dataLimpezaProcuraField.setText("");
        empregadoIDLimpezaProcuraField.setText("");
        quartoIDLimpezaProcuraField.setText("");
        ((DefaultTableModel)tabelaLimpezas.getModel()).setRowCount(0);
    }

    private void limparCamposFaturacaoPanel(){
        clienteNIFReservaProcuraField.setText("");
    }
}


