-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 17, 2019 at 12:16 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wellness_puj`
--

-- --------------------------------------------------------

--
-- Table structure for table `kategorija`
--

CREATE TABLE `kategorija` (
  `id_kategorije` int(11) NOT NULL,
  `naziv` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `aktivnost` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `kategorija`
--

INSERT INTO `kategorija` (`id_kategorije`, `naziv`, `aktivnost`) VALUES
(12, 'Masaza', 1),
(14, 'Njega koze', 1),
(15, 'Spa', 1),
(19, 'Anti celulit', 1);

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `id_korisnika` int(11) NOT NULL,
  `ime` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `e_mail` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `lozinka` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `administrator` tinyint(1) NOT NULL,
  `aktivnost` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`id_korisnika`, `ime`, `prezime`, `e_mail`, `lozinka`, `administrator`, `aktivnost`) VALUES
(1, 'admin', 'admin', 'admin@gmail.com', 'admin', 1, 1),
(12, 'monika', 'brzica', 'monika@gmail.com', 'monika', 1, 1),
(13, 'ana', 'anic', 'ana@gmail.com', 'ana', 0, 1),
(15, 'ante', 'anic', 'ante@gmail.com', 'ante', 0, 1),
(16, 'mirko', 'mirko', 'mirko@gmail.com', 'mirko', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `rezervacije`
--

CREATE TABLE `rezervacije` (
  `id_rezervacije` int(11) NOT NULL,
  `cijena` float NOT NULL,
  `id_usluge` int(11) NOT NULL,
  `id_korisnika` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `rezervacije`
--

INSERT INTO `rezervacije` (`id_rezervacije`, `cijena`, `id_usluge`, `id_korisnika`) VALUES
(42, 50, 25, 12),
(44, 30, 27, 15),
(45, 30, 26, 13);

-- --------------------------------------------------------

--
-- Table structure for table `usluga`
--

CREATE TABLE `usluga` (
  `id_usluge` int(11) NOT NULL,
  `id_korisnika` int(11) NOT NULL,
  `naziv` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `datum` date NOT NULL,
  `vrijeme` time NOT NULL,
  `br_termina` int(11) NOT NULL,
  `cijena` float NOT NULL,
  `id_kategorije` int(11) NOT NULL,
  `aktivnost` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `usluga`
--

INSERT INTO `usluga` (`id_usluge`, `id_korisnika`, `naziv`, `datum`, `vrijeme`, `br_termina`, `cijena`, `id_kategorije`, `aktivnost`) VALUES
(22, 12, 'noge', '2019-09-07', '00:00:12', 0, 20, 12, 1),
(25, 1, 'cijelo tijelo', '2019-09-13', '00:00:17', 2, 30, 12, 1),
(26, 1, 'piling lica', '2019-09-20', '00:00:16', 2, 30, 14, 1),
(27, 12, 'cijelo_tijelo', '2019-09-03', '00:00:15', 0, 30, 12, 1),
(28, 1, 'sauna', '2019-09-28', '00:00:14', 1, 20, 15, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategorija`
--
ALTER TABLE `kategorija`
  ADD PRIMARY KEY (`id_kategorije`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`id_korisnika`);

--
-- Indexes for table `rezervacije`
--
ALTER TABLE `rezervacije`
  ADD PRIMARY KEY (`id_rezervacije`),
  ADD KEY `id_usluge` (`id_usluge`),
  ADD KEY `FOREIGN KEY` (`id_korisnika`);

--
-- Indexes for table `usluga`
--
ALTER TABLE `usluga`
  ADD PRIMARY KEY (`id_usluge`),
  ADD KEY `id_kategorije` (`id_kategorije`),
  ADD KEY `usluga_ibfk_2` (`id_korisnika`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategorija`
--
ALTER TABLE `kategorija`
  MODIFY `id_kategorije` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `id_korisnika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `rezervacije`
--
ALTER TABLE `rezervacije`
  MODIFY `id_rezervacije` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `usluga`
--
ALTER TABLE `usluga`
  MODIFY `id_usluge` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rezervacije`
--
ALTER TABLE `rezervacije`
  ADD CONSTRAINT `rezervacije_ibfk_1` FOREIGN KEY (`id_usluge`) REFERENCES `usluga` (`id_usluge`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rezervacije_ibfk_2` FOREIGN KEY (`id_korisnika`) REFERENCES `korisnik` (`id_korisnika`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `usluga`
--
ALTER TABLE `usluga`
  ADD CONSTRAINT `usluga_ibfk_2` FOREIGN KEY (`id_korisnika`) REFERENCES `korisnik` (`id_korisnika`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usluga_ibfk_3` FOREIGN KEY (`id_kategorije`) REFERENCES `kategorija` (`id_kategorije`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
