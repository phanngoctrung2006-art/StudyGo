<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán Đơn Hàng (COD) - VideoApp</title>
</head>
<body>
<div class="container my-4">
    <div class="mb-3 pb-2 border-bottom">
        <h3 class="fw-bold text-dark mb-0">🚚 THANH TOÁN ĐƠN HÀNG (COD)</h3>
        <small class="text-muted">Kiểm tra thông tin giao hàng và xác nhận đơn hàng thanh toán khi nhận hàng</small>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-1"></i> ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="row g-4">
        <!-- Cột trái: Form thông tin giao hàng -->
        <div class="col-lg-7">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-primary text-white py-3">
                    <h5 class="mb-0 fw-bold">📍 1. Thông Tin Nhận Hàng</h5>
                </div>
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/checkout" method="post" id="checkoutForm">
                        <div class="mb-3">
                            <label for="fullname" class="form-label fw-semibold">Họ và tên người nhận (<span class="text-danger">*</span>):</label>
                            <input type="text" class="form-control" id="fullname" name="fullname" 
                                   value="${not empty user.fullname ? user.fullname : user.username}" required>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="phone" class="form-label fw-semibold">Số điện thoại liên hệ (<span class="text-danger">*</span>):</label>
                                <input type="text" class="form-control" id="phone" name="phone" 
                                       value="${user.phone}" placeholder="ví dụ: 0912345678" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label fw-semibold">Email nhận thông báo:</label>
                                <input type="email" class="form-control bg-light" id="email" 
                                       value="${user.email}" readonly>
                                <div class="form-text">Hệ thống sẽ gửi hóa đơn xác nhận về email này.</div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label fw-semibold">Địa chỉ nhận hàng chi tiết (<span class="text-danger">*</span>):</label>
                            <textarea class="form-control" id="address" name="address" rows="2" 
                                      placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố..." required></textarea>
                        </div>

                        <div class="mb-4">
                            <label for="note" class="form-label fw-semibold">Ghi chú giao hàng (tùy chọn):</label>
                            <textarea class="form-control" id="note" name="note" rows="2" 
                                      placeholder="Ví dụ: Giao giờ hành chính, gọi trước khi giao..."></textarea>
                        </div>

                        <!-- Phương thức thanh toán COD -->
                        <h5 class="fw-bold border-top pt-3 mb-3 text-dark">💳 2. Phương Thức Thanh Toán</h5>
                        
                        <div class="border rounded p-3 mb-4 bg-light">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="codPayment" value="COD" checked>
                                <label class="form-check-label fw-bold text-success fs-6" for="codPayment">
                                    💵 Thanh toán khi nhận hàng (COD - Cash On Delivery)
                                </label>
                                <p class="text-muted small mb-0 mt-1">
                                    Quý khách sẽ thanh toán trực tiếp số tiền <strong>${cart.formattedTotalAmount}</strong> bằng tiền mặt cho nhân viên giao hàng khi nhận được bưu phẩm.
                                </p>
                            </div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center">
                            <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
                                &laquo; Quay lại giỏ hàng
                            </a>
                            <button type="submit" class="btn btn-success btn-lg fw-bold px-4 shadow">
                                <i class="bi bi-check-circle-fill me-1"></i> Xác Nhận Đặt Hàng (COD)
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Cột phải: Tóm tắt đơn hàng -->
        <div class="col-lg-5">
            <div class="card shadow-sm border-0 sticky-top" style="top: 20px;">
                <div class="card-header bg-dark text-white py-3">
                    <h5 class="mb-0 fw-bold">📦 Đơn Hàng Của Bạn (${cart.totalQuantity} món)</h5>
                </div>
                <div class="card-body p-3">
                    <div class="list-group list-group-flush mb-3" style="max-height: 320px; overflow-y: auto;">
                        <c:forEach items="${cart.items}" var="item">
                            <div class="list-group-item px-0 d-flex justify-content-between align-items-center">
                                <div class="d-flex align-items-center gap-2">
                                    <c:choose>
                                        <c:when test="${item.video.poster.startsWith('http://') || item.video.poster.startsWith('https://')}">
                                            <img src="${item.video.poster}" alt="${item.video.title}" 
                                                 class="rounded border" style="width: 50px; height: 40px; object-fit: cover;">
                                        </c:when>
                                        <c:when test="${not empty item.video.poster}">
                                            <img src="${pageContext.request.contextPath}/image?fname=${item.video.poster}" 
                                                 alt="${item.video.title}" class="rounded border" style="width: 50px; height: 40px; object-fit: cover;"
                                                 onerror="this.src='https://placehold.co/50x40?text=Item';">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="https://placehold.co/50x40?text=Item" class="rounded border" style="width: 50px; height: 40px;">
                                        </c:otherwise>
                                    </c:choose>
                                    <div>
                                        <div class="fw-semibold text-truncate" style="max-width: 170px;" title="${item.video.title}">
                                            ${item.video.title}
                                        </div>
                                        <small class="text-muted">SL: <strong>x${item.quantity}</strong></small>
                                    </div>
                                </div>
                                <span class="fw-bold text-danger">${item.formattedTotalPrice}</span>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="border-top pt-3">
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Tạm tính:</span>
                            <span class="fw-semibold">${cart.formattedTotalAmount}</span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Phí ship COD:</span>
                            <span class="text-success fw-bold">0 VNĐ (Miễn phí)</span>
                        </div>
                        <div class="d-flex justify-content-between align-items-center pt-2 border-top">
                            <span class="fs-5 fw-bold">Tổng thanh toán COD:</span>
                            <span class="fs-4 fw-bold text-danger">${cart.formattedTotalAmount}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
