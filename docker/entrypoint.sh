#!/bin/bash
set -e

echo "=== [1/3] Khoi dong MySQL Service ==="
service mariadb start 2>/dev/null || service mysql start 2>/dev/null

# Đợi MySQL socket sẵn sàng
for i in $(seq 1 30); do
    if mysqladmin ping --silent 2>/dev/null || mysql -e "SELECT 1" 2>/dev/null; then
        break
    fi
    sleep 1
done

echo "=== [2/3] Cau hinh tai khoan & Database ==="
# Cấu hình mật khẩu root 123456 và phân quyền cho kết nối nội bộ lẫn bên ngoài
mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';" 2>/dev/null || true
mysql -u root -p123456 -e "CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY '123456'; GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION; FLUSH PRIVILEGES;" 2>/dev/null || true

# Nạp dữ liệu init.sql nếu database hoặc bảng users chưa tồn tại
if ! mysql -u root -p123456 -e "USE KtGiuaKi; SELECT 1 FROM users LIMIT 1;" 2>/dev/null; then
    echo "Dang import du lieu tu init.sql vao database KtGiuaKi..."
    mysql -u root -p123456 < /app/init.sql
    echo "Import du lieu thanh cong!"
else
    echo "Database KtGiuaKi da ton tai san."
fi

echo "=== [3/3] Khoi dong Apache Tomcat 10.1 ==="
exec catalina.sh run
