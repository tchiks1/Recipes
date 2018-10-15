-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 14 oct. 2018 à 22:53
-- Version du serveur :  5.7.19
-- Version de PHP :  7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `recette`
--

-- --------------------------------------------------------

--
-- Structure de la table `cookers`
--

DROP TABLE IF EXISTS `cookers`;
CREATE TABLE IF NOT EXISTS `cookers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `cookers`
--

INSERT INTO `cookers` (`id`, `name`, `email`, `password`) VALUES
(1, 'tchiks', 'tchikscorporation@yahoo.fr', 'password12'),
(2, 'patrick', 'patrick@yahoo.fr', 'password12'),
(3, 'tiks', 'tres@gmail.com', 'xzfIalSzyl9AAPkfS1EmHMgiPXQzZDFiYzI2YTVm');

-- --------------------------------------------------------

--
-- Structure de la table `description`
--

DROP TABLE IF EXISTS `description`;
CREATE TABLE IF NOT EXISTS `description` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `duration` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `guest` varchar(45) NOT NULL,
  `cookers_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `description`
--

INSERT INTO `description` (`id`, `img`, `name`, `duration`, `description`, `guest`, `cookers_id`) VALUES
(1, 'images/default_profil.png', 'banana', '12', 'diief bugfi  vsdgi biuds iugiusd', '5', '1'),
(2, 'images/default_profil.png', 'banana', '12', 'diief bugfi  vsdgi biuds iugiusd', '5', '1'),
(3, 'images/default_profil.png', 'plantain', '10', 'diief bugfi  vsdgi biuds iugiusd', '4', '1'),
(4, 'tchiks-macabo-2018.10.12.png', 'macabo', '10', 'diief bugfi  vsdgi biuds iugiusd', '4', '2'),
(5, 'tchiks-test-2018.10.12.png', 'test', '50', 'vdjdjvff', '5', '5'),
(6, 'htf', 'piles', '55', 'pile tout le plantain nÃ©cessaire.', '10', '2'),
(9, 'vfcddhdjdd', 'ndole', '14', 'legumes facilitant la digestion', '4', '2'),
(10, 'vfcddhdjdd', 'ndole', '14', 'legumes facilitant la digestion', '4', '2'),
(11, 'vfcddhdjdd', 'ndole', '14', 'legumes facilitant la digestion', '4', '2'),
(12, 'vfcddhdjdd', 'ndole', '14', 'legumes facilitant la digestion', '4', '2');

-- --------------------------------------------------------

--
-- Structure de la table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
CREATE TABLE IF NOT EXISTS `favorites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `receipts_id` int(11) NOT NULL,
  `cookers_id` int(11) NOT NULL,
  `enable` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
CREATE TABLE IF NOT EXISTS `ingredients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `quantity` float NOT NULL,
  `receipts_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `ingredients`
--

INSERT INTO `ingredients` (`id`, `name`, `quantity`, `receipts_id`) VALUES
(1, 'regime plantain', 1, 2),
(2, 'bouteille  d\'huile', 1, 2),
(3, 'boite d\'haricot', 10, 2),
(4, 'sachet poivron', 5, 4),
(5, 'rouleau de legumes', 5, 4),
(6, 'sachet poivron', 5, 4),
(7, 'rouleau de legumes', 5, 4),
(8, 'sachet poivron', 5, 4);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
