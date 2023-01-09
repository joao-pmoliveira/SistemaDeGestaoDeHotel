package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestorDeDatas {
    public static Date validarData(String dataInput){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(dataInput.isBlank()) return null;
        try {
            Date data = simpleDateFormat.parse(dataInput);
            return data;
        } catch (ParseException e) {
            System.out.println("Data Inválida. Formato: aaaa-mm-dd");
            return null;
        }
    }
}
