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

        exceptionBDInvalida = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.getTodosLayouts(null));
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
        assertEquals(expectedQuartosLayout.get(1).getQuartoId(), actualQuartosLayout.get(1).getQuartoId());
        assertEquals(expectedQuartosLayout.get(1).getLayoutId(), actualQuartosLayout.get(1).getLayoutId());
        assertEquals(expectedQuartosLayout.get(1).getPrecoBase(), actualQuartosLayout.get(1).getPrecoBase());
        assertEquals(expectedQuartosLayout.get(1).getLayoutNome(), actualQuartosLayout.get(1).getLayoutNome());
        assertEquals(expectedQuartosLayout.get(1).getLayoutDescricao(), actualQuartosLayout.get(1).getLayoutDescricao());
        assertEquals(expectedQuartosLayout.get(0).getQuartoId(), actualQuartosLayout.get(0).getQuartoId());
        assertEquals(expectedQuartosLayout.get(0).getLayoutId(), actualQuartosLayout.get(0).getLayoutId());
        assertEquals(expectedQuartosLayout.get(0).getPrecoBase(), actualQuartosLayout.get(0).getPrecoBase());
        assertEquals(expectedQuartosLayout.get(0).getLayoutNome(), actualQuartosLayout.get(0).getLayoutNome());
        assertEquals(expectedQuartosLayout.get(0).getLayoutDescricao(), actualQuartosLayout.get(0).getLayoutDescricao());
    }
    @Test
    void procurarQuartoDisponiveisEntreDuasDatasInvalidasTest(){
        String expectedMessage = "A data inicial é superior à final.";
        GestorDeDatas gestorDeDatas = new GestorDeDatas();

        Exception exceptionDatasInvalidas = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartosDisponiveis(gestorDeDatas.validarData("2023-10-04"), gestorDeDatas.validarData("2023-10-03"), gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionDatasInvalidas.getMessage());
    }
    @Test
    void procurarQuartoDisponiveisEntreDuasDatasSemQuartosDisponiveisTest(){
        gestorDeBaseDeDados.tryUpdateReservasDatabase();
        String expectedMessage = "Não existem quartos disponiveis no intervalo de datas fornecido";
        GestorDeDatas gestorDeDatas = new GestorDeDatas();

        Exception exceptionDatasInvalidas = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.procurarQuartosDisponiveis(gestorDeDatas.validarData("2021-12-31"), gestorDeDatas.validarData("2021-12-31"), gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionDatasInvalidas.getMessage());
        gestorDeBaseDeDados.tryResetDatabase();
    }
    @Test
    void procurarQuartoDisponiveisEntreDuasDatasTest(){
        GestorDeDatas gestorDeDatas = new GestorDeDatas();
        assertNotNull(gestorDeQuartos.procurarQuartosDisponiveis(gestorDeDatas.validarData("2023-10-03"), gestorDeDatas.validarData("2023-10-05"), gestorDeBaseDeDados));
    }
    @Test
    void adicionarQuartoComLayoutInvalidoTest(){
        String expectedMessage = "LayoutID não existe";
        Exception exceptionLayoutIDInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.adicionarQuarto(0, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionLayoutIDInvalido.getMessage());
    }
    @Test
    void adicionarQuartoComParametrosValidosTest(){
        assertTrue(gestorDeQuartos.getTodosLayouts(gestorDeBaseDeDados).containsKey(1));
        assertTrue(gestorDeQuartos.adicionarQuarto(1, gestorDeBaseDeDados));
    }
    @Test
    void getTodosOsLayoutsDisponiveisTest(){
        gestorDeBaseDeDados.tryDeleteAllLayoutsDatabase();
        String expectedMessage = "Não existem Layouts";
        Exception exceptionLayoutIDInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeQuartos.getTodosLayouts(gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionLayoutIDInvalido.getMessage());
        gestorDeBaseDeDados.tryResetDatabase();
    }
}