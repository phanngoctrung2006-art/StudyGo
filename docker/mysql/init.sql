-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: KtGiuaKi
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `KtGiuaKi`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `KtGiuaKi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `KtGiuaKi`;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `CategoryId` int NOT NULL AUTO_INCREMENT,
  `Categoryname` varchar(100) DEFAULT NULL,
  `Categorycode` varchar(100) DEFAULT NULL,
  `Images` varchar(500) DEFAULT NULL,
  `Status` bit(1) DEFAULT b'1',
  PRIMARY KEY (`CategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Lập Trình Java','JAVA','java_banner.png',_binary ''),(2,'Lập Trình Web','WEB','web_banner.png',_binary ''),(3,'Cơ Sở Dữ Liệu','DATABASE','db_banner.png',_binary '');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `FavoriteId` int NOT NULL AUTO_INCREMENT,
  `LikedDate` date DEFAULT NULL,
  `VideoId` varchar(50) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FavoriteId`),
  KEY `FK_Favorites_Videos` (`VideoId`),
  KEY `FK_Favorites_Users` (`Username`),
  CONSTRAINT `FK_Favorites_Users` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Favorites_Videos` FOREIGN KEY (`VideoId`) REFERENCES `videos` (`VideoId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
INSERT INTO `favorites` VALUES (1,'2026-09-10','VID01','user01'),(2,'2026-09-12','VID01','user02'),(3,'2026-09-15','VID01','user03'),(4,'2026-09-18','VID02','user01'),(5,'2026-09-20','VID03','user04');
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetails` (
  `OrderDetailId` int NOT NULL AUTO_INCREMENT,
  `OrderId` int NOT NULL,
  `VideoId` varchar(50) NOT NULL,
  `Quantity` int NOT NULL,
  `Price` decimal(12,2) NOT NULL,
  PRIMARY KEY (`OrderDetailId`),
  KEY `OrderId` (`OrderId`),
  KEY `VideoId` (`VideoId`),
  CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`OrderId`) ON DELETE CASCADE,
  CONSTRAINT `orderdetails_ibfk_2` FOREIGN KEY (`VideoId`) REFERENCES `videos` (`VideoId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetails`
--

LOCK TABLES `orderdetails` WRITE;
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
INSERT INTO `orderdetails` VALUES (1,1,'VID01',2,150000.00),(2,1,'VID05',1,150000.00),(3,2,'VID01',9,150000.00),(4,3,'VID01',2,150000.00);
/*!40000 ALTER TABLE `orderdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `OrderId` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `OrderDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `Fullname` varchar(100) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Note` varchar(500) DEFAULT NULL,
  `TotalAmount` decimal(12,2) NOT NULL,
  `PaymentMethod` varchar(50) DEFAULT 'COD',
  `Status` varchar(50) DEFAULT 'Pending',
  PRIMARY KEY (`OrderId`),
  KEY `Username` (`Username`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'admin','2026-10-05 09:19:59','Quản Trị Viên','0901234567','988 Phạm Văn Đồng','giao ở quán ăn kế bên',450000.00,'COD','Pending (COD)'),(2,'admin','2026-10-05 09:22:23','Quản Trị Viên','0901234567','988 Phạm văn đồng','abc',1350000.00,'COD','Pending (COD)'),(3,'trung','2026-10-05 09:33:07','Phan Ngọc Trung','0343089214','988 Phạm Văn Đồng','bên quán cơm trưa',300000.00,'COD','Pending (COD)');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shares`
--

DROP TABLE IF EXISTS `shares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shares` (
  `ShareId` int NOT NULL AUTO_INCREMENT,
  `Emails` varchar(50) DEFAULT NULL,
  `SharedDate` date DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `VideoId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ShareId`),
  KEY `FK_Shares_Users` (`Username`),
  KEY `FK_Shares_Videos` (`VideoId`),
  CONSTRAINT `FK_Shares_Users` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Shares_Videos` FOREIGN KEY (`VideoId`) REFERENCES `videos` (`VideoId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shares`
--

LOCK TABLES `shares` WRITE;
/*!40000 ALTER TABLE `shares` DISABLE KEYS */;
INSERT INTO `shares` VALUES (1,'friend1@example.com','2026-09-11','user01','VID01'),(2,'friend2@example.com','2026-09-13','user02','VID01'),(3,'colleague@example.com','2026-09-16','user01','VID02'),(4,'classmate@example.com','2026-09-21','user03','VID03');
/*!40000 ALTER TABLE `shares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Phone` varchar(15) DEFAULT NULL,
  `Fullname` varchar(50) DEFAULT NULL,
  `Email` varchar(150) DEFAULT NULL,
  `Admin` bit(1) DEFAULT b'0',
  `Active` bit(1) DEFAULT b'1',
  `Images` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','123456','0901234567','Quản Trị Viên','admin@example.com',_binary '',_binary '','admin.jpg'),('admin123','123456','0343089214','Phan Ngọc Trung','phanngocquang257@gmail.com',_binary '\0',_binary '\0','default.png'),('trung','123456','0343089214','Phan Ngọc Trung','phanngoctrung2006@gmail.com',_binary '\0',_binary '','default.png'),('user01','123456','0912345678','Nguyễn Văn A','user01@example.com',_binary '\0',_binary '','avatar1.jpg'),('user02','123456','0923456789','Trần Thị B','user02@example.com',_binary '\0',_binary '','avatar2.jpg'),('user03','123456','0934567890','Lê Văn C','user03@example.com',_binary '\0',_binary '','avatar3.jpg'),('user04','123456','0945678904','Phạm Minh Do','user04@example.com',_binary '',_binary '','avatar4.jpg'),('user05','123456','0956789012','Hoàng Anh E','user05@example.com',_binary '\0',_binary '','avatar5.jpg'),('user06','123456','0967890123','Đỗ Thảo F','user06@example.com',_binary '\0',_binary '','avatar6.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videos`
--

DROP TABLE IF EXISTS `videos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videos` (
  `VideoId` varchar(50) NOT NULL,
  `Title` varchar(200) DEFAULT NULL,
  `Poster` varchar(500) DEFAULT NULL,
  `Views` int DEFAULT '0',
  `Description` varchar(500) DEFAULT NULL,
  `Active` bit(1) DEFAULT b'1',
  `CategoryId` int DEFAULT NULL,
  `Price` decimal(12,2) DEFAULT '150000.00',
  PRIMARY KEY (`VideoId`),
  KEY `FK_Videos_Category` (`CategoryId`),
  CONSTRAINT `FK_Videos_Category` FOREIGN KEY (`CategoryId`) REFERENCES `category` (`CategoryId`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videos`
--

LOCK TABLES `videos` WRITE;
/*!40000 ALTER TABLE `videos` DISABLE KEYS */;
INSERT INTO `videos` VALUES ('VID01','Hướng dẫn Servlet và JSP cơ bản','https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=500&auto=format&fit=crop&q=60',1515,'Video hướng dẫn nhập môn xây dựng web với Java Servlet và JSP.',_binary '',1,150000.00),('VID02','Kết nối CSDL MySQL với JDBC','https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=500&auto=format&fit=crop&q=60',2306,'Cách thiết lập kết nối JDBC và thao tác CRUD trên MySQL.',_binary '',1,150000.00),('VID03','Xây dựng kiến trúc MVC trong Java Web','https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=500&auto=format&fit=crop&q=60',1852,'Tổ chức mã nguồn theo mô hình 3 lớp Controller, Service, DAO.',_binary '',1,150000.00),('VID04','Cấu hình SiteMesh Decorator cho JSP','https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=500&auto=format&fit=crop&q=60',923,'Hướng dẫn sử dụng thư viện SiteMesh 3 để trang trí layout trang web.',_binary '',1,150000.00),('VID05','Thiết kế giao diện Web Responsive với CSS','https://images.unsplash.com/photo-1507238691740-187a5b1d37b8?w=500&auto=format&fit=crop&q=60',3101,'Tạo giao diện tương thích trên nhiều màn hình thiết bị.',_binary '',2,150000.00),('VID06','Tối ưu hóa truy vấn SQL Server và MySQL','https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=500&auto=format&fit=crop&q=60',1201,'Các mẹo đánh chỉ mục Index và tối ưu hóa câu truy vấn phức tạp.',_binary '',3,150000.00),('VID07','Xây dựng RESTful API với Spring Boot','https://images.unsplash.com/photo-1607799279861-4dd421887fb3?w=500&auto=format&fit=crop&q=60',4200,'Hướng dẫn thiết kế API chuẩn RESTful sử dụng Spring Boot và Hibernate.',_binary '',1,150000.00),('VID08','Quản lý Transaction trong Spring Framework','https://images.unsplash.com/photo-1556075798-4825dfaaf498?w=500&auto=format&fit=crop&q=60',1650,'Tìm hiểu cơ chế @Transactional và quản lý phiên làm việc trong Java.',_binary '',1,150000.00),('VID09','Lập trình JavaScript ES6 hiện đại','https://images.unsplash.com/photo-1579468118864-1b9ea3c0db4a?w=500&auto=format&fit=crop&q=60',5100,'Các tính năng mới trong ES6: Arrow functions, Promises, Async/Await.',_binary '',2,150000.00),('VID10','Frontend cơ bản với HTML5 và CSS3','https://images.unsplash.com/photo-1523437113738-bbd3cc89fb19?w=500&auto=format&fit=crop&q=60',2890,'Khóa học nền tảng xây dựng cấu trúc và định dạng trang web.',_binary '',2,150000.00),('VID11','Thiết kế chuẩn hóa cơ sở dữ liệu (1NF đến 3NF)','https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=500&auto=format&fit=crop&q=60',3400,'Phương pháp phân tích và chuẩn hóa bảng dữ liệu chống trùng lặp.',_binary '',3,150000.00),('VID12','Viết Stored Procedure và Trigger trong SQL','https://images.unsplash.com/photo-1504639725590-34d0984388bd?w=500&auto=format&fit=crop&q=60',2150,'Hướng dẫn tự động hóa xử lý logic nghiệp vụ ngay tại tầng cơ sở dữ liệu.',_binary '',3,150000.00);
/*!40000 ALTER TABLE `videos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-06 20:59:24
