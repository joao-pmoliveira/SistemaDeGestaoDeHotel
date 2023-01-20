package quarto;

import basededados.GestorDeBaseDeDados;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GestorDeQuartos {
    public GestorDeQuartos(){}

    /**
     *
     * @param quartoId
     * @param gestorBD
     * @return Return do quarto com o id inserido pelo utilizador
     */
    public Quarto procurarQuartoPorID(int quartoId, GestorDeBaseDeDados gestorBD){
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        String query = String.format( "SELECT * FROM quarto, layout WHERE quarto.id = %d AND layout.id = quarto.layout_id", quartoId);
        List<String> dadosQuarto = gestorBD.tryQueryDatabase(query);

        if( dadosQuarto.isEmpty() ) throw new InvalidParameterException("Não existe quarto associado ao ID fornecido");

        String [] dados = dadosQuarto.get(0).split(",");
        Quarto quarto = new Quarto(quartoId, Integer.parseInt(dados[2]), Float.parseFloat(dados[5]), dados[3], dados[4]);
        return quarto;
    }

    /**
     *
     * @param layoutId
     * @param gestorBD
     * @return Return de lista com todos os quartos com o layout inserido pelo utilizador
     */
    public ArrayList<Quarto> procurarQuartoPorLayout(int layoutId, GestorDeBaseDeDados gestorBD){
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        ArrayList<Quarto> quartosLayout = new ArrayList<>();

        String query = String.format( "SELECT * FROM quarto, layout WHERE quarto.layout_id = %d AND layout.id = %d", layoutId, layoutId);
        List<String> dadosQuarto = gestorBD.tryQueryDatabase(query);
        if( dadosQuarto.isEmpty() ) throw new InvalidParameterException("Não existem quartos associado ao Layout fornecido");
        for (String quartodados:dadosQuarto) {
            String [] dados = quartodados.split(",");
            Quarto quarto = new Quarto(Integer.parseInt(dados[0]), Integer.parseInt(dados[2]), Float.parseFloat(dados[5]), dados[3], dados[4]);
            quartosLayout.add(quarto);
        }

        return quartosLayout;
    }

    /**
     *
     * @param dataInicial
     * @param dataFinal
     * @param gestorBD
     * @return  Return de lista de quartos disponiveis entre as datas introduzidas pelo utilizador
     */
    public ArrayList<Quarto> procurarQuartosDisponiveis(Date dataInicial, Date dataFinal, GestorDeBaseDeDados gestorBD) {
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        ArrayList<Quarto> quartosDisponiveis = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(dataInicial.after(dataFinal)) throw new InvalidParameterException("A data inicial é superior à final.");

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
        for (String quartodados : dadosQuarto) {
            String[] dados = quartodados.split(",");
            Quarto quarto = new Quarto(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), Float.parseFloat(dados[4]), dados[2], dados[3]);
            quartosDisponiveis.add(quarto);
        }
        return quartosDisponiveis;
    }

    /**
     *
     * @param gestorBD
     * @return Return de um HashMap com todos os layouts presentes na base de dados
     */
    public HashMap<Integer, String> getTodosLayouts(GestorDeBaseDeDados gestorBD){
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        String query = "SELECT id, nome FROM layout";
        HashMap <Integer, String> layouts = new HashMap<>();
        List<String> dadosLayout = gestorBD.tryQueryDatabase(query);
        if(dadosLayout.isEmpty()) throw new InvalidParameterException("Não existem Layouts");
        for (String layoutDados : dadosLayout) {
            String[] dados = layoutDados.split(",");
            layouts.put(Integer.parseInt(dados[0]), dados[1]);
        }
        return layouts;
    }

    /**
     *
     * @param layoutId
     * @param gestorBD
     * @return Return de true quando o utilizador é introduzido com sucesso na base de dados
     */
    public boolean adicionarQuarto(int layoutId, GestorDeBaseDeDados gestorBD){
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        String verificacaoLayout = getTodosLayouts(gestorBD).get(layoutId);
        if(verificacaoLayout == null) throw new InvalidParameterException("LayoutID não existe");

        String query = String.format("REPLACE INTO quarto(layout_id) VALUES ('%d')", layoutId);
        gestorBD.tryUpdateDatabase(query);
        return true;
    }
}
