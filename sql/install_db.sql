drop database if exists `mspr`;
create database if not exists `mspr` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `mspr`;

CREATE TABLE `client` (
  `code_client` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `adresse` varchar(250) NOT NULL,
  `code_postal` int(5) NOT NULL,
  `ville` VARCHAR(50) NOT NULL,
  `stimestamp` TIMESTAMP DEFAULT CURRENT_DATE(),
  PRIMARY KEY (`code_client`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `commande` (
  `code_commande` int(11) NOT NULL AUTO_INCREMENT,
  `stimestamp` TIMESTAMP DEFAULT CURRENT_DATE(),
  `code_client` int(11)  NOT NULL,
  `date_commande` DATE,
  PRIMARY KEY (`code_commande`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `detail_commande` (
  `code_detail_commande` int(11) NOT NULL AUTO_INCREMENT,
  `quantite` int(6) NOT NULL,
  `stimestamp` TIMESTAMP DEFAULT CURRENT_DATE(),
  `code_commande` int(11) NOT NULL,
  `code_produit` int(11) NOT NULL,
  PRIMARY KEY (`code_detail_commande`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `produit` (
  `code_produit` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `prix` int(11) DEFAULT NULL,
  `stimestamp` TIMESTAMP DEFAULT CURRENT_DATE(),
  PRIMARY KEY (`code_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `role` (
  `code_role` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(50)  NOT NULL,
  `stimestamp` TIMESTAMP DEFAULT CURRENT_DATE(),
  PRIMARY KEY (`code_role`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `utilisateur` (
  `code_utilisateur` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(50)  NOT NULL,
  `stimestamp` TIMESTAMP DEFAULT CURRENT_DATE(),
  `code_role` int(11) DEFAULT NULL,
  PRIMARY KEY (`code_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

ALTER TABLE `utilisateur` ADD CONSTRAINT UNIQ_8567 UNIQUE(`email`);
ALTER TABLE `commande` ADD FOREIGN KEY (`code_client`) REFERENCES `client` (`code_client`);
ALTER TABLE `detail_commande` ADD FOREIGN KEY (`code_produit`) REFERENCES `produit` (`code_produit`);
ALTER TABLE `detail_commande` ADD FOREIGN KEY (`code_commande`) REFERENCES `commande` (`code_commande`);
ALTER TABLE `utilisateur` ADD FOREIGN KEY (`code_role`) REFERENCES `role` (`code_role`);
