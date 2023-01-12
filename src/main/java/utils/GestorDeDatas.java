package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorDeDatas {
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

    public static List<LocalDate> obterDatasEntreDuasDatas(LocalDate dataInicial, LocalDate dataFinal){
        List<LocalDate> datas = new ArrayList<>();

        LocalDate dataAtual = dataInicial;
        while (dataAtual.isBefore(dataFinal) || dataAtual.isEqual(dataFinal)) {
            datas.add(dataAtual);
            dataAtual = dataAtual.plusDays(1);
        }
        return datas;
    }

    public static LocalDate converterDateParaLocalDate(Date data){
        return data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
