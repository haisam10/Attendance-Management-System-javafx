-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 25, 2025 at 10:20 PM
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
-- Database: `ats_data`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `id` int(11) NOT NULL,
  `student_id` varchar(50) DEFAULT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `section` varchar(10) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`id`, `student_id`, `student_name`, `section`, `date`, `status`) VALUES
(1, '223071102', NULL, NULL, '2025-07-25', 1),
(2, '223071103', NULL, NULL, '2025-07-25', 1),
(3, '223071104', NULL, NULL, '2025-07-25', 1),
(4, '223071105', NULL, NULL, '2025-07-25', 1),
(5, '223071106', NULL, NULL, '2025-07-25', 0),
(6, '223071114', NULL, NULL, '2025-07-25', 1),
(7, '223071102', NULL, NULL, '2025-07-26', 1),
(8, '223071103', NULL, NULL, '2025-07-26', 1),
(9, '223071104', NULL, NULL, '2025-07-26', 0),
(10, '223071105', NULL, NULL, '2025-07-26', 1),
(11, '223071106', NULL, NULL, '2025-07-26', 0),
(12, '223071114', NULL, NULL, '2025-07-26', 1),
(13, '223071102', NULL, NULL, '2025-07-24', 1),
(14, '223071103', NULL, NULL, '2025-07-24', 1),
(15, '223071104', NULL, NULL, '2025-07-24', 0),
(16, '223071105', NULL, NULL, '2025-07-24', 1),
(17, '223071106', NULL, NULL, '2025-07-24', 1),
(18, '223071114', NULL, NULL, '2025-07-24', 1);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `department` varchar(10) DEFAULT NULL,
  `section` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `student_info`
--

CREATE TABLE `student_info` (
  `student_id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `section` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_info`
--

INSERT INTO `student_info` (`student_id`, `name`, `department`, `section`, `email`, `phone`) VALUES
('223071102', 'sourov', 'CSE', 'C', 'sourov@mail.com', '123456'),
('223071103', 'titi', 'CSE', 'C', 'titi@mail.com', '123456'),
('223071104', 'shakil', 'CSE', 'C', 'shakil@mail.com', '123456'),
('223071105', 'meam', 'CSE', 'C', 'meam@mail.com', '123456'),
('223071106', 'nir', 'CSE', 'C', 'nir@mail.com', '123456'),
('223071107', 'Arshi', 'CSE', 'B', 'arshi', '123456'),
('223071108', 'tanzila', 'CSE', 'C', 'tanzila@mail.com', '123456'),
('223071114', 'MD Haisam Hoque', 'CSE', 'C', 'mdhh.info@gmail.com', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `id` int(255) NOT NULL,
  `username` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `timetable`
--

CREATE TABLE `timetable` (
  `id` int(11) NOT NULL,
  `teacher_name` varchar(100) DEFAULT NULL,
  `day` varchar(20) DEFAULT NULL,
  `time_slot` varchar(20) DEFAULT NULL,
  `classroom_no` varchar(10) DEFAULT NULL,
  `class` varchar(20) DEFAULT NULL,
  `section` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `timetable`
--

INSERT INTO `timetable` (`id`, `teacher_name`, `day`, `time_slot`, `classroom_no`, `class`, `section`) VALUES
(1, 'abs_tec', 'Monday', '8:30 to 10:30', '101', 'Class X', 'A'),
(2, 'sacsac', 'Monday', '8:30 to 10:30', '101', 'Class X', 'A');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('Admin','Teacher','Student') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `password`, `role`) VALUES
('admin', 'admin', 'Admin'),
('223071114', 'Haisam', 'Student'),
('teacher', 'teacher', 'Teacher');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student_info`
--
ALTER TABLE `student_info`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `timetable`
--
ALTER TABLE `timetable`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `timetable`
--
ALTER TABLE `timetable`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
