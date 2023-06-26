package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import main.GLaDOS;

public class DataLogger {
	public static void log(int amount) {
		GLaDOS g = GLaDOS.getInstance();
		
		String url = "jdbc:sqlite:./database.db";
		String sqlQuery1 = "INSERT INTO online_users(amount) VALUES(" + amount + ");";
		String sqlQuery2 = "INSERT INTO activity(amount) VALUES(" + g.activityCounter + ");";

        try {
            Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.executeQuery(sqlQuery1);
			stmt.executeQuery(sqlQuery2);
			g.activityCounter = 0;
        } catch (SQLException e) {
        }
	}
}
