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
    @ParameterizedTest
    @ValueSource(ints={0,123456789})
    void procurarReservaComParametrosInvalidosTest(int nif){
        assertNull(gestorDeReserva.getTodasReservasPorClienteNIF(nif, gestorDeBaseDeDados));
        assertNull(gestorDeReserva.getTodasReservasPorClienteNIF(nif, null));
        assertNull(gestorDeReserva.getReservasPorFaturarPorClienteNif(nif, gestorDeBaseDeDados));
        assertNull(gestorDeReserva.getReservasPorFaturarPorClienteNif(nif, null));
    }

    @Test
    void adicionarReservaComNifInvalidoTest(){
        int nifInvalido = 0;
        List<LocalDate> datas = new ArrayList<>();
        assertFalse(gestorDeReserva.adicionarReserva(nifInvalido, 1, datas, new int[4], gestorDeBaseDeDados));
    }

}