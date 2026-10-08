package web.com.service;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class EmailService_24110366 {

    private static final String API_URL = "https://api.brevo.com/v3/smtp/email";

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("D:/JAVA/KTGiuaKi")   // thư mục chứa file .env
            .ignoreIfMissing()
            .load();
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void sendEmailOTP(String toEmail, String username, String otp) throws MessagingException {
        String content = "<h1>Hello " + html(username) + ",</h1>"
                + "<p>Your OTP code is: <strong>" + html(otp) + "</strong></p>"
                + "<p>This code will expire in 5 minutes.</p>";
        send(toEmail, "Your OTP Code", content);
    }

    public static void sendOrderConfirmation(String toEmail, String customerName, int orderId,
                                             String address, String totalFormatted) {
        new Thread(() -> {
            try {
                String content = "<h2>Kính chào " + html(customerName) + ",</h2>"
                        + "<p>Cảm ơn bạn đã đặt hàng tại <strong>ShopApp / VideoApp</strong>.</p>"
                        + "<p><strong>Mã đơn hàng:</strong> #" + orderId + "</p>"
                        + "<p><strong>Địa chỉ giao hàng:</strong> " + html(address) + "</p>"
                        + "<p><strong>Hình thức thanh toán:</strong> COD (Thanh toán tiền mặt khi nhận hàng)</p>"
                        + "<p><strong>Tổng số tiền cần thanh toán:</strong> <span style='color:red;font-size:16px;'><strong>"
                        + html(totalFormatted) + "</strong></span></p>"
                        + "<p>Đơn hàng của bạn đang được xử lý và sẽ được chuyển phát nhanh nhất có thể!</p>"
                        + "<p>Trân trọng cảm ơn quý khách!</p>";
                send(toEmail, "Xác nhận đơn hàng #" + orderId + " - Thanh toán COD", content);
            } catch (Exception e) {
                System.err.println("Lỗi gửi email xác nhận đơn hàng: " + e.getMessage());
            }
        }).start();
    }

    private static void send(String toEmail, String subject, String htmlContent) throws MessagingException {
        String apiKey = dotenv.get("BREVO_API_KEY");
        String senderEmail = dotenv.get("BREVO_SENDER_EMAIL");
        String senderName = dotenv.get("BREVO_SENDER_NAME", "Electronics Store");

        if (apiKey == null || apiKey.isBlank() || senderEmail == null || senderEmail.isBlank()) {
            throw new MessagingException("Thiếu BREVO_API_KEY hoặc BREVO_SENDER_EMAIL");
        }

        String body = "{\"sender\":{\"name\":" + q(senderName) + ",\"email\":" + q(senderEmail) + "},"
                + "\"to\":[{\"email\":" + q(toEmail) + "}],"
                + "\"subject\":" + q(subject) + ","
                + "\"htmlContent\":" + q(htmlContent) + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofSeconds(15))
                .header("api-key", apiKey)
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 300) {
                throw new MessagingException("Brevo lỗi " + res.statusCode() + ": " + res.body());
            }
        } catch (IOException e) {
            throw new MessagingException("Không kết nối được Brevo: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagingException("Gửi email bị gián đoạn", e);
        }
    }

    // Đóng gói chuỗi thành JSON string hợp lệ
    private static String q(String s) {
        if (s == null) return "\"\"";
        StringBuilder sb = new StringBuilder("\"");
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
                }
            }
        }
        return sb.append('"').toString();
    }

    // Tránh chèn HTML từ dữ liệu người dùng vào nội dung email
    private static String html(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}