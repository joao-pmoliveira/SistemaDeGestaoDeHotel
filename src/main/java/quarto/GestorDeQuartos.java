package quarto;

import basededados.GestorDeBaseDeDados;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorDeQuartos {
    public GestorDeQuartos(){}

    public Quarto procurarQuartoPorID(int quartoId, GestorDeBaseDeDados gestorBD){
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        String query = String.format( "SELECT * FROM quarto, layout WHERE quarto.id = %d AND layout.id = quarto.layout_id", quartoId);
        List<String> dadosQuarto = gestorBD.tryQueryDatabase(query);

        if( dadosQuarto.isEmpty() ) throw new InvalidParameterException("Não existe quarto associado ao ID fornecido");

        String [] dados = dadosQuarto.get(0).split(",");
        Quarto quarto = new Quarto(quartoId, Integer.parseInt(dados[2]), Float.parseFloat(dados[5]), dados[3], dados[4]);
        return quarto;
    }

    public ArrayList<Quarto> procurarQuartoPorLayout(int layoutId, GestorDeBaseDeDados gestorBD){
        ArrayList<Quarto> quartosLayout = new ArrayList<>();
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");

        String query = String.format( "SELECT * FROM quarto, layout WHERE quarto.layout_id = %d AND layout.id = %d", layoutId, layoutId);
        List<String> dadosQuarto = gestorBD.tryQueryDatabase(query);
        if( dadosQuarto.isEmpty() ) throw new InvalidParameterException("Não existem quartos associado ao Layout fornecido");
        for (String q:dadosQuarto) {
            String [] dados = q.split(",");
            Quarto quarto = new Quarto(Integer.parseInt(dados[0]), Integer.parseInt(dados[2]), Float.parseFloat(dados[5]), dados[3], dados[4]);
            quartosLayout.add(quarto);
        }

        return quartosLayout;
    }

    public ArrayList<Quarto> procurarQuartosDisponiveis(Date dataInicial, Date dataFinal, GestorDeBaseDeDados gestorBD) {
        ArrayList<Quarto> quartosLayout = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        if(dataInicial.equals(dataFinal)) throw new InvalidParameterException("As datas são iguais.");

        String query = String.format("SELECT quarto.id, layout_id, layout.nome, layout.descricao, layout.preco_base "
                        + "FROM quarto "
                        + "LEFT JOIN dia_reserva ON quarto.id = dia_reserva.quarto_id "
                        + "AND dia_reserva.data_reserva BETWEEN '%s' AND '%s' "
                        + "LEFT JOIN layout ON quarto.layout_id = layout.id "
                        + "WHERE dia_reserva.data_reserva is null",
                simpleDateFormat.format(dataInicial),
                simpleDateFormat.format(dataFinal));

        List<String> dadosQuarto = gestorBD.tryQueryDatabase(query);
        if( dadosQuarto.isEmpty() ) throw new InvalidParameterException("Não existem quartos disponiveis no intervalo de datas fornecido");
        for (String q : dadosQuarto) {
            String[] dados = q.split(",");
            Quarto quarto = new Quarto(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), Float.parseFloat(dados[4]), dados[2], dados[3]);
            quartosLayout.add(quarto);
        }
        return quartosLayout;
    }

    public boolean adicionarQuarto(int layoutId, GestorDeBaseDeDados gestorBD){
        if(layoutId < 1 || layoutId > 6) throw new InvalidParameterException("LayoutID inválido");
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");

        String query = String.format("REPLACE INTO quarto(layout_id) VALUES ('%d')", layoutId);
        gestorBD.tryUpdateDatabase(query);
        return true;
    }
}
