-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 07, 2018 at 04:27 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE mechanic_project;

CREATE DATABASE mechanic_project;

--
-- Database: `mechanic_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `APT_NUM` int(11) NOT NULL,
  `VIN` varchar(17) NOT NULL,
  `SHIFT_NUM` int(11) NOT NULL,
  `SERV_NUM` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`APT_NUM`, `VIN`, `SHIFT_NUM`, `SERV_NUM`) VALUES
(1, 'YS3FA4CY1B1306923', 1, 1),
(2, 'JAC12345678900990', 2, 2),
(3, '1G1YY12S745106110', 5, 2),
(4, 'JAC12345678900990', 5, 1),
(5, 'KM8JN12D78U948438', 6, 3),
(6, 'JNAMA43HXYGF09562', 7, 5),
(7, '1G1YY12S745106110', 8, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `CUS_NUM` int(5) NOT NULL,
  `CUS_FNAME` varchar(30) NOT NULL,
  `CUS_LNAME` varchar(20) NOT NULL,
  `CUS_CONTACT` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CUS_NUM`, `CUS_FNAME`, `CUS_LNAME`, `CUS_CONTACT`) VALUES
(1, 'Daniel', 'Yu', '248-213-4356'),
(2, 'Michael', 'Krajcev', '248-456-7890'),
(3, 'Jason', 'Yang', '248-248-2248'),
(4, 'Alice', 'Aloe', '248-123-0000'),
(5, 'Bob', 'Burns', '248-123-0001'),
(6, 'Charlie', 'Carrow', '248-123-0002'),
(7, 'Doug', 'Dimmadome', '248-123-0003'),
(8, 'Eric', 'Ellis', '248-123-0004'),
(9, 'Fiona', 'Fowler', '248-123-0005'),
(10, 'Greg', 'Gore', '248-123-0006'),
(11, 'Hannah', 'Harris', '248-123-0007'),
(12, 'Irene', 'Ick', '248-123-0008'),
(13, 'John', 'Johnson', '248-123-0009');

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `VIN` varchar(17) NOT NULL,
  `CUS_NUM` int(5) NOT NULL,
  `VCL_NUM` int(5) NOT NULL,
  `OWN_MILES` int(6) NOT NULL,
  `OWN_RECORD` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `owner`
--

INSERT INTO `owner` (`VIN`, `CUS_NUM`, `VCL_NUM`, `OWN_MILES`, `OWN_RECORD`) VALUES
('1FBHS31Y3GHA42455', 6, 12, 30980, 'n/a'),
('1FUPBLYB0RL458593', 10, 11, 76687, 'n/a'),
('1G1YY12S745106110', 1, 13, 1234, 'Slight water damage'),
('1GBHK74629F106761', 6, 6, 45565, 'n/a'),
('2GTEC19Z671122185', 12, 7, 56789, 'n/a'),
('3GCPCPEH9EG334737', 4, 4, 23880, 'n/a'),
('4S4BRBCC9D1353669', 4, 8, 34009, 'n/a'),
('JAC12345678900990', 1, 2, 165000, 'Involved in head-on collision in 2012'),
('JHS28390173284938', 3, 3, 120000, 'High mileage'),
('JNAMA43HXYGF09562', 9, 9, 45090, 'n/a'),
('KM8JN12D78U948438', 13, 5, 67544, 'n/a'),
('SALGV3TF4FA115374', 7, 10, 45667, 'n/a'),
('YS3FA4CY1B1306923', 1, 1, 50546, 'n/a');

-- --------------------------------------------------------

--
-- Table structure for table `recommendation`
--

CREATE TABLE `recommendation` (
  `REC_NUM` int(255) NOT NULL,
  `VIN` varchar(255) NOT NULL,
  `SERV_NUM` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `recommendation`
--

INSERT INTO `recommendation` (`REC_NUM`, `VIN`, `SERV_NUM`) VALUES
(1, 'YS3FA4CY1B1306923', 7),
(2, 'JHS28390173284938', 6),
(3, 'JHS28390173284938', 1),
(4, 'JAC12345678900990', 1);

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `SHIFT_NUM` int(11) NOT NULL,
  `SHIFT_DAY` date NOT NULL,
  `SHIFT_TIME` time NOT NULL,
  `SHIFT_TIME_END` time NOT NULL,
  `EMP_NUM` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`SHIFT_NUM`, `SHIFT_DAY`, `SHIFT_TIME`, `SHIFT_TIME_END`, `EMP_NUM`) VALUES
(1, '2018-04-09', '08:30:00', '12:00:00', 1),
(2, '2018-04-09', '12:00:00', '16:00:00', 4),
(3, '2018-04-09', '16:00:00', '20:00:00', 5),
(4, '2018-04-10', '08:00:00', '12:00:00', 3),
(5, '2018-04-10', '12:00:00', '16:00:00', 2),
(6, '2018-04-10', '16:00:00', '20:00:00', 1),
(7, '2018-04-11', '08:00:00', '12:00:00', 6),
(8, '2018-04-11', '12:00:00', '16:00:00', 6),
(9, '2018-04-11', '16:00:00', '20:00:00', 5),
(10, '2018-04-12', '08:00:00', '12:00:00', 1),
(11, '2018-04-12', '12:00:00', '16:00:00', 4),
(12, '2018-04-12', '16:00:00', '20:00:00', 6),
(13, '2018-04-13', '08:00:00', '12:00:00', 2),
(14, '2018-04-13', '12:00:00', '16:00:00', 5),
(15, '2018-04-13', '16:00:00', '20:00:00', 6),
(16, '2018-04-14', '10:00:00', '14:00:00', 4),
(17, '2018-04-14', '14:00:00', '18:00:00', 3),
(18, '2018-04-15', '10:00:00', '14:00:00', 5),
(19, '2018-04-15', '14:00:00', '18:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `SERV_NUM` int(255) NOT NULL,
  `SERV_DESC` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`SERV_NUM`, `SERV_DESC`) VALUES
