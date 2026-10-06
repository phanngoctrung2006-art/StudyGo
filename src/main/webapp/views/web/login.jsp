<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập - VideoApp</title>
</head>
<body>
	<div class="row justify-content-center">
		<div class="col-md-5">
			<div class="card shadow-sm border-0 mt-3">
				<div class="card-header bg-primary text-white text-center py-3">
					<h4 class="mb-0 fw-bold">🔐 ĐĂNG NHẬP HỆ THỐNG</h4>
				</div>
				<div class="card-body p-4">
					<c:if test="${not empty error}">
						<div class="alert alert-danger alert-dismissible fade show"
							role="alert">
							${error}
							<button type="button" class="btn-close" data-bs-dismiss="alert"
								aria-label="Close"></button>
						</div>
					</c:if>
					<c:if test="${not empty success}">
						<div class="alert alert-success">${success}</div>
					</c:if>
					<c:if test="${not empty message}">
						<div class="alert alert-success alert-dismissible fade show"
							role="alert">
							${message}
							<button type="button" class="btn-close" data-bs-dismiss="alert"
								aria-label="Close"></button>
						</div>
					</c:if>

					<form action="${pageContext.request.contextPath}/login"
						method="post">
						<div class="mb-3">
							<label for="username" class="form-label fw-semibold">Tên
								đăng nhập:</label> <input type="text" class="form-control" id="username"
								name="username" placeholder="Nhập username" required autofocus>
						</div>

						<div class="mb-3">
							<label for="password" class="form-label fw-semibold">Mật
								khẩu:</label> <input type="password" class="form-control" id="password"
								name="password" placeholder="Nhập mật khẩu" required>
						</div>

						<div class="d-grid mt-4">
							<button type="submit" class="btn btn-primary btn-lg fw-semibold">Đăng
								nhập</button>
						</div>
					</form>

					<div class="text-center mt-3">
						<span>Chưa có tài khoản? </span> <a
							href="${pageContext.request.contextPath}/register"
							class="fw-semibold text-decoration-none">Đăng ký ngay</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
