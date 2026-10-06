<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng - Admin</title>
</head>
<body>
<div class="d-flex justify-content-between align-items-center mb-3">
    <div>
        <h3 class="fw-bold text-dark mb-0">QUẢN LÝ DANH SÁCH NGƯỜI DÙNG</h3>
        <small class="text-muted">Tổng số: <strong>${totalUsers}</strong> users | Phân trang: <strong>6 user / trang</strong></small>
    </div>
    <a href="${pageContext.request.contextPath}/admin/users/create" class="btn btn-primary fw-semibold">
        <i class="bi bi-person-plus-fill me-1"></i> Thêm người dùng mới
    </a>
</div>

<!-- Thông báo nếu có -->
<c:if test="${param.message == 'create_success'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        Thêm người dùng mới thành công!
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>
<c:if test="${param.message == 'update_success'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        Cập nhật thông tin người dùng thành công!
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>
<c:if test="${param.message == 'delete_success'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        Xóa người dùng thành công!
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<div class="card shadow-sm border-0">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped align-middle mb-0">
                <thead class="table-dark">
                    <tr>
                        <th class="text-center" style="width: 70px;">Ảnh</th>
                        <th>Username</th>
                        <th>Họ và tên</th>
                        <th>Email</th>
                        <th>Điện thoại</th>
                        <th class="text-center">Vai trò</th>
                        <th class="text-center">Trạng thái</th>
                        <th class="text-center" style="width: 150px;">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty userList}">
                            <c:forEach items="${userList}" var="u">
                                <tr>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${not empty u.images && u.images != 'default.png'}">
                                                <c:choose>
                                                    <c:when test="${u.images.startsWith('http://') || u.images.startsWith('https://')}">
                                                        <img src="${u.images}" 
                                                             alt="${u.username}" class="rounded-circle border" 
                                                             style="width: 45px; height: 45px; object-fit: cover;"
                                                             onerror="this.src='https://ui-avatars.com/api/?name=${u.username}&background=random';">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/image?fname=${u.images}" 
                                                             alt="${u.username}" class="rounded-circle border" 
                                                             style="width: 45px; height: 45px; object-fit: cover;"
                                                             onerror="this.src='https://ui-avatars.com/api/?name=${u.username}&background=random';">
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://ui-avatars.com/api/?name=${u.username}&background=0d6efd&color=fff" 
                                                     alt="${u.username}" class="rounded-circle" style="width: 45px; height: 45px;">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="fw-bold">${u.username}</td>
                                    <td>${u.fullname}</td>
                                    <td>${u.email}</td>
                                    <td>${u.phone}</td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${u.admin}">
                                                <span class="badge bg-danger">ADMIN</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">USER</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${u.active}">
                                                <span class="badge bg-success">Đã kích hoạt</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-warning text-dark">Chưa kích hoạt</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/admin/users/edit?username=${u.username}" 
                                           class="btn btn-sm btn-outline-primary me-1" title="Chỉnh sửa">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/users/delete?username=${u.username}" 
                                           class="btn btn-sm btn-outline-danger" 
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa user: ${u.username}?');" 
                                           title="Xóa">
                                            <i class="bi bi-trash-fill"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8" class="text-center py-4 text-muted">
                                    Không có dữ liệu người dùng.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Phân trang 6 user / trang -->
    <c:if test="${totalPages > 1}">
        <div class="card-footer bg-white d-flex justify-content-between align-items-center py-3">
            <span class="text-muted small">
                Trang <strong>${currentPage}</strong> trên tổng số <strong>${totalPages}</strong> trang
            </span>
            <nav aria-label="Page navigation">
                <ul class="pagination pagination-sm mb-0">
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=1">&laquo;&laquo;</a>
                    </li>
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage - 1}">&laquo;</a>
                    </li>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${currentPage + 1}">&raquo;</a>
                    </li>
                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/users?page=${totalPages}">&raquo;&raquo;</a>
                    </li>
                </ul>
            </nav>
        </div>
    </c:if>
</div>
</body>
</html>
