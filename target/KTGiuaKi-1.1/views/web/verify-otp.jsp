<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác thực mã OTP - VideoApp</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card shadow-sm border-0 mt-3">
            <div class="card-header bg-warning text-dark text-center py-3">
                <h4 class="mb-0 fw-bold">XÁC THỰC MÃ OTP</h4>
                <small>Nhập mã OTP 6 số đã được gửi qua email của bạn</small>
            </div>
            <div class="card-body p-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <div class="alert alert-info py-2 small">
                    Mã OTP có hiệu lực trong vòng <strong>5 phút</strong>. Vui lòng kiểm tra hộp thư đến hoặc thư mục Spam.
                </div>

                <form action="${pageContext.request.contextPath}/verify-otp" method="post">
                    <div class="mb-3 text-center">
                        <label for="otp" class="form-label fw-bold fs-5">Mã OTP (6 chữ số):</label>
                        <input type="text" class="form-control form-control-lg text-center fw-bold fs-4 letter-spacing-2" 
                               id="otp" name="otp" maxlength="6" placeholder="------" required autofocus>
                    </div>

                    <div class="d-grid mt-4">
                        <button type="submit" class="btn btn-warning btn-lg fw-bold">Xác nhận kích hoạt</button>
                    </div>
                </form>

                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/register" class="text-muted small">Quay lại trang Đăng ký</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
