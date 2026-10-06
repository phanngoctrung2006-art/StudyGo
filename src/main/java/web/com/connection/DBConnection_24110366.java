package web.com.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection_24110366 {
	public static String dbDriver = "com.mysql.cj.jdbc.Driver";
	public static String dbClass = "com.mysql.cj.jdbc.Driver";

	private static String getEnvOrDefault(String key, String defaultValue) {
		String val = System.getenv(key);
		return (val != null && !val.trim().isEmpty()) ? val.trim() : defaultValue;
	}

	public static String getDbUrl() {
		String envUrl = System.getenv("DB_URL");
		if (envUrl != null && !envUrl.trim().isEmpty()) {
			return envUrl.trim();
		}
		String host = getEnvOrDefault("DB_HOST", "localhost");
		String port = getEnvOrDefault("DB_PORT", "3306");
		String name = getEnvOrDefault("DB_NAME", "KtGiuaKi");
		return "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
	}

	public static String getDbUser() {
		return getEnvOrDefault("DB_USER", "root");
	}

	public static String getDbPassword() {
		return getEnvOrDefault("DB_PASSWORD", "123456");
	}

	public static String dbUrl = getDbUrl();
	public static String dbUser = getDbUser();
	public static String dbPassword = getDbPassword();

	public static Connection getConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName(dbDriver);
			con = DriverManager.getConnection(getDbUrl(), getDbUser(), getDbPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
