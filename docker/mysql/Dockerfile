# ==============================================================================
# Dockerfile dành riêng cho MySQL Database độc lập
# ==============================================================================
FROM mysql:8.0

LABEL maintainer="Phan Ngoc Trung - 24110366"
LABEL description="MySQL 8.0 Database Container preloaded with KtGiuaKi data"

ENV MYSQL_ROOT_PASSWORD=123456
ENV MYSQL_DATABASE=KtGiuaKi

# Tự động nạp dữ liệu khi container khởi tạo lần đầu
COPY init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 3306
