package limpeza;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import limpeza.GestorDeLimpeza;
import limpeza.RegistoDeLimpeza;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorDeLimpezaTest {

    static GestorDeBaseDeDados gestorDeBaseDeDados;
    static GestorDeLimpeza gestorDeLimpeza;

    @BeforeAll //Validar o Login Da Base De dados
    static void startup() {
        ValidadorDeLogin validadorDeLogin = new ValidadorDeLogin("src/main/java/loginData");
        gestorDeBaseDeDados = new GestorDeBaseDeDados(
                validadorDeLogin.getHostname(),
                validadorDeLogin.getPort(),
                validadorDeLogin.getSchema(),
                validadorDeLogin.getUsername(),
                validadorDeLogin.getPassword());

        gestorDeBaseDeDados.tryConnectionToDataBase();
        gestorDeLimpeza = new GestorDeLimpeza();
        gestorDeBaseDeDados.tryResetDatabase();
    }

    @AfterAll
    static void tearDown() {
        gestorDeBaseDeDados.tryResetDatabase();
    }

    @Test
    void VerificarBaseDeDadosInvalidaTest(){
        String expectedMessage = "Gestor de base de dados nulo";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeLimpeza.procurarRegistosPorQuarto(0,null));
        assertEquals(expectedMessage,exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeLimpeza.procurarRegistosPorEmpregadoId(0,null));
        assertEquals(expectedMessage,exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeLimpeza.procurarRegistosPorData(null,null));
        assertEquals(expectedMessage,exception.getMessage());

    }

    @Test
    void procurarRegistoComQuartoIDInvalidoTest() {
        String expectedMessage = "Não existe Quarto associado a esse ID!";

        Exception exceptionQuartoNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorQuarto(100, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartoNulo.getMessage());
    }

    @Test
    void procurarRegistoComQuartoIDSemRegistosTest() {
        String expectedMessage = "Não encontrado registo de limpeza para o quarto pedido!";

        Exception exceptionQuartoSemRegistos = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorQuarto(10, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartoSemRegistos.getMessage());
    }

    @Test
    void procurarRegistoComEmpregadoIDInvalidoTest() {
        String expectedMessage = "Não existe Empregado associado a esse ID!";

        Exception exceptionEmpregadoNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorEmpregadoId(100, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionEmpregadoNulo.getMessage());
    }

    @Test
    void procurarRegistoComEmpregadoIDSemRegistosTest() {
        String expectedMessage = "Não encontrado registo de limpeza para o empregado pedido!";

        Exception exceptionEmpregadoSemRegistos = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorEmpregadoId(2, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionEmpregadoSemRegistos.getMessage());
    }

    @Test
    void procurarRegistosComDataInvalidaTest() {
        String expectedMessage = "Data é nula";

        Exception exceptionDataNula = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorData(null, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionDataNula.getMessage());
    }
    @Test
    void procurarRegistosParaDataSemRegistosTest(){
        String expectedMessage = "Não forma encontrados registos de limpeza para a data fornecida";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorData(LocalDate.of(2020,12,12),gestorDeBaseDeDados));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void adicionarEProcurarRegistosTest(){
        LocalDate data = LocalDate.of(2021,12,25);
        assertTrue(gestorDeLimpeza.adicionarRegisto(data,15,1,gestorDeBaseDeDados));

        List<RegistoDeLimpeza> registosPorData = gestorDeLimpeza.procurarRegistosPorData(data, gestorDeBaseDeDados);
        assertEquals(1, registosPorData.size());

        List<RegistoDeLimpeza> registosPorEmpregadoId = gestorDeLimpeza.procurarRegistosPorEmpregadoId(1, gestorDeBaseDeDados);
        assertEquals(7, registosPorEmpregadoId.size());

        List<RegistoDeLimpeza> registosPorQuarto = gestorDeLimpeza.procurarRegistosPorQuarto(15, gestorDeBaseDeDados);
        assertEquals(1, registosPorQuarto.size());
    }
}

