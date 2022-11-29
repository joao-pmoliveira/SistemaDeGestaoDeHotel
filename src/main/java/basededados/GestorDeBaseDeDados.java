package basededados;

import java.sql.*;
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

    public void printResultOfQuery(String query){
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                System.out.println();
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + resultSetMetaData.getColumnName(i));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
