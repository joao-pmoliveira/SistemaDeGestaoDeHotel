package empregado;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class GestorDeEmpregadosTest {

    static GestorDeBaseDeDados gestorDeBaseDeDados;
    static GestorDeEmpregados gestorDeEmpregados;
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
        gestorDeEmpregados = new GestorDeEmpregados();
    }


    @Test
    void VerificarBaseDeDadosNulaTest(){
        String expectedMessage = "Gestor de base de dados nulo";
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.procurarEmpregadoPorNIF(123,null));
        assertEquals(expectedMessage,exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.procurarEmpregadoPorID(123,null));
        assertEquals(expectedMessage,exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("joaoo",1,"japao",943,123,1,horaEntrada,horaSaida,"passe",null));
        assertEquals(expectedMessage,exception.getMessage());

    }

    @Test
    void procurarClientePorClienteNifInvalidoTest(){
        int clienteNif = 21321;
        String expectedMessage = "Não encontrado cliente para o nif fornecido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.procurarEmpregadoPorNIF(clienteNif,gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());
    }

    @Test
    void procurarClientePorClienteIdInvalidoTest(){
        int clienteId = 654;
        String expectedMessage = "Não econtrado cliente para o ID fornecido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.procurarEmpregadoPorID(clienteId,gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());
    }

    @Test
    void adicionarEmpregadoComNomeInvalidoTest(){
        String empregadoNome = "joao";
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        String expectedMessage = "Nome inválido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado(empregadoNome,1,"japao",943,123,1,horaEntrada,horaSaida,"passe",gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());

    }

    @Test
    void adicionarEmpregadoComCargoInvalidoTest(){
        int cargoID = 100;
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        String expectedMessage = "Cargo de empregado inválido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("alberto",cargoID,"japao",943,123,1,horaEntrada,horaSaida,"passe",gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());

    }

    @Test
    void adicionarEmpregadoComMoradaInvalidoTest(){
        String empregadoMorada = "twerew,ewrwe";
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        String expectedMessage = "Morada inválida";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("alberto",1,empregadoMorada,943,123,1,horaEntrada,horaSaida,"passe",gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());

    }

    @Test
    void adicionarEmpregadoComTelefoneInvalidoTest(){
        int empregadoTelefone = -654;
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        String expectedMessage = "Número telefone inválido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("alberto",1,"japao", empregadoTelefone,123,1,horaEntrada,horaSaida,"passe",gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());
    }

    @Test
    void adicionarEmpregadoComNifInvalidoTest(){
        int empregadoNif = 123456789;
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        assertNotNull(gestorDeEmpregados.procurarEmpregadoPorNIF(empregadoNif,gestorDeBaseDeDados));
        String expectedMessage = "Nif introduzido já existe";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("alberto",1,"japao",943,empregadoNif,1,horaEntrada,horaSaida,"passe",gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());

    }

    @Test
    void adicionarEmpregadoComNifNullTest(){
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        String expectedMessage = "Nif introduzido inválido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("alberto",1,"japao",943,-5,1,horaEntrada,horaSaida,"passe",gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());

    }

    @Test
    void adicionarEmpregadoComSalarioInvalidoTest(){
        float empregadoSalario = -0.5f;
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        String expectedMessage = "Salário não pode ter valor negativo";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("alberto",1,"japao",943,1005,empregadoSalario,horaEntrada,horaSaida,"passe",gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());
    }

    @Test
    void adicionarEmpregadoPalavraPasseInvalidoTest(){
        String empregadoPasse = "fers fttfs";
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        String expectedMessage = "Campo de Palavra-Passe inválido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeEmpregados.adicionarEmpregado("alberto",1,"japao",943,1005,1,horaEntrada,horaSaida,empregadoPasse,gestorDeBaseDeDados));
        assertEquals(expectedMessage,exception.getMessage());


    }

    @Test
    void procurarEmpregadoPorNIFValidoTest(){
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        Empregado expectedEmpregado = new Empregado(1, "joao", 2, "Rua das flores 150", 914568524, 123456789, 1000, horaEntrada.toString(), horaSaida.toString());
        Empregado actualEmpregado = gestorDeEmpregados.procurarEmpregadoPorNIF(123456789,gestorDeBaseDeDados);

        assertNotNull(actualEmpregado);
        assertEquals(expectedEmpregado.getNif(),expectedEmpregado.getNif());
        assertEquals(expectedEmpregado.getId(),expectedEmpregado.getId());
        assertEquals(expectedEmpregado.getHoraSaida(),expectedEmpregado.getHoraSaida());
        assertEquals(expectedEmpregado.getHoraEntrada(),expectedEmpregado.getHoraEntrada());
        assertEquals(expectedEmpregado.getCargo(),expectedEmpregado.getCargo());
        assertEquals(expectedEmpregado.getMorada(),expectedEmpregado.getMorada());
        assertEquals(expectedEmpregado.getNome(),expectedEmpregado.getNome());
        assertEquals(expectedEmpregado.getSalario(),expectedEmpregado.getSalario());
        assertEquals(expectedEmpregado.getTelefone(),expectedEmpregado.getTelefone());
    }

    @Test
    void procurarEmpregadoPorIDValidoTest(){
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        Empregado expectedEmpregado = new Empregado(1, "joao", 2, "Rua das flores 150", 914568524, 123456789, 1000, horaEntrada.toString(), horaSaida.toString());
        Empregado actualEmpregado = gestorDeEmpregados.procurarEmpregadoPorID(1,gestorDeBaseDeDados);

        assertNotNull(actualEmpregado);
        assertEquals(expectedEmpregado.getNif(),expectedEmpregado.getNif());
        assertEquals(expectedEmpregado.getId(),expectedEmpregado.getId());
        assertEquals(expectedEmpregado.getHoraSaida(),expectedEmpregado.getHoraSaida());
        assertEquals(expectedEmpregado.getHoraEntrada(),expectedEmpregado.getHoraEntrada());
        assertEquals(expectedEmpregado.getCargo(),expectedEmpregado.getCargo());
        assertEquals(expectedEmpregado.getMorada(),expectedEmpregado.getMorada());
        assertEquals(expectedEmpregado.getNome(),expectedEmpregado.getNome());
        assertEquals(expectedEmpregado.getSalario(),expectedEmpregado.getSalario());
        assertEquals(expectedEmpregado.getTelefone(),expectedEmpregado.getTelefone());
    }

    @Test
    void AdicionarEmpregadoValido(){
        LocalTime horaEntrada = LocalTime.of(12,0);
        LocalTime horaSaida = LocalTime.of(23,0);
        assertTrue(gestorDeEmpregados.adicionarEmpregado("alberto ze",1,"Esquida Andrade", 2131231,65423435,1000,horaEntrada,horaSaida,"habibi",gestorDeBaseDeDados));
    }
}