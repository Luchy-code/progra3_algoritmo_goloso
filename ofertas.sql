-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-10-2024 a las 22:20:10
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.2.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ofertas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscriciones`
--

CREATE TABLE `inscriciones` (
  `id` int(11) NOT NULL,
  `dni` varchar(10) NOT NULL,
  `argentino` enum('Si','No') NOT NULL,
  `fechaInscripcion` varchar(10) NOT NULL,
  `fechaEvento` varchar(10) NOT NULL,
  `hora_in` varchar(5) NOT NULL,
  `hora_out` varchar(5) NOT NULL,
  `oferta` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `inscriciones`
--

INSERT INTO `inscriciones` (`id`, `dni`, `argentino`, `fechaInscripcion`, `fechaEvento`, `hora_in`, `hora_out`, `oferta`) VALUES
(17, '44584069', 'Si', '25/10/2024', '26/10/2024', '08:00', '12:00', '16000.00'),
(18, '44584069', 'Si', '25/10/2024', '26/10/2024', '12:00', '15:00', '14000.00'),
(19, '44584069', 'Si', '25/10/2024', '26/10/2024', '11:00', '16:00', '10000.00'),
(20, '44584069', 'Si', '25/10/2024', '26/10/2024', '17:00', '21:00', '14000.00'),
(21, '44584069', 'Si', '25/10/2024', '26/10/2024', '07:00', '11:00', '14000.00'),
(26, '44584069', 'Si', '25/10/2024', '26/10/2024', '21:01', '22:01', '1000.00'),
(30, '44584069', 'Si', '26/10/2024', '27/10/2024', '15:11', '16:11', '1000.00'),
(31, '44584069', 'Si', '26/10/2024', '27/10/2024', '16:16', '19:16', '2000.00'),
(32, '44584069', 'Si', '26/10/2024', '27/10/2024', '15:17', '17:17', '1000.00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `inscriciones`
--
ALTER TABLE `inscriciones`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `inscriciones`
--
ALTER TABLE `inscriciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
