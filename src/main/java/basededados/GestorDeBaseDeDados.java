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
}
