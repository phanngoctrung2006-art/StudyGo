<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản - VideoApp</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card shadow-sm border-0 mt-2">
            <div class="card-header bg-success text-white text-center py-3">
                <h4 class="mb-0 fw-bold"> ĐĂNG KÝ TÀI KHOẢN</h4>
                <small>Kích hoạt qua mã OTP gửi đến Email</small>
            </div>
            <div class="card-body p-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label fw-semibold">Tên đăng nhập (<span class="text-danger">*</span>):</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="Nhập username" required>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label fw-semibold">Mật khẩu (<span class="text-danger">*</span>):</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu" required>
                    </div>

                    <div class="mb-3">
                        <label for="fullname" class="form-label fw-semibold">Họ và tên:</label>
                        <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Nhập họ và tên">
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label fw-semibold">Email nhận OTP (<span class="text-danger">*</span>):</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="ví dụ: example@gmail.com" required>
                    </div>

                    <div class="mb-3">
                        <label for="phone" class="form-label fw-semibold">Số điện thoại:</label>
                        <input type="text" class="form-control" id="phone" name="phone" placeholder="Nhập số điện thoại">
                    </div>

                    <div class="d-grid mt-4">
                        <button type="submit" class="btn btn-success btn-lg fw-semibold">Đăng ký & Nhận mã OTP</button>
                    </div>
                </form>

                <div class="text-center mt-3">
                    <span>Đã có tài khoản? </span>
                    <a href="${pageContext.request.contextPath}/login" class="fw-semibold text-decoration-none">Đăng nhập</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
