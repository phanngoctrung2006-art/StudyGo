# ==============================================================================
# Stage 1: Build War file using Maven & Eclipse Temurin JDK 21
# ==============================================================================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml trước để cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B || true

# Copy toàn bộ mã nguồn
COPY src ./src

# Đóng gói file WAR (bỏ qua tests)
RUN mvn clean package -DskipTests -B

# ==============================================================================
# Stage 2: Runtime Environment with Tomcat 10.1 + Tích hợp MySQL Server
# ==============================================================================
FROM tomcat:10.1-jdk21-temurin

LABEL maintainer="Phan Ngoc Trung - 24110366"
LABEL description="All-in-One Container: Java Web (Tomcat 10.1) & MySQL Database"

# Cài đặt MySQL Server / MariaDB Server bên trong container
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    default-mysql-server \
    default-mysql-client && \
    rm -rf /var/lib/apt/lists/*

# Dọn dẹp các webapps mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR đã build vào thư mục webapps
# ROOT.war để truy cập trực tiếp tại http://localhost:8080/
COPY --from=build /app/target/KTGiuaKi-1.1.war /usr/local/tomcat/webapps/ROOT.war
# KTGiuaKi.war để truy cập tại http://localhost:8080/KTGiuaKi/
COPY --from=build /app/target/KTGiuaKi-1.1.war /usr/local/tomcat/webapps/KTGiuaKi.war

# Copy file SQL khởi tạo CSDL và script entrypoint
COPY docker/mysql/init.sql /app/init.sql
COPY docker/entrypoint.sh /entrypoint.sh

# Chuẩn hóa xuống dòng LF (tránh lỗi CRLF trên Windows) và cấp quyền chạy
RUN sed -i 's/\r$//' /entrypoint.sh && chmod +x /entrypoint.sh

# Mở cổng 8080 (Web Tomcat) và 3306 (MySQL Server)
EXPOSE 8080 3306

# Script tự động khởi động MySQL, nạp dữ liệu KtGiuaKi và chạy Tomcat
ENTRYPOINT ["/entrypoint.sh"]
