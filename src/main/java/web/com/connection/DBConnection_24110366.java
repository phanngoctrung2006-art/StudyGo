package web.com.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class DBConnection_24110366 {
	public static String dbDriver = "com.mysql.cj.jdbc.Driver";
	public static String dbClass = "com.mysql.cj.jdbc.Driver";

	private static final Map<String, String> envMap = new HashMap<>();

	static {
		loadConfiguration();
	}

	private static void loadConfiguration() {
		// 1. Thử đọc từ ClassLoader (được Tomcat/Eclipse nạp từ src/main/resources/.env)
		try (InputStream is = DBConnection_24110366.class.getResourceAsStream("/.env")) {
			if (is != null) {
				parseStream(is);
			}
		} catch (Exception ignored) {}

		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(".env")) {
			if (is != null) {
				parseStream(is);
			}
		} catch (Exception ignored) {}

		// 2. Thử đọc từ các đường dẫn tương đối và tuyệt đối trong dự án
		String[] filePaths = {
			".env",
			"../.env",
			"../../.env",
			"D:/JAVA/KTGiuaKi/.env",
			"D:\\JAVA\\KTGiuaKi\\.env"
		};
		for (String path : filePaths) {
			File f = new File(path);
			if (f.exists() && f.isFile()) {
				try (InputStream is = new FileInputStream(f)) {
					parseStream(is);
				} catch (Exception ignored) {}
			}
		}

		// 3. Tìm ngược từ thư mục bytecode của class
		try {
			File classLoc = new File(DBConnection_24110366.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			File curr = classLoc;
			for (int i = 0; i < 6 && curr != null; i++) {
				File candidate = new File(curr, ".env");
				if (candidate.exists() && candidate.isFile()) {
					try (InputStream is = new FileInputStream(candidate)) {
						parseStream(is);
					} catch (Exception ignored) {}
					break;
				}
				curr = curr.getParentFile();
			}
		} catch (Exception ignored) {}

		// 4. Nếu có DB_URL chứa user:pass@host:port/db thì bóc tách nếu thiếu USER/PASSWORD
		String rawUrl = getEnv("DB_URL");
		if (!rawUrl.isEmpty() && rawUrl.contains("@")) {
			try {
				String afterScheme = rawUrl.substring(rawUrl.indexOf("://") + 3);
				int atIdx = afterScheme.indexOf('@');
				if (atIdx > 0) {
					String userPass = afterScheme.substring(0, atIdx);
					int colonIdx = userPass.indexOf(':');
					if (colonIdx > 0) {
						if (!envMap.containsKey("DB_USER")) {
							envMap.put("DB_USER", userPass.substring(0, colonIdx));
						}
						if (!envMap.containsKey("DB_PASSWORD")) {
							envMap.put("DB_PASSWORD", userPass.substring(colonIdx + 1));
						}
					}
				}
			} catch (Exception ignored) {}
		}
	}

	private static void parseStream(InputStream is) {
		if (is == null) return;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("#")) continue;
				int idx = line.indexOf('=');
				if (idx > 0) {
					String key = line.substring(0, idx).trim();
					String val = line.substring(idx + 1).trim();
					if ((val.startsWith("\"") && val.endsWith("\"")) || 
					    (val.startsWith("'") && val.endsWith("'"))) {
						val = val.substring(1, val.length() - 1);
					}
					if (!envMap.containsKey(key)) {
						envMap.put(key, val);
					}
				}
			}
		} catch (Exception ignored) {}
	}

	public static String getEnv(String key) {
		String val = System.getenv(key);
		if (val != null && !val.trim().isEmpty()) {
			return val.trim();
		}
		val = envMap.get(key);
		if (val != null && !val.trim().isEmpty()) {
			return val.trim();
		}
		return "";
	}

	public static String getDbUrl() {
		String customUrl = getEnv("DB_URL");
		if (!customUrl.isEmpty()) {
			String url = customUrl;
			// Chuẩn hóa mysql:// -> jdbc:mysql://
			if (url.startsWith("mysql://")) {
				if (url.contains("@")) {
					int atIdx = url.indexOf('@');
					url = "jdbc:mysql://" + url.substring(atIdx + 1);
				} else {
					url = "jdbc:" + url;
				}
			} else if (!url.startsWith("jdbc:mysql://")) {
				url = "jdbc:mysql://" + url;
			}
			url = url.replace("ssl-mode=", "sslMode=");
			if (!url.contains("sslMode=")) {
				url += (url.contains("?") ? "&" : "?") + "sslMode=REQUIRED";
			}
			if (!url.contains("serverTimezone=")) {
				url += "&serverTimezone=UTC";
			}
			if (!url.contains("characterEncoding=")) {
				url += "&characterEncoding=UTF-8";
			}
			if (!url.contains("allowPublicKeyRetrieval=")) {
				url += "&allowPublicKeyRetrieval=true";
			}
			url = url.replace("/Ktgiuaki", "/KtGiuaKi");
			return url;
		}

		String host = getEnv("DB_HOST");
		String port = getEnv("DB_PORT");
		String name = getEnv("DB_NAME");

		if (host.isEmpty()) host = "localhost";
		if (port.isEmpty()) port = "3306";
		if (name.isEmpty()) name = "KtGiuaKi";

		if ("Ktgiuaki".equalsIgnoreCase(name)) {
			name = "KtGiuaKi";
		}

		boolean isCloud = host.contains("aivencloud.com") || (!host.equals("localhost") && !host.equals("127.0.0.1") && !host.equals("db"));
		String sslParam = isCloud ? "sslMode=REQUIRED" : "useSSL=false";

		return "jdbc:mysql://" + host + ":" + port + "/" + name
				+ "?" + sslParam
				+ "&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
	}

	public static String getDbUser() {
		String user = getEnv("DB_USER");
		return !user.isEmpty() ? user : "root";
	}

	public static String getDbPassword() {
		return getEnv("DB_PASSWORD");
	}

	public static String dbUrl = getDbUrl();
	public static String dbUser = getDbUser();
	public static String dbPassword = getDbPassword();

	public static Connection getConnection() throws Exception {
		Class.forName(dbDriver);
		String url = getDbUrl();
		String user = getDbUser();
		String password = getDbPassword();
		return DriverManager.getConnection(url, user, password);
	}

	public static void main(String[] args) {
		try {
			System.out.println("URL: " + getDbUrl());
			System.out.println("User: " + getDbUser());
			Connection conn = getConnection();
			if (conn != null && !conn.isClosed()) {
				System.out.println("-> KET NOI AIVEN THANH CONG!");
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}