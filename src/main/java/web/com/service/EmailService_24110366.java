	package web.com.service;
	
	import jakarta.mail.*;
	import jakarta.mail.internet.*;
	import java.io.IOException;
	import java.io.InputStream;
	import java.util.Properties;
	
	public class EmailService_24110366 {
	
	    private static final Properties emailProps = new Properties();
	
	    static {
	        try (InputStream is = EmailService_24110366.class.getClassLoader()
	                .getResourceAsStream("email.properties")) {
	            emailProps.load(is);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to load email configuration", e);
	        }
	    }
	
	   public static void sendEmailOTP(String toEmail,String username, String otp) throws MessagingException {
	        String host = emailProps.getProperty("mail.smtp.host");
	        String port = emailProps.getProperty("mail.smtp.port");
	        String password = emailProps.getProperty("mail.password");
	        String senderEmail = emailProps.getProperty("mail.from");
	
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", port);
	
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(senderEmail, password);
	            }
	        });
	
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(senderEmail));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	        message.setSubject("Your OTP Code");
	
	        String content = "<h1>Hello " + username + ",</h1>"
	                + "<p>Your OTP code is: <strong>" + otp + "</strong></p>"
	                + "<p>This code will expire in 5 minutes.</p>";
	
	        message.setContent(content, "text/html; charset=utf-8");
	
	        Transport.send(message);
	    }

	    public static void sendOrderConfirmation(String toEmail, String customerName, int orderId, String address, String totalFormatted) {
	        new Thread(() -> {
	            try {
	                String host = emailProps.getProperty("mail.smtp.host");
	                String port = emailProps.getProperty("mail.smtp.port");
	                String password = emailProps.getProperty("mail.password");
	                String senderEmail = emailProps.getProperty("mail.from");

	                Properties props = new Properties();
	                props.put("mail.smtp.auth", "true");
	                props.put("mail.smtp.starttls.enable", "true");
	                props.put("mail.smtp.host", host);
	                props.put("mail.smtp.port", port);

	                Session session = Session.getInstance(props, new Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(senderEmail, password);
	                    }
	                });

	                Message message = new MimeMessage(session);
	                message.setFrom(new InternetAddress(senderEmail));
	                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	                message.setSubject("Xác nhận đơn hàng #" + orderId + " - Thanh toán COD");

	                String content = "<h2>Kính chào " + customerName + ",</h2>"
	                        + "<p>Cảm ơn bạn đã đặt hàng tại <strong>ShopApp / VideoApp</strong>.</p>"
	                        + "<p><strong>Mã đơn hàng:</strong> #" + orderId + "</p>"
	                        + "<p><strong>Địa chỉ giao hàng:</strong> " + address + "</p>"
	                        + "<p><strong>Hình thức thanh toán:</strong> COD (Thanh toán tiền mặt khi nhận hàng)</p>"
	                        + "<p><strong>Tổng số tiền cần thanh toán:</strong> <span style='color:red;font-size:16px;'><strong>" + totalFormatted + "</strong></span></p>"
	                        + "<p>Đơn hàng của bạn đang được xử lý và sẽ được chuyển phát nhanh nhất có thể!</p>"
	                        + "<p>Trân trọng cảm ơn quý khách!</p>";

	                message.setContent(content, "text/html; charset=utf-8");
	                Transport.send(message);
	            } catch (Exception e) {
	                System.err.println("Lỗi gửi email xác nhận đơn hàng: " + e.getMessage());
	            }
	        }).start();
	    }
	}