/*
 * Execute this script before before run the app 
 * Database: MySQL
 * 
 * Author: MOHAMMAD HASAN KHAN
 */
DROP TABLE IF EXISTS `luminae`.`user`;
CREATE TABLE `luminae`.`user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

insert into `user`(`idUser`,`firstName`,`lastName`,`email`,`password`) values (11,'rajib','hasan','hasan@gmail.com','81dc9bdb52d04dc20036dbd8313ed055');
insert into `user`(`idUser`,`firstName`,`lastName`,`email`,`password`) values (12,'hasan','khan','khan@yahoo.com','81dc9bdb52d04dc20036dbd8313ed055');
