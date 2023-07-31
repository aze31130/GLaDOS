package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import glados.GLaDOS;

public class DataLogger {
	public static void log(int amount) {
		GLaDOS g = GLaDOS.getInstance();
		
		String url = "jdbc:sqlite:./database.db";
		String sqlQuery1 = "INSERT INTO online_users(amount) VALUES(?);";
		String sqlQuery2 = "INSERT INTO activity(amount) VALUES(?);";

        try {
            Connection conn = DriverManager.getConnection(url);
			PreparedStatement pstmt1 = conn.prepareStatement(sqlQuery1);
			pstmt1.setInt(1, amount);
			pstmt1.executeUpdate();

			PreparedStatement pstmt2 = conn.prepareStatement(sqlQuery2);
			pstmt2.setInt(1, g.activityCounter);
			pstmt2.executeUpdate();

			pstmt1.close();
			pstmt2.close();
			conn.close();
			g.activityCounter = 0;
        } catch (SQLException e) {
			e.printStackTrace();
        }
	}
}
