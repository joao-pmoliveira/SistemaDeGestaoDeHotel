package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class GestorDeDatas {
    /**
     *
     * @param dataInput
     * @return Return de data introduzida validada
     */
    public static Date validarData(String dataInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(dataInput.isBlank()) return null;
        try {
            return simpleDateFormat.parse(dataInput);
        } catch (ParseException e) {
            System.out.println("Data Inválida. Formato: aaaa-mm-dd");
            return null;
        }
    }

    /**
     *
     * @param dataInicial
     * @param dataFinal
     * @return  Return de lista com todas as datas entre duas datas especificas
     */
    public static HashSet<LocalDate> obterDatasEntreDuasDatas(LocalDate dataInicial, LocalDate dataFinal){
        HashSet<LocalDate> datas = new HashSet<>();

        LocalDate dataAtual = dataInicial;
        while (dataAtual.isBefore(dataFinal) || dataAtual.isEqual(dataFinal)) {
            datas.add(dataAtual);
            dataAtual = dataAtual.plusDays(1);
        }
        return datas;
    }

    /**
     *
     * @param data
     * @return Return de date em localdate
     */
    public static LocalDate converterDateParaLocalDate(Date data){
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
