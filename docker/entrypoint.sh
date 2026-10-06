#!/bin/bash
set -x

echo "=== [1/3] Khoi dong MySQL Service ==="
mkdir -p /var/run/mysqld /var/lib/mysql
chown -R mysql:mysql /var/run/mysqld /var/lib/mysql

if [ ! -d /var/lib/mysql/mysql ]; then
    mysql_install_db --user=mysql --datadir=/var/lib/mysql
    # Nếu là MySQL chính hãng: mysqld --initialize-insecure --user=mysql
fi

mysqld_safe --user=mysql > /tmp/mysql.log 2>&1 &

for i in $(seq 1 30); do
    mysqladmin ping --silent && break
    sleep 1
done

if ! mysqladmin ping --silent; then
    echo "MySQL KHONG khoi dong duoc. Log:"
    cat /tmp/mysql.log
    exit 1
fi

echo "=== [2/3] Import du lieu ==="
mysql -u root < /app/init.sql

echo "=== [3/3] Khoi dong Tomcat ==="
exec catalina.sh run