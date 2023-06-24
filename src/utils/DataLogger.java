package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataLogger {
	public static void log(int amount) {
		
		String url = "jdbc:sqlite:./database.db";
		String sqlQuery = "INSERT INTO online_users(amount) VALUES(" + amount + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
        }
	}
}
