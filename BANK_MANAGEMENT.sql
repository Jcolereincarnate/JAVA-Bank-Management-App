-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 25, 2025 at 02:02 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `BANK_MANAGEMENT`
--

-- --------------------------------------------------------

--
-- Table structure for table `Accounts`
--

CREATE TABLE `Accounts` (
  `accountID` int(11) NOT NULL,
  `account_number` varchar(11) DEFAULT NULL,
  `account_type` enum('current','savings','corporate','kiddies') DEFAULT NULL,
  `balance` int(11) DEFAULT NULL,
  `debt_balance` decimal(10,2) NOT NULL DEFAULT 0.00,
  `interest_rate` float(5,2) DEFAULT NULL,
  `status` enum('Tier 1','Tier 2','Tier 3') NOT NULL DEFAULT 'Tier 1',
  `credit` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Accounts`
--

-- --------------------------------------------------------

--
-- Table structure for table `CUSTOMERS`
--

CREATE TABLE `CUSTOMERS` (
  `customerID` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `address` text NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone_number` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `CUSTOMERS`
--


-- --------------------------------------------------------

--
-- Table structure for table `Customer_Accounts`
--

CREATE TABLE `Customer_Accounts` (
  `customerAccountID` int(11) NOT NULL,
  `customerID` int(11) DEFAULT NULL,
  `account_number` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Customer_Accounts`
--


-- --------------------------------------------------------

--
-- Table structure for table `Employees`
--

CREATE TABLE `Employees` (
  `employeeID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','Manager','Teller','Loan Officer','Customer Service') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Employees`
--

INSERT INTO `Employees` (`employeeID`, `name`, `username`, `password`, `role`) VALUES
(1, 'Okubule Temitope', 'Temitope', 'temitope', 'Manager');

-- --------------------------------------------------------

--
-- Table structure for table `TRANSACTIONS`
--

CREATE TABLE `TRANSACTIONS` (
  `transactionID` int(11) NOT NULL,
  `account_number` varchar(15) NOT NULL,
  `transaction_date` date NOT NULL,
  `transaction_type` varchar(50) NOT NULL,
  `amount` double(10,2) NOT NULL,
  `balance` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `TRANSACTIONS`
--

INSERT INTO `TRANSACTIONS` (`transactionID`, `account_number`, `transaction_date`, `transaction_type`, `amount`, `balance`) VALUES
(1, '3667998755', '2025-04-18', 'Deposit', 10000.00, 68000.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Accounts`
--
ALTER TABLE `Accounts`
  ADD PRIMARY KEY (`accountID`),
  ADD UNIQUE KEY `account_number` (`account_number`);

--
-- Indexes for table `CUSTOMERS`
--
ALTER TABLE `CUSTOMERS`
  ADD PRIMARY KEY (`customerID`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone_number` (`phone_number`),
  ADD UNIQUE KEY `email_2` (`email`,`phone_number`);

--
-- Indexes for table `Customer_Accounts`
--
ALTER TABLE `Customer_Accounts`
  ADD PRIMARY KEY (`customerAccountID`),
  ADD UNIQUE KEY `account_number` (`account_number`),
  ADD KEY `customerID` (`customerID`);

--
-- Indexes for table `Employees`
--
ALTER TABLE `Employees`
  ADD PRIMARY KEY (`employeeID`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `TRANSACTIONS`
--
ALTER TABLE `TRANSACTIONS`
  ADD PRIMARY KEY (`transactionID`),
  ADD KEY `accountID` (`account_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Accounts`
--
ALTER TABLE `Accounts`
  MODIFY `accountID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `CUSTOMERS`
--
ALTER TABLE `CUSTOMERS`
  MODIFY `customerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `Customer_Accounts`
--
ALTER TABLE `Customer_Accounts`
  MODIFY `customerAccountID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `Employees`
--
ALTER TABLE `Employees`
  MODIFY `employeeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `TRANSACTIONS`
--
ALTER TABLE `TRANSACTIONS`
  MODIFY `transactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Customer_Accounts`
--
ALTER TABLE `Customer_Accounts`
  ADD CONSTRAINT `customer_accounts_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `CUSTOMERS` (`customerID`) ON DELETE CASCADE,
  ADD CONSTRAINT `customer_accounts_ibfk_2` FOREIGN KEY (`account_number`) REFERENCES `Accounts` (`account_number`) ON DELETE CASCADE;

--
-- Constraints for table `TRANSACTIONS`
--
ALTER TABLE `TRANSACTIONS`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`account_number`) REFERENCES `Accounts` (`accountID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
