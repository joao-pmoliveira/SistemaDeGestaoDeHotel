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
        System.out.println("Limpa Base");
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
    void procurarComClienteNIFReservaDeVariosDiasSemFaturaEReservaDeUmDiaComFaturaTest(){
        int clienteNIF = 276953124;

        List<Reserva> reservas = gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, gestorDeBaseDeDados);
        assertNotNull(reservas);
        assertEquals(2, reservas.size());

        Reserva reservaComFatura = reservas.get(0);
        Reserva reservaSemFatura = reservas.get(1);

        assertEquals(4, reservaComFatura.getReservaID());
        assertEquals(clienteNIF, reservaComFatura.getClienteNIF());
        assertEquals(4, reservaComFatura.getEmpregadoID());
        assertEquals(90f, reservaComFatura.getPrecoAtual());
        assertTrue(reservaComFatura.getEstadoPagamento());
        assertNotNull(reservaComFatura.getFatura());
        assertEquals(2, reservaComFatura.getFatura().getFaturaID());
        assertEquals(90f, reservaComFatura.getFatura().getMontanteFinal());
        assertEquals(reservaComFatura.getPrecoAtual(), reservaComFatura.getFatura().getMontanteFinal());

        assertEquals(5, reservaSemFatura.getReservaID());
        assertEquals(clienteNIF, reservaSemFatura.getClienteNIF());
        assertEquals(3, reservaSemFatura.getEmpregadoID());
        assertEquals(180f, reservaSemFatura.getPrecoAtual());
        System.out.println(reservaSemFatura);
        assertFalse(reservaSemFatura.getEstadoPagamento());
        assertNull(reservaSemFatura.getFatura());
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
    @Test
    void procurarReservaParaClienteNIFSemReservas(){
        int clienteNIF = 298412365;
        String mensagemEsperada = "Não existe reservas associadas ao NIF fornecido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.getTodasReservasPorClienteNIF(clienteNIF, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
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
    void procurarReservasPorFaturaDadoClienteNIFInvalidoTest(){
        int clienteNIF = 253260000;
        String mensagemEsperada = "Não existe cliente associado ao NIF fornecido";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()-> gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIF, gestorDeBaseDeDados));

        assertEquals(mensagemEsperada, exception.getMessage());
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

    @Test
    void procuraReservaPorFaturarEFazFaturaTest(){
        int clienteNIF = 244555666;
        int reservasEsperadas = 1;
        HashSet<Integer> quartos = new HashSet<>();
        HashSet<LocalDate> datas = new HashSet<>();
        datas.add(LocalDate.of(2022,2,14));
        datas.add(LocalDate.of(2022,2,15));
        quartos.add(20);
        quartos.add(19);

        gestorDeReserva.adicionarReserva(clienteNIF, 3, datas, quartos, gestorDeBaseDeDados);

        List<Reserva> reservas = gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIF,gestorDeBaseDeDados);
        assertNotNull(reservas);
        assertEquals(reservasEsperadas, reservas.size());
        Reserva reserva = reservas.get(0);
        assertNotNull(reserva);

        assertEquals(6, reserva.getReservaID());
        assertEquals(clienteNIF, reserva.getClienteNIF());
        assertEquals(3, reserva.getEmpregadoID());
        assertEquals(1200f, reserva.getPrecoAtual());
        assertFalse(reserva.getEstadoPagamento());
        assertNull(reserva.getFatura());

        Fatura fatura = gestorDeReserva.gerarFaturaParaReserva(reserva, gestorDeBaseDeDados);
        assertNotNull(fatura);
        System.out.println(fatura);

        Exception exception = assertThrows(InvalidParameterException.class,
                ()->gestorDeReserva.getReservasPorFaturarPorClienteNif(clienteNIF, gestorDeBaseDeDados));
        assertEquals("Não existem reservas por faturar para o NIF dado", exception.getMessage());

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
        quartos.add(3);

        String mensagemEsperada = "Data final vem antes da data inicial";
        Exception exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, gestorDeBaseDeDados));
        assertEquals(mensagemEsperada, exception.getMessage());
    }
    @Test
    void verificarIndisponibilidadeComQuartosInvalidosTest(){
        LocalDate dataInicial = LocalDate.of(2023,1,11);
        LocalDate dataFinal = LocalDate.of(2023,1,12);
        HashSet<Integer> quartos = new HashSet<>();

        Exception exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(null, dataInicial, dataFinal, gestorDeBaseDeDados));
        assertEquals("Lista de quartos nula", exception.getMessage());

        exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, gestorDeBaseDeDados));
        assertEquals("Lista de quartos vazia", exception.getMessage());

        quartos.add(null);
        exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, gestorDeBaseDeDados));
        assertEquals("Lista de quarto contem elemento nulo", exception.getMessage());

        quartos.clear();
        quartos.add(-1);
        exception = assertThrows(InvalidParameterException.class,
                ()->GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, gestorDeBaseDeDados));
        assertEquals("Lista de quarto contêm quartos inexistentes", exception.getMessage());
    }
    @Test
    void verificarIndisponibilidadeTest(){
        HashSet<Integer> quartosDisponiveis = new HashSet<>();
        HashSet<Integer> quartosIndisponiveis = new HashSet<>();
        LocalDate dataInicial = LocalDate.of(2022,12,19);
        LocalDate dataFinal = LocalDate.of(2022,12,21);

        quartosIndisponiveis.add(2);
        quartosIndisponiveis.add(3);
        quartosDisponiveis.add(7);
        quartosDisponiveis.add(8);

        assertFalse(GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartosDisponiveis, dataInicial, dataFinal,gestorDeBaseDeDados));
        assertTrue(GestorDeReserva.verificarSeQuartosIndisponiveisParaDatas(quartosIndisponiveis, dataInicial,dataFinal,gestorDeBaseDeDados));
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
    void verificarQuartosInexistentesTest(){
        HashSet<Integer> quartosInexistentes = new HashSet<>();
        HashSet<Integer> quartosExistentes = new HashSet<>();
        quartosInexistentes.add(-1);
        quartosInexistentes.add(200);
        quartosExistentes.add(1);
        quartosExistentes.add(10);

        assertFalse(GestorDeReserva.contemQuartoInexistentes(quartosExistentes,gestorDeBaseDeDados));
        assertTrue(GestorDeReserva.contemQuartoInexistentes(quartosInexistentes,gestorDeBaseDeDados));
    }

    //Gestor de Base de Dados e Conexão
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