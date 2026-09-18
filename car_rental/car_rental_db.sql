-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2025 at 07:26 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `car_rental_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `car_id` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `car_model` varchar(50) DEFAULT NULL,
  `cost_per_day` double DEFAULT NULL,
  `cubic_capacity` int(11) DEFAULT NULL,
  `number_of_seats` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`car_id`, `category_id`, `car_model`, `cost_per_day`, `cubic_capacity`, `number_of_seats`) VALUES
(1, 1, 'Toyota Aygo', 30, 998, 4),
(2, 1, 'Fiat Panda', 28, 999, 4),
(3, 2, 'Honda Civic', 45, 1498, 5),
(4, 2, 'Mazda 3', 47, 1500, 5),
(5, 3, 'Volkswagen Passat', 65, 1600, 5),
(6, 3, 'Skoda Superb', 68, 1800, 5),
(7, 4, 'Jeep Wrangler', 85, 2200, 5),
(8, 4, 'Toyota Land Cruiser', 95, 2800, 7),
(9, 5, 'Ford Fiesta', 33, 1100, 5),
(10, 5, 'Renault Clio', 35, 1200, 5),
(11, 6, 'Mazda MX-5', 75, 2000, 2),
(12, 6, 'BMW Z4', 120, 2500, 2),
(13, 5, 'Ferrari', 50.76, 1600, 1),
(15, 8, 'Smart', 420.25, 2000, 4);

-- --------------------------------------------------------

--
-- Table structure for table `car_categories`
--

CREATE TABLE `car_categories` (
  `car_categories_id` int(11) NOT NULL,
  `car_size` varchar(254) DEFAULT NULL,
  `car_price` varchar(254) DEFAULT NULL,
  `car_type` varchar(254) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `car_categories`
--

INSERT INTO `car_categories` (`car_categories_id`, `car_size`, `car_price`, `car_type`) VALUES
(5, 'Compact', 'Budget', 'Hatchback'),
(6, 'Convertible', 'Premium', 'Sport'),
(3, 'Large', 'Premium', 'Family'),
(2, 'Medium', 'Standard', 'Sedan'),
(8, 'small', 'akis', 'programmer'),
(1, 'Small', 'Economy', 'City'),
(4, 'SUV', 'Luxury', 'Off-road'),
(9, 'tank', 'tank', 'tank');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `first_name` varchar(254) DEFAULT NULL,
  `last_name` varchar(254) DEFAULT NULL,
  `sex` enum('male','female') DEFAULT NULL,
  `address` varchar(254) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `sex`, `address`, `email`, `phone`) VALUES
(1, 'John', 'Doe', 'male', '123 Elm Street', 'john.doe@example.com', '555-1234'),
(2, 'Jane', 'Smith', 'female', '456 Oak Avenue', 'jane.smith@example.com', '555-5678'),
(3, 'Robert', 'Johnson', 'male', '789 Pine Road', 'robert.johnson@example.com', '555-8765'),
(4, 'Emily', 'Davis', 'female', '321 Maple Lane', 'emily.davis@example.com', '555-4321'),
(5, 'Michael', 'Brown', 'male', '22 Cedar Blvd', 'michael.brown@example.com', '555-2222'),
(6, 'Olivia', 'Wilson', 'female', '77 Birch Way', 'olivia.wilson@example.com', '555-3333'),
(7, 'William', 'Taylor', 'male', '88 Willow Lane', 'william.taylor@example.com', '555-4444'),
(8, 'Emma', 'Moore', 'female', '99 Cherry Circle', 'emma.moore@example.com', '555-5555'),
(9, 'Dzczcdzd', 'Czcdzc', 'male', 'Zdc', 'zdcdzcdz', 'czdcz'),
(10, 'Aldkjsddks', 'Sljnckajdbncklc', 'male', 'Dfxvxfvv', 'fxvfxvxf', 'vfxvxfvxfv'),
(11, 'Aldjfnf', 'To039-83eix0qhfol', 'male', 'Tthrvh', 'fvghfvhv', 'gfhvghfbvhv'),
(12, 'Gdgtvd', 'Vgdffrh', 'male', 'Nynghng', 'ngnghn', 'ghnghng');

-- --------------------------------------------------------

--
-- Table structure for table `rentals`
--

CREATE TABLE `rentals` (
  `rental_id` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  `days_of_rental` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rentals`
--

INSERT INTO `rentals` (`rental_id`, `customer_id`, `car_id`, `days_of_rental`, `start_date`, `end_date`) VALUES
(1, 1, 3, 5, '2025-06-01', '2025-06-06'),
(2, 2, 1, 3, '2025-06-03', '2025-06-06'),
(3, 3, 4, 7, '2025-06-05', '2025-06-12'),
(4, 4, 2, 2, '2025-06-02', '2025-06-04'),
(5, 5, 5, 6, '2025-06-10', '2025-06-16'),
(6, 6, 6, 1, '2025-06-15', '2025-06-16'),
(7, 7, 8, 3, '2025-06-12', '2025-06-15'),
(8, 8, 7, 4, '2025-06-14', '2025-06-18'),
(9, 1, 9, 2, '2025-06-20', '2025-06-22'),
(10, 2, 10, 3, '2025-06-19', '2025-06-22'),
(11, 3, 11, 2, '2025-06-21', '2025-06-23'),
(12, 4, 12, 5, '2025-06-22', '2025-06-27'),
(13, 1, 5, 1888, '2025-07-08', '2030-09-08'),
(14, 1, 1, 407, '2025-06-27', '2026-08-08'),
(15, 7, 13, 185, '2029-02-02', '2029-08-06');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`car_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `car_categories`
--
ALTER TABLE `car_categories`
  ADD PRIMARY KEY (`car_categories_id`),
  ADD UNIQUE KEY `unique_category` (`car_size`,`car_price`,`car_type`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `unique_customer` (`first_name`,`last_name`,`sex`,`address`,`email`,`phone`) USING HASH;

--
-- Indexes for table `rentals`
--
ALTER TABLE `rentals`
  ADD PRIMARY KEY (`rental_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `car_id` (`car_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `car_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `car_categories`
--
ALTER TABLE `car_categories`
  MODIFY `car_categories_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `rentals`
--
ALTER TABLE `rentals`
  MODIFY `rental_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cars`
--
ALTER TABLE `cars`
  ADD CONSTRAINT `cars_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `car_categories` (`car_categories_id`) ON DELETE CASCADE;

--
-- Constraints for table `rentals`
--
ALTER TABLE `rentals`
  ADD CONSTRAINT `rentals_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rentals_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
