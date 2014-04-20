/*
 * Execute this script before before run the app 
 * Database: MySQL
 * 
 * Author: MOHAMMAD HASAN KHAN
 */

DROP TABLE IF EXISTS `luminae`.`sportscar`;
CREATE TABLE `luminae`.`sportscar` (
  `idCar` int(11) NOT NULL AUTO_INCREMENT,
  `model` varchar(255) NOT NULL,
  `manufacturer` varchar(255) NOT NULL,
  `style` varchar(255) NOT NULL,
  `origin` varchar(255) NOT NULL,
  PRIMARY KEY (`idCar`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (1,'205','Abarth','Coupé','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (2,'Porsche 356B Carrera GTL Abarth','Abarth','Coupé','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (3,'Fiat Abarth 850 TC Berlina','Abarth','Roadster, Coupé','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (4,'3000ME','AC Cars','Coupé','England');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (6,'Aceca','AC Cars','Coupé','England');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (7,'Cobra','AC Cars','Roadster','England');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (8,'NSX','Acura','Coupé, Targa','Japan');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (9,'4C','Alfa Romeo','Coupé','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (10,'6C','Alfa Romeo','Roadster','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (11,'8C','Alfa Romeo','Roadster','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (12,'8C Competizione','Alfa Romeo','Roadster, Coupé','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (13,'4L','Alfa Romeo','Roadster','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (14,'Spider','Alfa Romeo','Roadster','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (15,'SZ','Alfa Romeo','Coupé','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (16,'RZ','Alfa Romeo','Roadster','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (17,'4C','Alfa Romeo','Roadster','Italy');
insert into `sportscar`(`idCar`,`model`,`manufacturer`,`style`,`origin`) values (18,'4C','Alfa Romeo','Roadster','Italy');
