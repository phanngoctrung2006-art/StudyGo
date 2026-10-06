<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Cập nhật Người Dùng' : 'Thêm Người Dùng Mới'} - Admin</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card shadow-sm border-0">
            <div class="card-header ${isEdit ? 'bg-primary' : 'bg-success'} text-white py-3">
                <h4 class="mb-0 fw-bold">
                    <i class="bi ${isEdit ? 'bi-pencil-square' : 'bi-person-plus-fill'} me-2"></i>
                    ${isEdit ? 'CẬP NHẬT THÔNG TIN NGƯỜI DÙNG' : 'THÊM NGƯỜI DÙNG MỚI'}
                </h4>
            </div>
            <div class="card-body p-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}${isEdit ? '/admin/users/edit' : '/admin/users/create'}" 
                      method="post" enctype="multipart/form-data">
                    
                    <c:if test="${isEdit}">
                        <input type="hidden" name="existingImage" value="${user.images}">
                    </c:if>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="username" class="form-label fw-semibold">Tên đăng nhập (<span class="text-danger">*</span>):</label>
                            <input type="text" class="form-control" id="username" name="username" 
                                   value="${user.username}" ${isEdit ? 'readonly' : 'required'}>
                            <c:if test="${isEdit}">
                                <div class="form-text">Tên đăng nhập là khóa chính (không thể thay đổi).</div>
                            </c:if>
                        </div>

                        <div class="col-md-6 mb-3">
                            <label for="password" class="form-label fw-semibold">Mật khẩu (<span class="text-danger">*</span>):</label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   value="${user.password}" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="fullname" class="form-label fw-semibold">Họ và tên:</label>
                            <input type="text" class="form-control" id="fullname" name="fullname" 
                                   value="${user.fullname}">
                        </div>

                        <div class="col-md-6 mb-3">
                            <label for="email" class="form-label fw-semibold">Email (<span class="text-danger">*</span>):</label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   value="${user.email}" required>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="phone" class="form-label fw-semibold">Số điện thoại:</label>
                            <input type="text" class="form-control" id="phone" name="phone" 
                                   value="${user.phone}">
                        </div>

                        <div class="col-md-6 mb-3">
                            <label for="imageFile" class="form-label fw-semibold">Ảnh đại diện:</label>
                            <input type="file" class="form-control" id="imageFile" name="imageFile" accept="image/*">
                            <c:if test="${isEdit && not empty user.images}">
                                <div class="mt-2 d-flex align-items-center gap-2">
                                    <span class="small text-muted">Ảnh hiện tại:</span>
                                    <c:choose>
                                        <c:when test="${user.images.startsWith('http://') || user.images.startsWith('https://')}">
                                            <img src="${user.images}" 
                                                 alt="Current Avatar" class="rounded border" style="width: 40px; height: 40px; object-fit: cover;"
                                                 onerror="this.src='https://ui-avatars.com/api/?name=${user.username}';">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/image?fname=${user.images}" 
                                                 alt="Current Avatar" class="rounded border" style="width: 40px; height: 40px; object-fit: cover;"
                                                 onerror="this.src='https://ui-avatars.com/api/?name=${user.username}';">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div class="row mt-2 mb-4">
                        <div class="col-md-6">
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="admin" name="admin" value="true" 
                                       ${user.admin ? 'checked' : ''}>
                                <label class="form-check-label fw-semibold text-danger" for="admin">
                                    Quyền Quản trị viên (Admin)
                                </label>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="active" name="active" value="true" 
                                       ${(user.active || !isEdit) ? 'checked' : ''}>
                                <label class="form-check-label fw-semibold text-success" for="active">
                                   Trạng thái Kích hoạt (Active)
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">
                            <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách
                        </a>
                        <button type="submit" class="btn ${isEdit ? 'btn-primary' : 'btn-success'} fw-semibold px-4">
                            <i class="bi ${isEdit ? 'bi-check-circle' : 'bi-plus-circle'} me-1"></i>
                            ${isEdit ? 'Lưu thay đổi' : 'Thêm người dùng'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
