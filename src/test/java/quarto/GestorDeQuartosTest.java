package quarto;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.GestorDeDatas;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestorDeQuartosTest {
    static GestorDeBaseDeDados gestorDeBaseDeDados;
    static GestorDeQuartos gestorDeQuartos;

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
        gestorDeQuartos = new GestorDeQuartos();
    }

    @Test
    void procurarQuartoComIDInvalidoTest(){
        String expectedMessage = "Não existe quarto associado ao ID fornecido";

        Exception exceptionQuartoIDInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartoPorID(123, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartoIDInvalido.getMessage());
    }
    @Test
    void procurarQuartoComBDNulaTest(){
        String expectedMessage = "Gestor de Base de Dados nulo.";

        Exception exceptionBDInvalida = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartoPorID(123, null));
        assertEquals(expectedMessage, exceptionBDInvalida.getMessage());

        exceptionBDInvalida = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartoPorLayout(1, null));
        assertEquals(expectedMessage, exceptionBDInvalida.getMessage());

        GestorDeDatas gestorDeDatas = new GestorDeDatas();
        exceptionBDInvalida = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartosDisponiveis(gestorDeDatas.validarData("2023-10-04"), gestorDeDatas.validarData("2023-10-06"),  null));
        assertEquals(expectedMessage, exceptionBDInvalida.getMessage());

        exceptionBDInvalida = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.adicionarQuarto(1, null));
        assertEquals(expectedMessage, exceptionBDInvalida.getMessage());
    }
    @Test
    void procurarQuartoComParametrosValidosTest(){
        Quarto expectedQuarto = new Quarto(1, 1, 80, "Casal", "1 cama de casal");
        Quarto actualQuarto = gestorDeQuartos.procurarQuartoPorID(1, gestorDeBaseDeDados);

        assertNotNull(actualQuarto);
        assertEquals(expectedQuarto.getQuartoId(), actualQuarto.getQuartoId());
        assertEquals(expectedQuarto.getLayoutId(), actualQuarto.getLayoutId());
        assertEquals(expectedQuarto.getPrecoBase(), actualQuarto.getPrecoBase());
        assertEquals(expectedQuarto.getLayoutNome(), actualQuarto.getLayoutNome());
        assertEquals(expectedQuarto.getLayoutDescricao(), actualQuarto.getLayoutDescricao());
    }
    @Test
    void procurarQuartosComLayoutInvalidoTest(){
        String expectedMessage = "Não existem quartos associado ao Layout fornecido";

        Exception exceptionLayoutIDInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartoPorLayout(7, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionLayoutIDInvalido.getMessage());
    }
    @Test
    void procurarQuartosComLayoutValidoTest(){
        ArrayList<Quarto> expectedQuartosLayout = new ArrayList<>();
        Quarto Quarto1 = new Quarto(19, 6, 300, "Suite Master", "2 divisões (1 cama de casal cada)");
        Quarto Quarto2 = new Quarto(20, 6, 300, "Suite Master", "2 divisões (1 cama de casal cada)");
        expectedQuartosLayout.add(Quarto1);
        expectedQuartosLayout.add(Quarto2);

        ArrayList<Quarto> actualQuartosLayout = gestorDeQuartos.procurarQuartoPorLayout(6, gestorDeBaseDeDados);

        assertNotNull(expectedQuartosLayout);
        assertEquals(expectedQuartosLayout.size(), actualQuartosLayout.size());
    }
    @Test
    void procurarQuartoDisponiveisEntreDuasDatasIguaisTest(){
        String expectedMessage = "As datas são iguais.";
        GestorDeDatas gestorDeDatas = new GestorDeDatas();

        Exception exceptionDatasInvalidas = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartosDisponiveis(gestorDeDatas.validarData("2023-10-04"), gestorDeDatas.validarData("2023-10-04"), gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionDatasInvalidas.getMessage());
    }
    @Test
    void adicionarQuartoComLayoutInvalidoTest(){
        String expectedMessage = "LayoutID inválido";

        Exception exceptionLayoutIDInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.adicionarQuarto(0, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionLayoutIDInvalido.getMessage());
    }
    @Test
    void adicionarQuartoComParametrosValidosTest(){
        assertTrue(gestorDeQuartos.adicionarQuarto(1, gestorDeBaseDeDados));
    }

}