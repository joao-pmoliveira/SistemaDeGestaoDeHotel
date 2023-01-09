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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

        int opcao = 0;

        do{
            printMenu();
            System.out.println("Escolha  uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:{
                    int clienteNIF = 0;
                    do{
                        System.out.println("NIF do cliente: ");
                        clienteNIF = scanner.nextInt();
                        scanner.nextLine();
                        if (clienteNIF<1) System.out.println("NIF Inválido. Introduza um valor superior a 0");
                    }while(clienteNIF < 1);

                    Cliente cliente = gestorDeClientes.procurarClientePorNIF(clienteNIF, gestorDeBaseDeDados);

                    if (cliente == null){
                        System.out.println("Não foi possível encontrar cliente para o NIF dado.");
                        break;
                    }
                    System.out.println(cliente.getNome()+" | "+cliente.getNIF()+" | "+cliente.getTelefone());
                    break;
                }
                case 2:{
                    //Adicionar novo cliente
                    break;
                }
                case 3:{
                    int empregadoNIF = 0;
                    do{
                        empregadoNIF = scanner.nextInt();
                        scanner.nextLine();
                        if(empregadoNIF < 1)  System.out.println("NIF Inválido. Introduza um valor superior a 0");
                    }while(empregadoNIF < 1);

                    Empregado empregado = gestorDeEmpregados.procurarEmpregadoPorNIF(empregadoNIF, gestorDeBaseDeDados);

                    if(empregado == null){
                        System.out.println("Não foi possível encontrar um empregado para o NIF dado.");
                        break;
                    }
                    System.out.println(empregado.getNome());
                    break;
                }
                case 4:{
                    int empregadoID = 0;
                    do{
                        empregadoID = scanner.nextInt();
                        scanner.nextLine();
                        if(empregadoID < 1)  System.out.println("ID Inválido. Introduza um valor superior a 0");
                    }while(empregadoID < 1);

                    Empregado empregado = gestorDeEmpregados.procurarEmpregadoPorNIF(empregadoID, gestorDeBaseDeDados);

                    if(empregado == null){
                        System.out.println("Não foi possível encontrar um empregado para o ID dado.");
                        break;
                    }
                    System.out.println(empregado.getNome());
                    break;
                }
                case 5:{
                    //Adicionar Empregado
                    break;
                }
                case 6:{
                    //procurar registos de limpeza dado quarto ID
                    break;
                }
                case 7:{
                    //procurar registos de limpeza dado empregado ID
                    break;
                }
                case 8:{
                    //procurar registos de limpeza dado data
                    break;
                }
                case 9:{
                    //adicionar novo registo de limpeza
                    break;
                }
                case 10:{
                    int quartoID = 0;
                    do{
                        System.out.println("Quarto ID:");
                        quartoID = scanner.nextInt();
                        scanner.nextLine();
                        if(quartoID < 1) System.out.println("ID de Quarto Inválido. Introduza um valor superior a 0");
                    }while(quartoID < 1);

                    Quarto quarto = gestorDeQuartos.procurarQuartoPorID(quartoID, gestorDeBaseDeDados);

                    if(quarto == null){
                        System.out.println("Não foi possível encontrar um quarto com o ID dado");
                        break;
                    }
                    System.out.println(quarto.getQuartoId()+"|"+quarto.getLayoutNome());
                    break;
                }
                case 11:{
                    int quartoLayoutID = 0;
                    do{
                        System.out.println("Quarto Layout ID:");
                        quartoLayoutID = scanner.nextInt();
                        scanner.nextLine();
                        if(quartoLayoutID < 1) System.out.println("Layout ID Inválido. Valores superiores a 0");
                    }while( quartoLayoutID < 1);

                    ArrayList<Quarto> quartos = gestorDeQuartos.procurarQuartoPorLayout(quartoLayoutID, gestorDeBaseDeDados);
                    System.out.println(quartos);


                    /*
                    * fornecer lista de layouts (retirados da base de dados
                    * recolher input do utilizador
                    * validar
                    * */
                }
                //default -> {/*sair*/}
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
        System.out.println("13: Consultar Reservar de Cliente.");
        System.out.println("14: Registar Nova Reserva");
        System.out.println("0: Sair");
    }
}
