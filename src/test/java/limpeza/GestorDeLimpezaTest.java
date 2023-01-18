package limpeza;

import org.junit.jupiter.api.Test;
import basededados.GestorDeBaseDeDados;
import basededados.ValidadorDeLogin;
import org.junit.jupiter.api.BeforeAll;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;



class GestorDeLimpezaTest {

    static GestorDeBaseDeDados gestorDeBaseDeDados;
    static GestorDeLimpeza gestorDeLimpeza;



    @BeforeAll //Validar o Login Da Base De dados
    static void startup() {
        ValidadorDeLogin validadorDeLogin = new ValidadorDeLogin("src/main/java/LoginData");
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
    void procurarLimpezacomBDInválida() {
        String expectedMessage = "Base de Dados Nula!!";

        //QuartosId
        Exception exceptionQuarto = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorQuarto(1, null));
        assertEquals(expectedMessage, exceptionQuarto.getMessage());

        //EmpregadosId
        Exception exceptionempregado = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorEmpregadoId(1, null));
        assertEquals(expectedMessage, exceptionempregado.getMessage());

        //Data
        Exception exceptiondata = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorData("2022-12-19", null));
        assertEquals(expectedMessage, exceptiondata.getMessage());

        //Adicionar Registo
        Exception exceptionAdicionarLimpeza = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.adicionarRegisto("2023-01-01", 1, 1,  null));
        assertEquals(expectedMessage, exceptionAdicionarLimpeza.getMessage());

    }

    @Test
    void procurarQuartoNulo() {
        String expectedMessage = "Não existe limpeza com o quarto associado";

        Exception exceptionQuartoNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorQuarto(0, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartoNulo.getMessage());
    }

    @Test
    void procurarQuartoNegativo() {
        String expectedMessage = "Número de Quarto não pode ser Negativo!!";

        Exception exceptionQuartoNegativo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorQuarto(-1, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionQuartoNegativo.getMessage());
    }

    @Test
    void procurarQuartoVálido(){
        RegistoDeLimpeza validolimpeza = new RegistoDeLimpeza("2022-12-19",1,1);
        ArrayList<RegistoDeLimpeza> procurarlimpeza =gestorDeLimpeza.procurarRegistosPorQuarto(1, gestorDeBaseDeDados);

        for (int tamanho = 0; tamanho < procurarlimpeza.size(); tamanho++) {
            assertNotNull(procurarlimpeza);
            assertEquals(validolimpeza.getQuartoId(), procurarlimpeza.get(tamanho).getQuartoId());
            assertEquals(validolimpeza.getEmpregadoId(), procurarlimpeza.get(tamanho).getEmpregadoId());
            assertEquals(validolimpeza.getData(), procurarlimpeza.get(tamanho).getData());

        }


    }

    @Test
    void procurarEmpregadoNulo() {
        String expectedMessage = "Não existe limpeza com o empregado associado";

        Exception exceptionEmpregadoNulo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorEmpregadoId(0, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionEmpregadoNulo.getMessage());
    }

    @Test
    void procurarEmpregadoNegativo() {
        String expectedMessage = "Número do Empregado não pode ser Negativo!!";

        Exception exceptionEmpregadoNegativo = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorEmpregadoId(-1, gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionEmpregadoNegativo.getMessage());
    }

    @Test
    void procurarEmpregadoVálido(){
        RegistoDeLimpeza validolimpeza = new RegistoDeLimpeza("2022-12-19",1,1);
        ArrayList<RegistoDeLimpeza> procurarlimpeza =gestorDeLimpeza.procurarRegistosPorEmpregadoId(1, gestorDeBaseDeDados);

        for (int tamanho = 0; tamanho < procurarlimpeza.size(); tamanho++) {
            assertNotNull(procurarlimpeza);
            assertEquals(validolimpeza.getQuartoId(), procurarlimpeza.get(tamanho).getQuartoId());
            assertEquals(validolimpeza.getEmpregadoId(), procurarlimpeza.get(tamanho).getEmpregadoId());
            assertEquals(validolimpeza.getData(), procurarlimpeza.get(tamanho).getData());

        }
    }

    @Test
    void procurarDataInválida() {
        String expectedMessage = "Não existe limpeza com essa data associada";

        Exception exceptionDataNula = assertThrows(InvalidParameterException.class,
                ()-> gestorDeLimpeza.procurarRegistosPorData("0000-00-00", gestorDeBaseDeDados));
        assertEquals(expectedMessage, exceptionDataNula.getMessage());
    }

    @Test
    void procurarDataVálida(){
        RegistoDeLimpeza validolimpeza = new RegistoDeLimpeza("2022-12-19",1,1);
        ArrayList<RegistoDeLimpeza> procurarlimpeza =gestorDeLimpeza.procurarRegistosPorData("2022-12-19", gestorDeBaseDeDados);

        for (int tamanho = 0; tamanho < procurarlimpeza.size(); tamanho++) {
            assertNotNull(procurarlimpeza);
            assertEquals(validolimpeza.getQuartoId(), procurarlimpeza.get(tamanho).getQuartoId());
            assertEquals(validolimpeza.getEmpregadoId(), procurarlimpeza.get(tamanho).getEmpregadoId());
            assertEquals(validolimpeza.getData(), procurarlimpeza.get(tamanho).getData());

        }
    }


}