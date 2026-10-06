<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt Hàng Thành Công - VideoApp</title>
</head>
<body>
<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow border-0 text-center p-4">
                <div class="card-body">
                    <div class="text-success mb-3" style="font-size: 4rem;">
                        <i class="bi bi-check-circle-fill"></i>
                    </div>
                    <h3 class="fw-bold text-success mb-2">🎉 ĐẶT HÀNG THÀNH CÔNG!</h3>
                    <p class="text-muted fs-5">
                        Cảm ơn bạn đã tin tưởng mua sắm. Đơn hàng của bạn đã được ghi nhận vào hệ thống.
                    </p>

                    <c:if test="${not empty order}">
                        <div class="card bg-light border-0 text-start p-3 my-4">
                            <h5 class="fw-bold border-bottom pb-2 mb-3">Thông Tin Đơn Hàng #${order.orderId}</h5>
                            <div class="row mb-2">
                                <div class="col-sm-4 text-muted">Người nhận:</div>
                                <div class="col-sm-8 fw-bold">${order.fullname} - ${order.phone}</div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-sm-4 text-muted">Địa chỉ nhận:</div>
                                <div class="col-sm-8">${order.address}</div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-sm-4 text-muted">Hình thức thanh toán:</div>
                                <div class="col-sm-8">
                                    <span class="badge bg-warning text-dark fs-6">${order.paymentMethod} (Thanh toán tiền mặt khi nhận hàng)</span>
                                </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-sm-4 text-muted">Tổng tiền cần thanh toán:</div>
                                <div class="col-sm-8 text-danger fw-bold fs-5">${order.formattedTotalAmount}</div>
                            </div>
                            <c:if test="${not empty order.note}">
                                <div class="row mb-2">
                                    <div class="col-sm-4 text-muted">Ghi chú:</div>
                                    <div class="col-sm-8 fst-italic">${order.note}</div>
                                </div>
                            </c:if>
                        </div>
                    </c:if>

                    <div class="alert alert-info py-2 small mb-4">
                        <i class="bi bi-envelope-check me-1"></i> Một email xác nhận đơn hàng chi tiết đã được gửi đến hộp thư của bạn.
                    </div>

                    <div class="d-flex justify-content-center gap-3">
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary px-4">
                            Về Trang Chủ
                        </a>
                        <a href="${pageContext.request.contextPath}/product" class="btn btn-primary px-4 fw-semibold">
                            Tiếp Tục Mua Sắm
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
