package reserva;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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

        //gestorDeBaseDeDads.tryUpdateDatabase("");
    }

    @AfterAll
    static void tearDown() {
        //gestorDeBaseDeDados.tryUpdateDatabase("");
    }

    //getTodasReservasPorClienteNIF()
    @Test
    void procurarReservaComClienteNIFInvalidoTest(){
        String expectedMessage = "Não existe cliente associado ao NIF fornecido";

        assertTrue(gestorDeBaseDeDados.temConexao());
        Exception exceptionClienteInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getTodasReservasPorClienteNIF(253265900, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionClienteInvalido.getMessage());
    }
    @Test
    void procurarReservaComClienteNIFComReservaAssociadaTest(){
        int clientNIF = 123458756;
        int reservasEsperadas = 1;
        Fatura faturaEsperada = new Fatura(1, 80f);
        Reserva reservaEsperada = new Reserva(1, clientNIF, 3, 80f, true, faturaEsperada);

        assertTrue(gestorDeBaseDeDados.temConexao());

        List<Reserva> reservasReais = gestorDeReserva.getTodasReservasPorClienteNIF(clientNIF, gestorDeBaseDeDados);
        assertNotNull(reservasReais);
        assertEquals(reservasEsperadas, reservasReais.size());

        Reserva reservaAtual = reservasReais.get(0);
        assertNotNull(reservaAtual);
        assertEquals(reservaEsperada.getReservaID(), reservaAtual.getReservaID());
        assertEquals(reservaEsperada.getClienteNIF(), reservaAtual.getClienteNIF());
        assertEquals(reservaEsperada.getEmpregadoID(), reservaAtual.getEmpregadoID());
        assertEquals(reservaEsperada.getPrecoAtual(), reservaAtual.getPrecoAtual());
        assertEquals(reservaEsperada.getEstadoPagamento(), reservaAtual.getEstadoPagamento());
        assertEquals(faturaEsperada.getFaturaID(), reservaAtual.getFatura().getFaturaID());
        assertEquals(faturaEsperada.getMontanteFinal(), reservaAtual.getFatura().getMontanteFinal());
    }
    @Test
    void procurarReservaComClienteNIFComDuasReservasAssociadasTeste(){
        int clienteNIF = 276953124;
        Fatura primeiraFatura = new Fatura(2, 90f);
        Reserva[] reservasEsperadas = new Reserva[2];
        reservasEsperadas[0] = new Reserva(4, clienteNIF, 4, 90f, true, primeiraFatura);
        reservasEsperadas[1] = new Reserva(5, clienteNIF, 3, 180f, false, null);
        assertTrue(gestorDeBaseDeDados.temConexao());

        List<Reserva> reservasReais = gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, gestorDeBaseDeDados);
        assertNotNull(reservasReais);
        assertEquals(reservasEsperadas.length, reservasReais.size());

        for(int i = 0; i < 2; i++){
            Reserva reservaReal = reservasReais.get(i);
            Reserva reservaEsperada = reservasEsperadas[i];
            assertEquals( reservaEsperada.getReservaID(), reservaReal.getReservaID() );
            assertEquals( reservaEsperada.getClienteNIF(), reservaReal.getClienteNIF() );
            assertEquals( reservaEsperada.getEmpregadoID(), reservaReal.getEmpregadoID() );
            assertEquals( reservaEsperada.getPrecoAtual(), reservaReal.getPrecoAtual() );
            assertEquals( reservaEsperada.getEstadoPagamento(), reservaReal.getEstadoPagamento() );

            if(reservaReal.getFatura() == null) assertNull(reservaEsperada.getFatura());
            else{
                assertNotNull(reservaEsperada.getFatura());
                assertEquals(reservaEsperada.getFatura().getFaturaID(), reservaReal.getFatura().getFaturaID());
                assertEquals(reservaEsperada.getFatura().getMontanteFinal(), reservaReal.getFatura().getMontanteFinal());
            }
        }
    }

    //getReservasPorFaturarPorClienteNif()
    @Test
    void procurarReservasPorFaturarComClienteNIFInvalidoTest(){
        String expectedMessage = "Não existe cliente associado ao NIF fornecido";

        assertTrue(gestorDeBaseDeDados.temConexao());
        Exception exceptionClienteInvalido = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getReservasPorFaturarPorClienteNif(253265900, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionClienteInvalido.getMessage());
    }
    @Test
    void procurarReservasPorFaturaParaClienteSemReservasPorFaturarTest(){
        String expectedMessage = "Cliente não tem reservas por faturar";
        assertTrue(gestorDeBaseDeDados.temConexao());
        Exception exceptionClienteSemReservasPorFaturar = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.getReservasPorFaturarPorClienteNif(276953124, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionClienteSemReservasPorFaturar.getMessage());
    }
    @Test
    void gerarFaturaParaReservaNulaTest(){
        String expectedMesssage = "Reserva nula.";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.gerarFaturaParaReserva(null, gestorDeBaseDeDados));

        assertEquals(expectedMesssage, exception.getMessage());
    }
    @Test
    void adicionarReservaComListaDeDatasVaziaOuNulaTest(){
        int clienteNIF = 253265859;
        int empregadoId = 3;
        List<LocalDate> datasVazias = new ArrayList<>();
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);

        String expectedMessage = "Lista de datas vazia. Impossível registar reserva.";

        Exception exceptionDatasNula = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoId, null, quartos, gestorDeBaseDeDados));

        Exception exceptionDatasVazias = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoId, datasVazias, quartos, gestorDeBaseDeDados));

        assertEquals(expectedMessage, exceptionDatasNula.getMessage());
        assertEquals(expectedMessage, exceptionDatasVazias.getMessage());
    }
    @Test
    void adicionarReservaComListaDeQuartosVaziaOuNulaTest(){
        int clienteNIF = 253265859;
        int empregadoId = 3;
        List<LocalDate> datas = new ArrayList<>();
        LocalDate data = LocalDate.of(2023, 1, 10);
        datas.add(data);
        HashSet<Integer> quartosVazios = new HashSet<>();

        String expectedMessage = "Lista de quartos vazia. Impossível registar reserva.";

        Exception exceptionQuartosNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoId, datas, null, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartosNulo.getMessage());

        Exception exceptionQuartosVazios = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoId, datas, quartosVazios, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartosVazios.getMessage());

        quartosVazios.add(null);
        Exception execeptionQuartosComValorNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoId, datas, quartosVazios, gestorDeBaseDeDados));
        assertEquals(expectedMessage, execeptionQuartosComValorNulo.getMessage());

    }
    @Test
    void adicionarReservaComEmpregadoIDInvalidoTest(){
        int empregadIDInvalido = 2;
        List<LocalDate> datas = new ArrayList<>();
        LocalDate data = LocalDate.of(2023, 1, 14);
        datas.add(data);
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);

        String expectedMessage = "Não existe empregado associado ao ID fornecido ou empregado não tem acesso a registar novas reservas. Impossível registar reserva.";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(253265859, empregadIDInvalido, datas, quartos, gestorDeBaseDeDados));

        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    void adicionarReservaComClienteNIFInvalidoTest(){
        int clienteNIFInvalido = 253265900;
        int empregadoID = 3;
        List<LocalDate> datas = new ArrayList<>();
        LocalDate data = LocalDate.of(2023, 1, 14);
        datas.add(data);
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);

        String expectedMessage = "Não existe cliente associado ao NIF fornecido. Impossível registar reserva.";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(clienteNIFInvalido, empregadoID, datas, quartos, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    void interagirComBaseDeDadosSemConexaoTest(){
        String expectedMessage = "Cannot invoke \"java.sql.Connection.createStatement()\" because \"this.connection\" is null";
        ValidadorDeLogin validadorDeLogin = new ValidadorDeLogin("src/main/java/loginData");
        GestorDeBaseDeDados gestorDeBaseDeDadosSemConexao = new GestorDeBaseDeDados(
                validadorDeLogin.getHostname(), validadorDeLogin.getPort(), validadorDeLogin.getSchema(),
                validadorDeLogin.getUsername(), validadorDeLogin.getPassword() );

        int clienteNIF = 276953124;
        int clienteNIFPorFaturar = 253265859;
        int empregadoID = 3;
        List<LocalDate> datas = new ArrayList<>();
        LocalDate data = LocalDate.of(2023, 1, 14);
        datas.add(data);
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);
        Reserva reservaPorFaturar = new Reserva(100, clienteNIF, empregadoID, 100, false, null);

        Exception exceptionProcurarReservas = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exceptionProcurarReservas.getMessage());

        Exception exceptionProcurarReservasFatura = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIFPorFaturar, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exceptionProcurarReservasFatura.getMessage());

        Exception exceptionAdicionarReserva = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoID, datas, quartos, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exceptionAdicionarReserva.getMessage());

        Exception exceptionGerarFatura = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.gerarFaturaParaReserva(reservaPorFaturar, gestorDeBaseDeDadosSemConexao) );
        assertEquals(expectedMessage, exceptionGerarFatura.getMessage());
    }
    @Test
    void interagirComBaseDeDadosGestorNuloTest(){
        int clienteNIF = 276953124;
        int clienteNIFPorFaturar = 253265859;
        int empregadoID = 3;
        List<LocalDate> datas = new ArrayList<>();
        LocalDate data = LocalDate.of(2023, 1, 14);
        datas.add(data);
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);
        Reserva reservaPorFaturar = new Reserva(100, clienteNIF, empregadoID, 100, false, null);

        String expectedMessage = "Gestor de Base de Dados nulo.";

        Exception exceptionProcurarReservas = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, null));
        assertEquals(expectedMessage, exceptionProcurarReservas.getMessage());

        Exception exceptionProcurarReservasFatura = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIFPorFaturar, null));
        assertEquals(expectedMessage, exceptionProcurarReservasFatura.getMessage());

        Exception exceptionAdicionarReserva = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoID, datas, quartos, null));
        assertEquals(expectedMessage, exceptionAdicionarReserva.getMessage());

        Exception exceptionGerarFatura = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.gerarFaturaParaReserva(reservaPorFaturar, null) );
        assertEquals(expectedMessage, exceptionGerarFatura.getMessage());
    }

    void criarFaturaParaReservaJaComReservaTest(){
        Fatura expectedFatura = new Fatura(5, 100f);
        Reserva expectedReserva = new Reserva(5, 10, 2, 100, true, expectedFatura);

        assertEquals(expectedFatura, expectedReserva.getFatura());
        gestorDeReserva.gerarFaturaParaReserva(expectedReserva, gestorDeBaseDeDados);
        assertEquals(expectedFatura, expectedReserva.getFatura());
    }

}