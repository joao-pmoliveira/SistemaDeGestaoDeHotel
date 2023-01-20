package cli;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import cli.cliente.Cliente;
import cli.cliente.GestorDeClientes;
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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

        boolean logged = false;
        int opcao = -1;
        do{
            if (!logged){
                try{
                    System.out.print("ID:");
                    int empregadoID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("password: ");
                    String password = scanner.nextLine();

                    String queryLogIn = "Select cargo_id from empregado where id = %d and palavra_passe = aes_encrypt('%s','%s')";

                    List<String> resultado = gestorDeBaseDeDados.tryQueryDatabase(String.format(queryLogIn, empregadoID, password, GestorDeBaseDeDados.getEncryptKey()));
                    if(!resultado.isEmpty()){
                        logged = true;
                        System.out.println("Login bem sucessido!");
                        int operadorID = Integer.parseInt(resultado.get(0));
                        gestorDeBaseDeDados.setOperadorID(operadorID);
                    }
                }catch (InputMismatchException e){
                    System.out.println("Erro ao ler número");
                }
                if(!logged) continue;
            }
            printMenu();
            System.out.println("Escolha  uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
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
                    try{
                        int quartoID;
                        System.out.println("Introduza o número do quarto: ");
                        quartoID = scanner.nextInt();
                        scanner.nextLine();

                        List<RegistoDeLimpeza> registosLimpeza =
                                gestorDeLimpeza.procurarRegistosPorQuarto(quartoID, gestorDeBaseDeDados);
                        for(RegistoDeLimpeza registoDeLimpeza : registosLimpeza){
                            System.out.println(registoDeLimpeza.getData()+" | "+registoDeLimpeza.getEmpregadoId());
                        }
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //7: consultar registos de limpeza (por empregado id)
                case 7 -> {
                    try{
                        int empregadoID;
                        System.out.println("Introduza o ID do empregado de limpeza: ");
                        empregadoID = scanner.nextInt();
                        scanner.nextLine();

                        List<RegistoDeLimpeza> registos =
                                gestorDeLimpeza.procurarRegistosPorEmpregadoId(empregadoID, gestorDeBaseDeDados);
                        for(RegistoDeLimpeza registoDeLimpeza : registos){
                            System.out.println("Empregado: "+registoDeLimpeza.getEmpregadoId()
                                +", Data:"+registoDeLimpeza.getData()+", Quarto: "+registoDeLimpeza.getQuartoId());
                        }
                    } catch (InputMismatchException e){
                        scanner.nextInt();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //8: consultar registos de limpeza (por data)
                case 8 -> {
                    try {
                        LocalDate dataRegisto;
                        System.out.println("Introduza a data do registo: ");
                        System.out.print("Ano: ");
                        int ano = scanner.nextInt();
                        System.out.print("Mês: ");
                        int mes = scanner.nextInt();
                        System.out.print("Dia: ");
                        int dia = scanner.nextInt();
                        scanner.nextLine();
                        dataRegisto = LocalDate.of(ano, mes, dia);
                        List<RegistoDeLimpeza> registos =
                                gestorDeLimpeza.procurarRegistosPorData(dataRegisto, gestorDeBaseDeDados);
                        for(RegistoDeLimpeza registoDeLimpeza : registos){
                            System.out.println("Empregado: "+registoDeLimpeza.getEmpregadoId()
                                    +", Data:"+registoDeLimpeza.getData()+", Quarto: "+registoDeLimpeza.getQuartoId());
                        }
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (DateTimeException e){
                        System.out.println("Data Inválida. Não foi possíve ler a data introduzida");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //9: registar limpeza
                case 9 -> {
                    try{
                        LocalDate dataRegisto;
                        System.out.println("Introduza a data do registo: ");
                        System.out.print("Ano: ");
                        int ano = scanner.nextInt();
                        System.out.print("Mês: ");
                        int mes = scanner.nextInt();
                        System.out.print("Dia: ");
                        int dia = scanner.nextInt();
                        scanner.nextLine();
                        dataRegisto = LocalDate.of(ano, mes, dia);

                        int quartoID;
                        System.out.println("Introduza o ID do quarto: ");
                        quartoID = scanner.nextInt();
                        scanner.nextLine();

                        int empregadoID;
                        System.out.println("Introduza o ID do empregado de limpeza: ");
                        empregadoID = scanner.nextInt();
                        scanner.nextLine();

                        //todo alterar parametro para usar LocalDate em vez de String
                        boolean resultado = gestorDeLimpeza.adicionarRegisto(dataRegisto, quartoID, empregadoID, gestorDeBaseDeDados);
                        System.out.println("Registo adicionado: "+resultado);
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (DateTimeException e){
                        System.out.println("Data Inválida. Não foi possível ler data introduzida");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //10: consultar ficha de quarto (id)
                case 10 -> {
                    try{
                        int quartoID;
                        System.out.println("Introduza o número do quarto: ");
                        quartoID = scanner.nextInt();
                        scanner.nextLine();
                        Quarto quarto = gestorDeQuartos.procurarQuartoPorID(quartoID, gestorDeBaseDeDados);
                        System.out.println(quarto.getQuartoId() + "|" + quarto.getLayoutNome());
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //11: consultar ficha de quarto (layout)
                case 11 -> {
                    try{
                        int layoutID;
                        System.out.println("Introduza o ID do layout: ");
                        layoutID = scanner.nextInt();
                        scanner.nextLine();

                        List<Quarto> quartos = gestorDeQuartos.procurarQuartoPorLayout(layoutID, gestorDeBaseDeDados);
                        for (Quarto quarto : quartos){
                            System.out.println(quarto.getQuartoId() + "| " + quarto.getLayoutNome());
                        }
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler o número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //12: registar novo quarto
                case 12 -> {
                    try{
                        int layoutID;
                        System.out.println("Introduza o ID do layout do novo quarto: ");
                        layoutID = scanner.nextInt();
                        scanner.nextLine();

                        boolean resultado = gestorDeQuartos.adicionarQuarto(layoutID, gestorDeBaseDeDados);
                        System.out.println("Quarto adicionado: "+resultado);
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler o número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //13: consultar reservas de cliente
                case 13 -> {
                    try{
                        int clienteNIF;
                        System.out.println("Introduza o NIF do cliente: ");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();

                        List<Reserva> reservas =
                                gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, gestorDeBaseDeDados);
                        for(Reserva reserva : reservas){
                            System.out.println(reserva);
                        }
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler o número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //14: gerar fatura para reserva
                case 14 ->{
                    try{
                        int clienteNIF;
                        System.out.println("Introduza o NIF do cliente: ");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();

                        List<Reserva> reservas =
                                gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIF, gestorDeBaseDeDados);
                        for (Reserva reserva : reservas){
                            System.out.println(reserva);
                        }
                        int reservaID;
                        System.out.println("Introduza o ID da reserva a gerar fatura: ");
                        reservaID = scanner.nextInt();
                        scanner.nextLine();
                        Reserva reservaAFaturar= null;
                        for(Reserva reserva : reservas){
                            if(reserva.getReservaID() == reservaID){
                                reservaAFaturar = reserva;
                                break;
                            }
                        }
                        if(reservaAFaturar == null){
                            System.out.println("ID introduzido não corresponde a nenhuma reserva");
                            break;
                        }
                        gestorDeReserva.gerarFaturaParaReserva(reservaAFaturar, gestorDeBaseDeDados);
                        System.out.println("Reserva Faturada: "+ reservaAFaturar);
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("Input Inválido. Não foi possível ler o número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                }
                //15: registar nova reserva
                case 15 -> {
                    try{
                        LocalDate dataInicial;
                        System.out.println("Introduza a data inicial da reserva: ");
                        System.out.print("Ano: ");
                        int ano = scanner.nextInt();
                        System.out.print("Mês: ");
                        int mes = scanner.nextInt();
                        System.out.print("Dia: ");
                        int dia = scanner.nextInt();
                        dataInicial = LocalDate.of(ano, mes, dia);

                        LocalDate dataFinal;
                        System.out.println("Introduza a data final da reserva: ");
                        System.out.print("Ano: ");
                        ano = scanner.nextInt();
                        System.out.print("Mês: ");
                        mes = scanner.nextInt();
                        System.out.print("Dia: ");
                        dia = scanner.nextInt();
                        dataFinal = LocalDate.of(ano, mes, dia);

                        //todo alterar parameteros de Date para LocalData
                        ZoneId defaultZoneId = ZoneId.systemDefault();
                        Date dataI = Date.from(dataInicial.atStartOfDay(defaultZoneId).toInstant());
                        Date dataF = Date.from(dataFinal.atStartOfDay(defaultZoneId).toInstant());
                        List<Quarto> quartosDisponiveis = gestorDeQuartos.procurarQuartosDisponiveis(dataI, dataF, gestorDeBaseDeDados);
                        HashSet<Integer> quartosDisponiveisIDs = new HashSet<>();

                        for(Quarto quarto : quartosDisponiveis){
                            quartosDisponiveisIDs.add(quarto.getQuartoId());
                        }

                        long reservaDuracaoDias = ChronoUnit.DAYS.between(dataInicial, dataFinal) + 1;

                        String[] quartosInseridos;
                        do{
                            System.out.println("Introduza os quartos a reservar (1,2,...):");
                            quartosInseridos = scanner.nextLine().trim().split(",");
                        }while(quartosInseridos.length == 0);

                        HashSet<Integer> quartosAReservar = new HashSet<>();
                        for (String quartoInserido : quartosInseridos){
                            int quartoID = Integer.parseInt(quartoInserido);
                            if(!quartosDisponiveisIDs.contains(quartoID))
                                throw new InvalidParameterException("Um dos quarto inseridos não está disponível");
                            quartosAReservar.add(quartoID);
                        }

                        float reservaPrecoBase = 0.0f;
                        for(Quarto quarto : quartosDisponiveis){
                            if(quartosAReservar.contains(quarto.getQuartoId()))
                                reservaPrecoBase += quarto.getPrecoBase() * reservaDuracaoDias;
                        }

                        System.out.printf("Montante base da reserva: %.2f euros\n", reservaPrecoBase);
                        System.out.println("Confirmar reserva? (s/n)");
                        String confirmacao = scanner.nextLine();
                        if(confirmacao.equalsIgnoreCase("n")){
                            System.out.println("Reserva cancelada.");
                            break;
                        }

                        int clienteNIF;
                        System.out.println("Introduza o NIF do cliente");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();

                        Cliente cliente = null;
                        try{
                            cliente = gestorDeClientes.procurarClientePorNIF(clienteNIF, gestorDeBaseDeDados);
                        }catch (InvalidParameterException e){
                            System.out.println("NIF não reconhecido. A registar novo cliente...");
                            System.out.println("Nome do cliente: ");
                            String clienteNome = scanner.nextLine();

                            System.out.println("Número de telefone");
                            int clienteTelefone = scanner.nextInt();
                            scanner.nextLine();

                            boolean resultado =
                                    gestorDeClientes.adicionarCliente(clienteNIF, clienteNome, clienteTelefone, gestorDeBaseDeDados);
                            System.out.println("Cliente adicionado: "+resultado);
                        }
                        if(cliente == null)
                            throw new InvalidParameterException("Não foi possível associar um cliente à reserva");

                        System.out.println("Introduza o ID do rececionista: ");
                        int empregadoID = scanner.nextInt();
                        scanner.nextLine();

                        HashSet<LocalDate> datas = GestorDeDatas.obterDatasEntreDuasDatas(dataInicial, dataFinal);
                        gestorDeReserva.adicionarReserva(cliente.getNIF(), empregadoID, datas, quartosAReservar, gestorDeBaseDeDados);

                    } catch (DateTimeException e){
                        System.out.println("Data Inválida. Não foi possível ler a data introduzida");
                    } catch (InputMismatchException e){
                        System.out.println("Input Inválido. Não foi possível ler o número introduzido");
                    } catch (InvalidParameterException e){
                        System.out.println(e.getMessage());
                    } catch (NumberFormatException e){
                        System.out.println("ID de Quarto Inválido. Não foi possível ler um dos IDs de quarto introduzidos");
                    }
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
        System.out.println("14: Gerar Fatura para Reserva.");
        System.out.println("15: Registar Nova Reserva");
        System.out.println("0: Sair");
    }
}
