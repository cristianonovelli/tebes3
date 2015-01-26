-- MySQL dump 10.13  Distrib 5.5.25a, for Win64 (x86)
--
-- Host: localhost    Database: tebes
-- ------------------------------------------------------
-- Server version	5.5.25a

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actiondescription`
--

DROP TABLE IF EXISTS `actiondescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actiondescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `testaction_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK79842806C1CFF2F1` (`testaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actiondescription`
--

LOCK TABLES `actiondescription` WRITE;
/*!40000 ALTER TABLE `actiondescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `actiondescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actionworkflow`
--

DROP TABLE IF EXISTS `actionworkflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actionworkflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actionworkflow`
--

LOCK TABLES `actionworkflow` WRITE;
/*!40000 ALTER TABLE `actionworkflow` DISABLE KEYS */;
/*!40000 ALTER TABLE `actionworkflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choreography`
--

DROP TABLE IF EXISTS `choreography`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `choreography` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choreography`
--

LOCK TABLES `choreography` WRITE;
/*!40000 ALTER TABLE `choreography` DISABLE KEYS */;
/*!40000 ALTER TABLE `choreography` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `description`
--

DROP TABLE IF EXISTS `description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `description` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `testplan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK993583FC4041EF03` (`testplan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `description`
--

LOCK TABLES `description` WRITE;
/*!40000 ALTER TABLE `description` DISABLE KEYS */;
/*!40000 ALTER TABLE `description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filestore`
--

DROP TABLE IF EXISTS `filestore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filestore` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fileRefId` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `source` text,
  `creationDatetime` varchar(255) DEFAULT NULL,
  `lastUpdateDatetime` varchar(255) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB2A510E5C9C715D1` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filestore`
--

LOCK TABLES `filestore` WRITE;
/*!40000 ALTER TABLE `filestore` DISABLE KEYS */;
/*!40000 ALTER TABLE `filestore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guidescription`
--

DROP TABLE IF EXISTS `guidescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guidescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `userinput_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA3306FE1D24B93DC` (`userinput_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guidescription`
--

LOCK TABLES `guidescription` WRITE;
/*!40000 ALTER TABLE `guidescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `guidescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inputdescription`
--

DROP TABLE IF EXISTS `inputdescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inputdescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `userinput_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9BA49F2D24B93DC` (`userinput_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inputdescription`
--

LOCK TABLES `inputdescription` WRITE;
/*!40000 ALTER TABLE `inputdescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `inputdescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sessionID` bigint(20) DEFAULT NULL,
  `datetime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `publication` varchar(255) DEFAULT NULL,
  `logLocation` varchar(255) DEFAULT NULL,
  `fullDescription` mediumtext,
  `xml` mediumtext,
  `partialResultSuccessfully` bit(1) NOT NULL,
  `finalResultSuccessfully` bit(1) NOT NULL,
  `globalResult` varchar(255) DEFAULT NULL,
  `atomicResult` varchar(255) DEFAULT NULL,
  `request4Interaction` varchar(255) DEFAULT NULL,
  `tempResult_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC84C553424E8F41` (`tempResult_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportdescription`
--

DROP TABLE IF EXISTS `reportdescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportdescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7567A648D92EA83` (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportdescription`
--

LOCK TABLES `reportdescription` WRITE;
/*!40000 ALTER TABLE `reportdescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportdescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'standard_user','Standard User Role: he can execute a test plan but he can\'t create/edit it',1),(2,'advanced_user','Advanced User Role: he can create/edit/execute a test plan',2),(3,'admin_user','System Administrator Role: he is an advanced user and can add/modify test suites',3),(4,'super_user','Super User Role: he has whole power and permissions on test bed platform',4);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sut`
--

DROP TABLE IF EXISTS `sut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sut` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1BE52AC268D63` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sut`
--

LOCK TABLES `sut` WRITE;
/*!40000 ALTER TABLE `sut` DISABLE KEYS */;
INSERT INTO `sut` VALUES (1,'DefaultSUT','Default SUT Document-WebSite for User Novelli','document',1),(2,'SystemSUT2','System SUT 2: Document - email','document',1),(3,'SystemSUT3','System SUT 3: Document - Web Service Client','document',1),(4,'SystemSUT4','System SUT 4: Document - Web Service Server','document',1),(5,'SystemSUT5','System SUT 5: Transport - email','transport',1),(6,'SystemSUT6','System SUT 6: Transport - Web Service Client','transport',1),(7,'SystemSUT7','System SUT 7: Transport - Web Service Server','transport',1),(8,'SystemSUT8','System SUT 8: Process - WebSite','process',1),(9,'SystemSUT9','System SUT 9: Process - email','process',1),(10,'SystemSUT10','System SUT 10: Process - Web Service Client','process',1),(11,'SystemSUT11','System SUT 11: Process - Web Service Server','process',1);
/*!40000 ALTER TABLE `sut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sutinteraction`
--

DROP TABLE IF EXISTS `sutinteraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sutinteraction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `endpoint` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  `timeout` int(11) NOT NULL,
  `sut_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK62ED4880C8782BD1` (`sut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sutinteraction`
--

LOCK TABLES `sutinteraction` WRITE;
/*!40000 ALTER TABLE `sutinteraction` DISABLE KEYS */;
INSERT INTO `sutinteraction` VALUES (1,'website',NULL,NULL,NULL,0,1),(2,'email',NULL,NULL,NULL,0,2),(3,'webservice-client',NULL,NULL,NULL,0,3),(4,'webservice-server','http://www.webservicex.net/globalweather.asmx?WSDL',NULL,NULL,0,4),(5,'email',NULL,NULL,NULL,0,5),(6,'webservice-client',NULL,NULL,NULL,0,6),(7,'webservice-server','http://www.webservicex.net/globalweather.asmx?WSDL',NULL,NULL,0,7),(8,'website',NULL,NULL,NULL,0,8),(9,'email',NULL,NULL,NULL,0,9),(10,'webservice-client',NULL,NULL,NULL,0,10),(11,'webservice-server','http://www.webservicex.net/globalweather.asmx?WSDL',NULL,NULL,0,11);
/*!40000 ALTER TABLE `sutinteraction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testaction`
--

DROP TABLE IF EXISTS `testaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(255) DEFAULT NULL,
  `prerequisite` bit(1) NOT NULL,
  `actionNumber` int(11) NOT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `actionId` varchar(255) DEFAULT NULL,
  `testLanguage` varchar(255) DEFAULT NULL,
  `testType` varchar(255) DEFAULT NULL,
  `testLocation` varchar(255) DEFAULT NULL,
  `testValue` varchar(255) DEFAULT NULL,
  `skipTurnedON` bit(1) NOT NULL,
  `workflow_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7DF08D486864CDF9` (`workflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testaction`
--

LOCK TABLES `testaction` WRITE;
/*!40000 ALTER TABLE `testaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `testaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testplan`
--

DROP TABLE IF EXISTS `testplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `creationDatetime` varchar(255) DEFAULT NULL,
  `lastUpdateDatetime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `publication` varchar(255) DEFAULT NULL,
  `testplanxml_id` bigint(20) DEFAULT NULL,
  `workflow_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBBB236BBAC268D63` (`user_id`),
  KEY `FKBBB236BBE3ACFC51` (`testplanxml_id`),
  KEY `FKBBB236BB6864CDF9` (`workflow_id`),
  CONSTRAINT `FKBBB236BB6864CDF9` FOREIGN KEY (`workflow_id`) REFERENCES `actionworkflow` (`id`),
  CONSTRAINT `FKBBB236BBE3ACFC51` FOREIGN KEY (`testplanxml_id`) REFERENCES `testplanxml` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplan`
--

LOCK TABLES `testplan` WRITE;
/*!40000 ALTER TABLE `testplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `testplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testplandescription`
--

DROP TABLE IF EXISTS `testplandescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testplandescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `testplan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK11E95A614041EF03` (`testplan_id`),
  CONSTRAINT `FK11E95A614041EF03` FOREIGN KEY (`testplan_id`) REFERENCES `testplan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplandescription`
--

LOCK TABLES `testplandescription` WRITE;
/*!40000 ALTER TABLE `testplandescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `testplandescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testplanxml`
--

DROP TABLE IF EXISTS `testplanxml`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testplanxml` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `absFileName` varchar(255) DEFAULT NULL,
  `xml` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplanxml`
--

LOCK TABLES `testplanxml` WRITE;
/*!40000 ALTER TABLE `testplanxml` DISABLE KEYS */;
/*!40000 ALTER TABLE `testplanxml` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testresult`
--

DROP TABLE IF EXISTS `testresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testresult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `globalResult` varchar(255) DEFAULT NULL,
  `line` int(11) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9B0ED72FD92EA83` (`report_id`),
  CONSTRAINT `FK9B0ED72FD92EA83` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testresult`
--

LOCK TABLES `testresult` WRITE;
/*!40000 ALTER TABLE `testresult` DISABLE KEYS */;
/*!40000 ALTER TABLE `testresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testsession`
--

DROP TABLE IF EXISTS `testsession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testsession` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(255) DEFAULT NULL,
  `localization` varchar(255) DEFAULT NULL,
  `creationDateTime` varchar(255) DEFAULT NULL,
  `lastUpdateDateTime` varchar(255) DEFAULT NULL,
  `messageStore` text,
  `actionMark` int(11) NOT NULL,
  `testPlan_id` bigint(20) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  `sut_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFBB155C4AC268D63` (`user_id`),
  KEY `FKFBB155C4D92EA83` (`report_id`),
  KEY `FKFBB155C44041EF03` (`testPlan_id`),
  KEY `FKFBB155C4C8782BD1` (`sut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testsession`
--

LOCK TABLES `testsession` WRITE;
/*!40000 ALTER TABLE `testsession` DISABLE KEYS */;
INSERT INTO `testsession` VALUES (7,'new','en','2014-11-06T15:29:29Z','2014-11-06T15:29:29Z',NULL,1,6,1,15,2),(8,'new','en','2014-11-06T15:29:30Z','2014-11-06T15:29:30Z',NULL,1,8,2,18,4),(9,'done','en','2014-11-06T15:29:30Z','2014-11-06T15:29:30Z',NULL,2,9,3,19,4);
/*!40000 ALTER TABLE `testsession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text`
--

DROP TABLE IF EXISTS `text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `refId` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36452DC9C715D1` (`session_id`),
  CONSTRAINT `FK36452DC9C715D1` FOREIGN KEY (`session_id`) REFERENCES `testsession` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text`
--

LOCK TABLES `text` WRITE;
/*!40000 ALTER TABLE `text` DISABLE KEYS */;
/*!40000 ALTER TABLE `text` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `textstore`
--

DROP TABLE IF EXISTS `textstore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `textstore` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `refId` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC341E7D4C9C715D1` (`session_id`),
  CONSTRAINT `FKC341E7D4C9C715D1` FOREIGN KEY (`session_id`) REFERENCES `testsession` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `textstore`
--

LOCK TABLES `textstore` WRITE;
/*!40000 ALTER TABLE `textstore` DISABLE KEYS */;
/*!40000 ALTER TABLE `textstore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `eMail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36EBCB6FBC983` (`role_id`),
  KEY `FK36EBCB5F12AF1` (`group_id`),
  CONSTRAINT `FK36EBCB5F12AF1` FOREIGN KEY (`group_id`) REFERENCES `usergroup` (`id`),
  CONSTRAINT `FK36EBCB6FBC983` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Cristiano','Novelli','cristiano.novelli@gmail.com','xcristiano',NULL,4),(2,'Angelo','Frascella','angelo.frascella@enea.it','xangelo',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroup`
--

LOCK TABLES `usergroup` WRITE;
/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinput`
--

DROP TABLE IF EXISTS `userinput`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinput` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `lg` varchar(255) DEFAULT NULL,
  `interaction` varchar(255) DEFAULT NULL,
  `isInteractionOK` bit(1) NOT NULL,
  `fileIdRef` varchar(255) DEFAULT NULL,
  `isInputSolved` bit(1) NOT NULL,
  `guiReaction` varchar(255) DEFAULT NULL,
  `guiMessage` varchar(255) DEFAULT NULL,
  `testaction_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK14C712DFC1CFF2F1` (`testaction_id`),
  CONSTRAINT `FK14C712DFC1CFF2F1` FOREIGN KEY (`testaction_id`) REFERENCES `testaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinput`
--

LOCK TABLES `userinput` WRITE;
/*!40000 ALTER TABLE `userinput` DISABLE KEYS */;
/*!40000 ALTER TABLE `userinput` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinteraction`
--

DROP TABLE IF EXISTS `userinteraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinteraction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `inputInterfaceType` varchar(255) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7CA12567C9C715D1` (`session_id`),
  CONSTRAINT `FK7CA12567C9C715D1` FOREIGN KEY (`session_id`) REFERENCES `testsession` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinteraction`
--

LOCK TABLES `userinteraction` WRITE;
/*!40000 ALTER TABLE `userinteraction` DISABLE KEYS */;
/*!40000 ALTER TABLE `userinteraction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-06 15:40:43
