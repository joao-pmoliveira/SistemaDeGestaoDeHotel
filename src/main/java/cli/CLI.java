package cli;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import cliente.Cliente;
import cliente.GestorDeClientes;
import empregado.Empregado;
import empregado.GestorDeEmpregados;
import limpeza.GestorDeLimpeza;
import limpeza.RegistoDeLimpeza;
import quarto.GestorDeQuartos;
import quarto.Quarto;
import reserva.GestorDeReserva;
import reserva.Reserva;
import utils.GestorDeDatas;

import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ValidadorDeLogin validadorDeLogin = new ValidadorDeLogin("src/main/java/loginData");
        GestorDeBaseDeDados gestorDeBaseDeDados = new GestorDeBaseDeDados(
            validadorDeLogin.getHostname(),
            validadorDeLogin.getPort(),
            validadorDeLogin.getSchema(),
            validadorDeLogin.getUsername(),
            validadorDeLogin.getPassword()
        );
        gestorDeBaseDeDados.tryConnectionToDataBase();

        GestorDeClientes gestorDeClientes = new GestorDeClientes();
        GestorDeEmpregados gestorDeEmpregados = new GestorDeEmpregados();
        GestorDeLimpeza gestorDeLimpeza = new GestorDeLimpeza();
        GestorDeQuartos gestorDeQuartos = new GestorDeQuartos();
        GestorDeReserva gestorDeReserva = new GestorDeReserva();

        int opcao;
        do{
            printMenu();
            System.out.println("Escolha  uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                // Input Inválido. Não foi possível ler número introduzido
                //1: consultar ficha de cliente.
                case 1 -> {
                    try{
                        int clienteNIF;
                        System.out.println("Introduza o NIF do cliente: ");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();
                        Cliente cliente = gestorDeClientes.procurarClientePorNIF(clienteNIF, gestorDeBaseDeDados);
                        System.out.println(cliente.getNome() + " | " + cliente.getNIF() + " | " + cliente.getTelefone());
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //2: registar novo cliente
                case 2 -> {
                    try{
                        int clienteTelefone;
                        System.out.println("Introduza o número de telefone do novo cliente:");
                        clienteTelefone = scanner.nextInt();
                        scanner.nextLine();

                        int clienteNIF;
                        System.out.println("Introduza o NIF do novo cliente: ");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();

                        String clienteNome;
                        System.out.println("Introduza o nome do novo cliente: ");
                        clienteNome = scanner.nextLine();

                        boolean resultado =
                                gestorDeClientes.adicionarCliente(clienteNIF, clienteNome, clienteTelefone, gestorDeBaseDeDados);
                        System.out.println("Cliente Adicionado: "+resultado);
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //3: consultar ficha de empregado (NIF)
                case 3 -> {
                    try{
                        int empregadoNIF;
                        System.out.println("Introduza o NIF do empregado: ");
                        empregadoNIF = scanner.nextInt();
                        scanner.nextLine();

                        Empregado empregado =
                                gestorDeEmpregados.procurarEmpregadoPorNIF(empregadoNIF, gestorDeBaseDeDados);
                        System.out.println(empregado.getNome());
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //4: consultar ficha de empregado (ID)
                case 4 -> {
                    try{
                        int empregadoID;
                        System.out.println("Introduza o ID do empregado: ");
                        empregadoID = scanner.nextInt();
                        scanner.nextLine();
                        Empregado empregado =
                                gestorDeEmpregados.procurarEmpregadoPorID(empregadoID, gestorDeBaseDeDados);
                        System.out.println(empregado.getNome());
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //5: registar novo empregado
                case 5 -> {
                    try{
                        String empregadoNome;
                        System.out.println("Introduza o nome do novo empregado: ");
                        empregadoNome = scanner.nextLine();

                        int empregadoCargoID;
                        System.out.println("Introduza o ID do cargo do empregado: ");
                        //todo listar cargos possíveis
                        empregadoCargoID = scanner.nextInt();
                        scanner.nextLine();

                        String empregadoMorada;
                        System.out.println("Introduza a morada do novo empregado: ");
                        empregadoMorada = scanner.nextLine();

                        int empregadoTelefone;
                        System.out.println("Introduza o número de telefone do novo empregado: ");
                        empregadoTelefone = scanner.nextInt();
                        scanner.nextLine();

                        int empregadoNIF;
                        System.out.println("Introduza o NIF do novo empregado: ");
                        empregadoNIF = scanner.nextInt();
                        scanner.nextLine();

                        float empregadoSalario;
                        System.out.println("Introduza o salário do novo empregado: ");
                        empregadoSalario = scanner.nextFloat();
                        scanner.nextLine();

                        LocalTime empregadoHorarioEntrada;
                        System.out.println("Introduza o horário de entrada do novo empregado: ");
                        System.out.print("hora (0-23): ");
                        int horaEntrada = scanner.nextInt();
                        System.out.print("min (0-59): ");
                        int minutoEntrada = scanner.nextInt();
                        empregadoHorarioEntrada = LocalTime.of(horaEntrada, minutoEntrada);

                        LocalTime empregadoHorarioSaida;
                        System.out.println("Introduza o horário de saída do novo empregado: ");
                        System.out.print("hora (0-23): ");
                        int horaSaida = scanner.nextInt();
                        System.out.println("min (0-59): ");
                        int minutoSaida = scanner.nextInt();
                        empregadoHorarioSaida = LocalTime.of(horaSaida, minutoSaida);
                        scanner.nextLine();

                        String empregadoPalavraPasse;
                        System.out.println("Introduza a palavra-passe do novo empregado: ");
                        empregadoPalavraPasse = scanner.nextLine();

                        boolean resultado =
                                gestorDeEmpregados.adicionarEmpregado(empregadoNome, empregadoCargoID, empregadoMorada,
                                        empregadoTelefone, empregadoNIF, empregadoSalario, empregadoHorarioEntrada,
                                        empregadoHorarioSaida, empregadoPalavraPasse, gestorDeBaseDeDados);
                        System.out.println("Empregado adicionado: "+resultado);
                    } catch (DateTimeException e){
                        System.out.println("Hora Inválida.");
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //6: consultar registos de limpeza (por quarto)
                case 6 -> {
                    String dataRegistoInput;
                    Date dataRegisto;
                    int quartoId;
                    int empregadoId;

                    do {
                        System.out.println("Insira a data do registo:");
                        dataRegistoInput = scanner.nextLine();
                        dataRegisto = GestorDeDatas.validarData(dataRegistoInput);
                    } while (dataRegisto == null);

                    Empregado empregado = null;
                    do{
                        System.out.println("Empregado ID:");
                        empregadoId = scanner.nextInt();
                        scanner.nextLine();
                        if(empregadoId < 1) {
                            System.out.println("Empregado ID inválido.");
                            continue;
                        }
                        empregado = gestorDeEmpregados.procurarEmpregadoPorID(empregadoId, gestorDeBaseDeDados);
                    }while(empregado == null);

                    Quarto quarto;
                    do{
                        System.out.println("Quarto ID:");
                        quartoId = scanner.nextInt();
                        scanner.nextLine();
                        if(quartoId < 1) System.out.println("Quarto ID inválido.");
                        quarto = gestorDeQuartos.procurarQuartoPorID(quartoId, gestorDeBaseDeDados);
                    }while(quarto == null);

                    gestorDeLimpeza.adicionarRegisto(String.valueOf(dataRegisto), quartoId, empregadoId, gestorDeBaseDeDados);
                }
                case 7 -> {
                    int empregadoId;
                    do{
                        System.out.println("Empregado ID:");
                        empregadoId = scanner.nextInt();
                        scanner.nextLine();
                        if(empregadoId < 1) System.out.println("Empregado ID inválido.");
                    }while(empregadoId < 1);

                    List<RegistoDeLimpeza> registoDeLimpezas = gestorDeLimpeza.procurarRegistosPorEmpregadoId(empregadoId, gestorDeBaseDeDados);

                    if(registoDeLimpezas == null || registoDeLimpezas.isEmpty()){
                        System.out.println("Não foram encontrados registos associados a esse empregado");
                        break;
                    }

                    for(RegistoDeLimpeza registoDeLimpeza : registoDeLimpezas){
                        System.out.println("Empregado: "+registoDeLimpeza.getEmpregadoId()
                            +", Data:"+registoDeLimpeza.getData()+", Quarto: "+registoDeLimpeza.getQuartoId());
                    }
                }
                case 8 -> {
                    String dataRegistoInput;
                    Date dataRegisto;

                    do {
                        System.out.println("Insira a data do registo:");
                        dataRegistoInput = scanner.nextLine();
                        dataRegisto = GestorDeDatas.validarData(dataRegistoInput);
                    } while (dataRegisto == null);
                    System.out.println(dataRegisto);

                    List<RegistoDeLimpeza> registoDeLimpezas = gestorDeLimpeza.procurarRegistosPorData(String.valueOf(dataRegisto), gestorDeBaseDeDados);

                    if(registoDeLimpezas == null || registoDeLimpezas.isEmpty()){
                        System.out.println("Não foram encontrados registos associados a essa data");
                        break;
                    }

                    for(RegistoDeLimpeza registoDeLimpeza : registoDeLimpezas){
                        System.out.println("Empregado: "+registoDeLimpeza.getEmpregadoId()
                                +", Data:"+registoDeLimpeza.getData()+", Quarto: "+registoDeLimpeza.getQuartoId());
                    }
                }
                case 9 -> {
                    int quartoId;
                    do{
                        System.out.println("Quarto ID:");
                        quartoId = scanner.nextInt();
                        scanner.nextLine();
                        if(quartoId < 1) System.out.println("Quarto ID inválido.");
                    }while(quartoId < 1);



                    List<RegistoDeLimpeza> registoDeLimpezas = gestorDeLimpeza.procurarRegistosPorQuarto(quartoId, gestorDeBaseDeDados);

                    if(registoDeLimpezas == null || registoDeLimpezas.isEmpty()){
                        System.out.println("Não foram encontrados registos associados a esse quarto");
                        break;
                    }

                    for(RegistoDeLimpeza registoDeLimpeza : registoDeLimpezas){
                        System.out.println("Empregado: "+registoDeLimpeza.getEmpregadoId()
                                +", Data:"+registoDeLimpeza.getData()+", Quarto: "+registoDeLimpeza.getQuartoId());
                    }

                }
                case 10 -> {
                    int quartoID;
                    do {
                        System.out.println("Quarto ID:");
                        quartoID = scanner.nextInt();
                        scanner.nextLine();
                        if (quartoID < 1) System.out.println("ID de Quarto Inválido. Introduza um valor superior a 0");
                    } while (quartoID < 1);

                    Quarto quarto = gestorDeQuartos.procurarQuartoPorID(quartoID, gestorDeBaseDeDados);

                    if (quarto == null) {
                        System.out.println("Não foi possível encontrar um quarto com o ID dado");
                        break;
                    }
                    System.out.println(quarto.getQuartoId() + "|" + quarto.getLayoutNome());
                }
                case 11 -> {
                    int quartoLayoutID;
                    do {
                        System.out.println("Quarto Layout ID:");
                        quartoLayoutID = scanner.nextInt();
                        scanner.nextLine();
                        if (quartoLayoutID < 1) System.out.println("Layout ID Inválido. Valores superiores a 0");
                    } while (quartoLayoutID < 1);

                    ArrayList<Quarto> quartos = gestorDeQuartos.procurarQuartoPorLayout(quartoLayoutID, gestorDeBaseDeDados);

                    if (quartos == null || quartos.isEmpty()) {
                        System.out.println("Não foram encontrados quartos do layout dado");
                        break;
                    }

                    for (Quarto quarto : quartos) {
                        System.out.println(quarto.getQuartoId() + "| " + quarto.getLayoutNome());
                    }
                }
                case 12 -> {
                    int quartoLayoutID;
                    do{
                        System.out.println("Layout ID do quarto: ");
                        quartoLayoutID = scanner.nextInt();
                        if (quartoLayoutID < 1) System.out.println("Layout ID Inválido.");
                    }while(quartoLayoutID < 1);

                    gestorDeQuartos.adicionarQuarto(quartoLayoutID, gestorDeBaseDeDados);
                }
                case 13 -> {
                    int clienteNIF;
                    do {
                        System.out.println("Cliente NIF:");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();
                        if (clienteNIF < 1) System.out.println("Cliente NIF Inválido. Valores superiores a 0");
                    } while (clienteNIF < 1);

                    List<Reserva> reservas = gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, gestorDeBaseDeDados);

                    if (reservas == null || reservas.isEmpty()) {
                        System.out.println("Não foram encontrados reservas com o NIF dado");
                        break;
                    }

                    for (Reserva reserva : reservas) {
                        System.out.println(reserva);
                    }
                }
                case 14 -> {
                    String dataInicialInput;
                    String dataFinalInput;
                    Date dataInicial;
                    Date dataFinal;
                    do {
                        System.out.println("Insira a data de começo:");
                        dataInicialInput = scanner.nextLine();
                        dataInicial = GestorDeDatas.validarData(dataInicialInput);
                    } while (dataInicial == null);

                    do {
                        System.out.println("Insira a data de saída:");
                        dataFinalInput = scanner.nextLine();
                        dataFinal = GestorDeDatas.validarData(dataFinalInput);
                    } while (dataFinal == null || dataFinal.before(dataInicial));


                    ArrayList<Quarto> quartosDisponiveis = gestorDeQuartos.procurarQuartosDisponiveis(dataInicial, dataFinal, gestorDeBaseDeDados);

                    if (quartosDisponiveis.isEmpty()) {
                        System.out.println("Não existem quartos disponíveis para as datas que indicou");
                        break;
                    }

                    HashSet<Integer> quartosDisponiveisIDs = new HashSet<>();
                    for (Quarto quarto : quartosDisponiveis) {
                        quartosDisponiveisIDs.add(quarto.getQuartoId());
                        System.out.println(quarto.getQuartoId());
                    }

                    long diferencaData = dataFinal.getTime() - dataInicial.getTime();
                    long diasDiferenca = (diferencaData / (1000 * 60 * 60 * 24)) % 365;
                    long diasReservados = diasDiferenca + 1;
                    System.out.println("Diferenca: " + diasReservados);

                    String[] quartosInseridos;
                    do {
                        System.out.println("Introduza os quartos a reservar: ");
                        quartosInseridos = scanner.nextLine().split(",");
                    } while (quartosInseridos.length == 0);

                    HashSet<Integer> quartosAReservar = new HashSet<>();

                    for (String quartoInserido : quartosInseridos) {
                        quartoInserido = quartoInserido.trim();
                        int quartoID = Integer.parseInt(quartoInserido);

                        if (quartosDisponiveisIDs.contains(quartoID)) {
                            quartosAReservar.add(quartoID);
                        } else {
                            System.out.println("Um dos quartos inseridos não está disponível: " + quartoInserido);
                        }
                    }

                    float precoBaseDaReserva = 0;
                    for (Quarto quarto : quartosDisponiveis) {
                        if (quartosAReservar.contains(quarto.getQuartoId())) {
                            precoBaseDaReserva += quarto.getPrecoBase() * diasReservados;
                        }
                    }

                    System.out.printf("Montante base da reserva: %.2f\n", precoBaseDaReserva);
                    System.out.println("Confirmar reserva?");
                    boolean continuarComReserva = scanner.nextBoolean();

                    if (!continuarComReserva) {
                        System.out.println("Reserva Cancelada");
                        break;
                    }

                    System.out.println("NIF do Cliente:");
                    int nif = scanner.nextInt();
                    scanner.nextLine();

                    Cliente cliente = gestorDeClientes.procurarClientePorNIF(nif, gestorDeBaseDeDados);

                    if (cliente == null) {
                        System.out.println("NIF do Cliente não atribuído a cliente existente.");
                        System.out.println("Criar novo cliente:");
                        System.out.println("Telefone do Cliente:");
                        String telefoneInput;
                        do {
                            telefoneInput = scanner.nextLine();
                        } while (!telefoneInput.matches("\\d{9}"));
                        int telefone = Integer.parseInt(telefoneInput);

                        String nome;
                        int minTamanhoNome = 3;
                        do {
                            System.out.println("Nome do Cliente:");
                            nome = scanner.nextLine();
                            nome = nome.trim();
                            nome = nome.replaceAll("[^A-Za-z\\s]", "");
                            if (nome.length() < minTamanhoNome) System.out.println("Nome Inválido.");
                        } while (nome.length() < minTamanhoNome);

                        gestorDeClientes.adicionarCliente(nif, nome, telefone, gestorDeBaseDeDados);
                    }

                    HashSet<LocalDate> datas = GestorDeDatas.obterDatasEntreDuasDatas(
                            GestorDeDatas.converterDateParaLocalDate(dataInicial),
                            GestorDeDatas.converterDateParaLocalDate(dataFinal));

                    gestorDeReserva.adicionarReserva(nif, 3, datas, quartosAReservar, gestorDeBaseDeDados);
                }
            }

        }while(opcao != 0);

    }


    private static void printMenu(){
        System.out.println("1: Consultar Ficha de Cliente.");
        System.out.println("2: Registar Novo Cliente.");
        System.out.println("3: Consultar Ficha de Empregado (NIF).");
        System.out.println("4: Consultar Ficha de Empregado (ID).");
        System.out.println("5: Registar Novo Empregado.");
        System.out.println("6: Consultar Registos de Limpeza (Por Quarto).");
        System.out.println("7: Consultar Registos de Limpeza (Por Empregado ID).");
        System.out.println("8: Consultar Registos de Limpeza (Por Data).");
        System.out.println("9: Registar Limpeza.");
        System.out.println("10: Consultar Ficha de Quarto (Por ID).");
        System.out.println("11: Consultar Ficha de Quarto (Por Layout).");
        System.out.println("12: Registar Novo Quarto");
        System.out.println("13: Consultar Reserva de Cliente.");
        System.out.println("14: Registar Nova Reserva");
        System.out.println("0: Sair");
    }
}
