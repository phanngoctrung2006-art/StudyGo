<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - VideoApp</title>
    <style>
        .hero-banner {
            background: linear-gradient(135deg, #0d6efd 0%, #0b5ed7 100%);
            color: #fff;
            padding: 45px 30px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        .featured-card {
            border: 1px solid #dee2e6;
            border-radius: 8px;
            background: #fff;
            transition: transform 0.2s, box-shadow 0.2s;
            overflow: hidden;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .featured-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 6px 18px rgba(0,0,0,0.1);
        }
        .featured-poster {
            height: 170px;
            background: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }
        .featured-poster img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .cat-box {
            border: 1px solid #e9ecef;
            border-radius: 8px;
            padding: 16px;
            background: #fff;
            text-align: center;
            transition: all 0.2s;
        }
        .cat-box:hover {
            border-color: #0d6efd;
            background: #f0f7ff;
        }
    </style>
</head>
<body>
<div class="container my-2">

    <!-- Thông báo thêm giỏ hàng nếu có -->
    <c:if test="${not empty sessionScope.cartMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-cart-check-fill me-1"></i> ${sessionScope.cartMessage}
            <a href="${pageContext.request.contextPath}/cart" class="fw-bold ms-2 text-decoration-none">Xem giỏ hàng &raquo;</a>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="cartMessage" scope="session"/>
    </c:if>

    <!-- 1. Hero Banner chào mừng -->
    <div class="hero-banner shadow-sm d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
        <div>
            <h2 class="fw-bold mb-2">Chào mừng đến với VideoApp!</h2>
            <p class="mb-0 fs-5 text-white-50">Hệ thống quản lý và chia sẻ video trực tuyến theo chuyên mục phong phú.</p>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/product" class="btn btn-warning btn-lg fw-bold shadow-sm">
                Khám phá Sản phẩm &raquo;
            </a>
        </div>
    </div>

    <!-- 2. Video mới & Nổi bật -->
    <div class="mb-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="fw-bold text-dark mb-0">VIDEO NỔI BẬT</h4>
            <span class="text-muted small">Tổng cộng: <strong>${totalVideos}</strong> video trong hệ thống</span>
        </div>

        <div class="row g-4">
            <c:choose>
                <c:when test="${not empty featuredVideos}">
                    <c:forEach items="${featuredVideos}" var="v">
                        <div class="col-md-4">
                            <div class="featured-card shadow-sm p-3">
                                <div>
                                    <div class="featured-poster rounded mb-3">
                                        <a href="${pageContext.request.contextPath}/video/detail?id=${v.videoId}" class="w-100 h-100 d-block">
                                            <c:choose>
                                                <c:when test="${not empty v.poster}">
                                                    <c:choose>
                                                        <c:when test="${v.poster.startsWith('http://') || v.poster.startsWith('https://')}">
                                                            <img src="${v.poster}" alt="${v.title}" onerror="this.src='https://placehold.co/400x250?text=Poster';">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/image?fname=${v.poster}" alt="${v.title}" onerror="this.src='https://placehold.co/400x250?text=Poster';">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-muted text-center pt-5">
                                                        <span>[poster]</span>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </div>

                                    <h6 class="fw-bold mb-1">
                                        <a href="${pageContext.request.contextPath}/video/detail?id=${v.videoId}" class="text-dark text-decoration-none">
                                            ${v.title}
                                        </a>
                                    </h6>
                                    <div class="small text-muted mb-1">Mã: <code>${v.videoId}</code></div>
                                    <div class="small mb-2">
                                        Danh mục: <span class="badge bg-secondary">${v.categoryName != null ? v.categoryName : 'Chưa phân loại'}</span>
                                    </div>
                                    <div class="small text-success fw-semibold">
                                        👁️ ${v.views} lượt xem
                                    </div>
                                    <div class="small text-danger fw-bold">
                                        Giá: ${v.formattedPrice}
                                    </div>
                                </div>

                                <div class="mt-2 pt-2 border-top d-flex justify-content-between align-items-center">
                                    <div>
                                        <span class="badge bg-light text-primary border me-1">🔗 ${v.shareCount}</span>
                                        <span class="badge bg-light text-danger border">❤️ ${v.likeCount}</span>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/video/detail?id=${v.videoId}" class="btn btn-sm btn-outline-primary">
                                        Chi tiết
                                    </a>
                                </div>

                                <div class="mt-2 d-grid">
                                    <a href="${pageContext.request.contextPath}/cart/add?videoId=${v.videoId}&quantity=1" class="btn btn-sm btn-warning fw-semibold">
                                        🛒 Thêm vào giỏ
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-12 text-center py-4 text-muted">
                        Chưa có video nào trong hệ thống.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/video" class="btn btn-outline-primary px-4 fw-semibold">
                Xem toàn bộ video theo từng danh mục &raquo;
            </a>
        </div>
    </div>

</div>
</body>
</html>
