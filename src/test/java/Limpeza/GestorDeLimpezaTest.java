package Limpeza;

import limpeza.GestorDeLimpeza;
import limpeza.RegistoDeLimpeza;
import org.junit.jupiter.api.Test;
import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.BeforeAll;
import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.ArrayList;

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
                ()->gestorDeLimpeza.procurarRegistosPorData("0000-00-00",null));
        assertEquals(expectedMessage,exception.getMessage());

    }


    @Test
    void procurarQuartoInvalidoTest() {
        String expectedMessage = "Não existe limpeza com o quarto associado";

        Exception exceptionQuartoNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorQuarto(0, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartoNulo.getMessage());
    }


    @Test
    void procurarQuartoValidoTest(){
        RegistoDeLimpeza validolimpeza = new RegistoDeLimpeza("2022-12-19",1,1);
        ArrayList<RegistoDeLimpeza> procurarlimpeza =gestorDeLimpeza.procurarRegistosPorQuarto(1, gestorDeBaseDeDados);

        for (int tamanho = 0; tamanho < procurarlimpeza.size(); tamanho++) {
            assertEquals(validolimpeza.getQuartoId(), procurarlimpeza.get(tamanho).getQuartoId());

        }


    }

    @Test
    void procurarEmpregadoInvalidoTest() {
        String expectedMessage = "Não existe limpeza com o empregado associado";

        Exception exceptionEmpregadoNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorEmpregadoId(0, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionEmpregadoNulo.getMessage());
    }


    @Test
    void procurarEmpregadoValidoTest(){
        RegistoDeLimpeza validolimpeza = new RegistoDeLimpeza("2022-12-19",1,1);
        ArrayList<RegistoDeLimpeza> procurarlimpeza =gestorDeLimpeza.procurarRegistosPorEmpregadoId(1, gestorDeBaseDeDados);

        for (int tamanho = 0; tamanho < procurarlimpeza.size(); tamanho++) {
            assertEquals(validolimpeza.getEmpregadoId(), procurarlimpeza.get(tamanho).getEmpregadoId());


        }
    }

    @Test
    void procurarDataInvalidaTest() {
        String expectedMessage = "Não existe limpeza com essa data associada";

        Exception exceptionDataNula = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorData("0000-00-00", gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionDataNula.getMessage());

    }

    @Test
    void procurarDataValidaTest(){
        RegistoDeLimpeza validolimpeza = new RegistoDeLimpeza("2022-12-19",1,1);
        ArrayList<RegistoDeLimpeza> procurarlimpeza =gestorDeLimpeza.procurarRegistosPorData("2022-12-19", gestorDeBaseDeDados);

        for (int tamanho = 0; tamanho < procurarlimpeza.size(); tamanho++) {
            assertEquals(validolimpeza.getData(), procurarlimpeza.get(tamanho).getData());

        }
    }

    @Test
    void AdicionarRegistoDeLimpezasValidoTest(){
        assertTrue(gestorDeLimpeza.adicionarRegisto("2022-12-19",1,1,gestorDeBaseDeDados));
    }
}

