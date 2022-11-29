package basededados;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ValidadorDeLogin {

    HashMap<String, String> detalhesLogin;

    public ValidadorDeLogin(String ficheiro){
        detalhesLogin = new HashMap<>();

        try{
            File ficheiroDadosLogin = new File(ficheiro);
            Scanner leitorFicheiro = new Scanner(ficheiroDadosLogin);

            while(leitorFicheiro.hasNextLine()){
                String linha = leitorFicheiro.nextLine();
                String[] dados = linha.split(":");
                detalhesLogin.put(dados[0], dados[1]);
            }
            leitorFicheiro.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHostname(){ return detalhesLogin.get("hostname");}
    public String getPort(){ return detalhesLogin.get("port");}
    public String getSchema(){ return detalhesLogin.get("schema");}
    public String getUsername(){ return detalhesLogin.get("username");}
    public String getPassword(){ return detalhesLogin.get("password");}
}
