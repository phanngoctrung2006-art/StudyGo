# ==============================================================================
# Stage 1: Build War file using Maven & Eclipse Temurin JDK 21
# ==============================================================================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml trước để cache maven dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B || true

# Copy toàn bộ mã nguồn
COPY src ./src

# Biên dịch và đóng gói ứng dụng thành file WAR
RUN mvn clean package -DskipTests -B

# ==============================================================================
# Stage 2: Runtime Environment with Apache Tomcat 10.1 (Jakarta EE 10 / Servlet 6.0)
# ==============================================================================
FROM tomcat:10.1-jdk21-temurin

LABEL maintainer="Phan Ngoc Trung - 24110366"
LABEL description="Docker container for Java Web Servlet & JSP project (KTGiuaKi)"

# Xóa các ứng dụng mẫu mặc định trong webapps của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR đã build từ Stage 1
# 1. Triển khai dưới dạng ROOT.war để truy cập trực tiếp tại: http://localhost:8080/
COPY --from=build /app/target/KTGiuaKi-1.1.war /usr/local/tomcat/webapps/ROOT.war

# 2. Đồng thời copy thành KTGiuaKi.war để truy cập tại: http://localhost:8080/KTGiuaKi/
COPY --from=build /app/target/KTGiuaKi-1.1.war /usr/local/tomcat/webapps/KTGiuaKi.war

# Mở cổng mặc định của Tomcat
EXPOSE 8080

# Chạy Tomcat ở chế độ foreground
CMD ["catalina.sh", "run"]
