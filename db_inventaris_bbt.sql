/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.21 : Database - db_inventaris_bbt
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_inventaris_bbt` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `db_inventaris_bbt`;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `codeCategory` varchar(5) NOT NULL,
  `categoryName` varchar(20) NOT NULL,
  PRIMARY KEY (`codeCategory`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `category` */

insert  into `category`(`codeCategory`,`categoryName`) values ('CG002','SWITCH MANAGE'),('CG003','SWITCH UNMANAGE'),('CG004','ROUTER'),('CG005','NETWORK CABLE'),('CG006','ACCESS POINT'),('CG007','TOOLS'),('CG008','MODEM');

/*Table structure for table `item_in` */

DROP TABLE IF EXISTS `item_in`;

CREATE TABLE `item_in` (
  `idItemIn` varchar(20) NOT NULL,
  `sourceItemIn` varchar(25) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `idUser` int(2) DEFAULT '0',
  PRIMARY KEY (`idItemIn`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `item_in_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `item_in` */

insert  into `item_in`(`idItemIn`,`sourceItemIn`,`tanggal`,`description`,`idUser`) values ('BM-LGI18/0/0001','LAINNYA','2018-10-25','-',1),('BM-LGI18/0/0002','BARANG KEMBALI','2018-10-25','bekas trial di mangrove',1);

/*Table structure for table `item_in_detail` */

DROP TABLE IF EXISTS `item_in_detail`;

