package basededados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GestorDeBaseDeDados {
    private final String url;
    private final Properties props;
    private Connection connection;

    public GestorDeBaseDeDados(String hostname, String port, String schema, String username, String password){
        url = String.format("jdbc:mysql://%s:%s/%s",
                hostname, port, schema);

        props = new Properties();
        props.put("user", username);
        props.put("password", password);
    }

    public boolean temConexao(){
        try {
            return connection.isValid(0);
        } catch (SQLException e) {
            return false;
        }
    }

    public void tryConnectionToDataBase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public List<String> tryQueryDatabase(String query){
        List<String> resultRows = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if(i>1) stringBuilder.append(",");
                    stringBuilder.append(resultSet.getString(i));
                }

                String row = stringBuilder.toString();
                resultRows.add(row);
                stringBuilder.setLength(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultRows;
    }

    public void tryUpdateDatabase(String query){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void tryResetDatabase(){
        tryUpdateDatabase("DELETE from empregado");
        tryUpdateDatabase("DELETE from cargo");
        tryUpdateDatabase("ALTER TABLE empregado AUTO_INCREMENT = 1");
        tryUpdateDatabase("ALTER TABLE cargo AUTO_INCREMENT = 1");
        tryUpdateDatabase("INSERT INTO cargo(nome) VALUES  ('Rececionista'), ('Empregado Limpeza'), ('Recursos Humanos')");
        tryUpdateDatabase("INSERT INTO empregado(nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida, palavra_passe) VALUES " +
                "('João Lopes', '2', 'Rua das flores 150', '914568524', '123456789', '1000', '06:00:00', '16:00:00', '1234'), " +
                "('Rodrigo Martim', '3', 'Avenida D.José 10', '939753152', '456789123', '1500', '09:00:00', '17:00:00', 'abc'), " +
                "('Ana Silva', '1', 'Rua da Sacristia 83', '915831556', '357159846', '2000', '07:00:00', '15:00:00', 'qwerty'), " +
                "('Duarte Simões', '1', 'Rua do Luar 78', '937854756', '456754154', '2000', '15:00:00', '23:00:00', 'minhapass')");
        tryUpdateDatabase("DELETE FROM cliente");
        tryUpdateDatabase("INSERT INTO cliente VALUES " +
                "('123458756', 'José Feiteira', '925412236'), " +
                "('253265859', 'Manuel Ferreira', '914656765'), " +
                "('256458573', 'Anabela Silva', '933874256'), " +
                "('276953124', 'Guilherme Jorge', '928943275'), " +
                "('244555666', 'Xavier Silva', '928999777'), " +
                "('298412365', 'Mariana Borges', '966021033')");
        tryUpdateDatabase("DELETE FROM layout");
        tryUpdateDatabase("ALTER TABLE layout AUTO_INCREMENT = 1");
        tryUpdateDatabase("INSERT INTO layout(nome, descricao, preco_base) VALUES " +
                "('Casal', '1 cama de casal', '80'), " +
                "('Casal + 1', '1 cama de casal + 1 cama de solteiro', '100'), " +
                "('Solteiro Duplo', '2 camas de solteiro', '90'), " +
                "('Casal + 2', '1 cama de casal + 2 camas de solteiro', '120'), " +
                "('Casal Duplo', '2 camas de casal', '130'), " +
                "('Suite Master', '2 divisões (1 cama de casal cada)', '300')");
        tryUpdateDatabase("DELETE FROM quarto");
        tryUpdateDatabase("ALTER TABLE quarto AUTO_INCREMENT = 1");
        tryUpdateDatabase("INSERT INTO quarto(layout_id) VALUES " +
                "('1'),('1'),('1'),('1'),('1'), " +
                "('2'),('2'),('2'), " +
                "('3'),('3'),('3'),('3'), " +
                "('4'),('4'),('4'), " +
                "('5'),('5'),('5'), " +
                "('6'),('6')");
        tryUpdateDatabase("DELETE FROM fatura");
        tryUpdateDatabase("ALTER TABLE fatura AUTO_INCREMENT = 1");
        tryUpdateDatabase("INSERT INTO fatura(montante_total) VALUES ('80'),('90')");
        tryUpdateDatabase("DELETE FROM registo_limpeza");
        tryUpdateDatabase("INSERT INTO registo_limpeza VALUES " +
                "('2022-12-19 06:00:00', '1', '1'),('2022-12-19 08:30:00', '2', '1'), " +
                "('2022-12-19 12:10:00', '3', '1'),('2022-12-19 15:45:00', '4', '1'), " +
                "('2022-12-20 06:22:00', '5', '1'),('2022-12-20 08:50:00', '6', '1')");
        tryUpdateDatabase("DELETE FROM reserva;");
        tryUpdateDatabase("ALTER TABLE reserva AUTO_INCREMENT = 1");
        tryUpdateDatabase("INSERT INTO reserva(cliente_nif, empregado_id, estado_pagamento, fatura_id) VALUES " +
                "('123458756', '3', '1', '1'),('253265859', '4', '0', NULL), " +
                "('256458573', '3', '0', NULL),('276953124', '4', '1', '2'), " +
                "('276953124', '3', '0', NULL)");
        tryUpdateDatabase("DELETE FROM dia_reserva");
        tryUpdateDatabase("INSERT INTO dia_reserva(data_reserva, quarto_id, reserva_id) VALUES " +
                "('2021-12-31', '10', '4'), ('2022-12-19', '3', '3'), " +
                "('2022-12-20', '2', '1'),('2022-12-20', '3', '3'), " +
                "('2022-12-21', '3', '3'),('2022-12-22', '3', '3'), " +
                "('2022-12-23', '3', '3'),('2022-12-24', '3', '3'), " +
                "('2022-12-25', '3', '3'),('2023-02-14', '5', '2'), " +
                "('2023-02-15', '5', '2'),('2023-03-05', '10', '5'), " +
                "('2023-03-05', '11', '5')");
    }
    public void tryDeleteAllLayoutsDatabase(){
        tryUpdateDatabase("DELETE FROM layout");
        tryUpdateDatabase("ALTER TABLE layout AUTO_INCREMENT = 1");
    }
    public void tryUpdateReservasDatabase(){
        tryUpdateDatabase("DELETE FROM dia_reserva");
        tryUpdateDatabase("INSERT INTO dia_reserva(data_reserva, quarto_id, reserva_id) VALUES " +
                "('2021-12-31', '1', '4'), ('2021-12-31', '11', '3'), " +
                "('2021-12-31', '2', '1'), ('2021-12-31', '12', '3'), " +
                "('2021-12-31', '3', '3'), ('2021-12-31', '13', '3'), " +
                "('2021-12-31', '4', '4'), ('2021-12-31', '14', '3'), " +
                "('2021-12-31', '5', '1'), ('2021-12-31', '15', '3'), " +
                "('2021-12-31', '6', '3'), ('2021-12-31', '16', '3'), " +
                "('2021-12-31', '7', '3'), ('2021-12-31', '17', '3'), " +
                "('2021-12-31', '8', '3'), ('2021-12-31', '18', '3'), " +
                "('2021-12-31', '9', '3'), ('2021-12-31', '19', '3'), " +
                "('2021-12-31', '10', '3'), ('2021-12-31', '20', '5')");
    }
}
