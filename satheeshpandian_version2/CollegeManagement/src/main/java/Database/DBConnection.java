package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
	Connection connection;

	public void InitiateDB() {
		try {
			String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
			String username = "CMS";
			String password = "$Oracle4";

			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int InsertDB(String sql, Object[] params) throws SQLException {
		PreparedStatement statement;
		String data = sql;
		statement = connection.prepareStatement(data);
		for (int i = 0; i < params.length; i++) {
			statement.setObject(i + 1, params[i]);
		}
		int result = statement.executeUpdate();
		return result;
	}

	public int DeleteDB(String sql, Object[] params) throws SQLException {
		PreparedStatement statement;
		String data = sql;
		statement = connection.prepareStatement(data);
		for (int i = 0; i < params.length; i++) {
			statement.setObject(i + 1, params[i]);
		}
		int result = statement.executeUpdate();
		return result;
	}

	public int ReadDB(String sql, Object[] params) throws SQLException {
		PreparedStatement statement;
		String data = sql;
		statement = connection.prepareStatement(data);
		for (int i = 0; i < params.length; i++) {
			statement.setObject(i + 1, params[i]);
		}
		int result = statement.executeUpdate();
		return result;
	}

	public int UpdateDB(String sql, Object[] params) throws SQLException {
		PreparedStatement statement;
		String data = sql;
		statement = connection.prepareStatement(data);
		for (int i = 0; i < params.length; i++) {
			statement.setObject(i + 1, params[i]);
		}
		int result = statement.executeUpdate();
		return result;
	}

	public Connection getConnection() {

		return connection;
	}

}
