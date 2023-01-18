package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {

    private static final String NOME_PADRAO = "^[a-zA-Z\\u00C0-\\u017F]+(([',. -][a-zA-Z\\u00C0-\\u017F ])?[a-zA-Z\\u00C0-\\u017F]*)*$";
    private static final int TAMANHO_MIN_NOME = 5;
    private static final String MORADA_PADRAO = "^[A-Za-zºª\\u00C0-\\u017F\\d\\s-]+$";
    private static final int TAMANHO_MIN_MORADA = 5;
    private static final String PASSWORD_PADRAO = "^[^,\\s]*$";
    private static final int TAMANHO_MIN_PASSWORD = 5;
    private static final String TELEFONE_PADRAO = "^[0-9]+$";
    private static final String NIF_PADRAO = "^[0-9]+$";

    public static String validoNome(String nome){
        if(nome == null) return null;
        String nomeValido = nome.trim();
        if(nomeValido.length() < TAMANHO_MIN_NOME) return null;
        Pattern padrao = Pattern.compile(NOME_PADRAO);
        Matcher comparador = padrao.matcher(nomeValido);
        if(comparador.matches()) return nomeValido;
        else return null;
    }

    public static String validaMorada(String morada){
        if(morada == null) return null;
        String moradaValida = morada.trim();
        if(moradaValida.length() < TAMANHO_MIN_MORADA) return null;
        Pattern padrao = Pattern.compile(MORADA_PADRAO);
        Matcher comparador = padrao.matcher(moradaValida);
        if(comparador.matches()) return moradaValida;
        else return null;
    }

    public static String validaPassword(String password){
        if(password == null) return null;
        String passwordValida = password.trim();
        if(passwordValida.length() < TAMANHO_MIN_PASSWORD) return null;
        Pattern padrao = Pattern.compile(PASSWORD_PADRAO);
        Matcher comparador = padrao.matcher(passwordValida);
        if(comparador.matches()) return passwordValida;
        else return null;
    }
    public static String validaTelefone(String telefone){
        if(telefone == null) return null;
        String telefoneValido = telefone.trim();
        if(telefoneValido.isEmpty()) return null;
        Pattern padrao = Pattern.compile(TELEFONE_PADRAO);
        Matcher comparador = padrao.matcher(telefoneValido);
        if(comparador.matches()) return telefoneValido;
        else return null;
    }

    public static String validaNIF(String nif){
        if(nif == null) return null;
        String nifValido = nif.trim();
        if(nif.isEmpty()) return null;
        Pattern padrao = Pattern.compile(NIF_PADRAO);
        Matcher comparador = padrao.matcher(nifValido);
        if(comparador.matches()) return nifValido;
        else return null;
    }




}
