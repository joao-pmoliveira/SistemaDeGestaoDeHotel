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

import java.time.LocalDate;
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
                case 1 -> {
                    int clienteNIF;
                    do {
                        System.out.println("NIF do cliente: ");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();
                        if (clienteNIF < 1) System.out.println("NIF Inválido. Introduza um valor superior a 0");
                    } while (clienteNIF < 1);

                    Cliente cliente = gestorDeClientes.procurarClientePorNIF(clienteNIF, gestorDeBaseDeDados);

                    if (cliente == null) {
                        System.out.println("Não foi possível encontrar cliente para o NIF dado.");
                        break;
                    }
                    System.out.println(cliente.getNome() + " | " + cliente.getNIF() + " | " + cliente.getTelefone());
                }
                case 2 -> {
                    int clienteNIF;
                    do{
                        System.out.println("NIF do cliente:");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();
                        if(clienteNIF < 1) System.out.println("NIF inválido.");
                    }while(clienteNIF < 1);

                    String clienteNome;
                    int minTamanhoNome = 3;
                    do {
                        System.out.println("Nome do Cliente:");
                        clienteNome = scanner.nextLine();
                        clienteNome = clienteNome.trim();
                        clienteNome = clienteNome.replaceAll("[^A-Za-z\\s]", "");
                        if (clienteNome.length() < minTamanhoNome) System.out.println("Nome Inválido.");
                    } while (clienteNome.length() < minTamanhoNome);

                    String telefone;
                    do {
                        System.out.println("Telefone: ");
                        telefone = scanner.nextLine();
                    } while (!telefone.matches("\\d{9}"));
                    int clienteTefelone = Integer.parseInt(telefone);

                    boolean resultado = gestorDeClientes.adicionarCliente(clienteNIF, clienteNome, clienteTefelone, gestorDeBaseDeDados);

                    System.out.println("Adição bem sucedida?: " + resultado);
                }
                case 3 -> {
                    int empregadoNIF;
                    do {
                        empregadoNIF = scanner.nextInt();
                        scanner.nextLine();
                        if (empregadoNIF < 1) System.out.println("NIF Inválido. Introduza um valor superior a 0");
                    } while (empregadoNIF < 1);

                    Empregado empregado = gestorDeEmpregados.procurarEmpregadoPorNIF(empregadoNIF, gestorDeBaseDeDados);

                    if (empregado == null) {
                        System.out.println("Não foi possível encontrar um empregado para o NIF dado.");
                        break;
                    }
                    System.out.println(empregado.getNome());
                }
                case 4 -> {
                    int empregadoID;
                    do {
                        empregadoID = scanner.nextInt();
                        scanner.nextLine();
                        if (empregadoID < 1) System.out.println("ID Inválido. Introduza um valor superior a 0");
                    } while (empregadoID < 1);

                    Empregado empregado = gestorDeEmpregados.procurarEmpregadoPorNIF(empregadoID, gestorDeBaseDeDados);

                    if (empregado == null) {
                        System.out.println("Não foi possível encontrar um empregado para o ID dado.");
                        break;
                    }
                    System.out.println(empregado.getNome());
                }
                case 5 -> {
                    String empregadoNome;
                    int minTamanhoNome = 3;
                    do {
                        System.out.println("Nome do empregado:");
                        empregadoNome = scanner.nextLine();
                        empregadoNome = empregadoNome.trim();
                        empregadoNome = empregadoNome.replaceAll("[^A-Za-z\\s]", "");
                        if (empregadoNome.length() < minTamanhoNome) System.out.println("Nome Inválido.");
                    } while (empregadoNome.length() < minTamanhoNome);

                    int empregadoCargoID;
                    do{
                        System.out.println("Cargo do Empregado: [1-Rececionista,2-Limpeza,3-RecursosHumanos");
                        empregadoCargoID = scanner.nextInt();
                        scanner.nextLine();
                    }while(empregadoCargoID < 1 || empregadoCargoID > 3);

                    String empregadoMorada;
                    do{
                        System.out.println("Morada: (sem vírgulas)");
                        empregadoMorada = scanner.nextLine();
                        empregadoMorada = empregadoMorada.replaceAll(",(?=\\s|$)", "");
                    }while(empregadoMorada.isEmpty());

                    String telefone;
                    do {
                        System.out.println("Telefone: ");
                        telefone = scanner.nextLine();
                    } while (!telefone.matches("\\d{9}"));
                    int empregadoTelefone = Integer.parseInt(telefone);

                    int empregadoNIF;
                    do{
                        System.out.println("NIF do empregado:");
                        empregadoNIF = scanner.nextInt();
                        if(empregadoNIF < 1) System.out.println("NIF inválido.");
                    }while(empregadoNIF < 1);

                    float empregadoSalario;
                    do{
                        System.out.println("Salario do empregado");
                        empregadoSalario = scanner.nextFloat();
                        scanner.nextLine();
                    }while(empregadoSalario <= 0f);

                    boolean horaValida;
                    String empregadoHoraEntrada;
                    do{
                        System.out.println("Introduza a hora de entrada HH:mm:ss");
                        empregadoHoraEntrada = scanner.nextLine();
                        Pattern pattern = Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
                        Matcher matcher = pattern.matcher(empregadoHoraEntrada);
                        horaValida = matcher.matches();
                    }while(!horaValida);

                    String empregadoHoraSaida;
                    do{
                        System.out.println("Introduza a hora de saida HH:mm:ss");
                        empregadoHoraSaida = scanner.nextLine();
                        Pattern pattern = Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
                        Matcher matcher = pattern.matcher(empregadoHoraSaida);
                        horaValida = matcher.matches();
                    }while(!horaValida);

                    String empregadoPasse;
                    boolean passwordValida;
                    do{
                        System.out.println("Introduza a password:");
                        empregadoPasse = scanner.nextLine();
                        passwordValida = !empregadoPasse.isEmpty();
                    }while(!passwordValida);

                    gestorDeEmpregados.adicionarEmpregado(empregadoNome, empregadoCargoID, empregadoMorada,
                            empregadoTelefone, empregadoNIF, empregadoSalario, empregadoHoraEntrada,
                            empregadoHoraSaida, empregadoPasse, gestorDeBaseDeDados);
                }
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
