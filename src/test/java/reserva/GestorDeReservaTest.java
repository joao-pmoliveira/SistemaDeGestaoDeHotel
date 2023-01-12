package reserva;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorDeReservaTest {

    static GestorDeBaseDeDados gestorDeBaseDeDados;
    static GestorDeReserva gestorDeReserva;

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
        gestorDeReserva = new GestorDeReserva();
    }

    @AfterAll
    static void tearDown() {
        //gestorDeBaseDeDados.tryUpdateDatabase("");
    }
    @Test
    void procurarReservaComParametrosInvalidosTest(){
        assertNull(gestorDeReserva.getTodasReservasPorClienteNIF(-1, null));
    }

    @Test
    void procurarReservaPorFaturaComParametrosInvalidosTest(){
        assertNull(gestorDeReserva.getReservasPorFaturarPorClienteNif(-1, null));
    }
    @Test
    void criarFaturaParaReservaJaComReservaTest(){
        Fatura expectedFatura = new Fatura(5, 100f);
        Reserva expectedReserva = new Reserva(5, 10, 2, 100, true, expectedFatura);

        assertEquals(expectedFatura, expectedReserva.getFatura());
        gestorDeReserva.gerarFaturaParaReserva(expectedReserva, gestorDeBaseDeDados);
        assertEquals(expectedFatura, expectedReserva.getFatura());
    }

    @Test
    void criarFaturaParaReservaComParametrosInvalidosTest(){
        gestorDeReserva.gerarFaturaParaReserva(null, null);
    }
    @Test
    void adicionarReservaComNifInvalidoTest(){
        int nifInvalido = 0;
        List<LocalDate> datas = new ArrayList<>();
        assertFalse(gestorDeReserva.adicionarReserva(nifInvalido, 1, datas, new int[4], gestorDeBaseDeDados));
    }

}