package Limpeza;

import limpeza.GestorDeLimpeza;
import limpeza.RegistoDeLimpeza;
import org.junit.jupiter.api.Test;
import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.BeforeAll;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    void procurarQuartoValidoTest(){
        LocalDate data=LocalDate.of(2022,12,19);
        RegistoDeLimpeza registoDeLimpezaEsperado = new RegistoDeLimpeza(data,1,1);
        List<RegistoDeLimpeza> registos =gestorDeLimpeza.procurarRegistosPorQuarto(1, gestorDeBaseDeDados);
        assertEquals(1, registos.size());
        RegistoDeLimpeza registoDeLimpezaReal=registos.get(0);
        assertEquals(registoDeLimpezaEsperado.getQuartoId(), registoDeLimpezaReal.getQuartoId());
        assertEquals(registoDeLimpezaEsperado.getEmpregadoId(), registoDeLimpezaReal.getEmpregadoId());
        assertEquals(registoDeLimpezaEsperado.getData(), registoDeLimpezaReal.getData());
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
    void procurarEmpregadoValidoTest(){
        LocalDate data=LocalDate.of(2022,12,19);
        RegistoDeLimpeza registoDeLimpezaEsperado = new RegistoDeLimpeza(data,1,1);
        List<RegistoDeLimpeza> registos =gestorDeLimpeza.procurarRegistosPorEmpregadoId(1, gestorDeBaseDeDados);

        assertEquals(1, registos.size());
        RegistoDeLimpeza registoDeLimpezaReal=registos.get(0);
        assertEquals(registoDeLimpezaEsperado.getQuartoId(), registoDeLimpezaReal.getQuartoId());
        assertEquals(registoDeLimpezaEsperado.getEmpregadoId(), registoDeLimpezaReal.getEmpregadoId());
        assertEquals(registoDeLimpezaEsperado.getData(), registoDeLimpezaReal.getData());
    }

    @Test
    void procurarDataInvalidaTest() {
        String expectedMessage = "Não encontrado registo de limpeza para a data pedida!";

        Exception exceptionDataNula = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorData(null, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionDataNula.getMessage());

    }


    @Test
    void procurarDataValidaTest(){
        LocalDate data = LocalDate.of(2022,12,19);
        RegistoDeLimpeza registoDeLimpezaEsperado = new RegistoDeLimpeza(data,1,1);
        List<RegistoDeLimpeza> registos =gestorDeLimpeza.procurarRegistosPorData(data, gestorDeBaseDeDados);

        assertEquals(1, registos.size());
        RegistoDeLimpeza registoDeLimpezaReal=registos.get(0);
        assertEquals(registoDeLimpezaEsperado.getQuartoId(), registoDeLimpezaReal.getQuartoId());
        assertEquals(registoDeLimpezaEsperado.getEmpregadoId(), registoDeLimpezaReal.getEmpregadoId());
        assertEquals(registoDeLimpezaEsperado.getData(), registoDeLimpezaReal.getData());
    }

    @Test
    void AdicionarRegistoDeLimpezasValidoTest(){
        LocalDate data=LocalDate.of(2022,12,19);
        assertTrue(gestorDeLimpeza.adicionarRegisto(data,1,1,gestorDeBaseDeDados));
    }
}

