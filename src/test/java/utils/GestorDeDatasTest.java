package utils;


import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GestorDeDatasTest {
    @Test
    void validarDataParametroVazioTest(){
        assertNull(GestorDeDatas.validarData(""));
    }
    @Test
    void validarDataParametroInvalidoTest(){
        assertNull(GestorDeDatas.validarData("23042003"));
    }
    @Test
    void validarDataParametroValidoTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = simpleDateFormat.parse("2003-03-23");
        Date actualDate = GestorDeDatas.validarData("2003-03-23");
        assertEquals(expectedDate, actualDate);
    }
    @Test
    void obterDatasEntreMesmasDatasTest(){
        HashSet<LocalDate> expectedDate = new HashSet<>();
        expectedDate.add(LocalDate.parse("2003-03-23"));

        LocalDate dataInicial = LocalDate.parse("2003-03-23");
        LocalDate dataFinal = LocalDate.parse("2003-03-23");
        HashSet<LocalDate> actualDate = GestorDeDatas.obterDatasEntreDuasDatas(dataInicial, dataFinal);

        assertEquals(expectedDate, actualDate);
    }
    @Test
    void obterDatasEntreDuasDatasDiferentesTest(){
        HashSet<LocalDate> expectedDate = new HashSet<>();
        expectedDate.add(LocalDate.parse("2003-03-23"));
        expectedDate.add(LocalDate.parse("2003-03-24"));

        LocalDate dataInicial = LocalDate.parse("2003-03-23");
        LocalDate dataFinal = LocalDate.parse("2003-03-24");
        HashSet<LocalDate> actualDate = GestorDeDatas.obterDatasEntreDuasDatas(dataInicial, dataFinal);

        assertEquals(expectedDate, actualDate);
    }
    @Test
    void obterDatasEntreDuasDatasInvalidasTest(){
        HashSet<LocalDate> expectedDate = new HashSet<>();

        LocalDate dataInicial = LocalDate.parse("2003-03-24");
        LocalDate dataFinal = LocalDate.parse("2003-03-23");
        HashSet<LocalDate> actualDate = GestorDeDatas.obterDatasEntreDuasDatas(dataInicial, dataFinal);

        assertEquals(expectedDate, actualDate);
    }
    @Test
    void converterDateParaLocalDateTest(){
        Date data = GestorDeDatas.validarData("23-04-2009");
        LocalDate expectedDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate actualDate = GestorDeDatas.converterDateParaLocalDate(data);
        assertEquals(expectedDate, actualDate);
    }


}