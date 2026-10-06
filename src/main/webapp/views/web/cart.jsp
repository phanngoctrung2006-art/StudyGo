<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng Của Bạn - VideoApp</title>
    <style>
        .cart-img {
            width: 80px;
            height: 60px;
            object-fit: cover;
            border-radius: 4px;
        }
        .qty-input {
            width: 55px;
            text-align: center;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container my-4">
    <div class="d-flex justify-content-between align-items-center mb-3 pb-2 border-bottom">
        <h3 class="fw-bold text-dark mb-0">🛒 GIỎ HÀNG CỦA BẠN</h3>
        <a href="${pageContext.request.contextPath}/video" class="btn btn-outline-primary btn-sm">
            &laquo; Tiếp tục mua sắm
        </a>
    </div>

    <!-- Thông báo thao tác giỏ hàng nếu có -->
    <c:if test="${not empty sessionScope.cartMessage}">
        <div class="alert alert-info alert-dismissible fade show" role="alert">
            <i class="bi bi-info-circle me-1"></i> ${sessionScope.cartMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <c:remove var="cartMessage" scope="session"/>
    </c:if>

    <c:choose>
        <c:when test="${empty cart}">
            <div class="card shadow-sm border-0 text-center py-5 my-4">
                <div class="card-body">
                    <div class="display-1 text-muted mb-3">🛒</div>
                    <h4 class="fw-bold text-secondary">Giỏ hàng của bạn đang trống!</h4>
                    <p class="text-muted">Hãy khám phá các video / sản phẩm hấp dẫn và thêm vào giỏ hàng ngay nhé.</p>
                    <a href="${pageContext.request.contextPath}/video" class="btn btn-primary btn-lg mt-2 fw-semibold">
                        Khám phá sản phẩm ngay &raquo;
                    </a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <!-- Danh sách sản phẩm trong giỏ -->
                <div class="col-lg-8">
                    <div class="card shadow-sm border-0">
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle mb-0">
                                    <thead class="table-light">
                                        <tr>
                                            <th style="width: 90px;">Hình ảnh</th>
                                            <th>Sản phẩm</th>
                                            <th class="text-center" style="width: 120px;">Đơn giá</th>
                                            <th class="text-center" style="width: 170px;">Số lượng</th>
                                            <th class="text-center" style="width: 130px;">Thành tiền</th>
                                            <th class="text-center" style="width: 60px;">Xóa</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${cart.items}" var="item">
                                            <tr>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.video.poster.startsWith('http://') or  item.video.poster.startsWith('https://')}">
                                                            <img src="${item.video.poster}" alt="${item.video.title}" class="cart-img border">
                                                        </c:when>
                                                        <c:when test="${not empty item.video.poster}">
                                                            <img src="${pageContext.request.contextPath}/image?fname=${item.video.poster}" 
                                                                 alt="${item.video.title}" class="cart-img border"
                                                                 onerror="this.src='https://placehold.co/80x60?text=Item';">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="https://placehold.co/80x60?text=Item" class="cart-img border">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/video/detail?id=${item.video.videoId}" 
                                                       class="fw-bold text-dark text-decoration-none d-block">
                                                        ${item.video.title}
                                                    </a>
                                                    <small class="text-muted">Mã: ${item.video.videoId}</small>
                                                    <c:if test="${not empty item.video.categoryName}">
                                                        <br><span class="badge bg-light text-secondary border">${item.video.categoryName}</span>
                                                    </c:if>
                                                </td>
                                                <td class="text-center fw-semibold text-muted">
                                                    ${item.formattedPrice}
                                                </td>
                                                <td class="text-center">
                                                    <!-- Điều khiển số lượng với giới hạn từ 1 đến 10 -->
                                                    <form action="${pageContext.request.contextPath}/cart/update" method="post" class="d-inline">
                                                        <input type="hidden" name="videoId" value="${item.video.videoId}">
                                                        <div class="input-group input-group-sm justify-content-center">
                                                            <!-- Nút giảm -->
                                                            <a href="${pageContext.request.contextPath}/cart/update?videoId=${item.video.videoId}&action=decrease" 
                                                               class="btn btn-outline-secondary ${item.quantity <= 1 ? 'disabled' : ''}">-</a>
                                                            
                                                            <input type="number" name="quantity" value="${item.quantity}" 
                                                                   min="1" max="10" class="form-control qty-input">
                                                            
                                                            <!-- Nút tăng -->
                                                            <a href="${pageContext.request.contextPath}/cart/update?videoId=${item.video.videoId}&action=increase" 
                                                               class="btn btn-outline-secondary ${item.quantity >= 10 ? 'disabled' : ''}">+</a>
                                                            
                                                            <button type="submit" class="btn btn-sm btn-outline-primary" title="Cập nhật">
                                                                <i class="bi bi-arrow-repeat"></i>
                                                            </button>
                                                        </div>
                                                    </form>
                                                    <small class="text-muted d-block mt-1" style="font-size: 0.75rem;">(Tối đa: 10 sp)</small>
                                                </td>
                                                <td class="text-center fw-bold text-danger">
                                                    ${item.formattedTotalPrice}
                                                </td>
                                                <td class="text-center">
                                                    <a href="${pageContext.request.contextPath}/cart/delete?videoId=${item.video.videoId}" 
                                                       class="btn btn-sm btn-outline-danger" 
                                                       onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?');"
                                                       title="Xóa món này">
                                                        <i class="bi bi-trash"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer bg-white d-flex justify-content-between align-items-center py-3">
                            <a href="${pageContext.request.contextPath}/cart/clear" 
                               class="btn btn-outline-danger btn-sm"
                               onclick="return confirm('Bạn có chắc chắn muốn làm trống toàn bộ giỏ hàng?');">
                                <i class="bi bi-trash3 me-1"></i> Làm trống giỏ hàng
                            </a>	
                            <a href="${pageContext.request.contextPath}/video" class="btn btn-outline-secondary btn-sm">
                                <i class="bi bi-plus-circle me-1"></i> Mua thêm sản phẩm khác
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Tóm tắt Đơn hàng & Nút Thanh Toán COD -->
                <div class="col-lg-4">
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-primary text-white py-3">
                            <h5 class="mb-0 fw-bold">📦 TỔNG KẾT ĐƠN HÀNG</h5>
                        </div>
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Tổng số lượng:</span>
                                <span class="fw-bold">${cart.totalQuantity} sản phẩm</span>
                            </div>
                            <div class="d-flex justify-content-between mb-3 pb-3 border-bottom">
                                <span class="text-muted">Phí vận chuyển:</span>
                                <span class="text-success fw-bold">Miễn phí (Free)</span>
                            </div>
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <span class="fs-5 fw-bold">Tổng thanh toán:</span>
                                <span class="fs-4 fw-bold text-danger">${cart.formattedTotalAmount}</span>
                            </div>

                            <div class="alert alert-warning py-2 small mb-3">
                                <i class="bi bi-truck me-1"></i> Hỗ trợ phương thức thanh toán <strong>COD (Thanh toán khi nhận hàng)</strong> an toàn & tiện lợi.
                            </div>

                            <div class="d-grid gap-2">
                                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success btn-lg fw-bold shadow-sm">
                                    Tiến hành Đặt Hàng (COD) &raquo;
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
