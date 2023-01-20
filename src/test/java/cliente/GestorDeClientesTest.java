package cliente;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import cli.cliente.Cliente;
import cli.cliente.GestorDeClientes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GestorDeClientesTest {
    static GestorDeBaseDeDados gestorDeBaseDeDados;
    static GestorDeClientes gestorDeClientes;

    @BeforeAll
    static void setUp() {
        ValidadorDeLogin validadorDeLogin = new ValidadorDeLogin("src/main/java/loginData");
        gestorDeBaseDeDados = new GestorDeBaseDeDados(
                validadorDeLogin.getHostname(),
                validadorDeLogin.getPort(),
                validadorDeLogin.getSchema(),
                validadorDeLogin.getUsername(),
                validadorDeLogin.getPassword());

        gestorDeBaseDeDados.tryConnectionToDataBase();
        gestorDeClientes = new GestorDeClientes();
    }
    @Test
    void procurarClienteComBDInvalidaTest(){
        String expectedMessage = "Gestor de Base de Dados nulo.";

        Exception exceptionClienteInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeClientes.procurarClientePorNIF(123, null));
        assertEquals(expectedMessage, exceptionClienteInvalido.getMessage());

        Exception exceptionBaseDeDadosInvalida = assertThrows(InvalidParameterException.class,
                ()-> gestorDeClientes.adicionarCliente(123, "José Paulo", 9128212,  null));
        assertEquals(expectedMessage, exceptionBaseDeDadosInvalida.getMessage());
    }
    @Test
    void procurarClienteComNIFInvalidoTest(){
        String expectedMessage = "Não existe cliente associado ao NIF fornecido";

        Exception exceptionClienteInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeClientes.procurarClientePorNIF(123, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionClienteInvalido.getMessage());
    }
    @Test
    void procurarClienteComParametrosValidosTest(){
        Cliente expectedCliente = new Cliente(123458756, "José Feiteira", 925412236);
        Cliente actualCliente  = gestorDeClientes.procurarClientePorNIF(123458756, gestorDeBaseDeDados);

        assertNotNull(actualCliente);
        assertEquals(expectedCliente.getNIF(), actualCliente.getNIF());
        assertEquals(expectedCliente.getNome(), actualCliente.getNome());
        assertEquals(expectedCliente.getTelefone(), actualCliente.getTelefone());
    }
    @Test
    void adicionarClienteComNIFInvalidoTest(){
        Exception exceptionNIFInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeClientes.adicionarCliente(-1, "José Paulo", 9128212,  gestorDeBaseDeDados));
        assertEquals("NIF Inválido", exceptionNIFInvalido.getMessage());
        Exception exceptionNomeInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeClientes.adicionarCliente(123, "", 9128212,  gestorDeBaseDeDados));
        assertEquals("Nome Inválido", exceptionNomeInvalido.getMessage());
        Exception exceptionTelefoneInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeClientes.adicionarCliente(123, "José Paulo", -1,  gestorDeBaseDeDados));
        assertEquals("Telefone Inválido", exceptionTelefoneInvalido.getMessage());
    }
    @Test
    void adicionarClienteComNIFRepetidoTest(){
        Exception exceptionNIFInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeClientes.adicionarCliente(123458756, "José Paulo", 9128212,  gestorDeBaseDeDados));
        assertEquals("NIF já existente", exceptionNIFInvalido.getMessage());
    }

    @Test
    void adicionarClienteComParametrosValidosTest(){
        assertTrue(gestorDeClientes.adicionarCliente(1234, "jose lopes", 23, gestorDeBaseDeDados));
    }
}