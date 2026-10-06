package web.com.utils;

import java.security.SecureRandom;
public class OTPUtils_24110366 {

    private static final SecureRandom RANDOM = new SecureRandom();

    // Sinh mã OTP dạng số với độ dài tùy chọn (mặc định phổ biến là 6 số)
    public static String generateOTP(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(RANDOM.nextInt(10)); // Lấy số ngẫu nhiên từ 0-9
        }
        return otp.toString();
    }

    // Tiện ích sinh nhanh mã 6 chữ số
    public static String generate6DigitOTP() {
        return generateOTP(6);
    }

   
}