(1, 'Scheduled maintenance'),
(2, '15-point inspection'),
(3, 'Oil Change'),
(4, 'Tire Alignment'),
(5, 'Tire change'),
(6, 'Replace air filter'),
(7, 'Flush break fluid');

-- --------------------------------------------------------

--
-- Table structure for table `technician`
--

CREATE TABLE `technician` (
  `EMP_NUM` int(255) NOT NULL,
  `EMP_FNAME` varchar(10) NOT NULL,
  `EMP_LNAME` varchar(10) NOT NULL,
  `EMP_CONTACT` varchar(11) NOT NULL,
  `EMP_RATING` double NOT NULL,
  `EMP_RATING_COUNT` int(4) NOT NULL,
  `EMP_WAGE` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `technician`
--

INSERT INTO `technician` (`EMP_NUM`, `EMP_FNAME`, `EMP_LNAME`, `EMP_CONTACT`, `EMP_RATING`, `EMP_RATING_COUNT`, `EMP_WAGE`) VALUES
(1, 'Sean', 'Dallas', '248-000-000', 8, 1, 15.8),
(2, 'Jenny', 'Jenny', '800-867-530', 9, 5, 15),
(3, 'John', 'Doe', '248-000-000', 6, 3, 10.6),
(4, 'Gordon', 'Freeman', '248-000-000', 10, 8, 15.9),
(5, 'Cid', 'Highwind', '248-000-000', 9, 5, 15.5),
(6, 'Hal', 'Emmerich', '248-000-000', 8, 4, 14.5),
(7, 'Isaac', 'Clarke', '248-000-000', 8, 6, 13.5);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `VCL_NUM` int(5) NOT NULL,
  `VCL_MAKE` varchar(20) NOT NULL,
  `VCL_MODEL` varchar(10) NOT NULL,
  `VCL_YEAR` int(11) NOT NULL,
  `VCL_MISC` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`VCL_NUM`, `VCL_MAKE`, `VCL_MODEL`, `VCL_YEAR`, `VCL_MISC`) VALUES
(1, 'SAAB', '9-3', 2011, 'Turbo4'),
(2, 'Acura', 'MDX', 2004, '3.5L'),
(3, 'Honda', 'S2000', 2007, 'AP1'),
(4, 'Ford', 'Mustang', 2018, 'EcoBoost'),
(5, 'Ford', 'Mustang', 2018, 'GT Premium'),
(6, 'Ford', 'Mustang', 2010, 'V6'),
(7, 'Ford', 'Mustang', 2010, 'V6 Premium'),
(8, 'Ford', 'F150', 2018, 'XL'),
(9, 'Ford', 'F150', 2018, 'RAPTOR'),
(10, 'Ford', 'F150', 2010, 'Standard'),
(11, 'Ford', 'F150', 2010, 'Premium'),
(12, 'Hyundai', 'Veloster', 2013, 'Turbo'),
(13, 'Mazda', 'MX-5 Miata', 2016, 'Club');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`APT_NUM`),
  ADD KEY `VIN` (`VIN`),
  ADD KEY `SHIFT_NUM` (`SHIFT_NUM`),
  ADD KEY `SERV_NUM` (`SERV_NUM`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CUS_NUM`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`VIN`),
  ADD KEY `CUS_NUM` (`CUS_NUM`),
  ADD KEY `VCL_NUM` (`VCL_NUM`);

--
-- Indexes for table `recommendation`
--
ALTER TABLE `recommendation`
  ADD PRIMARY KEY (`REC_NUM`),
  ADD KEY `VIN` (`VIN`),
  ADD KEY `SER_NUM` (`SERV_NUM`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`SHIFT_NUM`),
  ADD KEY `EMP_NUM` (`EMP_NUM`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`SERV_NUM`);

--
-- Indexes for table `technician`
--
ALTER TABLE `technician`
  ADD PRIMARY KEY (`EMP_NUM`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`VCL_NUM`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `APT_NUM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `CUS_NUM` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `recommendation`
--
ALTER TABLE `recommendation`
  MODIFY `REC_NUM` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `schedule`
--
ALTER TABLE `schedule`
  MODIFY `SHIFT_NUM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `SERV_NUM` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `technician`
--
ALTER TABLE `technician`
  MODIFY `EMP_NUM` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `VCL_NUM` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`VIN`) REFERENCES `owner` (`VIN`),
  ADD CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`SHIFT_NUM`) REFERENCES `schedule` (`SHIFT_NUM`),
  ADD CONSTRAINT `appointment_ibfk_3` FOREIGN KEY (`SERV_NUM`) REFERENCES `service` (`SERV_NUM`);
  

--
-- Constraints for table `owner`
--
ALTER TABLE `owner`
  ADD CONSTRAINT `owner_ibfk_1` FOREIGN KEY (`CUS_NUM`) REFERENCES `customer` (`CUS_NUM`),
  ADD CONSTRAINT `owner_ibfk_2` FOREIGN KEY (`VCL_NUM`) REFERENCES `vehicle` (`VCL_NUM`);

--
-- Constraints for table `recommendation`
--
ALTER TABLE `recommendation`
  ADD CONSTRAINT `recommendation_ibfk_1` FOREIGN KEY (`VIN`) REFERENCES `owner` (`VIN`),
  ADD CONSTRAINT `recommendation_ibfk_2` FOREIGN KEY (`SERV_NUM`) REFERENCES `service` (`SERV_NUM`);

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`EMP_NUM`) REFERENCES `technician` (`EMP_NUM`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
