/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.7.28-log : Database - fruitdb
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fruitdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

/*Table structure for table `t_fruit` */

CREATE TABLE `t_fruit` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) NOT NULL,
  `price` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `t_fruit` */

insert  into `t_fruit`(`fid`,`fname`,`price`,`fcount`,`remark`) values (2,'西瓜',3,31,'西瓜很好吃'),(4,'菠萝',5,63,'OK'),(8,'红富士',5,50,'红富士很好吃'),(9,'香蕉',3,50,'香蕉很好吃'),(10,'榴莲',19,100,'榴莲是一种神奇的水果'),(11,'山竹',8,55,'山竹是一种神奇的水果'),(12,'甘蔗',7,100,'甘蔗是一种神奇的水果'),(13,'萝卜',5,55,'萝卜是一种神奇的水果'),(14,'圣女果',3,99,'好吃'),(15,'哈密瓜',7,77,'哈密瓜很好吃'),(16,'火龙果',5,66,'好吃'),(33,'猕猴桃',15,100,'猕猴桃是水果之王'),(34,'榴莲',15,100,'榴莲是一种神奇的水果'),(35,'雪莲果',15,100,'好吃不贵');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
