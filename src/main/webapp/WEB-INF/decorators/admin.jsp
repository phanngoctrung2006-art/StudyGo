<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title"/> - Trang Quản Trị</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <sitemesh:write property="head"/>
    <style>
        .admin-sidebar {
            min-height: calc(100vh - 56px);
            background-color: #212529;
        }
        .admin-sidebar .nav-link {
            color: #adb5bd;
            font-size: 0.95rem;
            padding: 0.75rem 1rem;
            border-radius: 0.375rem;
            margin-bottom: 0.25rem;
        }
        .admin-sidebar .nav-link:hover, .admin-sidebar .nav-link.active {
            color: #fff;
            background-color: #0d6efd;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

    <!-- Admin Top Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3 shadow-sm">
        <a class="navbar-brand fw-bold text-warning" href="${pageContext.request.contextPath}/admin/users">
 HỆ THỐNG QUẢN TRỊ ADMIN
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="adminNavbar">
            <ul class="navbar-nav align-items-center gap-2">
                <li class="nav-item">
                    <span class="navbar-text text-light me-2">
                        Xin chào, <strong>${sessionScope.user != null ? sessionScope.user.fullname : 'Admin'}</strong>
                    </span>
                </li>
                <li class="nav-item">
                    <a class="btn btn-outline-danger btn-sm" href="${pageContext.request.contextPath}/logout">
                        <i class="bi bi-box-arrow-right"></i> Đăng xuất
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Main Content with Sidebar -->
    <div class="container-fluid flex-grow-1">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 admin-sidebar p-3 text-white">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">
                            <i class="bi bi-people-fill me-2"></i> Danh sách Users
                        </a>
                    </li>
                    <li class="nav-item mt-3 border-top pt-2">
                        <a class="nav-link text-info" href="${pageContext.request.contextPath}/home">
                            <i class="bi bi-house-door me-2"></i> Về Trang chủ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">
                            <i class="bi bi-box-arrow-right me-2"></i> Đăng xuất
                        </a>
                    </li>
                </ul>
            </div>

            <!-- Page Body -->
            <main class="col-md-9 col-lg-10 p-4">
                <sitemesh:write property="body"/>
            </main>
        </div>
    </div>

    <!-- Admin Footer -->
    <footer class="mt-auto">
        <%@ include file="/common/web/footer.jsp"%>
    </footer>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
