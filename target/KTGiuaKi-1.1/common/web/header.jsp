<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark tech-navbar px-4 py-2 sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold fs-4 tech-brand d-flex align-items-center" href="${pageContext.request.contextPath}/home">
            <i class="bi bi-cpu text-info me-2 fs-3"></i>
            <span>VIDEO<span class="text-info">APP</span></span>
        </a>
        <button class="navbar-toggler border-info" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-3">
                <li class="nav-item">
                    <a class="nav-link text-light fw-semibold px-3" href="${pageContext.request.contextPath}/home">
                        <i class="bi bi-house-door me-1"></i> Trang Chủ
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-light fw-semibold px-3" href="${pageContext.request.contextPath}/video">
                        <i class="bi bi-collection-play me-1"></i> Sản phẩm
                    </a>
                </li>
                <!-- Menu Trang quản trị: Chỉ hiển thị khi đăng nhập với vai trò Admin -->
                <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
                    <li class="nav-item">
                        <a class="nav-link text-warning fw-bold px-3" href="${pageContext.request.contextPath}/admin/home">
                            <i class="bi bi-shield-lock-fill me-1"></i> Trang quản trị
                        </a>
                    </li>
                </c:if>
            </ul>

            <div class="d-flex align-items-center gap-2">
                <!-- Nút Giỏ Hàng với Badge số lượng -->
                <a class="btn btn-outline-warning fw-semibold position-relative me-2 border-warning shadow-sm" href="${pageContext.request.contextPath}/cart">
                    <i class="bi bi-cart3 me-1"></i> Giỏ Hàng
                    <span class="badge bg-danger rounded-pill ms-1">
                        ${sessionScope.cart != null ? sessionScope.cart.totalQuantity : 0}
                    </span>
                </a>

                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span class="text-light me-2 small">
                            <i class="bi bi-person-circle text-info me-1"></i> Xin chào, 
                            <strong class="text-info">${sessionScope.user.fullname != null ? sessionScope.user.fullname : sessionScope.user.username}</strong>
                            <c:if test="${sessionScope.user.admin}">
                                <span class="badge bg-danger ms-1">Admin</span>
                            </c:if>
                        </span>
                        <a class="btn btn-outline-danger btn-sm" href="${pageContext.request.contextPath}/logout">
                            <i class="bi bi-box-arrow-right me-1"></i> Đăng xuất
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-outline-info btn-sm px-3" href="${pageContext.request.contextPath}/login">
                            <i class="bi bi-box-arrow-in-right me-1"></i> Đăng nhập
                        </a>
                        <a class="btn btn-info btn-sm fw-semibold text-dark px-3" href="${pageContext.request.contextPath}/register">
                            <i class="bi bi-person-plus me-1"></i> Đăng ký
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>