CREATE TABLE `item_in_detail` (
  `noItemInDetail` int(11) NOT NULL AUTO_INCREMENT,
  `idItemIn` varchar(20) DEFAULT NULL,
  `codeItem` varchar(5) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `serialNumber` varchar(50) DEFAULT '(NULL)',
  `kondisi` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`noItemInDetail`),
  KEY `idItemIn` (`idItemIn`),
  KEY `codeItem` (`codeItem`),
  CONSTRAINT `item_in_detail_ibfk_1` FOREIGN KEY (`idItemIn`) REFERENCES `item_in` (`idItemIn`),
  CONSTRAINT `item_in_detail_ibfk_2` FOREIGN KEY (`codeItem`) REFERENCES `items` (`codeItem`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

/*Data for the table `item_in_detail` */

insert  into `item_in_detail`(`noItemInDetail`,`idItemIn`,`codeItem`,`qty`,`serialNumber`,`kondisi`) values (1,'BM-LGI18/0/0001','CI007',1,'10:FE:ED:BC:1A:5D','BARU'),(2,'BM-LGI18/0/0001','CI001',1,'18A6F759AA5A','BARU'),(3,'BM-LGI18/0/0001','CI001',1,'18A6F759C574','BARU'),(4,'BM-LGI18/0/0001','CI009',1,'68725184279D','BARU'),(5,'BM-LGI18/0/0001','CI009',1,'6872518427F1','BARU'),(6,'BM-LGI18/0/0001','CI009',1,'687251842877','BARU'),(7,'BM-LGI18/0/0001','CI009',1,'687251842881','BARU'),(8,'BM-LGI18/0/0001','CI009',1,'6872518428F1','BARU'),(9,'BM-LGI18/0/0001','CI009',1,'6872518428F2','BARU'),(10,'BM-LGI18/0/0001','CI009',1,'6872518428F3','BARU'),(11,'BM-LGI18/0/0001','CI009',1,'687251842953','BARU'),(12,'BM-LGI18/0/0001','CI009',1,'6872518429C2','BARU'),(13,'BM-LGI18/0/0001','CI009',1,'6872518429E7','BARU'),(14,'BM-LGI18/0/0001','CI002',1000,'-','BARU'),(15,'BM-LGI18/0/0001','CI002',1000,'-','BARU'),(16,'BM-LGI18/0/0001','CI002',1000,'-','BARU'),(17,'BM-LGI18/0/0001','CI011',2530,'-','BARU'),(18,'BM-LGI18/0/0001','CI013',1,'6466B3CD1E0C','BARU'),(19,'BM-LGI18/0/0001','CI013',1,'6466B3CD0468','BARU'),(20,'BM-LGI18/0/0001','CI013',1,'6466B3CD1918','BARU'),(21,'BM-LGI18/0/0001','CI013',1,'6466B3CD1D0C','BARU'),(22,'BM-LGI18/0/0001','CI013',1,'6466B3CD1DCE','BARU'),(23,'BM-LGI18/0/0001','CI004',1,'EPU131000074','BARU'),(24,'BM-LGI18/0/0001','CI004',1,'EPU131000102','BARU'),(25,'BM-LGI18/0/0001','CI004',1,'EPU131000071','BARU'),(26,'BM-LGI18/0/0001','CI032',1,'7A3707E84333','BARU'),(27,'BM-LGI18/0/0001','CI032',1,'7A3708FB1F0F','BARU'),(28,'BM-LGI18/0/0001','CI031',3,'-','BEKAS'),(29,'BM-LGI18/0/0001','CI030',1,'BBT-LGI/64/0001','BEKAS'),(30,'BM-LGI18/0/0001','CI029',1,'BBT-LGI/32/0001','BARU'),(31,'BM-LGI18/0/0001','CI029',1,'BBT-LGI/32/0002','BARU'),(32,'BM-LGI18/0/0001','CI029',1,'BBT-LGI/32/0003','BARU'),(33,'BM-LGI18/0/0001','CI029',1,'BBT-LGI/32/0004','BARU'),(34,'BM-LGI18/0/0001','CI029',1,'BBT-LGI/32/0005','BARU'),(35,'BM-LGI18/0/0001','CI028',1,'1605270009','BARU'),(36,'BM-LGI18/0/0001','CI028',1,'1605270011','BARU'),(37,'BM-LGI18/0/0001','CI027',3,'-','BARU'),(38,'BM-LGI18/0/0001','CI014',4,'-','BARU'),(39,'BM-LGI18/0/0001','CI015',5,'-','BARU'),(40,'BM-LGI18/0/0001','CI016',5,'-','BARU'),(41,'BM-LGI18/0/0001','CI017',2,'-','BARU'),(42,'BM-LGI18/0/0001','CI018',2,'-','BARU'),(43,'BM-LGI18/0/0001','CI019',4,'-','BARU'),(44,'BM-LGI18/0/0001','CI020',4,'-','BARU'),(45,'BM-LGI18/0/0002','CI008',1,'1603G44D967CE74CA','BEKAS');

/*Table structure for table `item_out` */

DROP TABLE IF EXISTS `item_out`;

CREATE TABLE `item_out` (
  `idItemOut` varchar(20) NOT NULL,
  `request` varchar(50) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `idUser` int(2) DEFAULT NULL,
  PRIMARY KEY (`idItemOut`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `item_out_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `item_out` */

/*Table structure for table `item_out_detail` */

DROP TABLE IF EXISTS `item_out_detail`;

CREATE TABLE `item_out_detail` (
  `noItemOutDetail` int(11) NOT NULL AUTO_INCREMENT,
  `idItemOut` varchar(20) NOT NULL,
  `codeItem` varchar(5) NOT NULL,
  `qty` int(11) DEFAULT NULL,
  `serialNumber` varchar(50) DEFAULT NULL,
  `kondisi` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`noItemOutDetail`),
  KEY `idItemOut` (`idItemOut`),
  KEY `codeItem` (`codeItem`),
  CONSTRAINT `item_out_detail_ibfk_1` FOREIGN KEY (`idItemOut`) REFERENCES `item_out` (`idItemOut`),
  CONSTRAINT `item_out_detail_ibfk_2` FOREIGN KEY (`codeItem`) REFERENCES `items` (`codeItem`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `item_out_detail` */

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `codeItem` varchar(5) NOT NULL,
  `itemName` varchar(50) NOT NULL,
  `codeCategory` varchar(5) DEFAULT NULL,
  `codeUnit` varchar(5) DEFAULT NULL,
  `stockMin` int(1) DEFAULT '0',
  PRIMARY KEY (`codeItem`),
  KEY `codeCategory` (`codeCategory`),
  KEY `codeUnit` (`codeUnit`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`codeCategory`) REFERENCES `category` (`codeCategory`),
  CONSTRAINT `items_ibfk_2` FOREIGN KEY (`codeUnit`) REFERENCES `units` (`codeUnit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `items` */

insert  into `items`(`codeItem`,`itemName`,`codeCategory`,`codeUnit`,`stockMin`) values ('CI001','Access Point TP Link TLWA841ND','CG006','U02',1),('CI002','Cable Fiber Optic 2 Core DW','CG005','U01',2),('CI003','Access Point Ubiquity Rocket M2','CG006','U02',1),('CI004','OLT SMAI EFV EPU2F ','CG007','U02',1),('CI005','Modem ONU ZTE F660 ZXHN V5 ','CG008','U02',1),('CI006','Set Top Box IPTV IBOX','CG007','U02',1),('CI007','Access Point TP Link TLWA701ND','CG006','U02',1),('CI008','Access Point UBNT NSM5','CG006','U02',1),('CI009','Access Point UBNT Ubiquiti PicoStation M2HP','CG006','U02',1),('CI010','Cable Fiber Optic 12 Core Type','CG005','U01',5),('CI011','Cable Fiber Optic 48 Core Duct ','CG005','U01',10),('CI012','Cable Pipe Subduct HDPE 32/27 ','CG005','U01',5),('CI013','Modem TP Link TD-8840T ','CG008','U02',1),('CI014','Modul SFP Optone UTP 5 ','CG007','U02',1),('CI015','Modul SFP WDM SM 0210A 10KM ','CG007','U02',1),('CI016','Modul SFP WDM SM 0210B 10KM ','CG007','U02',1),('CI017','Modul SFP WDM SM 0220A 20KM ','CG007','U02',1),('CI018','Modul SFP WDM SM 0220B 20KM ','CG007','U02',1),('CI019','Modul SFP WDM SM 0240A 40KM ','CG007','U02',1),('CI020','Modul SFP WDM SM 0240B 40KM ','CG007','U02',1),('CI021','ONU SMAI SMA-1104 ','CG008','U02',1),('CI022','Pigtail SC/APC Hijau ','CG005','U02',1),('CI023','Protection Slave FO ','CG005','U02',1),('CI024','Roset FO ','CG005','U02',1),('CI025','Router Mikrotik RB3011UiAS-RM ','CG004','U02',1),('CI026','Set Top Box IPTV Android IBOX ','CG007','U02',1),('CI027','Set Top Box IPTV Coaxial ','CG007','U02',1),('CI028','Splitter 1:16 SC/APC Hijau ','CG007','U02',1),('CI029','Splitter 1:32 SC/PC Biru ','CG007','U02',1),('CI030','Splitter 1:64 SC/PC Biru ','CG007','U02',1),('CI031','Switch Cisco Linksys SRW 208G ','CG002','U02',1),('CI032','Switch Mikrotik RB260GSP ','CG002','U02',1),('CI033','MODEM ','CG008','U02',1);

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `noStock` int(11) NOT NULL AUTO_INCREMENT,
  `codeItem` varchar(5) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `kondisi` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`noStock`),
  KEY `codeItem` (`codeItem`),
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`codeItem`) REFERENCES `items` (`codeItem`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;

/*Data for the table `stock` */

insert  into `stock`(`noStock`,`codeItem`,`stock`,`kondisi`) values (3,'CI001',2,'BARU'),(4,'CI001',0,'BEKAS'),(5,'CI002',3000,'BARU'),(6,'CI002',0,'BEKAS'),(7,'CI003',0,'BARU'),(8,'CI003',0,'BEKAS'),(9,'CI004',3,'BARU'),(10,'CI004',0,'BEKAS'),(11,'CI005',0,'BARU'),(12,'CI005',0,'BEKAS'),(13,'CI006',0,'BARU'),(14,'CI006',0,'BEKAS'),(15,'CI007',1,'BARU'),(16,'CI007',0,'BEKAS'),(17,'CI008',0,'BARU'),(18,'CI008',1,'BEKAS'),(19,'CI009',10,'BARU'),(20,'CI009',0,'BEKAS'),(21,'CI010',0,'BARU'),(22,'CI010',0,'BEKAS'),(23,'CI011',2530,'BARU'),(24,'CI011',0,'BEKAS'),(25,'CI012',0,'BARU'),(26,'CI012',0,'BEKAS'),(27,'CI013',5,'BARU'),(28,'CI013',0,'BEKAS'),(29,'CI014',4,'BARU'),(30,'CI014',0,'BEKAS'),(31,'CI015',5,'BARU'),(32,'CI015',0,'BEKAS'),(33,'CI016',5,'BARU'),(34,'CI016',0,'BEKAS'),(35,'CI017',2,'BARU'),(36,'CI017',0,'BEKAS'),(37,'CI018',2,'BARU'),(38,'CI018',0,'BEKAS'),(39,'CI019',4,'BARU'),(40,'CI019',0,'BEKAS'),(41,'CI020',4,'BARU'),(42,'CI020',0,'BEKAS'),(43,'CI021',0,'BARU'),(44,'CI021',0,'BEKAS'),(45,'CI022',0,'BARU'),(46,'CI022',0,'BEKAS'),(47,'CI023',0,'BARU'),(48,'CI023',0,'BEKAS'),(49,'CI024',0,'BARU'),(50,'CI024',0,'BEKAS'),(51,'CI025',0,'BARU'),(52,'CI025',0,'BEKAS'),(53,'CI026',0,'BARU'),(54,'CI026',0,'BEKAS'),(55,'CI027',3,'BARU'),(56,'CI027',0,'BEKAS'),(57,'CI028',2,'BARU'),(58,'CI028',0,'BEKAS'),(59,'CI029',5,'BARU'),(60,'CI029',0,'BEKAS'),(61,'CI030',0,'BARU'),(62,'CI030',1,'BEKAS'),(63,'CI031',0,'BARU'),(64,'CI031',3,'BEKAS'),(65,'CI032',2,'BARU'),(66,'CI032',0,'BEKAS'),(67,'CI033',0,'BARU'),(68,'CI033',0,'BEKAS');

/*Table structure for table `transfer_order` */

DROP TABLE IF EXISTS `transfer_order`;

CREATE TABLE `transfer_order` (
  `idTransferOrder` varchar(20) NOT NULL,
  `tanggal` date DEFAULT NULL,
  `codeWarehouse` varchar(5) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `idUser` int(2) DEFAULT NULL,
  PRIMARY KEY (`idTransferOrder`),
  KEY `codeWarehouse` (`codeWarehouse`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `transfer_order_ibfk_1` FOREIGN KEY (`codeWarehouse`) REFERENCES `warehouse` (`codeWarehouse`),
  CONSTRAINT `transfer_order_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `transfer_order` */

/*Table structure for table `transfer_order_detail` */

DROP TABLE IF EXISTS `transfer_order_detail`;

CREATE TABLE `transfer_order_detail` (
  `noTransferOrderDetail` int(11) NOT NULL AUTO_INCREMENT,
  `idTransferOrder` varchar(20) DEFAULT NULL,
  `codeItem` varchar(5) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `serialNumber` varchar(50) DEFAULT NULL,
  `kondisi` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`noTransferOrderDetail`),
  KEY `idTransferOrder` (`idTransferOrder`),
  KEY `codeItem` (`codeItem`),
  CONSTRAINT `transfer_order_detail_ibfk_1` FOREIGN KEY (`idTransferOrder`) REFERENCES `transfer_order` (`idTransferOrder`),
  CONSTRAINT `transfer_order_detail_ibfk_2` FOREIGN KEY (`codeItem`) REFERENCES `items` (`codeItem`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `transfer_order_detail` */

/*Table structure for table `units` */

DROP TABLE IF EXISTS `units`;

CREATE TABLE `units` (
  `codeUnit` varchar(5) NOT NULL,
  `unitName` varchar(10) NOT NULL,
  PRIMARY KEY (`codeUnit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `units` */

insert  into `units`(`codeUnit`,`unitName`) values ('U01','METER'),('U02','PCS'),('U03','UNIT'),('U04','BOX');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `idUser` int(2) NOT NULL AUTO_INCREMENT,
  `nameUser` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `pass` varchar(20) NOT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`idUser`,`nameUser`,`username`,`pass`) values (1,'Danil','admin','admin123');

/*Table structure for table `warehouse` */

DROP TABLE IF EXISTS `warehouse`;

CREATE TABLE `warehouse` (
  `codeWarehouse` varchar(5) NOT NULL,
  `warehouse` varchar(5) NOT NULL,
  `location` varchar(20) DEFAULT NULL,
  `description` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`codeWarehouse`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `warehouse` */

insert  into `warehouse`(`codeWarehouse`,`warehouse`,`location`,`description`) values ('WH01','GLG','LAGOI','GUDANG LAGOI'),('WH02','GLB','LOBAM','GUDANG LOBAM'),('WH03','GTP','TANJUNG PINANG','GUDANG TANJUNG PINANG'),('WH04','GMK','MUKAKUNING','GUDANG MUKA KUNING');

/* Trigger structure for table `item_in_detail` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `TambahStok` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `TambahStok` AFTER INSERT ON `item_in_detail` FOR EACH ROW BEGIN
	update stock set stock = stock + new.qty
	where codeItem = new.codeItem and kondisi = new.kondisi;
    END */$$


DELIMITER ;

/* Trigger structure for table `item_out_detail` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `KurangStok` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `KurangStok` AFTER INSERT ON `item_out_detail` FOR EACH ROW BEGIN
	update stock set stock = stock - new.qty
	where codeItem = new.codeItem and kondisi = new.kondisi;
    END */$$


DELIMITER ;

/* Trigger structure for table `items` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `Stock_Item` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `Stock_Item` AFTER INSERT ON `items` FOR EACH ROW BEGIN
	INSERT INTO stock(`codeItem`,`stock`,`kondisi`) values(new.codeItem,'0','BARU'),(new.codeItem,'0','BEKAS');
    END */$$


DELIMITER ;

/* Trigger structure for table `transfer_order_detail` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `KurangStok1` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `KurangStok1` AFTER INSERT ON `transfer_order_detail` FOR EACH ROW BEGIN
	update stock set stock = stock - new.qty
	where `codeItem` = new.codeItem and kondisi = new.kondisi;
    END */$$


DELIMITER ;

/*Table structure for table `v_item_in` */

DROP TABLE IF EXISTS `v_item_in`;

/*!50001 DROP VIEW IF EXISTS `v_item_in` */;
/*!50001 DROP TABLE IF EXISTS `v_item_in` */;

/*!50001 CREATE TABLE  `v_item_in`(
 `idItemIn` varchar(20) ,
 `sourceItemIn` varchar(25) ,
 `tanggal` date ,
 `description` varchar(50) ,
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `qty` int(11) ,
 `codeCategory` varchar(5) ,
 `categoryName` varchar(20) ,
 `codeUnit` varchar(5) ,
 `unitName` varchar(10) ,
 `serialNumber` varchar(50) ,
 `kondisi` varchar(15) ,
 `idUser` int(2) ,
 `nameUser` varchar(20) 
)*/;

/*Table structure for table `v_item_in_qty_all` */

DROP TABLE IF EXISTS `v_item_in_qty_all`;

/*!50001 DROP VIEW IF EXISTS `v_item_in_qty_all` */;
/*!50001 DROP TABLE IF EXISTS `v_item_in_qty_all` */;

/*!50001 CREATE TABLE  `v_item_in_qty_all`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_all` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) 
)*/;

/*Table structure for table `v_item_in_qty_new` */

DROP TABLE IF EXISTS `v_item_in_qty_new`;

/*!50001 DROP VIEW IF EXISTS `v_item_in_qty_new` */;
/*!50001 DROP TABLE IF EXISTS `v_item_in_qty_new` */;

/*!50001 CREATE TABLE  `v_item_in_qty_new`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_new` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) ,
 `kondisi` varchar(15) 
)*/;

/*Table structure for table `v_item_in_qty_second` */

DROP TABLE IF EXISTS `v_item_in_qty_second`;

/*!50001 DROP VIEW IF EXISTS `v_item_in_qty_second` */;
/*!50001 DROP TABLE IF EXISTS `v_item_in_qty_second` */;

/*!50001 CREATE TABLE  `v_item_in_qty_second`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_second` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) ,
 `kondisi` varchar(15) 
)*/;

/*Table structure for table `v_item_out` */

DROP TABLE IF EXISTS `v_item_out`;

/*!50001 DROP VIEW IF EXISTS `v_item_out` */;
/*!50001 DROP TABLE IF EXISTS `v_item_out` */;

/*!50001 CREATE TABLE  `v_item_out`(
 `idItemOut` varchar(20) ,
 `request` varchar(50) ,
 `tanggal` date ,
 `description` varchar(50) ,
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `qty` int(11) ,
 `codeCategory` varchar(5) ,
 `categoryName` varchar(20) ,
 `codeUnit` varchar(5) ,
 `unitName` varchar(10) ,
 `serialNumber` varchar(50) ,
 `kondisi` varchar(15) ,
 `idUser` int(2) ,
 `nameUser` varchar(20) 
)*/;

/*Table structure for table `v_item_out_per_posting` */

DROP TABLE IF EXISTS `v_item_out_per_posting`;

/*!50001 DROP VIEW IF EXISTS `v_item_out_per_posting` */;
/*!50001 DROP TABLE IF EXISTS `v_item_out_per_posting` */;

/*!50001 CREATE TABLE  `v_item_out_per_posting`(
 `idItemOut` varchar(20) ,
 `request` varchar(50) ,
 `tanggal` date ,
 `description` varchar(50) ,
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `jml` decimal(32,0) ,
 `categoryName` varchar(20) ,
 `unitName` varchar(10) ,
 `nameUser` varchar(20) 
)*/;

/*Table structure for table `v_item_out_qty_all` */

DROP TABLE IF EXISTS `v_item_out_qty_all`;

/*!50001 DROP VIEW IF EXISTS `v_item_out_qty_all` */;
/*!50001 DROP TABLE IF EXISTS `v_item_out_qty_all` */;

/*!50001 CREATE TABLE  `v_item_out_qty_all`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_all` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) 
)*/;

/*Table structure for table `v_item_out_qty_new` */

DROP TABLE IF EXISTS `v_item_out_qty_new`;

/*!50001 DROP VIEW IF EXISTS `v_item_out_qty_new` */;
/*!50001 DROP TABLE IF EXISTS `v_item_out_qty_new` */;

/*!50001 CREATE TABLE  `v_item_out_qty_new`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_new` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) ,
 `kondisi` varchar(15) 
)*/;

/*Table structure for table `v_item_out_qty_second` */

DROP TABLE IF EXISTS `v_item_out_qty_second`;

/*!50001 DROP VIEW IF EXISTS `v_item_out_qty_second` */;
/*!50001 DROP TABLE IF EXISTS `v_item_out_qty_second` */;

/*!50001 CREATE TABLE  `v_item_out_qty_second`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_second` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) ,
 `kondisi` varchar(15) 
)*/;

/*Table structure for table `v_items` */

DROP TABLE IF EXISTS `v_items`;

/*!50001 DROP VIEW IF EXISTS `v_items` */;
/*!50001 DROP TABLE IF EXISTS `v_items` */;

/*!50001 CREATE TABLE  `v_items`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `categoryName` varchar(20) ,
 `unitName` varchar(10) ,
 `stockMin` int(1) 
)*/;

/*Table structure for table `v_sn` */

DROP TABLE IF EXISTS `v_sn`;

/*!50001 DROP VIEW IF EXISTS `v_sn` */;
/*!50001 DROP TABLE IF EXISTS `v_sn` */;

/*!50001 CREATE TABLE  `v_sn`(
 `codeItem` varchar(5) ,
 `serialNumber` varchar(50) ,
 `kondisi` varchar(15) 
)*/;

/*Table structure for table `v_stock_per_kondisi` */

DROP TABLE IF EXISTS `v_stock_per_kondisi`;

/*!50001 DROP VIEW IF EXISTS `v_stock_per_kondisi` */;
/*!50001 DROP TABLE IF EXISTS `v_stock_per_kondisi` */;

/*!50001 CREATE TABLE  `v_stock_per_kondisi`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `categoryName` varchar(20) ,
 `unitName` varchar(10) ,
 `stockMin` int(1) ,
 `stock` int(11) ,
 `kondisi` varchar(10) 
)*/;

/*Table structure for table `v_stock_total` */

DROP TABLE IF EXISTS `v_stock_total`;

/*!50001 DROP VIEW IF EXISTS `v_stock_total` */;
/*!50001 DROP TABLE IF EXISTS `v_stock_total` */;

/*!50001 CREATE TABLE  `v_stock_total`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockTotal` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) 
)*/;

/*Table structure for table `v_transfer_order` */

DROP TABLE IF EXISTS `v_transfer_order`;

/*!50001 DROP VIEW IF EXISTS `v_transfer_order` */;
/*!50001 DROP TABLE IF EXISTS `v_transfer_order` */;

/*!50001 CREATE TABLE  `v_transfer_order`(
 `idTransferOrder` varchar(20) ,
 `tanggal` date ,
 `codeWarehouse` varchar(5) ,
 `descWarehouse` varchar(25) ,
 `description` varchar(50) ,
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `qty` int(11) ,
 `codeCategory` varchar(5) ,
 `categoryName` varchar(20) ,
 `codeUnit` varchar(5) ,
 `unitName` varchar(10) ,
 `serialNumber` varchar(50) ,
 `kondisi` varchar(15) ,
 `idUser` int(2) ,
 `nameUser` varchar(20) 
)*/;

/*Table structure for table `v_transfer_order_qty_all` */

DROP TABLE IF EXISTS `v_transfer_order_qty_all`;

/*!50001 DROP VIEW IF EXISTS `v_transfer_order_qty_all` */;
/*!50001 DROP TABLE IF EXISTS `v_transfer_order_qty_all` */;

/*!50001 CREATE TABLE  `v_transfer_order_qty_all`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_all` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) 
)*/;

/*Table structure for table `v_transfer_order_qty_new` */

DROP TABLE IF EXISTS `v_transfer_order_qty_new`;

/*!50001 DROP VIEW IF EXISTS `v_transfer_order_qty_new` */;
/*!50001 DROP TABLE IF EXISTS `v_transfer_order_qty_new` */;

/*!50001 CREATE TABLE  `v_transfer_order_qty_new`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_second` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) ,
 `kondisi` varchar(15) 
)*/;

/*Table structure for table `v_transfer_order_qty_second` */

DROP TABLE IF EXISTS `v_transfer_order_qty_second`;

/*!50001 DROP VIEW IF EXISTS `v_transfer_order_qty_second` */;
/*!50001 DROP TABLE IF EXISTS `v_transfer_order_qty_second` */;

/*!50001 CREATE TABLE  `v_transfer_order_qty_second`(
 `codeItem` varchar(5) ,
 `itemName` varchar(50) ,
 `stockMin` int(1) ,
 `qty_second` decimal(32,0) ,
 `unitName` varchar(10) ,
 `categoryName` varchar(20) ,
 `kondisi` varchar(15) 
)*/;

/*View structure for view v_item_in */

/*!50001 DROP TABLE IF EXISTS `v_item_in` */;
/*!50001 DROP VIEW IF EXISTS `v_item_in` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_in` AS select `ii`.`idItemIn` AS `idItemIn`,`ii`.`sourceItemIn` AS `sourceItemIn`,`ii`.`tanggal` AS `tanggal`,`ii`.`description` AS `description`,`iid`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`iid`.`qty` AS `qty`,`i`.`codeCategory` AS `codeCategory`,`c`.`categoryName` AS `categoryName`,`i`.`codeUnit` AS `codeUnit`,`u`.`unitName` AS `unitName`,`iid`.`serialNumber` AS `serialNumber`,`iid`.`kondisi` AS `kondisi`,`ii`.`idUser` AS `idUser`,`us`.`nameUser` AS `nameUser` from (((((`item_in_detail` `iid` join `items` `i` on((`iid`.`codeItem` = `i`.`codeItem`))) join `item_in` `ii` on((`iid`.`idItemIn` = `ii`.`idItemIn`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) join `users` `us` on((`ii`.`idUser` = `us`.`idUser`))) order by `ii`.`idItemIn` */;

/*View structure for view v_item_in_qty_all */

/*!50001 DROP TABLE IF EXISTS `v_item_in_qty_all` */;
/*!50001 DROP VIEW IF EXISTS `v_item_in_qty_all` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_in_qty_all` AS select `iid`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`iid`.`qty`) AS `qty_all`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName` from (((`item_in_detail` `iid` join `items` `i` on((`iid`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) group by `iid`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_item_in_qty_new */

/*!50001 DROP TABLE IF EXISTS `v_item_in_qty_new` */;
/*!50001 DROP VIEW IF EXISTS `v_item_in_qty_new` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_in_qty_new` AS select `iid`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`iid`.`qty`) AS `qty_new`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName`,`iid`.`kondisi` AS `kondisi` from (((`item_in_detail` `iid` join `items` `i` on((`iid`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) where (`iid`.`kondisi` = 'BARU') group by `iid`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_item_in_qty_second */

/*!50001 DROP TABLE IF EXISTS `v_item_in_qty_second` */;
/*!50001 DROP VIEW IF EXISTS `v_item_in_qty_second` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_in_qty_second` AS select `iid`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`iid`.`qty`) AS `qty_second`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName`,`iid`.`kondisi` AS `kondisi` from (((`item_in_detail` `iid` join `items` `i` on((`iid`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) where (`iid`.`kondisi` = 'BEKAS') group by `iid`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_item_out */

/*!50001 DROP TABLE IF EXISTS `v_item_out` */;
/*!50001 DROP VIEW IF EXISTS `v_item_out` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_out` AS select `io`.`idItemOut` AS `idItemOut`,`io`.`request` AS `request`,`io`.`tanggal` AS `tanggal`,`io`.`description` AS `description`,`iod`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`iod`.`qty` AS `qty`,`i`.`codeCategory` AS `codeCategory`,`c`.`categoryName` AS `categoryName`,`i`.`codeUnit` AS `codeUnit`,`u`.`unitName` AS `unitName`,`iod`.`serialNumber` AS `serialNumber`,`iod`.`kondisi` AS `kondisi`,`io`.`idUser` AS `idUser`,`us`.`nameUser` AS `nameUser` from (((((`item_out_detail` `iod` join `items` `i` on((`iod`.`codeItem` = `i`.`codeItem`))) join `item_out` `io` on((`iod`.`idItemOut` = `io`.`idItemOut`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) join `users` `us` on((`io`.`idUser` = `us`.`idUser`))) */;

/*View structure for view v_item_out_per_posting */

/*!50001 DROP TABLE IF EXISTS `v_item_out_per_posting` */;
/*!50001 DROP VIEW IF EXISTS `v_item_out_per_posting` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_out_per_posting` AS select `v_item_out`.`idItemOut` AS `idItemOut`,`v_item_out`.`request` AS `request`,`v_item_out`.`tanggal` AS `tanggal`,`v_item_out`.`description` AS `description`,`v_item_out`.`codeItem` AS `codeItem`,`v_item_out`.`itemName` AS `itemName`,sum(`v_item_out`.`qty`) AS `jml`,`v_item_out`.`categoryName` AS `categoryName`,`v_item_out`.`unitName` AS `unitName`,`v_item_out`.`nameUser` AS `nameUser` from `v_item_out` group by `v_item_out`.`idItemOut`,`v_item_out`.`itemName` */;

/*View structure for view v_item_out_qty_all */

/*!50001 DROP TABLE IF EXISTS `v_item_out_qty_all` */;
/*!50001 DROP VIEW IF EXISTS `v_item_out_qty_all` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_out_qty_all` AS select `iid`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`iid`.`qty`) AS `qty_all`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName` from (((`item_out_detail` `iid` join `items` `i` on((`iid`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) group by `iid`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_item_out_qty_new */

/*!50001 DROP TABLE IF EXISTS `v_item_out_qty_new` */;
/*!50001 DROP VIEW IF EXISTS `v_item_out_qty_new` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_out_qty_new` AS select `iid`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`iid`.`qty`) AS `qty_new`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName`,`iid`.`kondisi` AS `kondisi` from (((`item_out_detail` `iid` join `items` `i` on((`iid`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) where (`iid`.`kondisi` = 'BARU') group by `iid`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_item_out_qty_second */

/*!50001 DROP TABLE IF EXISTS `v_item_out_qty_second` */;
/*!50001 DROP VIEW IF EXISTS `v_item_out_qty_second` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_item_out_qty_second` AS select `iid`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`iid`.`qty`) AS `qty_second`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName`,`iid`.`kondisi` AS `kondisi` from (((`item_out_detail` `iid` join `items` `i` on((`iid`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) where (`iid`.`kondisi` = 'BEKAS') group by `iid`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_items */

/*!50001 DROP TABLE IF EXISTS `v_items` */;
/*!50001 DROP VIEW IF EXISTS `v_items` */;

/*!50001 CREATE ALGORITHM=TEMPTABLE DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_items` AS (select `i`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`c`.`categoryName` AS `categoryName`,`u`.`unitName` AS `unitName`,`i`.`stockMin` AS `stockMin` from ((`items` `i` join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) order by `i`.`codeItem`) */;

/*View structure for view v_sn */

/*!50001 DROP TABLE IF EXISTS `v_sn` */;
/*!50001 DROP VIEW IF EXISTS `v_sn` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_sn` AS select `item_in_detail`.`codeItem` AS `codeItem`,`item_in_detail`.`serialNumber` AS `serialNumber`,`item_in_detail`.`kondisi` AS `kondisi` from `item_in_detail` where ((not(exists(select `item_out_detail`.`serialNumber` from `item_out_detail` where (`item_in_detail`.`serialNumber` = `item_out_detail`.`serialNumber`)))) and (not(exists(select `transfer_order_detail`.`serialNumber` from `transfer_order_detail` where (`item_in_detail`.`serialNumber` = `transfer_order_detail`.`serialNumber`)))) and (`item_in_detail`.`serialNumber` <> '-')) */;

/*View structure for view v_stock_per_kondisi */

/*!50001 DROP TABLE IF EXISTS `v_stock_per_kondisi` */;
/*!50001 DROP VIEW IF EXISTS `v_stock_per_kondisi` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_stock_per_kondisi` AS (select `i`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`c`.`categoryName` AS `categoryName`,`u`.`unitName` AS `unitName`,`i`.`stockMin` AS `stockMin`,`s`.`stock` AS `stock`,`s`.`kondisi` AS `kondisi` from (((`items` `i` join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) join `stock` `s` on((`i`.`codeItem` = `s`.`codeItem`))) where (`s`.`stock` >= `i`.`stockMin`) order by `i`.`codeItem`) */;

/*View structure for view v_stock_total */

/*!50001 DROP TABLE IF EXISTS `v_stock_total` */;
/*!50001 DROP VIEW IF EXISTS `v_stock_total` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_stock_total` AS select `a`.`codeItem` AS `codeItem`,`b`.`itemName` AS `itemName`,sum(`a`.`stock`) AS `stockTotal`,`c`.`unitName` AS `unitName`,`d`.`categoryName` AS `categoryName` from (((`stock` `a` join `items` `b` on((`a`.`codeItem` = `b`.`codeItem`))) join `units` `c` on((`b`.`codeUnit` = `c`.`codeUnit`))) join `category` `d` on((`b`.`codeCategory` = `d`.`codeCategory`))) group by `a`.`codeItem` order by `a`.`codeItem` */;

/*View structure for view v_transfer_order */

/*!50001 DROP TABLE IF EXISTS `v_transfer_order` */;
/*!50001 DROP VIEW IF EXISTS `v_transfer_order` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transfer_order` AS select `too`.`idTransferOrder` AS `idTransferOrder`,`too`.`tanggal` AS `tanggal`,`too`.`codeWarehouse` AS `codeWarehouse`,`w`.`description` AS `descWarehouse`,`too`.`description` AS `description`,`tod`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`tod`.`qty` AS `qty`,`i`.`codeCategory` AS `codeCategory`,`c`.`categoryName` AS `categoryName`,`i`.`codeUnit` AS `codeUnit`,`u`.`unitName` AS `unitName`,`tod`.`serialNumber` AS `serialNumber`,`tod`.`kondisi` AS `kondisi`,`too`.`idUser` AS `idUser`,`us`.`nameUser` AS `nameUser` from ((((((`transfer_order_detail` `tod` join `transfer_order` `too` on((`tod`.`idTransferOrder` = `too`.`idTransferOrder`))) join `items` `i` on((`tod`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) join `users` `us` on((`too`.`idUser` = `us`.`idUser`))) join `warehouse` `w` on((`too`.`codeWarehouse` = `w`.`codeWarehouse`))) */;

/*View structure for view v_transfer_order_qty_all */

/*!50001 DROP TABLE IF EXISTS `v_transfer_order_qty_all` */;
/*!50001 DROP VIEW IF EXISTS `v_transfer_order_qty_all` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transfer_order_qty_all` AS select `too`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`too`.`qty`) AS `qty_all`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName` from (((`transfer_order_detail` `too` join `items` `i` on((`too`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) group by `too`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_transfer_order_qty_new */

/*!50001 DROP TABLE IF EXISTS `v_transfer_order_qty_new` */;
/*!50001 DROP VIEW IF EXISTS `v_transfer_order_qty_new` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transfer_order_qty_new` AS select `too`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`too`.`qty`) AS `qty_second`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName`,`too`.`kondisi` AS `kondisi` from (((`transfer_order_detail` `too` join `items` `i` on((`too`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) where (`too`.`kondisi` = 'BARU') group by `too`.`codeItem` order by `i`.`itemName` */;

/*View structure for view v_transfer_order_qty_second */

/*!50001 DROP TABLE IF EXISTS `v_transfer_order_qty_second` */;
/*!50001 DROP VIEW IF EXISTS `v_transfer_order_qty_second` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transfer_order_qty_second` AS select `too`.`codeItem` AS `codeItem`,`i`.`itemName` AS `itemName`,`i`.`stockMin` AS `stockMin`,sum(`too`.`qty`) AS `qty_second`,`u`.`unitName` AS `unitName`,`c`.`categoryName` AS `categoryName`,`too`.`kondisi` AS `kondisi` from (((`transfer_order_detail` `too` join `items` `i` on((`too`.`codeItem` = `i`.`codeItem`))) join `category` `c` on((`i`.`codeCategory` = `c`.`codeCategory`))) join `units` `u` on((`i`.`codeUnit` = `u`.`codeUnit`))) where (`too`.`kondisi` = 'BEKAS') group by `too`.`codeItem` order by `i`.`itemName` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
