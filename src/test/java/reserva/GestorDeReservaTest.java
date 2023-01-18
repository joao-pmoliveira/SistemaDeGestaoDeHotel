package reserva;

import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalDate;
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

        gestorDeBaseDeDados.tryResetDatabase();
    }

    @AfterAll
    static void tearDown() {
        gestorDeBaseDeDados.tryResetDatabase();
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
    void procurarReservasPorFaturarDadoClienteNIFSemReservasPorFaturarAssociadasTest(){
        int clienteNIF = 123458756;
        assertTrue(gestorDeBaseDeDados.temConexao());

        String mensagemEsperada = "Não existem reservas por faturar para o NIF dado";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIF, gestorDeBaseDeDados));

        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void procurarReservasPorFaturarDadoClienteNIFComUmaReservaPorFaturarTest(){
        int clienteNIF = 253265859;
        Reserva reservaEsperada = new Reserva(2, clienteNIF, 4, 160.0f, false, null);

        List<Reserva> reservasReais = gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIF, gestorDeBaseDeDados);
        assertNotNull(reservasReais);
        assertEquals(1, reservasReais.size());

        Reserva reservaReal = reservasReais.get(0);
        assertNotNull(reservaReal);

        assertEquals(reservaEsperada.getReservaID(), reservaReal.getReservaID());
        assertEquals(reservaEsperada.getClienteNIF(), reservaReal.getClienteNIF());
        assertEquals(reservaEsperada.getEmpregadoID(), reservaReal.getEmpregadoID());
        assertEquals(reservaEsperada.getPrecoAtual(), reservaReal.getPrecoAtual());
        assertEquals(reservaEsperada.getEstadoPagamento(), reservaReal.getEstadoPagamento());
        assertFalse(reservaReal.getEstadoPagamento());
        assertNull(reservaReal.getFatura());
    }

    //gerarFatura()
    @Test
    void gerarFaturaParaReservaNulaTest(){
        String expectedMesssage = "Reserva nula.";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.gerarFaturaParaReserva(null, gestorDeBaseDeDados));

        assertEquals(expectedMesssage, exception.getMessage());
    }
    @Test
    void gerarFaturaParaReservaFaturadaTest(){
        Fatura fatura = new Fatura(99, 90.0f);
        Reserva reserva = new Reserva(999, 111111111, 90, 90.0f, true, fatura);

        String mensagemEsperada = "Não é possível gerar um fatura para a reserva fornecido, reserva já se encontra faturada";

        assertTrue(gestorDeBaseDeDados.temConexao());

        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.gerarFaturaParaReserva(reserva, gestorDeBaseDeDados));

        assertEquals(mensagemEsperada, exception.getMessage());
    }

    //adicionarReserva()
    @Test
    void adicionarReservaUsandoEmpregadoIDInvalidoTest(){
        int empregadoID = 1;
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2023, 1, 17));
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);

        String mensagemEsperada = "Não existe empregado associado ao ID fornecido ou empregado não tem acesso a registar novas reservas";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(123458756, empregadoID, datas, quartos, gestorDeBaseDeDados));

        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void adicionarReservaComDatasVaziasTest(){
        String mensagemEsperada = "Lista de datas vazia";
        HashSet<LocalDate> datas = new HashSet<>();
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(123458756, 3, datas, new HashSet<>(), gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void adicionarReservaComDatasNulasTest(){
        String mensagemEsperada = "Lista de datas nula";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(123458756, 3, null, new HashSet<>(), gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void adicionarReservaComDatasAConterDataNulaTest(){
        String mensagemEsperada = "Lista de datas com elemento nulo";
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2023, 1, 10));
        datas.add(null);
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(123458756, 3, datas, new HashSet<>(), gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void adicionarDatasDuplicadasTest(){
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2023,1,10));
        datas.add(LocalDate.of(2023, 1,11));
        datas.add(LocalDate.of(2023,1,10));
        assertNotEquals(3, datas.size());
        assertEquals(2, datas.size());
        assertTrue(datas.contains(LocalDate.of(2023,1,10)));
        assertTrue(datas.contains(LocalDate.of(2023, 1, 11)));
    }
    @Test
    void adicionarReservaParaDatasNaoConsecutivasTest(){
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2023,1,11));
        datas.add(LocalDate.of(2023, 1, 12));
        datas.add(LocalDate.of(2023,1,23));

        String mensagemEsperada = "Lista de datas inclui datas não consecutivas";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(123458756, 3, datas, new HashSet<>(), gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void adicionarReservaComQuartosVaziosTest(){
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add((LocalDate.of(2023,1,10)));

        String mensagemEsperada = "Lista de quartos vazia";
        HashSet<Integer> quartos = new HashSet<>();
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(123458756, 3, datas, quartos, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void adicionarReservaComQuartosNulosTest(){
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2023,1,10));

        String mensagemEsperada = "Lista de quarto é nula";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(123458756, 3, datas, null, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void adicionarReservaComQuartosAConterQuartoNuloTest(){
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2023,1,10));
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);
        quartos.add(null);
        String mensagemEsperada = "Lista de quartos com elemento nulo";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(123458756, 3, datas, quartos, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void adicionarQuartosDuplicadosTest(){
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);
        quartos.add(3);
        quartos.add(5);
        assertNotEquals(3, quartos.size());
        assertEquals(2, quartos.size());
        assertTrue(quartos.contains(5));
        assertTrue(quartos.contains(3));
    }
    @Test
    void adicionarReservaComQuartosIndisponiveis(){
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(3);
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2022,12,19));

        String mensagemEsperada = "Lista de quartos inclui quartos indisponíveis para as datas fornecidas";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(123458756, 3, datas, quartos, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void adicionarReservaComQuartosInexistentes(){
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);
        quartos.add(1000);
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2023,1,10));

        String mensagemEsperada = "Lista de quarto contêm quartos inexistentes";

        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(123458756,  3, datas, quartos, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void adicionarReservaParaClienteNaoRegistado(){
        HashSet<LocalDate> datas = new HashSet<>();
        HashSet<Integer> quartos = new HashSet<>();
        datas.add(LocalDate.of(2023,1,10));
        quartos.add(5);
        String mensagemEsperada = "Não existe cliente associado ao NIF fornecido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.adicionarReserva(10000, 3, datas, quartos, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    //verificarSeQuartosIndisponiveisParaDatas()
    @Test
    void verificarIndisponibilidadeComDatasInvalidasTest(){
        LocalDate dataInicial = LocalDate.of(2023, 1, 10);
        LocalDate dataFinal = LocalDate.of(2023,1,9);
        HashSet<Integer> quartos = new HashSet<>();

        String mensagemEsperada = "Data final vem antes da data inicial";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    //verificarSeAlgumQuartoInexistente()
    @Test
    void verificarQuartosInexistentesComListaNulaTest(){
        String mensagemEsperada = "Lista de quartos nula";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.contemQuartoInexistentes(null, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void verificarQuartosInexistentesComListaVaziaTest(){
        HashSet<Integer> quartos = new HashSet<>();
        String mensagemEsperada = "Lista de quartos vazia";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.contemQuartoInexistentes(quartos, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void verificarQuartosInexistentesComListaComQuartoNullTest(){
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(null);
        String mensagemEsperada = "Lista de quartos com elementos nulos";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.contemQuartoInexistentes(quartos, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
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
        HashSet<LocalDate> datas = new HashSet<>();
        LocalDate dataInicial = LocalDate.of(2023, 1, 14);
        datas.add(dataInicial);
        LocalDate dataFinal = LocalDate.of(2023,1,15);
        datas.add(dataFinal);
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);
        Reserva reservaPorFaturar = new Reserva(100, clienteNIF, empregadoID, 100, false, null);

        Exception exception;

        exception = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIFPorFaturar, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoID, datas, quartos, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(NullPointerException.class,
                ()-> gestorDeReserva.gerarFaturaParaReserva(reservaPorFaturar, gestorDeBaseDeDadosSemConexao) );
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(NullPointerException.class,
                ()-> GestorDeReserva.contemQuartoInexistentes(quartos, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(NullPointerException.class,
                ()-> GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, gestorDeBaseDeDadosSemConexao));
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    void interagirComBaseDeDadosGestorNuloTest(){
        int clienteNIF = 276953124;
        int clienteNIFPorFaturar = 253265859;
        int empregadoID = 3;
        HashSet<LocalDate> datas = new HashSet<>();
        LocalDate dataInicial = LocalDate.of(2023, 1, 14);
        datas.add(dataInicial);
        LocalDate dataFinal = LocalDate.of(2023,1,18);
        datas.add(dataFinal);
        HashSet<Integer> quartos = new HashSet<>();
        quartos.add(5);
        Reserva reservaPorFaturar = new Reserva(100, clienteNIF, empregadoID, 100, false, null);

        String expectedMessage = "Gestor de Base de Dados nulo.";
        Exception exception;

        exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, null));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIFPorFaturar, null));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.adicionarReserva(clienteNIF, empregadoID, datas, quartos, null));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.gerarFaturaParaReserva(reservaPorFaturar, null) );
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()-> GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, null));
        assertEquals(expectedMessage, exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()-> GestorDeReserva.contemQuartoInexistentes(quartos, null));
        assertEquals(expectedMessage, exception.getMessage());
    }

}