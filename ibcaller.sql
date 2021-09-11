-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.19 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for ibcaller
CREATE DATABASE IF NOT EXISTS `ibcaller` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ibcaller`;

-- Dumping structure for table ibcaller.contacts
CREATE TABLE IF NOT EXISTS `contacts` (
  `contact_id` bigint NOT NULL AUTO_INCREMENT,
  `contact` varchar(20) NOT NULL,
  `contact_user_id` varchar(20) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table ibcaller.contacts: ~21 rows (approximately)
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` (`contact_id`, `contact`, `contact_user_id`, `name`) VALUES
	(1, '88', NULL, 'Fraud'),
	(2, '45', NULL, 'Bada Fraud'),
	(3, '88', NULL, 'Invalid'),
	(4, '32', '1', 'Rahul'),
	(5, '99', '2', 'Bhola'),
	(6, '45', NULL, 'Loan'),
	(7, '55', NULL, 'Sonu Broker'),
	(8, '44', NULL, 'Himani'),
	(9, '46', '1', 'Sita'),
	(10, '47', NULL, 'Gyaani'),
	(11, '48', '2', 'Andrew'),
	(12, '49', '3', 'Broker'),
	(13, '50', NULL, 'John'),
	(14, '51', '3', 'Peter'),
	(15, '88', NULL, 'Friend'),
	(16, '52', NULL, 'Pasang'),
	(17, '53', NULL, 'Rohit'),
	(18, '54', '1', 'Swaroop'),
	(19, '56', NULL, 'Disha'),
	(20, '57', NULL, 'Diksha'),
	(21, '44', NULL, 'HomeBuy');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;

-- Dumping structure for table ibcaller.spam
CREATE TABLE IF NOT EXISTS `spam` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact` varchar(20) NOT NULL,
  `spamcount` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table ibcaller.spam: ~10 rows (approximately)
/*!40000 ALTER TABLE `spam` DISABLE KEYS */;
INSERT INTO `spam` (`id`, `contact`, `spamcount`) VALUES
	(2, '88', 1),
	(3, '45', 2),
	(4, '55', 2),
	(5, '46', 1),
	(6, '47', 5),
	(7, '48', 7),
	(8, '49', 2),
	(9, '50', 18),
	(10, '51', 12),
	(11, '44', 1);
/*!40000 ALTER TABLE `spam` ENABLE KEYS */;

-- Dumping structure for table ibcaller.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_contact` varchar(20) NOT NULL,
  `user_email` varchar(100) DEFAULT NULL,
  `user_password` varchar(100) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table ibcaller.user: ~6 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `user_contact`, `user_email`, `user_password`, `user_name`) VALUES
	(1, '99', 'pranav@gmail.com', '$2a$10$47rMQFrsq23Ohq2qseTYXe3EgxS5sOCswLIe4ab99sTv4.HbTmqVO', 'Pranav'),
	(2, '12', '12@gmail', '$2a$10$O.THIF7zSlbfQTYypl9J5uXKzwYgjIp5xEWHux.vkXB4VVbHSEGTy', 'Govind'),
	(3, '77', '17@gmail', '$2a$10$opJ7CMaVpfgk780Y1CPKQuGZvpaZJm0uwmpttChNaVEVT0j5tksIm', 'Amit'),
	(4, '9004655459', 'abc@gmail.com', '$2a$10$KnBADelPujKkwtddzphHvuZxsoHSbJGeW1JOnbxseDRUoM.47T/r.', 'Him'),
	(5, '90055459', NULL, '$2a$10$sa0ZTpch1d/tcuOPssxrz.8jXLCaopiS.vqVwNsqDP.CNom3yDfKC', 'Him'),
	(6, '44', 'himani@g.com', '$2a$10$65lI8A2NU2n3vu/bfkjnzu8RVkPFkO5PeGxUaeTGStXr8jz7QHXme', 'Himani');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
